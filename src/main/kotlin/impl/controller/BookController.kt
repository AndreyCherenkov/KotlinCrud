package impl.controller

import api.controller.BookController
import api.dto.BookDtoResponse
import api.dto.CreateBookDto
import api.service.BookService
import db.entity.Book
import org.springframework.http.ResponseEntity
import java.util.*

class BookController(val bookService: BookService) : BookController {

    override fun getBooks(): ResponseEntity<List<BookDtoResponse>> {
        throw NotImplementedError()
    }

    override fun getBook(bookId: String): ResponseEntity<BookDtoResponse> {
        throw NotImplementedError()
    }

    override fun createBook(createBookDto: CreateBookDto): ResponseEntity<BookDtoResponse> {
        throw NotImplementedError()
    }

    override fun updateBook(bookId: UUID, book: Book): ResponseEntity<BookDtoResponse> {
        throw NotImplementedError()
    }

    override fun deleteBook(bookId: UUID) {
        throw NotImplementedError()
    }
}