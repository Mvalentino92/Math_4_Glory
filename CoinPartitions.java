import java.util.*;
import java.math.BigInteger;
public class CoinPartitions
{
	//I need this to get the sum of the counts when I "look back" at other numbers
	public static BigInteger getSum(BigInteger[] list)
	{
		BigInteger sum = BigInteger.ZERO;
		for(int i = 0; i < list.length - 1; i++) sum = sum.add(list[i]);
		return sum;
	}

	public static void main(String[] args)
	{
		//User input
		Scanner input = new Scanner(System.in);
		System.out.print("What number would you like to know the partitions for?: ");
		int value = input.nextInt();

		//Setting variables I'll need, and creating my ArrayList that holds the information for each number and its partitions.
		BigInteger justOne = BigInteger.ONE;
		BigInteger solution = BigInteger.ONE;
		ArrayList<BigInteger[]> previous = new ArrayList<>();

		//Adding the base case.
		BigInteger[] one = {justOne,justOne};
		previous.add(one);

		for(int k = 2; k <= value; k++)
		{
			//Creating the new array for this current number and it's partitions, and finding the value to check back to.
			BigInteger[] nextArray = new BigInteger[k/2 + 1];
			int checkBackTo = k/2;

			for(int i = 0; i < checkBackTo; i++)
			{
				int arraySize = previous.size();
				BigInteger[] arrayToCheck = previous.get(arraySize - (i + 1));
				int lengthOfArrayToCheck = arrayToCheck.length - 1;
				
				/*Setting the the sum to zero. For each iteration, it will hold how many partitions end in +(some number) 
				 * Again, like for 5...how many end in +1, or +2.*/
				BigInteger sum = BigInteger.ZERO;
				for(int j = 0; j < i && j < lengthOfArrayToCheck; j++)
				{
					sum = sum.add(arrayToCheck[j]);
				}
				//Adding the sum of the counts for this interation to the array.
				nextArray[i] = arrayToCheck[lengthOfArrayToCheck].subtract(sum);
			}
			//Now for the final index of the array, I need to get the total of all the indexes, because the final index holds the actual
			//Amount of partitions for each number total. (It'll be the sum + 1, (because like just 5 counts too ya know?)
			nextArray[k/2] = BigInteger.ONE;
			nextArray[k/2] = nextArray[k/2].add(getSum(nextArray));

			//Add the completed "next array", to the ArrayList, and increment k.
			previous.add(nextArray);
			solution = nextArray[k/2];
		}

		//Print the solution.
		System.out.println(solution);
	}
}
