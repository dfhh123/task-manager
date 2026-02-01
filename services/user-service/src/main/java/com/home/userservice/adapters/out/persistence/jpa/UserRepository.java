package com.home.userservice.adapters.out.persistence.jpa;

import com.home.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    void deleteUserByKeycloakId(String keycloakId);

    Optional<User> findUserByKeycloakId(String publicId);

}