package com.home.entity

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import java.util.*

@Document(collection = "tasks")
data class Task(
    @Id
    @Field("_id")
    var id: UUID,

    @Field("user_id")
    var linkedUserId: UUID,

    @Field("name")
    @param:NotNull(message = "Title is required")
    @param:Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    var title: String,

    @Field("description")
    @param:Size(max = 1000, message = "Description must not exceed 1000 characters")
    var description: String,

    @Field("status")
    var status: TaskStatus = TaskStatus.PENDING,

    @Field("priority")
    @param:NotBlank(message = "Priority is required")
    var priority: TaskPriority = TaskPriority.LOW,

    @Field("deadline")
    var deadline: LocalDateTime

)

enum class TaskStatus {
    PENDING, IN_PROGRESS, COMPLETED, CANCELLED, ON_HOLD
}

enum class TaskPriority {
    LOW, MEDIUM, HIGH
}
