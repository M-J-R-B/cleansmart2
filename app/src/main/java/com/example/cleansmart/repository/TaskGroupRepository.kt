package com.example.cleansmart.repository

import com.example.cleansmart.models.TaskGroup
import com.example.cleansmart.models.SaveTaskGroupRequest
import com.example.cleansmart.models.TaskDataTransfer
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import retrofit2.HttpException

class TaskGroupRepository(private val apiService: ApiService) {

    suspend fun saveTaskGroup(userId: String, taskData: TaskDataTransfer): Result<TaskGroup> {
        return withContext(Dispatchers.IO) {
            try {
                // Convert TaskDataTransfer to TaskGroup
                val taskGroup = TaskGroup(
                    userId = userId,
                    areaName = taskData.areaName,
                    imageBase64 = taskData.imageBase64,
                    tasks = taskData.taskTitles,
                    progress = 0 // New task groups start with 0 progress
                )
                
                val request = SaveTaskGroupRequest(taskGroup)
                val response = apiService.saveTaskGroup(request)
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val savedTaskGroup = response.body()?.taskGroup
                    if (savedTaskGroup != null) {
                        Result.success(savedTaskGroup)
                    } else {
                        Result.failure(Exception("Task group saved but not returned"))
                    }
                } else {
                    Result.failure(Exception("Failed to save task group: ${response.message()}"))
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
                val response = apiService.getUserTaskGroups(userId)
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val taskGroups = response.body()?.taskGroups ?: emptyList()
                    Result.success(taskGroups)
                } else {
                    Result.failure(Exception("Failed to load task groups: ${response.message()}"))
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
                val progressMap = mapOf("progress" to progress)
                val response = apiService.updateTaskGroupProgress(taskGroupId, progressMap)
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val updatedTaskGroup = response.body()?.taskGroup
                    if (updatedTaskGroup != null) {
                        Result.success(updatedTaskGroup)
                    } else {
                        Result.failure(Exception("Task group updated but not returned"))
                    }
                } else {
                    Result.failure(Exception("Failed to update task group progress: ${response.message()}"))
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
} 