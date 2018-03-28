/* Solves the traveling salesmen problem using a heuristic approach */ 

import java.util.*; 
import java.math.*; 
import java.io.*; 

public class TSP
{
	//Deep copy of a matrix
	public static int[][] deepCopy(int[][] matrix)
	{
		int[][] newMat = new int[matrix.length][matrix[0].length];
		for(int i = 0; i < newMat.length; i++)
		{
			for(int j = 0; j < newMat[0].length; j++)
			{
				newMat[i][j] = matrix[i][j];
			}
		}
		return newMat;
	}

	//Fills a column with -1 after it chosen to be visited.
	public static void killColumn(int[][] matrix, int col)
	{
		for(int row = 0; row < matrix.length; row++) matrix[row][col] = -1;
	}

	//Finds the min of a row, used to determine percentage grades.
	public static int getRowMin(int[][] matrix, int row)
	{
		int min = matrix[row][0];
		for(int col = 1; col < matrix[row].length; col++)
		{
			if(matrix[row][col] < min && matrix[row][col] > 0) min = matrix[row][col];
		}
		return min;
	}
	
	//Finds the max of a row, used to determine percentage grades.
	public static int getRowMax(int[][] matrix, int row)
	{
		int max = matrix[row][0];
		for(int col = 1; col < matrix[row].length; col++)
		{
			if(matrix[row][col] > max && matrix[row][col] > 0) max = matrix[row][col];
		}
		return max;
	}

	//Getting the min value for the column sums
	public static int getColMin(int[][] matrix)
	{
		//Find where to start
		int startColumn = 0;
		while(matrix[0][startColumn++] == -1);
		startColumn--;

		//Get min value by setting it to first column
		int min = 0;
		for(int row = 0; row < matrix.length; row++) min += matrix[row][startColumn];

		//Finding the actual min.
		for(int col = startColumn + 1; col < matrix[0].length; col++)
		{
			int sumOfColumn = 0;
			for(int row = 0; row < matrix.length; row++) sumOfColumn += matrix[row][col];
			if(sumOfColumn < min) min = sumOfColumn;
		}
		return min;
	}

	//Finds the max of a column.
	public static int getColMax(int[][] matrix)
	{
		//Find where to start
		int startColumn = 0;
		while(matrix[0][startColumn++] == -1);
		startColumn--;

		int max = 0;
		for(int row = 0; row < matrix.length; row++) max += matrix[row][startColumn];

		for(int col = startColumn + 1; col < matrix[0].length; col++)
		{
			int sumOfColumn = 0;
			for(int row = 0; row < matrix.length; row++) sumOfColumn += matrix[row][col];
			if(sumOfColumn > max) max = sumOfColumn;
		}
		return max;
	}

	//Overloaded method to return the first column to start in based on max column
	public static int getColMin(int[][] matrix,boolean getFirstMove)
	{
		//Find where to start
		int startColumn = 0;
		while(matrix[0][startColumn++] == -1);
		startColumn--;

		//Get min value by setting it to first column
		int min = 0;
		int minIndex = startColumn;
		for(int row = 0; row < matrix.length; row++) min += matrix[row][startColumn];

		//Finding the actual min.
		for(int col = startColumn + 1; col < matrix[0].length; col++)
		{
			int sumOfColumn = 0;
			for(int row = 0; row < matrix.length; row++) sumOfColumn += matrix[row][col];
			if(sumOfColumn < min) 
			{
				min = sumOfColumn;
				minIndex = col;
			}
		}
		return minIndex;
	}

	//Returns the small score for the current element.
	public static double getSmallScore(int[][] matrix, int rowMin, int rowMax, int row, int col,int smallScale)
	{
		double currentValue = (double)matrix[row][col];
		double retval = (rowMax - currentValue)/(rowMax - rowMin);
		return retval/smallScale;
	}

