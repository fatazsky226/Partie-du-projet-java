#include <Wire.h>
#include <LiquidCrystal_I2C.h>


// Initialiser l'écran LCD avec l'adresse I2C par défaut (0x27)
LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup() {
  // Démarrer la communication série
  Serial.begin(9600);
  
  // Initialiser l'écran LCD
  lcd.begin(16, 2);
  lcd.setBacklight(LOW);  // Éteindre le rétroéclairage (optionnel)

  // Afficher un message initial
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Chargement...");
}

void loop() {
  // Vérifier si des données sont disponibles sur le port série
  if (Serial.available() > 0) {
    String message = Serial.readString();  // Lire la chaîne envoyée par Java

    // Effacer l'écran LCD et afficher le message reçu
    lcd.clear();
    lcd.setCursor(0, 0);  // Positionner le curseur à la première ligne
    lcd.print("Livres Disponibles");
    lcd.setCursor(0, 1);  // Positionner le curseur à la deuxième ligne
    lcd.print(message);  // Afficher le message reçu (par exemple "Books Available: 12")
    Serial.println(message);
    Serial.println("lire....");
  }
}
