package com.example.cleansmart.repository

import com.example.cleansmart.network.Task
import com.example.cleansmart.network.GenerateTasksRequest as NetworkGenerateTasksRequest
import com.example.cleansmart.network.GenerateTasksResponse as NetworkGenerateTasksResponse
import com.example.cleansmart.models.TaskGenerationRequest
import com.example.cleansmart.models.TaskGenerationResponse
import com.example.cleansmart.models.Task as ModelTask
import com.example.cleansmart.models.Priority
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

class TaskRepository(private val apiService: ApiService) {

    suspend fun getTasks(): Result<List<Task>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTasks()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to fetch tasks: ${response.message()}"))
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
                Result.failure(Exception("Error fetching tasks: ${e.message}"))
            }
        }
    }

    suspend fun createTask(task: Task): Result<Task> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.createTask(task)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to create task: ${response.message()}"))
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
                Result.failure(Exception("Error creating task: ${e.message}"))
            }
        }
    }

    suspend fun updateTask(taskId: String, task: Task): Result<Task> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.updateTask(taskId, task)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to update task: ${response.message()}"))
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
                Result.failure(Exception("Error updating task: ${e.message}"))
            }
        }
    }

    suspend fun generateTasks(request: TaskGenerationRequest): Result<TaskGenerationResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // Convert to network request
                val networkRequest = NetworkGenerateTasksRequest(
                    area = request.area,
                    imageBase64 = request.imageBase64
                )
                
                val response = apiService.generateTasks(networkRequest)
                if (response.isSuccessful && response.body() != null) {
                    // Convert to model response
                    val networkResponse = response.body()!!
                    val modelTasks = networkResponse.tasks?.map { networkTask ->
                        convertNetworkTaskToModelTask(networkTask)
                    }
                    
                    val modelResponse = TaskGenerationResponse(
                        success = networkResponse.success,
                        message = networkResponse.message,
                        tasks = modelTasks
                    )
                    
                    Result.success(modelResponse)
                } else {
                    Result.failure(Exception("Failed to generate tasks: ${response.message()}"))
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
                Result.failure(Exception("Error generating tasks: ${e.message}"))
            }
        }
    }
    
    private fun convertNetworkTaskToModelTask(networkTask: Task): ModelTask {
        val priority = when(networkTask.priority.uppercase()) {
            "HIGH" -> Priority.HIGH
            "LOW" -> Priority.LOW
            else -> Priority.MEDIUM
        }
        
        return ModelTask(
            id = networkTask.id ?: "",
            title = networkTask.title,
            description = networkTask.description ?: "",
            area = networkTask.area,
            priority = priority,
            isCompleted = networkTask.isCompleted,
            createdAt = networkTask.createdAt
        )
    }

    // For demonstration purposes, generate predefined tasks based on area
    fun generatePredefinedTasks(area: String): List<ModelTask> {
        val tasksByArea = mapOf(
            "kitchen" to listOf(
                ModelTask("k1", "Clean the countertops", "Wipe down all kitchen countertops with disinfectant", "Kitchen", Priority.HIGH),
                ModelTask("k2", "Wash the dishes", "Wash all dishes in the sink", "Kitchen", Priority.HIGH),
                ModelTask("k3", "Sweep the floor", "Sweep the kitchen floor", "Kitchen", Priority.MEDIUM),
                ModelTask("k4", "Mop the floor", "Mop the kitchen floor with cleaner", "Kitchen", Priority.MEDIUM),
                ModelTask("k5", "Clean the refrigerator", "Remove old food and wipe down shelves", "Kitchen", Priority.LOW)
            ),
            "bathroom" to listOf(
                ModelTask("b1", "Clean the toilet", "Scrub the toilet with toilet cleaner", "Bathroom", Priority.HIGH),
                ModelTask("b2", "Clean the shower", "Scrub the shower with bathroom cleaner", "Bathroom", Priority.HIGH),
                ModelTask("b3", "Clean the sink", "Wipe down the sink with disinfectant", "Bathroom", Priority.MEDIUM),
                ModelTask("b4", "Mop the floor", "Mop the bathroom floor", "Bathroom", Priority.MEDIUM),
                ModelTask("b5", "Replace towels", "Put out fresh towels", "Bathroom", Priority.LOW)
            ),
            "living room" to listOf(
                ModelTask("l1", "Vacuum the carpet", "Vacuum the living room carpet", "Living Room", Priority.HIGH),
                ModelTask("l2", "Dust the furniture", "Dust all surfaces in the living room", "Living Room", Priority.MEDIUM),
                ModelTask("l3", "Organize magazines", "Stack magazines neatly", "Living Room", Priority.LOW),
                ModelTask("l4", "Clean windows", "Clean the living room windows", "Living Room", Priority.LOW),
                ModelTask("l5", "Fluff pillows", "Fluff and arrange the couch pillows", "Living Room", Priority.LOW)
            ),
            "bedroom" to listOf(
                ModelTask("br1", "Make the bed", "Make the bed with fresh sheets", "Bedroom", Priority.HIGH),
                ModelTask("br2", "Put away clothes", "Fold and put away clean clothes", "Bedroom", Priority.MEDIUM),
                ModelTask("br3", "Vacuum the floor", "Vacuum the bedroom floor", "Bedroom", Priority.MEDIUM),
                ModelTask("br4", "Dust surfaces", "Dust all bedroom surfaces", "Bedroom", Priority.LOW),
                ModelTask("br5", "Organize nightstand", "Organize items on the nightstand", "Bedroom", Priority.LOW)
            ),
            "default" to listOf(
                ModelTask("d1", "Clean the area", "General cleaning task", "General", Priority.MEDIUM),
                ModelTask("d2", "Organize items", "Put things in their proper place", "General", Priority.MEDIUM),
                ModelTask("d3", "Dust surfaces", "Dust all surfaces in the area", "General", Priority.LOW)
            )
        )

        val normalizedArea = area.lowercase().trim()
        return tasksByArea[normalizedArea] ?: tasksByArea["default"]!!
    }
} 