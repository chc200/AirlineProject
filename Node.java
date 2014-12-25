public class Node<E>{
	String name;
	Node<E> next;
	Edge e;

	Node(String name)
	{
		this.name=name;
		this.next=null;
	}

	Node(Edge e)
	{
		this.e=e;
		this.next=null;
	}

}