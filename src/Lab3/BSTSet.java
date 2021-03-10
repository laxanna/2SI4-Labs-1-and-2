package Lab3;

public class BSTSet{
	
	//construction method 1
	private TNode root;													//BST root node
	public BSTSet() {
		root = null;													//Initialize empty set
	}
	
	//additional function to remove unwanted integers in the initial array:
	public int[] Sort(int[] input) {
		//sort descending array staring from the root
		int l = input.length;
		for(int current = 1; current<l; ++current) {					//sort starting from the second value of the array because the 1 index is the root 
			int temp = input[current];									//Initiate temp to hold the input value of the current index
			int previous = current-1;									//Initiate previous to hold the previous index value
			
			while(previous >= 0 && input[previous] > temp) {			//while the previous input is greater then the current input in the list
				input[previous + 1] = input[previous];					//the previous input is being shift to the next index
				previous=previous-1;									//the previous index update to the next index
			}
			input[previous + 1] = temp;									//the current input being push in order of descending 
		}
		//fine the total number of not duplicate arrays
		int temp = input[input.length-1];								// staring from the last index of the array looping back to the front array and the rest
		int counter = 0;
		for(int i = 0;  i< input.length; i++) {
			if(input[i]!= temp) {										//if the previous is not equivalent to the current one
				counter ++;												//count up
			}
			temp = input[i];											//looping back to the front and moving forward
		}
		// implement the values in order into a new array with no duplicate
		int[] sort_arr = new int[counter];
		temp = input[input.length-1];									//started from the last index of the array looping back to the front array and the rest
		counter = 0;
		for(int i=0; i<input.length; i++) {
			if (input[i] != temp) {										//if the previous is not equivalent to the current one
				sort_arr[counter] = input[i];							//insert the input index to the new array				
				counter++;												//count up
			}
			temp = input[i];											//looping back to the front and moving forward
		}
		return sort_arr;
	}
	
	//additional function to make new node for the Tree through recursive:
	public TNode generator(int[] input, int s, int e) {
		// Base case, no node in the middle of the search
		if (s > e) {													//if the start index is greater than the last index (aka no index in between)
			return null;
		}
		//Make median index of the array to become the root and add child to it through recursive
		int median = (s+e)/2;
		TNode new_node = new TNode(input[median],null, null);			//start from the median and insert the value into the node
		new_node.left = generator(input, s, median - 1);				//use recursive for the left child
		new_node.right = generator(input, median + 1, e);				//use recursive for the right child
		return new_node;
	}
	
	//construction method 2
	public BSTSet(int[] input) {
		if (input.length == 0) {										//if the array is empty 
			root = null;												//the root is empty
		} else {	
			int[] sort_arr = Sort(input);								//sort the array to get rid of duplicates and descending
			root = generator(sort_arr, 0, sort_arr.length - 1);			//make the BSTSet using node and array recursive generator
		}	
	}
	
//1)
	//function to compare the value recursively throughout the tree
	public boolean compare(TNode node, int v){	
		//return false if there are no element in the tree that is equal to v
		if(node == null) {
			return false;	
		}
		//return true if there is an element in the tree list
		if(node.element == v) {
			return true;
		}
		//Recursive true if either the left or the right node matches
		boolean left = compare(node.left, v);					//check the left side 1st
		if(left == true){
			return true;										//return true if matches v 
		}
		boolean right = compare(node.right, v);					//check the right side 2nd
		return right;											//return true if matches v	
	}
	
	//Check if integer v is in the Tree list
	public boolean isIn(int v) {
		if (root == null) {										//return false if the node is null
			return false;
		}
		return (compare(root, v));								//the compare recursive method to check if the value v is in the Tree or not
	}
		
//2)
	//function to add the values recursively 
	public TNode Add_check(TNode node, int v) {
		
		if (node == null) {										//if the node does not exist yet
			node = new TNode(v, null, null);					//create new node and and v value inside it
		}
		if (v < node.element)									//if v is less then node's value check the left side
			node.left = Add_check(node.left, v);
		else if (v > node.element)								//if v is more than node's value check the right right
			node.right = Add_check(node.right, v);				
		// Return the node when finish recursion and when the element is already there
		return node;
	}
	
