/*Consider a game, in which you have two types of powers, A and B and there are 3 types of Areas X, Y and Z. Every second you have to switch between these areas, each area has specific properties by which your power A and power B increase or decrease. We need to keep choosing areas in such a way that our survival time is maximized. Survival time ends when any of the powers, A or B reaches less than 0.*/
public class AreaCombo
{
	public static int survive(int A, int B,int choice)
	{
		if(A <= 0 || B <= 0) return -1;
		else
		{
			switch(choice)
			{
				case 1:
					return 1 + max(survive(A+3,B+2,2),survive(A+3,B+2,3));
				case 2:
					return 1 + max(survive(A-5,B-10,1),survive(A-5,B-10,3));
				case 3:
					return 1 + max(survive(A-20,B+5,1),survive(A-20,B+5,2));
				default: 
					return tripleMax(survive(A,B,1),
							survive(A,B,2),
							survive(A,B,3));
			}
		}
	}
	
	public static int max(int one, int two)
	{
		return one > two ? one : two;
	}

	public static int tripleMax(int one, int two, int three)
	{
		if(one > two && one > three) return one;
		else if(two > one && two > three) return two;
		else return three;
	}

	public static void main(String[] args)
	{
		System.out.println(survive(45,34,0));
	}
}

