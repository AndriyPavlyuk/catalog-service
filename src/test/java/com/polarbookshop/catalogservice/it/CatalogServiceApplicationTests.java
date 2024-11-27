package com.polarbookshop.catalogservice.it;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
class CatalogServiceApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenBookCreated() {
        var expecteBook = new Book("1234567890", "Title", "Author", 9.90);
        webTestClient
            .post()
            .uri("/books")
            .bodyValue(expecteBook)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Book.class).value(actualBook -> {
                assertThat(actualBook).isNotNull();
                assertThat(actualBook.isbn())
                    .isEqualTo(expecteBook.isbn());
            });
    }
}
