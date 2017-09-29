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
	public static final double ANIMATION_SPEED_SCALING_FACTOR = 20.0;
	public static final double DEFAULT_SPEEDUP_FACTOR = 2.0;

	public static final Locale DEFAULT_LOCALE = Locale.US;

	private UITextReader reader;
	private SimulationDisplay display;
	private Stage stage;

	private Timeline animation;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		reader = new UITextReader(UITextReader.RESOURCE_PACKAGE, DEFAULT_LOCALE);
		display = new SimulationDisplay(reader.getWidth(), reader.getHeight(), reader);
		stage.setTitle(reader.getTitleText());
		registerAnimation();
		updateStage(display.getMenuScene(stage, e -> updateStage(display
				.startSimulation(e1 -> speedUp(DEFAULT_SPEEDUP_FACTOR), e2 -> slowDown(DEFAULT_SPEEDUP_FACTOR)))));
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
		animation.setRate(animation.getCurrentRate() * factor);
	}

	public void slowDown(double factor) {
		animation.setRate(animation.getCurrentRate() / factor);
	}

	private void registerAnimation() {
		animation = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis((ANIMATION_SPEED_SCALING_FACTOR) * MILLISECOND_DELAY),
				e -> step(SECOND_DELAY));
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
