package com.home.integration

import com.home.TaskServiceApplication
import com.home.entity.Task
import com.home.repository.TaskRepository
import com.home.service.TaskService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@SpringBootTest(classes = [TaskServiceApplication::class])
@Testcontainers
@ActiveProfiles("test")
@Transactional
class TaskServiceIntegrationTest {

    companion object {
        @Container
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")

        @DynamicPropertySource
        @JvmStatic
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @Autowired
    private lateinit var taskService: TaskService

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @BeforeEach
    fun setUp() {
        taskRepository.deleteAll()
    }

    @Test
    fun `should create and retrieve task`() = runBlocking {
        // Given
        val task = Task(
            title = "Integration Test Task",
            description = "Test Description",
            linkedUsers = emptyList()
        )

        // When
        val createdTask = taskService.create(task)
        val retrievedTask = taskService.getOne(createdTask.taskId.toString())

        // Then
        assertNotNull(createdTask.taskId)
        assertEquals(task.title, createdTask.title)
        assertEquals(task.description, createdTask.description)
        assertEquals(createdTask, retrievedTask)
    }

    @Test
    fun `should update task`() = runBlocking {
        // Given
        val task = Task(
            title = "Original Title",
            description = "Original Description",
            linkedUsers = emptyList()
        )
        val createdTask = taskService.create(task)

        // When
        val updatedTask = createdTask.copy(
            title = "Updated Title",
            description = "Updated Description"
        )
        val result = taskService.update(updatedTask)

        // Then
        assertEquals("Updated Title", result.title)
        assertEquals("Updated Description", result.description)
        assertEquals(createdTask.taskId, result.taskId)
    }

    @Test
    fun `should delete task`() = runBlocking {
        // Given
        val task = Task(
            title = "Task to Delete",
            description = "This task will be deleted",
            linkedUsers = emptyList()
        )
        val createdTask = taskService.create(task)

        // When
        val deletedTask = taskService.delete(createdTask.taskId.toString())

        // Then
        assertEquals(createdTask, deletedTask)
        
        // Verify task is deleted
        assertThrows<ResponseStatusException> {
            runBlocking { taskService.getOne(createdTask.taskId.toString()) }
        }
    }

    @Test
    fun `should add and remove user from task`() = runBlocking {
        // Given
        val userId = UUID.randomUUID()
        val task = Task(
            title = "Task with Users",
            description = "This task will have users",
            linkedUsers = emptyList()
        )
        val createdTask = taskService.create(task)

        // When - Add user
        val taskWithUser = taskService.addUserToTask(createdTask.taskId.toString(), userId.toString())

        // Then
        assertTrue(taskWithUser.linkedUsers.contains(userId))

        // When - Remove user
        val taskWithoutUser = taskService.removeUserFromTask(createdTask.taskId.toString(), userId.toString())

        // Then
        assertFalse(taskWithoutUser.linkedUsers.contains(userId))
    }

    @Test
    fun `should get tasks by user id`() = runBlocking {
        // Given
        val userId = UUID.randomUUID()
        val task1 = Task(
            title = "Task 1",
            description = "First task",
            linkedUsers = listOf(userId)
        )
        val task2 = Task(
            title = "Task 2",
            description = "Second task",
            linkedUsers = listOf(userId)
        )
        val task3 = Task(
            title = "Task 3",
            description = "Third task",
            linkedUsers = emptyList()
        )

        taskService.create(task1)
        taskService.create(task2)
        taskService.create(task3)

        // When
        val userTasks = taskService.getTasksByUserId(userId.toString())

        // Then
        assertEquals(2, userTasks.size)
        assertTrue(userTasks.all { it.linkedUsers.contains(userId) })
    }

    @Test
    fun `should handle invalid UUID format`() = runBlocking {
        // When & Then
        assertThrows<ResponseStatusException> {
            runBlocking { taskService.getOne("invalid-uuid") }
        }
    }

    @Test
    fun `should handle task not found`() = runBlocking {
        // Given
        val nonExistentId = UUID.randomUUID()

        // When & Then
        assertThrows<ResponseStatusException> {
            runBlocking { taskService.getOne(nonExistentId.toString()) }
        }
    }
}
