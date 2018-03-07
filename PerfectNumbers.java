import java.util.*;
public class PerfectNumbers
{
	public static void main(String[] args)
	{
		ArrayList<Integer> perfectNumbers = new ArrayList<>();

		for(int i = 2; i < 10000; i++)
		{
			int sum = 1;
			double squareRoot = Math.sqrt(i);
			if(squareRoot == (int)squareRoot) sum += (int)squareRoot;
			for(int j = 2; j < Math.sqrt(i) && sum <= i; j++)
			{
				if(i % j == 0)
				{
					sum += j;
					sum += (int)(i/j);
				}
			}
			if(sum == i) perfectNumbers.add(i);
		}
		for(Integer num : perfectNumbers) System.out.println(num);
	}
}
