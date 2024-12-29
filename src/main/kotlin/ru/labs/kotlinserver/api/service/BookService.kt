package ru.labs.kotlinserver.api.service

import ru.labs.kotlinserver.api.dto.CreateBookDto
import ru.labs.kotlinserver.db.entity.Book
import org.springframework.http.ResponseEntity

interface BookService {

    fun createBook(createBookDto: CreateBookDto) : ResponseEntity<Book>

}