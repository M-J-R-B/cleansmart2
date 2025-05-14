package com.example.cleansmart.repository

import com.example.cleansmart.models.TaskGroup
import com.example.cleansmart.models.SaveTaskGroupRequest
import com.example.cleansmart.models.TaskDataTransfer
import com.example.cleansmart.network.UpdateProgressRequest
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import retrofit2.HttpException
import android.util.Log

class TaskGroupRepository(private val apiService: ApiService) {

    suspend fun saveTaskGroup(userId: String, taskData: TaskDataTransfer): Result<TaskGroup> {
        return withContext(Dispatchers.IO) {
            try {
                // Convert TaskDataTransfer to TaskGroup
                val taskGroup = TaskGroup(
                    id = taskData.id,
                    userId = userId,
                    areaName = taskData.areaName,
                    imageBase64 = taskData.imageBase64,
                    tasks = taskData.taskTitles,
                    progress = 0 // New task groups start with 0 progress
                )
                
                Log.d("TaskGroupRepository", "Saving task group with ID: ${taskGroup.id}")
                
                val request = SaveTaskGroupRequest(taskGroup)
                val response = apiService.saveTaskGroup(request)
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val savedTaskGroup = response.body()?.taskGroup
                    if (savedTaskGroup != null) {
                        Log.d("TaskGroupRepository", "Task group saved successfully. Server returned ID: ${savedTaskGroup.id}")
                        Result.success(savedTaskGroup)
                    } else {
                        Log.e("TaskGroupRepository", "Task group saved but not returned in response")
                        Result.failure(Exception("Task group saved but not returned"))
                    }
                } else {
                    val errorMsg = "Failed to save task group: ${response.message()}, code: ${response.code()}"
                    Log.e("TaskGroupRepository", errorMsg)
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: NetworkClient.NoConnectivityException) {
                Result.failure(Exception("No network connection. Please check your internet connection and try again."))
            } catch (e: NetworkClient.NetworkTimeoutException) {
                Result.failure(Exception("Network timeout. Server is taking too long to respond."))
            } catch (e: NetworkClient.ServerUnreachableException) {
                Result.failure(Exception("Cannot reach server. Please check your network settings."))
            } catch (e: NetworkClient.ConnectionFailureException) {
                Result.failure(Exception("Connection failure. Please try again later."))
            } catch (e: HttpException) {
                Result.failure(Exception("HTTP Error: ${e.code()} - ${e.message()}"))
            } catch (e: IOException) {
                Result.failure(Exception("Network Error: ${e.message}"))
            } catch (e: Exception) {
                Result.failure(Exception("Error saving task group: ${e.message}"))
            }
        }
    }
    
