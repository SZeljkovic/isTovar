package org.unibl.etf.bp.gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import application.DatabaseConnection;
import org.unibl.etf.bp.type.Session;

import java.sql.*;

public class TrailerInfoController {

    @FXML private Label typeLabel;
    @FXML private Label capacityLabel;
    @FXML private Label yearLabel;
    @FXML private Label licenseLabel;
    @FXML private Button closeButton;

    @FXML
    public void initialize() {
        int driverId = Session.getLoggedUserId();  // ID korisnika iz sesije
        loadTrailerInfo(driverId);
        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void loadTrailerInfo(int driverId) {
        String query = """
            SELECT p.vrsta, p.nosivost, p.godinaProizvodnje, p.registarskaOznaka
			FROM prikolica p
			JOIN kamion k ON k.idPrikolice = p.idPrikolice
			JOIN vozac v ON v.idKamiona = k.idKamiona
			WHERE v.idKorisnika = ?

        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                typeLabel.setText(rs.getString("vrsta"));
                capacityLabel.setText(rs.getString("nosivost") + " kg");
                yearLabel.setText(rs.getString("godinaProizvodnje"));
                licenseLabel.setText(rs.getString("registarskaOznaka"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

