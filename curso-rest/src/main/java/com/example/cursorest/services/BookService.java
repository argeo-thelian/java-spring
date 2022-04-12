package com.example.cursorest.services;

import com.example.cursorest.model.Book;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.List;
import java.util.Optional;

public interface BookService {
    public Book saveBook(Book book);
    public Optional<Book> getBook(Integer id);
    public List<Book> getBooks();
    //public Book updateBook(Book book);
    public String deleteBook(Integer id);
}
