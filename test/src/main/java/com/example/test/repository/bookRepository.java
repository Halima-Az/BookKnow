/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.test.repository;

import com.example.test.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADmiN
 */
@Repository
public interface bookRepository extends JpaRepository<Book, Long> {
    List<Book>findByType(String type);
    Book getBookById(long id);
}
