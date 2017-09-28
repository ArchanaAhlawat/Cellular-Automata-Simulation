package frontend;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.geometry.Insets;
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
	static {
		HashMap<String, int[]> myMap = new HashMap<>();
		myMap.put("rows", new int[] { MIN_ROWS, MAX_ROWS });
		myMap.put("cols", new int[] { MIN_COLS, MAX_COLS });
		myMap.put("width", new int[] { MIN_WIDTH, MAX_WIDTH });
		myMap.put("height", new int[] { MIN_HEIGHT, MAX_HEIGHT });
		limitsMap = Collections.unmodifiableMap(myMap);
	}

	// Simulation names as referenced in code - NOTE : This is different from
	// locale-specific display name loaded from ResourceBundle
	// move to separate constants files?
	public static final String SEGREGATION = "Segregation";
	public static final String PREDATOR_PREY = "Predator-prey";
	public static final String FIRE = "Fire";
	public static final String GAME_OF_LIFE = "Game of Life";

	// CONFIG folder
	public static final String XML_CONFIG_FOLDER = "config/";
	// Default config files
	public static final String DEFAULT_SEGREGATION_CONFIG = "default_segregation.xml";
	public static final String DEFAULT_PREDATOR_PREY_CONFIG = "default_predatory_prey.xml";
	public static final String DEFAULT_FIRE_CONFIG = "default_fire.xml";
	public static final String DEFAULT_GAME_OF_LIFE_CONFIG = "default_game_of_life.xml";

	private int width;
	private int height;
	private int cellWidth;
	private int cellHeight;
	private PanelDisplay panelDisplay;
	private Scene scene;
	// Would ImageView[][] be more appropriate? Then will need to keep calling a
	// helper function to convert from Cell[][] to ImageView[][], which is less
	// memory-efficient
	private Cell[][] grid;
	private TilePane tiles;

	// Simulation state
	private String currentSimulation = SEGREGATION;
	private String chosenConfigFileName;
	private boolean inProgress = false;

	public SimulationDisplay(int width, int height) {
		this.width = width;
		this.height = height;
		panelDisplay = new PanelDisplay(DEFAULT_IMAGE_BUTTON_FIT_WIDTH, DEFAULT_IMAGE_BUTTON_FIT_HEIGHT);
	}

	// TODO - Refactor to pass in a reference to UITextReader instead of extracting
	// strings from reader beforehand?
	public Scene getMenuScene(Stage primaryStage, String startString, String stopString, String uploadString,
			String simulationChoiceString, String[] simulationStrings, String dialogHeaderText,
			String dialogContentText, String errorDialogTitleText, String errorDialogHeaderText,
			String errorDialogContentText, EventHandler<ActionEvent> onStartButtonClicked) {
		// Use BorderPane, HBox, VBox, TilePane
		// TilePane in center of BorderPane
		BorderPane border = new BorderPane();
		border.setTop(PanelDisplay.initConfigBox(uploadString, simulationChoiceString, simulationStrings,
				(observable, oldVal, newVal) -> {
					currentSimulation = simulationStrings[(int) newVal];
				}, dialogHeaderText, dialogContentText, errorDialogTitleText, errorDialogHeaderText,
				errorDialogContentText));
		border.setBottom(PanelDisplay.initStartPanel(startString, onStartButtonClicked));
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

	// Choose String instead of Enum for simulation to support arbitrary simulation
	// names in future if necessary
	public static String getDefaultXMLConfigFile(String simulation) {
		String prefix = XML_CONFIG_FOLDER;
		String fileName = "";
		switch (simulation) {
		case SEGREGATION:
			fileName = DEFAULT_SEGREGATION_CONFIG;
			break;
		case PREDATOR_PREY:
			fileName = DEFAULT_PREDATOR_PREY_CONFIG;
			break;
		case FIRE:
			fileName = DEFAULT_FIRE_CONFIG;
			break;
		case GAME_OF_LIFE:
			fileName = DEFAULT_GAME_OF_LIFE_CONFIG;
			break;
		default:
			throw new IllegalArgumentException();
		}
		return prefix + fileName;
	}

	public boolean isInProgress() {
		return inProgress;
	}

	// TODO - Uncomment calls to backend methods when ready
	public void advanceOneCycle() {
		System.out.println("Advancing one cycle");
		// Uncomment when ready to integrate
		// cellManager.performUpdates();
		// updateTiles(cellManager.getMatrix())
	}

	public void advance(int cycles) {
		for (int cycle = 0; cycle < cycles; cycle++) {
			advanceOneCycle();
		}
	}

	// To transition from menu page to actual simulation display page after telling
	// initializer to read XML Config File and getting back starting matrix
	// TODO - uncomment function signature to include Initializer as a parameter
	// once it's ready
	public Scene startSimulation() { // Initializer initializer) {
		if (chosenConfigFileName == null) {
			chosenConfigFileName = getDefaultXMLConfigFile(currentSimulation);
		}
		// Tell Initializer which XMLConfig file to read
		/*
		 * // Will be uncommented once middleware package (Archana's part with
		 * Initializer, // CellManager, etc. is ready grid =
		 * initializer.loadConfig(chosenConfigFileName); if (grid == null || grid.length
		 * == 0 || grid[0].length == 0) { throw new IllegalStateException(); } // Set
		 * cell dimensions appropriately calculateCellDimensions(grid.length,
		 * grid[0].length, height, width);
		 */

		return getSimulationScene();
	}

	public void toggleSimulationPlayState() {
		if (inProgress) {
			pauseSimulation();
		} else {
			resumeSimulation();
		}
	}

	public void resumeSimulation() {
		System.out.println("Resuming simulation");
		inProgress = true;
		panelDisplay.setResumeButtonImage(PAUSE_BUTTON_IMAGE_URL);
	}

	public void pauseSimulation() {
		System.out.println("Pausing game");
		inProgress = false;
		panelDisplay.setResumeButtonImage(PLAY_BUTTON_IMAGE_URL);
	}

	private Scene getSimulationScene() {
		System.out.println("Getting simulation scene!");
		BorderPane border = new BorderPane();
		border.setBottom(panelDisplay.initBottomPanel(PLAY_BUTTON_IMAGE_URL, FORWARD_BUTTON_IMAGE_URL,
				e -> toggleSimulationPlayState(), e -> advanceOneCycle()));
		// border.setCenter(initTiles(grid));
		// DUMMY FOR NOW, just for testing
		border.setCenter(initTilesDummy(DEFAULT_ROWS, DEFAULT_COLS));
		this.scene = new Scene(border, width, height);
		return this.scene;
	}

	// will be called once integration is ready
	private TilePane updateTiles(Cell[][] matrix) throws IllegalArgumentException {
		if (matrix == null || matrix.length == 0) {
			throw new IllegalArgumentException();
		}
		int rows = matrix.length;
		int cols = matrix[0].length;
		if (tiles == null) {
			tiles = new TilePane();
		} else {
			tiles.getChildren().clear();
		}
		tiles.setPrefColumns(matrix[0].length);
		tiles.setPadding(new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		// TEMP - Make a dummy of texts with nums
		// In future, need Cell[][] matrix from CellManager.getMatrix()
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				tiles.getChildren().add(UIImageUtils.getImageViewForSimulationAndState(currentSimulation,
						matrix[row][col].getState(), cellWidth, cellHeight));
			}
		}
		return tiles;
	}

	// TEMP - ONLY FOR TESTING WHILE BACKEND IS NOT READY
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

	// Will be called in startSimulation() once Initializer is ready - can ignore warning for now
	private void calculateCellDimensions(int rows, int cols, int gridHeight, int gridWidth) {
		cellWidth = gridWidth / rows;
		cellHeight = gridHeight / rows;
	}

	private int getIndex(int row, int col, int numColsPerRow) {
		return row * numColsPerRow + col;
	}

}
