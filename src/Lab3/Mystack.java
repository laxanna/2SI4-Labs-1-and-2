package Lab3;
public class Mystack <TNode>{
	Node topindex;						//initiate a TNode
	
	//Is empty method
	public boolean isEmpty() {
		return(topindex == null);		//if there is nothing return true, else false
	}
	
	//push () method
	public void push(TNode element) {
		topindex= new Node((Lab3.TNode)element,topindex);	//push element to stack
	}
	
	//top() method
	public TNode top() {
		return (TNode)topindex.element;						//identify the top element
	}
	
	//pop() method
	public TNode pop() {									//pop the index
		if(isEmpty()) {										
			System.out.println("Stack empty");				//print stack Empty when there is nothing
		}
		TNode element = (TNode)topindex.element;			//the next node will move to the top index element
		topindex= topindex.next;
		return element;										//return the element
	}
}
	
	
	
