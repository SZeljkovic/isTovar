package org.unibl.etf.bp.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.unibl.etf.bp.type.Driver;
import application.DatabaseConnection;

import java.sql.*;

public class DriverSelectionDialog {

    private TableView<Driver> driversTable;
    private TableColumn<Driver, Integer> idColumn;
    private TableColumn<Driver, String> nameColumn;
    private TableColumn<Driver, String> surnameColumn;

    private ObservableList<Driver> availableDrivers = FXCollections.observableArrayList();
    private Driver selectedDriver;
    private Stage dialog;  

    public DriverSelectionDialog() {
        driversTable = new TableView<>(); 

        idColumn = new TableColumn<>("ID Vozača");
        nameColumn = new TableColumn<>("Ime");
        surnameColumn = new TableColumn<>("Prezime");

        
        idColumn.setCellValueFactory(data -> data.getValue().idKorisnikaProperty().asObject());
        nameColumn.setCellValueFactory(data -> data.getValue().imeProperty());
        surnameColumn.setCellValueFactory(data -> data.getValue().prezimeProperty());

        
        driversTable.getColumns().addAll(idColumn, nameColumn, surnameColumn);

        loadDriversFromDatabase(); 
    }

    
    public void showAndWait() {
        dialog = new Stage(); 
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Izbor Vozača");

        VBox vbox = new VBox(driversTable);  
        Scene scene = new Scene(vbox, 500, 400);
        dialog.setScene(scene);

        
        driversTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                selectedDriver = driversTable.getSelectionModel().getSelectedItem();
                if (selectedDriver != null) {
                    dialog.close(); 
                }
            }
        });

        dialog.showAndWait(); 
    }

    
    private void loadDriversFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT k.idKorisnika, k.korisnickoIme, k.ime, k.prezime, k.email, k.brojTelefona,\r\n"
            		+ "                           v.brojDozvole, v.licenca, v.idKamiona, v.dostupnost\r\n"
            		+ "                    FROM korisnik k\r\n"
            		+ "                    JOIN vozac v ON k.idKorisnika = v.idKorisnika";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                
                Driver driver = new Driver(
                        rs.getInt("idKorisnika"),
                        rs.getString("korisnickoIme"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("email"),
                        rs.getString("brojTelefona"),
                        rs.getString("brojDozvole"),
                        rs.getString("licenca"),
                        rs.getInt("idKamiona"),
                        rs.getString("dostupnost")
                );
                availableDrivers.add(driver); 
            }

            
            driversTable.setItems(availableDrivers);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }
}
