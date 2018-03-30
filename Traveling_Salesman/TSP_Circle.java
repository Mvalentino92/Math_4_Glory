/*Attempt to solve the traveling salesmen problem with a greedy approach.
 * My logic was that the easiest traveling salesmen problem to solve is where the points
 * form a perfect circle. Perhaps if we started at what is considered the center of the distributed citites,
 * we could follow the general direction and path of a circle while implementing the greedy algorithm.
 * I've incorporated this strategy by genearting the set of points for a circle. This circle, has a radius that
 * is half of the circle that embodies all the cities. Each iteration, I chose the shorest distance
 * But only if that corresponding next city is within the radius of a circle with it's center being the point
 * on the other circle. Hard to explain over text, but basically..I rotate a circle around the path a circle
 * and all the points I choose, must be within that circle. This attempts to ensure a general ideal
 * circular path while jumping between citites with the greedy approach.*/

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

	public double getDistance(Point P0)
	{
		double xDiff = this.x - P0.x;
		double yDiff = this.y - P0.y;
		double retval = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
		return retval;
	}

	public int getDistance(Point P0,int integer)
	{
		double xDiff = this.x - P0.x;
		double yDiff = this.y - P0.y;
		double retval = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
		return (int)retval;
	}
}

//Holds the distance from the matrix, and original index of this value.
class City implements Comparable<City>
{
	int distance;
	int col;

	public City(int distance, int col)
	{
		this.distance = distance;
		this.col = col;
	}

	@Override
	public int compareTo(City compareCity)
	{
		if(this.distance < compareCity.distance) return -1;
		if(this.distance > compareCity.distance) return 1;
		return 0;
	}
}

