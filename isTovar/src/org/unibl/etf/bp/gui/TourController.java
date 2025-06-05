package org.unibl.etf.bp.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.unibl.etf.bp.type.Tour;

import application.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TourController {

    @FXML
    private TableView<Tour> toursTable;
    @FXML
    private TableColumn<Tour, Integer> idColumn;
    @FXML
    private TableColumn<Tour, LocalDateTime> departureTimeColumn;
    @FXML
    private TableColumn<Tour, LocalDateTime> arrivalTimeColumn;
    @FXML
    private TableColumn<Tour, String> statusColumn;
    @FXML
    private TableColumn<Tour, Integer> driverIdColumn;
    @FXML
    private TableColumn<Tour, Integer> dispatcherIdColumn;
    @FXML
    private TableColumn<Tour, Double> totalPriceColumn;
    @FXML
    private Button closeButton;

    private final ObservableList<Tour> tours = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadToursFromDatabase();

        idColumn.setCellValueFactory(data -> data.getValue().idTureProperty().asObject());
        departureTimeColumn.setCellValueFactory(data -> data.getValue().vrijemePolaskaProperty());
        arrivalTimeColumn.setCellValueFactory(data -> data.getValue().vrijemeDolaskaProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        driverIdColumn.setCellValueFactory(data -> data.getValue().idVozacaProperty().asObject());
        dispatcherIdColumn.setCellValueFactory(data -> data.getValue().idDispeceraProperty().asObject());
        totalPriceColumn.setCellValueFactory(data -> data.getValue().ukupnaCijenaTureProperty().asObject());

        toursTable.setItems(tours);

        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void loadToursFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = """
                    SELECT idTure, vrijemePolaska, vrijemeDolaska, status, idVozaca, idDispecera, ukupnaCijenaTure
                    FROM tura
                    """;

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // Pretvaramo datetime u LocalDateTime
                String departureTime = rs.getString("vrijemePolaska");
                String arrivalTime = rs.getString("vrijemeDolaska");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                tours.add(new Tour(
                        rs.getInt("idTure"),
                        LocalDateTime.parse(departureTime, formatter),
                        LocalDateTime.parse(arrivalTime, formatter),
                        rs.getString("status"),
                        rs.getInt("idVozaca"),
                        rs.getInt("idDispecera"),
                        rs.getDouble("ukupnaCijenaTure")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

