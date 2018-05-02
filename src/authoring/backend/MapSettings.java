package authoring.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import authoring.view.AuthoringView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scenemanager.EndCondition;
import scenemanager.SceneManager;

public class MapSettings implements AuthoringView {
	private String mapName;
	private int numPlayers;
	private List<EndCondition> endConditions;
	private int mapwidth;
	private int mapheight;
	private String imagePath;
	@XStreamOmitField
	private transient MapEntity map;
	public MapSettings() {
		initializeAll();
	}
	
	private void initializeAll() {
		mapName = "Default map";
		numPlayers = 1;
		endConditions = new ArrayList<>();
		mapwidth = 1000;
		mapheight = 1000;
		imagePath = "/images/tt.jpg";
	}
	
	public void updateSettings(String mapName, int numPlayers, String imagePath, int mapwidth, int mapheight) {
		this.mapName = mapName;
		this.numPlayers = numPlayers;
		this.imagePath = imagePath;
		this.mapwidth = mapwidth;
		this.mapheight = mapheight;
		matchToSize(map);
		updateMapByImage(map);
	}
	
	public void setMap(MapEntity map) {
		this.map = map;
	}
	
	public void matchToSize(MapEntity map) {
		if (this.map == null) {
			setMap(map);
		}
		map.setPrefSize(mapwidth, mapheight);
	}
	
	public void setMapByImage(MapEntity map) {
		if (this.map == null) {
			setMap(map);
		}
		System.out.println(imagePath);
		ImageView image = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
		map.getChildren().add(image);
		image.toBack();
	}
	
	public void updateMapByImage(MapEntity map) {
		setMap(map);
		ImageView image = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
		map.getChildren().remove(0);
		map.getChildren().add(image);
		image.toBack();

	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public String getName() {
		return mapName;
	}
	public int getNumPlayers() {
		return numPlayers;
	}
	public int getMapHeight() {
		return mapheight;
	}
	public int getMapWidth() {
		return mapwidth;
	}

	public List<EndCondition> getEndConditions() {
		return endConditions;
	}
	
}
