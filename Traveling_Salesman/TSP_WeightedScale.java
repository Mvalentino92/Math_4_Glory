/* A first attempt at the traveling saleman problem. 
 * A greedy approach is taken. I take a weighted scale to determine what the best course of action is
 * based on how small the distance is to that city. And also how many large values are eliminated in
 * the future by choosing that city. If I visit that city, I can never go to it again.
 * If a lot of distances to that city are large, then I want to elimate it. 
 * While I still have to go to another city from said city, (and that value will be exactly the same
 * as one of the previously elimanted values), this particular distance may not be one of the large ones.
 * It's about choosing small distances, but also eliminating large values.
 * Which sets up you up to only have small values to choose from in the future.*/

import java.util.*; 
import java.math.*; 
import java.io.*; 

public class TSP_WeightedScale
{
	//Computes the distance. Returns an int represention.
	public static int getDistance(double x1, double y1, double x2, double y2)
	{
		double x = x1 - x2;
		double y = y1 - y2;
		return (int)Math.sqrt(x*x + y*y);
	}

	//Return a matrix with the distances
	public static int[][] getMatrix(int size)
	{
		//Make arrays for x and y points
		Scanner reader = new Scanner(System.in);
		double[] xCoord = new double[size];
		double[] yCoord = new double[size];

		//Store the coordinates (eat the node part first)
		for(int i = 0; i < size; i++)
		{
			reader.nextDouble();
			xCoord[i] = reader.nextDouble();
			yCoord[i]= reader.nextDouble();
		}

		//Iterate the arrays and compute the distance between points and store in matrix.
		int[][] matrix = new int[size][size];
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				if(i == j) matrix[i][j] = 0;
				matrix[i][j] = getDistance(xCoord[i],yCoord[i],xCoord[j],yCoord[j]);
			}
		}
		return matrix;
	}

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
	public static int getRowMin(int[][] matrix, int row, int originCity)
	{
		//Find where to start
		int startColumn = 0;
		while(matrix[0][startColumn++] == -1);
		startColumn--;

		int min = matrix[row][startColumn];
		for(int col = startColumn + 1; col < matrix[row].length; col++)
		{
			int currentValue = matrix[row][col];
			if(currentValue != -1 && col != originCity)
			{
				if(currentValue < min) min = currentValue;
			}
		}
		return min;
	}
	
	//Finds the max of a row, used to determine percentage grades.
	public static int getRowMax(int[][] matrix, int row, int originCity)
	{
		int startColumn = 0;
		while(matrix[0][startColumn++] == -1);
		startColumn--;
		
		int max = matrix[row][startColumn];
		for(int col = startColumn + 1; col < matrix[row].length; col++)
		{
			int currentValue = matrix[row][col];
			if(currentValue != -1 && col != originCity)
			{
				if(currentValue > max) max = currentValue;
			}
		}
		return max;
	}

	//Getting the min value for the column sums
	public static int getColMin(int[][] matrix, int currentRow)
	{
		//Find where to start
		int startColumn = 0;
		while(matrix[0][startColumn++] == -1);
		startColumn--;

		//Get min value by setting it to first column
		int min = 0;
		for(int row = 0; row < matrix.length; row++) 
		{
			if(row == currentRow) continue;
			min += matrix[row][startColumn];
		}

		//Finding the actual min.
		for(int col = startColumn + 1; col < matrix[0].length; col++)
		{
			if(matrix[0][col] == -1) continue;
			int sumOfColumn = 0;
			for(int row = 0; row < matrix.length; row++) 
			{
				if(row == currentRow) continue;
				sumOfColumn += matrix[row][col];
			}
			if(sumOfColumn < min) min = sumOfColumn;
		}
		return min;
	}

	//Finds the max of a column.
	public static int getColMax(int[][] matrix, int currentRow)
	{
		//Find where to start
		int startColumn = 0;
		while(matrix[0][startColumn++] == -1);
		startColumn--;

		int max = 0;
		for(int row = 0; row < matrix.length; row++) 
		{
			if(row == currentRow) continue;
			max += matrix[row][startColumn];
		}
		for(int col = startColumn + 1; col < matrix[0].length; col++)
		{
			if(matrix[0][col] == -1) continue;
			int sumOfColumn = 0;
			for(int row = 0; row < matrix.length; row++) 
			{
				if(row == currentRow) continue;
				sumOfColumn += matrix[row][col];
			}
			if(sumOfColumn > max) max = sumOfColumn;
		}
		return max;
	}

	//Returns the small score for the current element.
	public static double getSmallScore(int[][] matrix, int rowMin, int rowMax, int row, int col,int smallScale)
	{
		double currentValue = (double)matrix[row][col];
		double retval = (rowMax - currentValue)/(rowMax - rowMin);
		return retval/smallScale;
	}

	//Grabs the largeScore for the current element.
	public static double getLargeScore(int[][] matrix, int colMin, int colMax,
		       			   int currentRow, int col,int largeScale)
	{
		double currentValue = 0.0;
		for(int row = 0; row < matrix.length; row++) 
		{
			if(row == currentRow) continue;
			currentValue += matrix[row][col];
		}
		double retval = (currentValue - colMin)/(colMax - colMin);
		return retval/largeScale;
	}

	//Main method.
	public static void main(String[] args)
	{
		int[][] citiesFINAL = getMatrix(493);

		int[] smallScale = {1,2,3,4,5};
		int[] largeScale = {1,2,3,4,5};
		int minimumDistance = getColMax(citiesFINAL,0)*citiesFINAL.length/2;
		int trialCount = 1;
		for(int s = 0; s < smallScale.length; s++)
		{
			for(int l = 0; l < largeScale.length; l++)
			{
				if(s == l && s != 1) continue;
				else
				{

		//Get the starting position and initialize sum of travel
		int[][] cities = deepCopy(citiesFINAL);
		int currentCity = (int)(Math.random()*cities.length);
		int originCity = currentCity;
		int tempOriginSpot = cities[0][originCity];
		cities[0][originCity] = -1;
		int distanceTraveled = 0;


		//Begin loop going through matrix until it is all -1's.
		int cityCount = 1;
		while(cityCount != cities.length)
		{
			//Get all mins and max.
			int rowMin = getRowMin(cities,currentCity,originCity);
			int rowMax = getRowMax(cities,currentCity,originCity);
			int colMin = getColMin(cities,currentCity);
			int colMax = getColMax(cities,currentCity);


			//Iterate every element in the row, and get a score to add to and ArrayList
			ArrayList<double[]> scores = new ArrayList<>();
			for(int col = 0; col < cities.length; col++)
			{
				if(cities[currentCity][col] != -1 && col != originCity)
				{
					double smallScore = getSmallScore(cities,rowMin,rowMax,
									  currentCity,col,smallScale[s]);
					double largeScore = getLargeScore(cities,colMin,colMax,
									  currentCity,col,largeScale[l]);
					double[] valueANDindex = {smallScore+largeScore,(double)col};
					scores.add(valueANDindex);
				}
			}
			double maxValue = scores.get(0)[0];
			int indexOfBest = (int)Math.round(scores.get(0)[1]);
			for(int i = 1; i < scores.size(); i++)
			{
				if(scores.get(i)[0] > maxValue)
				{
					maxValue = scores.get(i)[0];
					indexOfBest = (int)Math.round(scores.get(i)[1]);
				}
			}
			
			distanceTraveled += cities[currentCity][indexOfBest];
			currentCity = indexOfBest;
			killColumn(cities,currentCity);
			cityCount++;
			if(cityCount == cities.length) 
			{
				cities[0][originCity] = tempOriginSpot;
				distanceTraveled += cities[currentCity][originCity];
			}
		}
			if(distanceTraveled < minimumDistance) minimumDistance = distanceTraveled;
				} //End of else block for changing scales
			} //End of inner for, for changing scales
		} //End of out for, for changing scales
		System.out.println(minimumDistance);
	}
}
