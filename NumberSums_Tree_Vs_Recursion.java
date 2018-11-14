import java.math.*;
import java.util.*;
public class NumberSums_Tree_Vs_Recursion
{
	//The initial numbers and current index, n, and root!
	Node root;
	public static long count = 0;
	int sum;
	int[] initialNumbers;
	int n;

	//No arg
	NumberSums_Tree_Vs_Recursion(){}

	//All arg
	public NumberSums_Tree_Vs_Recursion(int[] numbers, int n)
	{
		initialNumbers = new int[numbers.length];
		for(int i = 0; i < numbers.length; i++) initialNumbers[i] = numbers[i];
		this.n = n;
		root = new Node(initialNumbers[0],0,null);
		root.makeChildren(1);
	}

	
	//Gets the set of all possible combos	
	public Set<Integer> getCombos()
	{
		//Init the HashSet to return
		Set<Integer> retval = new HashSet<>();

		int i;
		Node currentNode = root;
		while(currentNode != null)
		{
			for(i = currentNode.childNumber; i < currentNode.children.length; i++)
			{
				//Add this to sum
				if(i == 0) sum += currentNode.value;

				//Check if we can add this minus root value
				if(sum - root.value <= n) retval.add(sum - root.value);
				else break;

				//Check to see if we need to recur up or down based on sum
				if(sum > n) break;
				else
				{
					retval.add(sum);
					currentNode.childNumber++;
					currentNode = currentNode.children[i];
					if(currentNode.children == null)
						currentNode.makeChildren(currentNode.indexValue + 1);
					i = -1;
				}
			}
			//If this for loop ended, that means you ran out of children
			if(currentNode.childNumber == 0 && sum <= n) 
			{
				sum += currentNode.value;
				if(sum - root.value <= n) retval.add(sum - root.value);
				if(sum <= n) retval.add(sum);
			}
			if(sum > n)
			{
				sum -= currentNode.value;
				Node temp = currentNode;
				if(currentNode.parent == null) break;
				temp = temp.parent;
				sum -= temp.value;
				if(temp.parent == null) break;
				currentNode = temp.parent;
				temp = null;
			}
			else
			{
				sum -= currentNode.value;
				if(currentNode.parent == null) break;
				Node temp = currentNode;
				currentNode = temp.parent;
				temp = null;
			}
		}
		return retval;
	}

	//Inner class Node
	class Node
	{
		//Value, array of children (to be populated), parent
		int value;
		int indexValue = 0;
		int childNumber = 0;
		Node[] children;
		Node parent;

		//No arg
		Node(){}

		//All arg
		Node(int value,int indexValue,Node parent)
		{
			count++;
			this.value = value;
			this.indexValue = indexValue;
			this.parent = parent;
		}

		//Create the array of children
		void makeChildren(int start)
		{
			//Create children array
			children = new Node[initialNumbers.length - start];

			//Populate with children
			int dex = 0;
			for(int i = start; i < initialNumbers.length; i++)
				children[dex] = new Node(initialNumbers[i],indexValue+(dex++)+1,this);
		}
	}

	//Recursive method to generate sums
	public static void populate(int[] arr,int sum, int index,Set<Integer> set,int n)
	{
		if(sum > n) return;
		set.add(sum);
		if(index == arr.length) return;
		populate(arr,sum + arr[index],index + 1,set,n);
		populate(arr,sum,index + 1,set,n);
	}

	/*Main Method*/
	public static void main(String[] args)
	{
		//List and n
		//int[] vals = {15,27,86,123,201,945};
		int[] vals = {2,18,41,65,112,267};
		int n = 99;

		//Getting the time for using Tree method
		double tStart = System.nanoTime();
		NumberSums_Tree_Vs_Recursion tree = new NumberSums_Tree_Vs_Recursion(vals,n);
		Set<Integer> retval = tree.getCombos();
		retval.remove(0);
		double tTime = (System.nanoTime() - tStart)/1e6;

		//Getting the time for using recursive method
		double rStart = System.nanoTime();
		Set<Integer> ret2 = new HashSet<>();
		populate(vals,0,0,ret2,n);
		ret2.remove(0);
		double rTime = (System.nanoTime() - rStart)/1e6;

		//Converting Tree set to sorted array
		Integer[] ele1 = new Integer[retval.size()];
		ele1 = retval.toArray(ele1);
		Arrays.sort(ele1);

		//Converting Recursive set to sorted array
		Integer[] ele2 = new Integer[ret2.size()];
		ele2 = ret2.toArray(ele2);
		Arrays.sort(ele2);

		//Checking for wrongness!
		if(ele1.length != ele2.length) 
		{
			System.out.println("WRONG!");
			System.exit(1);
		}
		for(int k = 0; k < ele1.length; k++)
		{
			if(ele1[k].compareTo(ele2[k]) != 0) 
			{
				System.out.println("WRONG MATCH");
				System.exit(1);
			}
		}

		//Printing results
		System.out.println("There are "+ele2.length+" unique combos for a list of length "
				   +vals.length+" with bound being "+n);
		System.out.print("The list is: ");
		for(int j = 0; j < vals.length; j++) System.out.print(vals[j]+" ");
		System.out.println("\nTree took: "+tTime+" milliseconds\nRecursive took: "+rTime+" milliseconds");
		System.out.println("Number of nodes created: "+count+"\nAnd number of potential "+
				   "nodes ignoring n: "+Math.pow(2,vals.length-1));
	}
}
