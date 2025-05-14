package com.example.cleansmart.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import com.example.cleansmart.models.Task
import com.example.cleansmart.models.TaskGenerationRequest
import com.example.cleansmart.models.TaskGenerationResponse
import com.example.cleansmart.models.TaskGroup
import com.example.cleansmart.models.SaveTaskGroupRequest
import com.example.cleansmart.models.SaveTaskGroupResponse
import com.example.cleansmart.models.GetTaskGroupsResponse

interface ApiService {
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

    @Headers("Content-Type: application/json")
    @POST("auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ChangePasswordResponse>

    // Task related endpoints
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>

    @POST("tasks")
    suspend fun createTask(@Body task: Task): Response<Task>

    @PUT("tasks/{taskId}")
    suspend fun updateTask(@Path("taskId") taskId: String, @Body task: Task): Response<Task>

    @POST("tasks/generate")
    suspend fun generateTasks(@Body request: TaskGenerationRequest): Response<TaskGenerationResponse>
    
    // Task group endpoints for persistence
    @POST("taskGroups")
    suspend fun saveTaskGroup(@Body request: SaveTaskGroupRequest): Response<SaveTaskGroupResponse>
    
    @GET("taskGroups")
    suspend fun getTaskGroups(): Response<GetTaskGroupsResponse>
    
    @GET("taskGroups/{userId}")
    suspend fun getUserTaskGroups(@Path("userId") userId: String): Response<GetTaskGroupsResponse>
    
    @PUT("taskGroups/{taskGroupId}")
    suspend fun updateTaskGroupProgress(
        @Path("taskGroupId") taskGroupId: String, 
        @Body progress: Map<String, Int>
    ): Response<SaveTaskGroupResponse>

    companion object {
        fun create(): ApiService {
            return NetworkClient.apiService
        }
    }
}

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