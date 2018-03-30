/*This attempts to solve the traveling sales problem using a modified greedy approach.
 * The modified part is that every iteration, each city is given a cluster score.
 * Which represents how close it is to surrounding citites. The pure greedy strategy of this problem is to 
 * always pick the shortest distance each time. Although I'm also taking the cluster score of the distances into
 * account as well. First priority is to go to clustered areas, and the second priority is pick up
 * cities that seem to be becoming isolated.*/

import java.util.*; 
import java.math.*; 
import java.io.*; 

//Holds the x and y coordinates of the city
class Point
{
	double x;
	double y;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public int getDistance(Point P0)
	{
		double xDiff = this.x - P0.x;
		double yDiff = this.y - P0.y;
		return (int)Math.sqrt(xDiff*xDiff + yDiff*yDiff);
	}
}

//Holds information about each city when determining which to visit next.
class City implements Comparable<City>
{
	int col;
	int distance;
	double clusterScore;

	public City(int col, int distance, double clusterScore)
	{
		this.col = col;
		this.distance = distance;
		this.clusterScore = clusterScore;
	}

	@Override
	public int compareTo(City compareCity)
	{
		if(this.distance < compareCity.distance) return -1;
		if(this.distance > compareCity.distance) return 1;
		return 0;
	}

	@Override
	public String toString()
	{
		String retval = "Distance: "+distance+" and clusterScore is: "+clusterScore;
		return retval;
	}
}
	
public class TSP_Cluster
{
	//Return a matrix with the distances
	public static int[][] getMatrix(int size)
	{
		//Make the array to store Points
		Scanner reader = new Scanner(System.in);
		Point[] coordinates = new Point[size];

		//Store the coordinates (eat the node part first)
		for(int i = 0; i < size; i++)
		{
			reader.nextDouble();
			coordinates[i] = new Point(reader.nextDouble(),reader.nextDouble());
		}

		//Iterate the array and compute the distance between points and store in matrix.
		int[][] matrix = new int[size][size];
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				if(i == j) matrix[i][j] = 0;
				matrix[i][j] = coordinates[i].getDistance(coordinates[j]);
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
	public static int getColMax(int[][] matrix,int currentRow)
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

	//Returns the cluster score for the current element.
	public static double getClusterScore(int[][] matrix, int colMin, int colMax, int currentRow, int col)
	{
		double currentValue = 0.0;
		for(int row = 0; row < matrix.length; row++)
		{
			if(row == currentRow) continue;
			currentValue += matrix[row][col];
		}
		//System.out.println("Min: "+colMin+" Max: "+colMax+" Current: "+currentValue);
		double retval = (colMax - currentValue)/(colMax - colMin);
		return retval;
	}

	//Main method.
	public static void main(String[] args)
	{
		//Generates the matrix of distances.
		int[][] cities = getMatrix(150);

		//Get the starting position and initialize sum of travel
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
			int colMin = getColMin(cities,currentCity);
			int colMax = getColMax(cities,currentCity);

			//Iterate every element in the row, and get a score to add to and ArrayList
			ArrayList<City> scores = new ArrayList<>();
			for(int col = 0; col < cities[0].length; col++)
			{
				if(cities[currentCity][col] != -1 && col != originCity)
				{
					double clusterScore = getClusterScore(cities,colMin,colMax,
									  currentCity,col);
					City nextCity = new City(col,cities[currentCity][col],clusterScore);
					scores.add(nextCity);
				}
			}
			
			//Sorts the ArrayList based on distance.
			Collections.sort(scores);

			//Picks the current best distance, taking into account cluster score as well.
			double upper = 0.85;
			double lower = 0.35;
			int indexOfBest = 0;
			boolean verdict = true;
			if(scores.size() > 1)
			{
				while(verdict)
				{
					for(int i = 0; i < scores.size(); i++)
					{
						if(scores.get(i).clusterScore > upper)
						{
							indexOfBest = scores.get(i).col;
							verdict = false;
							break;
						}
						else if(scores.get(i).clusterScore < lower)
						{
							indexOfBest = scores.get(i).col;
							verdict = false;
							break;
						}
					}
				}
			}
			else indexOfBest = scores.get(0).col;

			//Update everything.
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
		System.out.println(distanceTraveled);
	}
}
