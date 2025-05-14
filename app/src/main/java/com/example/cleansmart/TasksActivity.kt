package com.example.cleansmart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleansmart.activities.CameraTaskActivity
import com.example.cleansmart.activities.ProfileActivity
import com.example.cleansmart.adapters.TaskAdapter
import com.example.cleansmart.databinding.ActivityTasksBinding
import com.example.cleansmart.models.Task
import com.example.cleansmart.models.TaskGroup
import com.example.cleansmart.utils.SecureStorageManager
import com.example.cleansmart.viewmodels.TaskViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTasksBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var secureStorageManager: SecureStorageManager
    
    // Current active task group
    private var currentTaskGroup: TaskGroup? = null
    // All available task groups
    private var allTaskGroups: List<TaskGroup> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        secureStorageManager = SecureStorageManager.getInstance(this)
        
        // Initialize ViewModel with application context
        viewModel = ViewModelProvider(this, 
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[TaskViewModel::class.java]
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
        
        // Check if we should show all task groups
        val showAllTaskGroups = intent.getBooleanExtra("show_all_task_groups", false)
        if (showAllTaskGroups) {
            // Load all task groups and show the task group selector dialog automatically
            loadTaskGroups()
            // We'll show the selector dialog once data is loaded in observeTaskGroups()
        } else {
            // Check if we received a specific task group ID
            val taskGroupId = intent.getStringExtra("task_group_id")
            if (taskGroupId != null) {
                // Will load this specific task group when task groups are loaded
                loadTaskGroupsWithSelected(taskGroupId)
            } else {
                // Load all task groups and show the first one by default
                loadTaskGroups()
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tasks_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_group -> {
                showTaskGroupSelectionDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            this,
            emptyList(),
            { task, isCheckboxClick ->
                // Handle task click/completion
                onTaskClicked(task, isCheckboxClick)
            },
            { task -> 
                // Handle long press for deletion
                showDeleteTaskDialog(task)
                true // Return true to indicate the event is consumed
            }
        )
        
        binding.tasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TasksActivity)
            adapter = taskAdapter
        }
    }
    
    private fun setupObservers() {
        // Observe task groups loading
        observeTaskGroups()
        
        // Observe tasks loading
        viewModel.tasks.observe(this) { tasks ->
            taskAdapter.updateTasks(tasks)
        }
        
        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        // Observe error state
        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        
        // Observe task group progress updates
        viewModel.taskGroupProgress.observe(this) { progressMap ->
            currentTaskGroup?.id?.let { taskGroupId ->
                progressMap[taskGroupId]?.let { progress ->
                    // Update the progress bar
                    binding.taskProgressBar.progress = progress
                    binding.progressPercentage.text = "$progress%"
                    
                    // Check if all tasks are completed
                    if (progress == 100) {
                        Toast.makeText(this, "All tasks completed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    
    private fun setupClickListeners() {
        // Set up bottom navigation
        binding.bottomNavigation.tasksButton.isSelected = true // Highlight current screen
        
        // Home button navigates to LandingActivity
        binding.bottomNavigation.homeButton.setOnClickListener {
            startActivity(Intent(this, LandingActivity::class.java))
            finish()
        }
        
        // Profile button navigates to ProfileActivity
        binding.bottomNavigation.profileButton.setOnClickListener {
            try {
                startActivity(Intent(this, ProfileActivity::class.java))
            } catch (e: Exception) {
                Log.e("TasksActivity", "Error opening Profile: ${e.message}")
                Toast.makeText(this, "Error: Profile screen not available", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Add task FAB
        binding.addTaskFab.setOnClickListener {
            // Navigate to add task or camera task activity
            val intent = Intent(this, CameraTaskActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun loadTaskGroups() {
        val userId = secureStorageManager.getUserId()
        
        if (userId != null) {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.loadTaskGroups(userId)
        } else {
            // User isn't logged in, redirect to login
            Toast.makeText(this, "Please sign in to view your tasks", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }
    }
    
    private fun loadTaskGroupsWithSelected(selectedTaskGroupId: String) {
        val userId = secureStorageManager.getUserId()
        
        if (userId != null) {
            binding.progressBar.visibility = View.VISIBLE
            
            // Add a timeout handler to detect when loading fails
            val loadTimeoutHandler = Handler(android.os.Looper.getMainLooper())
            val timeoutRunnable = Runnable {
                Log.w("TasksActivity", "Task group loading timed out, attempting fallback")
                tryFallbackLoad(userId, selectedTaskGroupId)
            }
            
            // Set timeout to 5 seconds
            loadTimeoutHandler.postDelayed(timeoutRunnable, 5000)
            
            viewModel.loadTaskGroups(userId)
            
            // We need to observe task groups loading to find the one with matching ID
            viewModel.taskGroups.observe(this) { taskGroups ->
                // Cancel the timeout since we got a response
                loadTimeoutHandler.removeCallbacks(timeoutRunnable)
                
                // Log available task group IDs for debugging
                Log.d("TasksActivity", "Searching for task group ID: $selectedTaskGroupId")
                Log.d("TasksActivity", "Available task group IDs: ${taskGroups.map { it.id }}")
                
                // Find the specific task group by ID
                val selectedGroup = taskGroups.find { it.id == selectedTaskGroupId }
                
                if (selectedGroup != null) {
                    // Update UI with the selected task group
                    Log.d("TasksActivity", "Found task group: ${selectedGroup.areaName}")
                    currentTaskGroup = selectedGroup
                    updateTaskGroupUI()
                    viewModel.loadTasksForGroup(selectedGroup)
                    
                    // Remove observer after finding the group
                    viewModel.taskGroups.removeObservers(this)
                    
                    // Re-observe task groups for future updates
                    observeTaskGroups()
                } else {
                    // If we can't find the task group by exact ID, try to match by area name
                    // This serves as a fallback in case ID generation is inconsistent
                    Log.e("TasksActivity", "Task group not found with ID: $selectedTaskGroupId")
                    
                    // First try to create a simple task group locally as fallback
                    tryLocalFallback(selectedTaskGroupId)
                    
                    if (taskGroups.isNotEmpty()) {
                        Log.d("TasksActivity", "Defaulting to first available task group")
                        currentTaskGroup = taskGroups.first()
                        updateTaskGroupUI()
                        viewModel.loadTasksForGroup(taskGroups.first())
                    } else {
                        // If no task groups are available, show a message and navigate back
                        Log.e("TasksActivity", "No task groups available")
                        Toast.makeText(this, "No task groups available. Please create a task group first.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            // User isn't logged in, redirect to login
            Toast.makeText(this, "Please sign in to view your tasks", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }
    }
    
    // Try a fallback load if the main load fails
    private fun tryFallbackLoad(userId: String, selectedTaskGroupId: String) {
        Log.d("TasksActivity", "Trying fallback load for task group ID: $selectedTaskGroupId")
        lifecycleScope.launch {
            try {
                // Use repository directly
                val taskGroupRepository = com.example.cleansmart.repository.TaskGroupRepository(
                    com.example.cleansmart.network.ApiService.create()
                )
                val result = taskGroupRepository.getUserTaskGroups(userId)
                if (result.isSuccess) {
                    val taskGroups = result.getOrNull() ?: emptyList()
                    Log.d("TasksActivity", "Fallback load found ${taskGroups.size} task groups")
                    
                    // Process on main thread
                    withContext(kotlinx.coroutines.Dispatchers.Main) {
                        if (taskGroups.isNotEmpty()) {
                            // Try to find the requested group
                            val selectedGroup = taskGroups.find { it.id == selectedTaskGroupId }
                            
                            if (selectedGroup != null) {
                                Log.d("TasksActivity", "Fallback found the requested task group")
                                currentTaskGroup = selectedGroup
                                updateTaskGroupUI()
                                viewModel.loadTasksForGroup(selectedGroup)
                            } else {
                                Log.d("TasksActivity", "Fallback using first available task group")
                                currentTaskGroup = taskGroups.first()
                                updateTaskGroupUI()
                                viewModel.loadTasksForGroup(taskGroups.first())
                            }
                        } else {
                            Log.e("TasksActivity", "Fallback found no task groups")
                            Toast.makeText(this@TasksActivity, "No task groups found. Please create one first.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e("TasksActivity", "Fallback load failed: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                Log.e("TasksActivity", "Error in fallback load", e)
            }
        }
    }
    
    // Create a local task group as a fallback
    private fun tryLocalFallback(taskGroupId: String) {
        Log.d("TasksActivity", "Creating local fallback task group with ID: $taskGroupId")
        
        val userId = secureStorageManager.getUserId() ?: return
        
        // Create a simple local task group
        val fallbackTaskGroup = TaskGroup(
            id = taskGroupId,
            userId = userId,
            areaName = "Fallback Tasks",
            tasks = listOf("Task 1", "Task 2", "Task 3"),
            progress = 0
        )
        
        // Update UI with the fallback task group
        currentTaskGroup = fallbackTaskGroup
        updateTaskGroupUI()
        viewModel.loadTasksForGroup(fallbackTaskGroup)
        
        Toast.makeText(this, "Using fallback task list", Toast.LENGTH_SHORT).show()
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, SignInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }
    
    private fun observeTaskGroups() {
        // Observe task groups loading for UI updates
        viewModel.taskGroups.observe(this) { taskGroups ->
            allTaskGroups = taskGroups
            
            // Check if we should show all task groups selector
            if (intent.getBooleanExtra("show_all_task_groups", false) && taskGroups.isNotEmpty()) {
                // Show task group selector dialog automatically
                showTaskGroupSelectionDialog()
            } else if (taskGroups.isNotEmpty() && currentTaskGroup == null) {
                // Load the first task group by default if none is selected
                currentTaskGroup = taskGroups.first()
                updateTaskGroupUI()
                viewModel.loadTasksForGroup(currentTaskGroup!!)
            }
        }
    }
    
    private fun onTaskClicked(task: Task, isCheckboxClick: Boolean) {
        currentTaskGroup?.id?.let { taskGroupId ->
            // No need to determine new completion status, as the adapter now provides 
            // a new Task object with the updated isCompleted property
            val newCompletionStatus = task.isCompleted
            
            // Update the task and progress
            viewModel.updateTaskCompletion(task, newCompletionStatus, taskGroupId)
            
            // Show a toast message
            val message = if (newCompletionStatus) "Task marked as complete" else "Task marked as incomplete"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showTaskGroupSelectionDialog() {
        if (allTaskGroups.isEmpty()) {
            Toast.makeText(this, "No task groups available", Toast.LENGTH_SHORT).show()
            return
        }
        
        val taskGroupNames = allTaskGroups.map { it.areaName }.toTypedArray()
        
        val currentSelection = allTaskGroups.indexOfFirst { it.id == currentTaskGroup?.id } 
            .takeIf { it >= 0 } ?: 0
        
        // Create a dialog title based on whether we're showing from "View All" or not
        val dialogTitle = if (intent.getBooleanExtra("show_all_task_groups", false)) {
            "All Task Groups"
        } else {
            "Select Task Group"
        }
        
        // Create items with progress information
        val taskGroupItems = allTaskGroups.map { group ->
            val progress = viewModel.taskGroupProgress.value?.get(group.id) ?: group.progress
            "${group.areaName} (${progress}% complete)"
        }.toTypedArray()
        
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(dialogTitle)
            .setSingleChoiceItems(taskGroupItems, currentSelection) { dialog, which ->
                val selectedTaskGroup = allTaskGroups[which]
                currentTaskGroup = selectedTaskGroup
                updateTaskGroupUI()
                viewModel.loadTasksForGroup(selectedTaskGroup)
                dialog.dismiss()
            }
            .setPositiveButton("Select") { dialog, _ ->
                // This will only execute if the user clicks Select without selecting an item
                val selectedTaskGroup = allTaskGroups[currentSelection]
                currentTaskGroup = selectedTaskGroup
                updateTaskGroupUI()
                viewModel.loadTasksForGroup(selectedTaskGroup)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // If we came from "View All" and canceled without selecting,
                // go back to the landing page
                if (intent.getBooleanExtra("show_all_task_groups", false) && currentTaskGroup == null) {
                    finish()
                }
                dialog.dismiss()
            }
            .show()
    }
    
    private fun updateTaskGroupUI() {
        currentTaskGroup?.let { group ->
            binding.tasksTitle.text = "${group.areaName} Tasks"
            // Update progress
            val progress = viewModel.taskGroupProgress.value?.get(group.id) ?: 0
            binding.taskProgressBar.progress = progress
            binding.progressPercentage.text = "$progress%"
        }
    }
    
    private fun showDeleteTaskDialog(task: Task) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete the task '${task.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                deleteTask(task)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun deleteTask(task: Task) {
        // Find the task in the current task list
        val currentTasks = viewModel.tasks.value?.toMutableList() ?: return
        val taskIndex = currentTasks.indexOfFirst { it.id == task.id }
        
        if (taskIndex != -1) {
            // Remove the task
            currentTasks.removeAt(taskIndex)
            
            // Update the adapter
            taskAdapter.updateTasks(currentTasks)
            
            // Update the task group progress
            currentTaskGroup?.id?.let { taskGroupId ->
                // Calculate the new progress percentage
                val completedCount = currentTasks.count { it.isCompleted }
                val newProgress = if (currentTasks.isNotEmpty()) {
                    (completedCount * 100) / currentTasks.size
                } else {
                    0
                }
                
                // Update progress in ViewModel
                viewModel.updateTaskGroupProgress(taskGroupId, newProgress)
                
                // Remove the task's completion status
                viewModel.removeTaskCompletionStatus(task.id)
            }
            
            // Show success message
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
        }
    }
} 