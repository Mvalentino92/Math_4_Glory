/* Lists the prime factors of a number in non decreasing order */

import java.util.*; 
import java.math.*; 
import java.io.*; 

public class PrimeFactors
{
	//Constant's I'll need.
	public static final BigInteger ZERO = BigInteger.ZERO;
	public static final BigInteger ONE = BigInteger.ONE;
	public static final BigInteger TWO = new BigInteger("2");
	public static final BigInteger THREE = new BigInteger("3");

	//Double checks to make sure the factor is prime.
	public static boolean isPrime(BigInteger number)
	{
		if(number.mod(TWO).equals(ZERO)) return number.divide(TWO).equals(ONE) ? true : false;
		BigInteger divisor = THREE;
		while(number.divide(divisor).compareTo(divisor) != -1)
		{
			if(number.mod(divisor).equals(ZERO)) return false;
			divisor = divisor.add(TWO);
		}
		return true;
	}
			
	//Prints the prime factors of a number.
	public static void printFactors(BigInteger number)
	{
		if(number.equals(ONE)) 
		{
			System.out.println();
			return;
		}

		for(BigInteger i = TWO; i.compareTo(number) != 1; i = i.add(ONE))
		{
			if(number.mod(i).equals(ZERO))
			{
				if(isPrime(i)) System.out.print(i+" ");
				else System.out.print("X ");
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
