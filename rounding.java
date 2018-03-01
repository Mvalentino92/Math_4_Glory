public class rounding
{
	public static double round(double num, int place)
	{
		double decimal = num % 1;
		double multiplier = 1;
		for(int i = 0; i < place; i++) multiplier *= 10;

		decimal *= multiplier;
		int rounded = decimal % (int)decimal < 0.5 ? (int)decimal : (int)decimal + 1;

		double retval = (int)num + rounded/multiplier;
		return retval;
	}

	public static void main(String[] args)
	{
		System.out.println(round(15.4546435341,8));
		System.out.println(round(7.1398,3));
		System.out.println(round(9.471,1));
	}
}
