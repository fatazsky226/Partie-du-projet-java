package com.livre;

public class Membre {
    private int id;
    private String nom;
    private String email;

    // Constructeur sans paramètres
    public Membre() {
    }

    // Constructeur avec nom et email
    public Membre(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
