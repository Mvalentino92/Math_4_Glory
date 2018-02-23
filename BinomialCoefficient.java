/*So one of the dynamic programming examples was finding the binomail coefficient
 * of C(n,k). Gio showed me a formula that literally computes it, and he also showed me pascals triangle.
 * I noticed that by forming pascals triagle, I could get the answer from that as well.
 * Treating the depth as n (starting at 0, like the tip is 0), and treating the columns of each row as k.
 * Again like the first element is 0, just like you would access it in java.
 * So, I generated a ragged matrix, and started with just the initial base case of 1 (the tip).
 * I could have lined all the sides with 1 but I didn't feel like it lmao. I already figured out my conditionals
 * before I realized that hahahaah. Anyway, so yeah. I'm just using the previous rows the generate the next rows.
 * Like you literally would on paper.*/
public class BinomialCoefficient
{
	public static int findCoefficient(int n, int k)
	{
		//Making the ragged array with n + 1 rows
		int[][] pascal = new int[n+1][];
		for(int i = 0; i < pascal.length; i++)
		{
			pascal[i] = new int[i+1];
		}
		
		//Setting the base case (the tip to 1)
		pascal[0][0] = 1;
		for(int i = 1; i < pascal.length; i++)
		{
			for(int j = 0; j < pascal[i].length; j++)
			{
				/*So heres my 3 conditionals.
				 * If i'm all the way to the left, put a 1 (I could have just set it to 1 lol.)
				 * If i'm all the way to the right, put a 1 (again, I accessed the ele instead.)
				 * Otherise, compute this value using the previous ones above it*/
				if(j == 0) pascal[i][j] = pascal[i-1][j];
				else if( j == pascal[i].length - 1) pascal[i][j] = pascal[i-1][j-1];
				else pascal[i][j] = pascal[i-1][j-1] + pascal[i-1][j];
			}
		}
		return pascal[n][k]; //Return the number!
	}

	public static void main(String[] args)
	{
		System.out.println("The binomial coefficient is "+findCoefficient(5,2));
	}
}
