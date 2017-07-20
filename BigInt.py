# -*- coding: utf-8 -*-
"""
Created on Mon Jun 19 00:07:46 2017

@author: michael
"""

"""Made a simple one to do addition.
Thinking of how to approach multiplication next"""

#The inputs need to be lists
def bigsum(bigger,smaller):
    final = []
    #Thought it was easier to work in reverse with appending
    bigger.reverse()
    smaller.reverse()
    #I used this variable 'a' to keep track of
    #ones carrying over.
    a = 0
    for j in range(0,len(smaller)):
        summ = bigger[j] + smaller[j]
        #If the sum is less then 10, add 'a' and throw it down!
        #But also make 'a' zero for the next iteration
        if summ + a < 10:
            final.append(summ + a)
            a = 0
            #Otherwise minus 10 first, and add 'a' as well
            #But 'a' needs to be set to one this time.
            #So it can be added next iteration
        else:
            final.append((summ - 10) + a)
            a = 1
        
    #And here are all the the possible last number touches
    #Based on how big the size difference was between the numbers        
        
    if len(smaller) == len(bigger) and a == 1:              #Upcoming codeblocks handle all possible outcomes with the "1"
        final.append(a)                                     #carrying over.
        
        
    elif len(bigger) - len(smaller) == 1:
        x = bigger[len(smaller)]+a
        if x < 10:
            final.append(x)
        else:
            final.extend([0,1])
   
    
        
    elif len(bigger) - len(smaller) > 1:
        x = bigger[len(smaller)] + a
        if x < 10:
                final.append(x)
                final.extend(bigger[len(smaller)+1:])
        else:
            for i in range(len(smaller),len(bigger)):
                x = bigger[i] + a
                if x == 10:
                    final.append(0)
                    a = 1
                else:
                    final.append(x)
                    a = 0
                    break
            if len(final) < len(bigger):
                final.extend(bigger[i+1:])
            else:
                if a == 1:
                    final.append(a)
                
    #And if Python can store it, turn it into an integer
    
    
    final.reverse()
    Final = final
    """if len(final) < 20:
        Final = []
        for i in final:
            Final.append(str(i))
        Final = int(''.join(Final))
    else:
    #Otherwise, keep it as a list
        Final = final"""
    bigger.reverse()
    smaller.reverse()
    return Final
    
    
    """Multiplication fuction"""
    
def bigmult(BigList,SmallMultiplier):           #What better way to do mutiplication then to recursively do addition?
    
    Smult = []                                  #Just turning the SmallMutiplying number into an integer if it wasnt one already
    if type(SmallMultiplier) == list:
        for i in SmallMultiplier:
            Smult.append(str(i))
        Smult = int(''.join(Smult))
    else:
        Smult = SmallMultiplier
            
    Blist = []                                 #Getting a duplicate list of the BigList to add over and over again.
    for i in BigList:
        Blist.append(i)
            
    for j in range(0,Smult-1):                  #For however many interations (denoted by the size of the SmallMultiplier..
        BigList = bigsum(BigList,Blist)         #do addition of BigList and the static Blist over and over.
    return BigList
      
        
        
    
