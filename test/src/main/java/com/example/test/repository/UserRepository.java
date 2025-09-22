/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.test.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.test.model.User;
import java.util.Optional;

/**
 *
 * @author ADmiN
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  public boolean existsByUsername(String username); 
  public Optional<User>  findByUsername(String username);
}
