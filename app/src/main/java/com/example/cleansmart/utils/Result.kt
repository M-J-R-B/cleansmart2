package com.example.cleansmart.utils

/**
 * A generic class that holds a value or an error message.
 * @param <T> Type of the value
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()

    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error
    fun isLoading(): Boolean = this is Loading

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun errorMessage(): String? = when (this) {
        is Error -> message
        else -> null
    }
} 