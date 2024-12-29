package ru.labs.kotlinserver.api.service

import ru.labs.kotlinserver.api.dto.CreateAuthorDto
import ru.labs.kotlinserver.db.entity.Author
import org.springframework.http.ResponseEntity
import ru.labs.kotlinserver.api.dto.AddBookToAuthorDto
import ru.labs.kotlinserver.api.dto.AuthorDto
import java.util.UUID

interface AuthorService {

    fun getAllAuthors(): ResponseEntity<List<AuthorDto>>

    fun getAuthorById(id: UUID): ResponseEntity<AuthorDto>

    fun createAuthor(createAuthorDto: CreateAuthorDto): ResponseEntity<Author>

    fun addBookToAuthor(addBookToAuthorDto: AddBookToAuthorDto): ResponseEntity<AuthorDto>

}