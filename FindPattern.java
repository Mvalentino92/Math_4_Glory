import java.util.*; 
import java.math.*; 
import java.io.*; 

public class FindPattern
{
	//The global ArrayList containing all the patterns.
	public static ArrayList<int[]> patterns = new ArrayList<>();

	/*Function that traverses an array by the specified stride,
	 * returns a child array that potentially representative of an identical children set.
	 * If the array is the proper length, it was from an indentical children set.
	 * If the length is not (+1), then it is not*/
	public static int[] childrenMatch(int[] arr, int stride)
	{
		//The first child array should be matched by all other children.
		int[] dud = new int[stride + 1];
		int[] childToMatch = new int[stride];
		for(int i = 0; i < stride; i++) childToMatch[i] = arr[i];

		//Iterate the amount of other children remaining.
		for(int i = 1; i < arr.length/stride; i += 1)
		{
			//Check that all elements in this child match the main child.
			int nextIndex = 0;
			for(int j = i*stride; j < (i+1)*stride; j++)
			{
				if(childToMatch[nextIndex++] != arr[j]) return dud;
			}
		}
		return childToMatch;
	}

	/*Function that attempts to keep breaking down the current child array (The first potential longest pattern)
	 * into indentical children arrays. It will keep recursivley breaking down childs into more children arrays 
	 * until the children are no longer indentical. Once this happens, the parent of these non-identical children
	 * will be a potential longest pattern. This pattern must be checked to hold throughout the entire array at the end.
	 * Stores all results of subsequent calls in a global array.*/
	public static void recurChildren(int[] child)
	{
		int length = child.length;
		for(int i = 2; i <= length/2; i++)
		{
			if(length % i == 0)
			{
				int stride = length/i;
				int[] currentChild = childrenMatch(child,stride);
				if(currentChild.length == stride)
				{
					patterns.add(currentChild);
					recurChildren(currentChild);
					break;
				}
			}
		}
	}

	/*Function that iterates through the current sequence and searches for the first set of indentical children arrays.
	 * Stores those children in the global ArrayList, and attempts to keep breaking down these children to see if this 
	 * original child is actually just the multiple "stacks" of the actual longest pattern in a row*/
	public static void findChildren(int[] arr)
	{
		int length = arr.length;
		int bound = length/2;
		boolean foundPattern = false;
		for(int i = length - 1; i >= bound; i--)
		{
			for(int j = 2; j < i/2; j++)
			{
				if(i % j == 0)
				{
					int stride = i/j;
					int[] currentChild = childrenMatch(arr,stride);
					if(currentChild.length == stride)
					{
						patterns.add(currentChild);
						recurChildren(currentChild);
						foundPattern = true;
						break;
					}
				}
			}
			if(foundPattern) break;
		}
	}

	/*Finally, a function that ensures the found pattern actually constitutes a legit pattern. 
	 * Confirms that it appears without a miss til the end of the original array.
	 * If everything is good, return the pattern in an array, otherwise return an array only holding -1.*/
	public static int[] getPattern(int[] arr)
	{
		findChildren(arr);
		int[] dud = {-1};
		int patternsLength = patterns.size();
		int[] potentialPattern;

		if (patternsLength != 0) potentialPattern = patterns.get(patterns.size()-1);
		else return dud;

		int[] test = childrenMatch(arr,potentialPattern.length);
		if(test.length == potentialPattern.length)
		{
			int overflow = arr.length % potentialPattern.length;
			int start = arr.length - overflow;
			int index = 0;
			for(int i = start; i < arr.length; i++)
			{
				if(arr[i] != potentialPattern[index++]) return dud;
			}
			return potentialPattern;
		}
		return dud;
	}

	public static void main(String[] args)
	{

		//If pattern exists, will return it. Otherwise, returns -1.
		int[] arr1 = {1,2,1,2,4,5,4,5,4,5,4,5,4,5,4,5}; //No visible pattern.

		int[] arr2 = {1,1,3,1,1,3,2,1,1,3,1,1,3,2,1,1,3,1}; //Pattern is 1,1,3,1,1,3,2

		int[] arr3 = {1,1,3,1,1,3,2,1,1,3,1,1,3,2,5,1,1,3}; //Has no provable pattern. Notice the 5 that screws it up. 

		int[] arr4 = {1,1,3,1,1,3,1,1,3,1,1,3,1,1,3,1,1,3,4}; //Has no pattern, the 4 ruins it.

		int[] arr5 = {1,4,7,5,6,2,2,2,1,4,7,5,6,2,2,2,1,4,7}; //Pattern is 1,4,7,5,6,2,2,2

		int[] arr6 = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,1,1,1,1,1,1,1,1,1,1,1,1}; //Has no visisble pattern

		int[] arr7 = {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,
			      1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,
			      1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,
			      1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,
			      1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,
			      1,2,2,3}; //pattern is 1,2,2,3,3,3,4,4,4,4,5,5,5,5,5

		int[] arr8 = {8,8,8,1,8,8,8,2,8,8,8,1,8,8,8,2,8,8,3,
			      8,8,8,1,8,8,8,2,8,8,8,1,8,8,8,2,8,8,3,
			      8,8,8,1,8,8,8,2,8,8}; //pattern is 8,8,1,8,8,2,8,8,1,8,8,2,8,8,3

		int[] arr9 = {8,8,8,1,8,8,8,2,8,8,8,1,8,8,8,2,8,8,3,
			      8,8,8,1,8,8,8,2,8,8,8,1,8,8,8,2,8,8,4,
			      8,8,8,1,8,8,8,2,8,8}; //No pattern, the there is a 4 where a 4 should be.


		int[] arr10 = {1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3}; //pattern 1,2,3 -> shows speed.
		ArrayList<int[]> trials = new ArrayList<>();
		trials.add(arr1);
		trials.add(arr2);
		trials.add(arr3);
		trials.add(arr4);
		trials.add(arr5);
		trials.add(arr6);
		trials.add(arr7);
		trials.add(arr8);
		trials.add(arr9);
		trials.add(arr10);
		for(int j = 0; j < trials.size(); j++)
		{
			int[] pat = getPattern(trials.get(j));
			for(int i = 0; i < pat.length; i++) System.out.print(pat[i]+" ");
			System.out.println();
		}
	}
}
