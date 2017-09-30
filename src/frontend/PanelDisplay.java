package frontend;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PanelDisplay {

	public static final double DEFAULT_PANEL_SPACING = 5.0;

	private Button toggleButton;
	private int buttonImageFitWidth;
	private int buttonImageFitHeight;
	private SimulationDisplay sim;

	public PanelDisplay(int buttonWidth, int buttonHeight, SimulationDisplay sim) {
		this.buttonImageFitWidth = buttonWidth;
		this.buttonImageFitHeight = buttonHeight;
		this.sim = sim;
	}

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

	public Node initConfigBox(UITextReader reader, ChangeListener<? super Number> changeListener, EventHandler<? super MouseEvent> uploadHandler) {
		HBox container = new HBox();
		container.getChildren().add(initSimulationDropDownMenu(reader.getSimulationChoiceText(),
				reader.getSimulationTexts(), changeListener));
		container.getChildren().add(initUploadButton(reader, uploadHandler));
		container.getChildren().add(initGridSizeControlBox(reader));
		container.setAlignment(Pos.CENTER);
		container.setSpacing(DEFAULT_PANEL_SPACING);
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
			ChangeListener<? super Number> changeListener, EventHandler<? super MouseEvent> uploadHandler) {
		BorderPane border = new BorderPane();
		border.setTop(initConfigBox(reader, changeListener, uploadHandler));
		return border;
	}

	private static Node initSimulationDropDownMenu(String simulationChoiceText, String[] simulationTexts,
			ChangeListener<? super Number> changeListener) {
		HBox container = new HBox();
		container.getChildren().add(new Text(simulationChoiceText));
		ChoiceBox<String> simulationChoices = new ChoiceBox<String>(FXCollections.observableArrayList(simulationTexts));
		String defaultSimulationName = simulationChoices.getItems().get(0);
		simulationChoices.setValue(defaultSimulationName);
		simulationChoices.getSelectionModel().selectedIndexProperty().addListener(changeListener);
		container.getChildren().add(simulationChoices);
		container.setAlignment(Pos.BASELINE_CENTER);
		container.setSpacing(DEFAULT_PANEL_SPACING);
		return container;
	}

	private static Node initUploadButton(UITextReader reader, EventHandler<? super MouseEvent> uploadHandler) {
		HBox container = new HBox();
		Button uploadButton = new Button(reader.getUploadText());
		uploadButton.setOnMouseClicked(uploadHandler);
		container.getChildren().add(uploadButton);
		container.setAlignment(Pos.CENTER);
		return container;
	}

	private Node initGridSizeControlBox(UITextReader reader) {
		HBox hbox = new HBox();
		Label heightLabel = new Label(reader.getGridHeightText());
		TextField heightInput = new TextField();
		Label widthLabel = new Label(reader.getGridWidthText());
		TextField widthInput = new TextField();
		Button gridSizeUpdateButton = new Button(reader.getGridSizeUpdateText());
		EventHandler<? super MouseEvent> gridSizeUpdateHandler = e -> {
			try {
				int gridHeight = Integer.parseInt(heightInput.getText());
				int gridWidth = Integer.parseInt(widthInput.getText());
				// update grid width & grid height accordingly
				sim.setGridWidth(gridWidth);
				sim.setGridHeight(gridHeight);
			} catch (NumberFormatException ex) {
				UIActionDispatcher.displayWarningDialog(reader.getInvalidGridSizeDialogTitleText(),
						reader.getInvalidGridSizeDialogHeaderText(), reader.getInvalidGridSizeDialogContentText());
			}
		};
		gridSizeUpdateButton.setOnMouseClicked(gridSizeUpdateHandler);
		hbox.getChildren().addAll(heightLabel, heightInput, widthLabel, widthInput, gridSizeUpdateButton);
		hbox.setSpacing(DEFAULT_PANEL_SPACING);
		return hbox;
	}

}
