package ru.labs.kotlinserver.service

import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import ru.labs.kotlinserver.api.dto.AddBookToAuthorDto
import ru.labs.kotlinserver.api.dto.AuthorDto
import ru.labs.kotlinserver.api.dto.BookDto
import ru.labs.kotlinserver.api.dto.CreateAuthorDto
import ru.labs.kotlinserver.api.service.AuthorService
import ru.labs.kotlinserver.db.entity.Author
import ru.labs.kotlinserver.db.entity.Book
import ru.labs.kotlinserver.db.repository.AuthorRepository
import ru.labs.kotlinserver.db.repository.BookRepository
import ru.labs.kotlinserver.impl.service.AuthorServiceImpl
import java.time.Instant
import java.util.*

@ExtendWith(MockitoExtension::class)
class AuthorServiceTest {

    private val DATE: Date = Date.from(Instant.now())
    private val TITLE: String = "Test Title"
    private val UUID1: UUID = UUID.randomUUID()
    private val UUID2: UUID = UUID.randomUUID()

    private val authorRepository: AuthorRepository = mock(AuthorRepository::class.java)
    private val bookRepository: BookRepository = mock(BookRepository::class.java)

    private val authorService: AuthorService = AuthorServiceImpl(authorRepository, bookRepository)

    @Test
    fun `should return all authors with their books successfully`() {
        val author1 = Author(
            id = UUID1,
            name = "Author One",
            books = mutableListOf(
                Book(
                    id = UUID1,
                    title = "Book One",
                    datePublish = DATE
                )
            )
        )
        val author2 = Author(
            id = UUID2,
            name = "Author Two",
            books = mutableListOf(
                Book(
                    id = UUID2,
                    title = "Book Two",
                    datePublish = DATE
                )
            )
        )
        `when`(authorRepository.findAllWithBooks()).thenReturn(listOf(author1, author2))

        val expectedAuthorsDto = listOf(
            AuthorDto(
                UUID1,
                "Author One",
                listOf(BookDto(UUID1, "Book One"))
            ),
            AuthorDto(
                UUID2,
                "Author Two",
                listOf(BookDto(UUID2, "Book Two"))
            )
        )

        val response: ResponseEntity<List<AuthorDto>> = authorService.getAllAuthors()

        assertEquals(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(expectedAuthorsDto), response)
        verify(authorRepository, times(1)).findAllWithBooks()
    }

    @Test
    fun `should handle error when retrieving authors`() {
        `when`(authorRepository.findAllWithBooks()).thenThrow(RuntimeException("Database Error"))

        try {
            authorService.getAllAuthors()
        } catch (e: RuntimeException) {
            assertEquals("Database Error", e.message)
            verify(authorRepository, times(1)).findAllWithBooks()
        }
    }

    @Test
    fun `should return author by ID successfully`() {
        val authorId = UUID.randomUUID()
        val author = Author(
            id = UUID1,
            name = "Author One",
            books = mutableListOf(Book(id = UUID1, title = TITLE, datePublish = DATE))
        )
        `when`(authorRepository.findByAuthorId(authorId)).thenReturn(Optional.of(author))

        val expectedAuthorDto = AuthorDto(
            id = author.id!!,
            name = author.name,
            books = listOf(BookDto(author.books?.get(0)?.id!!, author.books!![0].title))
        )

        val response: ResponseEntity<AuthorDto> = authorService.getAuthorById(authorId)

        assertEquals(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(expectedAuthorDto), response)
        verify(authorRepository, times(1)).findByAuthorId(authorId)
    }

    @Test
    fun `should throw EntityNotFoundException when author not found by ID`() {
        val authorId = UUID.randomUUID()
        `when`(authorRepository.findByAuthorId(authorId)).thenReturn(Optional.empty())

        try {
            authorService.getAuthorById(authorId)
        } catch (e: EntityNotFoundException) {
            assertEquals("Автор с книгами не найден", e.message)
            verify(authorRepository, times(1)).findByAuthorId(authorId)
        }
    }

