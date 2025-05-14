package com.example.cleansmart

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleansmart.activities.CameraTaskActivity
import com.example.cleansmart.activities.ProfileActivity
import com.example.cleansmart.adapters.TaskGroupAdapter
import com.example.cleansmart.databinding.ActivityLandingBinding
import com.example.cleansmart.dialogs.TaskDetailsDialog
import com.example.cleansmart.models.TaskDataTransfer
import com.example.cleansmart.models.TaskGroup
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.repository.TaskGroupRepository
import com.example.cleansmart.utils.SecureStorageManager
import com.example.cleansmart.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    // Adapter for task groups
    private lateinit var taskGroupAdapter: TaskGroupAdapter
    // Repository for task groups
    private lateinit var taskGroupRepository: TaskGroupRepository
    private lateinit var secureStorageManager: SecureStorageManager
    private lateinit var sessionManager: SessionManager
    
    // Track saved task groups
    private var savedTaskGroups: MutableList<TaskGroup> = mutableListOf()

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_LONG).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "Image captured successfully", Toast.LENGTH_SHORT).show()
            // Handle the captured image here
        }
    }

    // Activity result launcher for CameraTaskActivity
    private val cameraTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Get the task data using the appropriate method based on Android version
            val taskData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra(CameraTaskActivity.RESULT_TASK_DATA, TaskDataTransfer::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getParcelableExtra(CameraTaskActivity.RESULT_TASK_DATA)
            }
            
            taskData?.let {
                updateDashboardWithTaskData(it)
                saveTaskGroupToBackend(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize repositories and managers
        taskGroupRepository = TaskGroupRepository(ApiService.create())
        secureStorageManager = SecureStorageManager.getInstance(this)
        sessionManager = SessionManager(this)

        setupRecyclerView()
        setupClickListeners()
        loadUserTaskGroups()
        updateUserGreeting()
        
        // Comment out or remove the test task group creation
        // val userId = secureStorageManager.getUserId()
        // if (userId != null) {
        //     createTestTaskGroup(userId)
        // }
    }
    
    private fun setupRecyclerView() {
        // Initialize the adapter with empty list
        taskGroupAdapter = TaskGroupAdapter(
            this,
            emptyList(),
            { taskGroup -> 
                // Handle click on task group
                showTaskDetails(taskGroupRepository.convertToTaskDataTransfer(taskGroup))
            },
            { taskGroup ->
                // Handle long press on task group (for deletion)
                showDeleteTaskGroupDialog(taskGroupRepository.convertToTaskDataTransfer(taskGroup))
                true
            }
        )
        
        // Set up RecyclerView
        binding.taskGroupsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@LandingActivity)
            adapter = taskGroupAdapter
        }
    }
    
    override fun onResume() {
        super.onResume()
        
        // Reload task groups when returning to this activity
        loadUserTaskGroups()
        
        // Update user greeting in case name was changed in profile
        updateUserGreeting()
        
        Log.d("LandingActivity", "onResume: Reloading task groups")
    }
    
    private fun loadUserTaskGroups() {
        val userId = secureStorageManager.getUserId()
        if (userId != null) {
            lifecycleScope.launch {
                try {
                    val result = taskGroupRepository.getUserTaskGroups(userId)
                    if (result.isSuccess) {
                        val taskGroups = result.getOrNull() ?: emptyList()
                        savedTaskGroups.clear()
                        savedTaskGroups.addAll(taskGroups)
                        
                        // Update UI with loaded task groups
                        withContext(Dispatchers.Main) {
                            updateTaskGroupsRecyclerView(taskGroups)
                        }
                    } else {
                        val error = result.exceptionOrNull()?.message ?: "Unknown error"
                        Log.e("LandingActivity", "Failed to load task groups: $error")
                    }
                } catch (e: Exception) {
                    Log.e("LandingActivity", "Error loading task groups", e)
                }
            }
        }
    }
    
    private fun updateTaskGroupsRecyclerView(taskGroups: List<TaskGroup>) {
        if (taskGroups.isEmpty()) {
            // Show empty state
            binding.emptyTasksText.visibility = View.VISIBLE
            binding.taskGroupsRecyclerView.visibility = View.GONE
        } else {
            // Show task groups
            binding.emptyTasksText.visibility = View.GONE
            binding.taskGroupsRecyclerView.visibility = View.VISIBLE
            
            // Update the adapter
            taskGroupAdapter.updateTaskGroups(taskGroups)
        }
    }

    private fun saveTaskGroupToBackend(taskData: TaskDataTransfer) {
        val userId = secureStorageManager.getUserId()
        if (userId != null) {
            lifecycleScope.launch {
                try {
                    val result = taskGroupRepository.saveTaskGroup(userId, taskData)
                    if (result.isSuccess) {
                        val savedTaskGroup = result.getOrNull()
                        if (savedTaskGroup != null) {
                            // Add to the local list
                            savedTaskGroups.add(savedTaskGroup)
                            Log.d("LandingActivity", "Task group saved successfully: ${savedTaskGroup.id}")
                            
                            // Refresh the RecyclerView
                            withContext(Dispatchers.Main) {
                                updateTaskGroupsRecyclerView(savedTaskGroups)
                            }
                        }
                    } else {
                        val error = result.exceptionOrNull()?.message ?: "Unknown error"
                        Log.e("LandingActivity", "Failed to save task group: $error")
                    }
                } catch (e: Exception) {
                    Log.e("LandingActivity", "Error saving task group", e)
                }
            }
        } else {
            Log.w("LandingActivity", "Cannot save task group: User not logged in")
        }
    }

    private fun navigateToLogin() {
        val loginIntent = Intent(this, SignInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(loginIntent)
        finish()
    }

    private fun setupClickListeners() {
        // Bottom navigation setup
        binding.bottomNavigation.profileButton.setOnClickListener {
            Log.d("Android Assignment", "Profile button clicked")
            
            // Navigate to profile screen with error handling 
            try {
                startActivity(Intent(this, ProfileActivity::class.java))
            } catch (e: Exception) {
                Log.e("Android Assignment", "Error opening Profile: ${e.message}")
                Toast.makeText(this, "Error: Profile screen not available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.bottomNavigation.settingsButton.setOnClickListener {
            Log.d("Android Assignment", "Settings button clicked")
            try {
                startActivity(Intent(this, SettingsActivity::class.java))
            } catch (e: Exception) {
                Log.e("Android Assignment", "Error opening Settings: ${e.message}")
                Toast.makeText(this, "Error: Settings screen not available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.bottomNavigation.homeButton.setOnClickListener {
            Log.d("Android Assignment", "Home button clicked")
            // Already on home/landing screen, no need to navigate
        }

        binding.bottomNavigation.tasksButton.setOnClickListener {
            Log.d("Android Assignment", "Tasks button clicked")
            try {
                startActivity(Intent(this, TasksActivity::class.java))
            } catch (e: Exception) {
                Log.e("Android Assignment", "Error opening Tasks: ${e.message}")
                Toast.makeText(this, "Error: Tasks screen not available", Toast.LENGTH_SHORT).show()
            }
        }

        // Quick Actions setup
        binding.cameraAction.setOnClickListener {
            navigateToCameraTaskActivity()
        }

        binding.scanAction.setOnClickListener {
            // TODO: Implement QR code scanning functionality
            Toast.makeText(this, "QR code scanning coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.addTaskAction.setOnClickListener {
            try {
                startActivity(Intent(this, TasksActivity::class.java))
            } catch (e: Exception) {
                Log.e("Android Assignment", "Error opening Tasks: ${e.message}")
                Toast.makeText(this, "Error: Tasks screen not available", Toast.LENGTH_SHORT).show()
            }
        }

        // Floating action button setup
        binding.cameraFab.setOnClickListener {
            navigateToCameraTaskActivity()
        }
    }

    private fun navigateToCameraTaskActivity() {
        try {
            // Use the activity result launcher to start CameraTaskActivity
            cameraTaskLauncher.launch(Intent(this, CameraTaskActivity::class.java))
        } catch (e: Exception) {
            Log.e("Android Assignment", "Error opening Camera Task: ${e.message}")
            Toast.makeText(this, "Error: Camera Task screen not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateDashboardWithTaskData(taskData: TaskDataTransfer) {
        // Convert TaskDataTransfer to TaskGroup with 0% progress
        val newTaskGroup = TaskGroup(
            id = taskData.id,
            userId = secureStorageManager.getUserId() ?: "",
            areaName = taskData.areaName,
            imageBase64 = taskData.imageBase64,
            tasks = taskData.taskTitles,
            progress = 0,
            dateCreated = taskData.dateCreated
        )
        
        // Add to our list and update RecyclerView
        savedTaskGroups.add(newTaskGroup)
        updateTaskGroupsRecyclerView(savedTaskGroups)
        
        // Show a success message
        Toast.makeText(
            this,
            "${taskData.numberOfTasks} tasks added for ${taskData.areaName}",
            Toast.LENGTH_SHORT
        ).show()
    }
    
    private fun showTaskDetails(taskData: TaskDataTransfer) {
        // Log the ID being passed for tracking purposes
        Log.d("LandingActivity", "Opening task group with ID: ${taskData.id}")
        
        // Navigate to TasksActivity instead of showing dialog
        val intent = Intent(this, TasksActivity::class.java).apply {
            putExtra("task_group_id", taskData.id)
        }
        startActivity(intent)
    }

    private fun checkCameraPermissionAndOpen() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            takePictureLauncher.launch(takePictureIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            Log.e("Android Assignment", "Error opening camera: ${e.message}")
        }
    }

    // Method to create a test task group for debugging
    private fun createTestTaskGroup(userId: String) {
        // Only run this in debug builds or when explicitly testing
        val testTaskData = TaskDataTransfer(
            areaName = "Test Area ${System.currentTimeMillis()}",
            numberOfTasks = 3,
            taskTitles = listOf("Test Task 1", "Test Task 2", "Test Task 3")
        )
        
        Log.d("LandingActivity", "Creating test task group with ID: ${testTaskData.id}")
        
        lifecycleScope.launch {
            try {
                val result = taskGroupRepository.saveTaskGroup(userId, testTaskData)
                if (result.isSuccess) {
                    val savedTaskGroup = result.getOrNull()
                    Log.d("LandingActivity", "Test task group saved successfully: ${savedTaskGroup?.id}")
                    
                    // Immediately try to load it back to verify
                    val loadResult = taskGroupRepository.getUserTaskGroups(userId)
                    if (loadResult.isSuccess) {
                        val groups = loadResult.getOrNull() ?: emptyList()
                        Log.d("LandingActivity", "Loaded ${groups.size} task groups after save")
                        Log.d("LandingActivity", "Group IDs: ${groups.map { it.id }}")
                        Log.d("LandingActivity", "Contains test ID? ${groups.any { it.id == testTaskData.id }}")
                    } else {
                        Log.e("LandingActivity", "Failed to load after save: ${loadResult.exceptionOrNull()?.message}")
                    }
                } else {
                    Log.e("LandingActivity", "Failed to save test task group: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                Log.e("LandingActivity", "Error creating test task group", e)
            }
        }
    }

    private fun showDeleteTaskGroupDialog(taskData: TaskDataTransfer) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Delete Task Group")
            .setMessage("Are you sure you want to delete the '${taskData.areaName}' task group? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                deleteTaskGroup(taskData.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun deleteTaskGroup(taskGroupId: String) {
        val userId = secureStorageManager.getUserId() ?: return
        
        lifecycleScope.launch {
            try {
                // Show loading indicator
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LandingActivity, "Deleting task group...", Toast.LENGTH_SHORT).show()
                }
                
                val result = taskGroupRepository.deleteTaskGroup(taskGroupId)
                
                withContext(Dispatchers.Main) {
                    if (result.isSuccess) {
                        Toast.makeText(this@LandingActivity, "Task group deleted successfully", Toast.LENGTH_SHORT).show()
                        // Reload task groups to refresh the UI
                        loadUserTaskGroups()
                    } else {
                        val error = result.exceptionOrNull()?.message ?: "Unknown error"
                        Toast.makeText(this@LandingActivity, "Failed to delete task group: $error", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LandingActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateUserGreeting() {
        // Get user's name from SessionManager
        val userName = sessionManager.getUserName()
        
        // Update the greeting text based on whether we have a name or not
        if (!userName.isNullOrBlank()) {
            binding.hiText.text = "Hi $userName"
        } else {
            // Fallback to generic greeting if name is not available
            binding.hiText.text = "Hi there"
        }
    }
}