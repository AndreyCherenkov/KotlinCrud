package ru.labs.kotlinserver.impl.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    fun test() : String {
        return "Hello, Kotlin!"
    }

}