package frontend;

import java.util.Locale;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final double ANIMATION_SPEED_SCALING_FACTOR = 0.05;

	public static final Locale DEFAULT_LOCALE = Locale.US;

	private UITextReader reader;
	private SimulationDisplay display;
	private Stage stage;

	private double animationRate = 1;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		reader = new UITextReader(UITextReader.RESOURCE_PACKAGE, DEFAULT_LOCALE);
		display = new SimulationDisplay(reader.getWidth(), reader.getHeight());
		stage.setTitle(reader.getTitleText());
		registerAnimation();
		// TODO - Refactor to pass in a reference to UITextReader instead of extracting
		// strings from reader beforehand?
		updateStage(display.getMenuScene(stage, reader, e -> updateStage(display.startSimulation())));
	}

	@Override
	public void stop() {
		System.out.println("Stopping");
		try {
			super.stop();
		} catch (Exception e) {
			display.pauseSimulation();
		}
	}

	public void updateStage(Scene scene) {
		System.out.println("Updating stage!");
		stage.setScene(scene);
		stage.show();
	}

	// TODO - Move these user-triggered methods to UserInterface
	public void speedUp(double factor) {
		animationRate *= factor;
	}

	public void slowDown(double factor) {
		animationRate /= factor;
	}

	private void registerAnimation() {
		KeyFrame frame = new KeyFrame(
				Duration.millis((animationRate / ANIMATION_SPEED_SCALING_FACTOR) * MILLISECOND_DELAY),
				e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private void step(double elapsedTime) {
		if (display.isInProgress()) {
			display.advanceOneCycle();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
