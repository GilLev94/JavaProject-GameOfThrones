package gamesofthrones;
	import java.util.Scanner;
	import javax.print.DocFlavor.BYTE_ARRAY;
	import java.io.IOException;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.util.List;

	public class game {

		public static void main(String[] args) {
			GameOfThrone game;
			// int[] a = { 1, 2, 3, 4, 5 };
			// int sum = getHash(a, a.length - 1);
			System.out.println("\n********** Game of Thrones board X*X **********");
			System.out.println("1. Enter Parameters By User.");
			System.out.println("2. Load Parameters From File.");
			System.out.print("\nEnter Your Option: ");
			Scanner s = new Scanner(System.in);

			if (s.nextInt() == 1) {
				System.out.println("\n********** Game of Thrones board X*X **********");
				System.out.print("Enter X size: ");
				int boardSize = s.nextInt();

				System.out.printf("\n********** Created Game of Thrones board %d X %d **********\n", boardSize, boardSize);
				game = new GameOfThrone(boardSize);

				System.out.println("\n********** Red team character creation **********");
				System.out.print("Enter number of Red Soldiers: ");
				game.AddSoldiers("RED", true);

				System.out.print("Enter number of Red Dragons: ");
				game.AddDragons("RED", true);

				System.out.println("********** Blue team character creation **********");

				System.out.print("Enter number of Blue Soldiers: ");
				game.AddSoldiers("BLUE", false);

				System.out.print("Enter number of Blue Dragons: ");
				game.AddDragons("BLUE", false);
			} else {
				System.out.println("\n********** Game of Thrones board X*X **********");
				System.out.print("Enter Path To File: ");
				String filename = s.next();
				Path gamePath = Paths.get(".", filename);
				// we store the file data in list of Strings each element in the list contains a
				// string represintation of the line
				List<String> lines = null;

				// try to read data from a file that the user typed in
				try {
					lines = Files.readAllLines(gamePath);

				} catch (IOException e) {
					System.out.println(e);
				}
				System.out.println("HASHCODE LOADING...");
				System.out.printf("\nLoaded HashCode = %d", Integer.valueOf(lines.get(7)));

				// Claulating the hash code for the lines that we got from the file
				int hashValue = CalcHashEachLine(lines);

				System.out.printf("\nGenerated File HashCode = %s", hashValue);

				if (hashValue != Integer.valueOf(lines.get(7))) {
					System.out.println("\nDamaged Input File!");
					System.out.println("\nExit!!!");
					System.exit(1);
				}
				System.out.printf("\nFile Loaded Successfully!!");

				int boardSize = Integer.parseInt(lines.get(0));

				System.out.printf("\nBOARD SIZE LOADING...\n     Size = %d x %d", boardSize, boardSize);
				// add red soldiers
				game = new GameOfThrone(boardSize);

				// lines.get(1 Or 4) == the color of the team
				// lines.get(2 Or 5) == position data for the soldiers
				// lines.get(3 Or 6) == position data for the dragons
				game.AddSoldiers(lines.get(1), true, lines.get(2));
				game.AddDragons(lines.get(1), true, lines.get(3));
				game.AddSoldiers(lines.get(4), false, lines.get(5));
				game.AddDragons(lines.get(4), false, lines.get(6));
			}
			System.out.println("\n\n*******************************************************************");
			System.out.println("**********              Let's Start Play                 **********");
			System.out.println("*******************************************************************");
			game.printBoard();

			while (!game.getIsGameOver()) {
				game.advanceStep();
				game.printBoard();
				game.getGameOver();
			}
			s.close();
		}

		// calculating the hash for each line, skipping the last line that containes the
		// hash itself
		public static int CalcHashEachLine(List<String> lines) {
			int hashValue = 0;
			for (int i = 0; i < lines.size() - 1; i++) {
				// we subtract one from the string length because with strings we've got 0 index
				hashValue += getHash(lines.get(i), lines.get(i).length() - 1);
			}
			return hashValue;
		}

		// recursion function that calculating the hash code for string. every one of
		// this strings we got from the file
		public static int getHash(String a, int n) {
			if (n == 0)
				return a.charAt(n);
			else
				return a.charAt(n) + getHash(a, n - 1);
		}
	}



