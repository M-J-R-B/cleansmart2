package com.example.cleansmart.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * SessionManager handles user session data storage and retrieval
 * Uses EncryptedSharedPreferences for secure storage of sensitive information
 */
class SessionManager(context: Context) {
    
    companion object {
        private const val PREF_NAME = "CleanSmartSession"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val TAG = "SessionManager"
    }
    
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    
    init {
        try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            
            sharedPreferences = try {
                EncryptedSharedPreferences.create(
                    context,
                    PREF_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            } catch (e: Exception) {
                // Fallback to regular preferences if encrypted ones fail
                Log.e(TAG, "Failed to create encrypted preferences, falling back to regular: ${e.message}", e)
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            }
            
            editor = sharedPreferences.edit()
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing SessionManager: ${e.message}", e)
            // Use regular shared preferences as fallback
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            editor = sharedPreferences.edit()
        }
    }
    
    /**
     * Save user email to shared preferences
     */
    fun saveUserEmail(email: String) {
        try {
            editor.putString(KEY_USER_EMAIL, email)
            editor.putBoolean(KEY_IS_LOGGED_IN, true)
            editor.apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving user email: ${e.message}", e)
        }
    }
    
    /**
     * Save user name to shared preferences
     */
    fun saveUserName(name: String) {
        try {
            editor.putString(KEY_USER_NAME, name)
            editor.apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error saving user name: ${e.message}", e)
        }
    }
    
    /**
     * Get user email from shared preferences
     */
    fun getUserEmail(): String? {
        return try {
            sharedPreferences.getString(KEY_USER_EMAIL, null)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user email: ${e.message}", e)
            null
        }
    }
    
    /**
     * Get user name from shared preferences
     */
    fun getUserName(): String? {
        return try {
            sharedPreferences.getString(KEY_USER_NAME, null)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user name: ${e.message}", e)
            null
        }
    }
    
    /**
     * Check if user is logged in
     */
    fun isLoggedIn(): Boolean {
        return try {
            sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        } catch (e: Exception) {
            Log.e(TAG, "Error checking login status: ${e.message}", e)
            false
        }
    }
    
    /**
     * Clear session data (logout)
     */
    fun clearSession() {
        try {
            editor.clear()
            editor.apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing session: ${e.message}", e)
        }
    }
} 