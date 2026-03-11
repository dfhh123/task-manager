package com.home.notificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "users")
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String publicId;

    private String email;

    private Collection<String> tasksIds;
}
