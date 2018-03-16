# Math_4_Glory
Some functions I wrote purely for the practice of writing them.

**AreaCombo.java:** Consider a game, in which you have two types of powers, A and B and there are 3 types of Areas X, Y and Z. Every second you have to switch between these areas, each area has specific properties by which your power A and power B increase or decrease. We need to keep choosing areas in such a way that our survival time is maximized. Survival time ends when any of the powers, A or B reaches less than 0.

**BigInt.py** Simulates "pen and paper" math by storing individual digits of a numbers in arrays. Has two functions. One for addition, and one for multiplication.The multiplication function simply calls the addition function over and over while updating the arrays.

**BinomialCoefficient.java:** Takes n and k as input, and uses Pascals Triangle to dynamically calculate the result. 

**CoinPartitions.java:** Generates the number of coin partitions for a number. Uses Dynamic Programming to do so, and uses the BigInteger class as well since the numbers get 30+ digits around a value of 1000.

**Continued_Fract_sqrt.jl:** Uses continued fractions to return the square root of a number. Perfectly accurate (error of 0 or 1e-15), and instantaneous in computation. Even up to large integers.

**Fractions.java:** I created a Fraction class that lets you represent doubles with Fractions. It supports all the basic operations (addition,subtraction,multiplication,division), and carries them out how you would on pen and paper. Good for if you want to visualize rational numbers in fraction form.

**InsertionSort.java:** Sorts an array of integers using an insertion based approach. Creates and returns a new array in the process. Before confirming it's sorted, I check to make sure the new array is correctly in descending order, and that all elements were retained correctly from the original array.

**Knapsack.java:** Solves the Knapsack problem using recursion. Only feasible for n < 30.

**MagicSquares.java:** Prints 864 unique 4X4 magic squares.

**MergeSort.java:** Executes MergeSort. Originally for int's, with an overloaded method for Strings to sort alphabetically. Wrote my own strcmp function to do so.

**PE53_BigIntegerComparison.java:** Uses Project Euler question 53 to analyze the efficiency of the BigInteger Class. The problem demanded calculation of factorials up to 100. A way was found to avoid using BigInteger at first, but I was curious to see how much time I had actually saved by only using ints. The BigInteger method was slower, but not by much! 20 milliseconds compared to 200.

**Partition_Dynamic_Versus_Recursive.java:** Calculates the partitions of numbers using a recursive approach and a dynamic approach. Compares the efficiency between the two for calculating partitions of numbers in order. Starting at 0, and ending at n (defined by user input).

**PerfectNumbers.java:** Generates the first 4 perfect numbers under 10,000. A perfect number is a numbers in which the sum of all it's divisors (omitting the number itself) equals that numbers. 

**PrimeConfirm.h:** Returns if the given number is a prime number or not. Instant return even for large integers. *(I now realize that I was converging on the square root of the number!)

**PrimeFactors.java:** Prints the prime factors of a number in non-increasing order.

**PrintTriangleOfNumbers.java:** Prints a triangle of random numbers between 1-9. The triangle values are stored in a ragged array. Just some practice traversing ragged arrays, and "printing acrobatics".

**QuickBubbleSort.java:** A modified implementation of the Bubble Sort algorithm that sorts in 63% of the time. 

**Threaded_VS_NormalMergeSort.java:** Compares the vanilla version of Mergesort, with a version that uses two threads. The multithreaded version splits the list of numbers, and hands each half off to a thread that will call mergesort. After they are both done executing, they are merged one final time resulting in the sorted list. Past around 60,000 the multithreaded version is faster, and receives a maximum speed boost of about half (for inputs in the millions).

**areaOfCircle.java:** Calculates the area of the circle, without the use of PI. Instead, uses an estimation based on a number of  triangles approaching infinitely small areas. Accurate to a percent error of 0.04

**babylonian.java:** Uses the babylonian method to approximate a square root. Asks the user for their initial guess.

**combo.java:** Calculates all possible combinations to reach a desired sum using a specified number of summands. It is in anticipation of calculating magic squares. The constraints are: The number of summands must be a perfect square of N, with 1-N, being the available numbers for the summands. All combinations must be unique.

**rounding.java:** Takes a double as input, and returns a double rounded to the decimal place provided. 
