package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.unibl.etf.bp.type.Tura;
import org.unibl.etf.bp.type.Session;

import application.DatabaseConnection;

import java.sql.*;


public class MyToursController {

    @FXML
    private VBox cardsContainer;

    @FXML
    private Button closeButton;

    @FXML
    public void initialize() {
        closeButton.setOnAction(event -> handleLogout());
        loadToursAsCards();
    }

    private void handleLogout() {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void loadToursAsCards() {
        int driverId = Session.getLoggedUserId();
        String sql = "SELECT * FROM tura WHERE idVozaca = ? ORDER BY vrijemePolaska DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tura tura = new Tura(
                        rs.getInt("idTure"),
                        rs.getTimestamp("vrijemePolaska"),
                        rs.getTimestamp("vrijemeDolaska"),
                        rs.getString("status"),
                        rs.getDouble("ukupnaCijenaTure")
                );
                VBox card = createCard(tura);
                cardsContainer.getChildren().add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Greška prilikom učitavanja podataka.");
            alert.showAndWait();
        }
    }

    private VBox createCard(Tura tura) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);");
        card.setPadding(new Insets(15));
        card.setMaxWidth(720);
        card.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Tura #" + tura.getIdTureProperty().get());
        title.setFont(Font.font("Arial", 18));
        title.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        Label departure = new Label("Polazak: " + tura.getDepartureTimeProperty().get().toString());
        Label arrival = new Label("Dolazak: " + tura.getArrivalTimeProperty().get().toString());
        Label status = new Label("Status: " + tura.getStatusProperty().get());
        Label price = new Label("Cijena: " + tura.getPriceProperty().get() + " KM");

        departure.setStyle("-fx-text-fill: #34495e;");
        arrival.setStyle("-fx-text-fill: #34495e;");
        status.setStyle("-fx-text-fill: #16a085;");
        price.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold;");

        card.getChildren().addAll(title, departure, arrival, status, price);
        Button detailsButton = new Button("Detalji");
        detailsButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-weight: bold;");
        detailsButton.setOnAction(e -> {
            TourDetailsController.setSelectedTuraId(tura.getIdTureProperty().get());
            SceneLoader.popupScene("TourDetails.fxml");
        });

        HBox buttonBox = new HBox(detailsButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().add(buttonBox);

        return card;
    }
}

