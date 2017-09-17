public class areaOfCircle
{
	public static double Half(double area, int iter)
	{
		for(int i = 0; i < iter; ++i)   
		{
			area /= 2;       //This function will serve to keep dividing the area the specified number of times.
		}                        //In this case, the area is representated in infinitely small triangles that make up space in the circle
		return area;
	}
	public static double circleArea(double radius)
	{
		double overShotArea = (7*Math.pow(radius,2))/2;      //This is the area computed that overshootst he bounds of the circle.
		double dividedArea = Math.pow(radius,2)/8;           //This will serve as base triangle that will divided into the infinitely small triangles.
		double areaToSubtract = 0;                           //The area to subtract fromt he overshot area.
		int a = 3, b = 5, check = 1;                         //a and b will hold values for how many times to divide the base triangle(dividedArea)
		double areaShaveOff = 5.0;                           //This represents and tracks values for the infinitely small triangles. Stop when the triangles are too small to store.
		while(areaShaveOff > 0.000000000000001)
		{
			if(check % 2 != 0)
			{
				check += 1;                          //The conditional statements adhere to the suspected pattern of the infinitely small triangles
				areaShaveOff = Half(dividedArea,a);  //This pattern follows a +1,+2 series. For instance. Chopping off a value equal to that of
				a += 3;                              //the dividedArea/8, then dividedArea/9, then dividedArea/11 and so.
				areaToSubtract += areaShaveOff*16;
			}
			else
			{
				check += 1;
				areaShaveOff = Half(dividedArea,b);
				b += 3;
				areaToSubtract += areaShaveOff*16;  //Multiply this number by 16. 4 quadrants of the circle. 2 sides per quadrant. Middle quadrant for each, is equal to 2 sides.
			}
		}
		System.out.println("It took "+check+" iterations to calculate the area");
		double newArea = overShotArea - areaToSubtract;     //Shave off the calculated overshot of the initially calculated area.
		return newArea;
	}

	public static void main(String[] args)
	{
		double radi = 10625.987745;
		double estiArea = circleArea(radi);
		double trueArea = Math.pow(radi,2)*Math.PI;
		double percentError = (estiArea - trueArea)/estiArea * 100;
		System.out.println("The percent error of the area estimation is "+percentError);
	}
}

