package ru.labs.kotlinserver.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class CreateBookDto(

    @JsonProperty("title")
    val title: String,

    @JsonProperty("datePublish")
    val datePublish: Date
)
