#This file will contain any relevant functions I need for operating on Strings, or anything not particularly related to mathematical needs.

#***Start Count***

#Counts the number of occurances of a character(s) in a string.
"""
```jldoctest
Count(String,Character(s))
```
Counts the amount of times the supplied character(s) occurs in the given string.
# Examples
```jldoctest
julia> My_String = "Writing code in Julia is so easy!!"
       Num_of_occur = Count(My_String,"i")
       print(Num_of_occur)
       5
```
"""
function Count(String,Character)
	String_Length = length(String)		
	Occur_Length = length(Character)
	Total_Occur = 0
	for i=1:String_Length			#Im iterating over the elements in the string
		Total_Match = 0			#I need to track if all the elements of the target string match
		for j=0:Occur_Length - 1				#Starting the second iteration to accomplish that
			if length(String[i:end]) >= Occur_Length	#Only check if you have enough space too!
				if String[i+j] == Character[j+1]	#EX: If I'm looking for 'happy', and the string is 3 char long
					Total_Match += 1		#It's a no go!
				end
			end
		end
		if Total_Match == Occur_Length		#If all the character match, add a point to total
			Total_Occur += 1
		end
	end
	return Total_Occur
end

#***End Count***

#***Start rotR***

#This function will rotate the elements in an array to right by the specified number of times.
"""
```jldoctest
rotR(Array,Num_of_places)
```
Rotates an array the amount of places specified to the right.\n
Works for *overshooting* as well. **Example:** If the array is of length 5, rotating 10 places will yield the same array.
# Examples
```jldoctest
julia> Array = [1,2,3,4,5]
       rotR(Array,3)
       print(Array)
       [3,4,5,1,2]
```
"""
function rotR(array,n)
	if n == length(array)		#If the rotation matches the number of elements itll do a perfect 360!
		return array
	end
	while n > length(array)	   	#You can go more than 360 though! 540 is the same as 180!. But we can only work with 180!
		n -= length(array)	#Minus 360 til we can work with it!
	end
	Temp = array[end-n+1:end]	#Make a temporary array holding the elements that are going to do a revolution around!
	array[n+1:end] = array[1:end-n]		#Now move up the elements up that wont 'go over the edge'
	array[1:n] = Temp[1:n]			#Place the numbers from the temporary array where they belong! (Beginning)
	return array
end
	
#***End rotR***

#***Start rotL***

#This functiion will rotate the elements in array to the left by the specified number of times
"""
```jldoctest
rotL(Array,Num_of_places)
```
Rotates an array the amount of places specified to the left.\n
Works for *overshooting* as well. **Example:** If the array is of length 5, rotating 10 places will yield the same array.
# Examples
```jldoctest
julia> Array = [1,2,3,4,5]
       rotL(Array,2)
       print(Array)
       [3,4,5,1,2]
```
"""
function rotL(array,n)
	if n == length(array)
		return array	#See above function!
	end
	while n > length(array)
		n -= length(array)
	end
	Temp = array[1:n]	#Same deal,save the elements that will 'fall off'
	array[1:end-n] = array[n+1:end]		#Place the elements where they belong, since they have room to move
	array[end-n+1:end] = Temp[1:n]		#And finally put the elements in the temp array where they belong (at the end)
	return array
end

#***Start slowSort***

#=This function will sort an array in non decreasing order. Destroys/deletes the original. Efficient until around 1e4=#
"""
```jldoctest
slowSort(Array)
```
Sorts an array in **non** decreasing order. Destroys the original array in the process.\n
This function is inferior to `Sort`, advisable to use `Sort` instead.
# Examples
```jldoctest
julia> x = [4,87,2,0,8]
       y = slowSort(x)
       print(x)
       {} Empty Array!
       print(y)
       [0,2,4,8,87]
```
"""
function slowSort(x)
        y = zeros(length(x))    #Making a new array of zeros. Doing the transfer technique
        for i=1:length(x)
                track = 1
                best = x[1]             #So the default lowest number will alwys be the first one
                for j=1:length(x)       #Iterating through starting from the top
                        if x[j] < best
                                best = x[j]             #if the number is lower, replace best
                                track = j
                        end
                end
                y[i] = best             #best is now the lowest number in x, so put it as the first entry into y
                deleteat!(x,track)      #So we dont just have an array of the first best, we need to delete it!
        end
        return y                        #return the new, and sorted vector y
end

#***End slowSort***

#***Begin Sort***

#Same as before, except a little bit more optimized, and doesnt destroy the original array.
"""
```jldoctest
Sort(Array)
```
Sorts the supplied array in **non** decreasing order. Returns the original array sorted.\n
Not intended for arrays of length 1e5++.
# Examples
```jldoctest
julia> x = [4,98,3,65,8,8,9]
       Sort(x)
       print(x)
       [3,4,8,8,9,65,98]
```
"""
function Sort(x)
        for i=1:length(x)-1     #Only going out to the second to last elements, because the last remaining element, will be the largest
                hold = x[i]     #Setting a variable to hold the value that will be replaced
                best = x[i]
                tracker = i             #This will track if the best value was updated or not
                for j=(i+1):length(x)
                        if x[j] < best          #Again, keep replacing the best (lowest) value
                                best = x[j]
                                tracker = j
                        end

                end
                if tracker != i                 #If tracker changed (as in the original best wasnt the smallest value)..
                        x[i] = x[tracker]       #Then switch the values around
                        x[tracker] = hold
                else
                        continue                #Otherwise, just do nothing, it was already the lowest!
                end
        end
        return x
end

#***End Sort***

#***Start Sinput***

"""
```jldoctest
Sinput(User_Prompt)
```
Prompts the user for String input, and saves the input to a variable.
# Examples
```jldoctest
julia> Name = Sinput("What is you're name partner?: ")
       What is you're name partner: Mike
       print(Name)
       Mike
```
"""
function Sinput(Prompt)
	print(Prompt)
	chomp(readline())
end

#***End Sinput***

#***Start Finput***
"""
```jldoctest
Finput(User_Prompt)
```
Prompts the user for Float input, and saves the input to a variable.
# Examples
```jldoctest
julia> Fav_Float = Finput("What is you're favorite Float?: ")
       What is you're favorite Float?: 1.666
       print(Fav_Float)
       1.666
```
"""
function Finput(Prompt)
	print(Prompt)
	parse(Float64,readline())
end

#***End Finput***

#***Start Ninput***
"""
```jldoctest
Ninput(User_Prompt)
```
Prompts the user for Int input, and saves the input to a variable.
# Examples
```jldoctest
julia> Fav_Integer = Ninput("What is you're favorite Integer?: ")
       What is you're favorite Integer?: 21
       print(Fav_Float)
       21
```
"""
function Ninput(Prompt)
	print(Prompt)
	parse(Int64,readline())
end

#***End Ninput***

