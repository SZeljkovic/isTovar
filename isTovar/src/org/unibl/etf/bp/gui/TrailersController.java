package org.unibl.etf.bp.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.unibl.etf.bp.type.Trailer;
import application.DatabaseConnection;

import java.sql.*;

public class TrailersController {

    @FXML private TableView<Trailer> trailersTable;
    @FXML private TableColumn<Trailer, Integer> idColumn;
    @FXML private TableColumn<Trailer, String> vrstaColumn;
    @FXML private TableColumn<Trailer, Integer> nosivostColumn;
    @FXML private TableColumn<Trailer, Integer> yearColumn;
    @FXML private TableColumn<Trailer, String> licensePlateColumn;
    @FXML private Button closeButton;

    private final ObservableList<Trailer> trailers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadTrailersFromDatabase();

        idColumn.setCellValueFactory(data -> data.getValue().idPrikoliceProperty().asObject());
        vrstaColumn.setCellValueFactory(data -> data.getValue().vrstaProperty());
        nosivostColumn.setCellValueFactory(data -> data.getValue().nosivostProperty().asObject());
        yearColumn.setCellValueFactory(data -> data.getValue().godinaProizvodnjeProperty().asObject());
        licensePlateColumn.setCellValueFactory(data -> data.getValue().registarskaOznakaProperty());

        trailersTable.setItems(trailers);

        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void loadTrailersFromDatabase() {
        String sql = "SELECT idPrikolice, vrsta, nosivost, godinaProizvodnje, registarskaOznaka FROM prikolica";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                trailers.add(new Trailer(
                        rs.getInt("idPrikolice"),
                        rs.getString("vrsta"),
                        rs.getInt("nosivost"),
                        rs.getInt("godinaProizvodnje"),
                        rs.getString("registarskaOznaka")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
