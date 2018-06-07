import java.math.*;
import java.util.*;
public class PE484_Attempt
{
	//Performs sieve to return primes.
	public static BigInteger[] sieve(int N)
	{
		//Create array of ints
		int[] nums = new int[N+1];	
		for(int i = 0; i <= N; i++) nums[i] = i;

		//Perform the sieve, and mark non primes as negative 1, while keeping count.
		int primeCount = N - 2;
		int Nsq = (int)Math.sqrt(N);
		for(int i = 2; i <= Nsq; i++)
		{
			if(nums[i] == -1) continue;
			for(int j = i + i; j <= N; j += i)
			{
				if(nums[j] != -1)
				{
					nums[j] = -1;
					primeCount--;
				}
			}
		}
		
		//Add the remaining primes to the real list.
		BigInteger[] primes = new BigInteger[primeCount+1];
		int index = 0;
		for(int i = 2; i <= N; i++)
		{
			if(nums[i] > 0)
			{
				primes[index++] = BigInteger.valueOf(nums[i]);
			}
		}

		return primes;
	}

	//Calculates this numbers contribution
	public static BigInteger contribution(BigInteger num, int count)
	{
		if(count == 1) return BigInteger.ONE;
		if(!(BigInteger.valueOf(count).mod(num)).equals(BigInteger.ZERO)) count--;

		BigInteger retval = num;
		for(int i = 0; i < count - 1; i++) retval = retval.multiply(num);
		return retval;
	}

	//Calculates the GCD
	public static BigInteger GCD(Stack<BigInteger> nums)
	{
		Stack<BigInteger> nums2 = (Stack<BigInteger>)nums.clone();
		BigInteger retval = BigInteger.ONE;
		while(!nums2.empty())
		{
			int count = 1;
			BigInteger current = nums2.pop();
			while(!nums2.empty())
			{
				if(nums2.peek().equals(current)) 
				{
					nums2.pop();
					count++;
				}
				else break;
			}

			retval = retval.multiply(contribution(current,count));
		}
	
		return retval;
	}

	//Returns the sum using the prime factors.
	public static BigInteger solution(int N, BigInteger[] primes)
	{
		Stack<BigInteger> nums = new Stack<>();
		BigInteger sol = BigInteger.ZERO;
		BigInteger total = BigInteger.ONE;
		int stop = N/2;
		BigInteger bound = BigInteger.ZERO;
		int index = 0;
		int count = 0;

		for(int i = 0; i < primes.length; i++)
		{
			if(primes[i].compareTo(BigInteger.valueOf(stop)) >= 0) bound = primes[i];
		}

		while(!total.equals(bound))
		{
			nums.push(primes[index]);
			total = total.multiply(primes[index]);

			if(total.compareTo(BigInteger.valueOf(N)) > 0)
			{
				total = total.divide(nums.pop());
				BigInteger cur = nums.pop();
				total = total.divide(cur);
				index = Arrays.asList(primes).indexOf(cur) + 1;
			}
			else 
			{
				count++;
				sol = sol.add(GCD(nums));
			}
		}

		sol = sol.add(BigInteger.valueOf(N - (count+1)));
		return sol;
	}

	public static void main(String[] args)
	{
		BigInteger[] primes = sieve(10000);
		System.out.println(solution(10000,primes));
	}
}
