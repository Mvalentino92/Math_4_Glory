#include <stdio.h>
//Will tell you if a number is a Prime. Works quickly up to the bounds of storage (1 trillion)
unsigned long int PrimeConfirm(unsigned long int Prime) {
    unsigned long int Num,k;
    short i,j,Tracker = 0,Odd[3] = {3,7,9}; //Setting up variables
    
    if((Prime > 2 & Prime % 2 == 0) | Prime == 1){  //Taking care of the lower primes (1,2)
        Tracker += 1;
    }
    if(Prime > 5 & Prime % 5 == 0){                 //Taking care of it the number is 5
        Tracker += 1;
    }
    for(j=0;j<3;j++){
        if(Prime != 3 & Prime != 7 & Prime % Odd[j] == 0){ //Taking care of 3 and 7. 
            Tracker += 1;           //Also, if then number is divisible by 3,7 or 9 then stop.
            break;
        }
    }
    if(Tracker == 0){           //So if it still hasnt been divisible yet then keep trying
        Num = Prime / 9;        //The upper bound will be the quotient of its last iteration of division
        for(k=11;k<Num;k+=2){
            Num = Prime / k;        //So keep updating the upper bound on each iteration!
            if(Prime % k == 0){
                Tracker += 1;
                break;
            }
        }
    }
    if(Tracker == 0){
        printf("Yes, %lu is a Prime Number\n",Prime);
        
    }
    else{
        printf("Sorry, %lu is not a Prime Number\n",Prime);
        
    }
    return 0;
}
    
    
