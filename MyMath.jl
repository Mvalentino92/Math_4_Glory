
#***Function to Compute the Arc Lenght, Decent Precision.***

import SymPy.diff

function ArcLen(f,x0,xend)	#Finds the arc length of funtion between two points.	
	total = 0
	iter = 1e-4		#Spacing Steps
	x1 = x0
	x2 = x0 + iter		#Setting x1,x2,y1,y2 for equation 
	y1 = f(x1)		
	y2 = f(x2)
	while x2 <= xend
		total += sqrt((x2-x1)^2 + (y2-y1)^2)	#Performing the operation in a loop
		x1 = x2
		x2 = x1 + iter		#Adjusting/iterating the values of x's and y's
		y1 = f(x1)
		y2 = f(x2)
	end
	return total		#Return the value
end

#***End of ArcLength Function***


#***Romberg integration***

function RomInt(f,x0,xend)			#Parameters are the function and two points (Uses a function, not data points!!)
	dt = 2e-4
	total = zeros(4)			#Setting an array of zeros to hold the four iterations of intergration
	for count = 1:4
		dt /= 2				#Cutting down my spacing by factors of two for each iteration
		yhold = collect(x0:dt:xend)
		y = map(f,yhold)		#Getting y values
		total[count] += (y[1]*dt/2 + y[end]*dt/2)	#Getting each newly improved appoximation of the integral
		for i in y[2:end-1]
			total[count] += i*dt 			#Getting the first and last values added to the approximations
		end
	end
	n = 4					#Setting n up to compute the weighted averages
	t1 = (n*total[2] - total[1])/(n-1)
	t2 = (n*total[3] - total[2])/(n-1)	#Computing the weighted averages to get a more precise approximation
	t3 = (n*total[4] - total[3])/(n-1)
	n *= 4					#Incrementing n by neccessary factors of 4
	t1 = (n*t2 - t1)/(n-1)
	t2 = (n*t3 - t2)/(n-1)
	n *= 4
	final = (n*t2 - t1)/(n-1)
	return final				#Returning the final improved integral
end

#***End of Romberg function***

#***Start Trapz****

function TrapZ(x,y)				#Gives area under the curve (integral), using Data Points!!
	dt = diff(x[1:2])[1]			#Finding what the spacing is...This is for even spacing!!
	total = (y[1]*dt/2 + y[end]*dt/2)	
	for i in y[2:end-1]
		total += i*dt			#Iterating through the values and adding them
	end
	return total				#Returning the total
end

#***End TrapZ***

#***Start UnevenTrapZ***			Gives the area under the curve using Data Points!! (For UNEQUAL SPACING)
function UnevenTrapZ(x,y)							#***Interpolating then using this function advised***
	total = (y[1]*diff(x[1:2])[1]/2 + y[end]*diff(x[end-1:end])[1]/2)	#Getting the end values using different spacing
	for i=2:length(y[1:end-1])						#Setting the other points to iterate through
		dt = (diff(x[i:i+1])[1] + diff(x[i-1:i])[1])/2		#Finding the mean of the spacing between the left...
		total += y[i]*dt					#...and the right sides for this interval
	end
	return total							#Returning the total

end
		

#***Start of CumTrapz Function	

function CumTrapZ(x,y)				#This a function to estimate the numerical integral of points from a Polynomial
	y_end = TrapZ(x,y)[1]			#First I'm finding the area under the curve of the data points
	dt = diff(x[1:2])[1]			#For Polynomials, this number will be the same as the integral evaluated at the...
	Integral = zeros(length(x))		#...last point
	Integral[1] = y_end			#Setting the first (Last) point in the Integral to this value
	y  = reverse(y)				#I'm reversing the order of y, so I can operate in order
	for i=1:length(x)-1
		Integral[i+1] = Integral[i] - (y[i]*dt + y[i+1]*dt)/2		#I'm calulating the integral of the next point
	end									#Working backwards, using the previous point 
	Integral = reverse(Integral)						#And knowledge of the spacing
	return Integral							#Reversing the vector, and returning the answer.
end

#***Start Lagrangian Interpolation***
	
