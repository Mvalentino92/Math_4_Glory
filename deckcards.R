#Percent error
perr = function(exp,tru) return((exp-tru)/tru*100)

#Calculates probability of getting all the hands
handProb <- function(deck,tries)
{
  counts = replicate(10,0)
  sampSize = 1:length(deck)
  for(i in c(1:tries))
  {
	  samp = sample(sampSize,5)
	  mn = min(samp)
 	  mx = max(samp)
	  dist = mx - mn
	  if(((mn >= 1 & mx <= 13)|
	     (mn >= 14 & mx <= 26)|
	     (mn >= 27 & mx <= 39)|
	     (mn >= 40 & mx <= 52)))
	  {
		  if(dist == 4)
		  {
			  if(mn %% 13 == 9) counts[1] = counts[1] + 1
			  else counts[2] = counts[2] + 1
		  }
		  else counts[5] = counts[5] + 1
	  }
          else 
	  {
		  rows = sapply(samp,(function(x)(x%%13)))
		  rows2 = rows
		  for(i in c(1:5))
			  if(rows[i] == 0) rows[i] = 13
		  dif = diff(sort(rows))
		  dif2 = diff(sort(rows2))
		  if(sum(dif == 1) == 4 | sum(dif2 == 1) == 4) counts[6] = counts[6] + 1
		  else
		  {
			s = sum(rows == rows[1])
			same = sum(dif == 0)
			if(same == 3)
			{
				if(s == 1 | s == 4) counts[3] = counts[3] + 1
				else counts[4] = counts[4] + 1
			}
			else if(same == 2)
			{
				s2 = sum(rows == rows[2])
				if((s == 3 | s == 1) & 
				   (s2 == 3 | s2 == 1)) counts[7] = counts[7] + 1
			        else counts[8] = counts[8] + 1
			}
			else if(same == 1) counts[9] = counts[9] + 1
			else counts[10] = counts[10] + 1
		  }
	  }
  }
		  
  results = sapply(counts,(function(x)(x/tries*100)))
  trueVals = c(0.000154,0.00139,0.024,0.1441,0.1965,0.3925,2.1128,4.7539,42.2569,51.1177)
  names = c("Royal Flush","Straight Flush","Four-of-a-kind","Full House",
	    "Flush","Straight","Three-of-a-kind","Two Pair","One Pair","No Pair/High Card")
  errors = c()
  for(i in c(1:length(results))) errors = c(errors,perr(results[i],trueVals[i]))
  chart = matrix(1:30,ncol=3)
  chart[,1] = results
  chart[,2] = trueVals
  chart[,3] = errors
  rownames(chart) = names
  colnames(chart) = c("Exp Prob","Theo Prob","Percent Err")
  return(chart)
}

#Init vars
deck = matrix(1:52,ncol=4)
rownames(deck) = c("2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace")
colnames(deck) = c("Hearts","Spades","Diamonds","Clubs")
tries = 365000 #3 million, takes about 3 minutes.

#Store results and print
chart = handProb(deck,tries)
print(chart)
