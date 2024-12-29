package ru.labs.kotlinserver.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import ru.labs.kotlinserver.api.dto.CreateBookDto
import ru.labs.kotlinserver.api.service.BookService
import ru.labs.kotlinserver.db.entity.Book
import ru.labs.kotlinserver.db.repository.BookRepository
import ru.labs.kotlinserver.impl.service.BookServiceImpl
import java.time.Instant
import java.util.Date

class BookServiceImplTest {

    private val DATE: Date = Date.from(Instant.now())
    private val TITLE: String = "Test Title"

    private val bookRepository: BookRepository = mock(BookRepository::class.java)

    private val bookServiceImpl: BookService = BookServiceImpl(bookRepository)

    @Test
    fun `should create book successfully`() {

        val createBookDto = CreateBookDto(
            TITLE,
            DATE,
        )
        val expectedBook = Book(
            title = TITLE,
            datePublish = DATE,
        )
        `when`(bookRepository.save(any(Book::class.java))).thenReturn(expectedBook)

        val response: ResponseEntity<Book> = bookServiceImpl.createBook(createBookDto)

        assertEquals(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(expectedBook), response)
        verify(bookRepository, times(1)).save(any(Book::class.java))
    }

    @Test
    fun `should handle error when saving book`() {
        val createBookDto = CreateBookDto(
            TITLE,
            DATE
        )
        `when`(bookRepository.save(any(Book::class.java))).thenThrow(RuntimeException("Database Error"))

        try {
            bookServiceImpl.createBook(createBookDto)
        } catch (e: RuntimeException) {
            assertEquals("Database Error", e.message)
            verify(bookRepository, times(1)).save(any(Book::class.java))
        }
    }
}
