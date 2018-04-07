import java.util.*; 
import java.math.*; 
import java.io.*; 

public class totient
{
	public static int[][] totient(int number)
	{
		int[][] mat = new int[number][number];

		int value = number - 1;
		int count = 1;
		for(int i = 0; i < mat.length; i++) mat[i][0] = value--;	

		for(int j = 1; j < mat[0].length; j++)
		{
			int high = number;
			int low = j + 1;
			for(int i = 0; i < mat.length; i++)
			{
				int val = high - low;
				if(val < 1) break;
				else if(val == 1)
				{
					count++;
					mat[i][j] = val;
					break;
				}
				else
				{
					mat[i][j] = val;
					if(val > low) high = val;
					else if(val < low)
					{
						high = low;
						low = val;
					}
					else
					{
						high = val;
						low = high;
					}
				}
			}
		}

		System.out.println(count);
		return mat;
	}

	public static int tot(int number)
	{
		if(number == 0) return 0;
		if(number == 1 || number == 2) return 1;
		int count = 1;
		for(int j = 1; j < number/2; j++)
		{
			int high = number;
			int low = j + 1;
			int val = high - low;
			while(val > 1)
			{
				if(val <= 1) continue;
				else
				{
					if(val > low) high = val;
					else if(val < low)
					{
						high = low;
						low = val;
					}
					else
					{
						high = val;
						low = high;
					}
					val = high - low;
				}
			}
			if(val == 1) count++;
		}
		return count*2;
	}

	public static void printMatrix(int[][] mat)
	{
		for(int i = 0; i < mat.length; i++)
		{
			for(int j = 0; j < mat[0].length; j++)
			{
				System.out.printf("%2s ",mat[i][j]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args)
	{
		//int[][] mat = totient(17);
		//printMatrix(mat);
		for(int i = 0 ; i < 3501; i++) System.out.println(i+" "+tot(i));
	}
}
