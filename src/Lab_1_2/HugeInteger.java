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
//////////additional constructor to help with addition 
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
			
			sum.value_sign = h.value_sign;									//the sign should be the same as other number and be positive									
			
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
					index_sum = index_sum-10;
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
					if(index_sum<0) {
						index_sum += 10;
						carry_out = -1;
					}
					else {
						carry_out = 0;
					}
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
		
		//Trivial case if input "0" and "-0".
		if(output.matches("^[0]")) {
			actual_sum.value_sign = 'p';
		}
				
		actual_sum.digit_list = new int[output.length()]; 						//copy integer to the array
		for(int i=0; i < output.length(); i++) {
			actual_sum.digit_list[output.length()-1-i] = output.charAt(i)-48;
		}
		
		return actual_sum;

	}

//////////////////////////////////////////////////////sub
	
	public HugeInteger subtract(HugeInteger h) {
		
		int diff_len = Math.max(this.digit_list.length, h.digit_list.length); 	//make a new array using the one have bigger length
		HugeInteger diff = new HugeInteger('p',diff_len+1);						//initialize a large sum array with allowance for overflow

		if(this.value_sign != h.value_sign) {									//when both number is different sign
			
			diff.value_sign = this.value_sign;									//the sign should be the same as other number and be positive									
			
			//now do long addition with h
			int carry_out = 0;
			for(int i=0; i<diff.digit_list.length; i++) {
				
				int index_diff = carry_out;
				
				if(i < this.digit_list.length) {
					index_diff = index_diff + this.digit_list[i];
				}
				if(i < h.digit_list.length) {
					index_diff = index_diff + h.digit_list[i];
				}
								
				if(index_diff >= 10) {
					carry_out = 1;
				}
				else {
					if(index_diff<0) {
						index_diff += 10;
						carry_out = -1;
					}
					else {
						carry_out = 0;
					}
				}
				
				int remainder = index_diff % 10;
				diff.digit_list[i] = remainder;
			}
		}

		else {																	//same sign
			
			HugeInteger big = new HugeInteger('p', 1);							//place holder for bigger absolute number 
			HugeInteger small = new HugeInteger('p', 1);						//					smaller absolute number
			
			if(this.digit_list.length>h.digit_list.length) {
				big = this;
				small = h;
				diff.value_sign = big.value_sign;								//the sign should be the same as other number 								
			}
			else if(h.digit_list.length>this.digit_list.length) {
				big = h;
				small = this;
				if(h.value_sign == 'n') {
					diff.value_sign = 'p';										//the sign should be the opposite as other numbers and be positive									
				}
				else {
					diff.value_sign ='n';
				}
			}
			else {
				int i=0;
				for(i= this.digit_list.length; i>0; i--) {
					if(this.digit_list[i-1]>h.digit_list[i-1]) {
						big = this;
						small = h;
						diff.value_sign = big.value_sign;						//the sign should be the same as other number 				
						break;
					}
					if(this.digit_list[i-1]<h.digit_list[i-1]) {
						big = h;
						small = this;
						if(h.value_sign == 'n') {
							diff.value_sign = 'p';										//the sign should be the opposite as other numbers and be positive									
						}
						else {
							diff.value_sign ='n';
						}
						break;
					}
				}
				if(i==0) {
					big = h;
					small = h;
					diff.value_sign = 'p';										//the sign should be positive				
				}
			}
			
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
					if(index_diff<0) {
						index_diff += 10;
						carry_out = -1;
					}
					else {
						carry_out = 0;
					}
				}
				diff.digit_list[i]= index_diff;
			}
		}
		
		String output = new String();											//copy the sum array to output array to turn it into wanted number
		for(int i=diff.digit_list.length-1; i>=0; i--) {
			output = output + diff.digit_list[i];
		}
		
		output = output.replaceFirst("^0+(?!$)", "");							//remove leading zeros
		
		//Trivial case if input "0" and "-0".
		if(output.matches("^[0]")) {
			diff.value_sign = 'p';
		}
		
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
			for (int i = this.digit_list.length-1; i>=0 ; i--) {
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
			for (int i=this.digit_list.length-1; i>=0; i--) {
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
//////multiply length under
	
public HugeInteger multiply_under10(HugeInteger h) {
	HugeInteger mul = new HugeInteger('p',this.digit_list.length+h.digit_list.length);
	
	int track=0;
	
	for(int i=0; i<this.digit_list.length; i++, track++) {
		int carry_over = 0;
		HugeInteger sub_mul = new HugeInteger('p',track + h.digit_list.length+1);
		for(int j=0; j<h.digit_list.length+1; j++) {
			int index_mul = carry_over;
			if(j<h.digit_list.length) {
				index_mul += (this.digit_list[i] * h.digit_list[j]);
			}
			if(index_mul>=10) {
				carry_over = (index_mul-(index_mul%10))/10;
			}
			else {
				carry_over = 0;
			}
			sub_mul.digit_list[track+j] = index_mul%10;
		}
		mul = mul.add(sub_mul);
	}
	
	String output = new String();											//copy the sum array to output array to turn it into wanted number
	for(int i=mul.digit_list.length-1; i>=0; i--) {
		output = output +mul.digit_list[i];
	}
	
	output = output.replaceFirst("^0+(?!$)", "");							//remove leading zeros
	
	if(h.value_sign == this.value_sign) {
		mul.value_sign = 'p';
	}
	else {
		mul.value_sign = 'n';
	}
	HugeInteger actual_mul= new HugeInteger('p',output.length());			//int a new array to store the final sum array
	actual_mul.value_sign = mul.value_sign;								//assign array's sign
	
	
	actual_mul.digit_list = new int[output.length()]; 						//copy integer to the array
	for(int i=0; i < output.length(); i++) {
		actual_mul.digit_list[output.length()-1-i] = output.charAt(i)-48;
	}

	return actual_mul; //long multiplication
	
}
	
	
/////////////////////////////////////////////////////multiply

public HugeInteger multiply(HugeInteger h){
	
	if(h.digit_list.length == 1 && h.digit_list[0] == 0) {
		HugeInteger value_l1 = new HugeInteger('p',1);	
		return value_l1;
	}
	
	if(this.digit_list.length == 1 &&  this.digit_list[0] == 0) {
		HugeInteger value_l1 = new HugeInteger('p',1);
		return value_l1;
	}
	
	int M = Math.max(h.digit_list.length, this.digit_list.length);
	if(h.digit_list.length<10 || this.digit_list.length<10) {
		
		return this.multiply_under10(h); //long multiplication
	}
	
	int N = (M/2) + (M%2);

	//Initiate each calculation term for Karatsuba method
	
	
	if(this.digit_list.length-N<0 || h.digit_list.length-N<0) {
		return this.multiply_under10(h); //long multiplycation
	}
	
	HugeInteger b = new HugeInteger(this.value_sign,this.digit_list.length-N);
	for(int i = 0; i<b.digit_list.length; i++) {
		b.digit_list[i] = this.digit_list[N+i];
	}	
	
	HugeInteger a_raw = new HugeInteger(this.value_sign,N+1);
	for(int i = 0; i<N; i++) {
		a_raw.digit_list[i] = this.digit_list[i];
	}
	
	HugeInteger d = new HugeInteger(h.value_sign,h.digit_list.length-N);
	for(int i = 0; i<d.digit_list.length; i++) {
		d.digit_list[i] = h.digit_list[N+i];
	}
	
	HugeInteger c_raw = new HugeInteger(h.value_sign,N+1);
	for(int i = 0; i<N; i++) {
		c_raw.digit_list[i] = h.digit_list[i];
	}

	//get ride of leading zeros in a
	String output1 = new String();											//copy the sum array to output array to turn it into wanted number
	for(int i=a_raw.digit_list.length-1; i>=0; i--) {
		output1 = output1 +a_raw.digit_list[i];
	}
	
	output1 = output1.replaceFirst("^0+(?!$)", "");							//remove leading zeros
	
	HugeInteger a = new HugeInteger(a_raw.value_sign,output1.length());	
	
	a.digit_list = new int[output1.length()]; 						//copy integer to the array
	for(int i=0; i < output1.length(); i++) {
		a.digit_list[output1.length()-1-i] = output1.charAt(i)-48;
	}

	
	//get_rid off leading 0s in c
	String output2 = new String();											//copy the sum array to output array to turn it into wanted number
	for(int i=c_raw.digit_list.length-1; i>=0; i--) {
		output2 = output2 +c_raw.digit_list[i];
	}
	
	output2 = output2.replaceFirst("^0+(?!$)", "");							//remove leading zeros
	
	HugeInteger c= new HugeInteger(c_raw.value_sign,output2.length());	
	
	c.digit_list = new int[output2.length()]; 						//copy integer to the array
	for(int i=0; i < output2.length(); i++) {
		c.digit_list[output2.length()-1-i] = output2.charAt(i)-48;
	}

	
	//calculate 1st term
	HugeInteger z0 = a.multiply(c);
	
	//calculate 3rd term
	HugeInteger z2 = b.multiply(d);
	
	//calculate 2nd term
	HugeInteger z1_sub3 = (a.add(b)).multiply(c.add(d));
	HugeInteger z1 = (z1_sub3.subtract(z0)).subtract(z2);
	
	//times by 10^N
	HugeInteger z1_term = new HugeInteger(z1.value_sign,z1.digit_list.length+N);
	for(int i = 0; i<z1.digit_list.length; i++) {
		z1_term.digit_list[N+i] = z1.digit_list[i];
	}
	
	//times by 10^(N*2)
	HugeInteger z2_term = new HugeInteger(z2.value_sign,z2.digit_list.length+(N*2));
	for(int i = 0; i<z2.digit_list.length; i++){
		z2_term.digit_list[N*2+i]=z2.digit_list[i];
	}
	
	//add all terms
	HugeInteger z = (z0.add(z1_term)).add(z2_term);
	//HugeInteger z = d;
	return z;
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
		
		String a = "100";
		String b = "-90";
		String  d= "2322123232";
		String c ="-2032334563298332";		
								//1928
		HugeInteger testa = new HugeInteger(a);
		HugeInteger testb = new HugeInteger(b);
		HugeInteger testc = new HugeInteger(c);
		HugeInteger testd = new HugeInteger(d);

		//System.out.println(testa.toString());
		//System.out.println(testb.toString());
		//System.out.println(testc.toString());
		//System.out.println(testd.toString());
		
		HugeInteger add_ab = testc.add(testd);
		//System.out.println(add_ab.toString());
		
		HugeInteger mul_cd = testd.multiply(testc);
		System.out.println(mul_cd.toString());
		
		HugeInteger mul_cd_true = testd.multiply_under10(testc);
		System.out.println(mul_cd_true.toString());
		
		int x = 5;
		HugeInteger testx = new HugeInteger(x);
		//System.out.println(testx.toString());
		
		String e = "hello";
		HugeInteger teste = new HugeInteger(e);
		//System.out.println(teste.toString());
		
		int y = 0 ;
		HugeInteger testy = new HugeInteger(y);
		//System.out.println(testy.toString());
		
		int z = -1 ;
		HugeInteger testz = new HugeInteger(z);
		//System.out.println(testz.toString());
	}
}