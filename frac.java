public class frac
{
	public static void main(String[] args)
	{
		Fraction test = new Fraction(.2,1,18);
		Fraction test2 = new Fraction(.2,1,8);
		Fraction test3 = new Fraction(0.4,14,35);
		test3.printFraction();
		test3.simplify();
		test3.printFraction();
		/*test.printFraction();
		test2.printFraction();
		Fraction sum = test.add(test2);
		sum.printFraction();
		Fraction difference = test.subtract(test2);
		difference.printFraction();*/
	}
}
class Fraction
{
	public static double EPSILON = 1e-4;
	public int N = 1;
	public int D = 1;
	public double value;
	public int wholePart = (int)value;

	//Calulates the numerator and denominator from the value
	public Fraction(double value)
	{
		this.value = value;
		getFraction();
	}

	//Is supplied the numerator and denominator from operations on two fractions.
	public Fraction(double value, int N, int D)
	{
		this.value = value;
		this.N = N;
		this.D = D;
	}

	//Calculate N and D.
	public void getFraction()
	{
		while(true)
		{
			while((double)N/D > value) D++;
			if(Math.abs((double)N/D - value) < EPSILON) break;

			while((double)N/D < value) N++;
			if(Math.abs((double)N/D - value) < EPSILON) break;
		}
		N = wholePart*D + N;
	}

	//Prints the fraction.
	public void printFraction()
	{
		System.out.println(N+"/"+D);
	}

	//Adds the two Fractions together, and returns the sum as a Fraction.
	public Fraction add(Fraction B)
	{
		if(D == B.D)
		{
			int sumN = N + B.N;
			Fraction retval = new Fraction((double)sumN/D,sumN,D);
		       	return retval;
		}
		else
		{
			//Get the multiplying factors and GCD
			int gcd = this.GCD(B);
			int AMult = B.D/gcd;
			int BMult = D/gcd;

			//Get the new numerator
			int newN = AMult*N + BMult*B.N;

			//Get the new denominator
			int newD = AMult*D;

			Fraction retval = new Fraction((double)newN/newD,newN,newD);
			return retval;
		}
	}

	//Subtracts the second from from the first, and returns the difference as a Fraction.
	public Fraction subtract(Fraction B)
	{
		if(D == B.D)
		{
			int sumN = N - B.N;
			Fraction retval = new Fraction((double)sumN/D,sumN,D);
		       	return retval;
		}
		else
		{
			//Get the multiplying factors and GCD
			int gcd = this.GCD(B);
			int AMult = B.D/gcd;
			int BMult = D/gcd;

			//Get the new numerator
			int newN = AMult*N - BMult*B.N;

			//Get the new denominator
			int newD = AMult*D;

			Fraction retval = new Fraction((double)newN/newD,newN,newD);
			return retval;
		}
	}

	public int GCD(Fraction B)
	{
		//If the denominator is the same, there you go!
		if(D == B.D) return D;

		//Find which of the denominators is smaller value.
		int smaller;
		int bigger;
		if(D < B.D)
		{
			smaller = D;
			bigger = B.D;
		}
		else
		{
			smaller = B.D;
			bigger = D;
		}
		
		/*Start checking for divisibility at half of the value.
		 * If you find a value that the smaller number is divisible by,
		 * check it against the larger value.*/
		int half = smaller/2;;
		while(half > 1)
		{
			if(smaller % half == 0)
			{
				if(bigger % half == 0) return half;
			}
			half--;
		}
		return half;
	}

	//Simplifies the fraction if possible.
	public void simplify()
	{
		//If top and bottom are equal it reduces to 1/1.
		if(N == D)
		{
			N = 1;
			D = 1;
			return;
		}

		int smaller;
		int bigger;
		if(value > 1)
		{
			smaller = D;
			bigger = N;
		}
		else
		{
			smaller = N;
			bigger = D;
		}
		
		/*Start checking for divisibility at half of the value.
		 * If you find a value that the smaller number is divisible by,
		 * check it against the larger value.*/
		int half = smaller/2;;
		while(half > 1)
		{
			if(smaller % half == 0)
			{
				if(bigger % half == 0) break;
			}
			half--;
		}
		N = N/half;
		D = D/half;
	}
}
