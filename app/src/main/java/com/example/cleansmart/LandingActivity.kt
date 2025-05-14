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
import com.example.cleansmart.activities.CameraTaskActivity
import com.example.cleansmart.activities.ProfileActivity
import com.example.cleansmart.databinding.ActivityLandingBinding
import com.example.cleansmart.dialogs.TaskDetailsDialog
import com.example.cleansmart.models.TaskDataTransfer
import com.example.cleansmart.models.TaskGroup
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.repository.TaskGroupRepository
import com.example.cleansmart.utils.SecureStorageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    // Store task data for later use
    private var taskDataCard1: TaskDataTransfer? = null
    private var taskDataCard2: TaskDataTransfer? = null
    
    // Repository for task groups
    private lateinit var taskGroupRepository: TaskGroupRepository
    private lateinit var secureStorageManager: SecureStorageManager
    
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
        
        // Initialize repositories
        taskGroupRepository = TaskGroupRepository(ApiService.create())
        secureStorageManager = SecureStorageManager.getInstance(this)

        setupClickListeners()
        loadUserTaskGroups()
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
                            updateDashboardWithTaskGroups(taskGroups)
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
    
    private fun updateDashboardWithTaskGroups(taskGroups: List<TaskGroup>) {
        if (taskGroups.isEmpty()) return
        
        // Clear existing task cards first
        binding.taskCard1.visibility = View.GONE
        binding.taskCard2.visibility = View.GONE
        taskDataCard1 = null
        taskDataCard2 = null
        
        // Display up to 2 task groups on the dashboard
        taskGroups.take(2).forEachIndexed { index, taskGroup ->
            val taskData = taskGroupRepository.convertToTaskDataTransfer(taskGroup)
            
            if (index == 0) {
                taskDataCard1 = taskData
                updateTaskCard1WithTaskData(taskData, taskGroup.progress)
            } else if (index == 1) {
                taskDataCard2 = taskData
                updateTaskCard2WithTaskData(taskData, taskGroup.progress)
            }
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
                            savedTaskGroups.add(savedTaskGroup)
                            Log.d("LandingActivity", "Task group saved successfully: ${savedTaskGroup.id}")
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
            
            // Navigate to profile screen
            startActivity(Intent(this, ProfileActivity::class.java))
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

        // Set up click listeners for task cards
        binding.taskCard1.setOnClickListener {
            taskDataCard1?.let { data ->
                showTaskDetails(data)
            }
        }

        binding.taskCard2.setOnClickListener {
            taskDataCard2?.let { data ->
                showTaskDetails(data)
            }
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
        // Calculate progress percentage (for demo purposes, random between 0-40%)
        val progressPercentage = (0..40).random()
        
        // Check if card1 is already used, if so use card2
        if (taskDataCard1 == null) {
            // Update Card 1
            taskDataCard1 = taskData
            updateTaskCard1WithTaskData(taskData, progressPercentage)
        } else {
            // Update Card 2
            taskDataCard2 = taskData
            updateTaskCard2WithTaskData(taskData, progressPercentage)
        }
        
        // Show a success message
        Toast.makeText(
            this,
            "${taskData.numberOfTasks} tasks added for ${taskData.areaName}",
            Toast.LENGTH_SHORT
        ).show()
    }
    
    private fun updateTaskCard1WithTaskData(taskData: TaskDataTransfer, progressPercentage: Int) {
        // Format the date for display
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(Date(taskData.dateCreated))
        
        binding.apply {
            dateText1.text = formattedDate
            todoTitle1.text = "${taskData.areaName} Tasks"
            progressBar1.progress = progressPercentage
            progressLabel1.text = "Progress: $progressPercentage%"
            timeText1.text = "${taskData.numberOfTasks} tasks to complete"
            
            // Make Card 1 visible
            taskCard1.visibility = View.VISIBLE
        }
    }
    
    private fun updateTaskCard2WithTaskData(taskData: TaskDataTransfer, progressPercentage: Int) {
        // Format the date for display
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(Date(taskData.dateCreated))
        
        binding.apply {
            dateText2.text = formattedDate
            todoTitle2.text = "${taskData.areaName} Tasks"
            progressBar2.progress = progressPercentage
            progressLabel2.text = "Progress: $progressPercentage%"
            timeText2.text = "${taskData.numberOfTasks} tasks to complete"
            
            // Make Card 2 visible
            taskCard2.visibility = View.VISIBLE
        }
    }
    
    private fun showTaskDetails(taskData: TaskDataTransfer) {
        val dialog = TaskDetailsDialog(this, taskData)
        dialog.show()
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
}