package com.home.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Entity representing a task in the notification service.
 * 
 * <p>This entity is used to track tasks that may trigger notifications
 * or are related to user activities within the system.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    /**
     * Unique identifier for the task.
     */
    @Id
    private UUID id;

    /**
     * ID of the user linked to this task.
     */
    @Column(name = "linked_user_id")
    private UUID linkedUserId;

    /**
     * Title or description of the task.
     */
    @Column(name = "title")
    private String title;

    /**
     * Flag indicating whether the task is completed.
     */
    @Column(name = "completed")
    private Boolean completed;
}
