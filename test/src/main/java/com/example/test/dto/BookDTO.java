package com.example.test.dto;

import org.springframework.web.multipart.MultipartFile;

public class BookDTO {

    private long id;
    private String nom;
    private String writer;
    private String date;
    private String type;
    private String language;
    private MultipartFile bookPdf; // uniquement pour le formulaire
    private MultipartFile image;

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public MultipartFile getBookPdf() {
        return bookPdf;
    }

    public void setBookPdf(MultipartFile bookPdf) {
        this.bookPdf = bookPdf;
    }
    public MultipartFile getImage(){
        return image;
    }
    public void setImage(MultipartFile img){
         this.image=img;
    }
}
