package ru.labs.kotlinserver.api.controller

import ru.labs.kotlinserver.api.dto.CreateAuthorDto
import ru.labs.kotlinserver.db.entity.Author
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.labs.kotlinserver.api.dto.AddBookToAuthorDto
import ru.labs.kotlinserver.api.dto.AuthorDto
import java.util.UUID

@RequestMapping("/api/v1/author")
interface AuthorController {

    @GetMapping("/all")
    fun getAllAuthors(): ResponseEntity<List<AuthorDto>>

    @GetMapping("/{id}")
    fun getAuthorById(@PathVariable id: UUID):  ResponseEntity<AuthorDto>

    @PostMapping
    fun createAuthor(@RequestBody createAuthorDto: CreateAuthorDto): ResponseEntity<Author>

    @PutMapping("/add/book")
    fun addBookToAuthor(@RequestBody addBookToAuthorDto: AddBookToAuthorDto): ResponseEntity<AuthorDto>

}
