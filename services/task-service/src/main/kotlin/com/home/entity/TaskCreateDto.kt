package com.home.entity

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.*

/**
 * DTO for creating a new Task
 */
data class TaskCreateDto(
    @field:NotNull(message = "linkedUserId is required")
    val linkedUserId: UUID,

    @field:NotBlank(message = "Title is required")
    @field:Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    val title: String,

    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    val description: String,

    // Optional in request; defaults to LOW in mapper if null
    val priority: TaskPriority? = null,

    @field:NotNull(message = "Deadline is required")
    val deadline: LocalDateTime
)