	//Grabs the largeScore for the current element.
	public static double getLargeScore(int[][] matrix, int colMin, int colMax, int col,int largeScale)
	{
		double currentValue = 0.0;
		for(int row = 0; row < matrix.length; row++) currentValue += matrix[row][col];
		double retval = (currentValue - colMin)/(colMax - colMin);
		return retval/largeScale;
	}

	//Main method.
	public static void main(String[] args)
	{
		int[][] citiesFINAL = {{0,29,20,21,16,31,100,12,4,31,18},
				  {29,0,15,29,28,40,72,21,29,41,12},
				  {20,15,0,15,14,25,81,9,23,27,13},
				  {21,29,15,0,4,12,92,12,25,13,25},
				  {16,28,14,4,0,16,94,9,20,16,22},
				  {31,40,25,12,16,0,95,24,36,3,37},
				  {100,72,81,92,94,95,0,90,101,99,84},
				  {12,21,9,12,9,24,90,0,15,25,13},
				  {4,29,23,25,20,36,101,15,0,35,18},
				  {31,41,27,13,16,3,99,25,35,0,38},
				  {18,12,13,25,22,37,84,13,18,38,0}};

		int[] smallScale = {1,2,3,4,5};
		int[] largeScale = {1,2,3,4,5};
		int minimumDistance = 1000000;
		for(int s = 0; s < smallScale.length; s++)
		{
			for(int l = 0; l < largeScale.length; l++)
			{
				if(s == l && s != 1) continue;
				else
				{

		//Get the starting position and initialize sum of travel
		int[][] cities = deepCopy(citiesFINAL);
		int currentCity = getColMin(cities,true);
		int originCity = currentCity;
		int distanceTraveled = 0;

		System.out.println("Starting city is: "+originCity);

		//Begin loop going through matrix until it is all -1's.
		int cityCount = 1;
		while(cityCount != cities.length)
		{
			//Get all mins and max.
			int rowMin = getRowMin(cities,currentCity);
			int rowMax = getRowMax(cities,currentCity);
			int colMin = getColMin(cities);
			int colMax = getColMax(cities);


			//Iterate every element in the row, and get a score to add to and ArrayList
			ArrayList<double[]> scores = new ArrayList<>();
			for(int col = 0; col < cities.length; col++)
			{
				if(cities[currentCity][col] != -1 && col != originCity)
				{
					double smallScore = getSmallScore(cities,rowMin,rowMax,
									  currentCity,col,smallScale[s]);
					double largeScore = getLargeScore(cities,colMin,colMax,
									  col,largeScale[l]);
					double[] valueANDindex = {smallScore+largeScore,col};
					scores.add(valueANDindex);
				}
			}
			double maxValue = scores.get(0)[0];
			int indexOfBest = (int)scores.get(0)[1];
			for(int i = 1; i < scores.size(); i++)
			{
				if(scores.get(i)[0] > maxValue)
				{
					maxValue = scores.get(i)[0];
					indexOfBest = (int)scores.get(i)[1];
				}
			}
			
			distanceTraveled += cities[currentCity][indexOfBest];
			System.out.println("From: "+currentCity+" to city: "+indexOfBest+" and added "+
					cities[currentCity][indexOfBest]+" for a total of: "+distanceTraveled);
			currentCity = indexOfBest;
			killColumn(cities,currentCity);
			cityCount++;
			if(cityCount == cities.length) 
			{
				distanceTraveled += cities[currentCity][originCity];
				System.out.println("From: "+currentCity+" to city: "+originCity+" and added "+
						cities[currentCity][originCity]+" for a total of "+
						distanceTraveled);
			}
		}
			if(distanceTraveled < minimumDistance) minimumDistance = distanceTraveled;
			System.out.println();
				} //End of else block for changing scales
			} //End of inner for, for changing scales
		} //End of out for, for changing scales
		System.out.println("The shortest distance with a heuristics approach is "+minimumDistance);
	}
}
