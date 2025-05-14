package com.example.cleansmart.network

import com.example.cleansmart.models.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API interface for CleanSmart app
 * This interface defines all network requests for the application
 */
interface ApiService {
    companion object {
        // DEV: For development against your local server (adjust to your computer's actual IP)
        private const val DEV_URL = "http://192.168.1.9:5000/api/"
        
        // EMULATOR: For Android emulator pointing to localhost on host machine
        private const val EMULATOR_URL = "http://10.0.2.2:5000/api/"
        
        // PROD: For production environment (update this when you deploy your backend)
        private const val PROD_URL = "https://your-production-server.com/api/"
        
        // Get the appropriate URL based on settings
        fun getBaseUrl(context: android.content.Context?): String {
            if (context == null) return DEV_URL
            
            val prefs = context.getSharedPreferences("app_settings", android.content.Context.MODE_PRIVATE)
            return when (prefs.getInt("api_endpoint", 0)) {
                0 -> DEV_URL
                1 -> EMULATOR_URL
                2 -> PROD_URL
                else -> DEV_URL
            }
        }
        
        // Default URL for when context is not available
        const val BASE_URL = DEV_URL
        
        fun create(): ApiService {
            return NetworkClient.apiService
        }
    }

    // Auth endpoints
    @Headers("Content-Type: application/json")
    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/logout")
    suspend fun logout(): Response<LogoutResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

//    @Headers("Content-Type: application/json")
//    @POST("auth/reset-password")
//    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ChangePasswordResponse>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "auth/delete-account", hasBody = true)
    suspend fun deleteAccount(@Body request: DeleteAccountRequest): Response<DeleteAccountResponse>

    // TaskGroup endpoints

    @Headers("Content-Type: application/json")
    @GET("taskGroups/{userId}")
    suspend fun getUserTaskGroups(@Path("userId") userId: String): Response<GetTaskGroupsResponse>

    @Headers("Content-Type: application/json")
    @GET("taskGroups")
    suspend fun getTaskGroups(): Response<GetTaskGroupsResponse>

    @Headers("Content-Type: application/json")
    @POST("taskGroups")
    suspend fun saveTaskGroup(@Body request: SaveTaskGroupRequest): Response<SaveTaskGroupResponse>

    @Headers("Content-Type: application/json")
    @PUT("taskGroups/{taskGroupId}")
    suspend fun updateTaskGroupProgress(
        @Path("taskGroupId") taskGroupId: String,
        @Body updateRequest: UpdateProgressRequest
    ): Response<SaveTaskGroupResponse>

    @Headers("Content-Type: application/json")
    @DELETE("taskGroups/{taskGroupId}")
    suspend fun deleteTaskGroup(@Path("taskGroupId") taskGroupId: String): Response<DeleteResponse>

    // Task endpoints
    @Headers("Content-Type: application/json")
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>

    @Headers("Content-Type: application/json")
    @POST("tasks")
    suspend fun createTask(@Body task: Task): Response<Task>

    @Headers("Content-Type: application/json")
    @PUT("tasks/{taskId}")
    suspend fun updateTask(
        @Path("taskId") taskId: String,
        @Body task: Task
    ): Response<Task>

    @Headers("Content-Type: application/json")
    @DELETE("tasks/{taskId}")
    suspend fun deleteTask(@Path("taskId") taskId: String): Response<DeleteResponse>

    @Headers("Content-Type: application/json")
    @POST("tasks/generate")
    suspend fun generateTasks(@Body request: GenerateTasksRequest): Response<GenerateTasksResponse>

    // Test endpoints - Use these for testing without authentication
    @Headers("Content-Type: application/json")
    @POST("taskGroups/test")
    suspend fun testSaveTaskGroup(@Body request: SaveTaskGroupRequest): Response<SaveTaskGroupResponse>

    @FormUrlEncoded
    @POST("taskGroups/simple-test")
    suspend fun simpleTestSaveTaskGroup(
        @Field("areaName") areaName: String,
        @Field("tasks") tasks: List<String>?
    ): Response<SaveTaskGroupResponse>
}

// Additional model classes needed for API interface


data class LoginResponse(
    val success: Boolean,
    val token: String?,
    val user: User?,
    val message: String?
)



data class RegisterResponse(
    val success: Boolean,
    val token: String?,
    val user: User?,
    val message: String?
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val isAdmin: Boolean = false
)

data class UpdateProgressRequest(
    val progress: Int
)

data class DeleteResponse(
    val success: Boolean,
    val message: String?
)

data class Task(
    val id: String? = null,
    val title: String,
    val description: String? = null,
    val area: String,
    val priority: String = "MEDIUM",
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

data class GenerateTasksRequest(
    val area: String,
    val imageBase64: String? = null
)

data class GenerateTasksResponse(
    val success: Boolean,
    val message: String?,
    val tasks: List<Task>?
)

data class SignupRequest(
    val fullName: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val success: Boolean,
    val message: String? = null,
    val user: UserData? = null
)

data class UserData(
    val id: String,
    val fullName: String,
    val email: String
)

data class LogoutResponse(
    val success: Boolean,
    val message: String
)

data class ForgotPasswordRequest(
    val email: String
)

data class ForgotPasswordResponse(
    val success: Boolean,
    val message: String
)

data class ChangePasswordRequest(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)

data class ChangePasswordResponse(
    val success: Boolean,
    val message: String
)

data class DeleteAccountResponse(
    val success: Boolean,
    val message: String
)

data class DeleteAccountRequest(
    val email: String,
    val password: String,
    val confirmation: String
)
