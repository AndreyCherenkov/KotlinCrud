package ru.labs.kotlinserver.db.entity

import jakarta.persistence.*
import java.util.*

@Entity
data class Author(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "author_id")
    val id: UUID? = null,

    val name: String,

    @ManyToMany(mappedBy = "authors", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val books: MutableList<Book>? = null,

)


