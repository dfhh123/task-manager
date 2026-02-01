package com.home.notificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "users")
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private UUID publicId;

    private String email;

    private List<Task> tasks;
}
