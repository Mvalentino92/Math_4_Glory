import java.util.*;
//Class for representinga simple pair of (x,y) coordinates.
class Point implements Comparable<Point>
{
	double x;
	double y;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public int compareTo(Point p)
	{
		double v = x - p.x;
		if(v < 0) return -1;
		else if(v > 0) return 1;
		else return 0;
	}
}

public class ClosestPoints
{
	public static void main(String[] args)
	{
		int n = 1000000;
		Point[] ps = new Point[n];
		for(int i = 0; i < ps.length; i++)
		{
			double x = (Math.random() - 0.5)*n*3;
			double y = (Math.random() - 0.5)*n*3;
			ps[i] = new Point(x,y);
		}
		System.out.println("For "+n+" elements:");

		/*Run Brute Force
		double start = System.nanoTime();
		double bf = bruteForce(ps);
		double time = (System.nanoTime() - start)/1e9;
		System.out.println("Brute force took "+time+" seconds.");*/

		//Run Divide and Conquer
		double start = System.nanoTime();
		double dc = dac(ps);
		double time = (System.nanoTime() - start)/1e9;
		System.out.println("DAC took "+time+" seconds.");

		/*Verdict
		boolean verdict = Math.abs(bf -dc) < 1e-15;
		if(verdict) System.out.println(dc);
		else System.out.println("Brute Force got: "+bf+"\tDAC got: "+dc);*/
	}

	//Computes distance between two points using Pythagoras Theorem.
	public static double dist(Point p1, Point p2)
	{
		double xd = p2.x - p1.x;
		double yd = p2.y - p1.y;
		return Math.sqrt(xd*xd + yd*yd);
	}

	//Brute for solution. Examine all distances between all points. Return minimum.
	public static double bruteForce(Point[] ps)
	{
		double min = Double.POSITIVE_INFINITY;
		for(int i = 0; i < ps.length-1; i++)
		{
			for(int j = i + 1; j < ps.length; j++)
			{
				double d = dist(ps[i],ps[j]);
				min = d < min ? d : min;
			}
		}
		return min;
	}

	//Divide and Conquer. Helper function
	public static double dacHelper(Point[] ps, int s, int t)
	{
		//Base case. A single visible point is infinitely far away from all other points.
		if(s == t) return Double.POSITIVE_INFINITY;

		//Divide the lists in half and get the minimum from each side.
		int mid = (s + t)/2;
		double line = (ps[mid].x + ps[mid+1].x)/2;
		double min = Math.min(dacHelper(ps,s,mid),dacHelper(ps,mid+1,t));

		//Compare all points that are less than this distance from the line
		int i = mid;
		while(i >= s && line - ps[i].x < min)
		{
			int j = mid + 1;
			while(j <= t && ps[j].x - ps[i].x < min)
			{
				double d = dist(ps[i],ps[j]);
				min = d < min ? d : min;
				j++;
			}
			i--;
		}
		return min;
	}

	public static double dac(Point[] ps)
	{
		Arrays.sort(ps);
		return dacHelper(ps,0,ps.length-1);
	}
}
