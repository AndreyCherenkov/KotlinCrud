package ru.labs.kotlinserver.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAuthorDto(
    @JsonProperty("name")
    val name: String,
)
