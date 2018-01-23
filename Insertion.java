public class Insertion
{
	public static int[] insertionSort(int[] list)
	{
		int[] sortedList = new int[list.length];
		sortedList[0] = list[0];
		for(int i = 1; i < list.length; i++)
		{
			int indexToInsert = 0;
			while(sortedList[indexToInsert] < list[i] && indexToInsert < i) indexToInsert++;
			
			int temp = sortedList[indexToInsert];
			sortedList[indexToInsert] = list[i];
			if(indexToInsert + 1 == sortedList.length) break;
			int temp2 = sortedList[indexToInsert + 1];
			sortedList[indexToInsert + 1] = temp;
			for(int j = indexToInsert + 1; j < i; j++)
			{
				temp = temp2;
				temp2 = sortedList[j + 1];
				sortedList[j + 1] = temp;
			}
		}
		return sortedList;
	}

	public static boolean isSorted(int list[])
	{
		for(int i = 0; i < list.length - 1; i++)
		{
			if(!(list[i] <= list[i+1])) return false;
		}
		return true;
	}

	public static boolean retainedAllElements(int[] list, int[] sortedList)
	{
		int i = 0;
		while(i < sortedList.length - 1)
		{
			int sortedCount = 1;
			while(i + 1 < sortedList.length) 
			{
				if(sortedList[i] == sortedList[++i]) sortedCount++;
				else break;
			}
			int count = 0;
			for(int j = 0; j < list.length; j++)
			{
				if(list[j] == sortedList[i-1]) count++;
			}
			if(sortedCount != count) return false;
		}
		return true;
	}

	public static void main(String[] args)
	{
		//Generating random array
		//int[] numbers = new int[1000];
		//for(int i = 0; i < numbers.length; i++) numbers[i] = (int)(Math.random()*45456) + 1;

		//Setting array manually
		int[] numbers = {1,3,3,4,7,5,6,7,5,5,4,2,2};

		//Printing the original array of numbers
		System.out.print("\nPrinting original numbers: ");
		for(int i = 0; i < numbers.length; i++) System.out.print(numbers[i]+" ");
		System.out.println();

		//Creating a new sorted array of the original
		int[] sortedNumbers = insertionSort(numbers);

		//If it's sorted correctly, say so, then print it.
		if(isSorted(sortedNumbers) && retainedAllElements(numbers,sortedNumbers)
				&& sortedNumbers.length == numbers.length)
		{	
			System.out.println("\nIt\'s sorted correctly!");
			System.out.print("Here are the sorted numbers: ");
			for(int i = 0; i < sortedNumbers.length; i++) System.out.print(sortedNumbers[i]+" ");
			System.out.println();
		}
		else System.out.println("\nSorry! The numbers were not sorted correctly.");
	}
}


