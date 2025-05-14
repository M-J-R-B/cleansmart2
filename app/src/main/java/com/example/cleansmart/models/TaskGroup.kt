package com.example.cleansmart.models

import java.util.Date

data class TaskGroup(
    val id: String = "",
    val userId: String,
    val areaName: String,
    val imageBase64: String? = null,
    val tasks: List<String>,
    val progress: Int = 0,
    val dateCreated: Long = Date().time
)

data class SaveTaskGroupRequest(
    val taskGroup: TaskGroup
)

data class SaveTaskGroupResponse(
    val success: Boolean,
    val message: String? = null,
    val taskGroup: TaskGroup? = null
)

data class GetTaskGroupsResponse(
    val success: Boolean,
    val message: String? = null,
    val taskGroups: List<TaskGroup>? = null
) 