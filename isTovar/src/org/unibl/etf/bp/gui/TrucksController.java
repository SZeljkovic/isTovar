package org.unibl.etf.bp.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.unibl.etf.bp.type.Truck;
import application.DatabaseConnection;

import java.sql.*;

public class TrucksController {

    @FXML private TableView<Truck> trucksTable;
    @FXML private TableColumn<Truck, Integer> idColumn;
    @FXML private TableColumn<Truck, String> typeColumn;
    @FXML private TableColumn<Truck, String> brandColumn;
    @FXML private TableColumn<Truck, Integer> hpColumn;
    @FXML private TableColumn<Truck, Integer> trailerIdColumn;
    @FXML private TableColumn<Truck, String> fuelColumn;
    @FXML private TableColumn<Truck, Integer> yearColumn;
    @FXML private TableColumn<Truck, String> licensePlateColumn;
    @FXML private TableColumn<Truck, Integer> mileageColumn;
    @FXML private Button closeButton;

    private final ObservableList<Truck> trucks = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadTrucksFromDatabase();

        idColumn.setCellValueFactory(data -> data.getValue().idKamionaProperty().asObject());
        typeColumn.setCellValueFactory(data -> data.getValue().tipProperty());
        brandColumn.setCellValueFactory(data -> data.getValue().markaProperty());
        hpColumn.setCellValueFactory(data -> data.getValue().konjskeSnageProperty().asObject());
        trailerIdColumn.setCellValueFactory(data -> data.getValue().idPrikoliceProperty().asObject());
        fuelColumn.setCellValueFactory(data -> data.getValue().vrstaGorivaProperty());
        yearColumn.setCellValueFactory(data -> data.getValue().godinaProizvodnjeProperty().asObject());
        licensePlateColumn.setCellValueFactory(data -> data.getValue().registarskaOznakaProperty());
        mileageColumn.setCellValueFactory(data -> data.getValue().kilometrazaProperty().asObject());

        trucksTable.setItems(trucks);

        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void loadTrucksFromDatabase() {
        String sql = """
                SELECT idKamiona, tip, marka, konjskeSnage, idPrikolice, vrstaGoriva,
                       godinaProizvodnje, registarskaOznaka, kilometraza
                FROM kamion
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                trucks.add(new Truck(
                        rs.getInt("idKamiona"),
                        rs.getString("tip"),
                        rs.getString("marka"),
                        rs.getInt("konjskeSnage"),
                        rs.getObject("idPrikolice") != null ? rs.getInt("idPrikolice") : null,
                        rs.getString("vrstaGoriva"),
                        rs.getInt("godinaProizvodnje"),
                        rs.getString("registarskaOznaka"),
                        rs.getInt("kilometraza")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

