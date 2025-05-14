package com.example.cleansmart.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecureStorageManager private constructor(context: Context) {
    private val TAG = "SecureStorageManager"
    private lateinit var securePreferences: SharedPreferences
    
    init {
        try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            securePreferences = try {
                EncryptedSharedPreferences.create(
                    context,
                    SECURE_PREFS_FILENAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            } catch (e: Exception) {
                // Fallback to regular preferences if encrypted ones fail
                Log.e(TAG, "Failed to initialize encrypted preferences: ${e.message}", e)
                context.getSharedPreferences(SECURE_PREFS_FILENAME, Context.MODE_PRIVATE)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing SecureStorageManager: ${e.message}", e)
            // Use regular shared preferences as fallback
            securePreferences = context.getSharedPreferences(SECURE_PREFS_FILENAME, Context.MODE_PRIVATE)
        }
    }

    companion object {
        private const val SECURE_PREFS_FILENAME = "secure_user_prefs"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_REMEMBER_ME = "remember_me"
        private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
        
        @Volatile
        private var instance: SecureStorageManager? = null

        fun getInstance(context: Context): SecureStorageManager {
            return instance ?: synchronized(this) {
                instance ?: SecureStorageManager(context.applicationContext).also { instance = it }
            }
        }
    }

    fun saveAuthToken(token: String) {
        try {
            securePreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving auth token: ${e.message}", e)
        }
    }

    fun getAuthToken(): String? {
        return try {
            securePreferences.getString(KEY_AUTH_TOKEN, null)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting auth token: ${e.message}", e)
            null
        }
    }

    fun saveUserId(userId: String) {
        try {
            securePreferences.edit().putString(KEY_USER_ID, userId).apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving user ID: ${e.message}", e)
        }
    }

    fun getUserId(): String? {
        return try {
            securePreferences.getString(KEY_USER_ID, null)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user ID: ${e.message}", e)
            null
        }
    }

    fun saveEmail(email: String) {
        try {
            securePreferences.edit().putString(KEY_EMAIL, email).apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving email: ${e.message}", e)
        }
    }

    fun getEmail(): String? {
        return try {
            securePreferences.getString(KEY_EMAIL, null)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting email: ${e.message}", e)
            null
        }
    }

    fun setRememberMe(enabled: Boolean) {
        try {
            securePreferences.edit().putBoolean(KEY_REMEMBER_ME, enabled).apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error setting remember me: ${e.message}", e)
        }
    }

    fun isRememberMeEnabled(): Boolean {
        return try {
            securePreferences.getBoolean(KEY_REMEMBER_ME, false)
        } catch (e: Exception) {
            Log.e(TAG, "Error checking remember me: ${e.message}", e)
            false
        }
    }

    fun setBiometricEnabled(enabled: Boolean) {
        try {
            securePreferences.edit().putBoolean(KEY_BIOMETRIC_ENABLED, enabled).apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error setting biometric: ${e.message}", e)
        }
    }

    fun isBiometricEnabled(): Boolean {
        return try {
            securePreferences.getBoolean(KEY_BIOMETRIC_ENABLED, false)
        } catch (e: Exception) {
            Log.e(TAG, "Error checking biometric: ${e.message}", e)
            false
        }
    }

    fun clearAll() {
        try {
            securePreferences.edit().clear().apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing secure storage: ${e.message}", e)
        }
    }
} 