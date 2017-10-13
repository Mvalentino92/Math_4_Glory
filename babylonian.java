import java.util.Scanner;
public class babylonian
{
	public static double babysqrt(double Number)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter an initial guess for the square root of "+Number+": ");
		double guess = -1;
		while(guess < 0)
		{
			while(!(input.hasNextDouble()))
			{
				System.out.print("ERROR: Please supply a positive double: ");
				input.next();
			}
			guess = input.nextDouble();
			if(guess < 0) System.out.print("ERROR: Please supply a POSITIVE double: ");
		}
		guess /= 2;
		double nextguess = guess*2;
		double EPSILON = 1e-8;
		int count = 0;
		while(Math.abs(guess - nextguess) > EPSILON)
		{
			guess = nextguess;
			nextguess = (guess + (Number/guess) ) / 2;
			count += 1;
		}
		System.out.println();
		System.out.println("The square root is: "+nextguess);
		System.out.println();
		System.out.println("It took "+count+" iterations to converge");
		System.out.println();
		double percentError = ((nextguess - Math.sqrt(Number))/Math.sqrt(Number)) * 100;
		System.out.println("The percent error from the true value is "+percentError);
		return nextguess;	
	}

	public static void main(String[] args)
	{
		double result = babysqrt(56749);
	}
}
