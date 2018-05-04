package authoring.view;

import java.io.File;
import java.io.IOException;

import gui_elements.factories.ButtonFactory;
import gui_elements.texts.StartScreenText;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server_client.ServerClient;

public class StartScreen implements AuthoringView {
	public static final String STYLE_PATH = "gui_elements/css/AuthoringView.css";
	public static final String TITLE = "Rap Tilt Swagger";
	private Stage myStage;
	private 	StackPane myPane;
	private Scene myScene; 

	public StartScreen(Stage primaryStage) {
		myStage = primaryStage;
		setupScreen();
		setupContent();
		setupStage();
	}
	
	private void setupScreen() {
		myPane = new StackPane();
		myPane.setBackground(new Background(new BackgroundFill(DEFAULT_BACKGROUND, null, null)));
		myPane.setId("start_screen");
		myScene = new Scene(myPane);
		myScene.getStylesheets().add(STYLE_PATH);
	}
	
	private void setupContent() {
		VBox box = new VBox();
		box.getChildren().addAll(
				new StartScreenText(),
				ButtonFactory.makeButton("Make Game", e -> new MakeGameSelect(myStage), "image_button"),
				//new MakeGameButton(myStage), 
				ButtonFactory.makeButton("Load Game", e -> {FileChooser myFC = new FileChooser();
															File myFile = myFC.showOpenDialog(new Stage());
															try {
																new MakeGameScreen(myStage, myFile);
															} catch (ClassNotFoundException | IOException | NullPointerException e2) {
																Alert alert = new Alert(AlertType.ERROR);
																alert.setTitle("Error Selecting Game");
																alert.setContentText("You did not choose a file or the file is incorrectly formatted");
																alert.setHeaderText(null);
																alert.showAndWait();
															}
															
				}, "image_button"),
				ButtonFactory.makeButton("Play Game", e -> {new ServerClient(new Stage());}, "image_button")
				);
				ButtonFactory.makeButton("Test Game", e -> {
					FileChooser myFC = new FileChooser();
					File myFile = myFC.showOpenDialog(new Stage());
					
				});
		box.setAlignment(Pos.CENTER_LEFT);
		box.setPadding(new Insets(0, 0, 0, 30));
		box.setSpacing(SPACING_SMALL);
		myPane.getChildren().add(box);
	}
	
	private void setupStage() {
		myStage.setScene(myScene);
		myStage.setTitle(TITLE);
		myStage.setWidth(INITIAL_SCENE_WIDTH);
		myStage.setHeight(INITIAL_SCENE_HEIGHT);
		myStage.setResizable(false);
		myStage.show();
	}	
}