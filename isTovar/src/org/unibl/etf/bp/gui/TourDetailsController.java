package org.unibl.etf.bp.gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import application.DatabaseConnection;

import java.sql.*;

public class TourDetailsController {

    private static int selectedTuraId;

    @FXML
    private VBox sourceCompaniesContainer;

    @FXML
    private VBox destinationCompaniesContainer;

    @FXML
    private Button backButton;

    public static void setSelectedTuraId(int id) {
        selectedTuraId = id;
    }

    @FXML
    public void initialize() {
        backButton.setOnAction(e -> ((Stage) backButton.getScene().getWindow()).close());
        loadTourDetails();
    }

    private void loadTourDetails() {
        loadCompanies(sourceCompaniesContainer, "tura_firma_izvorista", "vrijemeUtovara");
        loadCompanies(destinationCompaniesContainer, "tura_firma_odredista", "vrijemeIstovara");
    }

    private void loadCompanies(VBox container, String mappingTable, String timeColumn) {
        String sql = """
            SELECT f.naziv, a.ulica, a.broj, a.grad, a.postanskiBroj, a.drzava, tf.%s
            FROM %s tf
            JOIN firma f ON tf.idFirme = f.idFirme
            JOIN adresa a ON f.idFirme = a.idFirme
            WHERE tf.idTure = ?
        """.formatted(timeColumn, mappingTable);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, selectedTuraId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VBox card = new VBox(5);
                card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");

                String naziv = rs.getString("naziv");
                String adresa = rs.getString("ulica") + " " + rs.getString("broj") + ", " + rs.getString("grad") +
                        " " + rs.getString("postanskiBroj") + ", " + rs.getString("drzava");
                Timestamp vrijeme = rs.getTimestamp(timeColumn);

                Label nameLabel = new Label("Firma: " + naziv);
                Label addrLabel = new Label("Adresa: " + adresa);
                Label timeLabel = new Label("Vrijeme: " + vrijeme.toString());

                nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
                addrLabel.setStyle("-fx-text-fill: #34495e;");
                timeLabel.setStyle("-fx-text-fill: #27ae60;");

                card.getChildren().addAll(nameLabel, addrLabel, timeLabel);
                container.getChildren().add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

