package org.unibl.etf.bp.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DispatcherDashboardController {

    @FXML
    private Button logoutButton;

    @FXML
    private Button driversButton;

    @FXML
    private Button trucksButton;

    @FXML
    private Button trailersButton;
    
    @FXML
    private Button issuesButton;
    
    @FXML
    private Button toursButton;
    
    @FXML
    private Button addTourButton;
    
    @FXML
    private ImageView backgroundImage;
    
    @FXML
    private Button addCompanyButton;


    @FXML
    public void initialize() {
    	Image image = new Image(getClass().getResource("/DispatcherImg.jpg").toExternalForm());
        backgroundImage.setImage(image);
        logoutButton.setOnAction(event -> handleLogout());
        driversButton.setOnAction(event -> openDrivers());
        trucksButton.setOnAction(event -> openTrucks());
        trailersButton.setOnAction(event -> openTrailers());
        issuesButton.setOnAction(event -> showIssues());
        toursButton.setOnAction(event -> showTours());
        addTourButton.setOnAction(event -> addTour());
        addCompanyButton.setOnAction(event -> addCompany());
    }

    private void handleLogout() {
        Alert alert = new Alert(AlertType.INFORMATION, "Odjava izvršena.");
        alert.showAndWait();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    private void openDrivers() {
        SceneLoader.popupScene("Drivers.fxml");
        showInfo("Otvorena lista vozača.");
    }

    private void openTrucks() {
        SceneLoader.popupScene("Trucks.fxml");
        showInfo("Otvorena lista kamiona.");
    }

    private void openTrailers() {
        SceneLoader.popupScene("Trailers.fxml");
        showInfo("Otvorena lista prikolica.");
    }
    
    private void showIssues() {
        SceneLoader.popupScene("IssueList.fxml");
    }
    
    private void showTours() {
        SceneLoader.popupScene("Tour.fxml");
    }
    
    private void addTour() {
    	SceneLoader.popupScene("AddTour.fxml");
    }
    
    private void addCompany() {
    	SceneLoader.popupScene("AddCompany.fxml");
    }


    private void showInfo(String message) {
        Alert alert = new Alert(AlertType.INFORMATION, message);
        alert.showAndWait();
    }
}
