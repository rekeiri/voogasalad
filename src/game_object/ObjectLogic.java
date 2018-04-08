package game_object;

import java.util.ArrayList;
import java.util.List;

import interactions.Interaction;
import transform_library.Vector2;


/**
 * 
 * @author Rayan
 * GameUnit contains all the game logic for the object that the user will be allowed to interact with like
 * attributes and interactions
 */

public class ObjectLogic  
{
	ObjectAttributes attributes;
	
	//interactions should probably be stored in a container object like objectattributes
	List<Interaction> interactions;
	
	public ObjectLogic()
	{
		this.attributes = new ObjectAttributes();
		interactions = new ArrayList<>();
	}
	
	public ObjectAttributes accessAttributes()
	{
		return attributes;
	}
	
	public void executeInteractions(GameObject current, GameObject interactionTarget)
	{
		for(Interaction interaction : interactions)
		{
			if(current.getTransform().getDisplacement(interactionTarget.getTransform()) >= interaction.getRange())
			{
				interaction.executeCustomFunctions(current, interactionTarget);
			}
		}
	}
	
}
