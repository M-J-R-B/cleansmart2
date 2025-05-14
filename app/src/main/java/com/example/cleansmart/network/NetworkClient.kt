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
    private const val BASE_URL = "http://192.168.1.9:5000/api/" // Using original IP as requested
    private const val TIMEOUT_SECONDS = 30L
    private const val TAG = "NetworkClient"
    private val gson = Gson()
    private var secureStorageManager: SecureStorageManager? = null
    private var appContext: Context? = null

    // Initialize with application context to get secure storage
    fun initialize(context: Context) {
        appContext = context.applicationContext
        secureStorageManager = SecureStorageManager.getInstance(context)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val networkInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        
        // Check for network connectivity first
        if (!isNetworkAvailable() && appContext != null) {
            throw NoConnectivityException("No network connection available")
        }
        
        // Log the full request details
        Log.d(TAG, """
            --> ${originalRequest.method} ${originalRequest.url}
            Headers: ${originalRequest.headers}
            Body: ${originalRequest.body?.let { 
                try {
                    val buffer = okio.Buffer()
                    it.writeTo(buffer)
                    buffer.readUtf8()
                } catch (e: Exception) {
                    "Could not read body: ${e.message}"
                }
            }}
        """.trimIndent())

        // Add content-type header if not present
        val request = originalRequest.newBuilder()
            .apply {
                if (originalRequest.header("Content-Type") == null) {
                    addHeader("Content-Type", "application/json")
                }
            }
            .build()

        try {
            chain.proceed(request).also { response ->
                // Log the response details
                Log.d(TAG, """
                    <-- ${response.code} ${response.message}
                    URL: ${response.request.url}
                    Headers: ${response.headers}
                    Body: ${response.peekBody(Long.MAX_VALUE).string()}
                """.trimIndent())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network error during request to ${request.url}", e)
            // Log more details about the error
            Log.e(TAG, "Error type: ${e.javaClass.simpleName}")
            Log.e(TAG, "Error message: ${e.message}")
            Log.e(TAG, "Stack trace: ${e.stackTraceToString()}")
            
            // Rethrow with more specific exception types
            when (e) {
                is SocketTimeoutException -> throw NetworkTimeoutException("Connection timed out: ${e.message}")
                is UnknownHostException -> throw ServerUnreachableException("Server cannot be reached: ${e.message}")
                is ConnectException -> throw ConnectionFailureException("Failed to connect: ${e.message}")
                else -> throw e
            }
        }
    }
    
    // Add auth interceptor to add token to all requests
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        
        // Get auth token from secure storage
        val token = secureStorageManager?.getAuthToken()
        
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

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)  // Add auth interceptor first
        .addInterceptor(networkInterceptor)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create()) // Add Scalars first
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    
    // Custom exceptions for better error handling
    class NoConnectivityException(message: String) : IOException(message)
    class NetworkTimeoutException(message: String) : IOException(message)
    class ServerUnreachableException(message: String) : IOException(message)
    class ConnectionFailureException(message: String) : IOException(message)
    
    // Check if network is available
    private fun isNetworkAvailable(): Boolean {
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
    }
} 