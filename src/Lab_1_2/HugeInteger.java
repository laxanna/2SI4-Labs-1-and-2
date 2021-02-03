package Lab_1_2;

import java.util.Arrays;
//import the functions from Arrays library

public class HugeInteger {
	private int [] intArr;
	private boolean value_sign;
	
	public HugeInteger(int n) throws ArithmeticException {
		if(n>0) {
			this.intArr = new int [n];
			
			//find if the integer is positive or value_sign
			this.value_sign = Math.random() <0.5;
			
			//leading entry
			double x = Math.random();
			while(x==1 || x == 0) {
				x = Math.random();
			}
			this.intArr[n-1] = (int) (x*10);
			
			//generate the digit list
			for(int i=0; i<n-1; i++) {
				double y = Math.random();
				while (y==1) {
					y = Math.random();
				}
				this.intArr[i]= (int) (y*10);
			}
		}
		else if(n>1000) {
			throw new ArithmeticException ("Value too large");
		}
		else{
			throw new ArithmeticException ("Invalid value"); 
		}
	}
	//constructors
		public HugeInteger(String val) throws ArithmeticException {
			

			//first handle leading value_sign and remove it if it exists
			String newval = "";
			if(val.charAt(0) == '-') {
				this.value_sign = false;
				newval = val.substring(1); //new string without leading value_sign
			}
			else {
				this.value_sign = true;
				newval = val; //same string
			}
			
			//then remove leading zeroes from newval
			newval = newval.replaceFirst("^0+(?!$)", "");
			
			//now check if there are any illegal characters still in string
			if(newval.matches("[0-9]+") == false) {
				throw new ArithmeticException("There are characters other than 0 to 9");
			}
					
			
			//Trivial case if input "0" and "-0".
			if(newval.matches("^[0]")) {
				this.value_sign = true;
			}
			//these trivial cases handles 0 and -0 and 0000 and -0000
			//by turning it all into "0", this.value_sign = false.
			//then it goes through the constructor normally.
			

			
			//now turn processed string into huge integer.
			
			//convert string to chars
			char[] charArr = new char[newval.length()]; //initialize
			newval.getChars(0, newval.length(), charArr, 0);
			
			//now we can copy our stuff to an int array.
			this.intArr = new int[charArr.length]; //initialize
			for(int i=0; i < charArr.length; i++) {
				this.intArr[charArr.length-1-i] = charArr[i];
			}
			
			
			
			//Notice that the numbers are still encoded,
			//eg 0 -> 48, 1 -> 49 etc.
			//shift everything down by 48.
			for(int i=0; i < this.intArr.length; i++) {
				this.intArr[i] = this.intArr[i] - 48;
			}
			
			//now we have a sanitized HugeInteger in intArr.
			//they are of type integer which means you can do
			//arithmetic operations
			
			
		}
	public HugeInteger(int value, boolean sign, int digits) throws ArithmeticException {
		
		//if less than 0 length
		if(digits<0) {
			throw new ArithmeticException("An integer can't have value_sign length, additional constructor");
		}
		else if(digits>100000) {
			throw new ArithmeticException("That is like way too long, above 100000 digits in length, additional constructor");
		}
		else if(value<0) {
			throw new ArithmeticException("Value can't be value_sign. Use the boolean for that, additional constructor");
		}
		else {
			this.value_sign =sign;
			
			intArr = new int[digits];
			Arrays.fill(intArr, value);
		}
	}
	
