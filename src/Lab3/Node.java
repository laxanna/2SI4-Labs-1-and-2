package Lab3;

//create an extra class for Node structure
public class Node {
	TNode element;					//call TNode structure
	Node next;						//initiate a Node next pointer
	Node(TNode i, Node n){			//A function to create a Node
		element = i;				
		next = n;
	}
}
