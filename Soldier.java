package gamesofthrones;

public class Soldier {

	private int id;
	private int xPos;
	private int yPos;
	private String team;
	private boolean isAlive;
	private boolean direction;
	private String name;
	private static int numberOfSoldiers = 0;

	public Soldier(int x, int y, String team, boolean direction, String name) {
		this.xPos = x;
		this.yPos = y;
		this.team = team;
		this.direction = direction;
		this.name = name;
		this.isAlive = true; // flag to state
		this.id = ++numberOfSoldiers;

	}

	// default constructor
	public Soldier() {
		this.xPos = 0;
		this.yPos = 0;
		this.team = "";
		this.isAlive = false;
		this.direction = true;
		this.name = "";

	}

	public int getX() {
		return this.xPos;
	}

	public int getY() {
		return this.yPos;
	}

	public void setY(int y) {
		this.yPos = y;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean getIsAlive() {
		return this.isAlive;
	}

	public String getTeam() {
		return this.team;
	}

	public String getName() {
		return this.name;
	}

	public int getNextYPos()// Gives the next position on the y axis, which the soldier will walk to.
	{
		int nextY = this.yPos;
		nextY += (this.direction) ? 1 : -1;
		return nextY;
	}

	public int getId() {
		return this.id;
	}

}


