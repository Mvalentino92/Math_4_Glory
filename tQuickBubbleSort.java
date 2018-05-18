public class tQuickBubbleSort extends Thread
{

	//The bubble sort algorithm
	public static void bubbleSort(int[] numbers)
	{
		int index = 0;
		while(true)
		{
			boolean noneMoved = true;
			for(int j = 0; j < numbers.length - index - 1; j++)
			{
				if(numbers[j] > numbers[j+1])
				{
					noneMoved = false;
					int temp = numbers[j+1];
					numbers[j+1] = numbers[j];
					numbers[j] = temp;
				}
			}
			if(noneMoved) 
			{
				System.out.println("The array had "+numbers.length+" elements but it only took "+index+" calls to sort");
				break;
			}
			else index++;
		}
	}

	int[] numbers;
	public tQuickBubbleSort(int[] numbers)
	{
		this.numbers = numbers;
	}

	//A quicker versino of bubble sort that sorts in 68% of the time bubble sort does
	public void run()
	{
		//for(int k = 0; k < numbers.length/3 + 1; k++)
		int k = 0;
		while(true)
		{
			boolean noneMovedForward = true;
			boolean noneMovedBackard = true;	
			for(int i = 0 + k, j = numbers.length - 1 - k; i < numbers.length - k - 1; i++, j--)
			{
				if(numbers[i] > numbers[i+1])
				{
					noneMovedForward = false;
					int tempI = numbers[i+1];
					numbers[i+1] = numbers[i];
					numbers[i] = tempI;
				}
				if(numbers[j] < numbers[j-1])
				{
					noneMovedBackard = false;
					int tempJ = numbers[j-1];
					numbers[j-1] = numbers[j];
					numbers[j] = tempJ;
				}
			}
			if(noneMovedForward && noneMovedBackard)
			{
				System.out.println("The array had "+numbers.length+" elements but it only took "+k+" calls to sort");
				break;
			}
			else k++;
		}
	}

	//Checks if sorted
	public static boolean isSorted(int[] numbers)
	{
		for(int i = 0; i < numbers.length - 1; i++)
		{
			if(!(numbers[i] <= numbers[i+1])) return false;
		}
		return true;
	}

	public static void main(String[] args)
	{
		int[] numbers = new int[135000];
		for(int i = 0; i < numbers.length; i++)
		{
			numbers[i] = (int)(Math.random()*853434+ 1);
			//System.out.print(numbers[i]+" ");
		}
		System.out.println("\n");
		//bubbleSort(numbers);
		//int[] numbers = {4,7,1,5,3};

		tQuickBubbleSort sortOne = new tQuickBubbleSort(numbers);
		tQuickBubbleSort sortTwo = new tQuickBubbleSort(numbers);
		tQuickBubbleSort sortThree = new tQuickBubbleSort(numbers);
		tQuickBubbleSort sortFour = new tQuickBubbleSort(numbers);
		
		double start = System.nanoTime()/1e9;
		sortOne.start();
		sortTwo.start();
		sortThree.start();
		sortFour.start();

		while(sortOne.isAlive() || sortTwo.isAlive() || sortThree.isAlive() || sortFour.isAlive());
		if(isSorted(numbers)) System.out.println("Took "+(System.nanoTime()/1e9 - start)+" seconds to sort.");

		/*quickBubbleSort(numbers);
		if(isSorted(numbers))
		{	
			System.out.println("Sorted Correctly");
			//for(int i = 0; i < numbers.length; i++) System.out.print(numbers[i]+" ");
			//System.out.println();
		}
		else System.out.println("NOT sorted correcrly");*/
	}
}
