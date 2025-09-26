package com.home.service

import com.home.entity.Task
import com.home.entity.TaskStatus
import com.home.entity.TaskPriority
import java.time.LocalDateTime

interface TaskService {
    // Базовые CRUD операции
    suspend fun getOne(id: String): Task
    suspend fun getAll(): List<Task>
    suspend fun delete(id: String): Task
    suspend fun create(task: Task): Task
    suspend fun update(task: Task): Task
    
    // Операции с пользователями
    suspend fun getTasksByUserId(userId: String): List<Task>
    suspend fun addUserToTask(taskId: String, userId: String): Task
    suspend fun removeUserFromTask(taskId: String, userId: String): Task
    
    // Новые методы для MongoDB
    suspend fun getTasksByStatus(status: TaskStatus): List<Task>
    suspend fun getTasksByPriority(priority: TaskPriority): List<Task>
    suspend fun getTasksByAssignedUser(assignedTo: String): List<Task>
    suspend fun getTasksByStatusAndPriority(status: TaskStatus, priority: TaskPriority): List<Task>
    suspend fun getTasksByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<Task>
    suspend fun getOverdueTasks(): List<Task>
    suspend fun searchTasksByTitle(title: String): List<Task>
    
    // Статистика
    suspend fun getTaskCountByStatus(status: TaskStatus): Long
    suspend fun getTaskCountByPriority(priority: TaskPriority): Long
    suspend fun getTaskStatistics(): Map<String, Any>
    
    // Обновление статуса и приоритета
    suspend fun updateTaskStatus(taskId: String, status: TaskStatus): Task
    suspend fun updateTaskPriority(taskId: String, priority: TaskPriority): Task
    suspend fun assignTaskToUser(taskId: String, userId: String): Task
}
