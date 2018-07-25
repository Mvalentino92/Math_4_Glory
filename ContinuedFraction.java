import java.util.*; 
import java.math.*; 
import java.io.*; 

public class ContinuedFraction
{
	public static int[] getContFrac(double val, double tol,int terms)
	{
		//Get the first term of the continued fraction
		int[] cf = new int[terms];
		int digitCount = 0;
		int coeff = (int)val;
		double rem = val % 1;
		cf[digitCount++] = coeff;
		val = 1.0/rem;

		while(val % 1 > tol && digitCount < terms - 1)
		{
			coeff = (int)val;
			rem = val % 1;
			cf[digitCount++] = coeff;
			val = 1.0/rem;
		}

		cf[digitCount++] = (int)val;
		int[] finalcf = new int[digitCount];
		for(int i = 0; i < finalcf.length; i++) finalcf[i] = cf[i];
		return finalcf;
	}

	//Function to return a subarray of the original up to the specified index
	public static int[] getSub(int[] arr, int index)
	{
		int[] sub = new int[index+1];
		for(int i = 0; i <= index; i++) sub[i] = arr[i];
		return sub;
	}

	//Attempts to find the continued fractions of the sqrt roots of 2 -> 1000
	public static void main(String[] args)
	{
		for(int k = 2; k < 1000; k++)
		{
			int terms = 100;
			int[] pat = getContFrac(Math.sqrt(k),1e-8,terms);
			System.out.print("N: "+k+" ["+pat[0]+";");
			if(pat.length == terms) 
			{
				int[] finalPat = new int[terms - 1];
				for(int i = 0; i < finalPat.length; i++) finalPat[i] = pat[i+1];
				for(int i = 0; i < finalPat.length; i++)
				{
					int[] sub = getSub(finalPat,i);
					int[] temp = FindPattern.iGetPattern(sub);
					if(temp[0] != -1) pat = temp;
				}
				if(pat.length == terms) System.out.println("-1]");
				else 
				{
					for(int i = 0 ; i < pat.length - 1; i++) System.out.print(pat[i]+",");
					System.out.println(pat[pat.length-1]+"]");
				}
			}
			else 
			{
				for(int i = 1 ; i < pat.length - 1; i++) System.out.print(pat[i]+",");
				System.out.println(pat[pat.length-1]+"]");
			}
		}
	}
}
