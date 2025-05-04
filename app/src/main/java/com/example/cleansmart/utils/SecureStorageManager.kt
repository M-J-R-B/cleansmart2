package com.example.cleansmart.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecureStorageManager private constructor(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val securePreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        SECURE_PREFS_FILENAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

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
        securePreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(): String? {
        return securePreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun saveUserId(userId: String) {
        securePreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return securePreferences.getString(KEY_USER_ID, null)
    }

    fun saveEmail(email: String) {
        securePreferences.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(): String? {
        return securePreferences.getString(KEY_EMAIL, null)
    }

    fun setRememberMe(enabled: Boolean) {
        securePreferences.edit().putBoolean(KEY_REMEMBER_ME, enabled).apply()
    }

    fun isRememberMeEnabled(): Boolean {
        return securePreferences.getBoolean(KEY_REMEMBER_ME, false)
    }

    fun setBiometricEnabled(enabled: Boolean) {
        securePreferences.edit().putBoolean(KEY_BIOMETRIC_ENABLED, enabled).apply()
    }

    fun isBiometricEnabled(): Boolean {
        return securePreferences.getBoolean(KEY_BIOMETRIC_ENABLED, false)
    }

    fun clearAll() {
        securePreferences.edit().clear().apply()
    }
} 