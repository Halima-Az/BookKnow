/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.test.services;

import com.example.test.model.Book;
import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import com.example.test.repository.bookRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADmiN
 */
@Service
public class FavoriteService {
  @Autowired private UserRepository   UserRepository;
    @Autowired private bookRepository   bookRepository;
    
    public void addBookToFavorites(long userId, long bookId){
        User user=UserRepository.findById(userId).orElseThrow();
        Book book=bookRepository.findById(bookId).orElseThrow();
        user.getFavorites().add(book);
        UserRepository.save(user);
        
        
        
    }
    public void RemoveBookToFavorites(long userId, long bookId){
        User user=UserRepository.findById(userId).orElseThrow();
        Book book=bookRepository.findById(bookId).orElseThrow();
        user.getFavorites().remove(book);
        UserRepository.save(user);
        
        
        
    }
    // cette methode exactement pour l'utiliser dans l'affichage 
    //permet de renvoyer les livres d'un user connecte
    
      public Set<Book> getFavorites(Long userId) {
        return UserRepository.findById(userId)
                .map(User::getFavorites)
                .orElse(Set.of());//Set.of() pratique pour eviter NullPointerException
    }
      
      // ajouter ou supprimer un eleent de favorite
      public boolean toggleFavorite(long userId, long bookId) {
          User user=UserRepository.findById(userId).orElseThrow();
          Book book =bookRepository.findById(bookId).orElseThrow();
          if(user.getFavorites().contains(book)){
             user.getFavorites().remove(book);
              UserRepository.save(user);
             return false;
          }
          else{
              user.getFavorites().add(book);
               UserRepository.save(user);
              return true;
          }
         
          
      }
}
