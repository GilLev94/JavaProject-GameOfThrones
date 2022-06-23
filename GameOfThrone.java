package gamesofthrones;

import java.awt.Point;
import java.util.Scanner;

public class GameOfThrone {
    private int boardSize = 0;
    private String[][] Board;
    private int stepNumber;
    private boolean isGameOver;

    private Soldier[] soldiers;
    private int lastIndexOfSloiders;
    private int lastIndexOfDragons;
    private Dragon[] dragons;

    public GameOfThrone(int size) {
        this.boardSize = size;
        // padding the Board to in order to calculate Dragon jumps
        this.Board = new String[this.boardSize + 2][this.boardSize + 2];
        this.isGameOver = false;
        this.dragons = new Dragon[boardSize * boardSize];
        this.soldiers = new Soldier[boardSize * boardSize];
        this.lastIndexOfSloiders = 0;
        this.lastIndexOfDragons = 0;
        this.stepNumber = 0;
        this.isGameOver = false;
        this.initBoard();
    }

    private void initBoard() {
        // Methos to fill the actuall Board with init *
        for (int i = 0; i < this.boardSize + 2; i++)
            for (int j = 0; j < this.boardSize + 2; j++)
                Board[i][j] = "*";
    }

    private void addDragon(int x, int y, String team, boolean direction, String name) {
        // private Method for assing a dragon to the list of dragon as place it on the
        // Board
        Dragon dragon = new Dragon(x, y, team, direction, name);
        this.dragons[this.lastIndexOfDragons++] = dragon;

        // this.Board[dragon.getXPos()][dragon.getYPos()] = name;
        this.Board[dragon.getYPos()][dragon.getXPos()] = name;
    }

    private int getIndexOfPiece(int x, int y, boolean isSoldier) {
        // helper function to get index by location on the board
        // case - we look for solider
        if (isSoldier) {
            for (int i = 0; i < soldiers.length; i++) {
                if (this.soldiers[i].getIsAlive() && this.soldiers[i].getX() == x && this.soldiers[i].getY() == y) {
                    return i;
                }
            }
        } else {
            // we look for dragon
            for (int i = 0; i < dragons.length; i++) {
                if (this.dragons[i].getIsAlive() && this.dragons[i].getXPos() == x && this.dragons[i].getYPos() == y) {
                    return i;
                }
            }
        }
        return -1;// we didn't find the piece
    }

    private void addSoldier(int x, int y, String team, boolean direction, String name) {
        // add a soldier to the list of soldiers and place it on the board
        Soldier soldier = new Soldier(x, y, team, direction, name);
        this.soldiers[this.lastIndexOfSloiders++] = soldier;
        this.Board[soldier.getY()][soldier.getX()] = name;
    }

    private void dragonFightsSoldier(String name, int nextY, int nextX, int i) {
        // dragon fight an opponent soldier
        int opponent = getIndexOfPiece(nextX, nextY, true);
        System.out.printf("\nConflicts Charecters: %s and %s\n", this.Board[nextY][nextX], name);
        System.out.printf("%s Removed! \n", this.Board[nextY][nextX]);

        this.Board[nextY][nextX] = this.dragons[i].getName();
        soldiers[opponent].setIsAlive(false);
        this.Board[soldiers[opponent].getY()][this.soldiers[opponent].getX()] = "*";
    }

    private void soldierFightsDragon(String name, int nextY, int nextX, int i) {// A method for when a soldier meets and
        // solider encounter opponent dragon.
        System.out.printf("\nConflicts Charecters: %s and %s\n", this.Board[nextY][nextX], name);
        System.out.printf("%s Removed! %s Removed!\n", this.Board[nextY][nextX], name);
        this.Board[this.soldiers[i].getY()][this.soldiers[i].getX()] = "*";
        this.soldiers[i].setIsAlive(false);
    }

    private void soldierFightsSoldier(String name, int nextY, int nextX, int i) {// A method for when a soldier meets
        // opposite team soldiers fights
        int oppositeSoldier = getIndexOfPiece(nextX, nextY, true);
        System.out.printf("\nConflicts Charecters: %s and %s\n", this.Board[nextY][nextX], name);
        System.out.printf("%s Removed! %s Removed!\n", this.Board[nextY][nextX], name);
        this.Board[this.soldiers[i].getY()][this.soldiers[i].getX()] = "*";
        this.soldiers[i].setIsAlive(false);
        this.Board[nextY][nextX] = "*";
        this.soldiers[oppositeSoldier].setIsAlive(false);
    }

    private int dragonOnPadedX(int nextXPos) {
        // Handling dragon Jumping from the matrix to the padding zone - hance shows on
        // the other side of the board

        if (nextXPos == 0) {
            nextXPos = Board.length - 2;
            return nextXPos;
        }
        if (nextXPos > Board.length - 2) {
            nextXPos = 1;
            return nextXPos;
        }
        return nextXPos;
    }

