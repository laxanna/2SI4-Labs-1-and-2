package Lab_1_2;
public class HugeInteger {
	private int [] digit_list;
	public HugeInteger(int n) {
		//when n is a positive value
		if(n>=0) {
			this.digit_list[0] = 0;
		}
		
		//when n is a negative value
		else {
			this.digit_list[0] = 1;
			n= -n;
		}
		
		//find the number of digit of n
		int n_copy = n, val_len= 0, y = 1;
		while (n_copy>1) {
			val_len+=1;
			n_copy/=10;
			y*=10;
		}
		
		//enter the 
		int remainder, z=1;
		while (-n>0) {
			remainder = -n%y;
			this.digit_list[z]= ((n-remainder)/y);
			y/=10;
		}
	}
	
	public HugeInteger(char val) {
		
	}
	
	public void add(HugeInteger h) {
		
	}
}

/*
if(x < Math.pow(2,63-1) || x > Math.pow(-2,-63)) {
	System.out.println("This number is still able be store by the computer");
}
else {
	
}
*/