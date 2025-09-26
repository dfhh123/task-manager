package com.home.service

import com.home.entity.Task
import com.home.entity.TaskStatus
import com.home.entity.TaskPriority
import com.home.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val kafkaProducerService: KafkaProducerService
) : TaskService {
    
    private val logger = KotlinLogging.logger {}

    override suspend fun getOne(id: String): Task = withContext(Dispatchers.IO) {
        logger.info { "Retrieving task with ID: $id" }
        try {
            val task = taskRepository.findById(id)
                .orElseThrow { 
                    logger.warn { "Task not found with ID: $id" }
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found") 
                }
            logger.info { "Successfully retrieved task: ${task.taskId}" }
            task
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving task with ID: $id" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving task")
        }
    }

    override suspend fun getAll(): List<Task> = withContext(Dispatchers.IO) {
        logger.info { "Retrieving all tasks" }
        val tasks = taskRepository.findAll()
        logger.info { "Retrieved ${tasks.size} tasks" }
        tasks
    }

    override suspend fun delete(id: String): Task = withContext(Dispatchers.IO) {
        logger.info { "Deleting task with ID: $id" }
        try {
            val task = taskRepository.findById(id)
                .orElseThrow { 
                    logger.warn { "Task not found for deletion with ID: $id" }
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found") 
                }
            
            taskRepository.deleteById(id)
            logger.info { "Successfully deleted task: ${task.taskId}" }
            
            // Send Kafka event
            kafkaProducerService.sendTaskDeletedEvent(task)
            
            task
        } catch (e: Exception) {
            logger.error(e) { "Error deleting task with ID: $id" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting task")
        }
    }

    override suspend fun create(task: Task): Task = withContext(Dispatchers.IO) {
        logger.info { "Creating new task with title: ${task.title}" }
        try {
            val taskWithTimestamps = task.copy(
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            val savedTask = taskRepository.save(taskWithTimestamps)
            logger.info { "Successfully created task: ${savedTask.taskId}" }
            
            // Send Kafka event
            kafkaProducerService.sendTaskCreatedEvent(savedTask)
            
            savedTask
        } catch (e: Exception) {
            logger.error(e) { "Failed to create task: ${task.title}" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create task")
        }
    }

    override suspend fun update(task: Task): Task = withContext(Dispatchers.IO) {
        logger.info { "Updating task with ID: ${task.taskId}" }
        try {
            val taskId = task.taskId ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Task ID is required for update")
            
            if (taskRepository.existsById(taskId)) {
                val updatedTask = task.copy(updatedAt = LocalDateTime.now())
                val savedTask = taskRepository.save(updatedTask)
                logger.info { "Successfully updated task: ${savedTask.taskId}" }
                
                // Send Kafka event
                kafkaProducerService.sendTaskUpdatedEvent(savedTask)
                
                savedTask
            } else {
                logger.warn { "Task not found for update with ID: $taskId" }
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to update task: ${task.taskId}" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update task")
        }
    }

    override suspend fun getTasksByUserId(userId: String): List<Task> = withContext(Dispatchers.IO) {
        logger.info { "Retrieving tasks for user ID: $userId" }
        try {
            val tasks = taskRepository.findByLinkedUsersContaining(userId)
            logger.info { "Retrieved ${tasks.size} tasks for user: $userId" }
            tasks
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving tasks for user: $userId" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving tasks for user")
        }
    }

    override suspend fun addUserToTask(taskId: String, userId: String): Task = withContext(Dispatchers.IO) {
        logger.info { "Adding user $userId to task $taskId" }
        try {
            val task = taskRepository.findById(taskId)
                .orElseThrow { 
                    logger.warn { "Task not found for adding user with ID: $taskId" }
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found") 
                }
            
            val updatedLinkedUsers = if (!task.linkedUsers.contains(userId)) {
                task.linkedUsers + userId
            } else {
                logger.info { "User $userId is already linked to task $taskId" }
                task.linkedUsers
            }
            
            val updatedTask = task.copy(
                linkedUsers = updatedLinkedUsers,
                updatedAt = LocalDateTime.now()
            )
            val savedTask = taskRepository.save(updatedTask)
            logger.info { "Successfully added user $userId to task ${savedTask.taskId}" }
            
            // Send Kafka event
            kafkaProducerService.sendTaskUpdatedEvent(savedTask)
            
            savedTask
        } catch (e: Exception) {
            logger.error(e) { "Error adding user $userId to task $taskId" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding user to task")
        }
    }

    override suspend fun removeUserFromTask(taskId: String, userId: String): Task = withContext(Dispatchers.IO) {
        logger.info { "Removing user $userId from task $taskId" }
        try {
            val task = taskRepository.findById(taskId)
                .orElseThrow { 
                    logger.warn { "Task not found for removing user with ID: $taskId" }
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found") 
                }
            
            val updatedLinkedUsers = task.linkedUsers.filter { it != userId }
            
            if (updatedLinkedUsers.size == task.linkedUsers.size) {
                logger.info { "User $userId was not linked to task $taskId" }
            }
            
            val updatedTask = task.copy(
                linkedUsers = updatedLinkedUsers,
                updatedAt = LocalDateTime.now()
            )
            val savedTask = taskRepository.save(updatedTask)
            logger.info { "Successfully removed user $userId from task ${savedTask.taskId}" }
            
            // Send Kafka event
            kafkaProducerService.sendTaskUpdatedEvent(savedTask)
            
            savedTask
        } catch (e: Exception) {
            logger.error(e) { "Error removing user $userId from task $taskId" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error removing user from task")
        }
    }

    // Новые методы для MongoDB
    override suspend fun getTasksByStatus(status: TaskStatus): List<Task> = withContext(Dispatchers.IO) {
        logger.info { "Retrieving tasks by status: $status" }
        try {
            val tasks = taskRepository.findByStatus(status)
            logger.info { "Retrieved ${tasks.size} tasks with status: $status" }
            tasks
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving tasks by status: $status" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving tasks by status")
        }
    }

    override suspend fun getTasksByPriority(priority: TaskPriority): List<Task> = withContext(Dispatchers.IO) {
        logger.info { "Retrieving tasks by priority: $priority" }
        try {
            val tasks = taskRepository.findByPriority(priority)
            logger.info { "Retrieved ${tasks.size} tasks with priority: $priority" }
            tasks
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving tasks by priority: $priority" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving tasks by priority")
        }
    }

    override suspend fun getTasksByAssignedUser(assignedTo: String): List<Task> = withContext(Dispatchers.IO) {
        logger.info { "Retrieving tasks assigned to user: $assignedTo" }
        try {
            val tasks = taskRepository.findByAssignedTo(assignedTo)
            logger.info { "Retrieved ${tasks.size} tasks assigned to user: $assignedTo" }
            tasks
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving tasks assigned to user: $assignedTo" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving tasks assigned to user")
        }
    }

    override suspend fun getTasksByStatusAndPriority(status: TaskStatus, priority: TaskPriority): List<Task> = withContext(Dispatchers.IO) {
        logger.info { "Retrieving tasks by status: $status and priority: $priority" }
        try {
            val tasks = taskRepository.findByStatusAndPriority(status, priority)
            logger.info { "Retrieved ${tasks.size} tasks with status: $status and priority: $priority" }
            tasks
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving tasks by status and priority" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving tasks by status and priority")
        }
    }

    override suspend fun getTasksByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<Task> = withContext(Dispatchers.IO) {
        logger.info { "Retrieving tasks by date range: $startDate to $endDate" }
        try {
            val tasks = taskRepository.findByCreatedAtBetween(startDate, endDate)
            logger.info { "Retrieved ${tasks.size} tasks in date range" }
            tasks
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving tasks by date range" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving tasks by date range")
        }
    }

    override suspend fun getOverdueTasks(): List<Task> = withContext(Dispatchers.IO) {
        logger.info { "Retrieving overdue tasks" }
        try {
            val tasks = taskRepository.findOverdueTasks(LocalDateTime.now())
            logger.info { "Retrieved ${tasks.size} overdue tasks" }
            tasks
        } catch (e: Exception) {
            logger.error(e) { "Error retrieving overdue tasks" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving overdue tasks")
        }
    }

    override suspend fun searchTasksByTitle(title: String): List<Task> = withContext(Dispatchers.IO) {
        logger.info { "Searching tasks by title: $title" }
        try {
            val tasks = taskRepository.findByTitleContainingIgnoreCase(title)
            logger.info { "Found ${tasks.size} tasks matching title: $title" }
            tasks
        } catch (e: Exception) {
            logger.error(e) { "Error searching tasks by title: $title" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error searching tasks by title")
        }
    }

    // Статистика
    override suspend fun getTaskCountByStatus(status: TaskStatus): Long = withContext(Dispatchers.IO) {
        logger.info { "Getting task count by status: $status" }
        try {
            val count = taskRepository.countByStatus(status)
            logger.info { "Found $count tasks with status: $status" }
            count
        } catch (e: Exception) {
            logger.error(e) { "Error getting task count by status: $status" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting task count by status")
        }
    }

    override suspend fun getTaskCountByPriority(priority: TaskPriority): Long = withContext(Dispatchers.IO) {
        logger.info { "Getting task count by priority: $priority" }
        try {
            val count = taskRepository.countByPriority(priority)
            logger.info { "Found $count tasks with priority: $priority" }
            count
        } catch (e: Exception) {
            logger.error(e) { "Error getting task count by priority: $priority" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting task count by priority")
        }
    }

    override suspend fun getTaskStatistics(): Map<String, Any> = withContext(Dispatchers.IO) {
        logger.info { "Getting task statistics" }
        try {
            val totalTasks = taskRepository.count()
            val pendingTasks = taskRepository.countByStatus(TaskStatus.PENDING)
            val inProgressTasks = taskRepository.countByStatus(TaskStatus.IN_PROGRESS)
            val completedTasks = taskRepository.countByStatus(TaskStatus.COMPLETED)
            val overdueTasks = taskRepository.findOverdueTasks(LocalDateTime.now()).size
            
            val statistics = mapOf(
                "totalTasks" to totalTasks,
                "pendingTasks" to pendingTasks,
                "inProgressTasks" to inProgressTasks,
                "completedTasks" to completedTasks,
                "overdueTasks" to overdueTasks,
                "completionRate" to if (totalTasks > 0) (completedTasks.toDouble() / totalTasks * 100) else 0.0
            )
            
            logger.info { "Task statistics: $statistics" }
            statistics
        } catch (e: Exception) {
            logger.error(e) { "Error getting task statistics" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting task statistics")
        }
    }

    // Обновление статуса и приоритета
    override suspend fun updateTaskStatus(taskId: String, status: TaskStatus): Task = withContext(Dispatchers.IO) {
        logger.info { "Updating task $taskId status to $status" }
        try {
            val task = taskRepository.findById(taskId)
                .orElseThrow { 
                    logger.warn { "Task not found for status update with ID: $taskId" }
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found") 
                }
            
            val updatedTask = task.copy(
                status = status,
                updatedAt = LocalDateTime.now()
            )
            val savedTask = taskRepository.save(updatedTask)
            logger.info { "Successfully updated task $taskId status to $status" }
            
            // Send Kafka event
            kafkaProducerService.sendTaskUpdatedEvent(savedTask)
            
            savedTask
        } catch (e: Exception) {
            logger.error(e) { "Error updating task status for task: $taskId" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating task status")
        }
    }

    override suspend fun updateTaskPriority(taskId: String, priority: TaskPriority): Task = withContext(Dispatchers.IO) {
        logger.info { "Updating task $taskId priority to $priority" }
        try {
            val task = taskRepository.findById(taskId)
                .orElseThrow { 
                    logger.warn { "Task not found for priority update with ID: $taskId" }
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found") 
                }
            
            val updatedTask = task.copy(
                priority = priority,
                updatedAt = LocalDateTime.now()
            )
            val savedTask = taskRepository.save(updatedTask)
            logger.info { "Successfully updated task $taskId priority to $priority" }
            
            // Send Kafka event
            kafkaProducerService.sendTaskUpdatedEvent(savedTask)
            
            savedTask
        } catch (e: Exception) {
            logger.error(e) { "Error updating task priority for task: $taskId" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating task priority")
        }
    }

    override suspend fun assignTaskToUser(taskId: String, userId: String): Task = withContext(Dispatchers.IO) {
        logger.info { "Assigning task $taskId to user $userId" }
        try {
            val task = taskRepository.findById(taskId)
                .orElseThrow { 
                    logger.warn { "Task not found for assignment with ID: $taskId" }
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found") 
                }
            
            val updatedTask = task.copy(
                assignedTo = userId,
                updatedAt = LocalDateTime.now()
            )
            val savedTask = taskRepository.save(updatedTask)
            logger.info { "Successfully assigned task $taskId to user $userId" }
            
            // Send Kafka event
            kafkaProducerService.sendTaskUpdatedEvent(savedTask)
            
            savedTask
        } catch (e: Exception) {
            logger.error(e) { "Error assigning task $taskId to user $userId" }
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error assigning task to user")
        }
    }
}
