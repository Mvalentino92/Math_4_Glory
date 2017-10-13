public class GaussianElimination
{
	//Generates a random matrix for testing. Specify rows, columns, the upper bound of the numbers, and how often you want zero's
	//To be assigned in the matrix. zeroOccurance genrerates a random number up to the number specified, and for each element, if that random number
	//is equal to 1, that element is replaced with a zero. Use 1, for all zeros, and increase accoridingly based on size of matrix. 
	//Set boolean variable to FALSE if you want the rows to be linearly INDEPENDENT (may still be dependent by random chance of number genertion)
	//Setting to TRUE, will genrerate a column that is a linear combination of another two
	//**Must have at least a 3 X 3 to set a linearly DEPENDENT matrix**
	public static double[][] generateMatrix(int rows, int columns, int integerScale, int zeroOccurance,boolean dependent)
	{
		double matrix[][] = new double[rows][columns];
		int sign = 0;
		int chooseSign = 0;
		int zeroChange = 0;
		for(int i = 0; i < rows; ++i)
		{
			for(int j = 0; j < columns; ++j)
			{
				chooseSign = (int)(Math.random()*10);
				if(chooseSign % 2 == 0) sign = -1;
				else sign = 1;
				zeroChange = (int)(Math.random()*zeroOccurance + 1);
				matrix[i][j] = (int)((Math.random()*integerScale)*sign);
				if(zeroChange == 1) {
					matrix[i][j] = 0;
				}
			}
		}
		if(dependent)
		{
			for(int i = 0; i < matrix.length; ++i)
			{
				matrix[i][2] = matrix[i][0]*2 + matrix[i][1]*1;
			}	
		}
		return matrix;
	}


	//Prints the contents of a matrix
	public static void matprint(double[][] matrix)
	{
		for(int i = 0; i < matrix.length; ++i)
		{
			for(int j = 0; j < matrix[0].length; ++j)
			{
				System.out.printf("%6.2f ",matrix[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	//Counts the number of zero rows from the bottom up after the matrix is reduced to echelon form
	public static int countZeroRows(double[][] matrix)
	{
		boolean check = true;
		int zCount = 0;
		int startRow = matrix.length-1;
		int numColumns = matrix[0].length;
		while(check)
		{
			int tracker = 0;
			for(int i = 0; i < numColumns ; ++i)
			{
				if(matrix[startRow][i] == 0) tracker += 1;
				
			}
			if(tracker == numColumns)
			{
				zCount += 1;
				startRow -= 1;
			}
			else check = false;
		}
		return zCount;
	}

	//Switches a row with specified rows below it in a matrix
	public static double[][] switchRows(double[][] matrix, int rowNumber,int jump)
	{
		double[] temp =  new double[matrix[0].length];
		for(int i = 0; i < matrix[0].length; ++i)
		{
			temp[i] = matrix[rowNumber][i];
			matrix[rowNumber][i] = matrix[rowNumber + jump][i];
			matrix[rowNumber + jump][i] = temp[i];
		}
		return matrix;
	}

	/*Returns the matrix in reduced row echelon form*/
	public static double[][] gauss(double[][] matrix)
	{
		int rowNumber = matrix.length;
		int colNumber = matrix[0].length;
		int top = 0; //Repesents the row index of the matrix during descent to row echelon form
		int bottom = 0; //Represent the row index of the matrix during the ascent to reduced row echelon form
		int pivot  = 0; //Represents the column index of the used by the pivot element
		double kill = 0; //kill is the number to multiply the pivot element by so it can "zero out" the y elements
		for(int k = 0; k < colNumber; ++k)
		{
			for(int j = 1; j < (rowNumber - pivot); j++)
			{
				//x is the actual pivot element
				double x = matrix[top][pivot];
				int increment = 1;
				while((x == 0)&&((increment < (rowNumber - top))))
				{
					matrix = switchRows(matrix,top,increment);
					x = matrix[top][pivot];
					increment += 1;
				}
				//y is each element being altered by the pivot element and its row memebers
				double y = matrix[top+j][pivot];
				while(y == 0 && x == 0)
				{
					pivot++;
					if(pivot > colNumber - 1) break;
					x = matrix[top][pivot];
					y = matrix[top+j][pivot];
				}
				if(pivot > colNumber - 1) break;
				else if(y == 0) continue;
				kill = ((-1)*y)/x;
				for(int i = 0; i < matrix[0].length; ++i)
				{
					matrix[top + j][i] = matrix[top][i]*kill + matrix[top + j][i];
					//A tolerance of 1e-10 is used, testing will confirm if it is too low or too high
					if(Math.abs(matrix[top + j][i]) < 1e-10) matrix[top + j][i] = 0;
				}
			}
			pivot++;
			top++;
		}
		//Reduce the bottom row (division by the element);
		bottom = (matrix.length - 1) - countZeroRows(matrix);
		pivot  = (matrix.length - 1) - countZeroRows(matrix);
		int backtrack = pivot;
		double divisor = matrix[bottom][pivot];
		while(divisor == 0)
		{
			pivot += 1;
			divisor = matrix[bottom][pivot];
		}
		for(int i = 0; i < matrix[0].length; ++i)
		{
			matrix[bottom][i] = matrix[bottom][i]/divisor;
		}
		//Begin backtracking to put into reduced row echelon form, once it is in echelon form	
		for(int k = backtrack ; k > 0; k--)
		{
			for(int j = 1; j < bottom + 1; ++j)
			{
				//Safety precaution, never ran into this problem yet. Keep it live for testing purposes
				int increment = 1;
				double x = matrix[bottom][pivot];
				while((x == 0)&&(increment < (rowNumber)))
				{
					//System.out.println("ATTENTION: Rows being altered during backtrack");
					matrix = switchRows(matrix,bottom,increment);
					x = matrix[bottom][pivot];
					increment += 1;
				}
				double y = matrix[bottom - j][pivot];
				if(y == 0 && x == 0)
				{
					pivot--;
					if(pivot < 0) break;
					x = matrix[bottom][pivot];
					y = matrix[bottom - j][pivot];
				}
				else if(y == 0) continue;
				kill = ((-1)*y)/x;
				for(int i = pivot; i < matrix[0].length; i++)
				{
					matrix[bottom - j][i] = matrix[bottom][i]*kill + matrix[bottom - j][i];
					//A tolerance of 1e-10 is used, testing will confirm if it is too low or too high
					if(Math.abs(matrix[bottom - j][i]) < 1e-10) matrix[bottom -j][i] = 0;
				}
			}
			pivot--;
			bottom--;
			//Always get the next pivot point as 1 for next iteration
			if(pivot < 0) break;
			divisor = matrix[bottom][pivot];
			while(divisor == 0)
			{
				//Tracking how often this occurs for testing purposes
				//System.out.println("ATTENTION: Shifting to left to obtain non zero's");
				pivot--;
				if(pivot < 0) break;
				divisor = matrix[bottom][pivot];
			}
			//If the pivot element has been exhausted all the way to the left during backtrack, stop.
			if(pivot < 0) break;
			for(int i = 0; i < matrix[0].length; ++i)
			{
				matrix[bottom][i] = matrix[bottom][i]/divisor;
			}
		}
		return matrix;
	}

	public static void main(String[] args)
	{
		/*-------Generating random matrices, and testing specific situations----------*/
		
		System.out.println("Random dependent matrix most likely to be discontinous");
		double random1[][] = generateMatrix(3,4,10,10,true);
		random1 = gauss(random1);
		matprint(random1);
		System.out.println("-------------------------------------------------");	

		System.out.println("Random matrix that will have a unique solution");
		double random2[][] = generateMatrix(5,6,35,5,false);
		random2 = gauss(random2);
		matprint(random2);
		System.out.println("-------------------------------------------------");	
		
		System.out.println("Last column is a linear combination of the first two");
		double specific1[][] = {{1,-2,-1},{2,4,6},{3,0,3}};
		matprint(specific1);
		specific1 = gauss(specific1);
		matprint(specific1);
		System.out.println("-------------------------------------------------");	

		System.out.println("3 X 4 with solution showing ability to manipulate zeros");
		double specific2[][] = {{0,4,-2,4},{0,-4,5,-3},{2,1,0,1}};
		matprint(specific2);
		specific2 = gauss(specific2);
		matprint(specific2);
		System.out.println("-------------------------------------------------");	

		System.out.println("Random matrix showing ability to deal with an overrage of vector columns");
		double random3[][] = generateMatrix(4,11,15,5,false);
		random3 = gauss(random3);
		matprint(random3);
		System.out.println("-------------------------------------------------");	

		System.out.println("Showing how to get the inverse of a 2 X 2");
		double specific3[][] = {{1,2,1,0},{3,4,0,1}};
		matprint(specific3);
		specific3 = gauss(specific3);
		matprint(specific3);
		System.out.println("-------------------------------------------------");	

		System.out.println("Random matrix showing ability to deal with an ovverage of rows");
		double random4[][] = generateMatrix(8,4,15,7,false);
		random4 = gauss(random4);
		matprint(random4);
		System.out.println("-------------------------------------------------");	

		System.out.println("Showing ability to deal with condensed areas of zeros");
		double specific6[][] = {{1,4,5,6,7,8,9,1},{0,0,0,6,4,2,1,2},{0,0,0,8,8,1,2,3},{4,5,2,0,0,7,4,4},{5,6,1,0,0,4,5,5},{0,7,2,0,0,8,8,6},{0,8,3,5,5,0,0,7}};
		matprint(specific6);
		specific6 = gauss(specific6);
		matprint(specific6);
		System.out.println("-------------------------------------------------");	

		System.out.println("Testing a matrix with multiple linear combination rows");	
		double specific7[][] = {{1,3,4,7,5},{2,5,7,12,9},{1,2,3,5,4},{2,3,5,8,7}};
		matprint(specific7);
		specific7 = gauss(specific7);
		matprint(specific7);
		System.out.println("-------------------------------------------------");

		System.out.println("Finally, a scalar multiple indentity matrix should give the indentity matrix!");
		double specific5[][] = {{13,0,0},{0,13,0},{0,0,13}};
		matprint(specific5);
		specific5 = gauss(specific5);
		matprint(specific5);

		/*Showing the ability to use inverses to solve systems*/

		System.out.println("5 X 5 matrix that will have a unique solution");
		double A1[][] = generateMatrix(5,5,15,25,false);
		double b1[][] = generateMatrix(5,1,15,25,false);
		double x1[][] = linsolve(A1,b1);
		System.out.println("The solution is ");
		matprint(x1);
		double check1[][] = matmult(A1,x1);
		System.out.println("The original b is ");
		matprint(b1);
		System.out.println("A*x returns ");
		matprint(check1);
		System.out.println("------------------------------------------------------------------------------------------");

		System.out.println("25 X 25 matrix that will have a unique solution");
		double A3[][] = generateMatrix(25,25,15,25,false);
		double b3[][] = generateMatrix(25,1,15,25,false);
		double x3[][] = linsolve(A3,b3);
		System.out.println("The solution is ");
		matprint(x3);
		double check3[][] = matmult(A3,x3);
		System.out.println("The original b is ");
		matprint(b3);
		System.out.println("A*x returns ");
		matprint(check3);
		System.out.println("------------------------------------------------------------------------------------------");

		System.out.println("5 X 5 matrix that will NOT  have a unique solution");
		double A2[][] = generateMatrix(5,5,15,25,true);
		double b2[][] = generateMatrix(5,1,15,25,false);
		double x2[][] = linsolve(A2,b2);
		System.out.println("The solution is ");
		matprint(x2);
		double check2[][] = matmult(A2,x2);
		System.out.println("The original b is ");
		matprint(b2);
		System.out.println("A*x returns ");
		matprint(check2);
		System.out.println("------------------------------------------------------------------------------------------");

	}

	//Returns the inverse of a matrix
	//First it appends an equal dimension identity matrix on the end of the supplied matrix
	//Then it puts the new matrix into reduced row echelon form using Gaussian Elimination
	//If it was invertible, then the new reduced matrix should contain the indentity matrix on the right
	//and the inverse of the original matrix on the left. So the inverser is extracted.
	public static double[][] Inverse(double[][] matrix)
	{
		int rowNumber = matrix.length;
		int colNumber = matrix[0].length;
		double newMatrix[][] = new double[rowNumber][colNumber*2];
		double identity[][] = new double[rowNumber][colNumber];
		double inverse[][] = new double[rowNumber][colNumber];
		for(int i = 0; i < rowNumber; ++i)
		{
			for(int j = colNumber,k = 0; j < colNumber*2; ++j, ++k)
			{
				if((j-colNumber) == i)
				{
				       	newMatrix[i][j] = 1;
					identity[i][j-colNumber] = 1;
				}
				newMatrix[i][k] = matrix[i][k];
			}
		}
		newMatrix = gauss(newMatrix);
		boolean verdict = true;
		for(int i = 0; i < rowNumber; ++i)
		{
			for(int j = 0; j < colNumber; ++j)
			{
				if(newMatrix[i][j] != identity[i][j])
				{	
					verdict = false; 
					break;
				}
			}
			if(verdict == false) break;
		}
		if(verdict) 
		{
			for(int i = 0; i < rowNumber; ++i)
			{
				for(int j = 0; j < colNumber; ++j)
				{
					inverse[i][j] = newMatrix[i][j+colNumber];
				}
			}
			return inverse;
		}
		else
		{
			System.out.println("Matrix not invertible");
			double invalid[][] = {{Math.PI}};
			return invalid;
		}
	}

	//Multiplies two matrices
	public static double[][] matmult(double[][] matone, double[][] mattwo)
	{
		int matoneRows = matone.length;
		int matoneColumns = matone[0].length;
		int mattwoRows = mattwo.length;
		int mattwoColumns = mattwo[0].length;
		if(matoneColumns == mattwoRows)
		{
			int update = 0;
			double matrix[][] = new double[matoneRows][mattwoColumns];
			for(int k = 0; k < mattwoColumns; ++k)
			{
				for(int i = 0; i < matoneRows; ++i)
				{
					double total = 0;
					for(int j = 0; j < mattwoRows; ++j)
					{
						total += matone[i][j]*mattwo[j][update];
					}
					matrix[i][update] = total;
				}
				update += 1;
			}
			return matrix;
		}
		else 
		{
			System.out.println("Matrix multiplication not possible"); 
			double invalid[][] = {{Math.PI}}; 
			return invalid;
		}
	}

	//Method that returns the coefficient vector "x" of a system of equations with a unique solution
	public static double[][] linsolve(double[][] A, double[][] b)
	{
		A = Inverse(A);
		//If either the inverse method or multiplication method arent successful, PI is returned as a tracker
		if(A[0][0] == Math.PI) 
		{
			System.out.println("System is not unique");
		       	System.exit(1); 
		}
		double solution[][] = matmult(A,b);
		if(solution[0][0] == Math.PI)
		{
			System.out.println("System is not unique");
			System.exit(1);
		}
		return solution;
	}
}
					









