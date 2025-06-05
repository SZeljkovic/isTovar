package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.unibl.etf.bp.type.Driver;
import org.unibl.etf.bp.type.Session;
import application.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddTourController {

    @FXML private DatePicker departureDatePicker;
    @FXML private Spinner<Integer> departureHourSpinner;
    @FXML private Spinner<Integer> departureMinuteSpinner;

    @FXML private DatePicker arrivalDatePicker;
    @FXML private Spinner<Integer> arrivalHourSpinner;
    @FXML private Spinner<Integer> arrivalMinuteSpinner;

    @FXML private TextField statusField;
    @FXML private TextField totalPriceField;

    @FXML private Button driverSelectionButton;
    @FXML private Button submitButton;
    @FXML private Label driverInfoLabel;
    @FXML private Button closeButton;

    private int selectedDriverId;
    private int dispatcherId = Session.getLoggedUserId();

    @FXML
    public void initialize() {
        initSpinner(departureHourSpinner, 0, 23);
        initSpinner(departureMinuteSpinner, 0, 59);
        initSpinner(arrivalHourSpinner, 0, 23);
        initSpinner(arrivalMinuteSpinner, 0, 59);

        driverSelectionButton.setOnAction(event -> openDriverSelectionDialog());
        submitButton.setOnAction(event -> saveTour());
        closeButton.setOnAction(event -> closeWindow());
    }

    private void initSpinner(Spinner<Integer> spinner, int min, int max) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, 0));
        spinner.setEditable(true);
    }

    private void openDriverSelectionDialog() {
        DriverSelectionDialog dialog = new DriverSelectionDialog();
        dialog.showAndWait();
        Driver selectedDriver = dialog.getSelectedDriver();
        if (selectedDriver != null) {
            selectedDriverId = selectedDriver.getIdKorisnika();
            driverInfoLabel.setText("Izabrani vozač: " + selectedDriver.getIme() + " " + selectedDriver.getPrezime());
        }
    }

    private void saveTour() {
        try {
            LocalDate depDate = departureDatePicker.getValue();
            LocalTime depTime = LocalTime.of(departureHourSpinner.getValue(), departureMinuteSpinner.getValue());
            LocalDateTime departureDateTime = LocalDateTime.of(depDate, depTime);

            LocalDate arrDate = arrivalDatePicker.getValue();
            LocalTime arrTime = LocalTime.of(arrivalHourSpinner.getValue(), arrivalMinuteSpinner.getValue());
            LocalDateTime arrivalDateTime = LocalDateTime.of(arrDate, arrTime);

            String status = statusField.getText();
            double totalPrice = Double.parseDouble(totalPriceField.getText());

            // Provjera dostupnosti vozača
            boolean isDriverAvailable = checkDriverAvailability(selectedDriverId, departureDateTime, arrivalDateTime);
            if (!isDriverAvailable) {
                showAlert(Alert.AlertType.WARNING, "Vozač je već zauzet u tom vremenskom periodu.");
                return;
            }

            // Insert ako je vozač slobodan
            String insertQuery = "INSERT INTO tura (vrijemePolaska, vrijemeDolaska, status, idVozaca, idDispecera, ukupnaCijenaTure) " +
                                 "VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

                stmt.setTimestamp(1, Timestamp.valueOf(departureDateTime));
                stmt.setTimestamp(2, Timestamp.valueOf(arrivalDateTime));
                stmt.setString(3, status);
                stmt.setInt(4, selectedDriverId);
                stmt.setInt(5, dispatcherId);
                stmt.setDouble(6, totalPrice);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Tura je uspešno dodata!");
                    closeWindow();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Došlo je do greške prilikom dodavanja ture.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Neispravni podaci ili greška u bazi.");
        }
    }

    // Metoda koja poziva MySQL proceduru i vraća true ako je vozač slobodan
    private boolean checkDriverAvailability(int driverId, LocalDateTime start, LocalDateTime end) {
        String call = "{ call ProvjeriDostupnostVozaca(?, ?, ?, ?) }";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(call)) {

            stmt.setInt(1, driverId);
            stmt.setTimestamp(2, Timestamp.valueOf(start));
            stmt.setTimestamp(3, Timestamp.valueOf(end));
            stmt.registerOutParameter(4, Types.TINYINT);

            stmt.execute();

            int result = stmt.getInt(4);
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Greška prilikom provjere dostupnosti vozača.");
            return false;
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Obavještenje");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
