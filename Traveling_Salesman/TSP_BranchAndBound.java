/*My attempt to implement a Branch and Bound algorithm for the traveling salesman problem.
 * Uses a pure greedy algorithm to find an initial global minimum.
 * Explores all possible pathways while trying to eliminate ones that clearly do not have potential
 * to beat the current minimum. Only intended for small N. 
 * The methods I use to bound promising pathways is experimental. 
 * This particular set-up has been tested on N = 17,26 and 42 to completion.
 * Both did not ever completely return (in a reseaonable time), but arrived at the known solutions
 * in under 10 minutes.*/

import java.util.*;
public class TSP_BranchAndBound
{
	//Static variable that tracks the current minimum distance.
	public static int minimumDistance = 0;

	//Generates the matrix from the supplied .txt file.
	public static int[][] getMatrix(int size)
	{
		Scanner input = new Scanner(System.in);
		int[][] matrix = new int[size][size];
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				matrix[i][j] = input.nextInt();
			}
		}
		return matrix;
	}

	//Creates a deep copy of the current matrix. For use on the greedy algorithm. New matrix for each starting point.
	public static int[][] deepCopy(int[][] matrix)
	{
		int size = matrix.length;
		int[][] copyMatrix = new int[size][size];
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				copyMatrix[i][j] = matrix[i][j];
			}
		}
		return copyMatrix;
	}

	//Kills the current column with -1's.
	public static void killColumn(int[][] matrix, int targetColumn)
	{
		for(int i = 0; i < matrix.length; i++) matrix[i][targetColumn] = -1;
	}
	
	//Generates the best pure greedy algorithm solution.
	public static void greedy(int[][] matrix)
	{
		//Iterate every element in the array as the origin city.
		int size = matrix.length;
		for(int origin = 0; origin < size; origin++)
		{
			//Copy the matrix, and kill the origin column of the copy. Values can be retrieved from original matrix at the end.
			int[][] copiedMatrix = deepCopy(matrix);
			killColumn(copiedMatrix,origin);

			//Initialize starter variables, and begin iterating the greedy algorith.
			int count = 1;
			int distanceTraveled = 0;
			int currentRow = origin;
			while(count != size)
			{
				//Finding the column to start at, and setting its value as min.
				int min = 0;
				int minIndex = 0;
				int startColumn = 0;
				while(copiedMatrix[currentRow][startColumn++] == -1);
				min = copiedMatrix[currentRow][startColumn-1];
				minIndex = startColumn-1;

				for(int col = startColumn; col < size; col++)
				{
					int currentValue = copiedMatrix[currentRow][col];
					if(currentValue != -1)
					{
						if(currentValue < min) 
						{
							min = currentValue;
							minIndex = col;
						}
					}
				}

				//Update all the variables, and kill the chosen column.
				distanceTraveled += min;
				currentRow = minIndex;
				killColumn(copiedMatrix,currentRow);
				count++;

				//Check to see if at the end, if true, add the return value to origin.
				if(count == size) distanceTraveled += matrix[origin][currentRow];
			}

			//Check to see if the current distance is lower then the global minimum.
			if(origin == 0) minimumDistance = distanceTraveled;
			if(distanceTraveled < minimumDistance) minimumDistance = distanceTraveled;
		}
	}
	
	//Checks to see if current number can be uniquely placed.
	public static boolean isClear(int[] list, int target)
	{
		for(int i = 0; i < list.length; i++)
		{
			if(list[i] == target) return false;
		}
		return true;
	}

	//Replaces all elements in array with -1 (Used to "prime" the array for the method).
	public static void negOneOut(int[] list)
	{
		for(int i = 0; i < list.length; i++) list[i] = -1;
	}

	//Creates a deep copy of the array so the current unique combinations can be added to the list.
	public static int[] deepCopyList(int[] list)
	{
		int[] newList = new int[list.length];
		for(int i = 0; i < list.length; i++) newList[i] = list[i];
		return newList;
	}

	//Generates all possible pathways for the amount of points supplied (The length of list). 
	public static void branchANDbound(int[][] matrix, int[] list, double score, int distanceSum, int index, int badScoreCount)
	{
		//Return it's already impossible to beat the current minimum.
		if(distanceSum > minimumDistance) return;

		//Return if your done, up date minimum if applicable.
		if(index == list.length)
		{
			int currentDistance = matrix[list[list.length-1]][list[0]] + distanceSum;
			if(currentDistance < minimumDistance)
			{
				minimumDistance = currentDistance;
				System.out.println(minimumDistance);
			}
			return;
		}

		//Return if above the bounds.
		//Based on number of bad decisions, and overall score effected by decisions.
		if(score > (matrix.length - index)/10.0 || badScoreCount > Math.round(matrix.length/10.0)) return;

		for(int i = 0; i < list.length; i++)
		{
			//If you can uniquely choose this point, go for it. Also, skip forward once if this is the first call i
			//(Need two points to generate distance).
			if(!isClear(list,i)) continue;
			list[index] = i;
			if(index == 0) branchANDbound(matrix, list, score, distanceSum, index + 1, badScoreCount);
			else
			{
				//Generate bound variables, and update variables for next function call.
				double average = (double)minimumDistance/matrix.length;
				int row = list[index-1];
				int col = list[index];
				int distanceToAdd = matrix[row][col];
				double scoreToAdd = (average - distanceToAdd)/(average*-1.0);
				int badScore = scoreToAdd > 0.65 ? 1 : 0;

				//If a really bad move is move, redo.
				if(scoreToAdd > 1.65)
				{
					list[index] = -1;
					continue;
				}
				//Otherise, go for it.
				branchANDbound(matrix, list, score + scoreToAdd, distanceSum + distanceToAdd, index + 1, badScoreCount + badScore);
			}
			list[index] = -1;
		}
		return;
	}

	public static void main(String[] args)
	{
		int[][] matrix = getMatrix(48);
		greedy(matrix);
		System.out.println(minimumDistance);
		int[] paths = new int[matrix.length];
		for(int i = 0; i < paths.length; i++) paths[i] = i;
		negOneOut(paths);
		branchANDbound(matrix,paths,0,0,0,0);
		System.out.println(minimumDistance);
	}
}
