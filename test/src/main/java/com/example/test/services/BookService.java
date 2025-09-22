/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.test.services;

import com.example.test.model.Book;
import org.springframework.stereotype.Service;
import com.example.test.repository.bookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ADmiN
 */
@Service
public class BookService {

    @Autowired
    private bookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    public List<Book>getBookByType(String type){
        return bookRepository.findByType(type);
    }
    public Book getBookById(long id){
        return bookRepository.getBookById(id);
    }
}