function Lagrange(x,y)			#Function to interpolate more data points between existing data points
	Left = 1			#I'm using 3 points per iteration to get values between those three points
	Right = 3			#Setting variables for left and right indexing
	InterpolationTicks = []
	InterpolationValues = []
	while Right != (length(x) - 1)		#while loop to iterate through conditions to satisfy my desired interval spacing
		NEWX = x[Left:Right]		#Setting the boundaries for each interval for the x and y values
		NEWY = y[Left:Right]
		InitialSpacing = sum(diff(NEWX))/2	#Finding the mean of the spacing between the points of my interval
		NewSpacing = InitialSpacing/10		#Getting a new reduced spacing
		NEWX = convert(Array{Float64,1},NEWX)
		LagInterval = NEWX[1]:NewSpacing:NEWX[end]	#Settng up the new intervel with spacing
		HoldInterval = zeros(length(LagInterval))	#Array of zeros to hold the values
		for j=1:length(LagInterval)
			X = LagInterval[j]
			Lag_Sum = 0
			for i=1:length(NEWX)			#Iterating through the generate values corresponding to this interval
				Numerator = 1
				Denominator = 1
				for k=1:length(NEWX)
					if i != k
						Numerator *= (X-NEWX[k])
						Denominator *= (NEWX[i]-NEWX[k])
					end
				end
				Lag_Sum += (Numerator*NEWY[i])/Denominator	#Getting the new vales	
			end
			if (LagInterval[j] in InterpolationTicks) != true	#Preventing identical values from being appended
				push!(InterpolationTicks,LagInterval[j])
				push!(InterpolationValues,Lag_Sum)
			end
	       	end
		if Right == length(x)	#If your finished with the last interval, stop.
			break
		end
		Left = Right		#Updating our left and right index variables for the next interval
		Right += 2
		
	end
	if length(x) != Right		#If we have an even number of points, the last interval with have 4 points instead of 3
		Left = Left 
		Right += 1		#Just iterating through the equation again for this case
		NEWX = x[Left:Right]
		NEWY = y[Left:Right]
		InitialSpacing = sum(diff(NEWX))/3
		NewSpacing = InitialSpacing/10
		NEWX = convert(Array{Float64,1},NEWX)
		LagInterval = NEWX[1]:NewSpacing:NEWX[end]
		HoldInterval = zeros(length(LagInterval))
		for j=1:length(LagInterval)
			X = LagInterval[j]
			Lag_Sum = 0
			for i=1:length(NEWX)
				Numerator = 1
				Denominator = 1
				for k=1:length(NEWX)
					if i != k
						Numerator *= (X-NEWX[k])
						Denominator *= (NEWX[i]-NEWX[k])
					end
				end
				Lag_Sum += (Numerator*NEWY[i])/Denominator
			end
			if (LagInterval[j] in InterpolationTicks) != true
				push!(InterpolationTicks,LagInterval[j])
				push!(InterpolationValues,Lag_Sum)
			end
	       	end
	end			
	return InterpolationTicks,InterpolationValues	#Returning the new vectors with N*10 - 9 points of original
end
		
#***End Lagrangian Interpolation***


#***Start Taylor Series Polynomial Expansion***

function TaylorPoly(Func,point_of_expansion,order)	#Gives the taylor series appoximation in the form of a vector of coefficients
	Polynomial_Vector = [Func(point_of_expansion)]	#Getting first coefficient for zero factorial (Will be same value)
	for i = 1:order
		Func  = diff(Func) 			#Iterating through subsequent derivatives
		Func_Value = Func(point_of_expansion)	
		Coefficient = Func_Value/factorial(i)	#Getting the respective coefficients for each value/iteration
		push!(Polynomial_Vector,Coefficient)	#Appending it to the vector
	end
	#Polynomial = Poly(Polynomial_Vector)
	Polynomial = Polynomial_Vector			#Returning the coeffcient vector
	return Polynomial
end

#***End Taylor Series Polynomial Expansion***

#***Start Taylor Series Evaluation***

function TaylorEval(Coeff_Vector,Value,point_of_expansion)	#Evaluating the Taylor series appoximation for point(s)
	Value = Value - point_of_expansion			#Subtracting the point of expansion from value(s) to get actual value.
	Answer = PolyVecEval(Coeff_Vector,Value)
	return Answer						#Calling POLYVECEVAL to generate the answers
end

#***Start PolyVecEval***

function PolyVecEval(Coeff_Vector,Value)			#Evaluates a vector as a polynomial
	Total = zeros(length(Value))				
	for j=1:length(Value)					#Iterating through how many values are to be evaluated
		Total[j] = Coeff_Vector[1]			#The first one is just equal to the coefficient (no x)
		for i=2:length(Coeff_Vector)				#Iterating through the ascending orders
			Total[j] += Coeff_Vector[i]*Value[j]^(i-1)	#Performing the calculations EXAMPLE: 1.2*(x)^3
		end
	end
	return Total			#Returning the vector of the evaluated polynomial vector
		
end
	
#***End PolyVecEval***

