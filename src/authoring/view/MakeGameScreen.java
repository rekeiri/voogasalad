package authoring.view;

import java.io.File;
import java.io.IOException;

import authoring.backend.AuthoringController;
import authoring.backend.GameEntity;
import game_data.Reader;
import gui_elements.factories.ButtonFactory;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MakeGameScreen implements AuthoringView {
	public static final Color INITIAL_COLOR = Color.LIGHTSKYBLUE;

	private Stage myStage;
	private Scene myScene;
	private SplitPane myPane;
	private GameEntity myGame;
	private AuthoringController myAuthoringController;
	public MakeGameScreen (Stage stage) {
		myGame = new GameEntity();
		myStage = stage;
		setupScreen();
	}
	
	public MakeGameScreen(Stage stage, File myFile) throws ClassNotFoundException, IOException {
		List<Object> myAuthoringObjects = Reader.read(myFile.getCanonicalPath(), "list");
		List<Object> myMap = Reader.read(myFile.getCanonicalPath(), "map");
		List<Object> myMapSettings = Reader.read(myFile.getCanonicalPath(), "authoring.backend.MapSettings");
		
		myGame = new GameEntity();
		myStage = stage;
		setupScreen();
		
	}

	private void setupScreen() {
		myAuthoringController = new AuthoringController();
		HBox box = new HBox();
//		box.setId("start_screen");
		VBox inner = new VBox();
		inner.getChildren().addAll(
				new DisplayMenu(myAuthoringController, myGame),
				new CreatedObjectsTabs(myAuthoringController, myGame));
		box.getChildren().addAll(
				new MakeGameTabs(myAuthoringController, myGame),
				inner);

		
		box.setPadding(new Insets(10, 10, 10, 10));
		box.setSpacing(10);
		myScene = new Scene(box);
		myScene.getStylesheets().add(STYLE_PATH);
		myStage.setScene(myScene);
	}
	
}
