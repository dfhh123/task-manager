package com.home.entity

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.context.annotation.Description
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import java.util.*

@Document(collection = "tasks")
data class Task(
    @Id
    @Field("_id")
    val id: UUID,

    @Field("name")
    @param:NotBlank(message = "Title is required")
    @param:Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    val title: String,

    @Field("description")
    @param:Size(max = 1000, message = "Description must not exceed 1000 characters")
    val description: String,

    @Field("due")
    val status: TaskStatus,


    @Field("priority")
    @param:NotBlank(message = "Priority is required")
    val priority: TaskPriority,

    @Field("deadline")
    val deadline: LocalDateTime

)

enum class TaskStatus {
    PENDING, IN_PROGRESS, COMPLETED, CANCELLED, ON_HOLD
}

enum class TaskPriority {
    LOW, MEDIUM, HIGH
}
