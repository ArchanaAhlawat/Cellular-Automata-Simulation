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
	public static final String OCCUPIED_CROSS = "cross.png";
	public static final String OCCUPIED_CIRCLE = "circle.png";
	public static final String VACANT = "vacant.png";

	// IMAGE_URLs corresponding to above states
	public static final String STATES_IMAGES_FOLDER = "states/";
	public static final String OCCUPIED_CROSS_IMAGE_URL = "occupied_cross.png";
	public static final String OCCUPIED_CIRCLE_IMAGE_URL = "occupied_circle.png";
	public static final String VACANT_IMAGE_URL = "vacant.png";

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

		myMap.put(SimulationDisplay.SEGREGATION, mySegregationMap);
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
