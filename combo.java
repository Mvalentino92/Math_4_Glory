import java.util.*;
/*This will return a list of all the possible unique combinations with N summands that make a desired sum
 * from 1-n. Where the square root of n is N. 
 * For example all possible combinations of 1-16 that equal 34, where the sum is comprised of 4 summands.*/
public class combo
{
	public static int count = 0;
	public static boolean combos(ArrayList<int[]> combinations,int[] numbers, int desiredSum,
		       	int depth, int i,int summands)
	{
		for(i = i; i < numbers.length - depth; i++)
		{
			if(depth == 2)
			{
				boolean addedSome = false;
				for(i = i; i <= numbers.length - depth; i++)
				{
					for(int j = i + 1; j < numbers.length; j++)
					{
						if(numbers[i] + numbers[j] == desiredSum)
						{
							int[] newCombo = new int[summands];
							newCombo[summands - depth] = numbers[i];
							newCombo[summands - 1] = numbers[j];
							combinations.add(newCombo);
							count++;
							addedSome = true;
						}
					}
				}
				if(addedSome) return true;
			}
			else
			{
				int listSize = combinations.size();
				boolean addThem = combos(combinations,numbers,desiredSum - numbers[i],
					       	depth - 1, i + 1, summands);
				if(addThem)
				{
					for(int k = listSize; k < count; k++)
					{
						combinations.get(k)[summands - depth] = numbers[i];
					}
				}
			}
		}
		return true;
	}

	public static void main(String[] args)
	{
		//Setting the number to go up to from 1 (1-length), and the desired sum of all combos.
		int length = 49;
		int desiredSum = 175;

		//Initialzing the list of possible combinations, as well as the remaining variables.
		ArrayList<int[]> combinations = new ArrayList<>();
		int[] numbers = new int[length];
		for(int i = 1; i <= numbers.length; i++) numbers[i-1] = i;
		int summands = (int)(Math.sqrt(length));
		if(length % summands != 0) summands++;

		//Calculating the list of unique combinations.
		combos(combinations,numbers,desiredSum,summands,0,summands);

		//Printing each combination.
		for(int i = 0; i < combinations.size(); i++)
		{
			int checkSum = 0;
			String retval = new String();
			System.out.print("Combo "+(i+1)+": ");
			for(int j = 0; j < combinations.get(i).length; j++)
			{
				checkSum += combinations.get(i)[j];
				retval += Integer.toString(combinations.get(i)[j]);
				retval += " ";
			}
			if(checkSum == desiredSum) System.out.println(retval);
			else
			{
				System.out.println("WRONG");
				break;
			}
		}
		System.out.println("There are "+combinations.size()+" to write "+desiredSum+" as the sum of "
				+summands+" numbers uniquely from 1 to "+length+".");
	}
}
