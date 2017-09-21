package frontend;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import middleware.Cell;;

public class SimulationDisplay {

	// Display-related defaults and limits
	public static final int MIN_ROWS = 5;
	public static final int DEFAULT_ROWS = 10;
	public static final int MAX_ROWS = 50;
	public static final int MIN_COLS = 5;
	public static final int DEFAULT_COLS = 10;
	public static final int MAX_COLS = 50;
	public static final int MIN_WIDTH = 100;
	public static final int DEFAULT_WIDTH = 500;
	public static final int MAX_WIDTH = 1000;
	public static final int MIN_HEIGHT = 100;
	public static final int DEFAULT_HEIGHT = 500;
	public static final int MAX_HEIGHT = 1000;
	public static final int DEFAULT_PADDING = 10;
	public static final int DEFAULT_IMAGE_BUTTON_FIT_WIDTH = 20;
	public static final int DEFAULT_IMAGE_BUTTON_FIT_HEIGHT = 20;
	public static final String PLAY_BUTTON_IMAGE_URL = "play.png";
	public static final String PAUSE_BUTTON_IMAGE_URL = "pause.jpg";
	public static final String FORWARD_BUTTON_IMAGE_URL = "forward.jpg";
	public static final String DEFAULT_TILE_STYLE = "-fx-background-color: #336699;";
	public static final Map<String, int[]> limitsMap;

	// Simulation names as referenced in code - NOTE : This is different from
	// locale-specific display name loaded from ResourceBundle
	// move to separate constants files?
	public static final String SEGREGATION = "segregation";
	public static final String PREDATOR_PREY = "predator_prey";
	public static final String FIRE = "fire";
	public static final String GAME_OF_LIFE = "game_of_life";

	// Simulation-specific states TODO - Fill in states for other games based on
	// info from Rules (From Sam)
	// SEGREGATION
	public static final String OCCUPIED_CROSS = "cross";
	public static final String OCCUPIED_CIRCLE = "circle";
	public static final String VACANT = "vacant";

	// IMAGE_URLs corresponding to above states
	public static final String STATES_IMAGES_FOLDER = "states/";
	public static final String OCCUPIED_CROSS_IMAGE_URL = "occupied_cross.png";
	public static final String OCCUPIED_CIRCLE_IMAGE_URL = "occupied_circle.png";
	public static final String VACANT_IMAGE_URL = "vacant.png";

	static {
		HashMap<String, int[]> myMap = new HashMap<>();
		myMap.put("rows", new int[] { MIN_ROWS, MAX_ROWS });
		myMap.put("cols", new int[] { MIN_COLS, MAX_COLS });
		myMap.put("width", new int[] { MIN_WIDTH, MAX_WIDTH });
		myMap.put("height", new int[] { MIN_HEIGHT, MAX_HEIGHT });
		limitsMap = Collections.unmodifiableMap(myMap);
	}
	// Is there a better way of encoding this? ResourceBundles are for
	// locale-specific user-visible text, so this seems unsuitable for that

	public static final Map<String, Map<String, String>> STATE_TO_IMAGE_URL;
	static {
		HashMap<String, Map<String, String>> myMap = new HashMap<>();
		HashMap<String, String> mySegregationMap = new HashMap<>();
		mySegregationMap.put(OCCUPIED_CROSS, OCCUPIED_CROSS_IMAGE_URL);
		mySegregationMap.put(OCCUPIED_CIRCLE, OCCUPIED_CIRCLE_IMAGE_URL);
		mySegregationMap.put(VACANT, VACANT_IMAGE_URL);
		// TODO - fill in for other simulations

		myMap.put(SEGREGATION, mySegregationMap);
		STATE_TO_IMAGE_URL = Collections.unmodifiableMap(myMap);
	}

	private int width;
	private int height;
	private int cellWidth;
	private int cellHeight;
	private TilePane tiles;
	private Button toggleButton;
	private Scene scene;
	// Would ImageView[][] be more appropriate? Then will need to keep calling a
	// helper function to convert from Cell[][] to ImageView[][], which is less
	// memory-efficient
	private Cell[][] grid;

	// Simulation state
	private String currentSimulation;
	private boolean inProgress = false;

