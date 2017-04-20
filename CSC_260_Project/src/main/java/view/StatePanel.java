package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import model.FSM;
import model.StateLocations;
import model.FSMRunner;

/**
*@author Matthew Goff
*/
public class StatePanel extends JPanel
{
	private static final int STATE_WIDTH = 70;
	private static final int STATE_HEIGHT = 70;

	private FSM theStateMachine;
	private String myState;
	private StateLocations stateLocations;
	private FSMRunner theRunner;

	protected StatePanel(FSM theStateMachineParam, String myStateParam, StateLocations stateLocationsParam, FSMRunner theRunnerParam)
	{
		theStateMachine = theStateMachineParam;
		myState = myStateParam;
		stateLocations = stateLocationsParam;
		theRunner = theRunnerParam;

		this.setSize(new Dimension(STATE_WIDTH,STATE_HEIGHT));
	}

	protected void rename(String newName)
	{
		myState = newName;
	}

	public void paint(Graphics g) {
		super.paint(g);
		//g.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		System.out.println("From inside State.paint(): The current state is: "+theRunner.getCurrentState());
		if (myState.equals(theRunner.getCurrentState()))
		{
			g2.setColor(Color.CYAN);
			g2.fillOval(2,2,this.getWidth()-4,this.getHeight()-4);
		}

		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawOval(2,2,this.getWidth()-4,this.getHeight()-4);

		g2.setFont(g2.getFont().deriveFont(40.0f));
		g2.drawString(myState,this.getWidth()/2-11,this.getHeight()/2+14);

		if (theStateMachine.isAcceptingState(myState))
		{
			g2.setColor(Color.BLACK);
			g2.drawOval(13,13,this.getWidth()-26,this.getHeight()-26);
		}

		if (theStateMachine.isStartingState(myState))
		{

			PixelCoords drawingPoint = new PixelCoords(12,this.getHeight()/2);

			g2.setColor(Color.BLACK);
			g2.drawLine(drawingPoint.getX(),drawingPoint.getY(),drawingPoint.getX()-8,drawingPoint.getY()-10);
			g2.drawLine(drawingPoint.getX(),drawingPoint.getY(),drawingPoint.getX()-8,drawingPoint.getY()+10);
		}
	}

	protected PixelCoords getCoords()
	{
		return stateLocations.getStateLocation(myState);
	}

	protected int getUpperLeftX()
	{
		return (getCoords().getX()-this.getWidth()/2);
	}

	protected int getUpperLeftY()
	{
		return (getCoords().getY()-this.getHeight()/2);
	}

	protected PixelCoords getTopCoords()
	{
		return getCoords().moveUp(STATE_HEIGHT/2);
	}

	protected PixelCoords getRightCoords()
	{
		return getCoords().moveRight(STATE_WIDTH/2);
	}

	protected PixelCoords getBottomCoords()
	{
		return getCoords().moveDown(STATE_HEIGHT/2);
	}

	protected PixelCoords getLeftCoords()
	{
		return getCoords().moveLeft(STATE_WIDTH/2);
	}

	protected String getState()
	{
		return myState;
	}

}