package ru.labs.kotlinserver.db.repository

import ru.labs.kotlinserver.db.entity.Book
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.UUID

interface BookRepository: CrudRepository<Book, UUID> {

    @Query("SELECT b FROM Book b WHERE b.id = :bookId")
    fun findBookByIdWithoutAuthors(@Param("bookId") bookId: UUID): Book?

}