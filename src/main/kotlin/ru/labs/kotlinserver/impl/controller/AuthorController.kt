package ru.labs.kotlinserver.impl.controller

import ru.labs.kotlinserver.api.controller.AuthorController
import ru.labs.kotlinserver.api.dto.CreateAuthorDto
import ru.labs.kotlinserver.db.entity.Author
import ru.labs.kotlinserver.impl.service.AuthorServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.labs.kotlinserver.api.dto.AddBookToAuthorDto
import ru.labs.kotlinserver.api.dto.AuthorDto
import java.util.*

@RestController
class AuthorController(val authorServiceImpl: AuthorServiceImpl): AuthorController {

    override fun getAllAuthors(): ResponseEntity<List<AuthorDto>> {
        return authorServiceImpl.getAllAuthors()
    }

    override fun getAuthorById(id: UUID): ResponseEntity<AuthorDto> {
        return authorServiceImpl.getAuthorById(id)
    }

    override fun createAuthor(createAuthorDto: CreateAuthorDto): ResponseEntity<Author> {
        return authorServiceImpl.createAuthor(createAuthorDto)
    }

    override fun addBookToAuthor(addBookToAuthorDto: AddBookToAuthorDto): ResponseEntity<AuthorDto> {
        return authorServiceImpl.addBookToAuthor(addBookToAuthorDto)
    }

}