/*Solving the knapsack problem using recursion. Very inefficient.
 * It's time complexity is 2^n. I'm checking for an input of 15,
 * and count yields exactly 2^15.
 * Going to try and figure out how to do it dynamically. 
 * Reading a lot about what constitutes a problem that can be solved dynamically.
 * It makes sense, I understood some of the examples. Just going to take a lot of practice to
 * get "the eye", and be able to work through the logic for setting it up dynamically when it's appropiate.*/
public class Knapsack
{
	//Static variables.
	public static int count = 0;
	public static int maxValue = 0;
	public static int maxWeight = 100;

	public static void maxSack(int[][] knapsack, int currentIndex, int currentWeight, int currentValue)
	{
		//If your at the end, stop, check if currentMax is greater than max. Add to count. Return.
		if(currentIndex == knapsack[0].length) 
		{
			maxValue = currentValue > maxValue ? currentValue : maxValue;
			count++;
			return;
		}

		//We are not taking this item regardless of if we can or not.
		maxSack(knapsack,currentIndex + 1, currentWeight, currentValue);

		/*If we can take this item (enough weight left in the knapsack), take it.
		 * If we cant afford to take it, dont*/
		if(currentWeight + knapsack[0][currentIndex] > maxWeight)
		{
			maxSack(knapsack,currentIndex + 1, currentWeight, currentValue);
		}
		else maxSack(knapsack,currentIndex + 1, currentWeight + knapsack[0][currentIndex],
				    currentValue + knapsack[1][currentIndex]);
		
		//Return at the end.
		return;
	}

	public static void main(String[] args)
	{
		int[][] knapsack = new int[2][15];
		for(int i = 0; i < knapsack.length; i++)
		{
			for(int j = 0; j < knapsack[0].length; j++)
			{
				knapsack[i][j] = (int)(Math.random()*25 + 1);
			}
		}
		maxSack(knapsack,0,0,0);
		System.out.println("The max profit available is: "+maxValue);
		System.out.println("It took "+count+" possible combinations to figure this out!");
	}
}
