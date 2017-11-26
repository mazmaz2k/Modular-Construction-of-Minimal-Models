package Rules;


public class LinkedList
{
	
	public class Node
	{
		//Declare class variables
		Node next;
		Object var;
	
		
		public Node(Object dat)
		{
			var = dat;
		}
	}
	//Class variables for the Linked List
	public  Node head;
	public int numNodes;
	
	
	public LinkedList()
	{
		this.head = null;
		this.numNodes=0;
	}
	
	public void addAtHead(Object dat)
	{
		Node temp = head;
		head = new Node(dat);
		head.next = temp;
		this.numNodes++;
	}
	
	public void addAtTail(Object dat)
	{
		Node temp = head;
		if(temp==null)
		{
			head=new Node(dat);
			this.numNodes++;
			return;
		}
		while(temp.next != null)
		{
			temp = temp.next;
		}
		
		temp.next = new Node(dat);
		this.numNodes++;
	}
	
	public void addAtIndex(int index, Object dat)
	{
		Node temp = head;
		Node holder;
		for(int i=0; i < index-1 && temp.next != null; i++)
		{
			temp = temp.next;
		}
		holder = temp.next;
		temp.next = new Node(dat);
		temp.next.next = holder;
		this.numNodes++;
	}
	
	public void deleteAtIndex(int index)
	{
		Node temp = head;
		for(int i=0; i< index - 1 && temp.next != null; i++)
		{
			temp = temp.next;
		}
		temp.next = temp.next.next;
		this.numNodes--;
	}
		
	public void printList()
	{
		Node temp = head;
		while(temp != null)
		{
			System.out.println(temp.var);
			temp = temp.next;
		}
	}
	
	public int getSize()
	{
		return numNodes;
	}
	
	
	/*public static void main(String [] args)
	{
		System.out.println("/=/=/=/= TESTING /=/=/=/=");
		LinkedList ll = new LinkedList();
		ll.addAtTail(12);
		ll.addAtTail(15);
		ll.addAtTail(14);
		ll.addAtHead(13);

		
		
		ll.printList();
		
	}
	
	*/
	
}
