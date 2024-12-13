package com.livre;

import java.sql.*;
import java.util.Scanner;

public class RechercheAvancee {
    private static final String URL = "jdbc:postgresql://localhost:5432/bibliotheque";
    private static final String USER = "votre_utilisateur";
    private static final String PASSWORD = "votre_mot_de_passe";

    public static void rechercherLivre() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Entrez un critère de recherche : ");
            System.out.println("1. Par titre");
            System.out.println("2. Par auteur");
            System.out.println("3. Par année");
            System.out.println("4. Par mots-clés");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne restante
            
            String query = "SELECT * FROM book WHERE ";
            String critere = "";
            
            switch (choix) {
                case 1:
                    System.out.println("Entrez le titre : ");
                    critere = scanner.nextLine();
                    query += "titre ILIKE ?";
                    break;
                case 2:
                    System.out.println("Entrez l'auteur : ");
                    critere = scanner.nextLine();
                    query += "auteur ILIKE ?";
                    break;
                case 3:
                    System.out.println("Entrez l'année : ");
                    critere = scanner.nextLine();
                    query += "annee = ?";
                    break;
                case 4:
                    System.out.println("Entrez un mot-clé : ");
                    critere = scanner.nextLine();
                    query += "titre ILIKE ? OR auteur ILIKE ?";
                    break;
                default:
                    System.out.println("Choix invalide !");
                    return;
            }
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (choix == 4) {
                    statement.setString(1, "%" + critere + "%");
                    statement.setString(2, "%" + critere + "%");
                } else {
                    statement.setString(1, "%" + critere + "%");
                }
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Titre : " + resultSet.getString("titre") +
                            ", Auteur : " + resultSet.getString("auteur") +
                            ", Année : " + resultSet.getInt("annee"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
