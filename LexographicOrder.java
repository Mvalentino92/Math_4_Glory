import java.util.*; 
import java.math.*; 
import java.io.*; 

public class LexographicOrder
{
	//Copies array minus the element you dont want to transfer
	public static int[] getSubArray(int[] array, int drop)
	{
		int[] sub = new int[array.length - 1];
		int index = 0;
		for(int i = 0; i < array.length; i++)
		{
			if(array[i] == drop) continue;
			else sub[index++] = array[i];
		}
		return sub;
	}

	//Recursive function that prints all lexographic orders. Base case is two elements. 
	public static void lexographic(int[] array,String previous)
	{
		if(array.length == 2)
		{
			System.out.println(previous+array[0]+" "+array[1]);
			System.out.println(previous+array[1]+" "+array[0]);
			return;
		}
		else 
		{
			for(int i = 0; i < array.length; i++)
			lexographic(getSubArray(array,array[i]),(previous+array[i]+" "));
		}
		return;
	}
	
	//Prints all lexographic combinations.
	public static void main(String[] args)
	{
		int[] arr = {1,2,3,4,5,6,7,8,9,10};
		lexographic(arr,"");
	}
}
