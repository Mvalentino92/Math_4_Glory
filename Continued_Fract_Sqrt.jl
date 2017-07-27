#=This function approximates (with accuracy near 1e-12) the square root of N
It so does instantaneously by exploiting the fact that...
Sqrt(X)*Sqrt(N/X) = Sqrt(N)
The function will keep reducing N until it is under the bound of which the square root can be accurately and quickly attained.
The actual square root is achieved by a continued fraction=#

function Froot(N)
	X = 1024	#X is set to 1024(a perfect square), so no accuracy is lost during sqrt(X)*sqrt(N/X)			
	Iter = 625	#Since X is 1024, the highest possible value going through the iterations of the function
	Power = 0	#will in fact be 1024. So, 1024 was tested to achieve accuracy of 1e-12 at around 500 iterations.
	Deci = 0	#Iter was set to 625 for good measure (even if N was originally 1e30, it will still only take
	while(N > X)		#about 6,250 calculations to achieve the answer)
		Power += 1	#Tracking the amount of times N was divided by X. 
		N = N/X
	end
	for i=1:Iter
		Deci = (N-1)/(2+Deci)		#Handling the computation to achieve the square root of N
	end
	if Power == 0				#If N was originally less than or equal to 1024, then give the Root.
		Square_Root = Deci + 1
		return Square_Root
	else						#Otherwise, multiply by sqrt(X) (or 32), the respective number of times
		Square_Root = (Deci + 1)*32^Power
		return Square_Root
	end
		
end
		
