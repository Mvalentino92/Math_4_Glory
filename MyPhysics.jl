#These functions will help doing simple conversions for Physics.

#***Start distime***
"""
```jldoctest
distime(A,perB,C,perD,InitialValue)
```
This function will do a simple conversion for Distances over Time. **Example:** meters per second, to miles per hour.\n
Here is a list of input this function takes for Distances and Time respectively.\n
**Distance:** Millimeters = `"mm"` 	Centimeters = `"cm"` 	Decimeters = `"dm"` 	Meters = `"m"` 	Kilometers = `"km"`
              \nInches = `"inch"` 		Feet = `"ft"` 		Yards = `"yard"` 		Miles = `"mile"`\n
**Time:**     Seconds = `"sec"`		Minutes = `"min"` 	Hours = `"hour"` 		Days = `"day"`\n
# Example for converting 5 meters per second, to miles per hour
```jldoctest
julia> Dist_Conversion = distime("m","sec","mile","hour",5)
       print(Dist_Conversion)
       11.185
```
"""
function distime(A,perB,C,perD,InitVal)
	
	Time = Dict([("sec",1),("min",2),("hour",3),("day",4)]) #Using a Dict to keep track of index's for conversion
	Tconvert = [[1,60,3600,86400],[1/60,1,60,1440],[(1/60)/60,1/60,1,24],[((1/60)/60)/24,(1/60)/24,24,1]] #Here is the indexed list of all the conversions in order

	Distance = Dict([("mm",1),("cm",2),("dm",3),("m",4),("km",5),("inch",6),("ft",7),("yard",8),("mile",9)])

	Dconvert = [[1,10,100,1000,1000000,25.4,304.8,914.4,1609344],[0.1,1,10,100,10000,2.54,30.48,91.44,160934],[0.01,0.1,1,10,1000,0.254,3.048,9.144,16093.4],[0.001,0.01,0.1,1,1000,0.0254,0.3048,0.9144,1609.34],[0.000001,0.00001,0.0001,0.001,1,0.0000254,0.0003048,0.0009144,1.60934],[0.0393701,0.393701,3.93701,39.3701,39370.1,1,12,36,63360],[0.00328084,0.0328084,0.328084,3.28084,3280.84,0.8333333,1,3,5280],[0.00109361,0.0109361,0.109361,1.09361,1093.61,0.0277778,0.333333,1,1760],[0.000000621137,0.0000062137,0.000062137,0.00062137,0.62137,0.000015783,0.000189394,0.000568182,1]]


	N1 = Distance[A]  #Here I'm getting he conversion factors for both the numerator and denominator
	N2 = Distance[C]

	Numerator = InitVal*(Dconvert[N2][N2]/Dconvert[N1][N2]) #The numerator will be the initial value , times the conversion factors

	D1 = Time[perB]
	D2 = Time[perD] #The denominator will just be a time conversion

	Denominator = Tconvert[D2][D2]/Tconvert[D1][D2]

	return Numerator/Denominator #Return the numerator over the denominator!!
end

#***End distime***
