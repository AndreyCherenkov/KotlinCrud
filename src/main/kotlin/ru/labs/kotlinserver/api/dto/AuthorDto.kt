package ru.labs.kotlinserver.api.dto

import java.util.*

data class AuthorDto(
    val id: UUID,
    val name: String,
    val books: List<BookDto>
)
