import java.util.*;
public class Ulam
{
	//Generates an Ulam sequence using the index's of numbers.
	public static ArrayList<Integer> getSequence1(int a, int b, int bound)
	{
		//Create the ArrayList and add the default values
		ArrayList<Integer> ulams = new ArrayList<>();
		ulams.add(a);
		ulams.add(b);

		//Create the array to iterate, and set a and b.
		int[] numbers = new int[bound+1];
		numbers[a] = a;
		numbers[b] = b;

		//Begin to iterate, and use indexing to caluclate sums and check for duplicates.
		for(int i = b + 1; i < numbers.length; i++)
		{
			boolean unique = false;
			int target = i;
			int index = a;

			while(index < target/2 + 1)
			{
				int addTo = target - index;
				boolean noDoubles = addTo != index ? true : false;
				if(numbers[addTo] != 0 && noDoubles && unique)
				{
					unique = false;
					break;
				}
				else if(numbers[addTo] != 0 && noDoubles) unique = true;

				//Grab the next non-zero number to start with.
				index++;
				while(index < target/2 + 1)
				{
					if(numbers[index] == 0) index++;
					else break;
				}
			}
			//If the number was calculated only one time, add it.
			if(unique) 
			{
				numbers[i] = i;
				ulams.add(i);
			}
		}
		return ulams;
	}

	//Generates the Ulam sequence converging on the bounds of viable sums.
	public static ArrayList<Integer> getSequence2(int a, int b, int bound)
	{
		//Add default values
		ArrayList<Integer> ulams = new ArrayList<>();
		ulams.add(a);
		ulams.add(b);

		for(int i = b + 1; i < bound + 1; i++)
		{
			int count = 0;
			int l = 0;
			int r = ulams.size() - 1;
			int target = i;

			while(l < r)
			{
				int left = ulams.get(l);
				int right = ulams.get(r);

				if(left + right < target) l++;
				else if(left + right > target) r--;
				else
				{
					l++;
					r--;
					if(++count > 1) break;
				}
			}
			if(count == 1) ulams.add(i);
		}
		return ulams;
	}

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("What\'s the desired bound?: ");
		int size = input.nextInt();
		System.out.print("And what are a and b? (a < b): ");
		int a = input.nextInt();
		int b = input.nextInt();

		double start = System.nanoTime();
		ArrayList<Integer> seq1 = getSequence1(a,b,size);
		System.out.println("\nFirst algorithm took "+(System.nanoTime() - start)/1e9+" seconds.");
		System.out.println("And generated a list of "+seq1.size()+" numbers.\n");
		
		start = System.nanoTime();
		ArrayList<Integer> seq2 = getSequence2(a,b,size);
		System.out.println("Second algorithm took "+(System.nanoTime() - start)/1e9+" seconds.");
		System.out.println("And generated a list of "+seq2.size()+" numbers.\n");

		if(seq1.size() == seq2.size())
		{
			System.out.print("Would you like to see the sequence [y/n]?: ");
			if(input.next().charAt(0) == 'y')
			{
				for(int i = 0; i < seq1.size(); i++) 
				{
					if(seq1.get(i).equals(seq2.get(i))) System.out.print(seq1.get(i)+" ");
					else System.out.print("["+seq1.get(i)+"/"+seq2.get(i)+"] ");
				}
				System.out.println();
			}
		}
	}
}
