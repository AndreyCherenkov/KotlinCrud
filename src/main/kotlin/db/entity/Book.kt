package db.entity

import jakarta.persistence.*
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "books")
data class Book(
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    val bookId: UUID,

    val title: String,

    val author: String?,

    val datePublish: Date,

)