	public HugeInteger add(HugeInteger h) {
		
		//basically just use long addition with carry overs
		//starting in ones place and going to millionths etc
		
		//what if this + h makes a bigger array?
		//have the sum array be 1 bigger than the bigger of the two
		//then make another final array, with no unwanted empty spaces. 
		
		//if none are value_sign
		if(this.value_sign == true && h.value_sign == true) {
			
			//dont need to have cases for which one is bigger.
			//just detect the size of the biggest, and make the sum
			//array one bigger.
			int arrMax = Math.max(this.intArr.length, h.intArr.length);
			
			//initialize a large sum array with allowance for overflow
			HugeInteger sum = new HugeInteger(0,true,arrMax+1);

			//This is positive only.
			sum.value_sign = this.value_sign;
			
			
			//copy this into sum
			for(int i=0; i<this.intArr.length; i++) {
				sum.intArr[i] = this.intArr[i];
			}
			
			//now do long addition with h
			int carry = 0;
			for(int i=0; i<sum.intArr.length; i++) {
				
				int intSum = carry;
				
				if(i < this.intArr.length) {
					intSum = intSum + this.intArr[i];
				}
				if(i < h.intArr.length) {
					intSum = intSum + h.intArr[i];
				}
				
								
				if(intSum >= 10) {
					carry = 1;
				}
				else {
					carry = 0;
				}
				
				int remainder = intSum % 10;
				
				sum.intArr[i] = remainder;
			}
			
			
			
			//now copy to string
			String output = new String();
			for(int i=sum.intArr.length-1; i>=0; i--) {
				output = output + sum.intArr[i];
			}

			//do leading zero removal 
			output = output.replaceFirst("^0+(?!$)", "");
			
			//initialize a new final array
			HugeInteger sanitized = new HugeInteger(0,true,output.length());
			
			//copy string into a final array.
			//convert string to chars
			char[] charArray = new char[output.length()]; //initialize
			output.getChars(0, output.length(), charArray, 0);
			
			//now we can copy our stuff to an int array.
			sanitized.intArr = new int[charArray.length]; //initialize
			for(int i=0; i < charArray.length; i++) {
				sanitized.intArr[charArray.length-1-i] = charArray[i];
			}
			
			//Notice that the numbers are still ascii encoded,
			//eg 0 -> 48, 1 -> 49 etc.
			//shift everything down by 48.
			for(int i=0; i < sanitized.intArr.length; i++) {
				sanitized.intArr[i] = sanitized.intArr[i] - 48;
			}
			
			
			return sanitized;
		}

		//if this only is value_sign
		else if(this.value_sign == true && h.value_sign == false){
			; //deal with this in lab 2
			//call subtract
			return h;
		}
		//if h only is value_sign
		else if(this.value_sign == false && h.value_sign == true ){
			; //deal with this in lab 2
			//call subtract
			return h;
		}
		
		//if both are value_sign
		else {
			; //deal with this in lab 2
			//use positive addition for double value_signs.
			//then just say value_sign = true.
			return h;
		}
		

	}
//test cases
	public String toString(){
		String output = new String();
		output = "";
		if (this.value_sign == false){
		    output = "-";
		}
        for (int i=this.intArr.length-1; i>=0;i--) {
        	output= output + this.intArr[i];
        }
		return output;
	} 
	public static void main(String [] arges) {
		
		String a = "-5000";
		String b = "-0050";
		String c = "50000";
		String d = "00005";
		

		HugeInteger testa = new HugeInteger(a);
		HugeInteger testb = new HugeInteger(b);
		HugeInteger testc = new HugeInteger(c);
		HugeInteger testd = new HugeInteger(d);

		System.out.println(testa.toString());
		System.out.println(testb.toString());
		System.out.println(testc.toString());
		System.out.println(testd.toString());
		
		HugeInteger add_cd = testc.add(testd);
		System.out.println(add_cd.toString());
		
		String e = "hello";
		HugeInteger teste = new HugeInteger(e);
		System.out.println(teste.toString());
		
		int x = -1;
		int y = 0 ;
		int z = 5 ;
		
		HugeInteger testx = new HugeInteger(x);
		HugeInteger testy = new HugeInteger(y);
		HugeInteger testz = new HugeInteger(z);
		
		System.out.println(testx.toString());
		System.out.println(testy.toString());
		System.out.println(testz.toString());
	}
}

