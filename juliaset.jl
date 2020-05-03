using IterTools
using Plots
using Dates

# Negating tuples
neg(T) = map(x -> -x,T)

function inset(z::Complex,c::Complex,R::Real,maxiter::Int)
	for i = 1:maxiter
		z = z*z + c
		if abs(z) > R
			return false
		end
	end
	return true
end

function J(c::Complex;maxdepth::Int=2,h::Real=0.01073937,maxiter::Int=35,set::Vector=[])
	
	# Find R
	R = max(abs(c),2)

	# If not passing initial set of points to recur expand, generate
	if isempty(set)

		# Generate bound
		bound = sqrt(R)

		# Create intial ranges
		xrange = 0:h:bound
		yrange = -bound:h:bound

		# Sort the yrange, so we can cut out circle of radius R
		yrange = sort(yrange,by=(x -> abs(x)))

		# Create the set of initial points (speed up by negating any found)
		set = [(x,y) for x in xrange for y in 
		      takewhile(y -> x*x + y*y <= R,yrange) 
		      if inset(x + (y)im,c,R,maxiter)]

		# Grab negatives
		set = merge(set,map(x -> neg(x),set))
	end

	# Begin recursive calls for generating tighter and tighter points
	return isempty(set) ? set : 
	append!(set,mapreduce(x -> exploit(x,c,R,h/2,maxdepth,maxiter),(a,b) -> append!(a,b),set))
end

function exploit(z::Tuple,c::Complex,R::Real,h::Real,depth::Int,maxiter::Int)
	
	# If depth = 0 return empty
	if depth == 0 return [] end

	# Get new ranges
	xs,xt,ys,yt = z[1]-h*0.95,z[1]+h*0.95,z[2]-h*0.95,z[2]+h*0.95
	xrange = LinRange(xs,xt,4)
	yrange = LinRange(ys,yt,4)
	h = ((xrange[2] - xrange[1]) + (yrange[2]-yrange[1]))*0.5

	# Generate set
	set = [(x,y) for x in xrange for y in yrange if inset(x + (y)im,c,R,maxiter)]

	# Begin recursive calls for generating tighter and tighter points
	return isempty(set) ? set : 
	append!(set,mapreduce(x -> exploit(x,c,R,h/2,depth-1,maxiter),(a,b) -> append!(a,b),set))
end

function fractals(num::Int;crange::Real=sqrt(2))
	dte = string(pwd(),"/Fractals_",today(),"/")
	if !isdir(dte) mkdir(dte) end
	while num > 0
		c = 2*crange*(rand()-0.5) + (2*crange*(rand()-0.5))im
		initset = J(c,maxdepth=0)
		l = length(initset)
		if l < 17500 && l > 1000
			set = J(c,set=initset)
			c = round(c,digits=3)
			plt = scatter(set,markersize=0.1,title=string("c = ",c))
			png(plt,string(dte,"J(",c,")"))
			num -= 1
		end
	end
end

function mandelbrot(;bound::Real=pi,h::Real=0.01073937,maxiter::Int=35)

	# Generate the set by pure testing
	range = -bound:h:bound
	return [(x,y) for x in range for y in range if inset(x + (y)im, x + (y)im, max(2,sqrt(x*x + y*y)),35)]
end

