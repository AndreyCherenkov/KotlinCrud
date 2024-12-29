package ru.labs.kotlinserver.db.repository

import ru.labs.kotlinserver.db.entity.Author
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface AuthorRepository: CrudRepository<Author, UUID> {

    @Query("SELECT a FROM Author a JOIN FETCH a.books WHERE a.id = :authorId")
    fun findByAuthorId(@Param("authorId") authorId: UUID): Optional<Author>

    @Query("SELECT a FROM Author a JOIN FETCH a.books")
    fun findAllWithBooks(): List<Author>


}
