package com.home.entity

import java.time.LocalDateTime
import java.util.*

/**
 * DTO for [Task]
 */
data class TaskDto(
    val id: UUID,
    val linkedUserId: UUID,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val deadline: LocalDateTime
)