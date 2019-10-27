using Combinatorics
function comp(G::Matrix{Int64})
	V = size(G)[1]
	GComp = Matrix{Int64}(undef,V,V)
	for i = 1:V
		for j = 1:V
			GComp[i,j] = G[i,j] == 1 || i == j ? 0 : 1
		end
	end
	return GComp
end

function swapLabel(G::Matrix{Int64},i::Int64,j::Int64)
	V = size(G)[1]
	for k = 1:V
		if(k == i || k == j) continue end

		temp = G[k,i]
		G[k,i] = G[k,j]
		G[k,j] = temp

		temp = G[i,k]
		G[i,k] = G[j,k]
		G[j,k] = temp
	end
end

function isIso(G::Matrix{Int64},H::Matrix)
	V = size(G)[1]
	root = collect(1:V)
	perm = collect(permutations(root))
	for p in perm
		for i = 1:V
			if root[i] != p[i]
				for j = 1:V
					if root[i] == p[j]
						swapLabel(H,i,j)
						temp = root[i]
						root[i] = root[j]
						root[j] = temp
						break
					end
				end
			end
		end
		if G == H 
			retval = zeros(Int64,V)
			for j = 1:V retval[root[j]] = j end
			println(retval)
			return true
		end
	end
	return false
end