#***Start PolyForm***

function PolyForm(Value,Order)		#For Splines. Getting a polynomial with unknown coefficients evaulated at x=Value
	Vector = zeros(Order+1)		#Setting a vector of zeros, and setting the first value to 1 (no x attached to it)
	Vector[1] = 1
	for i=2:length(Vector)
		Vector[i] = Value^(i-1)	#Raising each value to its power in the polynomial
	end
	Vector = Vector'	#Tranpose the array before returning
	return Vector
end

#***EndPolyForm***

#***Start PolyDiff***		#Differentiates a polynomial vector

function PolyDiff(Polynomial_Vector)
	PolyDeriv = zeros(length(Polynomial_Vector)-1)	#Length of derivative vector will of course have one less slot
	for i=1:length(PolyDeriv)
		PolyDeriv[i] = i*Polynomial_Vector[i+1]	#Mutiplying the power by the coefficient of each value
	end
	PolyDeriv = PolyDeriv'	#Transposing befor returning
	return PolyDeriv
end

#***End PolyDiff****

#***Start PolyInt***		#Integrating polynomials vectors

function PolyInt(Polynomial_Vector)
	PolyIntegral = zeros(length(Polynomial_Vector)+1)	#It will have 1 more index (The constant term, set to zero)
	for i=2:length(PolyIntegral)				#Stting first value to zero
		PolyIntegral[i] = Polynomial_Vector[i-1]/(i-1)	#Dividing the coeffcients by the powers
	end
	PolyIntegral = PolyIntegral'	#Transposing before returning
	return PolyIntegral
end


#***Start Roots***

function Roots(Func,x0,xend,dt)		#Finds mutiple roots of a function within a specified domain, using specified spacing
	x = x0:dt:xend			#Spacing of 1 is reccomended unless you expect very condensed zeros
	y = Func(x)	
	Bool = zeros(length(y))		#Setting up a vector that will continue true and falses in the form of 1 and 0
	for i=1:length(y)
		if y[i] == 0
			Bool[i] = 2				#Setting the positives equal to 1, the negatives = 0, and 0's equal = 2
		else
			Bool[i] = (y[i] == abs(y[i]))
		end
	end
	
	Interval = []
	for i=1:length(y)-1
		if ((Bool[i] == 1) && (Bool[i+1] == 0)) || ((Bool[i] == 0) && (Bool[i+1] == 1))		#Finding to points where
			Bound = [x[i],x[i+1]]								#the signs switch
			push!(Interval,Bound)									
		end						#Created an array of intervals of left and right bounds for root findin
	end
	roots = zeros(length(Interval))
	for i=1:length(roots)
		roots[i] = fzero(Func,Interval[i][1],Interval[i][2])	#Iterating through calling the fzero function to find the 
	end								#roots for each interval containing a root
	for i=1:length(y)
		if y[i] == 0						#If any of our originally generated y values actually 
			push!(roots,x[i])				#contained a 0 (root), I'm tagging it on now
		end
	end
	roots = sort(roots)
	return roots			#Returning an array of all the roots
end
	

#***End Roots***

#***Start fzero***			#Will find the root of a function. Needs initial guesses		

function fzero(Func,a,b)
	Tol = 0.1			#Tolerance for the bisection method
	Deriv = diff(Func)		#Getting the derivative for the newton method
	x1 = 0.5*(a+b)
	if Func(x1) == 0		#If the first guess is right, just stop.
		x2 = x1
	else				#Otherwise generate the second value for the while loop
		if Func(x1)*Func(a) < 0
			b = x1
		else
			a = x1
		end
		x2 = 0.5*(a+b)
		if Func(x2)*Func(a) < 0
			b = x2
		else
			a = x2
		end
		count = 0
		while abs(x1 - x2) > Tol	#If the difference between values of x1 and x2 gets smaller than the Tol, stop.
			x1 = x2
			x2 = 0.5*(a+b)
			if Func(x2)*Func(a) < 0
				b = x2
			else
				a = x2
			end
			count += 1
		end
		xguess = x2			#The final x2 value from Bisection Method is the initial guess for Newtons Method
		Tol2 = 1e-6			#The tolerance is significantly lower
		x1 = xguess - Func(xguess)/Deriv(xguess)
		x2 = x1 - Func(x1)/Deriv(x1)			#Getting the first two values for the while loop
		while abs(x1 - x2) > Tol2	
			x1 = x2					#Iterating through
			x2 = x1 - Func(x1)/Deriv(x1)
		end
	end
	return x2						#Returning the zero
end

#***End fzero***
