package org.unibl.etf.bp.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.unibl.etf.bp.type.Driver;

import application.DatabaseConnection;

import java.sql.*;

public class DriversController {

    @FXML
    private TableView<Driver> driversTable;
    @FXML
    private TableColumn<Driver, Integer> idColumn;
    @FXML
    private TableColumn<Driver, String> usernameColumn;
    @FXML
    private TableColumn<Driver, String> nameColumn;
    @FXML
    private TableColumn<Driver, String> surnameColumn;
    @FXML
    private TableColumn<Driver, String> emailColumn;
    @FXML
    private TableColumn<Driver, String> phoneColumn;
    @FXML
    private TableColumn<Driver, String> licenseNumberColumn;
    @FXML
    private TableColumn<Driver, String> licenseColumn;
    @FXML
    private TableColumn<Driver, Integer> truckIdColumn;
    @FXML
    private TableColumn<Driver, String> availabilityColumn;
    @FXML
    private Button closeButton;

    private final ObservableList<Driver> drivers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadDriversFromDatabase();

        idColumn.setCellValueFactory(data -> data.getValue().idKorisnikaProperty().asObject());
        usernameColumn.setCellValueFactory(data -> data.getValue().korisnickoImeProperty());
        nameColumn.setCellValueFactory(data -> data.getValue().imeProperty());
        surnameColumn.setCellValueFactory(data -> data.getValue().prezimeProperty());
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());
        phoneColumn.setCellValueFactory(data -> data.getValue().brojTelefonaProperty());
        licenseNumberColumn.setCellValueFactory(data -> data.getValue().brojDozvoleProperty());
        licenseColumn.setCellValueFactory(data -> data.getValue().licencaProperty());
        truckIdColumn.setCellValueFactory(data -> data.getValue().idKamionaProperty().asObject());
        availabilityColumn.setCellValueFactory(data -> data.getValue().dostupnostProperty());

        driversTable.setItems(drivers);

        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void loadDriversFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = """
                    SELECT k.idKorisnika, k.korisnickoIme, k.ime, k.prezime, k.email, k.brojTelefona,
                           v.brojDozvole, v.licenca, v.idKamiona, v.dostupnost
                    FROM korisnik k
                    JOIN vozac v ON k.idKorisnika = v.idKorisnika
                    """;

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                drivers.add(new Driver(
                        rs.getInt("idKorisnika"),
                        rs.getString("korisnickoIme"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("email"),
                        rs.getString("brojTelefona"),
                        rs.getString("brojDozvole"),
                        rs.getString("licenca"),
                        rs.getObject("idKamiona") != null ? rs.getInt("idKamiona") : null,
                        rs.getString("dostupnost")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

