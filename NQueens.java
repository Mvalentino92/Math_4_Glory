import java.util.*;
public class NQueens
{
	/*Static initialization of:
	 * The Scanner and both the size and willPrint boolean returned from input methods
	 * The array that will hold the marked columns
	 * The board, and the count variable to track how many solved boards were found*/
	public static Scanner input = new Scanner(System.in);
	public static int size = getSize();
	public static boolean willPrint = toPrint();
	public static int[] markCol = new int[size];
	public static int[][] board = new int[size][size];
	public static int count = 0;
	
				/*Safe-guarded user input methods*/
	//Get size from user
	public static int getSize()
	{
		while(true)
		{
			System.out.print("Enter the dimensions of the board, the number must "+
					 "be > 4, and <= 16: ");
			if(input.hasNextInt()) 
			{
				int n = input.nextInt();
				if(n > 4 && n <= 16) return n;
			}
			input.nextLine();
		}
	}

	//Ask user to print or not print solutions
	public static boolean toPrint()
	{
		while(true)
		{
			System.out.print("Would you like to print all the board solutions? [y/n]: ");
			char verdict = input.next().charAt(0);
			if(verdict == 'y') return true;
			else if(verdict == 'n') return false;
			else input.nextLine();
		}
	}

	//Checks the diagonals in all directions and ensures that they are free of other queens
	public static boolean checkDiagonals(int row, int col)
	{
		//Get both minimums
		int minMajor = row < col ? row : col;
		int minMinor = (size - 1) - row < col ? (size - 1) - row : col;

		//Iterate and check diagonals (row major and minor)
		for(int i = row - minMajor, j = col - minMajor; i < size && j < size; i++, j++)
		{
			if(board[i][j] == 1) return false;
		}
		for(int i = row + minMinor, j = col - minMinor; i > -1 && j < size; i--, j++)
		{
			if(board[i][j] == 1) return false;
		}
		return true;
	}

	//Method to check if this a valid move (checks column and diagonals for other queens)
	public static boolean validMove(int row, int col)
	{
		return markCol[col] == 0 && checkDiagonals(row,col);
	}

	//Prints the board if a solution was found
	public static void printBoard()
	{
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++) System.out.print(board[i][j]+" ");
			System.out.println();
		}
		System.out.println();
	}

	//The recursive function that generates all solutions using a backtracking algorithm
	public static void queens(int currentRow)
	{
		//Base case, if currentRow == size, print the board, increment count and return
		if(currentRow == size)
		{
			if(willPrint) printBoard();
			count++;
			return;
		}

		//Iterate all columns in currentRow and seek a valid move 
		for(int currentCol = 0; currentCol < size; currentCol++)
		{
			//If this move is valid, update column array and the board, then recur
			if(validMove(currentRow,currentCol))
			{
				//Update column array and board
				board[currentRow][currentCol] = 1;
				markCol[currentCol] = -1;

				//Recur
				queens(currentRow+1);

				//When we return here, unmark the column array and board
				board[currentRow][currentCol] = 0;
				markCol[currentCol] = 0;
			}
		}
		//If no valid moves were found return
		return;
	}

	public static void main(String[] args)
	{
		//Get time of computation
		double start = System.nanoTime();
		queens(0);
		double time = (System.nanoTime() - start)/1e9;
		
		//Print results
		System.out.println("\nThere are "+count+" total solutions to the "+size+" Queens Problem.");
		if(time > 60) System.out.println("And it took "+((int)(time/60))+" minutes and "+
				                (((time/60)%1)*60)+" seconds to find them!");
		else System.out.println("And it took "+time+" seconds to find them!");
	}
}
