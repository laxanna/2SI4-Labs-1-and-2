package Lab_1_2;
public class HugeInteger {
	private int [] digit_list;
	private int num_len;
	
	public HugeInteger(int n) {
		//find if the integer is positive or negative
		if(n>=0) {										//if it is positive
			this.digit_list[0] = 0;
		}
		else {											//if it is negative
			this.digit_list[0] = 1;
			n= -n;
		}
		
		int n_copy = n, y = 1;							//find the largest 10^x that can be divide by the number
		while (n_copy>10) {
			n_copy/=10;
			y*=10;
		}
		
		int remainder, z = 1;							//enter the digits into the represented array
		while (n>0) {
			remainder = n%y;
			this.digit_list[z]= ((n-remainder)/y);
			y/=10;
			n = remainder;
			z++;
		}
		
		this.num_len = this.digit_list.length-1;		//update size of the list
	}
	
	public HugeInteger(String val) {
		String array[] = val.split("");					//Split the string into array of strings
		int x;											//find if the integer is positive or nevative 
		if (array[0]=="-") {							//if it is negative
			this.digit_list[0]=1;
			x=1;
		}
		else {											//if it is positive
			this.digit_list[0]=0;
			x=0;
		}
		
		for (int i=1; i<val.length(); i++, x++) {		//enter the digits into the represented array
			this.digit_list[i]=val.charAt(x);
		}
		
		this.num_len = this.digit_list.length-1;		//update size of the list
	}
	
	public HugeInteger add(HugeInteger h) {
		int x = 0;													//find the length of the sum array
		int y = 0;
		if(this.num_len == h.num_len) {								//if the numbers have the same number of digits
			x = this.num_len + 1;
		}
		else if (this.num_len > h.num_len) {						//if the value has more digit than h
			x = this.num_len; 
			y = 1;
		}
		else {														//if h has more digit than the value
			x = h.num_len;
			y = 2;
		}
		
		HugeInteger sum = new HugeInteger(0);						//initiate sum array
		sum.num_len = x;
		int z = 0;													//compare the sign between the numbers
		int w = 0;
		if (this.digit_list[0] == h.digit_list[0] ) {				//if they are the same sign
			sum.digit_list[0] = this.digit_list[0];							//initiate the sign of sum
			for(int i=x; i>1; i--) {
				z = this.digit_list[i] + h.digit_list[i] + w;		//add two value at the same digit level and the remainder
				if(z<=9) {											//if the sum is less than 10
					sum.digit_list[i] = z;
				}
				else {												//if the sum is more than 10
					sum.digit_list[i] = z - 10;
					w = 1;
				}
			}
		}
		else {
			int []temp1 = {0};											//initiate the dummy array
			int []temp2 = {0};											//initiate another dummy array
			if(y == 0) {									        //this number is the same digit as h
				for(int i=1; i<this.num_len; i++) {
					if(this.digit_list[0] > h.digit_list[0]) {		//the absolute of this number is greater h
						sum.digit_list[0]= this.digit_list[0];
						temp1 = this.digit_list;
						temp2 = h.digit_list;
					}
					else {											//the absolute of h is greater than this number
						sum.digit_list[0]= h.digit_list[0];
						temp1 = h.digit_list;
						temp2 = this.digit_list;
					}
				}
			}
			else if (y==1 ) {										//this number is greater than h in length
				sum.digit_list[0] = this.digit_list[0];
				temp1 = this.digit_list;
				temp2 = h.digit_list;
			}
			else {													//this number is smaller than h in length
				sum.digit_list[0] = h.digit_list[0];
				temp1 = h.digit_list;
				temp2 = this.digit_list;
			}
			for(int i = x; i>1 ; i--) {
				z = temp1[i] - temp2[i] - w;						//subtract two values at the same digit level and subtract the borrow 
				if (z>=0) {											//if the value is positive
					sum.digit_list[i] = z;
				}
				else {												//if the value is negative
					sum.digit_list[i] = z + 10;
					w = 1;
				}
			}
		}
		return sum;
	}
	public void sub(HugeInteger h) {
		int x = 0;													//find the length of the sum array
		int y = 0;
		if(this.num_len == h.num_len) {								//if the numbers have the same number of digits
			x = this.num_len + 1;
		}
		else if (this.num_len > h.num_len) {						//if the value has more digit than h
			x = this.num_len; 
			y = 1;
		}
		else {														//if h has more digit than the value
			x = h.num_len;
			y = 2;
		}
		
		HugeInteger diff = new HugeInteger(0);						//initiate difference array
		diff.num_len = x;
		int z = 0;													//compare the sign between the numbers
		int w = 0;
		if (this.digit_list[0] != h.digit_list[0] ) {				//if they are the same are different
			diff.digit_list[0] = this.digit_list[0];				//initiate the sign of sum
			for(int i=x; i>1; i--) {
				z = this.digit_list[i] + h.digit_list[i] + w;		//add two value at the same digit level and the remainder
				if(z<=9) {											//if the sum is less than 10
					diff.digit_list[i] = z;
				}
				else {												//if the sum is more than 10
					diff.digit_list[i] = z - 10;
					w = 1;
				}
			}
		}
	}
}

