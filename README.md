# Math_4_Glory
Some functions I wrote purely for the practice of writing them.

## Traveling_Salesman
I will keep my various attempts at the TSP problem here. This is a great problem to become more familiar with adopting different algorithms and strategies to improve results on the same problem.

**TSP_BranchAndBound.java:** My attempt at implementing a branch and bound algorithm for this problem. Initially, a pure greedy algorithm is used to obtain the current global minimum. Then the branch and bound algorithm is used afterwards. I use a set-up which attempts to stop traversal down non-promising pathways. Non-promising pathways are identified by factors such as a "decision score", and keeping track of the total amount of bad decisions as well. Bad decisions are based off of comparing the current distance traveled to the average distance traveled from the current minimum. If a very bad decision is made with respect to this average, the pathway is immediately disregarded. Only intended for small N. Has been tested to accurate completion for N = 17,26,42 (in under 10 minutes).

**TSP_Circle.java:** The foundation is a greedy algorithm. The easiest TSP problem to solve is one which has it's points in a circle. I try to exploit this, by having the greedy algorithm stick to a circular path.

**TSP_Cluster.java:** Another greedy algorithm approach. This greedy algorithm tries to prioritize two things in addition to the shortest distance. The higher priority is to move towards nearby clusters of points, and the lower priority is to pick off somewhat isolated points while they are still within a close distance.

**TSP_DivideAndConquer.java:** Uses a divide and conquer approach to try and avoid the local minima pitfalls of the greedy approach. The problem is chopped up into subproblems containing 5 cities, and a brute force attempt is used each time to obtain the definite minimum distance for each subproblem. They are added together, and if less than 5 citites remain a simple greedy algorithm is used to clean up the remaining N cities and return to the origin city. 

**TSP_WeightedScale:** My first attempt at the TSP problem. It uses a greedy algorithm approach. I score each city based on two things. The distance it is to that city (smaller is better of course), and also the distances that can no longer be taken, because you can only visit this city once. Larger values are more ideal here. If I try to eliminate the larger values so I cant visit them, by default, I'm also making it more propable I will visit smaller values in the future. I alternate between different set scales. As in, perhaps short distance is half as important as eliminating large values this iteration. Or vice versa.

## Miscellaneous
An assortment of Math oriented functions and codes I've written.

**AreaCombo.java:** Consider a game, in which you have two types of powers, A and B and there are 3 types of Areas X, Y and Z. Every second you have to switch between these areas, each area has specific properties by which your power A and power B increase or decrease. We need to keep choosing areas in such a way that our survival time is maximized. Survival time ends when any of the powers, A or B reaches less than 0.

**BigInt.py** Simulates "pen and paper" math by storing individual digits of a numbers in arrays. Has two functions. One for addition, and one for multiplication.The multiplication function simply calls the addition function over and over while updating the arrays.

**BinomialCoefficient.java:** Takes n and k as input, and uses Pascals Triangle to dynamically calculate the result. 

**CoinPartitions.java:** Generates the number of coin partitions for a number. Uses Dynamic Programming to do so, and uses the BigInteger class as well since the numbers get 30+ digits around a value of 1000.

**ContinuedFractions.java:** Attempts to find the continued fraction representation of rational and irrational numbers. Uses the FindPattern script to verify patterns for the continued fractions of irrational numbers.

**Continued_Fract_sqrt.jl:** Uses continued fractions to return the square root of a number. Perfectly accurate (error of 0 or 1e-15), and instantaneous in computation. Even up to large integers.

**DiceGame.java:** A simple dice game I wrote as an example for a friend.

**Diophantine.hs:** Solves the Diophantine equation a^2 + b^2 + ab = c^2, where c^2 is a square number. I use a method where taking the square root of c^2 isn't necessary. Proving a certain condition true, assures that c^2 must be a square number.

**Explosion.jl:** Simulates an explosion in a connected space with indestructible walls. I construct a matrix of 1's and 0's. The 1's represent indestructible walls, the 0's open space. An origin point of the explosion is picked, and the 0's are changed to ones to simulate the fire spreading through the connected space. This is done through recursion.

**FindPattern.java:** Finds a repeating pattern in a sequence of integers, if one is present. Works off of the constraints that for a pattern to be valid, it must appear completely at least twice in the sequence, followed by termination of the sequence, or an incomplete third repeat. There is a basic forward iterative method, and a recursive method that works backwards. 

**Fractions.java:** I created a Fraction class that lets you represent doubles with Fractions. It supports all the basic operations (addition,subtraction,multiplication,division), and carries them out how you would on pen and paper. Good for if you want to visualize rational numbers in fraction form.

