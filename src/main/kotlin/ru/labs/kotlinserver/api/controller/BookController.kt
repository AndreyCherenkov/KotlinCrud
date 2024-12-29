package ru.labs.kotlinserver.api.controller

import ru.labs.kotlinserver.db.entity.Book
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.labs.kotlinserver.api.dto.*

@RequestMapping("/api/v1/book")
interface BookController {

    @PostMapping
    fun createBook(@RequestBody createBookDto: CreateBookDto) : ResponseEntity<Book>

}