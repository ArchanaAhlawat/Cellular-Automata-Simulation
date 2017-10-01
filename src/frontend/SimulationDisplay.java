package frontend;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cell.GeneralCell;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import middleware.CellManager;
import middleware.Initializer;

public class SimulationDisplay {

	// Display-related defaults and limits
	public static final String ROWS = "rows";
	public static final String COLS = "cols";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String GRID_WIDTH = "grid_width";
	public static final String GRID_HEIGHT = "grid_height";
	public static final int MIN_ROWS = 5;
	public static final int DEFAULT_ROWS = 10;
	public static final int MAX_ROWS = 50;
	public static final int MIN_COLS = 5;
	public static final int DEFAULT_COLS = 10;
	public static final int MAX_COLS = 50;
	public static final int MIN_WIDTH = 100;
	public static final int DEFAULT_WIDTH = 1200;
	public static final int MAX_WIDTH = 2000;
	public static final int MIN_HEIGHT = 100;
	public static final int DEFAULT_HEIGHT = 600;
	public static final int MAX_HEIGHT = 2000;
	public static final int DEFAULT_GRID_WIDTH = 500;
	public static final int MAX_GRID_WIDTH = 1600;
	public static final int MIN_GRID_WIDTH = 50;
	public static final int DEFAULT_GRID_HEIGHT = 500;
	public static final int MAX_GRID_HEIGHT = 1600;
	public static final int MIN_GRID_HEIGHT = 50;
	public static final int DEFAULT_PADDING = 10;
	public static final int DEFAULT_IMAGE_BUTTON_FIT_WIDTH = 20;
	public static final int DEFAULT_IMAGE_BUTTON_FIT_HEIGHT = 20;
	public static final String PLAY_BUTTON_IMAGE_URL = "play.png";
	public static final String PAUSE_BUTTON_IMAGE_URL = "pause.jpg";
	public static final String FORWARD_BUTTON_IMAGE_URL = "forward.jpg";
	public static final String SPEEDUP_BUTTON_IMAGE_URL = "speedup.png";
	public static final String SLOWDOWN_BUTTON_IMAGE_URL = "slowdown.jpg";
	public static final String DEFAULT_TILE_STYLE = "-fx-background-color: #336699;";
	public static final Map<String, int[]> limitsMap;
	static {
		HashMap<String, int[]> myMap = new HashMap<>();
		myMap.put(ROWS, new int[] { MIN_ROWS, MAX_ROWS });
		myMap.put(COLS, new int[] { MIN_COLS, MAX_COLS });
		myMap.put(WIDTH, new int[] { MIN_WIDTH, MAX_WIDTH });
		myMap.put(HEIGHT, new int[] { MIN_HEIGHT, MAX_HEIGHT });
		myMap.put(GRID_WIDTH, new int[] { MIN_GRID_WIDTH, MAX_GRID_WIDTH });
		myMap.put(GRID_HEIGHT, new int[] { MIN_GRID_HEIGHT, MAX_GRID_HEIGHT });
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
	public static final String DEFAULT_SEGREGATION_CONFIG = "Segregation.xml";
	public static final String DEFAULT_PREDATOR_PREY_CONFIG = "PredatorPrey.xml";
	public static final String DEFAULT_FIRE_CONFIG = "Fire.xml";
	public static final String DEFAULT_GAME_OF_LIFE_CONFIG = "GameOfLife.xml";

	private int width;
	private int height;
	private int gridWidth = DEFAULT_GRID_WIDTH;
	private int gridHeight = DEFAULT_GRID_HEIGHT;
	private int cellWidth;
	private int cellHeight;
	private CellManager cellManager;
	private UITextReader reader;
	private PanelDisplay panelDisplay;
	private Scene scene;
	// Would ImageView[][] be more appropriate? Then will need to keep calling a
	// helper function to convert from Cell[][] to ImageView[][], which is less
	// memory-efficient
	private GeneralCell[][] grid; // Will be used once integrated
	private TilePane tiles;
	private UIGraphUtils grapher;
	private HBox belowSimulation;
	private Chart graph;

	// Simulation state
	private String currentSimulation = SEGREGATION;
	private String chosenConfigFileName;
	private boolean inProgress = false;
	private boolean hasNewConfig = false;
	private int cyclesElapsed = 0;

	public SimulationDisplay(int width, int height, UITextReader reader) {
		this.width = width;
		this.height = height;
		this.reader = reader;
		panelDisplay = new PanelDisplay(DEFAULT_IMAGE_BUTTON_FIT_WIDTH, DEFAULT_IMAGE_BUTTON_FIT_HEIGHT, reader, this);
	}

	public Scene getMenuScene(Stage primaryStage, EventHandler<ActionEvent> onStartButtonClicked) {
		String startString = reader.getStartText();
		BorderPane border = panelDisplay.initializeBorderPaneWithTopControlPanel(getSimulationChangeListener(),
				updateChosenConfigFileName());
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

	public boolean hasNewConfig() {
		return hasNewConfig;
	}

	public void acknowledgeConfig() {
		hasNewConfig = false;
	}

	// TODO - Uncomment calls to backend methods when ready
	public void advanceOneCycle() {
		cellManager.performUpdates();
		grid = cellManager.getGrid();
		updateTiles(grid);
		updateGraph(grid);
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
	public Scene startSimulation(EventHandler<ActionEvent> onSpeedUpButtonClicked,
			EventHandler<ActionEvent> onSlowDownButtonClicked) {
		if (chosenConfigFileName == null) {
			chosenConfigFileName = getDefaultXMLConfigFile(currentSimulation);
		}
		// Tell Initializer which XMLConfig file to read
		cellManager = Initializer.loadConfig(chosenConfigFileName);
		grid = cellManager.getGrid();
		if (grid == null || grid.length == 0 || grid[0].length == 0) {
			throw new IllegalStateException();
		}
		// Set cell dimensions appropriately
		calculateCellDimensions(grid.length, grid[0].length, height, width);
		inProgress = true;
		grapher = new UIGraphUtils(reader.getGraphTitleText());
		resetCycles();
		return getSimulationScene(onSpeedUpButtonClicked, onSlowDownButtonClicked);
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

	public void resetCycles() {
		cyclesElapsed = 0;
	}

	public int getCyclesElapsed() {
		return cyclesElapsed;
	}

	public void setGridWidth(int gridWidth) {
		if (!isValidConfig(GRID_WIDTH, gridWidth)) {
			throw new NumberFormatException();
		}
		setGridDimension(gridWidth, true);
	}

	public void setGridHeight(int gridHeight) {
		if (!isValidConfig(GRID_HEIGHT, gridHeight)) {
			throw new NumberFormatException();
		}
		setGridDimension(gridHeight, false);
	}

	private void setGridDimension(int value, boolean isWidth) {
		if (grid == null || grid.length == 0 || grid[0].length == 0) {
			return;
		}
		if (isWidth) {
			this.gridWidth = value;
		} else {
			this.gridHeight = value;
		}
		calculateCellDimensions(grid.length, grid[0].length, gridHeight, gridWidth);
	}

	private Scene getSimulationScene(EventHandler<ActionEvent> onSpeedUpButtonClicked,
			EventHandler<ActionEvent> onSlowDownButtonClicked) {
		System.out.println("Getting simulation scene!");
		BorderPane border = panelDisplay.initializeBorderPaneWithTopControlPanel(getSimulationChangeListener(),
				updateChosenConfigFileName());
		belowSimulation = new HBox();
		graph = grapher.getPopulationChart(grid);
		resizeChart(graph);
		belowSimulation.getChildren().add(graph);
		belowSimulation.getChildren()
				.add(panelDisplay.initBottomPanel(PLAY_BUTTON_IMAGE_URL, FORWARD_BUTTON_IMAGE_URL,
						SPEEDUP_BUTTON_IMAGE_URL, SLOWDOWN_BUTTON_IMAGE_URL, e -> toggleSimulationPlayState(),
						e -> advanceOneCycle(), onSpeedUpButtonClicked, onSlowDownButtonClicked));
		border.setBottom(belowSimulation);
		border.setCenter(updateTiles(grid));
		// DUMMY FOR NOW, just for testing
		// border.setCenter(initTilesDummy(DEFAULT_ROWS, DEFAULT_COLS));
		this.scene = new Scene(border, width, height);
		return this.scene;
	}

	// will be called once integration is ready
	private TilePane updateTiles(GeneralCell[][] matrix) throws IllegalArgumentException {
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
		// In future, need Cell[][] matrix from CellManager.getGrid()
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				tiles.getChildren().add(UIImageUtils.getImageViewForSimulationAndState(currentSimulation,
						matrix[row][col].getState(), cellWidth, cellHeight));
			}
		}
		return tiles;
	}

	private void updateGraph(GeneralCell[][] matrix) {
		belowSimulation.getChildren().remove(graph);
		graph = grapher.getPopulationChart(matrix);
		belowSimulation.getChildren().add(0, graph);
		resizeChart(graph);
	}

	private void resizeChart(Chart chart) {
		chart.setMaxHeight(height - gridHeight);
	}

	// Will be called in startSimulation() once Initializer is ready - can ignore
	// warning for now
	private void calculateCellDimensions(int rows, int cols, int gridHeight, int gridWidth) {
		if (rows == 0 || cols == 0) {
			System.out.println("Grid is empty");
			throw new IllegalArgumentException();
		}
		cellWidth = gridWidth / cols;
		cellHeight = gridHeight / rows;
	}

	private int getIndex(int row, int col, int numColsPerRow) {
		return row * numColsPerRow + col;
	}

	private ChangeListener<? super Number> getSimulationChangeListener() {
		return (observable, oldVal, newVal) -> {
			currentSimulation = reader.getSimulationTexts()[(int) newVal];
		};
	}

	// TODO - refactor by moving into Main and passing to SimulationDisplay as an
	// 'onUpload' parameter
	private EventHandler<? super MouseEvent> updateChosenConfigFileName() {
		return e -> {
			String uploadedFileName = UIActionDispatcher.displayFileNameInputDialogAndGetResult(reader.getUploadText(),
					reader.getDialogHeaderText(), reader.getDialogContentText(),
					reader.getMissingFileErrorDialogTitleText(), reader.getMissingFileErrorDialogHeaderText(),
					reader.getMissingFileErrorDialogContentText());
			if (uploadedFileName.length() > 0) {
				chosenConfigFileName = uploadedFileName;
				System.out.println("Updated chosen config file name to " + chosenConfigFileName);
				// Call startSimulation() to restart a new simulation
				hasNewConfig = true;
			}
		};
	}

}