**InsertionSort.java:** Sorts an array of integers using an insertion based approach. Creates and returns a new array in the process. Before confirming it's sorted, I check to make sure the new array is correctly in descending order, and that all elements were retained correctly from the original array.

**Knapsack.java:** Solves the Knapsack problem using recursion. Only feasible for n < 30.

**LexographicOrder.java:** Prints an array of integers (supplied in non-decreasing order), in Lexographic Order. The default example array is: {1,2,3,4,5,6,7,8,9,10}.

**MagicSquares.java:** Prints 864 unique 4X4 magic squares.

**MergeSort.java:** Executes MergeSort. Originally for int's, with an overloaded method for Strings to sort alphabetically. Wrote my own strcmp function to do so.

**PE53_BigIntegerComparison.java:** Uses Project Euler question 53 to analyze the efficiency of the BigInteger Class. The problem demanded calculation of factorials up to 100. A way was found to avoid using BigInteger at first, but I was curious to see how much time I had actually saved by only using ints. The BigInteger method was slower, but not by much! 20 milliseconds compared to 200.

**Partition_Dynamic_Versus_Recursive.java:** Calculates the partitions of numbers using a recursive approach and a dynamic approach. Compares the efficiency between the two for calculating partitions of numbers in order. Starting at 0, and ending at n (defined by user input).

**PerfectNumbers.java:** Generates the first 4 perfect numbers under 10,000. A perfect number is a numbers in which the sum of all it's divisors (omitting the number itself) equals that numbers. 

**PrimeConfirm.h:** Returns if the given number is a prime number or not. Instant return even for large integers. *(I now realize that I was converging on the square root of the number!)

**PrimeFactors.java:** Prints the prime factors of a number in non-increasing order.

**PrintTriangleOfNumbers.java:** Prints a triangle of random numbers between 1-9. The triangle values are stored in a ragged array. Just some practice traversing ragged arrays, and "printing acrobatics".

**QuickBubbleSort.java:** A modified implementation of the Bubble Sort algorithm that sorts in 63% of the time. 

**SecretSort.jl:** Is it Insertion Sort? Is it BubbleSort? Is it kind of an iterative version of MergeSort that only works with arrays who's length are a power of 2? Who knows! All I know is it's pretty slow but it was fun to write! Takes 13.5 minutes to sort 2^20 (1048576), and 53.5 minutes to sort 2^21 (2097152).

**SolveDiophantine.jl:** Yet another Diophantine equation solver. It finds one solution for the linear Diophantine equation ax + by = c, only c is divisible by the GCD of a,b (that's just the restrictions for this having solutions). I use a method that traverses a matrix (that I dont actually construct), and converges towards a special value I'm searching for. Works very quickly for numbers up to about 1 billion.

**Threaded_VS_NormalMergeSort.java:** Compares the vanilla version of Mergesort, with a version that uses two threads. The multithreaded version splits the list of numbers, and hands each half off to a thread that will call mergesort. After they are both done executing, they are merged one final time resulting in the sorted list. Past around 60,000 the multithreaded version is faster, and receives a maximum speed boost of about half (for inputs in the millions).

**UlamSequence.java:** Per Project Euler's description "For two positive integers a and b, the Ulam sequence U(a,b) is defined by U(a,b)1 = a, U(a,b)2 = b and for k > 2, U(a,b)k is the smallest integer greater than U(a,b)(k-1) which can be written in exactly one way as the sum of two distinct previous members of U(a,b)." This script will prompt you for the bound you want to go up until, and what you want to set a and b to (a < b). It will then generate the Ulam Sequence with two seperate algorithms, display the computation time and Ulam Sequence length for each (which will be the same). User is prompted for optional display of the sequence.

**areaOfCircle.java:** Calculates the area of the circle, without the use of PI. Instead, uses an estimation based on a number of  triangles approaching infinitely small areas. Accurate to a percent error of 0.04

**babylonian.java:** Uses the babylonian method to approximate a square root. Asks the user for their initial guess.

**combo.java:** Calculates all possible combinations to reach a desired sum using a specified number of summands. It is in anticipation of calculating magic squares. The constraints are: The number of summands must be a perfect square of N, with 1-N, being the available numbers for the summands. All combinations must be unique.

**rounding.java:** Takes a double as input, and returns a double rounded to the decimal place provided. 

**tQuickBubbleSort.java:** QuickBubbleSort but using 4 threads. The efficiency boost is definitely there. But BubbleSort is still not an efficient algorithm regardless. Essentially makes the best of a bad situation.

**totient.java:** Euler's totient function. One method which prints a symetrical matrix of all of the GCD's computed. One method that simply returns the number of co-primes.
