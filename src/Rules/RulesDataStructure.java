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

    public Rule[] RulesArray ;
    Hashtable<Integer, LinkedList> varHT ;
    Hashtable<Integer, Boolean> valueTable;// We will store the value of literals in this structure as we go along

    public RulesDataStructure (int numOfRules)
    {
    	RulesArray = new Rule[numOfRules];
    	for (int i = 0; i < RulesArray.length; i++)
    	{
    		RulesArray[i]= new Rule();
		}
    	
    	varHT = new Hashtable<Integer, LinkedList>();
    	
    	valueTable = new Hashtable<Integer,Boolean>();
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
    	if(varHT.containsKey(var))//key exist
    	{
    		ls = varHT.get(var);
    		if(VariableExistInLinkedList(ruleIndex,ls))
    		{
    			return;
    		}
    		ls.addAtTail(ruleIndex);
    		varHT.put(var,ls); 		
    	}
    	else//key does not exist
    	{
    		ls = new LinkedList();
    		ls.addAtTail(ruleIndex);
    		varHT.put(var,ls); 	
    	}
    	
    }
    public boolean VariableExistInLinkedList(int var,LinkedList l)
    {
    	Node n = l.head;
    	while(n!=null)
    	{
    		if(n.var==var)
    		{
    			return true;
    		}
    		n=n.next;
    	}
    	return false;
    }
    
    public void printHashTable() 
    {
    	 Set<Integer> keys = varHT.keySet();
    	 for(int key: keys)
    	 {
    		 System.out.println("Value of " + key +" is: ");
    		 varHT.get(key).printList();
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
    	LinkedList l = varHT.get(var);
    	if(variableExist(var))
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
    
    
    public boolean variableExist(int var)
    {
    	LinkedList l = varHT.get(var);
    	if(l==null)
    	{
    		return false;
    	}
    	return true;
    }
    
    public void ChangeDataStrucureByPlacingValueInVar(int var , boolean value)
    {
    	if(conflictExist(var, value))
    	{
    		System.out.println("CONFLICT");
    		return ;
    	}
    	if(!variableExist(var))
    	{
    		System.out.println("VARIABLE NOT EXIST");
    		return ;
    	}
    	LinkedList l = varHT.get(var);
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
    			list = varHT.get(bo.var);
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
    			list = varHT.get(he.var);
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
    		LinkedList list = varHT.get(var);
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
    
    
    
    public void checkForUnits()
    {
    	for (int i = 0; i < RulesArray.length; i++)
    	{
			if((RulesArray[i].body.getSize() + RulesArray[i].head.getSize())==1)
			{
				if(RulesArray[i].body.getSize() ==1)//body size is 1 and head size is 0s
				{
					valueTable.put(RulesArray[i].body.head.var, false);
					ChangeDataStrucureByPlacingValueInVar(RulesArray[i].body.head.var, false);
					
				}
				else//head size is 1 and body size is 0
				{
					valueTable.put(RulesArray[i].head.head.var, true);

					ChangeDataStrucureByPlacingValueInVar(RulesArray[i].head.head.var, true);
				}
			}
		}
    	System.out.println("Ufter unit check");
    }
    public void printValueOfVariables()
    {
    	System.out.println("-------THE VALUE TABLE--------");
    	Set<Integer> keys = valueTable.keySet();
    	for(int key: keys)
   	 	{
    		System.out.print("Value of " + key +" is ");
    		if(valueTable.get(key))
    		{
    			System.out.println("TRUE");
    		}
    		else
    		{
    			System.out.println("FALSE");
    		}

   	 	}
    	System.out.println("------------------------------");
    }
    
    public LinkedList Ts(LinkedList s) 
    {
    	int i ,j;
    	LinkedList l = new LinkedList();
    	Node n1 =s.head;
    	int ruleNum;
    	//System.out.println("size of s : " + s.getSize());
    	LinkedList[] arrList = new LinkedList[s.getSize()];
    	for ( i = 0; i < arrList.length; i++) 
    	{
			arrList[i]=varHT.get(n1.var);
			n1=n1.next;
		}
    	//arrList[0].printList();
    	Node n2=arrList[0].head;
    	int times;
    	while(n2!=null)
    	{
    		times=0;
    		ruleNum=n2.var;
    		times++;
    		for (j = 1; j < arrList.length; j++)
    		{
				Node n3=arrList[j].head;
				while(n3!=null)
				{
					if(ruleNum==n3.var)
					{
						times++;
						break;
					}
					n3=n3.next;
				}
			}
    		if(times==s.getSize())
    		{
    			l.addAtTail(ruleNum);
    		}
    		n2=n2.next;
    	}
    	
    	Node n4 = l.head;
    	n1=s.head;
    	int index=0;
    	boolean delete ;
    	while(n4!=null)
    	{
    		delete = false;
    		ruleNum=n4.var;
    		Node nBody = RulesArray[ruleNum].body.head;
    		Node nHead = RulesArray[ruleNum].head.head;
    		while(nBody!=null)
    		{
    			if(!existInList(s, nBody.var))
    			{
    				delete=true;
    			}
    			nBody=nBody.next;
    		}
    		if(!delete)
    		{
    			while(nHead!=null)
        		{
    				if(!existInList(s, nHead.var))
        			{
        				delete=true;
        			}
        			nHead=nHead.next;
        		}
    		}
    		if(delete)
    		{
    			l.deleteAtIndex(index);
    		}
    		else
    		{
    			index++;
    		}
    		n4=n4.next;
    	}
    	
    	
    	
    	return l;
    }
    
    private boolean existInList(LinkedList l , int num)
    {
    	Node n = l.head;
    	while(n!=null)
    	{
    		if(n.var==num)
    		{
    			return true;
    		}
    		n=n.next;
    	}
    	return false;
    }
    
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    

	

}
