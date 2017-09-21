package frontend;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class UITextReader {
	
	// ResourceBundle for locale-specific customization
	ResourceBundle myResources;
	public static final String RESOURCE_PACKAGE = "resources/";
	
	// Key constants for ResourceBundle
	// Required ones
	public static final String TITLE_KEY = "Title";
	public static final String START_KEY = "Start";
	public static final String STOP_KEY = "Stop";
	public static final String UPLOAD_KEY = "Upload";
	public static final String SIMULATION_CHOICE_KEY = "Simulation_Choice";
	public static final String SIMULATIONS_KEY = "Simulations";
	// Optional ones
	public static final String ROW_KEY = "rows";
	public static final String COL_KEY = "cols";
	public static final String WIDTH_KEY = "width";
	public static final String HEIGHT_KEY = "height";
		
	// Map holding these for convenience
	private Map<String, Integer> displayConfig;
	
	public UITextReader(String resourceFolder, Locale locale) {
		// TODO - Read all resource files from folder, initialize variables accordingly
		myResources = ResourceBundle.getBundle(resourceFolder + locale);
		displayConfig = new HashMap<>();
		displayConfig.put(ROW_KEY, SimulationDisplay.DEFAULT_ROWS);
		displayConfig.put(COL_KEY, SimulationDisplay.DEFAULT_COLS);
		displayConfig.put(WIDTH_KEY, SimulationDisplay.DEFAULT_WIDTH);
		displayConfig.put(HEIGHT_KEY, SimulationDisplay.DEFAULT_HEIGHT);
		overrideDisplayConfigIfNecessary();		
	}
	
	private void overrideDisplayConfigIfNecessary() {
		for (String configKey : displayConfig.keySet()) {
			if (myResources.containsKey(configKey)) {
				setConfig(configKey);
			}
		}
	}
	
	// Returning of error dialog will be handled by UserInterface
	private void setConfig(String configKey) throws IllegalArgumentException {
		int configVal = assertNumericString(configKey);
		if (!SimulationDisplay.isValidConfig(configKey, configVal)) {
			throw new IllegalArgumentException();
		}
		displayConfig.put(configKey, configVal);
	}
	
	private int assertNumericString(String value) throws NumberFormatException {
		return Integer.parseInt(value);
	}
	
	public String getTitleText() {
		return myResources.getString(TITLE_KEY);
	}
	
	public String getStartText() {
		return myResources.getString(START_KEY);
	}
	
	public String getStopText() {
		return myResources.getString(STOP_KEY);
	}
	
	public String getUploadText() {
		return myResources.getString(UPLOAD_KEY);
	}
	
	public String getSimulationChoiceText() {
		return myResources.getString(SIMULATION_CHOICE_KEY);
	}
	
	public String[] getSimulationTexts() {
		return myResources.getString(SIMULATIONS_KEY).split(", ");
	}
	
	public int getRows() {
		return displayConfig.get(ROW_KEY);
	}
	
	public int getCols() {
		return displayConfig.get(COL_KEY);
	}
	
	public int getWidth() {
		return displayConfig.get(WIDTH_KEY);
	}
	
	public int getHeight() {
		return displayConfig.get(HEIGHT_KEY);
	}
		
}
