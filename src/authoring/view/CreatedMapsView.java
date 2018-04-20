package authoring.view;

import authoring.backend.AuthoringController;
import authoring.backend.AuthoringObject;
import authoring.backend.CreatedMaps;
import authoring.backend.DraggableScrollPane;
import authoring.backend.MapEntity;
import authoring.backend.MapSelectionImageView;
import authoring.backend.ObjectSelectionImageView;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import observables.Listener;

public class CreatedMapsView extends ScrollPane implements Listener {
	public static final int THUMBNAIL_WIDTH = 150;
	public static final int THUMBNAIL_HEIGHT = 150;
	private AuthoringController authoringcontroller;
	private DraggableScrollPane dragscroll;
	private CreatedMaps createdmaps;
	
	public CreatedMapsView(AuthoringController ac, CreatedMaps cm) {
		this.authoringcontroller = ac;
		this.dragscroll = ac.getScroll();
		this.createdmaps = cm;
		cm.addListener(this);
		setupBox();
	}
	
	private void setupBox() {
		VBox box = new VBox();
		int size = createdmaps.getSize();
		for (int i=0; i<size; i++) {
			box.getChildren().add(setupIndivBox(createdmaps.getObjectByIndex(i)));
		}
		if (size != 0) {
			this.setContent(box);
		}
	}
	
	private VBox setupIndivBox(MapEntity map) {
		VBox box = new VBox();
		box.getChildren().add(extractImage(map));
		box.getChildren().add(new Text(extractName(map)));
		box.setPadding(new Insets(10, 10, 0, 10));
		return box;
	}
	
	private MapSelectionImageView extractImage(MapEntity map) {
		MapSelectionImageView imgview = new MapSelectionImageView(map, dragscroll, authoringcontroller);
		imgview.setFitWidth(THUMBNAIL_WIDTH);
		imgview.setFitHeight(THUMBNAIL_HEIGHT);
		return imgview;
	}
	
	private String extractName(MapEntity map) {
		return map.getName();
	}

	@Override
	public void update() {
		setupBox();
	}

}