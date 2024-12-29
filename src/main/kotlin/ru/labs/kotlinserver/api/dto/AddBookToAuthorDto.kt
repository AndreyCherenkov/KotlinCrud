package ru.labs.kotlinserver.api.dto

import java.util.*

data class AddBookToAuthorDto(
    val authorId: UUID,
    val bookIds: List<UUID>
)