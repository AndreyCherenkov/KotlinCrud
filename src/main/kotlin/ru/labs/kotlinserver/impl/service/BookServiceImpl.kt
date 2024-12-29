package ru.labs.kotlinserver.impl.service

import org.apache.coyote.BadRequestException
import ru.labs.kotlinserver.api.service.BookService
import ru.labs.kotlinserver.db.entity.Book
import ru.labs.kotlinserver.db.repository.BookRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.labs.kotlinserver.api.dto.*

@Service
class BookServiceImpl(
    val bookRepository: BookRepository,
) : BookService {

    override fun createBook(createBookDto: CreateBookDto): ResponseEntity<Book> {

        if (createBookDto.title.isBlank()) {
            throw BadRequestException("Title cannot be empty")
        }

        val book = Book(
            title = createBookDto.title,
            datePublish = createBookDto.datePublish,
            authors = mutableListOf()
        )

        val savedBook = bookRepository.save(book)

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(savedBook)
    }

}