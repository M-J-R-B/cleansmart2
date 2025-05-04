package com.example.cleansmart.utils

import android.content.Context
import android.content.SharedPreferences
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
    }
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREF_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    
    /**
     * Save user email to shared preferences
     */
    fun saveUserEmail(email: String) {
        editor.putString(KEY_USER_EMAIL, email)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }
    
    /**
     * Save user name to shared preferences
     */
    fun saveUserName(name: String) {
        editor.putString(KEY_USER_NAME, name)
        editor.apply()
    }
    
    /**
     * Get user email from shared preferences
     */
    fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }
    
    /**
     * Get user name from shared preferences
     */
    fun getUserName(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }
    
    /**
     * Check if user is logged in
     */
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    
    /**
     * Clear session data (logout)
     */
    fun clearSession() {
        editor.clear()
        editor.apply()
    }
} 