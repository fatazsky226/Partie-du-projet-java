package com.livre;

public class Pret {
    private int id;
    private int membreId;   // Référence à Membre
    private int bookId;     // Référence à Book
    private String datePret;

    // Constructeur
    public Pret(int membreId, int bookId, String datePret) {
        this.membreId = membreId;
        this.bookId = bookId;
        this.datePret = datePret;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMembreId() {
        return membreId;
    }

    public void setMembreId(int membreId) {
        this.membreId = membreId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getDatePret() {
        return datePret;
    }

    public void setDatePret(String datePret) {
        this.datePret = datePret;
    }
}
