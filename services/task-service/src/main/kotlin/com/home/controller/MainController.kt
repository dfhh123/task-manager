package com.home.controller

import com.home.entity.Task
import com.home.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/rest/tasks")
class MainController(
    private val taskService: TaskService
) {

    @GetMapping
    suspend fun getAll(): List<Task> {
        return taskService.getAll()
    }

    @GetMapping("/{id}")
    suspend fun getOne(
        @PathVariable id: String
    ): Task {
        return taskService.getOne(id)
    }

    @GetMapping("/user/{userId}")
    suspend fun getTasksByUserId(
        @PathVariable userId: String
    ): List<Task> {
        return taskService.getTasksByUserId(userId)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete(
        @PathVariable id: String
    ) {
        taskService.delete(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(
        @Valid @RequestBody task: Task
    ): Task {
        return taskService.create(task)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun update(
        @Valid @RequestBody task: Task,
        @PathVariable id: String
    ): Task {
        val updatedTask = task.copy(taskId = UUID.fromString(id))
        return taskService.update(updatedTask)
    }

    @PostMapping("/{id}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun addUserToTask(
        @PathVariable id: String,
        @PathVariable userId: String
    ): Task {
        return taskService.addUserToTask(id, userId)
    }

    @DeleteMapping("/{id}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun removeUserFromTask(
        @PathVariable id: String,
        @PathVariable userId: String
    ): Task {
        return taskService.removeUserFromTask(id, userId)
    }
}
