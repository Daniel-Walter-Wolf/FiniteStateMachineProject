package model;

import java.io.Serializable;

public interface Transition extends Serializable
{

	public char getLabel(); //This is the only method needed in this interface

	public String nextState(); //to get the next state from the transition

	public String toString(); //to string


}	