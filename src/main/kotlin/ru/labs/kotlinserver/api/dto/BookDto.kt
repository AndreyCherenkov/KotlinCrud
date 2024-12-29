package ru.labs.kotlinserver.api.dto

import java.util.UUID

data class BookDto(
    val id: UUID?,
    val title: String
)

