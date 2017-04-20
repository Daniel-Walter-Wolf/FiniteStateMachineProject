package model;

import java.util.LinkedList;

/**
*
*
*/
public class FSMRunner
{
	private FSM theMachine;
	private boolean running;
	private LinkedList<String> currentStates;
	private LinkedList<Observer> observers;
	private boolean inAcceptingState;
	

	public FSMRunner(FSM theMachineParam)
	{
		theMachine = theMachineParam;
		running = false;
		inAcceptingState = false;
		currentStates = new LinkedList<String>();
		observers = new LinkedList<Observer>();
	}

	public void addObserver(Observer newObserver)
	{
		observers.add(newObserver);
	}

	/**
 	* FULLY IMPLEMENTED
 	*/
 	public boolean run()
 	{
 		if (!this.isRunning()) //only a precaution, the Window will not offer the run option if its already running.
 		{
 			State startingState = theMachine.getStartingState();
 			if (startingState==null)
 			{
 				return false; //The Window will notify the user that the FSM couldn't run because there wasn't an appropriate starting state
 			}
 			else
 			{
 				currentStates.add(startingState.toString());
 				running = true;
 				notifyObservers();
 				return true;
 			}
 		}
 		else
 		{
 			return false;
 		} 		
 	}

 	private void notifyObservers()
 	{
 		for (Observer observer: observers)
		{
			observer.notifyObserver();
		}
 	}

 	/**
 	* FULLY IMPLEMENTED
 	*/
 	public void stop()
 	{
 		if (this.isRunning()) //only a precaution, the Window will not offer the stop option if it isn't running.
 		{
 			System.out.println("we are stopping");
 			running = false;
 			currentStates = null;
 		}

 		notifyObservers();
 	}

 	public void acceptInput(char inputChar)
 	{
 		System.out.println("Runner got input: "+inputChar);
 		if(isRunning())
 		{
 			for(int x = 0; x <currentStates.size(); x++){
 				LinkedList<Character> newTrans = theMachine.getTransitions(currentStates.get(x));
 				for(int y = 0; y < newTrans.size(); y++){
 					if(newTrans.get(y) == inputChar){
 						LinkedList<String> newStates = theMachine.getNextStates(currentStates.get(x), inputChar);
 						currentStates.addAll(newStates);
 					}
 				}
 				currentStates.remove(currentStates.get(x));
 			}
 		}
 		System.out.println("CurrentStates = "+currentStates.getFirst());
 	}

 	/**
 	* FULLY IMPLEMENTED
 	*/
 	public boolean isRunning()
 	{
 		System.out.println("From within FSMRunner.isRunning(): running = "+String.valueOf(running));
 		return running;
 	}


 	//This should really return a linkedList of Strings, but for the sake of example:
 	public String getCurrentState()
 	{
 		if (currentStates == null || currentStates.isEmpty())
 		{
 			return null;
 		}
 		else
 		{
 			return currentStates.getFirst();
 		}
 	}

 	public boolean inAcceptingState()
 	{
 		if (currentStates.isEmpty())
 		{
 			return false;
 		}
 		else
 		{
 			return (theMachine.isAcceptingState(currentStates.getFirst()));
 		}
 	}
}