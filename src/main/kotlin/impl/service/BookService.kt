package impl.service

import api.dto.BookDtoResponse
import api.dto.CreateBookDto
import api.service.BookService
import db.entity.Book
import db.repository.BookRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class BookService(val bookRepository: BookRepository) : BookService {

    override fun getBooks(): ResponseEntity<List<BookDtoResponse>> {
        return ResponseEntity(bookRepository.findAll() as List<BookDtoResponse>, HttpStatus.OK)
    }

    override fun getBook(bookId: String): ResponseEntity<BookDtoResponse> {

        val id: UUID = try {
            UUID.fromString(bookId)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid book id $bookId")
        }

//        if (bookRepository.existsById(id) {
//            bookRepository.findById(id)
//            return ResponseEntity()
//        }

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