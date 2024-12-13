package com.livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection connection;

    public BookDAO() throws SQLException {
        // Connexion à la base de données PostgreSQL
        String url = "jdbc:postgresql://localhost:5433/books_db";
        String user = "postgres";
        String password = "root";
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYear());
            stmt.executeUpdate();
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setYear(rs.getInt("year"));
                books.add(book);
            }
        }
        return books;
    }

    public void deleteBook(int id) throws SQLException {
        String query = "DELETE FROM books WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    public List<Book> rechercherLivre(String titre) throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE title ILIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + titre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setYear(rs.getInt("year"));
                    books.add(book);
                }
            }
        }
        return books;
    }
    
    // Méthode pour obtenir les statistiques
    public void afficherStatistiques() throws SQLException {
        System.out.println("\n=== Statistiques de la Bibliothèque ===");

        // 1. Nombre total de livres
        String queryBooks = "SELECT COUNT(*) AS total_books FROM books";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(queryBooks)) {
            if (rs.next()) {
                System.out.println("Nombre total de livres : " + rs.getInt("total_books"));
            }
        }

        // 2. Nombre total de membres
        String queryMembers = "SELECT COUNT(*) AS total_members FROM membre";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(queryMembers)) {
            if (rs.next()) {
                System.out.println("Nombre total de membres : " + rs.getInt("total_members"));
            }
        }

        // 3. Nombre total de locations
        String queryLocations = "SELECT COUNT(*) AS total_locations FROM pret";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(queryLocations)) {
            if (rs.next()) {
                System.out.println("Nombre total de locations : " + rs.getInt("total_locations"));
            }
        }

        // 4. Top 5 des livres les plus empruntés
        System.out.println("\n=== Top 5 des livres les plus empruntés ===");
        String queryTopBooks = """
            SELECT b.title, COUNT(l.id) AS borrow_count
            FROM books b
            JOIN pret l ON b.id = l.book_id
            GROUP BY b.title
            ORDER BY borrow_count DESC
            LIMIT 5
        """;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(queryTopBooks)) {
            while (rs.next()) {
                System.out.println(rs.getString("title") + " - Emprunté " + rs.getInt("borrow_count") + " fois");
            }
        }

        // 4. Nombre de livres empruntés
    try {
        String queryBorrowedBooks = "SELECT COUNT(DISTINCT book_id) AS borrowed_books FROM pret";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(queryBorrowedBooks)) {
            if (rs.next()) {
                System.out.println("Nombre de livres empruntés : " + rs.getInt("borrowed_books"));
            }
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de l'accès à la table pret pour les livres empruntés : " + e.getMessage());
    }

    // 5. Nombre de livres disponibles
    try {
        String queryAvailableBooks = 
            "SELECT COUNT(*) AS available_books " +
            "FROM books " +
            "WHERE id NOT IN (SELECT book_id FROM pret)";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(queryAvailableBooks)) {
            if (rs.next()) {
                System.out.println("Nombre de livres disponibles : " + rs.getInt("available_books"));
            }
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors du calcul des livres disponibles : " + e.getMessage());
    }
    }

    public int getAvailableBooks() throws SQLException {
        // Requête pour obtenir le nombre de livres disponibles
        String query = "SELECT COUNT(*) AS available_books FROM books WHERE id NOT IN (SELECT book_id FROM pret)";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("available_books");
            }
        }
        return 0; // Retourne 0 si aucun livre n'est disponible
    }
    
    
}
