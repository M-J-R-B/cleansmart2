package com.example.cleansmart.repository

import com.example.cleansmart.models.Task
import com.example.cleansmart.models.TaskGenerationRequest
import com.example.cleansmart.models.TaskGenerationResponse
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
                val response = apiService.generateTasks(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
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

    // For demonstration purposes, generate predefined tasks based on area
    fun generatePredefinedTasks(area: String): List<Task> {
        val tasksByArea = mapOf(
            "kitchen" to listOf(
                Task("k1", "Clean the countertops", "Wipe down all kitchen countertops with disinfectant", "Kitchen", com.example.cleansmart.models.Priority.HIGH),
                Task("k2", "Wash the dishes", "Wash all dishes in the sink", "Kitchen", com.example.cleansmart.models.Priority.HIGH),
                Task("k3", "Sweep the floor", "Sweep the kitchen floor", "Kitchen", com.example.cleansmart.models.Priority.MEDIUM),
                Task("k4", "Mop the floor", "Mop the kitchen floor with cleaner", "Kitchen", com.example.cleansmart.models.Priority.MEDIUM),
                Task("k5", "Clean the refrigerator", "Remove old food and wipe down shelves", "Kitchen", com.example.cleansmart.models.Priority.LOW)
            ),
            "bathroom" to listOf(
                Task("b1", "Clean the toilet", "Scrub the toilet with toilet cleaner", "Bathroom", com.example.cleansmart.models.Priority.HIGH),
                Task("b2", "Clean the shower", "Scrub the shower with bathroom cleaner", "Bathroom", com.example.cleansmart.models.Priority.HIGH),
                Task("b3", "Clean the sink", "Wipe down the sink with disinfectant", "Bathroom", com.example.cleansmart.models.Priority.MEDIUM),
                Task("b4", "Mop the floor", "Mop the bathroom floor", "Bathroom", com.example.cleansmart.models.Priority.MEDIUM),
                Task("b5", "Replace towels", "Put out fresh towels", "Bathroom", com.example.cleansmart.models.Priority.LOW)
            ),
            "living room" to listOf(
                Task("l1", "Vacuum the carpet", "Vacuum the living room carpet", "Living Room", com.example.cleansmart.models.Priority.HIGH),
                Task("l2", "Dust the furniture", "Dust all surfaces in the living room", "Living Room", com.example.cleansmart.models.Priority.MEDIUM),
                Task("l3", "Organize magazines", "Stack magazines neatly", "Living Room", com.example.cleansmart.models.Priority.LOW),
                Task("l4", "Clean windows", "Clean the living room windows", "Living Room", com.example.cleansmart.models.Priority.LOW),
                Task("l5", "Fluff pillows", "Fluff and arrange the couch pillows", "Living Room", com.example.cleansmart.models.Priority.LOW)
            ),
            "bedroom" to listOf(
                Task("br1", "Make the bed", "Make the bed with fresh sheets", "Bedroom", com.example.cleansmart.models.Priority.HIGH),
                Task("br2", "Put away clothes", "Fold and put away clean clothes", "Bedroom", com.example.cleansmart.models.Priority.MEDIUM),
                Task("br3", "Vacuum the floor", "Vacuum the bedroom floor", "Bedroom", com.example.cleansmart.models.Priority.MEDIUM),
                Task("br4", "Dust surfaces", "Dust all bedroom surfaces", "Bedroom", com.example.cleansmart.models.Priority.LOW),
                Task("br5", "Organize nightstand", "Organize items on the nightstand", "Bedroom", com.example.cleansmart.models.Priority.LOW)
            ),
            "default" to listOf(
                Task("d1", "Clean the area", "General cleaning task", "General", com.example.cleansmart.models.Priority.MEDIUM),
                Task("d2", "Organize items", "Put things in their proper place", "General", com.example.cleansmart.models.Priority.MEDIUM),
                Task("d3", "Dust surfaces", "Dust all surfaces in the area", "General", com.example.cleansmart.models.Priority.LOW)
            )
        )

        val normalizedArea = area.lowercase().trim()
        return tasksByArea[normalizedArea] ?: tasksByArea["default"]!!
    }
} 