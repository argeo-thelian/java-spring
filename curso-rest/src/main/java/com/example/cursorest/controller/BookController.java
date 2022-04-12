package com.example.cursorest.controller;

import com.example.cursorest.model.Book;
import com.example.cursorest.repository.BookRepo;
import com.example.cursorest.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Annotation
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/books")
    public Book saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @GetMapping("/books/{id}")
    public Optional<Book> getBook(@PathVariable int id) {
        return bookService.getBook(id);
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable int id){
        return bookService.deleteBook(id);
    }

}
