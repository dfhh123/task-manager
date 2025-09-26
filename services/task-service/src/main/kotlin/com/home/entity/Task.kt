package com.home.entity

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import java.util.*

@Document(collection = "tasks")
@Schema(description = "Task entity representing a task in the system")
data class Task(
    @Id
    @Field("_id")
    @Schema(description = "Unique identifier of the task", example = "550e8400-e29b-41d4-a716-446655440000")
    val taskId: String? = null,
    
    @field:NotBlank(message = "Title cannot be blank")
    @field:Size(max = 255, message = "Title cannot exceed 255 characters")
    @Indexed
    @Field("title")
    @Schema(description = "Title of the task", example = "Implement user authentication", required = true)
    val title: String,
    
    @field:Size(max = 5000, message = "Description cannot exceed 5000 characters")
    @Field("description")
    @Schema(description = "Detailed description of the task", example = "Implement JWT-based authentication system")
    val description: String? = null,
    
    @Field("linked_users")
    @Schema(description = "List of user IDs linked to this task")
    val linkedUsers: List<String> = emptyList(),
    
    @Indexed
    @Field("status")
    @Schema(description = "Current status of the task", example = "PENDING")
    val status: TaskStatus = TaskStatus.PENDING,
    
    @Indexed
    @Field("priority")
    @Schema(description = "Priority level of the task", example = "MEDIUM")
    val priority: TaskPriority = TaskPriority.MEDIUM,
    
    @Field("assigned_to")
    @Schema(description = "User ID assigned to this task")
    val assignedTo: String? = null,
    
    @Field("due_date")
    @Schema(description = "Due date for the task")
    val dueDate: LocalDateTime? = null,
    
    @Field("created_at")
    @Indexed
    @Schema(description = "Timestamp when the task was created")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Field("updated_at")
    @Schema(description = "Timestamp when the task was last updated")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class TaskStatus {
    PENDING, IN_PROGRESS, COMPLETED, CANCELLED, ON_HOLD
}

enum class TaskPriority {
    LOW, MEDIUM, HIGH, URGENT
}
