/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ADmiN
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "First Name is obligatory")
    private String nom;
    @NotBlank(message = "Last Name is obligatory")
    private String prenom;
    @NotBlank(message = "email is obligatory")
    @Email(message = "email format invalid")
    @Column(unique = true)
    private String username;
    private String password;
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name="user_favorites",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="book_id")
    )
    private Set<Book>favorites=new HashSet<>(); 
    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public long getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String nom) {
        this.prenom = nom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String motDePasse) {
        this.password = motDePasse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }
    
    public Set<Book> getFavorites(){
        return this.favorites;
    }
    public void setFavorites(Set<Book>  f){
         this.favorites=f;
    }

}