public class TSP_Circle
{
	//Returns an array of the x and y coordinates
	public static Point[] getCoordinates(int size)
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
		return coordinates;
	}

	//Returns the center of the distributed points.
	public static Point getCenter(Point[] coordinates)
	{
		//Get the min and max of both x and y values.
		double minX = coordinates[0].x;
		double maxX = coordinates[0].x;
		double minY = coordinates[0].y;
		double maxY = coordinates[0].y;

		for(int i = 1; i < coordinates.length; i++)
		{
			double currentValueX = coordinates[i].x;
			double currentValueY = coordinates[i].y;
			if(currentValueX < minX) minX = currentValueX;
			if(currentValueX > maxX) maxX = currentValueX;
			if(currentValueY < minY) minY = currentValueY;
			if(currentValueY > maxY) maxY = currentValueY;
		}

		//Get the center coordinates and return.
		double x = (maxX - minX)/2 + minX;
		double y = (maxY - minY)/2 + minY;
		Point center = new Point(x,y);
		return center;
	}

	//Zero's out the center as the origin and re-evaluates all the data points.
	public static void adjustCoordinates(Point[] coordinates, Point center)
	{
		for(int i = 0; i < coordinates.length; i++)
		{
			coordinates[i].x -= center.x;
			coordinates[i].y -= center.y;
		}
	}

	//Returns the radius of the inner circle
	public static double getInnerRadius(Point[] coordinates)
	{
		//Grab the furthest point of the coordinates, that will be the outer radius.
		Point origin = new Point(0.0,0.0);
		double max = 0;

		for(int i = 0; i < coordinates.length; i++)
		{
			double currentDistance = coordinates[i].getDistance(origin);
			if(currentDistance > max) max = currentDistance;
		}

		//Divide this number by 2, to obtain the inner circle radius.
		double innerRadius = max/2;
		return innerRadius;
	}

	/*Returns an array of linearly spaced points that lie on the inner circle
	 * that will guide the greedy algorithm*/
	public static Point[] getInnerCircle(Point[] coordinates,double innerRadius)
	{	
		//Get linearly spaced points between 0 and 2pi. Size will be number of total points. 
		double[] spacedPoints = new double[coordinates.length];
		double h = (2*Math.PI - 0)/(spacedPoints.length - 1);
		spacedPoints[spacedPoints.length - 1] = 2*Math.PI;
		for(int i = 1; i < spacedPoints.length - 1; i++) spacedPoints[i] = spacedPoints[i-1] += h;

		//Get generate the Point object (x and y coordinate), for all of these spaced points.
		Point[] innerCirclePoints = new Point[spacedPoints.length];

		for(int i = 0; i < innerCirclePoints.length; i++)
		{
			double x = Math.cos(spacedPoints[i])*innerRadius;
			double y = Math.sin(spacedPoints[i])*innerRadius;
			
			Point newPoint = new Point(x,y);
			innerCirclePoints[i] = newPoint;
		}
		return innerCirclePoints;
	}

	//Rotates the array of innerCirclePoints so the first one is the closest to originCity.
	public static Point[] rotateInnerCircle(Point[] innerCirclePoints, Point[] coordinates, int currentCity)
	{
		//Begin iterating to find the closest point on circle to origin city.
		Point originPoint = coordinates[currentCity];
		double minDistance = innerCirclePoints[0].getDistance(originPoint);
		int minIndex = 0;

		for(int i = 1; i < innerCirclePoints.length; i++)
		{
			double currentDistance = innerCirclePoints[i].getDistance(originPoint);
			if(currentDistance < minDistance) 
			{
				minDistance = currentDistance;
				minIndex = i;
			}
		}

		//Rotate the area so that index is the first position.
		int rotate = innerCirclePoints.length - minIndex;

		//Save the temp list
		Point temp[] = new Point[minIndex];
		for(int i = 0; i < temp.length; i++) temp[i] = innerCirclePoints[i];

		//Create new list and move the ends of previous list to front of this.
		Point[] newList = new Point[innerCirclePoints.length];
		for(int i = 0, j = minIndex; j < innerCirclePoints.length; i++, j++)
		{
			newList[i] = innerCirclePoints[j];
		}

		//Finally, replace the rest of the this list with temp.
		for(int i = 0, j = rotate; i < temp.length; i++, j++) newList[j] = temp[i];
		return newList;
	}	

	//Return a matrix with the distances
	public static int[][] getMatrix(Point[] coordinates)
	{
		//Iterate the array and compute the distance between points and store in matrix.
		int size = coordinates.length;
		int[][] matrix = new int[size][size];
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				if(i == j) matrix[i][j] = 0;
				matrix[i][j] = coordinates[i].getDistance(coordinates[j],1);
			}
		}
		return matrix;
	}

	//Fills a column with -1 after it chosen to be visited.
	public static void killColumn(int[][] matrix, int col)
	{
		for(int row = 0; row < matrix.length; row++) matrix[row][col] = -1;
	}

	//Checks to see if can move the circle safely.
	public static boolean safeMove(Point[] coordinates, Point[] innerCirclePoints,
		         		int pointCount,int[][] matrix, int originCity, double innerRadius)
	{	
		Point circlePoint = innerCirclePoints[pointCount+1];
		for(int i = 0; i < matrix[0].length; i++)
		{
			if(matrix[0][i] != -1 && i != originCity)
			{
				Point currentCity = coordinates[i];
				if(currentCity.getDistance(circlePoint) <= innerRadius) return true;
			}
		}
		return false;
	}

	//Main method.
	public static void main(String[] args)
	{
		/*Get the x and y coordinates from the data.
		 * Use that to get the center and adjust for the center being the origin.
		 * So, the points on the innerCircle can be calculated easily.*/
		Point[] coordinates = getCoordinates(150);
		Point center = getCenter(coordinates);
		adjustCoordinates(coordinates,center);
		double innerRadius = getInnerRadius(coordinates);
		Point[] innerCirclePoints = getInnerCircle(coordinates,innerRadius);

		//Generates the matrix of distances.
		int[][] cities = getMatrix(coordinates);

		//Get the starting position and initialize sum of travel,and reorder innerCircle point array.
		int currentCity = (int)(Math.random()*cities.length);
		innerCirclePoints = rotateInnerCircle(innerCirclePoints,coordinates,currentCity);
		int originCity = currentCity;
		int distanceTraveled = 0;

		//Begin loop going through matrix until it is all -1's.
		int cityCount = 1;
		int pointCount = 0;
		while(cityCount != cities.length)
		{
			//Iterate every element in the row, and get a score to add to and ArrayList
			ArrayList<City> orderedCities = new ArrayList<>();
			for(int col = 0; col < cities[0].length; col++)
			{
				if(cities[currentCity][col] != -1 && col != originCity)
				{
					City nextCity = new City(cities[currentCity][col],col);
					orderedCities.add(nextCity);
				}
			}

			//Sort the avaiable cities and begin to check for the smallest one within
			//the radius of the current point.
			Collections.sort(orderedCities);
			int indexOfBest = -1;
			while(indexOfBest == -1)
			{
				//Checking to make sure we dont break, and getting current point of circle.
				if(pointCount + 1 >= innerCirclePoints.length) pointCount = 0;
				else pointCount++;
				Point currentInnerPoint = innerCirclePoints[pointCount];

				for(int i = 0; i < orderedCities.size(); i++)
				{
					int indexOfCoord = orderedCities.get(i).col;
					double thisDistance = coordinates[indexOfCoord].getDistance(currentInnerPoint);
					if(thisDistance <= innerRadius*1.085)
					{
						indexOfBest = indexOfCoord;
						break;
					}
				}
			}

			//Add the new distance and change currentCity and kill the columns.
			distanceTraveled += cities[currentCity][indexOfBest];
			currentCity = indexOfBest;
			killColumn(cities,currentCity);
			cityCount++;

			//If were done
			if(cityCount == cities.length) distanceTraveled += cities[currentCity][originCity];
		}
		System.out.println(distanceTraveled);
	}
}
