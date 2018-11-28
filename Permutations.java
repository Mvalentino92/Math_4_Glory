import java.util.*;
import java.math.*;

public class Permutations
{
	//Dynamic approach to generating permutations in lexographic order.
	//p = current matrix of permutations
	//n = next set of permutations (if p is the permutations for 4, n is 5)
	//arr = List of things to permutate
	public static String[][] nextPermutations(int[][] p, int n, String[] arr)
	{
		//If hit bound, create and populate actual permutations array and return it
		if(p[0].length == arr.length)
		{
			String[][] retval = new String[p.length][p[0].length];
			for(int i = 0; i < p.length; i++)
			{
				for(int j = 0; j < p[0].length; j++)
				{
					retval[i][j] = arr[p[i][j]];
				}
			}
			return retval;
		}

		//Create the array to recursively pass for next call
		int[][] np = new int[p.length*n][p[0].length+1];
		int rowIndex = 0;

		//Iterate n times
		for(int i = 0; i < n; i++)
		{
			//Iterate p
			for(int j = 0; j < p.length; j++)
			{
				//Put first element
				int colIndex = 0;
				np[rowIndex][colIndex++] = i;

				//Iterate contents of p
				for(int k = 0; k < p[0].length; k++)
				{
					np[rowIndex][colIndex++] = p[j][k] >= i ? p[j][k] + 1 : p[j][k];
				}
				rowIndex++;
			}
		}
		//Recursively call with new permuations and n+1
		return nextPermutations(np,n+1,arr);
	}

	//Helper function
	public static String[][] getPermutations(String[] arr)
	{
		int[][] init = {{0}}; //This is all were starting with!
		return nextPermutations(init,2,arr);
	}

	public static void main(String[] args)
	{
		//Create Scanner to prompt for input
		Scanner input = new Scanner(System.in);
		System.out.print("How many elements would you like the permutations of?"+
				 "Enter a positive number <= 10: ");
		int len = input.nextInt();
		String[] arr = new String[len];
		for(int i = 0; i < len; i++)
		{
			System.out.print("Enter element: ");
			arr[i] = input.next();
		}

		//Get the permuations, and time computation
		double start = System.nanoTime();
		String[][] permutations = getPermutations(arr);
		double time = (System.nanoTime() - start)/1e9;

		//Print permutations
		System.out.println("\nThe permutations are:");
		for(int i = 0; i < permutations.length; i++)
		{
			for(int j = 0; j < permutations[i].length; j++) 
			{
				System.out.print(permutations[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("Number of permutations for a list of "+arr.length+
				   " elements is "+permutations.length+", and took "
				   +time+" seconds.");
	}
}
