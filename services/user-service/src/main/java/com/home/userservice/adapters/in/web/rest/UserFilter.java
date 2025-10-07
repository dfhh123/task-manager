package com.home.userservice.adapters.in.web.rest;

import com.home.userservice.domain.entity.User;
import org.springframework.data.jpa.domain.Specification;

public record UserFilter(Long id) {
    public Specification<User> toSpecification() {
        return idSpec();
    }

    private Specification<User> idSpec() {
        return ((root, query, cb) -> id != null
                ? cb.equal(root.get("id"), id)
                : null);
    }
}