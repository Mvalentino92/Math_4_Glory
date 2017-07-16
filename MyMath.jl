
#***Function to Compute the Arc Lenght, Decent Precision.***

function ArcLen(f,x0,xend)
	total = 0
	iter = 1e-4
	x1 = x0
	x2 = x0 + iter
	y1 = f(x1)
	y2 = f(x2)
	while x2 <= xend
		total += sqrt((x2-x1)^2 + (y2-y1)^2)
		x1 = x2
		x2 = x1 + iter
		y1 = f(x1)
		y2 = f(x2)
	end
	return total
end

#***End of ArcLength Function***


#***Romberg integration***

function RomInt(f,x0,xend)
	dt = 2e-4
	total = zeros(4)
	for count = 1:4
		dt /= 2
		yhold = collect(x0:dt:xend)
		y = map(f,yhold)
		total[count] += (y[1]*dt/2 + y[end]*dt/2)
		for i in y[2:end-1]
			total[count] += i*dt
		end
	end
	n = 4
	t1 = (n*total[2] - total[1])/(n-1)
	t2 = (n*total[3] - total[2])/(n-1)
	t3 = (n*total[4] - total[3])/(n-1)
	n *= 4
	t1 = (n*t2 - t1)/(n-1)
	t2 = (n*t3 - t2)/(n-1)
	n *= 4
	final = (n*t2 - t1)/(n-1)
	return final
end

#***End of Romberg function***

#***Start Trapz****

function TrapZ(x,y)
	dt = diff(x[1:2])
	total = (y[1]*dt/2 + y[end]*dt/2)
	for i in y[2:end-1]
		total += i*dt
	end
	return total
end

#***End TrapZ***
	

#***Start of CumTrapz Function	

function CumTrapZ(x,y)
	y_end = TrapZ(x,y)[1]
	dt = diff(x[1:2])[1]
	Integral = zeros(length(x))
	Integral[1] = y_end
	y  = reverse(y)
	for i=1:length(x)-1
		Integral[i+1] = Integral[i] - (y[i]*dt + y[i+1]*dt)/2
	end
	Integral = reverse(Integral)
	return Integral
end

#***Start Lagrangian Interpolation***
	
function Lagrange(x,y)
	Left = 1
	Right = 3
	InterpolationTicks = []
	InterpolationValues = []
	while Right != (length(x) - 1)
		NEWX = x[Left:Right]
		NEWY = y[Left:Right]
		InitialSpacing = sum(diff(NEWX))/2
		NewSpacing = InitialSpacing/10
		NEWX = convert(Array{Float64,1},NEWX)
		LagInterval = NEWX[1]:NewSpacing:NEWX[end]
		println(LagInterval)
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
		if Right == length(x)
			break
		end
		Left = Right
		Right += 2
		
	end
	if length(x) != Right
		Left = Left 
		Right += 1
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
	return InterpolationTicks,InterpolationValues	
end
		
#***End Lagrangian Interpolation***
	
