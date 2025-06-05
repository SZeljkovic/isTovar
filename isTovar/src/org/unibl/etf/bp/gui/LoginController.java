package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

import org.unibl.etf.bp.type.Session;

import application.DatabaseConnection;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private AnchorPane rootPane;
    @FXML private Label registerLabel;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        registerLabel.setOnMouseClicked(event -> {
            SceneLoader.popupScene("Registration.fxml", rootPane);
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Polja ne mogu biti prazna!");
            return;
        }

        String query = "SELECT * FROM korisnik WHERE korisnickoIme = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPasswordFromDB = rs.getString("lozinka");

                String enteredPasswordHashed = hashPasswordSHA256(password);
                if (enteredPasswordHashed == null) {
                    showAlert(AlertType.ERROR, "Greška pri hesiranju lozinke.");
                    return;
                }

                if (hashedPasswordFromDB.equals(enteredPasswordHashed)) {
                    int uloga = rs.getInt("uloga");

                    if (uloga == 0) {
                    	int id = rs.getInt("idKorisnika");
                        Session.setLoggedUserId(id); 
                        SceneLoader.popupScene("DispatcherDashboard.fxml", rootPane);
                    } else if (uloga == 1) {
                    	int id = rs.getInt("idKorisnika");
                        Session.setLoggedUserId(id);
                        SceneLoader.popupScene("DriverDashboard.fxml", rootPane);
                    } else {
                        showAlert(AlertType.ERROR, "Nepoznata uloga korisnika.");
                    }
                } else {
                    showAlert(AlertType.ERROR, "Pogrešna lozinka.");
                }

            } else {
                showAlert(AlertType.ERROR, "Korisnik ne postoji.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Greška prilikom pristupa bazi podataka.");
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
        alert.setTitle("Login status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
