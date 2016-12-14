package common;

public class MyPair {

	public Object	code,		// serial number(PK)
					price,
					selected,	// is Selected
					count;		// original count
	
	public MyPair( Object[] list ) {
		code		= list[0];
		price		= list[1];
		selected	= list[2];
		count		= list[3];
	}
	
}
