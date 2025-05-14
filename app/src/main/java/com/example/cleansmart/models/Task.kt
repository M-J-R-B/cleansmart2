package com.example.cleansmart.models

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val area: String,
    val priority: Priority,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class Priority {
    LOW, MEDIUM, HIGH
}

// This class will be used when generating tasks from camera
data class TaskGenerationRequest(
    val area: String,
    val imageBase64: String? = null
)

// Response from the server with generated tasks
data class TaskGenerationResponse(
    val success: Boolean,
    val message: String? = null,
    val tasks: List<Task>? = null
) 