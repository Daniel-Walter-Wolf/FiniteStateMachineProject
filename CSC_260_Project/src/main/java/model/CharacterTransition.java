package model;

import java.io.Serializable;

public class CharacterTransition implements Transition
{
	private static final long serialVersionUID = 1233472925644276147L;

	private char myChar;

	private String nextState;

	public CharacterTransition(char myCharParam, String nextStateParam)
	{
		myChar = myCharParam;
		nextState = nextStateParam;
	}

	public boolean equals(Object other)
	{
		if (other == null)
		{
			return false;
		}
		else
		{
			if (other instanceof CharacterTransition)
			{
				CharacterTransition that = (CharacterTransition) other;
				if (this.myChar==that.myChar)
				{
					return true;
				}
				else
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}
	}

	public char getLabel()
	{
		return myChar;
	}


	public String nextState(){
		return nextState;
	}

	public String toString(){
		return String.valueOf(myChar);
	}
}