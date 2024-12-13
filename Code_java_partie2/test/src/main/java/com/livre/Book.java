package com.livre;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;

    public Book() {
    }
    
    // Constructeur avec titre et auteur
    public Book(String title, String author) {
        this.title = title;
        this.author= author;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
