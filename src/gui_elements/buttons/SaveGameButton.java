package gui_elements.buttons;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.backend.AuthoringController;
import authoring.backend.AuthoringObject;
import authoring.backend.CreatedObjects;
import authoring.backend.DraggableImageView;
import authoring.backend.GameEntity;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import game_data.Writer;
import resources.Resources;
import transform_library.Vector2;

public class SaveGameButton extends MainButton {

	private static final String FILENAME = "save_game_button.properties";
	private static final String RESOURCES_STRING = "AUTHOR_LOCATION_OBJECTS";
	private static final String RESOURCES_STRING2 = "AUTHOR_LOCATION_MAP";
	private static final String ALERT_TITLE = "COmponent Saved";
	private static final String ALERT_MESSAGE = "Your component has been saved!";
	private static final boolean EXPLICIT_SET_ACTION = false;
	private Writer myWriter;
	private AuthoringController myAuthoringController;
	private GameEntity myGameEntity;
//	private Reader myReader;

	public SaveGameButton(AuthoringController ac, GameEntity gameEntity) {
		super(FILENAME, EXPLICIT_SET_ACTION);
		myAuthoringController = ac;
		myGameEntity = gameEntity;
		setAction();
	}

	@Override
	protected void setAction() {
		getButton().setOnAction(value -> {
			myWriter = new Writer();
//			myReader = new Reader();
			try {
				myWriter.write(Resources.getString(RESOURCES_STRING), myGameEntity.getCreatedObjects().getAuthoringObjects());
				Map<AuthoringObject, List<DraggableImageView>> map = myAuthoringController.getMap().getLocations();
				Map<AuthoringObject, List<Vector2>> changedMap = turnImageViewToVector2(map);
				
				List<Map<AuthoringObject, List<Vector2>>> listform = new ArrayList<>();
				listform.add(changedMap);
				myWriter.write(Resources.getString(RESOURCES_STRING2), listform);
				createAlert();
			} catch (IOException e) {
				System.err.println("Could not save created authoring objects");
			}
			
			CreatedObjects.getAuthoringObjects().clear();			
		});
	}
	
	private Map<AuthoringObject, List<Vector2>> turnImageViewToVector2(Map<AuthoringObject, List<DraggableImageView>> originalMap) {
		Map<AuthoringObject, List<Vector2>> newMap = new HashMap<>();
		for (AuthoringObject obj: originalMap.keySet()) {
			List<DraggableImageView> list = originalMap.get(obj);
			List<Vector2> newList = new ArrayList();
			for (DraggableImageView img: list) {
				Vector2 v = new Vector2(img.getX(), img.getY());
				newList.add(v);
			}
			newMap.put(obj, newList);
		}
		return newMap;
	}

	private void createAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(ALERT_TITLE);
		alert.setContentText(ALERT_MESSAGE);
		alert.showAndWait();
	}
}