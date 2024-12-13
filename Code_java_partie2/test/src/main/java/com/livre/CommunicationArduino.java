package com.livre;

import com.fazecast.jSerialComm.SerialPort;
import java.sql.*;

public class CommunicationArduino {
    private static final String PORT = "COM14"; // Remplace par ton port série (ex : COM3 sous Windows ou /dev/ttyUSB0 sous Linux)
    private static final int BAUD_RATE = 9600;

    public static void main(String[] args) throws Exception {
        // Créer une instance de BookDAO pour récupérer les données
        BookDAO bookDAO = new BookDAO();
        int availableBooks = bookDAO.getAvailableBooks(); // Appeler la méthode pour obtenir les livres disponibles

        // Ouvrir la communication série
        SerialPort serialPort = SerialPort.getCommPort(PORT);
        serialPort.setBaudRate(BAUD_RATE);
        
        if (serialPort.openPort()) {
            System.out.println("Communication série ouverte");

            // Envoyer le message à l'Arduino
            String message = "livre dispo: " + availableBooks;
            serialPort.getOutputStream().write(message.getBytes());
            System.out.println("Message envoyé: " + message);

            // Fermer la communication après l'envoi
            serialPort.closePort();
        } else {
            System.out.println("Erreur lors de l'ouverture du port série");
        }
    }
}
