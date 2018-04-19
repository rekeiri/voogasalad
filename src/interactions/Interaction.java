package interactions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import game_engine.EngineObject;
import game_object.GameObject;
import game_object.GameObjectManager;
import game_object.ObjectLogic;
import javafx.scene.image.Image;
import transform_library.Transform;

/**
 * Codes for an action that occurs in the game. Different actions might simulate a GameObject acting on itself or acting on another object
 * @author andrew, Rayan
 *
 */
public class Interaction implements EngineObject {
	
	private int id;
	private List<String> targetTags;
	private String name;
	private Image img;
	private String description;
	
	//store functions by id
	private List<CustomFunction> customFunctions;
	private double range;
	
	public Interaction(int id)
	{
		customFunctions = new ArrayList<>();
		targetTags = new ArrayList<>();
		this.id = id;
	}
	
	
	/**
	 * 
	 * @param type
	 * @return
	 * Adds a custom function to the interaction.
	 * need to add the functionality that only the variables related to those tags can be changed etc.
	 */
	public CustomFunction addCustomFunction(String type)
	{
		CustomFunctionFactory factory = new CustomFunctionFactory();
			
		//this is where i need to make it better
		CustomFunction function = factory.getCustomFunction(type);
		customFunctions.add(function);
		return function;
	}
	
	
	/**
	 * Runs all the custom functions in the interactions
	 * Each custom function can affect the other game object
	 */
	public void executeCustomFunctions(GameObject current, GameObject other)
	{
		if(matchesTags(other, targetTags)) return;
		for(CustomFunction cFunc : customFunctions)
		{
			cFunc.Execute(current, other);
		}
	}
	
	private boolean matchesTags(GameObject other, List<String> tags)
	{
		for(String s : other.getTags())
		{
			if(tags.contains(s)) return true;
		}
		return false;
	}
	
	public void setRange(double range)
	{
		this.range = range;
	}
	
	public double getRange()
	{
		return range;
	}
	
	
 	public List<String> getTargetTags()
 	{
 		return targetTags;
 	}
 	public void addTag(String newTag) 
 	{
 		if(!targetTags.contains(newTag))
 			targetTags.add(newTag);
 	}
 	public void removeTag(String oldTag) 
 	{
 		targetTags.remove(oldTag);
 	}
 	public CustomFunction getCustomFunction(int x) 
 	{
 		return customFunctions.get(x);
 	}


	@Override
	public int getID() {
		return id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Image getImg() {
		return img;
	}


	public void setImg(Image img) {
		this.img = img;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

}
