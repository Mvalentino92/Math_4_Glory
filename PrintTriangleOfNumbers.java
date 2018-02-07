public class PrintTriangleOfNumbers
{
	public static void main(String[] args)
	{
		int triangle[][] = new int[43][];
		for(int i = 0; i < triangle.length; i++)
		{
			triangle[i] = new int[i+1];
			for(int j = 0; j < triangle[i].length; j++)
			{
				triangle[i][j] = (int)(Math.random()*9 + 1);
			}
		}

		int spaces = triangle.length*2 - 1;
		int tip = spaces/2;

		for(int i = 0; i < triangle.length; i++)
		{
			int count = 0;
			int evenTracker = 0;
			int limit = i;
			int fluff = (spaces - (limit*2 + 1))/2;
			for(int j = 0; j < fluff; j++) System.out.print(" ");
			for(int j = fluff; j < spaces - fluff; j++)
			{
				if(evenTracker % 2 == 0) System.out.print(triangle[i][count++]);
				else System.out.print(" ");
				evenTracker++;
			}
			System.out.println();
		}
	}
}

