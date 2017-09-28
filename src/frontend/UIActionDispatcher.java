package frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class UIActionDispatcher {
	// Call methods of Initializer, CellManager & Rules
	// Can mock until Archana & Sam push their respective parts

	public static void displayFileNameInputDialog(String uploadText, String headerText, String contentText,
			String errorDialogTitleText, String errorDialogHeaderText, String errorDialogContentText) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(uploadText);
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(fileName -> validateFileAndWarnIfNotValid(fileName, errorDialogTitleText,
				errorDialogHeaderText, errorDialogContentText));
	}

	public static void validateFileAndWarnIfNotValid(String fileName, String errorDialogTitleText,
			String errorDialogHeaderText, String errorDialogContentText) {
		File file = new File(SimulationDisplay.XML_CONFIG_FOLDER + fileName);
		try {
			// TODO - better way of verifying if file exists than creating a dummy scanner like this
			Scanner in = new Scanner(file);
		} catch (FileNotFoundException e) {
			displayWarningDialog(errorDialogTitleText, errorDialogHeaderText, errorDialogContentText);
			return;
		}
	}

	public static void displayWarningDialog(String errorDialogTitle, String errorDialogHeaderText,
			String errorDialogContentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(errorDialogTitle);
		alert.setHeaderText(errorDialogHeaderText);
		alert.setContentText(errorDialogContentText);
		alert.showAndWait();
	}

}
