function phantine(a,b,c)
	d = gcd(a,b)
	if(c % d != 0) 
		 println("The GCD of a,b must divide c!")
		 return (0,0)
	elseif b >= a
		println("b must be less than a!")
		return (0,0)
	else
		a = div(a,d)
		b = div(b,d)
		c = div(c,d)
	end

	start = a-b
	target = c % start
	x = 1 + div(c,start)
	y = 1 + div(c,start)

	iter = 0
	while(start != target)
		if start > target
			moveRight = cld((start - target),b)
			start -= b*moveRight
			y += moveRight
			iter += 1
		elseif target > start
			start += a
			x += 1
			iter += 1
		else continue end
	end
	println("Took $iter iterations")
	if a*x + b*(-y) == c return (x,-y)
	else 
		println("Computed incorrectly!")
		return (0,0) end
end

for i = 1:10
	a = rand(2:100000)
	b = rand(1:99999)
	while b >= a
		b = rand(1:99999)
	end
	d = gcd(a,b)
	c = rand(1:100000)
	while(c % d != 0)
		c = rand(1:100000)
	end
	sol = phantine(a,b,c)
	println("A solution for $(a)x + $(b)y = $c is: $a($(sol[1])) + $b($(sol[2]))\n")
end
