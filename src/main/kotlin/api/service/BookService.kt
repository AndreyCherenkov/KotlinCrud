package api.service

import api.dto.BookDtoResponse
import api.dto.CreateBookDto
import db.entity.Book
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
interface BookService {

    fun getBooks() : ResponseEntity<List<BookDtoResponse>>

    fun getBook(bookId : String) : ResponseEntity<BookDtoResponse>

    fun createBook(createBookDto: CreateBookDto) : ResponseEntity<BookDtoResponse>

    fun updateBook(bookId : UUID, book: Book) : ResponseEntity<BookDtoResponse>

    fun deleteBook(bookId : UUID)

}