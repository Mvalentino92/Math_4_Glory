# Math_4_Glory
Some functions I wrote purely for the practice of writing them.

**BigInt.py** Simulates "pen and paper" math by storing individual digits of a numbers in arrays. Has two functions. One for addition, and one for multiplication.The multiplication function simply calls the addition function over and over while updating the arrays.

**Continued_Fract_sqrt.jl:** Uses continued fractions to return the square root of a number. Perfectly accurate (error of 0 or 1e-15), and instantaneous in computation. Even up to large integers.

**PrimeConfirm.h:** Returns if the given number is a prime number or not. Instant return even for large integers. *(I now realize that I was converging on the square root of the number!)

**areaOfCircle.java:** Calculates the area of the circle, without the use of PI. Instead, uses an estimation based on a number of  triangles approaching infinitely small areas. Accurate to a percent error of 0.04

**babylonian.java:** Uses the babylonian method to approximate a square root. Asks the user for their initial guess.

**combo.java:** Calculates all possible combinations to reach a desired sum using a specified number of summands. It is in anticipation of calculating magic squares. The constraints are: The number of summands must be a perfect square of N, with 1-N, being the available numbers for the summands. All combinations must be unique.
