package org.unibl.etf.bp.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// Klasa koja sadrzi metode za prelazak na druge scene.
 
public class SceneLoader {
	
	
	 //Ova metoda otvara novu scenu u novom prozoru (Stage).
	
	public static void popupScene(String fxmlFileName, AnchorPane pane) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(fxmlFileName));
            Parent root = loader.load();
            
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	// Metoda kojom se prelazi na novu scenu, ali ona ne otvara u novom prozoru.
	 
	public static void loadScene(String fxmlFileName, AnchorPane pane) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(fxmlFileName));
            Parent root = loader.load();
            pane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void popupScene(String fxmlFileName) {
	    try {
	        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(fxmlFileName));
	        Parent root = loader.load();

	        Stage newStage = new Stage();
	        Scene scene = new Scene(root);
	        newStage.setScene(scene);
	        newStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}