    @Test
    fun `should create author successfully`() {
        val createAuthorDto = CreateAuthorDto(name = "New Author")
        val savedAuthor = Author(id = UUID.randomUUID(), name = createAuthorDto.name, books = mutableListOf())
        `when`(authorRepository.save(any(Author::class.java))).thenReturn(savedAuthor)

        val response: ResponseEntity<Author> = authorService.createAuthor(createAuthorDto)

        assertEquals(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(savedAuthor), response)
        verify(authorRepository, times(1)).save(any(Author::class.java))
    }

    @Test
    fun `should handle error when creating author`() {
        val createAuthorDto = CreateAuthorDto(name = "New Author")
        `when`(authorRepository.save(any(Author::class.java))).thenThrow(RuntimeException("Database Error"))

        try {
            authorService.createAuthor(createAuthorDto)
        } catch (e: RuntimeException) {
            assertEquals("Database Error", e.message)
            verify(authorRepository, times(1)).save(any(Author::class.java))
            return
        }
    }

    @Test
    fun `should add books to author successfully`() {
        val authorId = UUID.randomUUID()
        val bookId1 = UUID.randomUUID()
        val bookId2 = UUID.randomUUID()

        val book1 = Book(id = bookId1, title = "Book One", datePublish = DATE, authors = mutableListOf())
        val book2 = Book(id = bookId2, title = "Book Two", datePublish = DATE, authors = mutableListOf())

        val author = Author(id = authorId, name = "Author One", books = mutableListOf())
        val addBookToAuthorDto = AddBookToAuthorDto(authorId = authorId, bookIds = listOf(bookId1, bookId2))

        `when`(bookRepository.findById(bookId1)).thenReturn(Optional.of(book1))
        `when`(bookRepository.findById(bookId2)).thenReturn(Optional.of(book2))
        `when`(authorRepository.findById(authorId)).thenReturn(Optional.of(author))

        val response: ResponseEntity<AuthorDto> = authorService.addBookToAuthor(addBookToAuthorDto)

        assertEquals(
            ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
                AuthorDto(
                    id = author.id!!,
                    name = author.name,
                    books = listOf(BookDto(book1.id!!, book1.title), BookDto(book2.id!!, book2.title))
                )
            ), response
        )

        verify(authorRepository, times(1)).findById(authorId)
        verify(bookRepository, times(1)).findById(bookId1)
        verify(bookRepository, times(1)).findById(bookId2)
        verify(authorRepository, times(1)).save(author)
    }

    @Test
    fun `should throw EntityNotFoundException when author not found`() {
        val authorId = UUID.randomUUID()
        val bookId1 = UUID.randomUUID()

        val addBookToAuthorDto = AddBookToAuthorDto(authorId = authorId, bookIds = listOf(bookId1))

        `when`(authorRepository.findById(authorId)).thenReturn(Optional.empty())

        try {
            authorService.addBookToAuthor(addBookToAuthorDto)
        } catch (e: EntityNotFoundException) {
            assertEquals("Автор не найден", e.message)
            verify(authorRepository, times(1)).findById(authorId)
            return
        }
    }

    @Test
    fun `should handle case when some books are not found`() {
        val authorId = UUID.randomUUID()
        val bookId1 = UUID.randomUUID()
        val bookId2 = UUID.randomUUID()

        val author = Author(id = authorId, name = "Author One", books = mutableListOf())
        val addBookToAuthorDto = AddBookToAuthorDto(authorId = authorId, bookIds = listOf(bookId1, bookId2))

        `when`(bookRepository.findById(bookId1)).thenReturn(
            Optional.of(
                Book(
                    id = bookId1,
                    title = "Book One",
                    datePublish = DATE,
                    authors = mutableListOf()
                )
            )
        )
        `when`(bookRepository.findById(bookId2)).thenReturn(Optional.empty())
        `when`(authorRepository.findById(authorId)).thenReturn(Optional.of(author))

        // Act
        val response: ResponseEntity<AuthorDto> = authorService.addBookToAuthor(addBookToAuthorDto)

        // Assert
        assertEquals(
            ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
                AuthorDto(
                    id = author.id!!,
                    name = author.name,
                    books = listOf(BookDto(bookId1, "Book One"))
                )
            ), response
        )

        verify(authorRepository, times(1)).findById(authorId)
        verify(bookRepository, times(1)).findById(bookId1)
        verify(bookRepository, times(1)).findById(bookId2)
        verify(authorRepository, times(1)).save(author)
    }
}
