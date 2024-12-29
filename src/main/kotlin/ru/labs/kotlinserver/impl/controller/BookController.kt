package ru.labs.kotlinserver.impl.controller

import ru.labs.kotlinserver.api.controller.BookController
import ru.labs.kotlinserver.api.dto.CreateBookDto
import ru.labs.kotlinserver.api.service.BookService
import ru.labs.kotlinserver.db.entity.Book
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(private val bookService: BookService) : BookController {

    override fun createBook(createBookDto: CreateBookDto): ResponseEntity<Book> {
        return bookService.createBook(createBookDto)
    }
}