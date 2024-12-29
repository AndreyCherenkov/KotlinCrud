package ru.labs.kotlinserver.impl.handler

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(
        ex: EntityNotFoundException,
        request: WebRequest
    ): ResponseEntity<String> {
        return ResponseEntity("Entity not found: ${ex.message}", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: WebRequest
    ): ResponseEntity<String> {
        return if (ex.message?.contains("UUID") == true) {
            ResponseEntity("Invalid UUID format: ${ex.message}", HttpStatus.BAD_REQUEST)
        } else {
            ResponseEntity("Invalid argument: ${ex.message}", HttpStatus.BAD_REQUEST)
        }
    }

}
