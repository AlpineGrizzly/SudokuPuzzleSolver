
public class MyStack<E> {
	/*Head of stack*/
	private Node<E> topPtr; 
	/*Initilize head to null */
	public MyStack() {
		topPtr = null;
	}
	/*Node class */
	public class Node<E> {
		E element;
		Node<E> next;
		public Node(E e) { 
			this.element = e;
			next = null;
		}
		
	}
	/*push the value onto the stack*/
	public void push(E e) {
		Node<E> node = new Node<E>(e);		
		
		node.next = topPtr;
		topPtr = node;
		

}
	/*Pop the value off the stack */
	public E pop() {

		if(topPtr == null) {
			return null;
		} else {
			E e = topPtr.element;
			topPtr = topPtr.next;
			return e;
		}
	}
	

	
}
