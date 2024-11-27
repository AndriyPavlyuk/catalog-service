package com.polarbookshop.catalogservice.service;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.exception.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.exception.BookNotFoundException;
import com.polarbookshop.catalogservice.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    /**
     * Edits the details of a book in the catalog.
     *
     * @param isbn The ISBN of the book to be edited.
     * @param book The updated book details.
     * @return The edited book.
     */
    public Book editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn)
            .map(existingBook -> {
                var newBook = new Book(
                    existingBook.isbn(),
                    book.title(),
                    book.author(),
                    book.price()
                );
                return bookRepository.save(newBook);
            })
            .orElseGet(() -> addBookToCatalog(book));
    }
}
