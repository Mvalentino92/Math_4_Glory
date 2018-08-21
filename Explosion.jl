function explosion(arr,row,col)
	
	#Going up
	for i = row:-1:1
		if arr[i][col] == 1 break end
		if arr[i][col] == 8 continue end
		arr[i][col] = 8
		explosion(arr,i,col)
	end

	#Going right
	for i = col:length(arr[1])
		if arr[row][i] == 1 break end
		if arr[row][i] == 8 continue end
		arr[row][i] = 8
		explosion(arr,row,i)
	end

	#Going down
	for i = row:length(arr)
		if arr[i][col] == 1 break end
		if arr[i][col] == 8 continue end
		arr[i][col] = 8
		explosion(arr,i,col)
	end

	#Going left
	for i = col:-1:1
		if arr[row][i] == 1 break end
		if arr[row][i] == 8 continue end
		arr[row][i] = 8
		explosion(arr,row,i)
	end
end

function printGrid(arr)
	for i = 1:length(arr)
		for j = 1:length(arr[i])
			print("$(arr[i][j]) ")
		end
		println()
	end
	println()
end

x = [[1,1,1,1,1,1,1,1,1,1,1],
     [1,1,0,0,1,1,1,0,0,0,1],
     [1,1,0,0,1,1,1,0,0,0,1],
     [1,1,0,0,0,1,1,0,0,1,1],
     [1,1,0,0,0,0,0,0,0,1,1],
     [1,1,0,0,0,0,0,0,0,1,1],
     [1,0,0,1,1,0,0,1,1,1,1],
     [1,0,1,1,1,0,0,1,1,1,1],
     [1,0,1,1,1,0,0,0,0,0,1],
     [1,0,1,1,1,0,0,0,0,1,1],
     [1,0,0,0,1,1,1,0,0,1,1],
     [1,1,0,0,1,1,1,0,0,0,1],
     [1,0,0,1,1,1,1,1,1,1,1],
     [1,1,1,1,1,1,1,1,1,1,1]]

printGrid(x)
explosion(x,6,5)
printGrid(x)
