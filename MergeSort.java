public class MergeSort
{
//***************Get the left half of the array**************************//

	//INT VERSION
	public static int[] getLeft(int[] list)
	{
		int newLength = list.length/2;
		int[] leftList = new int[newLength];
		for(int i = 0; i < newLength; i++) leftList[i] = list[i];
		return leftList;
	}

	//STRING VERSION
	public static String[] getLeft(String[] list)
	{
		int newLength = list.length/2;
		String[] leftList = new String[newLength];
		for(int i = 0; i < newLength; i++) leftList[i] = list[i];
		return leftList;
	}

//**************Get the right half of the array**************************//

	//INT VERSION
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
	
	//STRING VERSION
	public static String[] getRight(String[] list)
	{
		if(list.length % 2 != 0)
		{
			int indexTracker = 0;
			int newLength  = list.length/2 + 1;
			String[] rightList = new String[newLength];
			for(int i = newLength - 1; i < list.length; i++) rightList[indexTracker++] = list[i];
		       	return rightList;
		}
		else
		{
			int indexTracker = 0;
			int newLength = list.length/2;
			String[] rightList = new String[newLength];
			for(int i = newLength; i < list.length; i++) rightList[indexTracker++] = list[i];
			return rightList;
		}
	}	

	//Compares strings
	public static int strcmp(String A, String B)
	{
		String smallerString;
		String biggerString;
		int signFlip = 1;
		if(A.length() < B.length() || A.length() == B.length())
		{
			smallerString = A;
			biggerString = B;
		}
		else 
		{
			smallerString = B;
			biggerString = A;
			signFlip *= -1;
		}

		int index = 0;
		while(index < smallerString.length())
		{
			if(smallerString.charAt(index) < biggerString.charAt(index)) return -1*signFlip;
			else if(smallerString.charAt(index) > biggerString.charAt(index)) return 1*signFlip;
			else index++;
		}
		if(smallerString.length() == biggerString.length()) return 0;
		else return -1*signFlip;
	}

//***********************************The merge method****************************************************************
	      //Compares the elements between the left and right array (both are sorted independently at this point).
	      //The lower value during each comparison, is added to the new array.

	//INT VERSION
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

	//STRING VERSION
	public static String[] merge(String[] left, String[] right)
	{
		int newLength = left.length + right.length;
		String[] mergedList = new String[newLength];
		
		int leftIndex = 0;
		int rightIndex = 0;
		int mergeIndex = 0;

		while(leftIndex < left.length && rightIndex < right.length)
		{
			int verdict = strcmp(left[leftIndex],right[rightIndex]);
			if(verdict == -1 || verdict == 0) mergedList[mergeIndex++] = left[leftIndex++];
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

//*****************************************The recursive Merge Sort method.***************************************************************************
	 /*Will first check if the length of the list is 1. If it is, it returns the list. As it cannot be broken down any further.
	 * If not, it will call itself on first the left, then the right side of the list. 
	 * Finally, once both the left and right side are returned (sorted: either because their lengths are 1, or because they are the result of previous call backs,
	 * it will merge and sort those two lists. */

	//INT VERSION
	public static int[] mergeSort(int[] list)
	{
		if(list.length == 1) return list;
		int[] left = mergeSort(getLeft(list));
		int[] right = mergeSort(getRight(list));
		return merge(left,right);
	}

	//STRING VERSION
	public static String[] mergeSort(String[] list)
	{
		if(list.length == 1) return list;
		String[] left = mergeSort(getLeft(list));
		String[] right = mergeSort(getRight(list));
		return merge(left,right);
	}
//******************************************************************************************************************************************************
	public static void main(String[] args)
	{
		int[] list = new int[2500000];
		for(int i = 0; i < list.length; i++) list[i] = (int)(Math.random()*4) + 1;
		//int[] list = {3,4,7,6,4,2,1,5,91,7,8};
		int[] sorted = mergeSort(list);

		//for(int i = 0; i < sorted.length; i++) System.out.print(sorted[i]+" ");
		System.out.println("Sorted "+sorted.length+" elements.");
		String[] names = {"mike","john","albert","matthew","al","michael","matt","steven","anthony","mitch","albert","alexa"};
		String[] newNames = new String[2500000];
		int count = 0;
		while(count < newNames.length)
		{
			for(int i = 0; i < names.length; i++)
			{
				if(!(count < newNames.length)) break;
				newNames[count++] = names[i];
			}
		}
				
		String[] sortedNames  = mergeSort(newNames);
		System.out.println("Sorted "+sortedNames.length+" elements.");
		/*for(int i = 0; i < sortedNames.length; i++)
		{
			System.out.println(sortedNames[i]);
		}*/
	}
}
