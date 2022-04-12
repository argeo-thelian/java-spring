package com.example.cursorest.services;

import com.example.cursorest.model.Book;
import com.example.cursorest.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl  implements BookService{

    @Autowired
    private BookRepo repo;

    @Override
    public Book saveBook(Book book){
        return repo.save(book);
    }

    @Override
    public Optional<Book> getBook(Integer id){
        return repo.findById(id);
    }
    public List<Book> getBooks(){
        return repo.findAll();
    }

    @Override
    public String deleteBook(Integer id){
        repo.deleteById(id);
        return "Deleted Successfully";
    }


}
