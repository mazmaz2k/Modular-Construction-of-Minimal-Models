package Rules;

import java.util.Hashtable;
import java.util.Set;

import Rules.LinkedList.Node;

public class RulesDataStructure extends Rule
{

    public  Rule[] RulesArray ;
    Hashtable<Integer, LinkedList> array ;

    public RulesDataStructure (int numOfRules)
    {
    	RulesArray = new Rule[numOfRules];
    	for (int i = 0; i < RulesArray.length; i++)
    	{
    		RulesArray[i]= new Rule();
		}
    	
    	array = new Hashtable<Integer, LinkedList>();
    }
    
    public void addToRulsArray(int index , int var)
    {
    	if(var==0)//can't be because its checked
    		return;
    	
    	else if(var<0)
    	{
    		var*=-1;
    		RulesArray[index].addToBody(var);
    	}
    	else
    	{
    		RulesArray[index].addToHead(var);
    	} 
		addToHashTable(var,index);

    }
    
    public void printRulesArray()
    {
    	int i;
    	Node tempBody;
    	Node tempHead;
    	for (i = 0; i < RulesArray.length; i++)
    	{
    		System.out.println("RULE NUMBER " + i);
    		System.out.print("\t Body Of Rule : ");
    		tempBody = RulesArray[i].body.head;
    		tempHead =RulesArray[i].head.head;
    		while(tempBody!=null)
    		{
    			System.out.print(tempBody.var);
    			if(tempBody.next!=null)
    				System.out.print(" --> ");
    			tempBody=tempBody.next;
    		}
    		System.out.println("\n");
    		System.out.print("\t Head Of Rule : ");
    		while(tempHead!=null)
    		{
    			System.out.print(tempHead.var);
    			if(tempHead.next!=null)
    				System.out.print(" --> ");
    			tempHead=tempHead.next;
    		}
    		System.out.println("\n");

		}
    }
    
    public void addToHashTable(int var , int ruleIndex) 
    {
    	LinkedList ls;
    	if(array.containsKey(var))//key exist
    	{
    		ls = array.get(var);
    	}
    	else//key does not exist
    	{
    		ls = new LinkedList();
    	}
    	ls.addAtTail(ruleIndex);
		array.put(var,ls ); 	
    }
    
    public void printHashTable() 
    {
    	 Set<Integer> keys = array.keySet();
    	 for(int key: keys)
    	 {
    		 System.out.println("Value of " + key +" is: ");
    		 array.get(key).printList();
    	 }
    }
    
    

	

}
