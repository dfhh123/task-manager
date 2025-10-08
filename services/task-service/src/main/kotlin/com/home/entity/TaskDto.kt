package com.home.entity

import java.time.LocalDateTime
import java.util.*

/**
 * DTO for [Task]
 */
data class TaskDto(
    val id: UUID,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val priority: TaskPriority,
    val deadline: LocalDateTime
)