package gui_elements.buttons;

import authoring.view.InteractionAddCustomFunctionsScreen;
import authoring.view.ComponentAddInteractionsScreen;
import interactions.InteractionManager;

public class AddCustomFunctionsButton extends MainButton {

	private static final String FILENAME = "add_custom_functions_button.properties";
	private InteractionManager interaction_manager;
	private ComponentAddInteractionsScreen component_add_interactions_screen;
	private static final boolean EXPLICIT_SET_ACTION = false;

	public AddCustomFunctionsButton(InteractionManager interaction_manager, ComponentAddInteractionsScreen component_add_interactions_screen) {
		super(FILENAME, EXPLICIT_SET_ACTION);
		this.interaction_manager = interaction_manager;
		this.component_add_interactions_screen = component_add_interactions_screen;
		setAction();
	}

	@Override
	protected void setAction() {
		getButton().setOnAction(value -> {
			new InteractionAddCustomFunctionsScreen(interaction_manager, component_add_interactions_screen.getCurrentInteractionID());
		});
	}
}