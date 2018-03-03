import java.math.BigInteger;
import java.util.*;
public class Partitions_Dynamic_Versus_Recursive
{
	public static BigInteger partition(BigInteger n,BigInteger summand, BigInteger sum)
	{
		if(sum.equals(n)) return BigInteger.ONE;
		if(summand.equals(BigInteger.ZERO) || sum.compareTo(n) == 1) return BigInteger.ZERO;

		return partition(n,summand,sum.add(summand)).add(partition(n,summand.subtract(BigInteger.ONE), sum));
	}

	public static BigInteger getSum(BigInteger[] list)
	{
		BigInteger sum = BigInteger.ZERO;
		for(int i = 0; i < list.length - 1; i++) sum = sum.add(list[i]);
		return sum;
	}

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("What value of n would you like all of the partitions up until?: ");
		int max = input.nextInt();
		System.out.println();

		BigInteger justOne = BigInteger.ONE;
		ArrayList<BigInteger[]> previous = new ArrayList<>();

		BigInteger[] one = {justOne,justOne};
		previous.add(one);

		double start = System.nanoTime()/1e9;
		for(int k = 0; k <= max; k++)
		{
			BigInteger[] nextArray = new BigInteger[k/2 + 1];
			int checkBackTo = k/2;

			for(int i = 0; i < checkBackTo; i++)
			{
				int arraySize = previous.size();
				BigInteger[] arrayToCheck = previous.get(arraySize - (i + 1));
				int lengthOfArrayToCheck = arrayToCheck.length - 1;
				
				BigInteger sum = BigInteger.ZERO;
				for(int j = 0; j < i && j < lengthOfArrayToCheck; j++)
				{
					sum = sum.add(arrayToCheck[j]);
				}
				nextArray[i] = arrayToCheck[lengthOfArrayToCheck].subtract(sum);
			}

			nextArray[k/2] = BigInteger.ONE;
			nextArray[k/2] = nextArray[k/2].add(getSum(nextArray));
			System.out.println("Number: "+k+"\t"+"Partition: "+nextArray[k/2]);

			previous.add(nextArray);
		}
		double dynamic = System.nanoTime()/1e9 - start;
		System.out.println("\nThe dynamic approach took "+dynamic+
				" seconds to compute up to n = "+max);
		System.out.println("**********************************************************************************************");
		System.out.print("Press ENTER to see the recursive approach.");
		input.nextLine();
		input.nextLine();
		System.out.println();

		start = System.nanoTime()/1e9;
		BigInteger bigMax = BigInteger.valueOf(max + 1);
		BigInteger zero = BigInteger.ZERO;
		for(BigInteger i = zero; i.compareTo(bigMax) == -1; i = i.add(justOne))
		{
			System.out.println("Number: "+i+"\t"+"Partition: "+
					partition(i,i,zero));
		}
		double recursive = System.nanoTime()/1e9 - start;
		System.out.println("\nThe recursive approach took "+recursive+
				" seconds to compute up to n = "+max);

		System.out.println("***********************************************************************************************");
		if(dynamic < recursive)
		{
		double percentFaster = ((recursive - dynamic)/dynamic)*100;
		System.out.printf("\nThe dynamic approach is %.2f%% faster than the recursive approach.\n",percentFaster);
		}
		else
		{
			double percentFaster = ((dynamic - recursive)/recursive)*100;
		System.out.printf("\nThe recursive approach is %.2f%% faster than the dynamic approach.\n",percentFaster);
		}
	}
}
