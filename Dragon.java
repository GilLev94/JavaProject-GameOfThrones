package gamesofthrones;


import java.util.Random;

public class Dragon {

	private int id;
	private static int numberOfDragons = 0;
	private String team;
	private int xPos;
	private int yPos;
	private boolean direction; // true - from 0 to end : false from Ent to 0
	private String name; // the name of the dragon drg0, drg1 and so on
	private boolean isAlive; // flag to state
	private int currentStep;

	public Dragon(int x, int y, String color, boolean direction, String name) {
		this.xPos = x;
		this.yPos = y;
		this.direction = direction;
		this.name = name;
		this.team = color;
		this.isAlive = true;
		this.currentStep = 0;
		this.id = ++numberOfDragons;
	}

	public Dragon() {
		this.xPos = 0;
		this.yPos = 0;
		this.team = "";
		this.isAlive = false;
		this.direction = true;
		this.name = "";

	}

	public int getXPos() {
		return this.xPos;
	}

	public void setXPos(int x) {
		this.xPos = x;
	}

	public int getYPos() {
		return this.yPos;
	}

	public void setYPos(int y) {
		this.yPos = y;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean getIsAlive() {
		return this.isAlive;
	}

	public int getNextYPos() {
		int nextYPosition = this.yPos;
		if (this.currentStep != 1)// The dragon advances on the y axis twice each turn.
		{
			nextYPosition += (this.direction) ? 1 : -1;
		}

		return nextYPosition;

	}

	public int getNextXPos()// Gives the next position on the x axis, which the dragon will fly to.
	{
		int nextXPosition = this.xPos;

		if (this.currentStep == 1) {
			Random r = new Random();
			return (r.nextBoolean() == true) ? nextXPosition + 1 : nextXPosition - 1;
		}

		return nextXPosition;

	}

	public String getTeam() {
		return this.team;
	}

	public String getName() {
		return this.name;
	}

	public void setStep(int i) {
		this.currentStep = i;
	}

	public int getId() {
		return this.id;
	}

}

	
	


