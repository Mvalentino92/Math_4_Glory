import java.util.*; 
import java.math.*; 
import java.io.*; 

public class PE328_Attempt
{
	/* Make functions that check definitively for the highest possible value obtained
	 * from any reach. Including: The current key, and the next key. 
	 * Perhaps just always start all the way from the beginning,
	 * and use a while loop to keep iterating the chosen reaches, and stop when you pass
	 * the parabolic critical maximum. like 3 7 8 6 4 (its 8)
	 * Do this for BOTH things.
	 * REMEMBER TO NOT GO OUT OF BOUNDS AND HAVE NEGATIVES SCREW IT UP*/
	public static int getLargest(int N, int key, ArrayList<int[]> table)
	{
		int[] values = table.get(key);
		int scale = N - key;
		int currentReach = scale;

		int[] vals = new int[N];
		int index = 0;
		while(scale >= key/2)
		{
			int toAdd = 0;
			for(int i = 0; i < values.length; i++) toAdd += (values[i] + scale);
			vals[index++] = currentReach + toAdd;
			scale -= (key +  1);
			currentReach += scale;
		}

		int max = 0;
		for(int i = 0; i < vals.length; i++)
		{
			if(vals[i] > max) max = vals[i];
		}
		return max;
	}

	public static void fillFirst(int[] n, int[] left)
	{
		for(int i = 1; i < left.length + 1; i++) n[i] = left[i-1];
	}

	public static void fillSecond(int[] n, int[] left)
	{
		int scale = n[0];
		for(int i = 1; i < left.length + 1; i++) n[i] = (left[i-1] + scale);
	}

	public static void main(String[] args)
	{
		//The keys and their corresponding values will be stored in a look up table.
		//A variable called "currentKey" will be incrementedby 4, everytime the next
		//key level is achieved.
		int currentKey = 3;
		ArrayList<int[]> keyTable = new ArrayList<>();

		for(int i = 0; i < 100000; i++) keyTable.add(new int[1]);
		int[] a = {2};
		keyTable.set(3,a);
		int[] b = {4,6};
		keyTable.set(7,b);
		
		int n = 11;
		int cut = 3;
		int left = cut;
		int power = 1;

		while(n < 50000)
		{
			for(int i = 0; i < power; i++)
			{
				left += 4;
				int[] leftsArray = keyTable.get(left);
				int[] nArray = new int[leftsArray.length + 1];
				nArray[0] = n - cut;
				fillFirst(nArray,leftsArray);
				keyTable.set(n,nArray);
				n += 4;
			}
			for(int i = 0; i < power; i++)
			{
				int[] leftsArray = keyTable.get(left);
				int[] nArray = new int[leftsArray.length + 1];
				nArray[0] = n - left;
				fillSecond(nArray,leftsArray);
				keyTable.set(n,nArray);
				n += 4;
			}
			cut = left;
			power *= 2;
		}


		
		//Setting up the sum for each numbers lowest cost search.
		long sum = 3;

		/*The algorithm works as follows:
		 * We start with the current key. 
		 * Each iteration, we will see how many reaches yields the worst scenario.
		 * I'm assuming the reaches increment in evenly by 1.
		 * So start with a reach, do one level, and add a sum toget stopping at the level.
		 * Do so by accessing the keys table values, and getting the index of the choices
		 * for that key. Compare n reaches to (n+1) reaches. Keep track of how many reaches
		 * you are currently on as well. Maybe use a for loop that iterates the number reaches
		 * and updates the sum. Do first reach before for loop. Then have the for loops start from 1
		 * and go til reach (reach will be initialized to 1).
		 * Get the largest value for the current key. Then do a single reach for the next key.
		 * If that value is LOWER, that is the solution, and we add 4 to currentKey,
		 * as well as set reach back to 1*/

		for(int N = 4; N <= 10000; N++)
		{
			int[] choices = new int[1000];
			for(int i = 0, j = 0; i < choices.length; i++, j += 4)
			{
				choices[i] = getLargest(N,currentKey + j,keyTable);
			}

			int min = choices[0];
			int minDex = 0;
			for(int i = 0; i < choices.length; i++)
			{
				if(choices[i] < min && choices[i] != 0)
				{
					min = choices[i];
					minDex = i;
				}
			}
			sum += min;
			currentKey += minDex*4;

			/*int choice = getLargest(N,currentKey,keyTable);
			int firstLevel = getLargest(N,(currentKey + 4), keyTable);
			int secondLevel = getLargest(N,(currentKey + 8),keyTable);
			if((firstLevel <= choice && firstLevel <= secondLevel) && firstLevel != 0)
			{
				currentKey += 4;
				sum += firstLevel;
			}
			else if((secondLevel <= choice && secondLevel <= firstLevel) && secondLevel != 0)
			{
				currentKey += 8;
				sum += secondLevel;
			}
			else sum += choice;*/
		}
		System.out.println("The sum  to 200000 is: "+sum);
		System.out.println("Current key is: "+currentKey);

		for(int i = 3; i < 50; i += 4)
		{
			int[] values = keyTable.get(i);
			System.out.print(i+": ");
			for(int j = 0; j < values.length; j++) System.out.print(values[j]+" ");
			System.out.println();
		}

	}
}
		
