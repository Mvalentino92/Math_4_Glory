#Rotates the array to the right by using a temporary variable

function RotateRight(array,n)
	if n == length(array)
		return array
	end
	while n > length(array)
		n -= length(array)
	end
	Temp = array[end-n+1:end]
	array[n+1:end] = array[1:end-n]
	array[1:n] = Temp[1:n]
	return array
end

#Rotates the array to the left

function RotateLeft(array,n)
	if n == length(array)
		return array
	end
	while n > length(array)
		n -= length(array)
	end
	Temp = array[1:n]
	array[1:end-n] = array[n+1:end]
	array[end-n+1:end] = Temp[1:n]
	return array
end
