package org.unibl.etf.bp.gui;

import application.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.util.*;

public class IssueListController {

    @FXML private TableView<Map<String, String>> issuesTable;
    @FXML private TableColumn<Map<String, String>, String> idColumn;
    @FXML private TableColumn<Map<String, String>, String> userIdColumn;
    @FXML private TableColumn<Map<String, String>, String> textColumn;
    @FXML private TableColumn<Map<String, String>, String> dateColumn;
    @FXML private TableColumn<Map<String, String>, String> statusColumn;

    @FXML private Button closeButton;
    @FXML private Button resolveButton;

    private String selectedProblemId = null;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("id")));
        userIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("userId")));
        textColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("text")));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("date")));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("status")));

        loadIssues();

        closeButton.setOnAction(e -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });

        // Listener za selekciju reda
        issuesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !"Riješen".equals(newVal.get("status"))) {
                selectedProblemId = newVal.get("id");
                resolveButton.setVisible(true);
            } else {
                resolveButton.setVisible(false);
                selectedProblemId = null;
            }
        });

        resolveButton.setOnAction(e -> resolveIssue());
    }

    private void loadIssues() {
        issuesTable.getItems().clear();

        String query = "SELECT idProblem, idKorisnika, tekstProblema, datum, status FROM problem";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("id", String.valueOf(rs.getInt("idProblem")));
                row.put("userId", String.valueOf(rs.getInt("idKorisnika")));
                row.put("text", rs.getString("tekstProblema"));
                row.put("date", rs.getDate("datum").toString());
                row.put("status", rs.getInt("status") == 1 ? "Riješen" : "Neriješen");
                issuesTable.getItems().add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resolveIssue() {
        if (selectedProblemId == null) return;

        String updateQuery = "UPDATE problem SET status = 1 WHERE idProblem = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setInt(1, Integer.parseInt(selectedProblemId));
            stmt.executeUpdate();

            loadIssues();
            resolveButton.setVisible(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
