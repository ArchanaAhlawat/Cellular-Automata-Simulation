package frontend;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UIImageUtils {

	// Simulation-specific states TODO - Fill in states for other games based on
	// info from Rules (From Sam)
	// SEGREGATION
	public static final String AGENT1 = "agent1";
	public static final String AGENT2 = "agent2";
	public static final String EMPTY = "empty";
	
	// PREDATOR-PREY
	public static final String FISH = "fish";
	public static final String SHARK = "shark";

	// FIRE
	public static final String FIRE = "fire";
	public static final String TREE = "tree";
	// Empty same as above

	// GAME OF LIFE
	public static final String ALIVE = "alive";

	// IMAGE_URLs corresponding to above states
	public static final String STATES_IMAGES_FOLDER = "states/";
	// SEGREGATION
	public static final String AGENT1_IMAGE_URL = "agent1.png";
	public static final String AGENT2_IMAGE_URL = "agent2.png";

	// PREDATOR-PREY
	public static final String FISH_IMAGE_URL = "fish.jpg";
	public static final String SHARK_IMAGE_URL = "shark.png";
	public static final String EMPTY_IMAGE_URL = "empty.png";
	// FIRE
	public static final String FIRE_IMAGE_URL = "fire.jpeg";
	public static final String TREE_IMAGE_URL = "tree.jpeg";
	// GAME OF LIFE
	public static final String ALIVE_IMAGE_URL = "alive.jpeg";

	// Is there a better way of encoding this? ResourceBundles are for
	// locale-specific user-visible text, so this seems unsuitable for that

	public static final Map<String, Map<String, String>> STATE_TO_IMAGE_URL;
	static {
		HashMap<String, Map<String, String>> myMap = new HashMap<>();

		HashMap<String, String> mySegregationMap = new HashMap<>();
		mySegregationMap.put(AGENT1, AGENT1_IMAGE_URL);
		mySegregationMap.put(AGENT2, AGENT2_IMAGE_URL);
		mySegregationMap.put(EMPTY, EMPTY_IMAGE_URL);
		myMap.put(SimulationDisplay.SEGREGATION, mySegregationMap);

		HashMap<String, String> myPredatorPreyMap = new HashMap<>();
		myPredatorPreyMap.put(FISH, FISH_IMAGE_URL);
		myPredatorPreyMap.put(SHARK, SHARK_IMAGE_URL);
		myPredatorPreyMap.put(EMPTY, EMPTY_IMAGE_URL);
		myMap.put(SimulationDisplay.PREDATOR_PREY, myPredatorPreyMap);

		HashMap<String, String> myFireMap = new HashMap<>();
		myFireMap.put(FIRE, FIRE_IMAGE_URL);
		myFireMap.put(TREE, TREE_IMAGE_URL);
		myFireMap.put(EMPTY, EMPTY_IMAGE_URL);
		myMap.put(SimulationDisplay.FIRE, myFireMap);

		HashMap<String, String> myGameOfLifeMap = new HashMap<>();
		myGameOfLifeMap.put(ALIVE, ALIVE_IMAGE_URL);
		myGameOfLifeMap.put(EMPTY, EMPTY_IMAGE_URL);
		myMap.put(SimulationDisplay.GAME_OF_LIFE, myGameOfLifeMap);

		STATE_TO_IMAGE_URL = Collections.unmodifiableMap(myMap);
	}

	public static ImageView getImageViewForSimulationAndState(String currentSimulation, String state, int imageFitWidth,
			int imageFitHeight) {
		return getGraphicFromImageURL(STATES_IMAGES_FOLDER + STATE_TO_IMAGE_URL.get(currentSimulation).get(state),
				imageFitWidth, imageFitHeight);
	}

	public static ImageView getGraphicFromImageURL(String imageURL, int imageFitWidth, int imageFitHeight) {
		return resizeImage(new Image(UIImageUtils.class.getClassLoader().getResourceAsStream(imageURL)), imageFitWidth,
				imageFitHeight);
	}

	public static ImageView resizeImage(Image buttonImage, double imageFitWidth, double imageFitHeight) {
		ImageView imageView = new ImageView(buttonImage);
		imageView.setFitWidth(imageFitWidth);
		imageView.setFitHeight(imageFitHeight);
		return imageView;
	}

}