    suspend fun getUserTaskGroups(userId: String): Result<List<TaskGroup>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("TaskGroupRepository", "Fetching task groups for user: $userId")
                val response = apiService.getUserTaskGroups(userId)
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val taskGroups = response.body()?.taskGroups ?: emptyList()
                    Log.d("TaskGroupRepository", "Received ${taskGroups.size} task groups: ${taskGroups.map { it.id }}")
                    Result.success(taskGroups)
                } else {
                    val errorMsg = "Failed to load task groups: ${response.message()}, code: ${response.code()}"
                    Log.e("TaskGroupRepository", errorMsg)
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: NetworkClient.NoConnectivityException) {
                Result.failure(Exception("No network connection. Please check your internet connection and try again."))
            } catch (e: NetworkClient.NetworkTimeoutException) {
                Result.failure(Exception("Network timeout. Server is taking too long to respond."))
            } catch (e: NetworkClient.ServerUnreachableException) {
                Result.failure(Exception("Cannot reach server. Please check your network settings."))
            } catch (e: NetworkClient.ConnectionFailureException) {
                Result.failure(Exception("Connection failure. Please try again later."))
            } catch (e: HttpException) {
                Result.failure(Exception("HTTP Error: ${e.code()} - ${e.message()}"))
            } catch (e: IOException) {
                Result.failure(Exception("Network Error: ${e.message}"))
            } catch (e: Exception) {
                Result.failure(Exception("Error fetching task groups: ${e.message}"))
            }
        }
    }
    
    suspend fun updateTaskGroupProgress(taskGroupId: String, progress: Int): Result<TaskGroup> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("TaskGroupRepository", "Updating progress for task group $taskGroupId to $progress%")
                val progressRequest = UpdateProgressRequest(progress)
                val response = apiService.updateTaskGroupProgress(taskGroupId, progressRequest)
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val updatedTaskGroup = response.body()?.taskGroup
                    if (updatedTaskGroup != null) {
                        Log.d("TaskGroupRepository", "Progress update successful. Server returned: ${updatedTaskGroup.progress}%")
                        Result.success(updatedTaskGroup)
                    } else {
                        Log.e("TaskGroupRepository", "Task group progress updated but no task group returned in response")
                        Result.failure(Exception("Task group updated but not returned"))
                    }
                } else {
                    val errorMsg = "Failed to update task group progress: ${response.message()}, code: ${response.code()}"
                    Log.e("TaskGroupRepository", errorMsg)
                    
                    // Try to log the response body for more details
                    try {
                        val errorBody = response.errorBody()?.string()
                        Log.e("TaskGroupRepository", "Error response body: $errorBody")
                    } catch (e: Exception) {
                        Log.e("TaskGroupRepository", "Could not read error body", e)
                    }
                    
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: NetworkClient.NoConnectivityException) {
                Result.failure(Exception("No network connection. Please check your internet connection and try again."))
            } catch (e: NetworkClient.NetworkTimeoutException) {
                Result.failure(Exception("Network timeout. Server is taking too long to respond."))
            } catch (e: NetworkClient.ServerUnreachableException) {
                Result.failure(Exception("Cannot reach server. Please check your network settings."))
            } catch (e: NetworkClient.ConnectionFailureException) {
                Result.failure(Exception("Connection failure. Please try again later."))
            } catch (e: HttpException) {
                Result.failure(Exception("HTTP Error: ${e.code()} - ${e.message()}"))
            } catch (e: IOException) {
                Result.failure(Exception("Network Error: ${e.message}"))
            } catch (e: Exception) {
                Result.failure(Exception("Error updating task group: ${e.message}"))
            }
        }
    }
    
    // Utility method to convert TaskGroup to TaskDataTransfer for UI
    fun convertToTaskDataTransfer(taskGroup: TaskGroup): TaskDataTransfer {
        return TaskDataTransfer(
            id = taskGroup.id,
            areaName = taskGroup.areaName,
            numberOfTasks = taskGroup.tasks.size,
            taskTitles = taskGroup.tasks,
            imageBase64 = taskGroup.imageBase64,
            dateCreated = taskGroup.dateCreated
        )
    }
    
    // Delete a task group by ID
    suspend fun deleteTaskGroup(taskGroupId: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("TaskGroupRepository", "Deleting task group: $taskGroupId")
                val response = apiService.deleteTaskGroup(taskGroupId)
                
                if (response.isSuccessful && response.body()?.success == true) {
                    Log.d("TaskGroupRepository", "Task group deleted successfully")
                    Result.success(true)
                } else {
                    val errorMsg = "Failed to delete task group: ${response.message()}, code: ${response.code()}"
                    Log.e("TaskGroupRepository", errorMsg)
                    
                    // Try to log the response body for more details
                    try {
                        val errorBody = response.errorBody()?.string()
                        Log.e("TaskGroupRepository", "Error response body: $errorBody")
                    } catch (e: Exception) {
                        Log.e("TaskGroupRepository", "Could not read error body", e)
                    }
                    
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e("TaskGroupRepository", "Error deleting task group", e)
                Result.failure(e)
            }
        }
    }
} 