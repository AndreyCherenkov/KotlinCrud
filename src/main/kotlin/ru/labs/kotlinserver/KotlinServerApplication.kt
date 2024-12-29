package ru.labs.kotlinserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class KotlinServerApplication

fun main(args: Array<String>) {
	runApplication<KotlinServerApplication>(*args)
}
