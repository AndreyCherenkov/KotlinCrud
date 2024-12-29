package ru.labs.kotlinserver.db.entity

import jakarta.persistence.*
import java.util.Date
import java.util.UUID

@Entity
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "book_id")
    val id: UUID? = null,

    val title: String,

    @Column(name = "date_published")
    val datePublish: Date,

    @ManyToMany
    @JoinTable(
        name = "author_book",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    val authors: MutableList<Author>? = null
)