    private int dragonOnPadedY(int nextYPos) {
        // Handling dragon Jumping from the matrix to the padding zone - hance shows on
        // the other side of the board
        if (nextYPos == 0) {
            nextYPos = Board.length - 2;
            return nextYPos;
        }
        if (nextYPos > Board.length - 2) {
            nextYPos = 1;
            return nextYPos;
        }
        return nextYPos;
    }

    private void advanceDragon(int nextY, int nextX, int i) {
        // helper method to advance the dragon 3 squares
        Board[this.dragons[i].getYPos()][this.dragons[i].getXPos()] = "*";
        this.dragons[i].setXPos(nextX);
        this.dragons[i].setYPos(nextY);
        Board[nextY][nextX] = dragons[i].getName();
    }

    private void dragonFightsDragon(String name, int nextY, int nextX, int i) {
        // method is called when dragon meet an opponent dragon.
        int opponent = getIndexOfPiece(nextX, nextY, false);
        System.out.printf("\nConflicts Charecters: %s and %s\n", Board[nextY][nextX], name);
        System.out.printf("%s Removed! %s Removed!\n", Board[nextY][nextX], name);

        Board[this.dragons[i].getYPos()][this.dragons[i].getXPos()] = "*";
        this.dragons[opponent].setIsAlive(false);
        this.Board[nextY][nextX] = "*";
        this.dragons[i].setIsAlive(false);
        ;
    }

    public void AddDragons(String color, boolean directions) {
        // method for getting user input for the dragon
        Scanner scn = new Scanner(System.in);
        int numOfDragons = scn.nextInt();
        System.out.printf("Adding %d of %s Dragons to the game\n", numOfDragons, color);
        for (int i = 0; i < numOfDragons; i++) {
            System.out.printf("Please enter the X position of dragon # %d: ", i + 1);
            int x = scn.nextInt();
            System.out.printf("Please enter the Y position of dragon # %d: ", i + 1);
            int y = scn.nextInt();
            System.out.println();
            String name = color + "_Drg" + Integer.toString(i);
            addDragon(x + 1, y + 1, color, directions, name); // considering the padded matrix we add 1
            System.out.printf("Dragon # %d added to the boarde\n", i + 1);

        }
        System.out.println();
    }

    public void AddDragons(String color, boolean directions, String dragonsList) {
        // method for getting File input for the soldiers
        String[] parts = dragonsList.split(" ");
        int numOfDragons = Integer.parseInt(parts[0]);

        for (int i = 1; i < parts.length - 1; i += 2) {
            String name = color + "_Drg" + Integer.toString(i / 2 + 1);
            addDragon(Integer.parseInt(parts[i + 1]) + 1, Integer.parseInt(parts[i]) + 1, color, directions, name);
            System.out.println("Dragons Created Successfully!");
        }
        System.out.println();
    }

    public void AddSoldiers(String color, boolean direction) {
        // method for getting user input for the dragon
        Scanner s = new Scanner(System.in);
        int numOfSoldiers = s.nextInt();
        
      

        Scanner scn = new Scanner(System.in);
        System.out.printf("Adding %d of %s Soldiers to the game\n", numOfSoldiers, color);
        for (int i = 0; i < numOfSoldiers; i++) {
            System.out.printf("Please enter the X position of soldier # %d: ", i + 1);
            int x = scn.nextInt();
            System.out.printf("Please enter the Y position of soldier # %d: ", i + 1);
            int y = scn.nextInt();
            
            

            String name = color + "_Sol" + Integer.toString(i);
            // considering the padded matrix we add 1
            addSoldier(x + 1, y + 1, color, direction, name);
            System.out.printf("\nSoldier # %d added to the Board\n", i + 1);

        }
        System.out.println();
    }

    public void AddSoldiers(String color, boolean direction, String soldiersList) {
        // method for getting File input for the soldiers
        System.out.printf("\n%s TEAM LOADING... \n", color);
        String[] parts = soldiersList.split(" ");

        for (int i = 1; i < parts.length - 1; i += 2) {
            String name = color + "_Sol" + Integer.toString(i / 2 + 1);
            // considering the padded matrix we add 1
            addSoldier(Integer.parseInt(parts[i + 1]) + 1, Integer.parseInt(parts[i]) + 1, color, direction, name);
            System.out.println("Soldier Created Successfully!");
        }
        System.out.println();
    }

