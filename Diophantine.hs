main = do
     let x = getDio 10000
     print x
     return (x)

-- This is going to check if the current a and b satisfy the Diophantine equation.
dioPhantine a b
   | last numbers == a*b = length numbers - 1
   | otherwise = 0
   where k = a + b
         thresh = a*b
         oddStart = 2*k - 1
         numbers = takeWhile (<=thresh) $ scanl (+) 0 [oddStart,(oddStart - 2)..1]

{- Uses a list comprehension to iterate a's and b's, while calling the above function as well.
 - This will return a list of a bunch of (a,b,c) triplets. -}
getDio bound = [(a,b,(a + b - n)) | a <- [1..bound], b <- [(a+1)..bound], let n = dioPhantine a b, n > 0 ]
