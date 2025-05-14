package com.example.cleansmart.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleansmart.models.Task
import com.example.cleansmart.models.TaskGroup
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.repository.TaskGroupRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = ApiService.create()
    private val taskGroupRepository = TaskGroupRepository(apiService)
    
    // SharedPreferences to persist task completion status
    private val prefs: SharedPreferences = application.getSharedPreferences(
        "task_completion_prefs", Context.MODE_PRIVATE
    )

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _taskGroups = MutableLiveData<List<TaskGroup>>()
    val taskGroups: LiveData<List<TaskGroup>> = _taskGroups

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _taskGroupProgress = MutableLiveData<Map<String, Int>>()
    val taskGroupProgress: LiveData<Map<String, Int>> = _taskGroupProgress
    
    // Map to store completion status of tasks, using task ID as key
    private val taskCompletionStatus = mutableMapOf<String, Boolean>()

    // Load task groups for the user
    fun loadTaskGroups(userId: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val result = taskGroupRepository.getUserTaskGroups(userId)
                if (result.isSuccess) {
                    val groups = result.getOrNull() ?: emptyList()
                    _taskGroups.postValue(groups)
                    
                    // Initialize progress mapping
                    val progressMap = groups.associate { it.id to it.progress }
                    _taskGroupProgress.postValue(progressMap)
                } else {
                    _error.postValue(result.exceptionOrNull()?.message ?: "Failed to load task groups")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    // Update task completion status and adjust the task group progress
    fun updateTaskCompletion(task: Task, isCompleted: Boolean, taskGroupId: String) {
        // Update the task in the local list
        val currentTasks = _tasks.value?.toMutableList() ?: mutableListOf()
        val taskIndex = currentTasks.indexOfFirst { it.id == task.id }
        
        if (taskIndex != -1) {
            val updatedTask = task.copy(isCompleted = isCompleted)
            currentTasks[taskIndex] = updatedTask
            _tasks.value = currentTasks
            
            // Store the completion status to persist it
            taskCompletionStatus[task.id] = isCompleted
            
            // Save to SharedPreferences
            saveTaskCompletionStatus(task.id, isCompleted)
            
            // Calculate the new progress percentage
            val completedCount = currentTasks.count { it.isCompleted }
            val newProgress = if (currentTasks.isNotEmpty()) {
                (completedCount * 100) / currentTasks.size
            } else {
                0
            }
            
            // Update the progress in the task group
            updateTaskGroupProgress(taskGroupId, newProgress)
        }
    }

    // Load tasks for a specific task group
    fun loadTasksForGroup(taskGroup: TaskGroup) {
        val tasks = taskGroup.tasks.mapIndexed { index, taskTitle ->
            val taskId = "${taskGroup.id}_task_$index"
            // Get completion status from SharedPreferences
            val isCompleted = getTaskCompletionStatus(taskId)
            
            // Store in memory map
            taskCompletionStatus[taskId] = isCompleted
            
            Task(
                id = taskId,
                title = taskTitle,
                description = "Task for ${taskGroup.areaName}",
                area = taskGroup.areaName,
                priority = com.example.cleansmart.models.Priority.MEDIUM,
                isCompleted = isCompleted // Use the stored status or default to false
            )
        }
        _tasks.value = tasks
    }

    // Public method to update a task group's progress
    fun updateTaskGroupProgress(taskGroupId: String, progress: Int) {
        // Update the local progress map
        val currentProgressMap = _taskGroupProgress.value?.toMutableMap() ?: mutableMapOf()
        currentProgressMap[taskGroupId] = progress
        _taskGroupProgress.value = currentProgressMap
        
        android.util.Log.d("TaskViewModel", "Updating progress for task group $taskGroupId to $progress%")
        
        // Update the progress on the server
        viewModelScope.launch {
            try {
                android.util.Log.d("TaskViewModel", "Sending progress update to server...")
                val result = taskGroupRepository.updateTaskGroupProgress(taskGroupId, progress)
                if (result.isSuccess) {
                    val updatedGroup = result.getOrNull()
                    android.util.Log.d("TaskViewModel", "Server update success. Server returned progress: ${updatedGroup?.progress}%")
                } else {
                    val error = result.exceptionOrNull()?.message ?: "Unknown error"
                    android.util.Log.e("TaskViewModel", "Failed to update progress on server: $error")
                    _error.postValue("Failed to update progress: $error")
                }
            } catch (e: Exception) {
                android.util.Log.e("TaskViewModel", "Error updating progress: ${e.message}", e)
                _error.postValue("Error updating progress: ${e.message}")
            }
        }
    }
    
    // Save task completion status to SharedPreferences
    private fun saveTaskCompletionStatus(taskId: String, isCompleted: Boolean) {
        prefs.edit().putBoolean(taskId, isCompleted).apply()
    }
    
    // Get task completion status from SharedPreferences
    private fun getTaskCompletionStatus(taskId: String): Boolean {
        return prefs.getBoolean(taskId, false)
    }
    
    // Remove task completion status when a task is deleted
    fun removeTaskCompletionStatus(taskId: String) {
        // Remove from in-memory map
        taskCompletionStatus.remove(taskId)
        
        // Remove from SharedPreferences
        prefs.edit().remove(taskId).apply()
        
        android.util.Log.d("TaskViewModel", "Removed completion status for task: $taskId")
    }
} 