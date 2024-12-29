package ru.labs.kotlinserver.testcontainers

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import ru.labs.kotlinserver.api.dto.CreateBookDto
import java.time.Instant
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerTest {

    private companion object {
        const val POSTGRES_IMAGE = "postgres:16-alpine"
        const val REQUEST_MAPPING = "/api/v1/book"
        val DATE: Date = Date.from(Instant.now())

        lateinit var postgres: PostgreSQLContainer<*>

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            postgres = PostgreSQLContainer<Nothing>(POSTGRES_IMAGE).apply {
                start()
            }
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            postgres.stop()
        }

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @LocalServerPort
    private var port: Int = 0

    @BeforeEach
    fun setUp() {
        RestAssured.baseURI = "http://localhost:${port}"
    }

    @Test
    fun `save valid book dto should return status 200`() {
        val createBookDto = CreateBookDto(
            title = "Test Book Title",
            datePublish = DATE
        )

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(createBookDto)
            .`when`()
            .post(REQUEST_MAPPING)
            .then()
            .statusCode(HttpStatus.OK.value())
    }

    @Test
    fun `save invalid book dto should return status 400`() {
        val createBookDto = CreateBookDto(
            title = "",
            datePublish = DATE
        )

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(createBookDto)
            .`when`()
            .post(REQUEST_MAPPING)
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
    }
}
