package com.livre;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BookDAO bookDAO;
    private static MembreDAO membreDAO;
    private static PretDAO pretDAO;

    public static void main(String[] args) {
        try {
            bookDAO = new BookDAO();
            membreDAO = new MembreDAO();
            pretDAO = new PretDAO();
            
            while (true) {
                afficherMenuPrincipal();
                int choix = scanner.nextInt();
                scanner.nextLine(); // Consomme la ligne restante
                switch (choix) {
                    case 1:
                        menuGestionLivres();
                        break;
                    case 2:
                        menuGestionMembres();
                        break;
                    case 3:
                        menuGestionPrets();
                        break;
                    case 4:
                        afficherStatistiques();
                        break;
                    case 0:
                        System.out.println("Au revoir!");
                        return;
                    default:
                        System.out.println("Choix invalide.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données: " + e.getMessage());
        }
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n=== Menu Principal ===");
        System.out.println("1. Gestion des livres");
        System.out.println("2. Gestion des membres");
        System.out.println("3. Gestion des prêts");
        System.out.println("4. Statistiques de la bibliothèque");
        System.out.println("0. Quitter");
        System.out.print("Choisissez une option: ");
    }
    
    private static void afficherStatistiques() throws SQLException {
        bookDAO.afficherStatistiques();
    }
    

    private static void menuGestionLivres() throws SQLException {
        System.out.println("\n=== Gestion des Livres ===");
        System.out.println("1. Ajouter un livre");
        System.out.println("2. Lister les livres");
        System.out.println("3. Supprimer un livre");
        System.out.println("4. Rechercher un livre"); // Nouvelle option
        System.out.print("Choisissez une option: ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consomme la ligne restante
        switch (choix) {
            case 1:
                ajouterLivre();
                break;
            case 2:
                listerLivres();
                break;
            case 3:
                supprimerLivre();
                break;
            case 4:
                rechercherLivre(); // Appel de la méthode
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }
    

    private static void ajouterLivre() throws SQLException {
        System.out.print("Titre du livre: ");
        String titre = scanner.nextLine();
        System.out.print("Auteur du livre: ");
        String auteur = scanner.nextLine();
        
        Book book = new Book(titre, auteur);
        bookDAO.addBook(book);
        System.out.println("Livre ajouté avec succès!");
    }

    private static void listerLivres() throws SQLException {
        List<Book> books = bookDAO.getAllBooks();
        System.out.println("=== Liste des livres ===");
        for (Book book : books) {
            System.out.println("ID: " + book.getId() + " | Titre: " + book.getTitle() + " | Auteur: " + book.getAuthor());
        }
    }

    private static void supprimerLivre() throws SQLException {
        System.out.print("ID du livre à supprimer: ");
        int id = scanner.nextInt();
        bookDAO.deleteBook(id);
        System.out.println("Livre supprimé avec succès!");
    }

    private static void rechercherLivre() throws SQLException {
        System.out.print("Entrez le critère de recherche (titre ou auteur): ");
        String critere = scanner.nextLine();
    
        // Appel de la méthode rechercherLivre() du DAO
        List<Book> livresTrouves = bookDAO.rechercherLivre(critere);
    
        // Affichage des résultats
        if (livresTrouves.isEmpty()) {
            System.out.println("Aucun livre trouvé correspondant au critère.");
        } else {
            System.out.println("=== Livres trouvés ===");
            for (Book livre : livresTrouves) {
                System.out.println("ID: " + livre.getId() + " | Titre: " + livre.getTitle() + " | Auteur: " + livre.getAuthor());
            }
        }
    }
    

    // Menu pour la gestion des membres
    private static void menuGestionMembres() throws SQLException {
        System.out.println("\n=== Gestion des Membres ===");
        System.out.println("1. Ajouter un membre");
        System.out.println("2. Lister les membres");
        System.out.println("3. Supprimer un membre");
        System.out.print("Choisissez une option: ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consomme la ligne restante
        switch (choix) {
            case 1:
                ajouterMembre();
                break;
            case 2:
                listerMembres();
                break;
            case 3:
                supprimerMembre();
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private static void ajouterMembre() throws SQLException {
        System.out.print("Nom du membre: ");
        String nom = scanner.nextLine();
        System.out.print("Email du membre: ");
        String email = scanner.nextLine();
        
        Membre membre = new Membre(nom, email);
        membreDAO.addMembre(membre);
        System.out.println("Membre ajouté avec succès!");
    }

    private static void listerMembres() throws SQLException {
        List<Membre> membres = membreDAO.getAllMembres();
        System.out.println("=== Liste des membres ===");
        for (Membre membre : membres) {
            System.out.println("ID: " + membre.getId() + " | Nom: " + membre.getNom() + " | Email: " + membre.getEmail());
        }
    }

    private static void supprimerMembre() throws SQLException {
        System.out.print("ID du membre à supprimer: ");
        int id = scanner.nextInt();
        membreDAO.deleteMembre(id);
        System.out.println("Membre supprimé avec succès!");
    }

    // Menu pour la gestion des prêts
    private static void menuGestionPrets() throws SQLException {
        System.out.println("\n=== Gestion des Prêts ===");
        System.out.println("1. Ajouter un prêt");
        System.out.println("2. Lister les prêts");
        System.out.println("3. Supprimer un prêt");
        System.out.print("Choisissez une option: ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consomme la ligne restante
        switch (choix) {
            case 1:
                ajouterPret();
                break;
            case 2:
                listerPrets();
                break;
            case 3:
                supprimerPret();
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private static void ajouterPret() throws SQLException {
        System.out.print("ID du membre: ");
        int membreId = scanner.nextInt();
        System.out.print("ID du livre: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consomme la ligne restante
        System.out.print("Date du prêt (format YYYY-MM-DD): ");
        String datePret = scanner.nextLine();
        
        Pret pret = new Pret(membreId, bookId, datePret);
        pretDAO.addPret(pret);
        System.out.println("Prêt ajouté avec succès!");
    }

    private static void listerPrets() throws SQLException {
        List<Pret> prets = pretDAO.getAllPrets();
        System.out.println("=== Liste des prêts ===");
        for (Pret pret : prets) {
            System.out.println("ID Prêt: " + pret.getId() + " | Membre ID: " + pret.getMembreId() + " | Livre ID: " + pret.getBookId() + " | Date: " + pret.getDatePret());
        }
    }

    private static void supprimerPret() throws SQLException {
        System.out.print("ID du prêt à supprimer: ");
        int id = scanner.nextInt();
        pretDAO.deletePret(id);
        System.out.println("Prêt supprimé avec succès!");
    }
}
