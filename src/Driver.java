import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/****
 * 
 * @author Dalton Kinney
 *
 */
public class Driver {
	/***
	 * @name - Variable to get the name of whatever puzzle it is solving or
	 * reading from the file
	 * @solved =  boolean to check whether or not a puzzle is solved or
	 * unsolved puzzle.
	 */
static String name;
static boolean solved = false;
	public static void main(String[] args) throws Exception{
	/*Get the file with the puzzles in it */
	File puzzles = new File("puzzles.txt");
	/*scanner that will be used to read the file with puzzles in it */
	Scanner scanner = new Scanner(puzzles);
	/*File that will hold the solutions to the puzzles */
	File answers = new File("answers.txt");
	/*Print writer that will print the solutions to the answer.txt file */
	PrintWriter pw = new PrintWriter(answers);
	/***
	 * @titles - holds the title string so it can print to the answer file 
	 * @array - holds the puzzles that need to be solved
	 */
	String titles = "";
	/* While there is still another line, initialize the puzzles solved state to false
	 * make a new array
	 * Get the title of the puzzle, then get the puzzle its self in the for loop
	 */
	while(scanner.hasNext()) {
		/* puzzle isn't solved */
		solved = false;
		/*Empty array that will hold the unsolved puzzle */
		int[][]array = new int[9][9];
		/*First line will always hold the title of the puzzles */
		titles = scanner.nextLine();
		/*Get the puzzle and put it in the array */
		for(int i = 0; i < 9; i++) {
			for(int j =0; j < 9; j++) {
				array[i][j] = scanner.nextInt();
			}
		}	
		/*print the unsolved puzzle to the document */
		pw.print(printPuzzle(array, titles));	
		/*followed by the solved puzzle */
		pw.print(printPuzzle(solve(array), titles));	
		/*Skip a line to leave room for the next puzzle and keep it organized */
		pw.println();	
		scanner.nextLine();
	}
	pw.close();
	scanner.close();	
	/*Once all puzzles have been solved, say its done */
System.out.println("Complete");		
	}
	/***
	 * prints an array in puzzle format given an unsolved or solved array 
	 * @param array - takes in a 9 x 9 array of integers which will be the puzzle 
	 * @param title - title of the puzzle being passed in
	 * @return - string for puzzle to be printed to the answer.txt
	 */
	public static String printPuzzle(int array[][], String title) {
		String string = "";
		/*If the puzzle isn't solved, it will print unsolved + title of the puzzle 
		 * else if it is solved, solved + title of the puzzle
		 */
		if(solved == false) {
			string += "Unsolved " + title + "\n\n";
			} else {
				string += "Solved " + title + "\n\n";
			solved = false;
			}
		/*Printing the array */
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if((j + 1 )== 9) {
					string += array[i][j] + " ";
				} else if((j + 1)% 3 == 0) {
					string += array[i][j] + " | ";

				}else {
					string += array[i][j] + " ";
				}
			}
			string+="\n";
			if((i + 1) != 9 && (i + 1) % 3 == 0) {
				string += "--------------------- \n";
			}
		}
		/*Return the string of the puzzle followed by a space */
		return string + "\n";
	}
	/***
	 * Main puzzle solving algorithym
	 * @param array - take in an array and solve it
	 * @return - solved puzzle
	 */
	public static int[][] solve(int[][] array) {
		/*Create a stack object that will hold moves made */
		MyStack<Move> moves = new MyStack<Move>();
		/*Main loops that will iterate through every cell of the puzzle */
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				/*If the puzzle isn't empty continue to the next cell*/
				if(array[i][j] != 0) {
					continue;
				}
				/*Test integer 1 - 9 to see if they can be valid moves in a given cell at row i and column j */
					for(int k = 1; k < 10; k++) {
						if(isValid(array, i, j, k)) {
							/*If it is a valid move, push the move onto the stack and set the cell equal to the valid move value */
							moves.push(new Move(i, j, k));
							array[i][j] = k;
							/*Break out of the function since we have found a move*/
							break;
						}
					}
					/*If the array still equals zero because no move could be made, start backtracing */
					if(array[i][j] == 0) {
						/*Booelan to see if it still needs to backtrace */
					boolean backtrack = true;
						while(backtrack) {
						/*Temporary Move object to represent nodes being backtraced to */
						Move temp = moves.pop();
						/*Set the backtraced cell to 0 again */
						array[temp.row][temp.col] = 0;
						i = temp.row;
						j = temp.col;
						/*If the value reaches 9 and still no move can be made, keep backtracing */
						if(temp.move == 9) {
							continue;
						}
						/*start at the 1 + the last move to 9, and try to find another valid move that will work */
						for(int m = temp.move + 1; m < 10; m++) {
							if(isValid(array, i, j, m)) {
								moves.push(new Move(i, j, m));
								array[i][j] = m;
								backtrack = false;
								break;
							}
						}
						}
					}
				
				
				}
			}
		/*The puzzle is solved at this point, so set it to true */
		solved = true;
		/*return the solved array */
		return array;
	}
	/* Check to see if a given move at a position in an array is valid */	
	public static boolean isValid(int[][] array, int row, int column, int move) {
		/*Check the row */
		for(int k = 0; k < 9; k++) {
			if(array[k][column] != 0 && array[k][column] == move) {
				return false;
			}
			/*Check the column*/
			if(array[row][k] != 0 && array[row][k] == move) {
				return false;
			}
		}
		/*Check the 3 x 3 its in */
		if(checkBox(array, row, column, move) == false) {
			return false;
		}
		return true;
	}
	public static Boolean checkBox(int[][] array, int row, int col, int move) {
		int r = row - row % 3;
		int c = col - col % 3;
		
		for(int i = r; i < r + 3;i++) {
			for(int j = c; j < c + 3; j++) {
				if(array[i][j] == move) {
					return false;
				}
			}
		}
		return true;
	}
}
