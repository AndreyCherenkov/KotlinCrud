package api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class CreateBookDto(

    @JsonProperty("title")
    val title: String,

    @JsonProperty("author")
    val author: String?,

    @JsonProperty("datePublish")
    val publishedDate: Date
)
