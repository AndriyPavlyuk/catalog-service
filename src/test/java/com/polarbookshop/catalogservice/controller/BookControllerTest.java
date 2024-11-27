package com.polarbookshop.catalogservice.controller;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.polarbookshop.catalogservice.exception.BookNotFoundException;
import com.polarbookshop.catalogservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "1234567890";
        given(bookService.viewBookDetails(isbn))
            .willThrow(BookNotFoundException.class);
        mockMvc
            .perform(get("/books/" + isbn))
            .andExpect(status().isNotFound());

    }
}
