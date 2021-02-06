package Lab_1_2;

import java.util.Arrays;
//import the functions from Arrays library

public class HugeInteger {
	private int [] digit_list;
	private char value_sign;
	
	public HugeInteger(int n) throws ArithmeticException {
		if(n>10000) {															//if the value is too big
			throw new ArithmeticException ("Value too long");
		}
		else if(n>0) {
				this.digit_list = new int [n];
				
				int z = (int) Math.random();									//find if the integer is positive or value_sign
				if (z>0.5) {
					this.value_sign = 'p';
				}
				else {
					this.value_sign = 'n';
				}
				
				
				double x = Math.random();										// generate leading entry
				while(x == 0) {
					x = Math.random();
					}
				this.digit_list[n-1] = (int) (x*10);
				
				for(int i=0; i<n-1; i++) {										//generate the digit list
					double y = Math.random();
					while (y==1) {
						y = Math.random();
					}
					this.digit_list[i]= (int) (y*10);
				}
			}
		
		else{																	//if the value digit is negative
			throw new ArithmeticException ("can't generate integer with negative number of digits"); 
		}
	}

		public HugeInteger(String val) throws ArithmeticException {
			
			String newval = "";													//first handle leading value_sign and remove it if it exists
			if(val.charAt(0) == '-') {
				this.value_sign = 'n';											//false is negative 
				newval = val.substring(1); 										//new string without leading value_sign
			}
			else {
				this.value_sign = 'p';											//true is positive
				newval = val;													//same string
			}
			
			newval = newval.replaceFirst("^0+(?!$)", "");						//then remove leading zeroes from newval	
			
			if(newval.matches("^[0]")) {										//Trivial case if input "0" and "-0".
				this.value_sign = 'p';
			}
			
			this.digit_list = new int[newval.length()];							//now we can copy our stuff to an int array.
			for(int i=0; i < newval.length(); i++) {
				this.digit_list[newval.length()-1-i] = newval.charAt(i)-48;		//insert number into the corresponding place
				if((int)newval.charAt(i) > 57 || (int) newval.charAt(i) < 48 ) {//check if there is any character other than 0-9
					throw new ArithmeticException("There are characters other than 0 to 9");
				}
			}
				
		}
	public HugeInteger(char sign, int digits) throws ArithmeticException {
		
		if(digits<0) {															//if less than 0 length
			throw new ArithmeticException("An integer can't have value_sign length, additional constructor");
		}
		else if(digits>100000) {												//if more than 100000
			throw new ArithmeticException("That is like way too long, above 100000 digits in length, additional constructor");
		}
		else {
			this.value_sign =sign;												//the true/false boolean
			
			digit_list = new int[digits];										//int a new digit_list
			Arrays.fill(digit_list, 0);											//fill it with 0s
		}
	}
///////////////////////////////////////////////////////add
	public HugeInteger add(HugeInteger h) {
		
		int sum_len = Math.max(this.digit_list.length, h.digit_list.length); 	//make a new array using the one have bigger length
		HugeInteger sum = new HugeInteger('p',sum_len+1);						//initialize a large sum array with allowance for overflow

		if(this.value_sign == h.value_sign) {									//when both number is same sign
			
			sum.value_sign = this.value_sign;									//the sign should be the same as other number and be positive									
			
			//now do long addition with h
			int carry_out = 0;
			for(int i=0; i<sum.digit_list.length; i++) {
				
				int index_sum = carry_out;
				
				if(i < this.digit_list.length) {
					index_sum = index_sum + this.digit_list[i];
				}
				if(i < h.digit_list.length) {
					index_sum = index_sum + h.digit_list[i];
				}
								
				if(index_sum >= 10) {
					carry_out = 1;
				}
				else {
					carry_out = 0;
				}
				
				int remainder = index_sum % 10;
				sum.digit_list[i] = remainder;
			}
		}

		//if this only is value_sign
		else {
			
			HugeInteger big = new HugeInteger('p', 1);							//place holder for bigger absolute number 
			HugeInteger small = new HugeInteger('p', 1);						//					smaller absolute number
			
			if(this.digit_list.length>h.digit_list.length) {
				big = this;
				small = h;
			}
			else if(h.digit_list.length>this.digit_list.length) {
				big = h;
				small = this;
			}
			else {
				int i=0;
				for(i= this.digit_list.length; i>0; i--) {
					if(this.digit_list[i-1]>h.digit_list[i-1]) {
						big = this;
						small = h;
						break;
					}
					if(this.digit_list[i-1]<h.digit_list[i-1]) {
						big = h;
						small = this;
						break;
					}
				}
				if(i==0) {
					big = h;
					small = h;
				}
			}
			sum.value_sign = big.value_sign;									//the sign should be the same as other number and be positive									
			
			//now do long addition with h
			int carry_out = 0;
			for(int i=0; i<sum.digit_list.length; i++) {
				
				int index_sum = carry_out;
				
				if(i < big.digit_list.length) {
					index_sum = index_sum + big.digit_list[i];
				}
				
				if(i < small.digit_list.length) {
					if(index_sum<small.digit_list[i]) {
						index_sum += 10;
						carry_out = -1;
					}
					else {
						carry_out = 0;
					}
					index_sum = index_sum - small.digit_list[i];
				}
				else {
					carry_out = 0;
				}
				sum.digit_list[i]= index_sum;
			}
		}
		
		String output = new String();											//copy the sum array to output array to turn it into wanted number
		for(int i=sum.digit_list.length-1; i>=0; i--) {
			output = output + sum.digit_list[i];
		}

		output = output.replaceFirst("^0+(?!$)", "");							//remove leading zeros
		
		HugeInteger actual_sum = new HugeInteger('p',output.length());			//int a new array to store the final sum array
		actual_sum.value_sign = sum.value_sign;									//assign array's sign
		
		
		actual_sum.digit_list = new int[output.length()]; 						//copy integer to the array
		for(int i=0; i < output.length(); i++) {
			actual_sum.digit_list[output.length()-1-i] = output.charAt(i)-48;
		}
		
		return actual_sum;

	}

//////////////////////////////////////////////////////sub
	
