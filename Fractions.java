public class Fractions
{
	public static void main(String[] args)
	{
		Fraction test = new Fraction(-0.957);
		Fraction test2 = new Fraction(-0.82352941176);
		System.out.print("The actual value is: "+test.value+" and the fraction is: ");
		test.printFraction();
		System.out.print("The actual value is: "+test2.value+" and the fraction is: ");
		test2.printFraction();
		System.out.print("Adding the fractions yields: ");
		(test.add(test2)).printFraction();
		System.out.print("Subtracting the fractions yields: ");
		(test.subtract(test2)).printFraction();
		System.out.print("Multiplying the fractions yields: ");
		(test.multiply(test2)).printFraction();
		System.out.print("Dividing the fractions yields: ");
		(test.divide(test2)).printFraction();
		(new Fraction(-5415.0053210)).printFraction();
		Fraction test4 = new Fraction(-1,56,-56);
		test4.simplify();
		test4.printFraction();
	}
}
class Fraction implements Comparable<Fraction>
{
	public static double EPSILON = 1e-4;
	public int N = 1;
	public int D = 1;
	public double value;
	public int wholePart;
	public double decimalPart;
	boolean negative;

	//Calulates the numerator and denominator from the value
	public Fraction(double value)
	{
		this.value = value;
		negative = value < 0 ? true : false;
		wholePart = (int)value;
		decimalPart = value - wholePart;
		getFraction(negative);
	}

	//Is supplied the numerator and denominator from operations on two fractions.
	public Fraction(double value, int N, int D)
	{
		this.value = value;
		this.N = N;
		this.D = D;
		wholePart = (int)value;
		decimalPart = value - wholePart;
		negative = ((N < 0 || D < 0) && !(N < 0 && D < 0)) ? true : false;
	}

	//Calculate N and D.
	public void getFraction(boolean negative)
	{
		double tempDecimalPart = negative ? -1*decimalPart : decimalPart;
		while(true)
		{
			while((double)N/D > tempDecimalPart) D++;
			if(Math.abs((double)N/D - tempDecimalPart) < EPSILON) break;

			while((double)N/D < tempDecimalPart) N++;
			if(Math.abs((double)N/D - tempDecimalPart) < EPSILON) break;
		}
		N = negative ? wholePart*D + -1*N : wholePart*D + N;
	}

	//Prints the fraction.
	public void printFraction()
	{
		System.out.println(N+"/"+D);
	}

	@Override
	public int compareTo(Fraction B)
	{
		if(this.value == B.value) return 0;
		else return this.value > B.value ? 1 : -1;
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

	public Fraction multiply(Fraction B)
	{
		int newN = N*B.N;
		int newD = D*B.D;
		Fraction retval = new Fraction((double)newN/newD,newN,newD);
		retval.simplify();
		return retval;
	}

	public Fraction divide(Fraction B)
	{
		int newN = N*B.D;
		int newD = D*B.N;
		Fraction retval = new Fraction((double)newN/newD,newN,newD);
		retval.simplify();
		return retval;
	}

	public int GCD(Fraction B)
	{
		//If the denominator is the same, there you go!
		if(D == B.D) return D;

		//Find which of the denominators is smaller value.
		int smaller;
		int bigger;
		if(Math.abs(D) < Math.abs(B.D))
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
		int half = Math.abs(smaller)/2;
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
		if(Math.abs(N) == Math.abs(D))
		{
			N = negative ? -1 : 1;
			D = 1;
			return;
		}

		int smaller;
		int bigger;
		if(Math.abs(value) > 1)
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
		int half = Math.abs(smaller)/2;;
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
		if((D < 0 && N < 0) || (D < 0 && N > 0))
		{
			N *= -1;
			D *= -1;
		}
	}
}
