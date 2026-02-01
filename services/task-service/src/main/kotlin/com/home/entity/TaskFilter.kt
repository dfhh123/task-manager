package com.home.entity

import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import java.util.UUID

data class TaskFilter (

    @field:NotNull("Linked user id is required")
    val linkedUserId: UUID,

    val status: TaskStatus? = null,

    val priority: TaskPriority? = null,

    @field:Size(
        min = 2,
        max = 100,
        message = "Search query must be between 2 and 100 characters"
    )
    val search: String? = null,

    val deadlineBefore: LocalDateTime? = null,

    val deadlineAfter: LocalDateTime? = null,

    ) {

    init {
        if (deadlineBefore != null && deadlineAfter != null) {
            require(deadlineAfter.isBefore(deadlineBefore)) {
                "deadlineAfter ($deadlineAfter) must be before deadlineBefore ($deadlineBefore)"
            }
        }

        search?.let {
            require(it.isNotBlank()) {
                "Search query cannot be blank"
            }
        }
    }

}