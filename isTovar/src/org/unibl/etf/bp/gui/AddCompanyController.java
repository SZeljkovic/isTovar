package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.DatabaseConnection;

import java.sql.*;

public class AddCompanyController {

    @FXML private TextField nazivField;
    @FXML private TextField emailField;
    @FXML private TextField ziroRacunField;
    @FXML private TextField ulicaField;
    @FXML private TextField brojField;
    @FXML private TextField gradField;
    @FXML private TextField postanskiBrojField;
    @FXML private TextField drzavaField;
    @FXML private TextField brojTelefonaField;
    @FXML private CheckBox faxCheckBox;
    @FXML private Button addButton;
    @FXML private Button clearButton;
    @FXML private Button closeButton;

    @FXML
    public void initialize() {
        addButton.setOnAction(e -> addCompany());
        clearButton.setOnAction(e -> clearForm());
        closeButton.setOnAction(e -> closeWindow());
    }

    private void addCompany() {
        String naziv = nazivField.getText();
        String email = emailField.getText();
        String ziroRacun = ziroRacunField.getText();
        String ulica = ulicaField.getText();
        String broj = brojField.getText();
        String grad = gradField.getText();
        String postanskiBroj = postanskiBrojField.getText();
        String drzava = drzavaField.getText();
        String brojTelefona = brojTelefonaField.getText();
        boolean fax = faxCheckBox.isSelected();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String insertFirmaSQL = "INSERT INTO firma (naziv, mejl, ziroRacun) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertFirmaSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, naziv);
                stmt.setString(2, email);
                stmt.setString(3, ziroRacun);
                stmt.executeUpdate();

                // Dobijanje ID firme
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int idFirme = rs.getInt(1);

                    String insertAdresaSQL = "INSERT INTO adresa (ulica, broj, grad, postanskiBroj, drzava, idFirme) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement adresaStmt = conn.prepareStatement(insertAdresaSQL)) {
                        adresaStmt.setString(1, ulica);
                        adresaStmt.setString(2, broj);
                        adresaStmt.setString(3, grad);
                        adresaStmt.setString(4, postanskiBroj);
                        adresaStmt.setString(5, drzava);
                        adresaStmt.setInt(6, idFirme);
                        adresaStmt.executeUpdate();
                    }

                    String insertTelefonSQL = "INSERT INTO telefon (brojTelefona, fax, idFirme) VALUES (?, ?, ?)";
                    try (PreparedStatement telefonStmt = conn.prepareStatement(insertTelefonSQL)) {
                        telefonStmt.setString(1, brojTelefona);
                        telefonStmt.setBoolean(2, fax);
                        telefonStmt.setInt(3, idFirme);
                        telefonStmt.executeUpdate();
                    }
                }
            }

            showAlert("Uspešno", "Firma je uspešno dodana!");
            clearForm();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Greška", "Došlo je do greške prilikom dodavanja firme.");
        }
    }

    private void clearForm() {
        nazivField.clear();
        emailField.clear();
        ziroRacunField.clear();
        ulicaField.clear();
        brojField.clear();
        gradField.clear();
        postanskiBrojField.clear();
        drzavaField.clear();
        brojTelefonaField.clear();
        faxCheckBox.setSelected(false);
    }

    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
