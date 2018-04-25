package gui_elements.buttons;

import authoring.backend.AuthoringObject;
import authoring.backend.CreatedObjects;
import authoring.backend.TagController;
import gui_elements.combo_boxes.MainComboBox;
import gui_elements.tabs.DesignTab;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateComponentButton extends MainButton {

	private static final String FILENAME = "create_component_button.properties";
	private static final String SPACE = " ";
	private static final String IMAGE_PATH_HEADING = "/images/";
	private AuthoringObject authoring_object;
	private static final boolean EXPLICIT_SET_ACTION = false;
	private TextField name_tf, movement_speed_tf;
	private ComboBox<String> tag_cb;
	private Label image_text_label;
	private TagController tag_controller;
	private DesignTab design_tab;
	private ComboBox<String> building_cb;
	private TextField build_time_tf;
	private ComboBox<String> resource_cb;
	private TextField resource_cost_tf;

		
	public CreateComponentButton(AuthoringObject authoring_object, TextField name_tf, ComboBox<String> tag_cb, 
			TagController tag_controller, Label image_text_label, TextField movement_speed_tf, ComboBox<String> building_cb, 
			TextField build_time_tf, ComboBox<String> resource_cb, TextField resource_cost_tf, DesignTab design_tab) {
		super(FILENAME, EXPLICIT_SET_ACTION);
		this.authoring_object = authoring_object;
		this.name_tf = name_tf;
		this.tag_cb = tag_cb;
		this.tag_controller = tag_controller;
		this.image_text_label = image_text_label;
		this.movement_speed_tf = movement_speed_tf;
		this.design_tab = design_tab;
		this.building_cb = building_cb;
		this.build_time_tf = build_time_tf;
		this.resource_cb = resource_cb;
		this.resource_cost_tf = resource_cost_tf;
		setAction();
	}

	@Override
	protected void setAction() {
		getButton().setOnAction(value -> {
			String tag_text = tag_cb.getEditor().getText();
			authoring_object.setName(name_tf.getText());
			for(String tag : tag_text.split(SPACE)) {
				authoring_object.addTag(tag);
				tag_controller.addTag(tag, authoring_object);
				if(!tag_cb.getItems().contains(tag))
					tag_cb.getItems().add(tag);
			}
			authoring_object.setImage(IMAGE_PATH_HEADING + image_text_label.getText());
			authoring_object.setMovementSpeed(Double.parseDouble(movement_speed_tf.getText()));
			authoring_object.setBuilding(Boolean.parseBoolean(building_cb.getValue()));
			authoring_object.setBuildTime(Double.parseDouble(build_time_tf.getText()));
//			authoring_object.setBuildCost(resource_cb.getValue(), Double.parseDouble(resource_cost_tf.getText()));
			CreatedObjects.addObject(authoring_object);
			design_tab.setNewAuthoringObject();
			design_tab.resetComponents();
		});
	}
}