
package model;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashMap;

import java.io.Serializable;

public class State implements Serializable{

	private static final long serialVersionUID = 7533492725627111147L;

	private boolean startingState;
	private boolean acceptingState;
	private String myLabel;
	private LinkedList<Transition> transitions;
	//private HashMap<Transition, LinkedList<String>> transitions;
	/*This has to be from Transition to String, not from Transition to state
	because States can't hold other states, that would result in infinite
	memory useage.
	*/

	protected State(String myLabelParam){
		myLabel = myLabelParam;
		//transitions = new HashMap<Transition,LinkedList<String>>();
		transitions = new LinkedList<Transition>();

	}

	/**
	*
	*gets the label for the State in String form
	*@return String
	*/
	protected String getLabel()
	{
		return myLabel;
	}

	protected void changeName(String newName)
	{
		myLabel = newName;
	}

	/**
	* Remove all references to a given state from our transitions hashmap
	* FULLY IMPLEMENTED
	*/
	protected void removeState(String stateLabel)
	{
		/*
		LinkedList<Transition> transitionList = this.getTransitions();
		LinkedList<String> nextStates;


		for (Transition transition: transitionList)
		{
			nextStates = transitions.get(transition);
			nextStates.remove(stateLabel);
		}
		*/
	}

	/**
	* FULLY IMPLEMENTED
	*/
	protected void addTransition(Transition t)
	{
		if(!transitions.contains(t)){
			transitions.add(t);
		}
		System.out.print(transitions);

		
		/*if (transitions.containsKey(t))
		{
			transitions.get(t).add(to);
		}
		else
		{
			LinkedList<String> newList = new LinkedList<String>();
			newList.add(to);
			transitions.put(t, newList);
		}
		*/
		
	}

	protected void removeTransition(String to, char transitionChar)
	{

	}

	/**
	* Returns the state which occur after this state by transition t
	*/
	protected LinkedList<String> getNextState(char t)
	{
		LinkedList<Transition> transList = getTransitionFromChar(t);
		LinkedList<String> newSL = new LinkedList<String>();

    	
    	for(int x = 0; x < transList.size(); x++){
      	newSL.add(transList.get(x).nextState());
    	}
    	return newSL;
    	
	}

	/**
	* Simply returns all of the keys from the hash map
	*/
	protected LinkedList<Character> getTransitions()
	{
		LinkedList<Character> newCharList = new LinkedList<Character>();
		for( int x = 0; x < transitions.size() ; x++){
			newCharList.add(transitions.get(x).getLabel());
		}
		return newCharList;
		//Set<Transition> transSet = transitions.keySet();
		//return iterableToLinkedList(transSet);
	}
	
	//FULLY IMPLEMENTED
	protected void makeAcceptingState()
	{
		acceptingState = true;
	}
	
	//FULLY IMPLEMENTED
	protected void makeNotAcceptingState()
	{
		acceptingState = false;
	}
	
	//FULLY IMPLEMENTED
	protected boolean isAcceptingState()
	{
		return acceptingState;
	}

	//FULLY IMPLEMENTED
	protected void makeStartingState()
	{
		startingState = true;
	}

	//FULLY IMPLEMENTED
	protected void makeNotStartingState()
	{
		startingState = false;
	}

	//FULLY IMPLEMENTED
	protected boolean isStartingState()
	{
		return startingState;
	}
	



	private LinkedList<Transition> iterableToLinkedList(Iterable<Transition> iter){

        Iterator<Transition> elements = iter.iterator();
        LinkedList<Transition> elementList = new LinkedList<Transition>();

        while(elements.hasNext()){
            elementList.add(elements.next());
        }
        return elementList;
    }

    private LinkedList<Transition> getTransitionFromChar(char t){
    	LinkedList<Transition> returnTransitions = new LinkedList<Transition>();
    	for (int x = 0; x < transitions.size(); x++){
    		if (transitions.get(x).getLabel() == t){
    			returnTransitions.add(transitions.get(x));
    		}
    	}
    	return returnTransitions;

    }
    
    public String toString()
    {
    	return this.getLabel();
    }

}