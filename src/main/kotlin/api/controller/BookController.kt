package api.controller

import api.dto.BookDtoResponse
import api.dto.CreateBookDto
import db.entity.Book
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

//todo Написать dto классы
@RestController
@RequestMapping("/api/v1/books")
interface BookController {

    @GetMapping("/all")
    fun getBooks() : ResponseEntity<List<BookDtoResponse>>

    @GetMapping("/{bookId}")
    fun getBook(@PathVariable bookId : String) : ResponseEntity<BookDtoResponse>

    @PostMapping
    fun createBook(createBookDto: CreateBookDto) : ResponseEntity<BookDtoResponse>

    @PutMapping("/{bookId}")
    fun updateBook(@PathVariable bookId : UUID, book: Book) : ResponseEntity<BookDtoResponse>

    @DeleteMapping("/{bookId}")
    fun deleteBook(@PathVariable bookId : UUID)

}