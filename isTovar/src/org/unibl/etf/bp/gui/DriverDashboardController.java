package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DriverDashboardController {

    @FXML
    private Button logoutButton;

    @FXML
    private Button viewTruckButton;

    @FXML
    private Button viewTrailerButton;

    @FXML
    private Button reportIssueButton;
    
    @FXML
    private Button viewToursButton;
    
    @FXML
    private ImageView backgroundImage;

    @FXML
    public void initialize() {
    	Image image = new Image(getClass().getResource("/E09_truck_rt.jpg").toExternalForm());
        backgroundImage.setImage(image);
        logoutButton.setOnAction(event -> handleLogout());
        viewTruckButton.setOnAction(event -> showTruckInfo());
        viewTrailerButton.setOnAction(event -> showTrailerInfo());
        reportIssueButton.setOnAction(event -> reportProblem());
        viewToursButton.setOnAction(event -> showTours());
    }

    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Odjava izvršena.");
        alert.showAndWait();

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    private void showTruckInfo() {
        SceneLoader.popupScene("TruckInfo.fxml");
    }

    private void showTrailerInfo() {
    	SceneLoader.popupScene("TrailerInfo.fxml");
    }

    private void reportProblem() {
        SceneLoader.popupScene("ReportIssue.fxml");
    }
    
    private void showTours() {
        SceneLoader.popupScene("MyTours.fxml");
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }
}
