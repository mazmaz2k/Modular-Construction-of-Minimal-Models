package Rules;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

//import Graph.LinkedList1;
//import Graph.LinkedList1.Node;

import Rules.LinkedList.Node;
//import Rules.LinkeList;

public class RulesDataStructure extends Rule
{

    public  Rule[] RulesArray ;
    Hashtable<Integer, LinkedList> array ;
    
    HashMap<Integer, Boolean> literalMap;// We will store the value of literals in this structure as we go along

    public RulesDataStructure (int numOfRules)
    {
    	RulesArray = new Rule[numOfRules];
    	for (int i = 0; i < RulesArray.length; i++)
    	{
    		RulesArray[i]= new Rule();
		}
    	
    	array = new Hashtable<Integer, LinkedList>();
    	
    	literalMap = new HashMap<Integer,Boolean>();
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
		//need to check if the same variable appears more than once in the same clause

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
    
    public boolean existInBody(int var, int ruleNum)
    {
    	
    	Node n;
    	n = RulesArray[ruleNum].body.head;
    	while(n!=null)
    	{
    		if(n.var == var)
    			return true;
    		n=n.next;
    	}
    	return false;
    }
    
    public boolean existInHead(int var , int ruleNum)
    {
    	Node n;
    	n = RulesArray[ruleNum].head.head;
    	while(n!=null)
    	{
    		if(n.var == var)
    			return true;
    		n=n.next;
    	}
    	return false;
    }
    
    public boolean conflictExist(int var ,boolean b)
    {
    	LinkedList l = array.get(var);
    	if(exist(var))
    	{
    		Node n = l.head;
    		while(n!=null)
    		{
    			int sizeOfBody, sizeOfHead;
    			sizeOfBody = RulesArray[n.var].body.getSize();
    			sizeOfHead = RulesArray[n.var].head.getSize();
    			if( (existInBody(var,n.var )) && sizeOfBody==1 && b &&sizeOfHead==0)
    			{
    				return true;
    			}
    			else if((existInHead(var,n.var)) && sizeOfHead==1 && !b &&sizeOfBody==0)
    			{
    				return true;
    			}
    			else
    			{
    				return false;
    			}
    		}		
    	}
    	return false;   	
    }
    
    
    public boolean exist(int var)
    {
    	LinkedList l = array.get(var);
    	if(l==null)
    	{
    		return false;
    	}
    	return true;
    }
    
    public void placeValue(int var , boolean value)
    {
    	if(conflictExist(var, value))
    	{
    		System.out.println("CONFLICT");
    		return ;
    	}
    	if(!exist(var))
    	{
    		System.out.println("VARIABLE NOT EXIST");
    		return ;
    	}
    	LinkedList l = array.get(var);
    	Node n = l.head;
    	while(n!=null)
    	{
    		if(existInBody(var, n.var)&& !value)
    		{
    			deleteRule(n.var);
    			System.out.println("DELETE RULE NUMBER " + n.var);
    		}
    		else if((existInBody(var, n.var)&& value))
    		{
    			deleteVarFromBody(var,n.var);
    			System.out.println( "DELETE VARIABLE " + var + " IN RULE " + n.var);
    		}
    		else if(existInHead(var, n.var)&& value)
    		{
    			deleteRule(n.var);
    			System.out.println("DELETE RULE NUMBER " + n.var);
    		}
    		else if (existInHead(var, n.var)&& !value)
    		{
    			deleteVarFromHead(var,n.var);
    			System.out.println("DELETE VARIABLE "+var+" IN RULE " + n.var);
    		}
    		
    		n=n.next;
    		
    	}
    	return;
    	
    }
    
    
    private void deleteRule(int ruleNum)
    {
    	updateHT(0,ruleNum);
    	RulesArray[ruleNum].body.deleteList();
    	RulesArray[ruleNum].head.deleteList();
    }
    
    private void deleteVarFromBody(int var, int ruleNum)
    {
    	updateHT(var, ruleNum);
    	LinkedList l = RulesArray[ruleNum].body;
    	int index = 0;
    	Node n = l.head;
    	while(n!=null)
    	{
    		if(n.var==var)
    		{
    			l.deleteAtIndex(index);
    			break;
    		}
    		index++;
    		n=n.next;
    	}
    }
    private void deleteVarFromHead(int var, int ruleNum)
    {
    	updateHT(var, ruleNum);
    	LinkedList l = RulesArray[ruleNum].head;
    	int index = 0;
    	Node n = l.head;
    	while(n!=null)
    	{
    		if(n.var==var)
    		{
    			l.deleteAtIndex(index);
    			break;
    		}
    		index++;
    		n=n.next;
    	}
    }
    
    private void updateHT(int var , int ruleNum)
    {
    	if(var==0)//from deleteRule method
    	{
    		
    		Node bo = RulesArray[ruleNum].body.head;
    		Node he = RulesArray[ruleNum].head.head;
    		LinkedList list;
    		int index;
    		while(bo!=null)
    		{
    			index = 0;
    			list = array.get(bo.var);
    			Node n = list.head;
    			while(n!=null)
    			{
    				if(n.var==ruleNum)
    				{
    					list.deleteAtIndex(index);
    					break;
    				}
    				index++;
    				n=n.next;
    			}
    			bo=bo.next;
    		}
    		while(he!=null)
    		{
    			index = 0;
    			list = array.get(he.var);
    			Node n = list.head;
    			while(n!=null)
    			{
    				if(n.var==ruleNum)
    				{
    					list.deleteAtIndex(index);
    					break;
    				}
    				index++;
    				n=n.next;
    			}
    			he=he.next;
    		}
    		

    		
    		
    	}
    	else
    	{
    		LinkedList list = array.get(var);
    		Node n = list.head;
    		int index =0;
    		while(n!=null)
    		{
    			if(n.var==ruleNum)
    			{
    				list.deleteAtIndex(index);
    				break;
    			}
    			index++;
    			n=n.next;
    		}
    	}
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

	

}
