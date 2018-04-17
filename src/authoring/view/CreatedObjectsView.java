package authoring.view;

import authoring.backend.AuthoringController;
import authoring.backend.AuthoringObject;
import authoring.backend.CreatedObjects;
import authoring.backend.DraggableImageView;
import authoring.backend.DraggableScrollPane;
import authoring.backend.MapEntity;
import authoring.backend.SelectionImageView;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import observables.Listener;

public class CreatedObjectsView extends ScrollPane implements AuthoringView, Listener {
	public static final int THUMBNAIL_WIDTH = 150;
	public static final int THUMBNAIL_HEIGHT = 150;
	private CreatedObjects createdobjects;
	private MapEntity map;
	
	public CreatedObjectsView(AuthoringController ac, CreatedObjects cb) {
		createdobjects = cb;
		map = ac.getMap();
		cb.addListener(this);
		setupBox();
	}
	
	private void setupBox() {
		VBox box = new VBox();
		int size = createdobjects.getSize();
		for (int i=0; i<size; i++) {
			box.getChildren().add(setupIndivBox(createdobjects.getObjectByIndex(i)));
		}
		if (size != 0) {
			this.setContent(box);
		}
		return;
	}
	
	private VBox setupIndivBox(AuthoringObject obj) {
		VBox box = new VBox();
		box.getChildren().add(extractImage(obj));
		box.getChildren().add(new Text(extractName(obj)));
		box.setPadding(new Insets(10, 10, 0, 10));
		return box;
	}
	
	private SelectionImageView extractImage(AuthoringObject obj) {
		SelectionImageView imgview = new SelectionImageView(obj, map);
		imgview.setFitWidth(THUMBNAIL_WIDTH);
		imgview.setFitHeight(THUMBNAIL_HEIGHT);
		return imgview;
	}
	
	private String extractName(AuthoringObject obj) {
		return obj.getName();
	}

	@Override
	public void update() {
		setupBox();
	}
}