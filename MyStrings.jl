#This file will contain any relevant functions I need for operating on Strings, or anything not particularly related to mathematical needs.

#***Start Count***

#Counts the number of occurances of a character(s) in a string.

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

