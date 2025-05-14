package com.example.cleansmart.viewmodels

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleansmart.models.Task
import com.example.cleansmart.models.TaskGenerationRequest
import com.example.cleansmart.network.ApiService
import com.example.cleansmart.repository.TaskRepository
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class TaskGenerationViewModel : ViewModel() {
    private val apiService = ApiService.create()
    private val taskRepository = TaskRepository(apiService)

    private val _generatedTasks = MutableLiveData<List<Task>>()
    val generatedTasks: LiveData<List<Task>> = _generatedTasks

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _capturedImage = MutableLiveData<Bitmap?>()
    val capturedImage: LiveData<Bitmap?> = _capturedImage

    private val _areaName = MutableLiveData<String>()
    val areaName: LiveData<String> = _areaName

    fun setCapturedImage(bitmap: Bitmap?) {
        _capturedImage.value = bitmap
    }

    fun setAreaName(area: String) {
        _areaName.value = area
    }

    fun generateTasksFromApi() {
        _isLoading.value = true
        _error.value = null

        // Create request with image if available
        val request = TaskGenerationRequest(
            area = _areaName.value ?: "Unknown",
            imageBase64 = _capturedImage.value?.let { bitmapToBase64(it) }
        )

        viewModelScope.launch {
            try {
                val result = taskRepository.generateTasks(request)
                
                if (result.isSuccess && result.getOrNull()?.tasks != null) {
                    _generatedTasks.postValue(result.getOrNull()?.tasks)
                } else {
                    // For demo purposes, fall back to predefined tasks if API fails
                    generatePredefinedTasks()
                }
            } catch (e: Exception) {
                // For demo purposes, fall back to predefined tasks
                generatePredefinedTasks()
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun generatePredefinedTasks() {
        _isLoading.value = true
        _error.value = null
        
        val area = _areaName.value ?: "Unknown"
        val predefinedTasks = taskRepository.generatePredefinedTasks(area)
        _generatedTasks.value = predefinedTasks
        _isLoading.value = false
    }

    fun clearData() {
        _capturedImage.value = null
        _areaName.value = ""
        _generatedTasks.value = emptyList()
        _error.value = null
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
} 