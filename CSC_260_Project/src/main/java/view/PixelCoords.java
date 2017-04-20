package view;

import java.io.Serializable;

/**
* A small class used to store screen coordinates in pixels
*
*@author Matthew Goff
*/
public class PixelCoords implements Serializable
{
	private static final long serialVersionUID = 7533472123427276147L;
	private int xcoord;
	private int ycoord;

	protected PixelCoords(int xcoordParam, int ycoordParam)
	{
		xcoord = xcoordParam;
		ycoord = ycoordParam;
	}

	protected int getX()
	{
		return xcoord;
	}

	protected int getY()
	{
		return ycoord;
	}

	/**
	* I'm not sure this method is called ever since I implemented the methods below.
	*/
	protected PixelCoords move(int xChange, int yChange)
	{
		return new PixelCoords(this.getX()+xChange, this.getY()+yChange);
	}

	protected PixelCoords moveUp(int change)
	{
		return new PixelCoords(this.getX(),this.getY()-change);
	}

	protected PixelCoords moveRight(int change)
	{
		return new PixelCoords(this.getX()+change,this.getY());
	}

	protected PixelCoords moveDown(int change)
	{
		return new PixelCoords(this.getX(),this.getY()+change);
	}

	protected PixelCoords moveLeft(int change)
	{
		return new PixelCoords(this.getX()-change,this.getY());
	}

	protected int verticalDistanceFrom(PixelCoords otherCoords)
	{
		return Math.abs(this.getY()-otherCoords.getY());
	}

	protected int horizontalDistanceFrom(PixelCoords otherCoords)
	{
		return Math.abs(this.getX()-otherCoords.getX());
	}

	public String toString()
	{
		return ("("+this.getX()+","+this.getY()+")");
	}
}