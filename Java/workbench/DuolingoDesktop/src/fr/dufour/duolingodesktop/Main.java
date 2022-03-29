package fr.dufour.duolingodesktop;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("GUI.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			//scene.getStylesheets().add(" WindowStyle.css");
			primaryStage.setScene(scene);

			//primaryStage.setTitle("Duolingo Desktop");
			primaryStage.setResizable(true);
			primaryStage.show();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}