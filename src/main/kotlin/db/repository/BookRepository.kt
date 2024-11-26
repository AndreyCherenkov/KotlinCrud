package db.repository

import db.entity.Book
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface BookRepository: CrudRepository<Book, UUID> {
}