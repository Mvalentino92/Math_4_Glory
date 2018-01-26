public class MergeSort
{
	//The left half of the original array
	public static int[] getLeft(int[] list)
	{
		int newLength = list.length/2;
		int[] leftList = new int[newLength];
		for(int i = 0; i < newLength; i++) leftList[i] = list[i];
		return leftList;
	}

	//The right half of the original array
	public static int[] getRight(int[] list)
	{
		if(list.length % 2 != 0)
		{
			int indexTracker = 0;
			int newLength  = list.length/2 + 1;
			int[] rightList = new int[newLength];
			for(int i = newLength - 1; i < list.length; i++) rightList[indexTracker++] = list[i];
		       	return rightList;
		}
		else
		{
			int indexTracker = 0;
			int newLength = list.length/2;
			int[] rightList = new int[newLength];
			for(int i = newLength; i < list.length; i++) rightList[indexTracker++] = list[i];
			return rightList;
		}
	}	

	/*Compares the elements between the left and right array (both are sorted independently at this point).
	 * The lower value during each comparison, is added to the new array. */
	public static int[] merge(int[] left, int[] right)
	{
		int newLength = left.length + right.length;
		int[] mergedList = new int[newLength];
		
		int leftIndex = 0;
		int rightIndex = 0;
		int mergeIndex = 0;

		while(leftIndex < left.length && rightIndex < right.length)
		{
			if(left[leftIndex] <= right[rightIndex]) mergedList[mergeIndex++] = left[leftIndex++];
			else mergedList[mergeIndex++] = right[rightIndex++];
		}
		
		if(leftIndex == left.length)
		{
			while(rightIndex < right.length) mergedList[mergeIndex++] = right[rightIndex++];
		}
		else
		{
			while(leftIndex < left.length) mergedList[mergeIndex++] = left[leftIndex++];
		}
		
		return mergedList;
	}

	/*The recursive Merge Sort method.
	 * Will first check if the length of the list is 1. If it is, it returns the list. As it cannot be broken down any further.
	 * If not, it will call itself on first the left, then the right side of the list. 
	 * Finally, once both the left and right side are returned (sorted: either because their lengths are 1, or because they are the result of previous call backs,
	 * it will merge and sort those two lists. */
	public static int[] mergeSort(int[] list)
	{
		if(list.length == 1) return list;
		int[] left = mergeSort(getLeft(list));
		int[] right = mergeSort(getRight(list));
		return merge(left,right);
	}

	public static void main(String[] args)
	{
		int[] list = new int[2500000];
		for(int i = 0; i < list.length; i++) list[i] = (int)(Math.random()*4) + 1;
		//int[] list = {3,4,7,6,4,2,1,5,91,7,8};
		int[] sorted = mergeSort(list);

		//for(int i = 0; i < sorted.length; i++) System.out.print(sorted[i]+" ");
		System.out.println("Sorted "+sorted.length+" elements.");
	}
}
