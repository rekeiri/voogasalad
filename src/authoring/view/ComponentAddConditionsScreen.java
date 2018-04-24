package authoring.view;

import javafx.geometry.Insets;
import java.util.ResourceBundle;
import javax.swing.JComboBox;
import authoring.backend.AuthoringObject;
import authoring.backend.ButtonFactory;
import authoring.backend.ConditionStatement;
import conditions.Comparator;
import conditions.ComparatorManager;
import conditions.ConditionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ComponentAddConditionsScreen implements AuthoringView {
	private final String PROPERTY_FILENAME = "data/component_add_conditions_screen.properties";
	private final Paint BACKGROUND = Color.WHITE;
	private final String TITLE = "Create Component Attributes";
	private final int[] COLUMNS = {0, 2, 4, 10};
	private ConditionManager conditionManager;
	private ComparatorManager comparatorManager;
	private Group root;
	private GridPane grid;
	private int currentRow;

	public ComponentAddConditionsScreen(ConditionManager cm) {
		conditionManager = cm;
		comparatorManager = new ComparatorManager();
		currentRow = 0;
//		getProperties();
		initializeScreen();
		newPane();
		setupButtons();
	}
	
	private void getProperties() {
//		myResources = ResourceBundle.getBundle(PROPERTY_FILENAME);
	}
	
	private void initializeScreen() {
		root = new Group();
		Scene scene = new Scene(root, PANEL_WIDTH, PANEL_HEIGHT, BACKGROUND);
		scene.getStylesheets().add(STYLE_PATH);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.show();
	}
	
	private void newPane() {
		grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(100, 50, 100, 50));
		root.getChildren().add(grid);
		setTitles();
		newLine();
	}
	
	private void setTitles() {
		addLine(new Label("Attribute"), new Label("Comparator"), new Label("Value"), new Label("Condition Triggered"));
	}
	
	private void newLine() {
		addLine(new TextField(), comparatorSelect(), new TextField(), customConditionSelect());
	}
	
	private void addLine(Node a, Node b, Node c, Node d) {
		grid.add(a, COLUMNS[0], currentRow);
		grid.add(b, COLUMNS[1], currentRow);
		grid.add(c, COLUMNS[2], currentRow);
		grid.add(d, COLUMNS[3], currentRow);
		currentRow += 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ComboBox comparatorSelect() {
		ComboBox box = new ComboBox();
		ObservableList<String> comparatorSigns = FXCollections.observableArrayList(comparatorManager.getComparatorSigns());
		box.setItems(comparatorSigns);
		return box;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private ComboBox customConditionSelect() {
		ComboBox box = new ComboBox();
		ObservableList<String> comparatorSigns = FXCollections.observableArrayList(conditionManager.availableCustomConditions());
		box.setItems(comparatorSigns);
		return box;
	}
	
	private void setupButtons() {
		HBox box = new HBox();
		Button addLineButton = ButtonFactory.makeButton("Add Condition", e -> newLine());
		Button saveButton = ButtonFactory.makeButton("Save", e -> saveConditions()); 
		box.getChildren().addAll(addLineButton, saveButton);
		box.setPadding(new Insets(10, 10, 10, 10));
		box.setSpacing(5);
		root.getChildren().add(box);
		box.toFront();
	}
	
	private void saveConditions() {
		for (int i=1; i<currentRow; i++) {
			String attribute = extractTextField(findNode(i,COLUMNS[0]));
			int comparatorId = extractComparatorId(findNode(i,COLUMNS[1]));
			String value = extractTextField(findNode(i,COLUMNS[2]));
			String conditionString = extractComboBox(findNode(i,COLUMNS[3]));
			int conditionId = conditionManager.createCondition(comparatorId, attribute, value);
			conditionManager.getCondition(conditionId).addCustomCondition(conditionString);

		}
	}
	private String extractTextField(Node n) {
		if (n instanceof TextField) {
			return ((TextField) n).getText();
		}
		return "";
	}
	
	private int extractComparatorId(Node n) {
		if (n instanceof ComboBox) {
			String symbol = (String) ((ComboBox) n).getValue();
			return matchComparatorId(symbol);
		}
		return 0;
	}
	
	private String extractComboBox(Node n) {
		if (n instanceof ComboBox) {
			String symbol = (String) ((ComboBox) n).getValue();
			return symbol;
		}
		return "";
	}
	
	private int matchComparatorId(String symbol) {
		for (Comparator c: comparatorManager.getComparators()) {
			if (c.getSign().equals(symbol)) {
				return c.getID();
			}
		}
		return -1;
	}
		
	private Node findNode(int row, int col) {
		Node result = null;
		for (Node node : grid.getChildren()) {
			if (node instanceof TextField | node instanceof ComboBox) {
				if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
		            result = node;
		            break;
		        }
			}
	    }
	    return result;
	}	
}