import java.util.Scanner;
class nuclearMerge extends Thread
{
	int[] list;
	int[] newList;

	public nuclearMerge(int[] list) {this.list = list;}

	public void run()
	{
		newList = Threaded_VS_NormalMergeSort.mergeSort(list);
	}
}

public class Threaded_VS_NormalMergeSort
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
	
	//INT VERSION
	public static int[] merge(int[] left, int[] right)
	{
		if(left == null || right == null) System.out.println("THEY ARE NULL!");
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

	public static int[] mergeSort(int[] list)
	{
		if(list.length == 1) return list;
		int[] left = mergeSort(getLeft(list));
		int[] right = mergeSort(getRight(list));
		return merge(left,right);
	}

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		for(int k = 0; k < 15; k++)
		{
			System.out.print("Enter how many elements you want: ");
			int size = input.nextInt();
			int[] list = new int[size];
			int[] list2 = new int[list.length];
			for(int i = 0; i < list.length; i++) 
			{
				list[i] = (int)(Math.random()*14525345) + 1;
				list2[i] = list[i];
			}

			double start = System.nanoTime()/1e6;
			nuclearMerge firstHalf = new nuclearMerge(getLeft(list));
			nuclearMerge secondHalf = new nuclearMerge(getRight(list));

			firstHalf.start();
			secondHalf.start();
			while(firstHalf.isAlive() || secondHalf.isAlive());

			int[] finalList = merge(firstHalf.newList,secondHalf.newList);
			System.out.println("Double Threaded MergeSort took "+(int)(System.nanoTime()/1e6 - start)+" milliseconds, and the list is "+finalList.length+
						" elements long.");

			start = System.nanoTime()/1e6;
			int[] finalList2 = mergeSort(list2);
			System.out.println("Regular MergeSort took "+(int)(System.nanoTime()/1e6 - start)+" milliseconds, and the list is "+finalList2.length+
						" elements long.");
			System.out.println();
		}
	}
}
