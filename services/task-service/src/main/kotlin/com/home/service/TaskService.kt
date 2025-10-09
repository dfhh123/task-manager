package com.home.service

import com.fasterxml.jackson.databind.JsonNode
import com.home.entity.TaskDto
import com.home.entity.TaskCreateDto
import java.io.IOException
import java.util.UUID

interface TaskService {
    fun getOne(id: UUID): TaskDto

    fun findManyByLinkedUserId(linkedUserId: UUID): List<TaskDto>

    fun create(dto: TaskCreateDto): TaskDto

    @Throws(IOException::class)
    fun patch( id: UUID,  patchNode: JsonNode): TaskDto

    fun update(id: UUID, dto: TaskDto): TaskDto

    fun delete(id: UUID): TaskDto?
}