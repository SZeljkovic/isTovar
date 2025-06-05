package org.unibl.etf.bp.gui;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.DatabaseConnection;
import org.unibl.etf.bp.type.Session;

import java.sql.*;
import java.time.LocalDate;

public class ReportIssueController {

    @FXML private TextArea problemTextArea;
    @FXML private Button submitButton;
    @FXML private Button closeButton;

    @FXML
    public void initialize() {
        submitButton.setOnAction(e -> handleSubmit());
        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void handleSubmit() {
        String text = problemTextArea.getText().trim();

        if (text.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Polje za opis problema ne smije biti prazno.");
            return;
        }

        String query = "INSERT INTO problem (idKorisnika, tekstProblema, datum, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Session.getLoggedUserId());
            stmt.setString(2, text);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setInt(4, 0); // Status 0 = nerijesen

            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Problem je uspješno prijavljen.");
            problemTextArea.clear();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Došlo je do greške pri slanju prijave.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Prijava Problema");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

