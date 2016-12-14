package common;

public class RentPair {

	public static int size = 5;
	
	public Object	productName,
					rentData,
					turnData,
					overData,
					whoName;
	
	public RentPair( Object[] list ) {
		productName	= list[0];
		rentData	= list[1];
		turnData	= list[2];
		overData	= list[3];
		whoName		= list[4];
	}
	
}
