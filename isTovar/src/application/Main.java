package application;
	

import java.sql.SQLException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/unibl/etf/bp/gui/Login.fxml"));
	        Parent root = loader.load();

	        Scene scene = new Scene(root);
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Login");
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		
		
		launch(args);
	}
}
