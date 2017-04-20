package model;

import java.util.LinkedList;
import java.util.Iterator;

import java.io.Serializable;

/**
*a class that represents a Finite State machine
*
*@author Daniel W. Wolf, Matthew Goff, Anton Morozov
*@version 1.0
**/
public class FSM implements Serializable
{
	private static final long serialVersionUID = 7533472925627276147L;
	private LinkedList<State> states;
  private LinkedList<Observer> observers; //This is a bit excessive because we only have one observer but good practice I guess.

	public FSM()
	{
		observers = new LinkedList<Observer>();
		states = new LinkedList<State>();
	}

	/*
 	* getting an object from string
 	* This MUST be private
 	*/
	private State getStateByName(String stateName)
	{
		for (State state:states)
		{
			if((state.getLabel()).equals(stateName))
			{
				return state;
			}
		}
		return null;
	}

	/**
	* FULLY IMPLEMENTED
	*/
	public void addObserver(Observer newObserver)
	{
		observers.add(newObserver);
	}

	/**
	* FULLY IMPLEMENTED
	*/
	private void notifyObservers()
	{
		for (Observer observer: observers)
		{
			observer.notifyObserver();
		}
	}

	public boolean addState(String stateName)
	{
    if (getStateByName(stateName)==null)
    {
      states.addFirst(new State(stateName));
      return true; // Do not call notify listeners here. the MainWindow needs the feedback from this boolean return before it can render.
      
    }
    else
    {
      return false;
    }
		
	}

  /**
  * Remove State from FSM
  *
  * @param stateName state name to remove
  * 
  */
  public void removeState(String stateName)
  {
    State stateToRemove = getStateByName(stateName);
    if (stateToRemove != null)
    {
      states.remove(stateToRemove);
      for (State state:states)
      {
        state.removeState(stateName);
      }
      notifyObservers();  
    }
  }


  public void addTransitionDouble(){
    System.out.println("in double");
    this.addTransition("B","C",'t');

  }

	/**
	* FULLY IMPLEMENTED
	*/
	public void addTransition(String from, String to, char transitionChar)
	{
		Transition newTransition = new CharacterTransition(transitionChar, to);
		getStateByName(from).addTransition(newTransition);
    notifyObservers();
	}

	public void removeTransition(String from, String to, char transitionChar)
 	{
    getStateByName(from).removeTransition(to, transitionChar);
    notifyObservers();
 	}

 /*
 * Change name of the State
 *
 * @Param oldName
 * @Param newName
 * 
 */
  public void renameState(String oldName, String newName)
  {
    State st = getStateByName(oldName);
    State other_state = getStateByName(newName);

    if(st != null && other_state == null) st.changeName(newName); 
  }

 /**
 *
 * Set Accepting state on
 *
 * @Param stateName
 * 
 */
  public void makeAcceptingState(String stateName)
  {
  State st = getStateByName(stateName);
  if(st !=null)st.makeAcceptingState(); 
  notifyObservers();
  }

 /**
 *
 * Set Accepting state off
 *
 * @Param stateName
 * 
 */
  public void removeAcceptingState(String stateName)
  {
  State st = getStateByName(stateName);
  if(st != null)st.makeNotAcceptingState();
  notifyObservers();
  }

 /**
 *
 * Set Starting state on
 *
 * @Param stateName
 * 
 */
  public void makeStartingState(String stateName)
  {
  State st = getStateByName(stateName);
  if(st != null)st.makeStartingState();
  notifyObservers();
  System.out.println(stateName+" is now a starting state");
  }

 /**
 *
 * Set Starting state off
 *
 * @Param stateName
 * 
 */
  public void removeStartingState(String stateName)
  {
  State st = getStateByName(stateName);
  if(st != null)st.makeNotStartingState();
  notifyObservers();
  }

/**
 * Return Accepting state
 *
 *@param stateName
 * 
 */
 public boolean isAcceptingState(String stateName)
  {
  State st = getStateByName(stateName);
  if(st != null)
  {
   return st.isAcceptingState();
  }
   return false;
  }

 /**
 *
 * Return Starting state
 *
 * @Param stateName
 * 
 */
  public boolean isStartingState(String stateName)
  {
  State st = getStateByName(stateName);
  if(st != null)
  {
   return st.isStartingState();
  }
   return false;
  }



/**
 *
 * Return char array of transitions
 *
 * @param stateName
 * @return char array of transitions  
 */
/*
  public char[] getTransitions(String stateName)
  {
    State st = getStateByName(stateName);
    if(st != null)
    {
      LinkedList<Transition> stateTransitions = st.getTransitions();
      char[] returnList = new char[stateTransitions.size()];
      for (int i=0;i<returnList.length;i++)
      {
        returnList[i]=stateTransitions.getFirst().getLabel();
        stateTransitions.removeFirst();
      }
      return returnList;
    }
    return null;
  }
  */

  public LinkedList<Character> getTransitions(String stateName){
    State st = getStateByName(stateName);
    return st.getTransitions();

  }

/**
 *
 * Iterator, return next State to current
 *
 * @param stateName
 * @param transition
 *
 * @return LinkedList<String>   
 */
  public LinkedList<String> getNextStates(String stateName, char transitionChar)
  {
    State st = getStateByName(stateName);

    

    if(st != null){
      return st.getNextState(transitionChar);
    }
    return null;
    /*
    if(st != null)
    {
      return st.getNextState(new CharacterTransition(transitionChar)); 
    }
    return null;

    */
  }

 	/**
 	* Returns null if there is more than one starting state.
 	* FULLY IMPLEMENTED
 	*/
 	protected State getStartingState()
 	{
 		State startingState = null;

 		for (State state: states)
 		{
 			if (state.isStartingState())
 			{
 				if (startingState!=null)//If there is more than one starting state
 				{
 					return null;
 				}
 				else
 				{
 					startingState = state;
 				}
 			}
 		}
 		
 		return startingState; //If there were no starting states than we return null anyway;
 	}


	/**
	* FULLY IMPLEMENTED
	*/
	public LinkedList<String> getStates()
	{
		LinkedList<String> returnList = new LinkedList<String>();

		for (State state:states)
		{
			returnList.add(state.getLabel());
		}

		return returnList;
	}

	public String toString()
	{
		String returnString = "FSM:\n----";

		for (State state: states)
		{
      if (isStartingState(state.getLabel()))
      {
        returnString+="\n\t>"+state.getLabel();
      }
      else
      {
        returnString+="\n\t"+state.getLabel();
      }
			
      for (Character transitionChar : state.getTransitions())
      {
        returnString+="("+transitionChar+","+state.getNextState(transitionChar.charValue())+")";
      }
		}

		return returnString+"\n----";
	}

}