package ru.labs.kotlinserver.impl.service

import jakarta.persistence.EntityNotFoundException
import ru.labs.kotlinserver.api.dto.CreateAuthorDto
import ru.labs.kotlinserver.api.service.AuthorService
import ru.labs.kotlinserver.db.entity.Author
import ru.labs.kotlinserver.db.repository.AuthorRepository
import ru.labs.kotlinserver.db.repository.BookRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.labs.kotlinserver.api.dto.AddBookToAuthorDto
import ru.labs.kotlinserver.api.dto.AuthorDto
import ru.labs.kotlinserver.api.dto.BookDto
import java.util.*

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) : AuthorService {

    override fun getAllAuthors(): ResponseEntity<List<AuthorDto>> {
        val authors = authorRepository.findAllWithBooks().map { author ->
            AuthorDto(
                author.id!!,
                author.name,
                author.books!!.map { book ->
                    BookDto(
                        book.id,
                        book.title
                    )
                }
            )
        }

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(authors)
    }


    override fun getAuthorById(id: UUID): ResponseEntity<AuthorDto> {
        val author: Author = authorRepository.findByAuthorId(id).orElseThrow {
            EntityNotFoundException("Автор с книгами не найден")
        }

        val authorDto = AuthorDto(
            id = author.id!!,
            name = author.name,
            books = author.books!!.map { BookDto(it.id!!, it.title) }
        )

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(authorDto)
    }

    override fun createAuthor(createAuthorDto: CreateAuthorDto): ResponseEntity<Author> {
        val author = Author(
            name = createAuthorDto.name,
            books = mutableListOf()
        )

        val savedAuthor = authorRepository.save(author)

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(savedAuthor)
    }

    @Transactional
    override fun addBookToAuthor(addBookToAuthorDto: AddBookToAuthorDto): ResponseEntity<AuthorDto> {

        val books = addBookToAuthorDto.bookIds.mapNotNull { bookDto ->
            bookRepository.findById(bookDto).orElse(null)
        }

        val authorOptional = authorRepository.findById(addBookToAuthorDto.authorId)

        if (authorOptional.isPresent) {
            val author = authorOptional.get()
            books.stream()
                .forEach { book -> book.authors?.add(author) }

            author.books!!.addAll(books)
            authorRepository.save(author)

            val authorDTO = AuthorDto(
                id = author.id!!,
                name = author.name,
                books = author.books!!.map { BookDto(it.id, it.title) }
            )

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(authorDTO)
        } else {
            throw EntityNotFoundException("Автор не найден")
        }
    }
}