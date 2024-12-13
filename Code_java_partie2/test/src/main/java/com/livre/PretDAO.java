package com.livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PretDAO {
    private Connection connection;

    public PretDAO() throws SQLException {
        // Connexion à la base de données PostgreSQL
        String url = "jdbc:postgresql://localhost:5433/books_db";
        String user = "postgres";
        String password = "root";
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void addPret(Pret pret) throws SQLException {
        String query = "INSERT INTO pret (membre_id, book_id, date_pret) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pret.getMembreId());  // Utilise le getter membreId
            stmt.setInt(2, pret.getBookId());    // Utilise le getter bookId
            stmt.setString(3, pret.getDatePret()); // Utilise le getter datePret
            stmt.executeUpdate();
        }
    }

    public List<Pret> getAllPrets() throws SQLException {
        List<Pret> prets = new ArrayList<>();
        String query = "SELECT * FROM pret";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Pret pret = new Pret(rs.getInt("membre_id"), rs.getInt("book_id"), rs.getString("date_pret"));
                pret.setId(rs.getInt("id"));
                prets.add(pret);
            }
        }
        return prets;
    }

    public void deletePret(int id) throws SQLException {
        String query = "DELETE FROM pret WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
