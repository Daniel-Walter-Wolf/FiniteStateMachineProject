package model;

import java.util.HashMap;
import java.util.LinkedList;

import view.PixelCoords;

import java.io.Serializable;

/**
* This class will be changed from Pixel Coords to non-pixel coords
*
* A wrapper class for a HashMap which maps States to Coordinates
*
*@author Matthew Goff
*/
public class StateLocations implements Serializable
{
	private static final long serialVersionUID = 7533472543217276147L;

	private HashMap<String,PixelCoords> locations;

	public StateLocations()
	{
		locations = new HashMap<String,PixelCoords>();
	}

	public void putStateLocation(String state, PixelCoords location)
	{
		locations.put(state,location);
	}

	public PixelCoords getStateLocation(String state)
	{
		return locations.get(state);
	}

	public Iterable<PixelCoords> getLocations()
	{
		return (Iterable<PixelCoords>)locations.values();
	}

	public String toString()
	{
		String returnString = "-----";
		for (String state : locations.keySet())
		{
			returnString+="\t"+state+"-->"+locations.get(state).toString();
		}
		returnString+="-----";

		return returnString;
	}
}