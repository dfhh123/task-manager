package com.home.entity

import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
abstract class TaskMapper {
    abstract fun toTaskDto(task: Task): TaskDto

    // Create mapping: generate id, map linkedUserId from create dto, default status and priority
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "linkedUserId", source = "dto.linkedUserId")
    @Mapping(target = "status", expression = "java(com.home.entity.TaskStatus.PENDING)")
    @Mapping(target = "priority", expression = "java(dto.getPriority() != null ? dto.getPriority() : com.home.entity.TaskPriority.LOW)")
    abstract fun toEntity(dto: TaskCreateDto): Task

    // Update from full DTO: for immutable entity, return new Task via copy is not supported directly by MapStruct
    // Keep signature but note it won't work with val-only entity; prefer manual copy in service
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun updateWithNull(taskDto: TaskDto, @MappingTarget task: Task): Task
}