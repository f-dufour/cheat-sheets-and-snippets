package com.whatisjavafx;

import java.io.File;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private Circle dropZone;

	@FXML
	private Label labelPath, statusLabel;

	@FXML
	private Button exportBtn, chooseFolderButton;

	@FXML
	private ListView<String> listFileNames;
	
	@FXML
	private Tooltip tooltipExport, tooltipChooseFolder;
	
	@FXML
	void dragEntering(DragEvent event) {
		System.out.println("Entering");
		statusLabel.setText("Entering");
	}
	
	private Stage mainStage;

	@FXML
	void draggingOver(DragEvent event) {
		Dragboard board = event.getDragboard();
		if (board.hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
			dropZone.setStyle("-fx-stroke: pink;");
			System.out.println("DraggingOver");
			statusLabel.setText("DraggingOver");
		}
	}

	@FXML
	void dropping(DragEvent event) {
		Dragboard board = event.getDragboard();
		List<File> list = board.getFiles();
		System.out.println("Dropping");
		statusLabel.setText("Dropping");
		dropZone.setStyle("-fx-stroke: #dadada;");
		for (File f : list) {
			System.out.println(f.getAbsolutePath());
			labelPath.setText(f.getAbsolutePath());
		}
	}

	@FXML
	void dragExiting(DragEvent event) {
		dropZone.setStyle("-fx-stroke: #dadada;");
	}
	
	@FXML
	void exportButtonListener() {
		String saveAs = getUserSavingDestination();
		System.out.println("exporting" + saveAs);
	}

	private String getUserSavingDestination() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		File file = fileChooser.showSaveDialog(mainStage);
		return file.getAbsolutePath();
	}

	
	void tooltipFired(String str) { 
		tooltipExport.setText("Hello" + str);
	}
	
	@FXML
	void tooltipExportFired() {
		tooltipFired("florent");	
	}
	
	@FXML
	void initialize() {
	}
}