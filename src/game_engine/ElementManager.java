package game_engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author Rayan
 *
 * @param <E>
 * 
 * Manager interface for creating engine managers like gameobject manager etc.
 * Manager itself can only set the id for the gameobject 
 */

public abstract class ElementManager {
	
	private Map<Integer, EngineObject> elementMap;
	
	public ElementManager()
	{
		elementMap = new HashMap<Integer, EngineObject>();
	}
	
	/**
	 * 
	 * @return
	 * Allows the manager to calculate the id before adding
	 */
	protected int calculateID()
	{
		if(elementMap.isEmpty()) return 1;
		return elementMap.size() + 1;
	}
	
	/**
	 * 
	 * @param element
	 * Remove that element from the manager
	 */
	public void removeElement(EngineObject element)
	{
		elementMap.remove(element.getID());
	}
	
	/**
	 * 
	 * @return
	 * Returns a list of key value pairs from the manager which can be accessed 
	 */
	protected List<EngineObject> getElementsRaw() 
	{
		List<EngineObject> elementList = new ArrayList<>();
		
		for(Map.Entry<Integer, EngineObject> var : elementMap.entrySet())
		{
			elementList.add(var.getValue());
		}
		
		return Collections.unmodifiableList(elementList);
	}	
	
	public EngineObject get(int id) 
	{
		return elementMap.get(id);
	}
	
	/**
	 * 
	 * @return
	 * Allows subclasses to directly access the map, but prevents objects that call the manager from directly accessing it
	 */
	public void addElement(EngineObject obj)
	{
		elementMap.put(obj.getID(), obj);
	}
	
	/**
	 * Clears the element database
	 */
	public void clearManager()
	{
		elementMap.clear();
	}

}
