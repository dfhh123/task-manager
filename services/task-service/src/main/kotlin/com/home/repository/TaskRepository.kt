package com.home.repository

import com.home.entity.Task
import com.home.entity.TaskStatus
import com.home.entity.TaskPriority
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TaskRepository : MongoRepository<Task, String> {

    // Поиск задач по связанным пользователям
    fun findByLinkedUsersContaining(userId: String): List<Task>
    
    // Поиск задач по статусу
    fun findByStatus(status: TaskStatus): List<Task>
    
    // Поиск задач по приоритету
    fun findByPriority(priority: TaskPriority): List<Task>
    
    // Поиск задач по назначенному пользователю
    fun findByAssignedTo(assignedTo: String): List<Task>
    
    // Поиск задач по статусу и приоритету
    fun findByStatusAndPriority(status: TaskStatus, priority: TaskPriority): List<Task>
    
    // Поиск задач по дате создания
    fun findByCreatedAtBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Task>
    
    // Поиск задач по сроку выполнения
    fun findByDueDateBefore(dueDate: LocalDateTime): List<Task>
    
    // Поиск задач по статусу и связанным пользователям
    fun findByStatusAndLinkedUsersContaining(status: TaskStatus, userId: String): List<Task>
    
    // Сложный запрос с использованием MongoDB Query
    @Query("{'status': ?0, 'priority': ?1, 'createdAt': {'$gte': ?2, '$lte': ?3}}")
    fun findTasksByStatusPriorityAndDateRange(
        status: TaskStatus, 
        priority: TaskPriority, 
        startDate: LocalDateTime, 
        endDate: LocalDateTime
    ): List<Task>
    
    // Поиск задач с текстовым поиском по заголовку
    @Query("{'title': {'$regex': ?0, '$options': 'i'}}")
    fun findByTitleContainingIgnoreCase(title: String): List<Task>
    
    // Подсчет задач по статусу
    fun countByStatus(status: TaskStatus): Long
    
    // Подсчет задач по приоритету
    fun countByPriority(priority: TaskPriority): Long
    
    // Поиск просроченных задач
    @Query("{'dueDate': {'$lt': ?0}, 'status': {'$ne': 'COMPLETED'}}")
    fun findOverdueTasks(currentDate: LocalDateTime): List<Task>
}
