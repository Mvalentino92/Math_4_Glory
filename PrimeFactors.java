/* Lists the prime factors of a number in decreasing order */

import java.util.*; 
import java.math.*; 
import java.io.*; 

public class PrimeFactors
{
	public static void printFactors(BigInteger number)
	{
		if(number.equals(BigInteger.ONE)) 
		{
			System.out.println();
			return;
		}

		for(BigInteger i = new BigInteger("2"); i.compareTo(number) != 1; i = i.add(BigInteger.ONE))
		{
			if(number.mod(i).equals(BigInteger.ZERO))
			{
				System.out.print(i+" ");
				printFactors(number.divide(i));
				return;
			}
		}
	}

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Number: ");
		BigInteger number = input.nextBigInteger();
		System.out.print(number+": ");
		printFactors(number);
	}
}
