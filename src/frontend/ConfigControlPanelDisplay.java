package frontend;

import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import middleware.CurrentParameters;
import middleware.Initializer;

public class ConfigControlPanelDisplay {

	public static final double CONFIG_HORIZONTAL_SPACING = 5.0;
	private UITextReader reader;

	public ConfigControlPanelDisplay(UITextReader reader) {
		this.reader = reader;
	}

	public Node getConfigControlPanel() {
		VBox configControlPanel = new VBox();
		HBox titleBox = new HBox();
		titleBox.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(new Text(reader.getConfigChangePanelTitleText()));
		configControlPanel.getChildren().add(titleBox);
		CurrentParameters cp = Initializer.getCurrentParameters();
		Map<String, String> globalParams = cp.getGameLevelParameterMap();
		for (String param : globalParams.keySet()) {
			HBox paramContainer = new HBox();
			paramContainer.getChildren().add(new Label(param));
			TextField paramVal = new TextField();
			paramVal.setPromptText(globalParams.get(param));
			paramContainer.getChildren().add(paramVal);
			Button changeButton = new Button(reader.getConfigChangeText());
			// TODO - Differentiate between numeric & non-numeric input boxes
			// TODO - Use UIActionDispatcher to return warning dialog where necessary
			// TODO - Use UIActionDispatcher to return success dialog where necessary
			changeButton.setOnMouseClicked(e -> { 
				if (isValidConfig(param, paramVal.getText())) {
					cp.setGameLevelParameter(param, paramVal.getText());	
				} else {
					// TODO
					//displayNonNumericalInputWarningDialog();
				}
			});
			paramContainer.getChildren().add(changeButton);
			paramContainer.setSpacing(CONFIG_HORIZONTAL_SPACING);
			configControlPanel.getChildren().add(paramContainer);
		}
		return configControlPanel;
	}

	// TODO - Validate new config input
	private boolean isValidConfig(String param, String paramVal) {
		// TEMP
		return false;
	}
	
	// TODO - for generic non numerical input error
	public void displayNonNumericalInputWarningDialog() {
		UIActionDispatcher.displayWarningDialog(reader.getNonNumericalInputDialogTitleText(),
				reader.getNonNumericalInputDialogHeaderText(), reader.getNonNumericalInputDialogContentText());
	}


}
