#=Compares and sorted two sorted lists.
Does the sorting in the array by swapping elements.=#
function swap(array,stride,start)
	for i = start:(start + stride - 1)
		k = start + stride
		if array[k] < array[i]
			temp = array[i]
			array[i] = array[k]
			for j = i+1:(start + stride - 1)
				temp2 = array[j]
				array[j] = temp
				temp = temp2
			end
			array[k] = temp
			while k < start + stride*2 - 1
				if array[k] > array[k+1]
					temp = array[k]
					array[k] = array[k+1]
					array[k+1] = temp
					k += 1
				else break end
			end
		end
	end
end

#=A combination of an iterative mergesort, insertion sort, and bubblesort.
Only to be used to sort arrays with a length equaling a power of 2.=#
function powSort(array)
	stride = 1
	while stride <= div(length(array),2)
		for i = 1:stride*2:length(array) - stride
			swap(array,stride,i)
		end
		stride *= 2
	end
end

x = rand(1:1000,2^17)
powSort(x)
println(x == sort(x))
