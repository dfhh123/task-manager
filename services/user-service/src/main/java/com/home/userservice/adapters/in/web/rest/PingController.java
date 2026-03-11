package com.home.userservice.adapters.in.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/rest/{version}/ping", version = "1")
public class PingController {

    @GetMapping
    public String ping() {
        return "pong";
    }
}
