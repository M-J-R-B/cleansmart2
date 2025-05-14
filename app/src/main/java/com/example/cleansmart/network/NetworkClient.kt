package com.example.cleansmart.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.cleansmart.utils.SecureStorageManager
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

object NetworkClient {
    // Use the BASE_URL from ApiService instead of hardcoding a different one
    private var baseUrl = ApiService.BASE_URL
    private const val TIMEOUT_SECONDS = 30L
    private const val TAG = "NetworkClient"
    private val gson = Gson()
    private var secureStorageManager: SecureStorageManager? = null
    private var appContext: Context? = null
    private var retrofit: Retrofit? = null
    private var isInitialized = false

    // Initialize with application context to get secure storage
    fun initialize(context: Context) {
        try {
            if (isInitialized) return
            
            appContext = context.applicationContext
            secureStorageManager = SecureStorageManager.getInstance(context)
            
            // Update the base URL based on user preferences
            baseUrl = ApiService.getBaseUrl(context)
            
            // Initialize Retrofit with the selected URL
            initializeRetrofit()
            
            isInitialized = true
            Log.d(TAG, "NetworkClient successfully initialized")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize NetworkClient: ${e.message}", e)
            // Continue even if there's an error - we'll handle it later when making requests
        }
    }
    
    private fun initializeRetrofit() {
        try {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create()) 
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing Retrofit: ${e.message}", e)
            throw e
        }
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val networkInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        
        // Check for network connectivity first
        if (!isNetworkAvailable() && appContext != null) {
            Log.e(TAG, "Network unavailable when attempting to make a request to ${originalRequest.url}")
            throw NoConnectivityException("No network connection available. Please check your internet connection and try again.")
        }
        
        // Simplified logging to avoid security issues on MIUI
        Log.d(TAG, "--> REQUEST ${originalRequest.method} ${originalRequest.url}")

        // Add content-type header if not present
        val request = originalRequest.newBuilder()
            .apply {
                if (originalRequest.header("Content-Type") == null) {
                    addHeader("Content-Type", "application/json")
                }
            }
            .build()

        try {
            val response = chain.proceed(request)
            
            // Simplified logging for response
            Log.d(TAG, "<-- RESPONSE ${response.code} for ${response.request.url}")
            
            return@Interceptor response
        } catch (e: Exception) {
            Log.e(TAG, "Network error during request to ${request.url}: ${e.message}")
            
            // Try to provide more helpful error messages
            val errorMessage = when (e) {
                is SocketTimeoutException -> "Connection timed out. The server might be slow or unreachable at the moment."
                is UnknownHostException -> "Server cannot be reached. Please check if the server address is correct: ${request.url.host}"
                is ConnectException -> "Failed to connect to the server. Is your server running at ${request.url.host}:${request.url.port}?"
                else -> "Network error: ${e.message}"
            }
            
            // Rethrow with more specific exception types
            when (e) {
                is SocketTimeoutException -> throw NetworkTimeoutException(errorMessage)
                is UnknownHostException -> throw ServerUnreachableException(errorMessage)
                is ConnectException -> throw ConnectionFailureException(errorMessage)
                else -> throw e
            }
        }
    }
    
    // Add auth interceptor to add token to all requests
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        
        // Get auth token from secure storage
        val token = try {
            secureStorageManager?.getAuthToken()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting auth token: ${e.message}")
            null
        }
        
        // If token exists, add it to the request
        val newRequest = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }
        
        chain.proceed(newRequest)
    }

    private val okHttpClient by lazy {
        try {
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)  // Add auth interceptor first
                .addInterceptor(networkInterceptor)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        } catch (e: Exception) {
            Log.e(TAG, "Error creating OkHttpClient: ${e.message}", e)
            // Fallback to a simpler client if we encounter issues
            OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()
        }
    }

    val apiService: ApiService by lazy {
        try {
            if (retrofit == null) {
                initializeRetrofit()
            }
            retrofit!!.create(ApiService::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating ApiService: ${e.message}", e)
            throw RuntimeException("Failed to initialize API service. Please restart the app.", e)
        }
    }
    
    // Custom exceptions for better error handling
    class NoConnectivityException(message: String) : IOException(message)
    class NetworkTimeoutException(message: String) : IOException(message)
    class ServerUnreachableException(message: String) : IOException(message)
    class ConnectionFailureException(message: String) : IOException(message)
    
    // Check if network is available
    private fun isNetworkAvailable(): Boolean {
        try {
            val context = appContext ?: return false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
                return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else {
                @Suppress("DEPRECATION")
                val networkInfo = connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking network availability: ${e.message}", e)
            // Default to true if we can't check, to avoid blocking network requests
            return true
        }
    }
} 