/* Lists the prime factors of a number in decreasing order */

import java.util.*; 
import java.math.*; 
import java.io.*; 

public class PrimeFactors
{
	public static void printFactors(int number)
	{
		if(number == 1) 
		{
			System.out.println();
			return;
		}

		for(int i = 2; i <= number; i++)
		{
			if(number % i == 0)
			{
				System.out.print(i+" ");
				printFactors(number / i);
				return;
			}
		}
	}

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Number: ");
		int number = input.nextInt();
		System.out.print(number+": ");
		printFactors(number);
	}
}
