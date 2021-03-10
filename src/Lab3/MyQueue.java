package Lab3;

public class MyQueue {
	//initiate the node positions
	Node first;		//front Node
	Node last;		//end Node
	
	//when the tree is empty, the last is also the 1st queue
	public MyQueue() {
		first = new Node(null, null);			//they are both null
		last  = first;
	}
	
	//is empty method
	public boolean IsEmpty() {
		if(first == last) {						//compare the position of front and end
			return true;						//if they are the same, return true
		}
		else { 
			return false;						//else, return false
		}
	}
	
	//enqueue method
	public void enqueue(TNode node) {
		last.next = new Node(node, null);		//create an index after the end of the queue 
		last= last.next; 						//the end of the queue is null		
	}
	
	//dequeue method
	public TNode dequeue() {
		if(IsEmpty()) {							//if the queue is empty
			System.out.print("Queue is empty.");//print the queue is empty
		}
		if(last == first.next) {				//if the end is side by side of the front 
			last=first;							//the end is the front
		}
		TNode node = first.next.element;		//initiate a new node to store the second element
		first.next = first.next.next;			//delete the second element
		
		return node;							//return value of the second element
	}

	
}