	public void initialize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	// Boilerplate layout set-up code from Oracle docs for Layouts at
	// http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm#CHDCJDBD
	public Scene getMenuScene(Stage primaryStage, String startString, String stopString, String uploadString,
			String simulationChoiceString, String[] simulationStrings, EventHandler<ActionEvent> onStartButtonClicked) {
		// Use BorderPane, HBox, VBox, TilePane
		// TilePane in center of BorderPane
		BorderPane border = new BorderPane();
		// border.setTop(initUploadBox(uploadString));
		border.setTop(initConfigBox(uploadString, simulationChoiceString, simulationStrings));
		border.setBottom(initStartPanel(startString, onStartButtonClicked));
		this.scene = new Scene(border, width, height);
		return this.scene;
	}

	public static boolean isValidConfig(String config, int value) {
		if (!limitsMap.containsKey(config)) {
			return false;
		}
		int[] limits = limitsMap.get(config);
		if (value < limits[0] || value > limits[1]) {
			return false;
		}
		return true;
	}

	public boolean isInProgress() {
		return inProgress;
	}

	// TODO
	public void advanceOneCycle() {
		System.out.println("Advancing one cycle");
		// cellManager.performUpdates();
		// colorMatrix(cellManager.getMatrix())
	}

	public void advance(int cycles) {
		for (int cycle = 0; cycle < cycles; cycle++) {
			advanceOneCycle();
		}
	}

	/*
	 * public void colorMatrix(Cell[][] matrix) { // Map state to ImageView -> get
	 * image path for state through for (int row = 0; row < matrix.length; row ++) {
	 * for (int col = 0; col < matrix[row].length; col ++) { grid[row][col] =
	 * getImageViewForSimulationAndState(currentSimulation,
	 * matrix[row][col].getState()) } }
	 * 
	 * 
	 * }
	 */

	// To transition from menu page to actual simulation display page after telling
	// initializer to read XML Config File and getting back starting matrix
	// TODO - uncomment function signature to include Initializer as a parameter
	// once it's ready
	public Scene startSimulation() { // Initializer initializer) {
		// Tell Initializer which XMLConfig file to read
		/*
		 * Will be uncommented once middleware package (Archana's part with Initializer,
		 * CellManager, etc. is ready)
		 *
		 * try { grid = initializer.loadConfig(chosenConfigFileName) } catch (Exception
		 * e) { // TODO - Raise error dialog
		 * System.out.println("Invalid config file loaded! Not starting."); return; }
		 * if (grid == null || grid.length == 0 || grid[0].length == 0) {
		 * 	throw new IllegalStateException();
		 * }
		 * // Set cell dimensions appropriately
		 * calculateCellDimensions(grid.length, grid[0].length, height, width);
		 * 
		 */

		return getSimulationScene();
	}

	public void toggleSimulationPlayState() {
		if (inProgress) {
			pauseSimulation();
			toggleButton.setGraphic(getGraphicFromImageURL(PLAY_BUTTON_IMAGE_URL, DEFAULT_IMAGE_BUTTON_FIT_WIDTH, DEFAULT_IMAGE_BUTTON_FIT_HEIGHT));
		} else {
			resumeSimulation();
			toggleButton.setGraphic(getGraphicFromImageURL(PAUSE_BUTTON_IMAGE_URL, DEFAULT_IMAGE_BUTTON_FIT_WIDTH, DEFAULT_IMAGE_BUTTON_FIT_HEIGHT));
		}
	}

	public void resumeSimulation() {
		System.out.println("Resuming simulation");
		inProgress = true;
	}

	public void pauseSimulation() {
		System.out.println("Pausing game");
		inProgress = false;
	}

	private Scene getSimulationScene() {
		System.out.println("Getting simulation scene!");
		BorderPane border = new BorderPane();
		border.setBottom(initBottomPanel(PLAY_BUTTON_IMAGE_URL, FORWARD_BUTTON_IMAGE_URL));
		// TODO - should take matrix from getMatrix() instead of rows and cols as
		//border.setCenter(initTiles(grid));
		// DUMMY FOR NOW, just for testing
		border.setCenter(initTilesDummy(DEFAULT_ROWS, DEFAULT_COLS));
		this.scene = new Scene(border, width, height);
		return this.scene;
	}

	private Node initStartPanel(String startText, EventHandler<ActionEvent> onStartButtonClicked) {
		HBox container = new HBox();
		Button startButton = makeButton(startText, onStartButtonClicked);
		container.getChildren().add(startButton);
		container.setAlignment(Pos.CENTER);
		return container;
	}

