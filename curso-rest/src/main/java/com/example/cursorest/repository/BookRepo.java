package com.example.cursorest.repository;

// Java Program to Illustrate BookRepo File

import com.example.cursorest.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepo
        extends MongoRepository<Book, Integer> {
}