	public HugeInteger sub(HugeInteger h) {
		
		int diff_len = Math.max(this.digit_list.length, h.digit_list.length); 	//make a new array using the one have bigger length
		HugeInteger diff = new HugeInteger('p',diff_len+1);						//initialize a large sum array with allowance for overflow

		if(this.value_sign != h.value_sign) {									//when both number is same sign
			
			diff.value_sign = this.value_sign;									//the sign should be the same as other number and be positive									
			
			//now do long addition with h
			int carry_out = 0;
			for(int i=0; i<diff.digit_list.length; i++) {
				
				int index_sum = carry_out;
				
				if(i < this.digit_list.length) {
					index_sum = index_sum + this.digit_list[i];
				}
				if(i < h.digit_list.length) {
					index_sum = index_sum + h.digit_list[i];
				}
								
				if(index_sum >= 10) {
					carry_out = 1;
				}
				else {
					carry_out = 0;
				}
				
				int remainder = index_sum % 10;
				diff.digit_list[i] = remainder;
			}
		}

		//if this only is value_sign
		else {
			
			HugeInteger big = new HugeInteger('p', 1);							//place holder for bigger absolute number 
			HugeInteger small = new HugeInteger('p', 1);						//					smaller absolute number
			
			if(this.digit_list.length>h.digit_list.length) {
				big = this;
				small = h;
			}
			else if(h.digit_list.length>this.digit_list.length) {
				big = h;
				small = this;
			}
			else {
				int i=0;
				for(i= this.digit_list.length; i>0; i--) {
					if(this.digit_list[i-1]>h.digit_list[i-1]) {
						big = this;
						small = h;
						break;
					}
					if(this.digit_list[i-1]<h.digit_list[i-1]) {
						big = h;
						small = this;
						break;
					}
				}
				if(i==0) {
					big = h;
					small = h;
				}
			}
			diff.value_sign = big.value_sign;									//the sign should be the same as other number and be positive									
			
			//now do long addition with h
			int carry_out = 0;
			for(int i=0; i<diff.digit_list.length; i++) {
				
				int index_diff = carry_out;
				
				if(i < big.digit_list.length) {
					index_diff = index_diff + big.digit_list[i];
				}
				
				if(i < small.digit_list.length) {
					if(index_diff<small.digit_list[i]) {
						index_diff += 10;
						carry_out = -1;
					}
					else {
						carry_out = 0;
					}
					index_diff = index_diff - small.digit_list[i];
				}
				else {
					carry_out = 0;
				}
				diff.digit_list[i]= index_diff;
			}
		}
		
		String output = new String();											//copy the sum array to output array to turn it into wanted number
		for(int i=diff.digit_list.length-1; i>=0; i--) {
			output = output + diff.digit_list[i];
		}

		output = output.replaceFirst("^0+(?!$)", "");							//remove leading zeros
		
		HugeInteger actual_diff = new HugeInteger('p',output.length());			//int a new array to store the final sum array
		actual_diff.value_sign = diff.value_sign;								//assign array's sign
		
		
		actual_diff.digit_list = new int[output.length()]; 						//copy integer to the array
		for(int i=0; i < output.length(); i++) {
			actual_diff.digit_list[output.length()-1-i] = output.charAt(i)-48;
		}
		
		return actual_diff;

	}
	
/////////////////////////////////////////////////////compare