	//Add integer v into the Tree
	public void add(int v) {
		if (root == null) {										// If empty tree
			root = new TNode(v, null, null);					//create new node and and v value inside it
		}
		else {
			Add_check(root, v);									//goes through the addition recursion to add the node
		}
	}
	
//3)
	// 2 functions for remove using recursion
	//function 1 find the maximum element for later implementation for looking for the largest element on the left hand side for deletion
	public int Max(TNode node) {
		int maximum = node.element;								//initiate initial node value
		while (node.right != null) {							//while the most right hind side is not null
			maximum = node.right.element;						//update the maximum value
			root = node.right;									//update the right hand side node
		}
		return maximum;											//return the maximum value
	}
	//function 2
	public TNode Remove_check(TNode node, int v) {
		// if empty
		if (node == null) {										//if the node does not exist yet
			return node;										//return the BSTSet
		}

		// Travel down the tree 
		if (v < node.element) {									//if Integer v is less than the node
			node.left = Remove_check(node.left, v);				//recursive Remove that travel down the left side of the node
		} else if (v > node.element) {							//if Integer v is greater than the node
			node.right = Remove_check(node.right, v);			//recursive Remove travel down the right side of the node
		}
		else {													//when the delete node is reached
			// Single parent node
			if (node.left == null) {							//if there are no left node
				return node.right;								//return right hand side as the node
			} 
			else if (node.right == null) {						//if there are no right node
				return node.left;								//return left hand side node
			}
			//Deletion
			node.element = Max(node.left);						// Get the Largest value in the left side of the tree to delete
			node.left = Remove_check(node.left, node.element);	//delete by replacing
		}
		return node;
	}	
	
	//Remove integer v from the tree
	public boolean remove(int v) {
		if (isIn(v) == false) {			//Return false if there is no v integer in the tree
			return false;
		} else {						//If v integer is there in the tree
			Remove_check(root, v);		//Remove the node via recursion
			return true;				//And return true
		}
	}
	
//4)
	//function for union via add method and recursion
	public void Union(BSTSet s, TNode node) {
		if(node != null) {				//when there are values in the node
			s.add(node.element);		//Use add function to add the element of the SET as a new node
			Union(s,node.right);		//do recursion of Union on the right side
			Union(s,node.left);			//do recursion of Union on the left side
		}
	}
	
	//Union of two different sets
	public BSTSet union(BSTSet s) {
		BSTSet all = new BSTSet();		//initiate a null Set to add the Union values
		Union(all, this.root); 			//add nodes from the original set to the null Set by calling Union recursion
		Union(all, s.root);				//add nodes from the s set to the new set by calling Union recursion
		return all;						//return the BSTSet
	}
	
	
//5)
	//function for intersection using add method and recursion
	public void intersection_repeat(BSTSet s, TNode node) {
		if(node != null) {						//when there are values in the node
			if(isIn(node.element)) {			//if the element exist in the other Set
				s.add(node.element);			//Adding the element to the new set
			}
			intersection_repeat(s,node.left); 	//Adding the left side element to the new Set if exist on both Set
			intersection_repeat(s,node.right); 	//Adding the right side element to the new Set if exist on both Set
		}
	}
	
	//Intersection of two different sets
	public BSTSet intersection(BSTSet s) {
		BSTSet cross = new BSTSet();			//initiate a new Set
		intersection_repeat(cross, s.root);		//calling the intersection recursion method to add values to the new set
		return cross;
	}
	
//6)
	//function for intersection using add method and recursion
		public void difference_repeat(BSTSet fin, BSTSet s, TNode node) {
			if(node != null) {						//when there are values in the node
				if(s.isIn(node.element)) {			//if the element exist in the other Set
				}
				else{								//if not
					fin.add(node.element);			//Adding the element to the new set, nothing is added
				}
				difference_repeat(fin,s,node.left); //Adding the left side element to the new Set if does not exist on the other Set
				difference_repeat(fin,s,node.right); //Adding the right side element to the new Set if does not exist on the other Set
			}
		}
		
