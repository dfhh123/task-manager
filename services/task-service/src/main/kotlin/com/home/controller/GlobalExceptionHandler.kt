package com.home.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler

@Component
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(e: Throwable): ResponseEntity<*> {
        logger.error(e.toString())
        return ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}