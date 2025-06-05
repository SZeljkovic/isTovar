package org.unibl.etf.bp.gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import application.DatabaseConnection;

public class RegistrationController {

    @FXML private TextField imeField;
    @FXML private TextField prezimeField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private TextField brojTelefonaField;
    @FXML private ChoiceBox<String> ulogaChoiceBox;
    @FXML private Button registerButton;

    @FXML private TextField brojDozvoleField;
    @FXML private TextField licencaField;

    @FXML
    public void initialize() {
        ulogaChoiceBox.getItems().addAll("Dispečer", "Vozač");

        // Prikaz dodatnih polja ako je vozač
        ulogaChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean isVozac = "Vozač".equals(newVal);
            brojDozvoleField.setVisible(isVozac);
            licencaField.setVisible(isVozac);
        });

        registerButton.setOnAction(event -> handleRegistration());
    }

    private void handleRegistration() {
        String ime = imeField.getText();
        String prezime = prezimeField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String brojTelefona = brojTelefonaField.getText();
        String ulogaText = ulogaChoiceBox.getValue();

        if (ime == null || prezime == null || username == null || password == null ||
            email == null || brojTelefona == null || ulogaText == null ||
            ime.isEmpty() || prezime.isEmpty() || username.isEmpty() || password.isEmpty() ||
            email.isEmpty() || brojTelefona.isEmpty()) {
            showAlert(AlertType.WARNING, "Sva polja su obavezna!");
            return;
        }

        int uloga = ulogaText.equals("Dispečer") ? 0 : 1;

        // Ako je vozač, provjeri dodatna polja
        String brojDozvole = null;
        String licenca = null;
        if (uloga == 1) {
            brojDozvole = brojDozvoleField.getText();
            licenca = licencaField.getText();

            if (brojDozvole == null || brojDozvole.isEmpty() || licenca == null || licenca.isEmpty()) {
                showAlert(AlertType.WARNING, "Unesite broj dozvole i licencu za vozača.");
                return;
            }
        }

        String hashedPassword = hashPasswordSHA256(password);
        if (hashedPassword == null) {
            showAlert(AlertType.ERROR, "Greška pri hesiranju lozinke.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO korisnik (korisnickoIme, ime, prezime, email, lozinka, uloga, brojTelefona) VALUES (?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.setString(2, ime);
            stmt.setString(3, prezime);
            stmt.setString(4, email);
            stmt.setString(5, hashedPassword);
            stmt.setInt(6, uloga);
            stmt.setString(7, brojTelefona);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int korisnikId = rs.getInt(1);

                    if (uloga == 0) {
                        try (PreparedStatement dispecerStmt = conn.prepareStatement(
                                "INSERT INTO dispecer (idKorisnika, status) VALUES (?, ?)")) {
                            dispecerStmt.setInt(1, korisnikId);
                            dispecerStmt.setInt(2, 1); // Dostupan
                            dispecerStmt.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement vozacStmt = conn.prepareStatement(
                                "INSERT INTO vozac (idKorisnika, brojDozvole, licenca, dostupnost) VALUES (?, ?, ?, ?)")) {
                            vozacStmt.setInt(1, korisnikId);
                            vozacStmt.setString(2, brojDozvole);
                            vozacStmt.setString(3, licenca);
                            vozacStmt.setInt(4, 1); // Dostupan
                            vozacStmt.executeUpdate();
                        }
                    }

                    showAlert(AlertType.INFORMATION, "Registracija uspješna!");
                } else {
                    showAlert(AlertType.ERROR, "Greška pri dobijanju ID korisnika.");
                }
            } else {
                showAlert(AlertType.ERROR, "Registracija nije uspjela.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Greška pri radu sa bazom podataka.");
        }
    }

    private String hashPasswordSHA256(String password) {
        try {
            ProcessBuilder pb = new ProcessBuilder("openssl", "dgst", "-sha256");
            Process process = pb.start();

            process.getOutputStream().write(password.getBytes());
            process.getOutputStream().close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();
            if (output != null && output.contains("=")) {
                return output.split("=")[1].trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Status registracije");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
