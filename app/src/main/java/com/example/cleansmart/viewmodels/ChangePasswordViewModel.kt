package com.example.cleansmart.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.network.ChangePasswordRequest
import com.example.cleansmart.utils.SecureStorageManager
import kotlinx.coroutines.launch

sealed class ChangePasswordState {
    object Loading : ChangePasswordState()
    data class Success(val message: String) : ChangePasswordState()
    data class Error(val message: String) : ChangePasswordState()
}

class ChangePasswordViewModel : ViewModel() {
    private val apiService = ApiService.create()
    private val _changePasswordState = MutableLiveData<ChangePasswordState>()
    val changePasswordState: LiveData<ChangePasswordState> = _changePasswordState

    fun changePassword(context: Context, oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            _changePasswordState.value = ChangePasswordState.Loading
            try {
                val secureStorage = SecureStorageManager.getInstance(context)
                val email = secureStorage.getEmail()
                
                if (email == null) {
                    _changePasswordState.value = ChangePasswordState.Error("User email not found. Please log in again.")
                    return@launch
                }

                val response = apiService.changePassword(
                    ChangePasswordRequest(email, oldPassword, newPassword)
                )
                if (response.isSuccessful && response.body()?.success == true) {
                    _changePasswordState.value = ChangePasswordState.Success(
                        response.body()?.message ?: "Password changed successfully"
                    )
                } else {
                    _changePasswordState.value = ChangePasswordState.Error(
                        response.body()?.message ?: "Failed to change password"
                    )
                }
            } catch (e: Exception) {
                _changePasswordState.value = ChangePasswordState.Error(
                    e.message ?: "An error occurred while changing password"
                )
            }
        }
    }
} 