	//Difference of two different set
	public BSTSet difference(BSTSet s) {
		BSTSet different = new BSTSet();			//initiate a new Set
		difference_repeat(different,s,root);		//calling the difference recursion method to add value to the new set
		return different;
	}
	
//7)
	//function used to find size of the Tree using recursion
	public int count_size(TNode node, int count) {
		if (node == null) {							//do nothing when the node is null
		}
		else {
			count++;								//if there is something in the tree's node, count it up
			count = count_size(node.left, count);	//recursion for counting on the left size
			count = count_size(node.right, count);	//recursion for counting on the right size
		}
		return count;
	}
	
	//The size of the Tree
	public int size() {
		if(root==null) {	
			return 0;	 				//return 0 if there is nothing
		}
		else {
			return(count_size(root,0));	//return the total size of the Tree using the recursion
		}
	}
	
//8)
	//function used to find height of the Tree using recursive
	public int total_height(TNode node) {

		if (node == null) {								//if there is no height, return -1
			return -1;
		} else {
			int left_h = total_height(node.left);		//keep calling recursive height until hit null 1+1+...+1+(-1) on the left size
			int right_h = total_height(node.right);		//keep calling recursive height until hit null 1+1+...+1+(-1) on the right size

			if (left_h <right_h) {						//compare the two height
				return (right_h + 1);					//add 1 to right if right is deeper
			} else {
				return (left_h + 1);					//add 1 to left if left is deeper
			}
		}		
	}
	
	//The height of the Tree
	public int height() {
		return (total_height(root)); 					//calculate the high starting from the root
	}
	
//9)a)
	public void printBSTSet() {
		if(root == null) {
			System.out.println("The set is empty");
		}
		else {
			System.out.print("The set element are: ");
			printBSTSet(root);
			System.out.print("\n");
		}
	}
	private void printBSTSet(TNode t) {
		if(t!=null) {
			printBSTSet(t.left);
			System.out.print(" " + t.element + ", ");
			printBSTSet(t.right);
		}
	}
	
//9)b)
	//Use Mystack to print the tree in descending-order 
	public void printNonRec() {
		System.out.print("Elements are: ");		// Structure to prints the integers in this BSTSet in increasing order.  	
		MyStack stack = new MyStack;			//create an empty stack array
		TNode node = root;						//create a new root
		//Create an infinite while loop to go through the tree
		while(true) {
			while(node != null) {				//while the node is not empty
				stack.push(node);				//call stack to push the value of the node into the stack array
				node = node.left;				//move downward to the left node
			}
		}
		
		if(stack.IsEmpty()){					//if the stack array is empty, break
			break;								//stop the while loop
		}
		
		node=stack.pop(); 						//go back to the parent node
		
		System.out.print(node.element + ", " );	//print the elements of the node
		node=node.right; 						//move to the right side of the node after finishing printing the element
	}
//10)
	//use MyQueue class to print the tree in the level order
	public void printLevelOrder() {
		TNode new_node = root;									//initiate a node type.
		
		if(new_node!= null) {									//when the root has values
			System.out.print("Elements are: ");					//structure to prints the integers in this BSTSet in tree level order.
			MyQueue queue = new MyQueue();						//create an empty queue array
			//a while loop to go tree array and make sure it is empty
			while(!queue.IsEmpty()) {
				TNode node = queue.dequeue();					//dequeue a node
				System.out.print(node.element + ", ");			//print out the dequeue node
				
				if (node.right != null) {						//check the right node if it is holding a value
					queue.enqueue(node.right);					//move the right node to become the check node
				}
				
				if (node.left != null) {						//check the left node if it is holding a value
					queue.enqueue(node.left);					//move the left node to become the check node
				}
			}
		}
		else {													//if the tree is empty
			System.out.print("Binary tree is empty"); 			//Print a line to indicate there is nothing in the tree
		}
	}
	
// Get root
	public TNode getRoot() {
		return root;
	}
}