	// TODO - Rewrite based on simulation UI instead of menu UI, include more images
	private Node initBottomPanel(String resumeImageURL, String forwardImageURL) {
		HBox container = new HBox();
		toggleButton = makeImageButton(resumeImageURL, e -> toggleSimulationPlayState());
		container.getChildren().add(toggleButton);
		container.getChildren().add(makeImageButton(forwardImageURL, e -> advanceOneCycle()));
		container.setAlignment(Pos.CENTER);
		return container;
	}

	private Node initConfigBox(String uploadText, String simulationChoiceText, String[] simulationTexts) {
		HBox container = new HBox();
		container.getChildren().add(initSimulationDropDownMenu(simulationChoiceText, simulationTexts));
		container.getChildren().add(new Button(uploadText));
		container.setAlignment(Pos.CENTER);
		return container;
	}

	private Node initSimulationDropDownMenu(String simulationChoiceText, String[] simulationTexts) {
		// TODO - Make a choice box
		HBox container = new HBox();
		container.getChildren().add(new Text(simulationChoiceText));
		ChoiceBox<String> simulationChoices = new ChoiceBox<String>(FXCollections.observableArrayList(simulationTexts));
		simulationChoices.setValue(simulationChoices.getItems().get(0));
		container.getChildren().add(simulationChoices);
		container.setAlignment(Pos.BASELINE_CENTER);
		return container;
	}

	private TilePane initTiles(Cell[][] matrix) throws IllegalArgumentException {
		if (matrix == null || matrix.length == 0) {
			throw new IllegalArgumentException();
		}
		int rows = matrix.length;
		int cols = matrix[0].length;
		tiles = new TilePane();
		tiles.setPrefColumns(matrix[0].length);
		tiles.setPadding(new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		// TEMP - Make a dummy of texts with nums
		// In future, need Cell[][] matrix from CellManager.getMatrix()
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				tiles.getChildren().add(getImageViewForSimulationAndState(currentSimulation, matrix[row][col].getState()));
			}
		}
		return tiles;
	}
	
	private TilePane initTilesDummy(int rows, int cols) {
		tiles = new TilePane();
		tiles.setPrefColumns(cols);
		tiles.setPadding(new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		// TEMP - Make a dummy of texts with nums
		// In future, need Cell[][] matrix from CellManager.getMatrix()
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				HBox tile = new HBox();
				tile.setPadding(new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
				Text tileText = new Text();
				String tileString = Integer.toString(getIndex(row, col, cols));
				tileText.setText(tileString);
				tile.getChildren().add(tileText);
				tile.setStyle(DEFAULT_TILE_STYLE);
				tiles.getChildren().add(tile);
			}
		}
		return tiles;
	}

	private ImageView getImageViewForSimulationAndState(String currentSimulation, String state) {
		return getGraphicFromImageURL(
				STATES_IMAGES_FOLDER + STATE_TO_IMAGE_URL.get(currentSimulation).get(state), cellWidth, cellHeight);
	}

	private Button makeButton(String buttonText, EventHandler<ActionEvent> handler) {
		Button button = new Button();
		button.setText(buttonText);
		button.setOnAction(handler);
		return button;
	}

	private Button makeImageButton(String buttonImageURL, EventHandler<ActionEvent> handler) {
		Button button = new Button();
		button.setGraphic(getGraphicFromImageURL(buttonImageURL, DEFAULT_IMAGE_BUTTON_FIT_WIDTH, DEFAULT_IMAGE_BUTTON_FIT_HEIGHT));
		button.setOnAction(handler);
		return button;
	}

	private ImageView getGraphicFromImageURL(String imageURL, int imageFitWidth, int imageFitHeight) {
		return resizeImage(new Image(getClass().getClassLoader().getResourceAsStream(imageURL)), imageFitWidth,
				imageFitHeight);
	}

	private ImageView resizeImage(Image buttonImage, double imageFitWidth, double imageFitHeight) {
		ImageView imageView = new ImageView(buttonImage);
		imageView.setFitWidth(imageFitWidth);
		imageView.setFitHeight(imageFitHeight);
		return imageView;
	}

	// Will be called in startSimulation() once Initializer is ready
	private void calculateCellDimensions(int rows, int cols, int gridHeight, int gridWidth) {
		cellWidth = gridWidth / rows;
		cellHeight = gridHeight / rows;
	}
	
	private int getIndex(int row, int col, int numColsPerRow) {
		return row * numColsPerRow + col;
	}

}