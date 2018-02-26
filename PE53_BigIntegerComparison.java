import java.math.BigInteger;
/*There are exactly ten ways of selecting three from five, 12345:
 *
 * 123, 124, 125, 134, 135, 145, 234, 235, 245, and 345
 *
 * In combinatorics, we use the notation, 5C3 = 10.
 *
 * In general,
 *
 * nCr =nCr
 * n!
 * r!(n−r)!
 * ,where r ≤ n, n! = n×(n−1)×...×3×2×1, and 0! = 1.
 * It is not until n = 23, that a value exceeds one-million: 23C10 = 1144066.
 *
 * How many, not necessarily distinct, values of  nCr, for 1 ≤ n ≤ 100, are greater than one-million?*/
public class PE53_BigIntegerComparison
{
	//Calculatest the factorial using BigInteger.
	public static BigInteger factorial(int number)
	{
		BigInteger retval = BigInteger.ONE;
		
		for(int i = 2; i <= number; i++)
		{
			retval = retval.multiply(BigInteger.valueOf(i));
		}

		return retval;
	}

	public static void main(String[] args)
	{
		double start = System.nanoTime();
		//Make two arrays for n and r, both containing 1-100. (Could use just one, but will use two for clarity)
		double[] n = new double[101];
		double[] r = new double[101];
		for(int i = 1; i < n.length; i++)
		{
			n[i] = i;
			r[i] = i;
		}

		//Initialize count, and start to iterate n(i) and r(j).
		int count = 0;
		for(int i = 1; i < n.length; i++)
		{
			for(int j = 1; j < i; j++)
			{

				/*Create variables that hold where n and r start, and what value they end at.
				 * Again, redundant for clarity.*/
				int nStart = j + 1;
				int rStart = 1;
			      	
				int nEnd = i;
				int rEnd = i - j;

				double value = 1;

				//While still within the bounds of n and r....
				while(nStart <= nEnd && rStart <= rEnd)
				{
					/*Alternate between multiplying by the numerator(n) and dividing by the denominator(r).
					 * This will ensure that despite dealing with factorials, the value will never get too high or too low.*/
					if(value > 1e5) value /= r[rStart++];
					else if(value < 1e-5) value *= n[nStart++];
					else
					{
						value /= r[rStart++];
						value *= n[nStart++];
					}
				}

				//If n terms (in the numerator) remain, keep multiplying them while under our target value as long as they exist.
				while(nStart <= nEnd && value <= 1e6) value *= n[nStart++];
				
				//If we hit our mark, add one to count.
				if(value > 1e6) count++;
			}
		}
		double end = System.nanoTime();
		double elapsed = end - start;
		System.out.println("The number of combinatoric selections for these parameters is: "+count+", and "+
			       	   "int[] method took "+(elapsed/1e6)+" milliseconds to compute!");

//***************************************************END FIRST METHOD******************************************************************************
		
		//Creating every value as a BigInteger
		start = System.nanoTime();
		count = 0;
		BigInteger targer = new BigInteger("1000000");

		for(int i = 1; i <= 100; i++)
		{
			for(int j = 1; j <= i; j++)
			{
				BigInteger numerator = factorial(i);
				BigInteger denominator1 = factorial(j);
				BigInteger denominator2 = factorial(i-j);

				denominator1 = denominator1.multiply(denominator2);
				numerator = numerator.divide(denominator1);

				if(numerator.compareTo(targer) > 0) count++;
			}
		}
		end = System.nanoTime();
		elapsed = end - start;
		System.out.println("The number of combinatoric selections for these parameters is: "+count+", and "+
				   "BigInteger method took "+(elapsed/1e6)+" milliseconds to compute!");
	}
}
