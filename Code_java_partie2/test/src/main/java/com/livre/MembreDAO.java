package com.livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {
    private Connection connection;

    public MembreDAO() throws SQLException {
        // Connexion à la base de données PostgreSQL
        String url = "jdbc:postgresql://localhost:5433/books_db";
        String user = "postgres";
        String password = "root";
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void addMembre(Membre membre) throws SQLException {
        String query = "INSERT INTO membre (nom, email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getEmail());
            stmt.executeUpdate();
        }
    }

    public List<Membre> getAllMembres() throws SQLException {
        List<Membre> membres = new ArrayList<>();
        String query = "SELECT * FROM membre";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Membre membre = new Membre();
                membre.setId(rs.getInt("id"));
                membre.setNom(rs.getString("nom"));
                membre.setEmail(rs.getString("email"));
                membres.add(membre);
            }
        }
        return membres;
    }

    public void deleteMembre(int id) throws SQLException {
        String query = "DELETE FROM membre WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
