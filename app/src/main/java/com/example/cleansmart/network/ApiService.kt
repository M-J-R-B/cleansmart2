package com.example.cleansmart.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers

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