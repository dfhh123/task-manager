package com.home.service

import com.home.entity.Task
import com.home.repository.TaskRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.*

class TaskServiceTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var kafkaProducerService: KafkaProducerService
    private lateinit var taskService: TaskServiceImpl

    @BeforeEach
    fun setUp() {
        taskRepository = mockk()
        kafkaProducerService = mockk()
        taskService = TaskServiceImpl(taskRepository, kafkaProducerService)
        
        every { kafkaProducerService.sendTaskCreatedEvent(any()) } returns CompletableFuture.completedFuture(mockk())
        every { kafkaProducerService.sendTaskUpdatedEvent(any()) } returns CompletableFuture.completedFuture(mockk())
        every { kafkaProducerService.sendTaskDeletedEvent(any()) } returns CompletableFuture.completedFuture(mockk())
    }

    @Test
    fun `should create task successfully`() = runBlocking {
        // Given
        val task = Task(
            title = "Test Task",
            description = "Test Description",
            linkedUsers = emptyList()
        )
        val savedTask = task.copy(
            taskId = UUID.randomUUID(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        every { taskRepository.save(any()) } returns savedTask

        // When
        val result = taskService.create(task)

        // Then
        assertEquals(savedTask, result)
        verify { taskRepository.save(any()) }
        verify { kafkaProducerService.sendTaskCreatedEvent(savedTask) }
    }

    @Test
    fun `should get task by id successfully`() = runBlocking {
        // Given
        val taskId = UUID.randomUUID()
        val task = Task(
            taskId = taskId,
            title = "Test Task",
            description = "Test Description",
            linkedUsers = emptyList()
        )
        
        every { taskRepository.findById(taskId) } returns Optional.of(task)

        // When
        val result = taskService.getOne(taskId.toString())

        // Then
        assertEquals(task, result)
        verify { taskRepository.findById(taskId) }
    }

    @Test
    fun `should throw exception when task not found`() = runBlocking {
        // Given
        val taskId = UUID.randomUUID()
        every { taskRepository.findById(taskId) } returns Optional.empty()

        // When & Then
        val exception = assertThrows<ResponseStatusException> {
            runBlocking { taskService.getOne(taskId.toString()) }
        }
        assertEquals(HttpStatus.NOT_FOUND, exception.statusCode)
    }

    @Test
    fun `should get all tasks successfully`() = runBlocking {
        // Given
        val tasks = listOf(
            Task(taskId = UUID.randomUUID(), title = "Task 1", linkedUsers = emptyList()),
            Task(taskId = UUID.randomUUID(), title = "Task 2", linkedUsers = emptyList())
        )
        
        every { taskRepository.findAll() } returns tasks

        // When
        val result = taskService.getAll()

        // Then
        assertEquals(tasks, result)
        verify { taskRepository.findAll() }
    }

    @Test
    fun `should delete task successfully`() = runBlocking {
        // Given
        val taskId = UUID.randomUUID()
        val task = Task(
            taskId = taskId,
            title = "Test Task",
            description = "Test Description",
            linkedUsers = emptyList()
        )
        
        every { taskRepository.findById(taskId) } returns Optional.of(task)
        every { taskRepository.deleteById(taskId) } just Runs

        // When
        val result = taskService.delete(taskId.toString())

        // Then
        assertEquals(task, result)
        verify { taskRepository.findById(taskId) }
        verify { taskRepository.deleteById(taskId) }
        verify { kafkaProducerService.sendTaskDeletedEvent(task) }
    }

    @Test
    fun `should update task successfully`() = runBlocking {
        // Given
        val taskId = UUID.randomUUID()
        val task = Task(
            taskId = taskId,
            title = "Updated Task",
            description = "Updated Description",
            linkedUsers = emptyList()
        )
        val updatedTask = task.copy(updatedAt = LocalDateTime.now())
        
        every { taskRepository.existsById(taskId) } returns true
        every { taskRepository.save(any()) } returns updatedTask

        // When
        val result = taskService.update(task)

        // Then
        assertEquals(updatedTask, result)
        verify { taskRepository.existsById(taskId) }
        verify { taskRepository.save(any()) }
        verify { kafkaProducerService.sendTaskUpdatedEvent(updatedTask) }
    }

    @Test
    fun `should add user to task successfully`() = runBlocking {
        // Given
        val taskId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val task = Task(
            taskId = taskId,
            title = "Test Task",
            description = "Test Description",
            linkedUsers = emptyList()
        )
        val updatedTask = task.copy(
            linkedUsers = listOf(userId),
            updatedAt = LocalDateTime.now()
        )
        
        every { taskRepository.findById(taskId) } returns Optional.of(task)
        every { taskRepository.save(any()) } returns updatedTask

        // When
        val result = taskService.addUserToTask(taskId.toString(), userId.toString())

        // Then
        assertEquals(updatedTask, result)
        verify { taskRepository.findById(taskId) }
        verify { taskRepository.save(any()) }
        verify { kafkaProducerService.sendTaskUpdatedEvent(updatedTask) }
    }

    @Test
    fun `should remove user from task successfully`() = runBlocking {
        // Given
        val taskId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val task = Task(
            taskId = taskId,
            title = "Test Task",
            description = "Test Description",
            linkedUsers = listOf(userId)
        )
        val updatedTask = task.copy(
            linkedUsers = emptyList(),
            updatedAt = LocalDateTime.now()
        )
        
        every { taskRepository.findById(taskId) } returns Optional.of(task)
        every { taskRepository.save(any()) } returns updatedTask

        // When
        val result = taskService.removeUserFromTask(taskId.toString(), userId.toString())

        // Then
        assertEquals(updatedTask, result)
        verify { taskRepository.findById(taskId) }
        verify { taskRepository.save(any()) }
        verify { kafkaProducerService.sendTaskUpdatedEvent(updatedTask) }
    }

    @Test
    fun `should get tasks by user id successfully`() = runBlocking {
        // Given
        val userId = UUID.randomUUID()
        val tasks = listOf(
            Task(taskId = UUID.randomUUID(), title = "Task 1", linkedUsers = listOf(userId)),
            Task(taskId = UUID.randomUUID(), title = "Task 2", linkedUsers = listOf(userId))
        )
        
        every { taskRepository.findByLinkedUsersContaining(userId) } returns tasks

        // When
        val result = taskService.getTasksByUserId(userId.toString())

        // Then
        assertEquals(tasks, result)
        verify { taskRepository.findByLinkedUsersContaining(userId) }
    }
}
