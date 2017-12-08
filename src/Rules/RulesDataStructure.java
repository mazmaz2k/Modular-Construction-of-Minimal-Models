package Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

//import Graph.LinkedList1;
//import Graph.LinkedList1.Node;

import Rules.LinkedList.Node;
//import Rules.LinkeList;

public class RulesDataStructure extends DavisPutnamHelper
{
    public Rule[] RulesArray ;
    Hashtable<Integer, LinkedList> varHT ;
   static HashMap<String, Boolean> literalMap;// We will store the value of literals in this structure as we go along
    public RulesDataStructure (int numOfRules)
    {
    	RulesArray = new Rule[numOfRules];
    	for (int i = 0; i < RulesArray.length; i++)
    	{
    		RulesArray[i]= new Rule();
		}
    	
    	varHT = new Hashtable<Integer, LinkedList>();
    	
    	literalMap = new HashMap<String,Boolean>();
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
    /*
    private boolean existInBody(int var, int ruleNum)
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
    
    private boolean existInHead(int var , int ruleNum)
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
    	RulesArray[ruleNum]=null;
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
    */
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
    
    
    
   /* public boolean checkForUnits()
    {
    	boolean found = false;
    	for (int i = 0; i < RulesArray.length; i++)
    	{
			if((RulesArray[i].body.getSize() + RulesArray[i].head.getSize())==1)
			{
				found = true;
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
		return found;

    }*/
    public void printValueOfVariables()
    {
    	System.out.println("-------THE VALUE TABLE--------");
    	Set<String> keys = literalMap.keySet();
    	for(String key: keys)
   	 	{
    		System.out.print("Value of " + key +" is ");
    		if(literalMap.get(key))
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
    
    	for ( i = 0; i < arrList.length; i++) //creae an array. each cell contains list of ruleNums that a var from s contains in the hash table 
    	{
			arrList[i]=varHT.get(n1.var);
			n1=n1.next;
		}
    	//arrList[0].printList();
    	Node n2=arrList[0].head;
    	int times;
    	while(n2!=null)//insert to list the common rulenums
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
    	while(n4!=null)//delete rulesnum from the list that have variables which exist in a rule but not in s
    	{
    		delete = false;
    		ruleNum=n4.var;
    		Node nBody = RulesArray[ruleNum].body.head;
    		Node nHead = RulesArray[ruleNum].head.head;
    		while(nBody!=null)
    		{
    			if(!VariableExistInLinkedList( nBody.var,s))
    			{
    				delete=true;
    			}
    			nBody=nBody.next;
    		}
    		if(!delete)
    		{
    			while(nHead!=null)
        		{
    				if(!VariableExistInLinkedList (nHead.var,s))
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
    	for (int k = 0; k < arrList.length; k++) 
    	{
			arrList[k].deleteList();
		}
    	
    	//-- All variables in s are false at first 
    	n1 = s.head;
    	while(n1!=null)
    	{
    		literalMap.put(String.valueOf(n1.var), false);
    		n1=n1.next;
    	}
    
    	return l;
    }
    
    
    
    public void ModuMin(LinkedList Ts)
    {
 	  ArrayList<Clause> clauses = new ArrayList<>();
 	   Node n = Ts.head;
 	  for (int i = 0; i < Ts.getSize(); i++) 
 	  {	
 		Node nBody =RulesArray[n.var].body.head;
		Node nHead = RulesArray[n.var].head.head;
		Clause clause = new Clause();
		String literal;
		while(nBody!=null)
		{
		  literal = "-";
		  literal+= String.valueOf(nBody.var);
		  clause.addLiteral(literal);
		  nBody=nBody.next;
		}
		while(nHead!=null)
		{
		  literal = String.valueOf(nHead.var); 
		  clause.addLiteral(literal);
		  nHead=nHead.next;
		  }
		clauses.add(clause);

		 n=n.next;	
		
 	  }
 	  DLL(clauses);
 	  for(Clause c : clauses)
 	  {
 		  System.out.println(c.printClause());
 	  }
    }
    public boolean DLL(ArrayList<Clause> Clauses)
	{
    	if(Clauses.size() == 0) 
		{
			System.out.println("T = {EMPTY}");
			return true;
		}
		//Unitary Propagation
		while(true)
		{	
			String literalToRemove =searchSingleLiteral(Clauses, literalMap);
			if(!literalToRemove.equals("NotFoundYet"))
			{
				printClauses(Clauses);
				System.out.println("Performing unitary propagation with: "+literalToRemove);
				removeClauses(literalToRemove,Clauses);
				cutClauses(literalToRemove,Clauses);
				printClauses(Clauses);
				if(Clauses.size() == 0) 
				{
					System.out.println("All clauses removed. Returning true.");
					return true;
				}
				if(hasFalsehood(Clauses)) 
				{
					System.out.println("Falsehood detected. Returning false.");
					return false;
				}
				else if(hasEmptyClause(Clauses))
				{
					System.out.println("Empty clause detected. Returning false.");
					return false;
				}
			}
			else
			{
				System.out.println("No single literals.");
				System.out.println("Cannot perform unitary propagation.");
				break;
			}
		}
		ArrayList<Clause> copy1 = new ArrayList<Clause>();
		ArrayList<Clause> copy2 = new ArrayList<Clause>();
		for(Clause c: Clauses)
		{
			Clause c2 = new Clause();
			for(String s: c.literals)
			{
				c2.addLiteral(s);
			}
			copy1.add(c2);
		}
		for(Clause c: Clauses)
		{
			Clause c2 = new Clause();
			for(String s: c.literals)
			{
				c2.addLiteral(s);
			}
			copy2.add(c2);
		}
		Clause clause1 = new Clause();
		Clause clause2 = new Clause();
		String l1 = pickLiteral(Clauses);
		String l2 = "";
		
		if(l1.startsWith("-")) l2 = l1.substring(1);
		else l2 = "-"+l1;
		clause1.addLiteral(l1);
		clause2.addLiteral(l2);
		copy1.add(clause1);
		copy2.add(clause2);
		//Moment of the truth
		System.out.println("Adding clause: ["+l1+"]");
		if(DLL(copy1) == true)
		{
			return true;
		}
		else
		{
			System.out.println("Trying opposite clause: ["+l2+"]");
			return DLL(copy2);
		}
	}
    
    
    ////////////////////////////////////////////////////////////////////////////////////
 /*  public void ModuMin(LinkedList Ts)
   {
	  // checkForUnits();//maybe Ts as param
	   Rule[] TsClauses = new Rule[SIZE];
	   for (int i = 0; i < SIZE; i++) 
	   {
		   Node n = Ts.head;
		   TsClauses[i]= new Rule();
		   Node nBody =RulesArray[n.var].body.head;
		   Node nHead = RulesArray[n.var].head.head;
		   while(nBody!=null)
		   {
			   TsClauses[i].body.addAtTail(nBody.var);
			   nBody=nBody.next;
		   }
		   while(nHead!=null)
		   {
			   TsClauses[i].head.addAtTail(nHead.var);
			   nHead=nHead.next;
		   }
		   n=n.next;
	   }
	   dpll(TsClauses);	   
   }
   
  
   
   public boolean dpll(Rule[] TsClauses)
   {
	   while(true)//unit
	   {
		  int literalToRemove = literalToRemove(TsClauses);
		  System.out.println("literal to remove = "+ literalToRemove);
		  if(literalToRemove!=0)
		  { 
		
			  if(SIZE==0)
			  {
				  System.out.println("Sat");
				  return true;
			  }
			  
		  }
		  else
		  {  
			  break;
		  }
	   }
	   
	   Rule[] copy1 = new Rule[TsClauses.length + 1];
	   copy(TsClauses , copy1);
	   Rule[] copy2 = new Rule[TsClauses.length + 1];
	   copy(TsClauses , copy2);
	
	   SIZE++;
	   int lit =pickLiteral(TsClauses);
	   System.out.println("pick literal "+ lit);
	   copy1[TsClauses.length].addToBody(lit);
	   System.out.println("copy1");
	   copy2[TsClauses.length].addToHead(lit);
	   System.out.println("copy2");
	   
	  if(dpll(copy1))
	  {
		  return true;
	  }
	  else {
		  return dpll(copy2);
	  }
   }
  
   
    private int pickLiteral(Rule[] TsClause)
    {
    	int literal = 0;
    	
    	for (int i = 0; i < TsClause.length; i++)
    	{
    		
    			Node nBody =TsClause[i].body.head;
    			Node nHead = TsClause[i].head.head;
    			if(nBody!=null)
				{
					literal =nBody.var;
					break;
				}
				else if(nHead!=null)
				{
					literal =nHead.var;
					break;
				}
			
		}
    	return literal; 
    }
    private void copy(Rule[] from , Rule[] to)
    {
    	int i;
    	 for ( i= 0; i < SIZE; i++) 
  	   {
  		
  			   to[i]= new Rule();
  			   Node nBody =from[i].body.head;
  			   Node nHead = RulesArray[i].head.head;
  			   while(nBody!=null)
  			   {
  				   to[i].body.addAtTail(nBody.var);
  				   nBody=nBody.next;
  			   }
  			   while(nHead!=null)
  			   {
  				   to[i].head.addAtTail(nHead.var);
  				   nHead=nHead.next;
  			   }
  	   }
    	 to[i]= new Rule();
    }
   
   public int literalToRemove(Rule[] TsClauses) // check units
   {
	   
	   int literal = 0 ; //not found
	   for (int i = 0; i < TsClauses.length; i++)
       {
		  if((TsClauses[i].body.getSize() + TsClauses[i].head.getSize())==1)
			{
				if(TsClauses[i].body.getSize() ==1)//body size is 1 and head size is 0s
				{

					literal = TsClauses[i].body.head.var;
					valueTable.put(literal, false);	
					Remove(TsClauses, literal, false);
				}
				else//head size is 1 and body size is 0
				{
					literal = TsClauses[i].head.head.var;
					valueTable.put(literal, true);
					Remove(TsClauses, literal, true);
				}
				break;
				
		   }
		   
			
		}
	   return literal;
   }

   public void Remove(Rule[] TsClauses , int var , boolean val)
   {
	 //  System.out.println("sbdhfbvdcsknajdvwvbhf");
	   for (int i = 0; i < TsClauses.length; i++) 
	   {
		   if(InBody(TsClauses,var,i)&& !val)
		   {
			   deleteRule(TsClauses ,i);
			   System.out.println("delete rule number "+ i);
			   SIZE--;
		   }
		   
		   else if((InBody(TsClauses,var,i)&& val))
	   		{
	   			deleteVarFromBody(TsClauses,var,i);
	   			System.out.println( "DELETE VARIABLE " + var + " IN RULE " + i);
	   		}
	   		else if(InHead(TsClauses,var, i)&& val)
	   		{
	   			deleteRule(TsClauses ,i);
	   			System.out.println("DELETE RULE NUMBER " + i);
	   			SIZE--;
	   		}
	   		else if (InHead(TsClauses,var, i)&& !val)
	   		{
	   			deleteVarFromHead(TsClauses,var,i);
	   			System.out.println("DELETE VARIABLE "+var+" IN RULE " +i );
	   		}
       }
	
   	return;
   }
   
   
   public boolean InBody(Rule[] TsClauses , int var , int ruleNum)
   {
	 	if(TsClauses[ruleNum]==null)
	 		return false;
	   Node n;
	   n = TsClauses[ruleNum].body.head;
  
   	while(n!=null)
   	{
   		if(n.var == var)
   			return true;
   		n=n.next;
   	}
   	return false;
   }
   
   public boolean InHead(Rule[] TsClauses , int var , int ruleNum)
   {
	   if(TsClauses[ruleNum]==null)
	 		return false;
	   Node n;
   	n = TsClauses[ruleNum].head.head;
   	while(n!=null)
   	{
   		if(n.var == var)
   			return true;
   		n=n.next;
   	}
   	return false;
   }
   public void deleteRule(Rule[] TsClauses , int ruleNum)
   {
	  
   	TsClauses[ruleNum].body.deleteList();
   	TsClauses[ruleNum].head.deleteList();
  // 	TsClauses[ruleNum]=null;
   }
   
   private void deleteVarFromBody(Rule[] TsClauses,int var, int ruleNum)
   {
   
   	LinkedList l = TsClauses[ruleNum].body;
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
   private void deleteVarFromHead(Rule[] TsClauses,int var, int ruleNum)
   {
   	LinkedList l = TsClauses[ruleNum].head;
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
   
   public void printTs(Rule[] T)
   {
   	int i;
   	Node tempBody;
   	Node tempHead;
   	for (i = 0; i < T.length; i++)
   	{
   		System.out.println("RULE NUMBER " + i);
   		System.out.print("\t Body Of Rule : ");
   		tempBody = T[i].body.head;
   		tempHead =T[i].head.head;
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
   
   
   
   
   
  */
	

}
