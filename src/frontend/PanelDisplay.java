package frontend;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PanelDisplay {

	private Button toggleButton;
	private int buttonImageFitWidth;
	private int buttonImageFitHeight;

	public PanelDisplay(int buttonWidth, int buttonHeight) {
		this.buttonImageFitWidth = buttonWidth;
		this.buttonImageFitHeight = buttonHeight;
	}

	// TODO - Rewrite based on simulation UI instead of menu UI, include more images
	public Node initBottomPanel(String resumeImageURL, String forwardImageURL, String speedUpImageURL,
			String slowDownImageURL, EventHandler<ActionEvent> toggleHandler, EventHandler<ActionEvent> forwardHandler,
			EventHandler<ActionEvent> speedUpHandler, EventHandler<ActionEvent> slowDownHandler) {
		HBox container = new HBox();
		toggleButton = makeImageButton(resumeImageURL, buttonImageFitWidth, buttonImageFitHeight, toggleHandler);
		// TODO - try and avoid the repetition in the next 3-4 lines, perhaps using an
		// array of tuples?
		container.getChildren()
				.add(makeImageButton(slowDownImageURL, buttonImageFitWidth, buttonImageFitHeight, slowDownHandler));
		container.getChildren().add(toggleButton);
		container.getChildren()
				.add(makeImageButton(speedUpImageURL, buttonImageFitWidth, buttonImageFitHeight, speedUpHandler));
		container.getChildren()
				.add(makeImageButton(forwardImageURL, buttonImageFitWidth, buttonImageFitHeight, forwardHandler));
		container.setAlignment(Pos.CENTER);
		return container;
	}

	public void setResumeButtonImage(String imageURL) {
		toggleButton
				.setGraphic(UIImageUtils.getGraphicFromImageURL(imageURL, buttonImageFitWidth, buttonImageFitHeight));
	}

	public static Node initStartPanel(String startText, EventHandler<ActionEvent> onStartButtonClicked) {
		HBox container = new HBox();
		Button startButton = makeButton(startText, onStartButtonClicked);
		container.getChildren().add(startButton);
		container.setAlignment(Pos.CENTER);
		return container;
	}

	public static Node initConfigBox(String uploadText, String simulationChoiceText, String[] simulationTexts,
			ChangeListener<? super Number> changeListener, String dialogHeaderText, String dialogContentText,
			String errorDialogTitleText, String errorDialogHeaderText, String errorDialogContentText) {
		HBox container = new HBox();
		container.getChildren().add(initSimulationDropDownMenu(simulationChoiceText, simulationTexts, changeListener));
		container.getChildren().add(initUploadButton(uploadText, dialogHeaderText, dialogContentText,
				errorDialogTitleText, errorDialogHeaderText, errorDialogContentText));
		/* TODO in next commit
		container.getChildren().add(initGridSizeControlBox());
		*/
		container.setAlignment(Pos.CENTER);
		return container;
	}
	
	public static Button makeButton(String buttonText, EventHandler<ActionEvent> handler) {
		Button button = new Button();
		button.setText(buttonText);
		button.setOnAction(handler);
		return button;
	}

	public static Button makeImageButton(String buttonImageURL, int buttonImageFitWidth, int buttonImageFitHeight,
			EventHandler<ActionEvent> handler) {
		Button button = new Button();
		button.setGraphic(
				UIImageUtils.getGraphicFromImageURL(buttonImageURL, buttonImageFitWidth, buttonImageFitHeight));
		button.setOnAction(handler);
		return button;
	}
	
	public BorderPane initializeBorderPaneWithTopControlPanel(UITextReader reader,
			ChangeListener<? super Number> changeListener) {
		String uploadString = reader.getUploadText();
		String simulationChoiceString = reader.getSimulationChoiceText();
		String[] simulationStrings = reader.getSimulationTexts();
		String dialogHeaderText = reader.getDialogHeaderText();
		String dialogContentText = reader.getDialogContentText();
		String errorDialogTitleText = reader.getMissingFileErrorDialogTitleText();
		String errorDialogHeaderText = reader.getMissingFileErrorDialogHeaderText();
		String errorDialogContentText = reader.getMissingFileErrorDialogContentText();
		BorderPane border = new BorderPane();
		border.setTop(initConfigBox(uploadString, simulationChoiceString, simulationStrings,
				changeListener, dialogHeaderText, dialogContentText, errorDialogTitleText, errorDialogHeaderText,
				errorDialogContentText));
		return border;
	}

	private static Node initSimulationDropDownMenu(String simulationChoiceText, String[] simulationTexts,
			ChangeListener<? super Number> changeListener) {
		// TODO - Make a choice box
		HBox container = new HBox();
		container.getChildren().add(new Text(simulationChoiceText));
		ChoiceBox<String> simulationChoices = new ChoiceBox<String>(FXCollections.observableArrayList(simulationTexts));
		String defaultSimulationName = simulationChoices.getItems().get(0);
		simulationChoices.setValue(defaultSimulationName);
		simulationChoices.getSelectionModel().selectedIndexProperty().addListener(changeListener);
		container.getChildren().add(simulationChoices);
		container.setAlignment(Pos.BASELINE_CENTER);
		return container;
	}

	private static Node initUploadButton(String uploadText, String headerText, String contentText,
			String errorDialogTitleText, String errorDialogHeaderText, String errorDialogContentText) {
		HBox container = new HBox();
		Button uploadButton = new Button(uploadText);
		uploadButton.setOnMouseClicked(e -> UIActionDispatcher.displayFileNameInputDialog(uploadText, headerText,
				contentText, errorDialogTitleText, errorDialogHeaderText, errorDialogContentText));
		container.getChildren().add(uploadButton);
		container.setAlignment(Pos.CENTER);
		return container;
	}
	
	/* TODO in next commit
	private static Node initGridSizeControlBox(String rowsLabel, String colsLabel) {
		HBox hbox = new HBox();
		Label rowsLabel = new Label()
	}
	*/

}
