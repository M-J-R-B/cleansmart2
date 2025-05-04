package com.example.cleansmart.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import com.google.gson.Gson

object NetworkClient {
    private const val BASE_URL = "http://10.0.2.2:5000/api/" // Added /api/ to match backend URL structure
    private const val TIMEOUT_SECONDS = 30L
    private const val TAG = "NetworkClient"
    private val gson = Gson()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val networkInterceptor = okhttp3.Interceptor { chain ->
        val originalRequest = chain.request()
        
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
            throw e
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(networkInterceptor)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
} 