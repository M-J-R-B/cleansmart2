package com.example.cleansmart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.network.ForgotPasswordRequest
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    private val _forgotPasswordState = MutableLiveData<ForgotPasswordState>()
    val forgotPasswordState: LiveData<ForgotPasswordState> = _forgotPasswordState

    private val apiService = ApiService.create()

    fun requestPasswordReset(email: String) {
        viewModelScope.launch {
            _forgotPasswordState.value = ForgotPasswordState.Loading
            try {
                val response = apiService.forgotPassword(ForgotPasswordRequest(email))
                if (response.isSuccessful && response.body()?.success == true) {
                    _forgotPasswordState.value = ForgotPasswordState.Success(
                        response.body()?.message ?: "Password reset instructions sent to your email"
                    )
                } else {
                    _forgotPasswordState.value = ForgotPasswordState.Error(
                        response.body()?.message ?: "Failed to send reset instructions"
                    )
                }
            } catch (e: Exception) {
                _forgotPasswordState.value = ForgotPasswordState.Error(
                    "Network error: ${e.message}"
                )
            }
        }
    }
}

sealed class ForgotPasswordState {
    object Loading : ForgotPasswordState()
    data class Success(val message: String) : ForgotPasswordState()
    data class Error(val message: String) : ForgotPasswordState()
}