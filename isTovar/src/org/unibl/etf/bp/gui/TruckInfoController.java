package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import application.DatabaseConnection;
import org.unibl.etf.bp.type.Session;

import java.sql.*;

public class TruckInfoController {

    @FXML private Label typeLabel;
    @FXML private Label brandLabel;
    @FXML private Label hpLabel;
    @FXML private Label fuelLabel;
    @FXML private Label yearLabel;
    @FXML private Label licenseLabel;
    @FXML private Label mileageLabel;
    @FXML private Button closeButton;

    @FXML
    public void initialize() {
        int driverId = Session.getLoggedUserId();  // Prethodno sačuvan ID vozača
        loadTruckInfo(driverId);
        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void loadTruckInfo(int driverId) {
        String query = """
                SELECT k.tip, k.marka, k.konjskeSnage, k.vrstaGoriva, 
                       k.godinaProizvodnje, k.registarskaOznaka, k.kilometraza
                FROM kamion k
                JOIN vozac v ON v.idKamiona = k.idKamiona
                WHERE v.idKorisnika = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                typeLabel.setText(rs.getString("tip"));
                brandLabel.setText(rs.getString("marka"));
                hpLabel.setText(rs.getString("konjskeSnage"));
                fuelLabel.setText(rs.getString("vrstaGoriva"));
                yearLabel.setText(rs.getString("godinaProizvodnje"));
                licenseLabel.setText(rs.getString("registarskaOznaka"));
                mileageLabel.setText(rs.getString("kilometraza") + " km");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
