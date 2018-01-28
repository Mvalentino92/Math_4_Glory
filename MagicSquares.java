import java.util.*;
public class MagicSquares
{
	public static int count = 0;
	public static int N = 4;
	public static int magicSum = N*(N*N + 1)/2;

	public static int[] upperLeft = {0,1,4,5};
	public static int[] bottomLeft = {8,9,12,13};
	public static int[] upperRight = {2,3,6,7};
	public static int[] bottomRight = {10,11,14,15};
	public static ArrayList<int[]> corners = new ArrayList<>();

	public static int[] diagonalOne = {0,5,10,15};
	public static int[] diagonalTwo = {3,6,9,12};

	public static int[] centerSquare = {5,6,9,10};

	public static void addCorners()
	{
		corners.add(upperRight);
		corners.add(upperLeft);
		corners.add(bottomRight);
		corners.add(bottomLeft);
	}

	/*The following methods are meant to check the correspondings rows, columns, squares, and diagonals of the new numbers index.
	 * If whichever block of elements the method is checking (whether row, column etc), contains any empty spaces (zeros), it will by default return true.
	 * If it DOES NOT contain any empty spaces, that means it is full and it must be checked to equal the magic sum*/

	public static boolean checkRow(int[] board, int index)
	{
		index += 1;
		while(true)
		{
			if(index % N == 0) break;
			else index++;
		}
		index--;

		int sum = 0;
		for(int i = 0; i < N; i++)
		{
			if(board[index - i] == 0) return true;
			else sum += board[index - i];
		}

		if(sum == magicSum) return true;
		else return false;
	}

	public static boolean checkCol(int[] board, int index)
	{
		index += 1;
		while(true)
		{
			if(index - N < 1) break;
			else index -= N;
		}
		index--;

		int sum = 0;
		for(int i = 0; i < N*N; i += N)
		{
			if(board[index + i] == 0) return true;
			else sum += board[index + i];
		}
		
		if(sum == magicSum) return true;
		else return false;
	}

	public static boolean checkCorner(int[] board, int index)
	{
		for(int k = 0; k < corners.size(); k++)
		{
			for(int i = 0; i < corners.get(k).length; i++)
			{
				if(index == corners.get(k)[i])
				{
					//Check to see if the sum will be the magic sum
					int sum = 0;
					for(int j = 0; j < corners.get(k).length; j++)
					{
						int numberAtSpace = board[corners.get(k)[j]];
						if(numberAtSpace == 0) return true;
						else sum += numberAtSpace;
					}
					
					if(sum == magicSum) return true;
					else return false;
				}
			}
		}
		return false;
	}

	public static boolean checkCenter(int[] board, int index)
	{
		for(int k = 0; k < centerSquare.length; k++)
		{
			if(index == centerSquare[k])
			{
				int sum = 0;
				for(int i = 0; i < centerSquare.length; i++)
				{
					int numberAtSpace = board[centerSquare[i]];
					if(numberAtSpace == 0) return true;
					else sum += numberAtSpace;
				}
				if(sum == magicSum) return true;
				else return false;
			}
		}
		return true;
	}

	public static boolean checkFirstDiagonal(int[] board, int index)
	{
		for(int k = 0; k < diagonalOne.length; k++)
		{
			if(index == diagonalOne[k])
			{
				int sum = 0;
				for(int i = 0; i < diagonalOne.length; i++)
				{
					int numberAtSpace = board[diagonalOne[i]];
					if(numberAtSpace == 0) return true;
					else sum += numberAtSpace;
				}
				if(sum == magicSum) return true;
				else return false;
			}
		}
		return true;
	}

	public static boolean checkSecondDiagonal(int[] board, int index)
	{
		for(int k = 0; k < diagonalTwo.length; k++)
		{
			if(index == diagonalTwo[k])
			{
				int sum = 0;
				for(int i = 0; i < diagonalTwo.length; i++)
				{
					int numberAtSpace = board[diagonalTwo[i]];
					if(numberAtSpace == 0) return true;
					else sum += numberAtSpace;
				}
				if(sum == magicSum) return true;
				else return false;
			}
		}
		return true;
	}

	//The method that wraps all the "check" methods together and returns true if they are all satisfied.
	public static boolean validMove(int[] board, int index)
	{
		if(checkRow(board,index) 
			&& checkCol(board,index)
			&& checkCorner(board,index) 
			&& checkCenter(board,index)
			&& checkFirstDiagonal(board,index)
			&& checkSecondDiagonal(board,index)) return true;
		else return false;
	}

	//Checks that the current number is not already placed on board.
	public static boolean validNumber(int[] board, int number)
	{
		for(int i = 0; i < board.length; i++)
		{
			if(board[i] == 0) continue;
			if(board[i] == number) return false;
		}
		return true;
	}

	//Just double checks everything is right before printing.
	public static boolean doubleCheck(int[] board)
	{
		if(checkRow(board,0) && checkRow(board,4)
				&& checkRow(board,8)
				&& checkRow(board,12)
				&& checkCol(board,0)
				&& checkCol(board,1)
				&& checkCol(board,2)
				&& checkCol(board,3)
				&& checkCorner(board,0)
				&& checkCorner(board,3)
				&& checkCorner(board,12)
				&& checkCorner(board,15)
				&& checkFirstDiagonal(board,0)
				&& checkSecondDiagonal(board,3)
				&& checkCenter(board,5)) return true;
		else return false;
	}

	/*The recursive backtracking method. This will iterate all possible combinations of magic squares.
	 * It will print all of them, even the ones that exploit simply swapping some pairs of elements.*/
	public static boolean getNextMove(int[] board, int index)
	{
		//if(board[N*N - 1] != 0) return true;
		for(int number = 1; number < N*N + 1; number++)
		{
			if(validNumber(board,number))
			{
				board[index] = number;
				if(validMove(board,index))
				{
					if(board[N*N - 1] != 0)
					{
						if(doubleCheck(board))
						{
							count++;
							printMagicSquare(board);
						}
						board[index] = 0;
						return true;
					}
					getNextMove(board,index + 1);
					board[index] = 0;
				}
				else board[index] = 0;
			}
		}
		return false;
	}

	//Prints the current magic square.
	public static void printMagicSquare(int[] board)
	{
		for(int i = 0; i < N*N; i++)
		{
			if(i % 4 == 0) System.out.println();
			System.out.printf("%3d ",board[i]);
		}
		System.out.println();
	}
	
	public static void main(String[] args)
	{
		addCorners();
		int[] magicBoard = new int[N*N];
		getNextMove(magicBoard,0);
		System.out.println("There are "+count+" ways to write the 4X4 magic square.");
	}
}