    public boolean getGameOver() {
        int numActivePieces = 0;
        // game is over when the soldiers are at the edge of the board or in the case
        // that
        // all of them died
        for (int i = 0; i < this.lastIndexOfSloiders; i++) {
            if (soldiers[i].getIsAlive()) {
                if (soldiers[i].getY() == (Board.length - 2) && soldiers[i].getName().toUpperCase().contains("RED")) {
                    System.out.println(
                            "***********************************************************************************");
                    System.out.println(
                            "******************************   Game Over RED team wins **************************");
                    System.out.println(
                            "***********************************************************************************");
                    this.isGameOver = true;
                    return true;
                } else if (soldiers[i].getY() == 1 && soldiers[i].getName().toUpperCase().contains("BLUE")) {
                    System.out.println(
                            "***********************************************************************************");
                    System.out.println(
                            "******************************   Game Over BLUE team wins *************************");
                    System.out.println(
                            "***********************************************************************************");
                    this.isGameOver = true;
                    return true;
                }
            }
        }
        // find out if there are soldier left on board
        for (int i = 0; i < this.Board.length; i++) {
            for (int j = 0; j < this.Board.length; j++) {
                if (this.Board[i][j].contains("Sol")) {
                    numActivePieces++;
                }
                if (numActivePieces > 0) {
                    return false;
                }
            }
        }
        // if there no soliders on board we declare a tie
        if (numActivePieces == 0) {
            System.out.println("***********************************************************************************");
            System.out.println("****************************** Game Over with Tie!!********************************");
            System.out.println("***********************************************************************************");
            this.isGameOver = true;
            return true;
        }

        return false;
    }

    public void printBoard() {
        if (this.stepNumber == 0)
            System.out.printf(
                    "-------------------------------------------------- Init Board ---------------------------------------\n",
                    this.stepNumber++);
        else
            System.out.printf(
                    "------------------------------------------------------ STEP %d --------------------------------------\n",
                    this.stepNumber++);

        System.out.printf("%10.9s", " ");
        for (int i = 0; i < this.Board.length - 2; i++)
            System.out.printf("%10.9s", i);

        System.out.println();
        for (int i = 0; i < this.Board.length - 2; i++) {
            System.out.printf("%10.9s", i);
            for (int j = 0; j < this.Board.length - 2; j++) {
                System.out.printf("%10.9s", this.Board[i + 1][j + 1]);
            }
            System.out.printf("%10.9s\n", i);
        }
        System.out.printf("%10.9s", " ");
        for (int i = 0; i < this.Board.length - 2; i++)
            System.out.printf("%10.9s", i);
        System.out.println(
                "\n-------------------------------------------------=============--------------------------------------\n");
    }

    public boolean getIsGameOver() {
        return this.isGameOver;
    }

    private void advanceSoldier(int nextY, int posX, int i) {
        // method for advancing the soldier one step forward
        Board[this.soldiers[i].getY()][this.soldiers[i].getX()] = "*";
        this.soldiers[i].setY(nextY);
        Board[nextY][posX] = this.soldiers[i].getName();
    }

    public void advanceStep() {
        // this public method is called in order to perform global advancment of the
        // pieses on the board
        for (int i = 0; i < this.lastIndexOfDragons; i++) {
            if (!(this.dragons[i].getIsAlive()))
                continue;
            // The dragon advance 3 squer each time
            // lets check each of it step to verify its new location
            for (int step = 0; step < 3; step++) {
                dragons[i].setStep(step);
                int nextPosX = this.dragons[i].getNextXPos();
                int nextPosY = this.dragons[i].getNextYPos();
                String team = this.dragons[i].getTeam();
                String name = this.dragons[i].getName();

                nextPosX = dragonOnPadedX(nextPosX);
                nextPosY = dragonOnPadedY(nextPosY);

                // Dragon or soldier from opposing after next move
                if (!(Board[nextPosY][nextPosX].contains("*")) && !(Board[nextPosY][nextPosX].contains(team))) {
                    if (Board[nextPosY][nextPosX].contains("Drg")) {
                        dragonFightsDragon(name, nextPosY, nextPosX, i);
                        break;
                    } else if (Board[nextPosY][nextPosX].contains("Sol")) {
                        dragonFightsSoldier(name, nextPosY, nextPosX, i);
                        break;
                    }
                } else if (Board[nextPosY][nextPosX].contains(team)) {
                    break;
                }

                else {
                    advanceDragon(nextPosY, nextPosX, i);
                }
            }
        }

        for (int i = 0; i < this.lastIndexOfSloiders; i++) {
            if (!(this.soldiers[i].getIsAlive()))
                continue;
            int posX = this.soldiers[i].getX();
            int nextPosY = this.soldiers[i].getNextYPos();
            String team = this.soldiers[i].getTeam();
            String name = this.soldiers[i].getName();

            if (!(this.Board[nextPosY][posX].contains("*")) && !(this.Board[nextPosY][posX].contains(team))) {
                if (this.Board[nextPosY][posX].contains("Drg")) {
                    soldierFightsDragon(name, nextPosY, posX, i);
                    break;
                } else if (Board[nextPosY][posX].contains("Sol")) {// Checks if the next position of the soldier
                    // will
                    soldierFightsSoldier(name, nextPosY, posX, i);
                    break;
                }
            } else {
                advanceSoldier(nextPosY, posX, i);
            }
        }
    }
}

	


