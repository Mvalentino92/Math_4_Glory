import java.util.*; 
import java.math.*; 
import java.io.*; 

public class DiceGame
{
	public static Scanner input = new Scanner(System.in);
	
	/*This is a user input safe function.
	 * Accidentally typing in wrong information will not break the program,
	 * and will simply keep prompting the user to enter correct information until they do so.
	 * Java is expecting an int, so if they type a character the program will crash.
	 * You (yes literally you!), are expecting the person to type in a positive number, that is 
	 * with the bounds of the coins the have. It will check for that as well.*/
	public static int getBet(int coins)
	{
		int bet;
		boolean isInt = false;
		while(true)
		{
			while(true)
			{
				System.out.print("How much would you like to bet?: ");
				isInt = input.hasNextInt();
				if(isInt) break; 
				else input.nextLine();
			}

			bet = input.nextInt();
			input.nextLine();
			if(bet > 0 && bet <= coins) return bet;
			else System.out.println("Please bet a positive wager that you actually have!");
		}
	}

	/*This is another user input safe function. While there isn't any input you type in that would
	 * break the program, (because were asking for a String, and anything on your keyboard can be interpretted as a String),
	 * there is the possibility that they won't enter what you want. Which is either yes or no, or some form of them.
	 * It will run on forever until they enter correct information as well.*/
	public static boolean verdict(String question)
	{
		boolean isValid = false;
		String reply = "";
		char first;
		while(true)
		{
			System.out.print(question);
			reply = input.nextLine();
			first = reply.charAt(0);
			if(first == 'y'|| first == 'Y' ||
			   first == 'n'|| first == 'N') break;
		}
		if(first == 'y' || first == 'Y') return true;
		else return false;
	}

	//Rolls 6 dice for comp
	public static int compRoll()
	{
		int retval = 0;
		for(int i = 0; i < 6; i++) retval += (int)(Math.random()*6) + 1;
		return retval;
	}

	//Rolls initial 3 dice for player
	public static int initRoll()
	{
		int retval = 0;
		for(int i = 0; i < 3; i++) retval += (int)(Math.random()*6) + 1;
		return retval;
	}

	//Rolls one more dice for player
	public static int addDice() {return (int)(Math.random()*6) + 1;}

	public static void main(String[] args)
	{
		int coins = 100;
		int[] multipliers = {5,3,2,1};
		String[] questions = {"Would you like to roll another dice for 3x your bet?: [y/n] ",
				      "Would you like to roll another dice for 2x your bet?: [y/n] ",
				      "Would you like to roll another dice for only 1x your bet?: [y/n] "};
		while(coins > 0)
		{
			//Get initial roll and CPU's final roll.
			int compSum = compRoll();
			int yourSum = initRoll();

			//Print coins and get current bet.
			System.out.println("Current coins: "+coins);
			int bet = getBet(coins); 


			/*Print the current bet multiplier along with your current roll sum.
			 * Prompt for additional rolls with less multiplier*/
			int i;
			int dice = 3;
			for(i = 0; i < questions.length; i++)
			{
				System.out.println("You rolled a "+yourSum+" with "+dice+" dice and have current bet multiplier of "+multipliers[i]+"x");
				boolean v = verdict(questions[i]);
				if(!v) break;
				else 
				{
					dice++;
					yourSum += addDice();
					System.out.println();
				}
			}

			//Print the results, and state the verdict of this round and adjust coins accordingly.
			System.out.println("\nYour sum: "+yourSum+" CPU sum: "+compSum);
			if(yourSum > compSum)
			{
				int winnings = multipliers[i]*bet;
				System.out.println("You won "+winnings+"!");
				coins += winnings;
			}
			else if(compSum > yourSum)
			{
				int losings = multipliers[i]*bet;
				System.out.println("You lost "+losings+"!");
				coins -= losings;
			}
			else System.out.println("It was a tie!");
		}
		System.out.println("You ran out of money, play again!");
	}
}
