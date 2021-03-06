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
	 * will be a potential longest pattern. This pattern must be checked to hold throughout the possible overhang values at the end.
	 * Stores all results of subsequent calls in a global array.*/
	public static void recurChildren(int[] child,int hang)
	{
		int length = child.length;
		for(int i = 2; length/i > hang; i++)
		{
			if(length % i == 0)
			{
				int stride = length/i;
				int[] currentChild = childrenMatch(child,stride);
				if(currentChild.length == stride)
				{
					patterns.add(currentChild);
					recurChildren(currentChild,hang);
					return;
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
		int bound = (int)(length*(2.0/3.0));
		for(int i = length; i > bound; i--)
		{
			int hang = length - i;
			for(int j = 2; i/j > hang; j++)
			{
				if(i % j == 0)
				{
					int stride = i/j;
					int[] currentChild = childrenMatch(arr,stride);
					if(currentChild.length == stride)
					{
						patterns.add(currentChild);
						recurChildren(currentChild,hang);
						return;
					}
				}
			}
		}
	}

	/*Finally, a function that ensures the found pattern actually constitutes a legit pattern. 
	 * If everything is good, return the pattern in an array, otherwise return an array only holding -1.*/
	public static int[] getPattern(int[] arr)
	{
		findChildren(arr);
		int[] dud = {-1};
		int patternsLength = patterns.size();
		int[] potentialPattern;

		if (patternsLength != 0) potentialPattern = patterns.get(patterns.size()-1);
		else return dud;

		int overflow = arr.length % potentialPattern.length;
		int start = arr.length - overflow;
		int index = 0;
		for(int i = start; i < arr.length; i++)
		{
			if(arr[i] != potentialPattern[index++]) return dud;
		}
		return potentialPattern;
	}

	/*An iterative version that works forwards instead of backwards.
	 * The first valid pattern that it finds, is the smallest pattern*/
	public static int[] iGetPattern(int[] arr)
	{
		int[] pattern = {-1};
		int[] dud = {-1};
		for(int i = 1; i <= arr.length/2; i++)
		{
			pattern = childrenMatch(arr,i);
			if(pattern.length == i)
			{
				int overflow = arr.length % pattern.length;
				int start = arr.length - overflow;
				int index = 0;
				for(int j = start; j < arr.length; j++)
				{
					if(arr[j] != pattern[index++]) return dud;
				}
				return pattern;
			}
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
			       1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,4,4,2}; //pattern 1,2,3 -> shows speed.
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
			patterns = new ArrayList<>();
			for(int i = 0; i < pat.length; i++) System.out.print(pat[i]+" ");
			System.out.println();
		}

		int[] arr = new int[200000];
		int[] data = {1,6,7,6,6,6,5,4,5,4,1,1,1,1,7,6,8,8,8,7,6,1,3,5,4,3,3,1,2,4,5,1,
		              4,5,4,2,34,4,6,7,5,3,1,23,3,5,7,8,0,9,8,7,6,6,7,7,6,5,4,1,23,41,
		              1,2,3,4,4,5,3,34,5,6,6,4,1,2,3,3,4,5,6,7,6,5,5,4,3,3,4,5,4,21,
			      1,5,6,55,4,2,1,2,3,3,3,4,3,2,1,2,2,1,4,5,6,5,5,4,2,3,4,3,1,1,3}; //Valid
		int bound = arr.length/data.length;
		int index = 0;
		for(int i = 0; i < bound; i++)
		{
				for(int j = 0; j < data.length; j++) arr[index++] = data[j];
		}
		for(int i = index, j = 0; i < arr.length; i++, j++) arr[i] = data[j];;
		
		double start = System.nanoTime();
		int[] pat = getPattern(arr);
		for(int i = 0; i < pat.length; i++) System.out.print(pat[i]+" ");
		System.out.println();
		double end = System.nanoTime();
		System.out.println("It took: "+((end-start)/1e9)+" seconds.");
	}
}