	public int compareTo(HugeInteger h){
	if(this.value_sign == 'n' && h.value_sign == 'p') {
		return -1;
	}
	else if(this.value_sign == 'p' && h.value_sign == 'n') {
		return 1;
	}
	else if(this.value_sign == 'p') {
		if(this.digit_list.length > h.digit_list.length) {
			return 1;
		}
		else if(this.digit_list.length < h.digit_list.length) {
			return -1;
		}
		else {
			for (int i = this.digit_list.length-1; i>0 ; i--) {
				if(this.digit_list[i] > h.digit_list[i]) {
					return 1;
				}
				if(this.digit_list[i] < h.digit_list[i]) {
					return -1;
				}
			}
			return 0;
		}
	}
	else {
		if(this.digit_list.length > h.digit_list.length) {
			return -1;
		}
		else if(this.digit_list.length < h.digit_list.length) {
			return 1;
		}
		else {
			for (int i = 1; i<this.digit_list.length; i++) {
				if(this.digit_list[i] > h.digit_list[i]) {
					return -1;
				}
				if(this.digit_list[i] < h.digit_list[i]) {
					return 1;
				}
			}
			return 0;
		}
	}
}


/////////////////////////////////////////////////////to string	
	public String toString(){
		String output = new String();
		output = "";
		if (this.value_sign == 'n'
				){
		    output = "-";
		}
        for (int i=this.digit_list.length-1; i>=0;i--) {
        	output= output + this.digit_list[i];
        }
		return output;
	} 
	//test case
	public static void main(String [] arges) {
		
		String a = "56700";
		String b = "-56700";
		String c = "-6700";
		String d = "999";		

		HugeInteger testa = new HugeInteger(a);
		HugeInteger testb = new HugeInteger(b);
		HugeInteger testc = new HugeInteger(c);
		HugeInteger testd = new HugeInteger(d);

		System.out.println(testa.toString());
		System.out.println(testb.toString());
		System.out.println(testc.toString());
		System.out.println(testd.toString());
		
		HugeInteger add_bd = testa.sub(testd);
		System.out.println(add_bd.toString());
		
		HugeInteger add_bc = testc.sub(testd);
		System.out.println(add_bc.toString());
		
		int x = 5;
		HugeInteger testx = new HugeInteger(x);
		System.out.println(testx.toString());
		
		String e = "hello";
		HugeInteger teste = new HugeInteger(e);
		System.out.println(teste.toString());
		
		int y = 0 ;
		HugeInteger testy = new HugeInteger(y);
		System.out.println(testy.toString());
		
		int z = -1 ;
		HugeInteger testz = new HugeInteger(z);
		System.out.println(testz.toString());
	}
}