package Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import Graph.Vertex;

//import Graph.LinkedList1;
//import Graph.LinkedList1.Node;

import Rules.LinkedList.Node;
//import Rules.LinkeList;

public class RulesDataStructure extends DavisPutnamHelper
{
    public Rule[] RulesArray ;
    public int rulesNum;
    Hashtable<Integer, LinkedList> varHT ;
    static HashMap<Integer, Boolean> literalMap;// We will store the value of literals in this structure as we go along
    public int dpCalls;
    public LinkedList minModel;
    public int placedValueCounter;
    public int SIZE;
    public RulesDataStructure (int numOfRules)
    {
    	this.rulesNum=numOfRules;
    	SIZE=numOfRules;
    	RulesArray = new Rule[numOfRules];
    	for (int i = 0; i < RulesArray.length; i++)
    	{
    		RulesArray[i]= new Rule();
		}
    	
    	varHT = new Hashtable<Integer, LinkedList>();
    	
    	literalMap = new HashMap<Integer,Boolean>();
    	minModel = new LinkedList();
    	dpCalls = 0;
    	placedValueCounter=0;
    }
   
    public void addToRulsArray(int index , int var)
    {
    	if(var==0)//can't be because its checked when reading the file
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
    
    public LinkedList checkFormat()
    {
    	LinkedList l = new LinkedList();
    	for (int i = 0; i < RulesArray.length; i++) 
    	{
    		int SizeBody = RulesArray[i].body.getSize();
    		int SizeHead = RulesArray[i].head.getSize();
    		if(SizeBody>0 && SizeHead==0)
    			l.addAtTail(i+2);
		}
    	return l;
    }
    
    public void printRulesArray()
    {
    	int i;
    	Node tempBody;
    	Node tempHead;
    	for (i = 0; i < RulesArray.length; i++)
    	{
    		if(RulesArray[i]!=null)
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
    private boolean VariableExistInLinkedList(int var,LinkedList l)
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
   
  
    
    
   
    
    
    
    
   public void checkForUnits()
    {
	   boolean flag;
	   do
	   {
		   Rule r;
		   flag= false;
		   for (int i = 0; i < RulesArray.length; i++)
		   {
			   r=RulesArray[i];
			   if(r!=null)
			   {
				   if(r.getSize()==1)
				   {
					   flag = true;
					   if(r.body.getSize() ==1)//body size is 1 and head size is 0s
					   {
						   literalMap.put(RulesArray[i].body.head.var, false);
						   //System.out.println(RulesArray[i].body.head.var);
					   }
					   else//head size is 1 and body size is 0
					   {
						   literalMap.put(RulesArray[i].head.head.var, true);
						 // System.out.println( RulesArray[i].head.head.var);
					   }
					   updateRuleDS();
				   }
			   }
		   }
	   }while(flag);
    	System.out.println("after unit check");

    }
    public void printValueOfVariables()
    {
    	System.out.println("-------THE VALUE TABLE--------");
    	Set<Integer> keys = literalMap.keySet();
    	for(int key: keys)
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
    
    public LinkedList Ts (LinkedList s)
    {
    	LinkedList Ts = new LinkedList();
    	
    	Node Snode =s.head;
    	//System.out.println("xbncndsabnxsamnxsanm"+Snode.var);
    	DefaultHashMap<Integer, Boolean> map = new DefaultHashMap<Integer, Boolean>(false);//on default we did not check the rules
    	boolean addToTs;
    	for (int i = 0; i < s.getSize() ; i++)
    	{
    		literalMap.put(Snode.var, false);//init all vars of s to false
    		LinkedList l = varHT.get(Snode.var);
    		//System.out.println("listtttttt");
    		//l.printList();
    		if(l!=null)
    		{
    			Node n =l.head; 
    			while(n!=null)
    			{
    				if(!map.get(n.var))//if we did not check this rule, lets check
    				{
    					map.put(n.var, true);
    					addToTs = true;
    					if(!allExistInList(n.var, s))
    					{
    						addToTs=false;
    					}
    					if(addToTs)
    					{
    						Ts.addAtTail(n.var);
    					}
    				}
    				n=n.next;
    			}
    		
    		}
    		Snode= Snode.next;
		}
//    	if(Ts.isEmpty())
//    	{
//    		this.counter+=s.getSize();//count how many times we put value in a variable
//    	}
    	return Ts;
    }
    private boolean allExistInList(int ruleNum , LinkedList l)//all vars in rule exist in List
    {
    	Rule r =RulesArray[ruleNum];
    	Node nBody = r.body.head;
    	Node nHead =r.head.head;
    	while(nBody!=null)
    	{
    		if(!VariableExistInLinkedList(nBody.var, l))
    		{
    			return false;
    		}
    		nBody=nBody.next;
    	}
    	while(nHead!=null)
    	{
    		if(!VariableExistInLinkedList(nHead.var, l))
    		{
    			return false;
    		}
    		nHead=nHead.next;
    	}
    	return true;
    	
    }
    
  
    
    public boolean FindMinimalModelForTs(LinkedList Ts)
    {
 	  ArrayList<Clause> clauses = new ArrayList<>();
 	  Node nTs = Ts.head;
 	  while(nTs!=null)
 	  {
 		Node nBody =RulesArray[nTs.var].body.head;
		Node nHead = RulesArray[nTs.var].head.head;
		Clause clause = new Clause();
		String literal;
		while(nBody!=null)//first put the negative literals in order to calculate a minimal model (because of the way that DLL works)
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

		 nTs=nTs.next;	
		
 	  }
 	// printClauses(clauses);
 	  if(DLL(clauses))
 	  {
 		  return true;
 	  }
 	  return false;
 	 
    }
    private boolean DLL(ArrayList<Clause> Clauses)
	{
		this.dpCalls++;//count how many times we called to DP
    	if(Clauses.size() == 0) 
		{
		//	System.out.println("T = {EMPTY}");
			return true;
		}
		//Unitary Propagation
		while(true)
		{	
			String literalToRemove =searchSingleLiteral(Clauses, literalMap);
			if(!literalToRemove.equals("NotFoundYet"))
			{
				//printClauses(Clauses);
				//System.out.println("Performing unitary propagation with: "+literalToRemove);
				removeClauses(literalToRemove,Clauses);
				cutClauses(literalToRemove,Clauses);
				//printClauses(Clauses);
				if(Clauses.size() == 0) 
				{
				//	System.out.println("All clauses removed. Returning true.");
					return true;
				}
				if(hasFalsehood(Clauses)) 
				{
					//System.out.println("Falsehood detected. Returning false.");
					return false;
				}
				else if(hasEmptyClause(Clauses))
				{
					//System.out.println("Empty clause detected. Returning false.");
					return false;
				}
			}
			else
			{
			//	System.out.println("No single literals.");
			//	System.out.println("Cannot perform unitary propagation.");
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
		String l1 = pickLiteral(Clauses);//most of time pick a negative literal because the order of a clause (first body then head)
		String l2 = "";
		
		if(l1.startsWith("-"))
		{
			l2 = l1.substring(1);
			clause1.addLiteral(l1);
			clause2.addLiteral(l2);
			copy1.add(clause1);
			copy2.add(clause2);
		}
		else
		{
			l2 = "-"+l1;
			clause1.addLiteral(l2);
			clause2.addLiteral(l1);
			copy1.add(clause1);
			copy2.add(clause2);
		}
		
		
		//Moment of the truth
		//System.out.println("Adding clause: ["+l1+"]");
		if(DLL(copy1) == true)
		{
			return true;
		}
		else
		{
		//	System.out.println("Trying opposite clause: ["+l2+"]");
			return DLL(copy2);
		}
	}
    
    public void putMinModelInLiteralMap(LinkedList minmodel)
    {
    	Node n =minmodel.head;
    	while(n!=null)
    	{
    		literalMap.put(n.var, true);
    		n=n.next;
    	}
    }
    
    public void updateRuleDS()
    {
    	Set<Integer> keys = literalMap.keySet();
    	
    		for(int key: keys)
    		{
    			//System.out.println("key: "+ Integer.parseInt(key) + " value:  "+literalMap.get(key) );
    			if(literalMap.get(key))
    			{
    				minModel.addAtTail(key);
    			}
    			//System.out.println("key is: " + key +"   value is: " + literalMap.get(key));
    			ChangeDataStrucureByPlacingValueInVar(key, literalMap.get(key));
    			this.placedValueCounter++;
    		//	literalMap.remove(key);
    			//System.out.println("remove key  " + key);
    		}
    		literalMap.clear();

    }
    
    
    public void ChangeDataStrucureByPlacingValueInVar(int var , boolean value)
    {
    	if(conflictWithAssignment(var, value))
    	{
    		System.out.println("CONFLICT");
    		return ;
    	}
    	if(!variableExist(var))
    	{
    	//	System.out.println("VARIABLE NOT EXIST");
    		return ;
    	}
    	LinkedList l = varHT.get(var);
	    Node n = l.head;
    	while(n!=null)
    	{
    		if((existInBody(var, n.var)&& !value)||(existInHead(var, n.var)&& value))
    		{
    			deleteRule(n.var);
    			this.SIZE--;
    			//System.out.println("DELETE RULE NUMBER " + n.var);
    		}
    		else if((existInBody(var, n.var)&& value))
    		{
    			deleteVarFromBody(var,n.var);
    			//System.out.println( "DELETE VARIABLE FROM BODY" + var + " IN RULE " + n.var);
    		}
    		else if (existInHead(var, n.var)&& !value)
    		{
    			deleteVarFromHead(var,n.var);
    			//System.out.println("DELETE VARIABLE FROM HEAD"+var+" IN RULE " + n.var);
    		}	
    		n=n.next;
    		
    	}
    	return;
    	
    }
    
    /**check if variable exist in the set of rules*/
    public boolean variableExist(int var)
    {
    	LinkedList l = varHT.get(var);
    	if(l==null)
    	{
    		return false;
    	}
    	return true;
    }
    
    /**check if we return false if we put value inside the variable
     * by the rules of logic*/
    public boolean conflictWithAssignment(int var ,boolean val)
    {
    	LinkedList l = varHT.get(var);
    	Node n = l.head;
    	while(n!=null)
    	{
    		int sizeOfBody, sizeOfHead;
    		sizeOfBody = RulesArray[n.var].body.getSize();
    		sizeOfHead = RulesArray[n.var].head.getSize();
    		if( (existInBody(var,n.var )) && sizeOfBody==1 && val &&sizeOfHead==0)
    		{
    				return true;
    		}
    		else if((existInHead(var,n.var)) && sizeOfHead==1 && !val &&sizeOfBody==0)
    		{
    			return true;
    		}
    		
    			n=n.next;
    	}		
    	
    	return false;   	
    }
    
    
    /**
     * Check if there is conflict in the theory . 
     * for example " a and not a "
     * */
    public boolean isConflict()
    {
    	Rule r1,r2;
    	for (int i = 0; i < RulesArray.length; i++) 
    	{
    		r1=RulesArray[i];
    		if(r1!=null&&r1.getSize()==1)
    		{
    			boolean isPositive;
    			int var;
    			if(r1.body.getSize()==1)
    			{
    				isPositive=false;
    				var=r1.body.head.var;
    			}
    			else
    			{
    				isPositive=true;
    				var=r1.head.head.var;
    			}
    			
    			for(int j=i+1;j<RulesArray.length;j++)
    			{
    				r2=RulesArray[j];
    				if(r2!=null&&r2.getSize()==1)
    				{
    					if(r2.body.getSize()==1)
    					{
    						if(var==r2.body.head.var && isPositive)
    							return true;//conflict exist
    					}
    					else
    					{
    						if(var==r2.head.head.var && !isPositive)
    							return true; //conflict exist
    					}
    				}		
    			}
    			
    		}
			
		}
    	
    	return false;
    }
    
    /**receive a rule number an delete the rule from rules array*/
    private void deleteRule(int ruleNum)
    {
    	updateHT(0,ruleNum);
    	RulesArray[ruleNum].body.deleteList();
    	RulesArray[ruleNum].head.deleteList();
    	RulesArray[ruleNum]=null;
    }
    
    /**delete variable from body inside rules array */
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
    			index--;
    			break;
    		}
    		index++;
    		n=n.next;
    	}
    }
    /**delete variable from head inside rules array*/
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
    			index--;
    			break;
    		}
    		index++;
    		n=n.next;
    	}
    }
    
    /**update the hash table of the variables
     * on every changes we make */
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
    /**return a string of the minimal model */
    public String StringMinimalModel()
    {
    	String str= "[ ";
    	Node n = minModel.head;
    	while(n!=null)
    	{
    		str+= "{"+n.var+"}" + " ";
    		n=n.next;
    	}
    	str+= "]" + "\r\n" +" |MM| = "+ minModel.getSize();
    	return str;
    }
    
  /***Receive a set of vertexes from graph ds and put values on each vertex
    also checks if the values we put in the variables return SAT if so we change rules ds 
    by the values we found and if not we try different values for the variables
     ***/
    public void splitConnectedComponent(ArrayList<Vertex<Integer>> v)
    {
    	//System.out.println("enter split");

    	ArrayList<Clause> clauses = new ArrayList<>();
    	for (int i = 0; i < RulesArray.length; i++) 
    	{
    		if(RulesArray[i]!=null)
    		{
    			Node nBody =RulesArray[i].body.head;
    			Node nHead = RulesArray[i].head.head;
    			Clause clause = new Clause();
    			String literal;
    			while(nBody!=null)//first put the negative literals in order to calculate a minimal model (because of the way that DLL works)
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
    		}
    	}
    	//System.out.println("print clauses");
    	//printClauses(clauses);
 //   	System.out.println("copy to array list");
    	int size = v.size();
    //	System.out.println("size of array is: " + size);
    	int N = (int)Math.pow(2,size); 
    	boolean[] binaryArray ;
		String literal;
		Clause clause;
		ArrayList<Clause> copy;
    	for (int i = 0; i < N; i++)//from 0 to 2^n -1
    	{
    		copy = new ArrayList<Clause>();//
    		for(Clause c: clauses)
    		{
    			Clause c2 = new Clause();
    			for(String s: c.literals)
    			{
    				c2.addLiteral(s);
    			}
    			copy.add(c2);
    		}// copy original to not make any changes
    		binaryArray = toBinary(i,size);//returns array
    		for (int j = 0; j < size; j++) 
    		{
    			
    			clause= new Clause();
    			if(binaryArray[j])
    			{
    				literal = String.valueOf(v.get(j).getId());
    			}
    			else
    			{
    				literal = "-"+String.valueOf(v.get(j).getId());
    			}
    			clause.addLiteral(literal);
    			copy.add(clause);
    			//System.out.println( "Adding clause: "+clause.printClause());	
			}
    		//check if sat
    		//System.out.println("check sat");
    		//printRulesArray();
    		if(DLL(copy))
    		{
        		literalMap.clear();
    			//System.out.println("found and update . we found in index: "+ i);
    			for (int j = 0; j < size; j++) 
    			{
    				System.out.println("index: "+ i + " var: " + v.get(j).getId() +" val: " + binaryArray[j]);
    				//literalMap.put((int)v.get(j).getId(), binaryArray[j]);
    				ChangeDataStrucureByPlacingValueInVar((int)v.get(j).getId(), binaryArray[j]);
    				
				}
    			//updateRuleDS();
    			if(isTheoryPositive())
    			{
    				for (int j = 0; j < size; j++) 
        			{
        				if(binaryArray[j])
        				{
        					minModel.addAtTail((int)v.get(j).getId());
        				}        				
    				}
    				return ;
    			}
    		}
    		//System.out.println(i);
    		literalMap.clear();
    		
		}
    	
    }
    
   
    
    /**return a binary value of the number 
     * ,the (base) last bits */
     private boolean[] toBinary(int number, int base)
     {
        final boolean[] ret = new boolean[base];
        for (int i = 0; i < base; i++) {
            ret[base - 1 - i] = (1 << i & number) != 0;
        }
        return ret;
    }
     
     
     public void removeDoubles()
     {
    	 for (int i = 0; i < RulesArray.length; i++) 
    	 {
    		 LinkedList body = RulesArray[i].body;
    		 Node nBody =body.head;
    		 while(nBody!=null)
    		 {
    			 int var =nBody.var;
    			 Node nBody2=nBody.next;
    			 int index=1;
    			 while(nBody2!=null)
    			 {
    				 if(var==nBody2.var)
    				 {
    					 body.deleteAtIndex(index);
    					 index--;
    				 }
    				 index++;
    				 nBody2=nBody2.next;
    			 }
    			nBody=nBody.next;		
    		 }
    		 LinkedList head = RulesArray[i].head;
    		 Node nHead =head.head;
    		 while(nHead!=null)
    		 {
    			 int var =nHead.var;
    			 Node nHead2=nHead.next;
    			 int index=1;
    			 while(nHead2!=null)
    			 {
    				 if(var==nHead2.var)
    				 {
    					 head.deleteAtIndex(index);
    					 index--;
    				 }
    				 index++;
    				 nHead2=nHead2.next;
    			 }
    			nHead=nHead.next;
    		 }
    		 
    	 }
     }
     
     
     public boolean isTheoryPositive()
     {
    	 for (int i = 0; i < RulesArray.length; i++) 
    	 {
			if(RulesArray[i]!=null)
			{
				Rule r = RulesArray[i];
				if(r.head.getSize()==0)
				{
					return false;
				}
			}
		}
    	 return true;
     }
     
     
     
     
     
//*******************************check copy, another split method****************************************//
     
     
     
     
     
     
     
     public void splitConnectedComponent2(ArrayList<Vertex<Integer>> VertexSeperatorArray)
     {
     	System.out.println("enter split 2");
     	int size = VertexSeperatorArray.size();
        System.out.println("size of array is: " + size);
         int N = (int)Math.pow(2,size); 
         boolean[] binaryArray ;
     	
         for (int i = 0; i < N; i++) 
         {
        	//System.out.println("copy the DS");
        	Rule[] copy = copyRulesDS();
            boolean conflict=false;
         	binaryArray = toBinary(i,size);//returns array
 			//System.out.println("INDEX "+i);
         	
         	for(int j =0;j<size;j++)
         	{
         		//System.out.println("check conflict with assigment " +(int)VertexSeperatorArray.get(j).getId()+"  "+ binaryArray[j]);
         		if(conflictWithAssignment2(copy,(int)VertexSeperatorArray.get(j).getId(), binaryArray[j]))
         		{
         			conflict = true;
         			System.out.println("111");
         			break;
         		}
         		else
         		{
         			//System.out.println("not found conflict");
         			ChangeDataStrucureByPlacingValueInVar2(copy,(int)VertexSeperatorArray.get(j).getId(), binaryArray[j]);
         			checkForUnits2(copy);
         			if(isConflict2(copy))
         			{
         				conflict=true;
         				System.out.println("222");
         				break;
         			}
         			if(!isTheoryPositive2(copy))
         			{
         				conflict = true;
         				System.out.println("333");
         				break;
         			}
         		}
         	}
         	if(!conflict)
         	{
         		//System.out.println("we found one in index: "+ i);
         		System.out.println(isTheoryPositive2(copy));
         		for (int j = 0; j < size; j++)
         		{
         			//System.out.println("change DS variable: "+(int)VertexSeperatorArray.get(j).getId()+" value: "+binaryArray[j]);
         			//ChangeDataStrucureByPlacingValueInVar((int)VertexSeperatorArray.get(j).getId(), binaryArray[j]);
         			literalMap.put((int)VertexSeperatorArray.get(j).getId(), binaryArray[j]);
         			//checkForUnits();
				}
         		updateRuleDS();
         	//	printRulesArray();
         		//System.out.println("VAR_HT");
         		//printHashTable();
         		return;
         	}
 		}
   
     	
     }
     
     public Rule[] copyRulesDS()
     {
     	Rule[] rules = new Rule[rulesNum];
     	for (int i = 0; i < rulesNum; i++)
     	{
     		rules[i]= new Rule();
 		}
     	for (int i = 0; i < RulesArray.length; i++) 
     	{
 			if(RulesArray[i]==null)
 			{
 				rules[i]=null;
 			}
 			else
 			{
 				Node nBody=RulesArray[i].body.head;
 				Node nHead=RulesArray[i].head.head;
 				rules[i].body=new LinkedList();
 				rules[i].head= new LinkedList();
 				while(nBody!=null)
 				{
 					rules[i].body.addAtTail(nBody.var);
 					nBody=nBody.next;
 				}
 				while(nHead!=null)
 				{
 					rules[i].head.addAtTail(nHead.var);
 					nHead=nHead.next;
 				}
 			}
 		}
     	return rules;
     }
     public void print(Rule[] array)
     {

     	int i;
     	Node tempBody;
     	Node tempHead;
     	for (i = 0; i < array.length; i++)
     	{
     		if(array[i]!=null)
     		{
     			System.out.println("RULE NUMBER " + i);
     			System.out.print("\t Body Of Rule : ");
     			tempBody = array[i].body.head;
     			tempHead =array[i].head.head;
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
     }
     public boolean conflictWithAssignment2(Rule[] array, int var ,boolean val)
     {
     	LinkedList l = varHT.get(var);
     	Node n = l.head;
     	while(n!=null)
     	{
     		int sizeOfBody, sizeOfHead;
     		if(array[n.var]!=null)
     		{
     			sizeOfBody = array[n.var].body.getSize();
     			sizeOfHead = array[n.var].head.getSize();
     			if( sizeOfBody==1  && array[n.var].body.head.var==var  && val &&sizeOfHead==0)
     			{
     				return true;
     			}
     			else if(sizeOfHead==1 && array[n.var].head.head.var==var && !val &&sizeOfBody==0)
     			{
     				return true;
     			}
     		}
     		
     			n=n.next;
     	}		
     	
     	return false;   	
     }
     public void ChangeDataStrucureByPlacingValueInVar2(Rule[] array, int var , boolean value)
     {
    	 
     	LinkedList l = varHT.get(var);
 	    Node n = l.head;
     	while(n!=null)
     	{
     		if((existInBody2(array,var, n.var)&& !value)||(existInHead2(array,var, n.var)&& value))
     		{
     			deleteRule2(array,n.var);
     			//System.out.println("DELETE RULE NUMBER " + n.var);
     		}
     		else if((existInBody2(array, var, n.var)&& value))
     		{
     			deleteVarFromBody2(array,var,n.var);
     			//System.out.println( "DELETE VARIABLE FROM BODY" + var + " IN RULE " + n.var);
     		}
     		else if (existInHead2(array, var, n.var)&& !value)
     		{
     			deleteVarFromHead2(array, var,n.var);
     			//System.out.println("DELETE VARIABLE FROM HEAD"+var+" IN RULE " + n.var);
     		}	
     		n=n.next;
     		
     	}
     	return;
     	
     }
     
     private void deleteRule2(Rule[] array, int ruleNum)
     {
    	 if(array[ruleNum]==null)
    		 return;
     	array[ruleNum].body.deleteList();
     	array[ruleNum].head.deleteList();
     	array[ruleNum]=null;
     }
     
     /**delete variable from body inside rules array */
     private void deleteVarFromBody2(Rule[] array, int var, int ruleNum)
     {
     	LinkedList l = array[ruleNum].body;
     	int index = 0;
     	Node n = l.head;
     	while(n!=null)
     	{
     		if(n.var==var)
     		{
     			l.deleteAtIndex(index);
     			index--;
     			break;
     		}
     		index++;
     		n=n.next;
     	}
     }
     /**delete variable from head inside rules array*/
     private void deleteVarFromHead2(Rule[] array, int var, int ruleNum)
     {
     	LinkedList l = array[ruleNum].head;
     	int index = 0;
     	Node n = l.head;
     	while(n!=null)
     	{
     		if(n.var==var)
     		{
     			l.deleteAtIndex(index);
     			index--;
     			break;
     		}
     		index++;
     		n=n.next;
     	}
     }
     
     private boolean existInBody2(Rule[] array, int var, int ruleNum)
     {
     	if(array[ruleNum]==null)
     	{
     		return false;
     	}
     	Node n;
     	n = array[ruleNum].body.head;
     	while(n!=null)
     	{
     		if(n.var == var)
     			return true;
     		n=n.next;
     	}
     	return false;
     }
     
     private boolean existInHead2(Rule[] array, int var , int ruleNum)
     {
    	 if(array[ruleNum]==null)
      	{
      		return false;
      	}
     	Node n;
     	n = array[ruleNum].head.head;
     	while(n!=null)
     	{
     		if(n.var == var)
     			return true;
     		n=n.next;
     	}
     	return false;
     }
     
     public boolean isConflict2(Rule[] array)
     {
     	Rule r1,r2;
     	for (int i = 0; i < array.length; i++) 
     	{
     		r1=array[i];
     		if(r1!=null&&r1.getSize()==1)
     		{
     			boolean isPositive;
     			int var;
     			if(r1.body.getSize()==1)
     			{
     				isPositive=false;
     				var=r1.body.head.var;
     			}
     			else
     			{
     				isPositive=true;
     				var=r1.head.head.var;
     			}
     			
     			for(int j=i+1;j<array.length;j++)
     			{
     				r2=array[j];
     				if(r2!=null&&r2.getSize()==1)
     				{
     					if(r2.body.getSize()==1)
     					{
     						if(var==r2.body.head.var && isPositive)
     							return true;//conflict exist
     					}
     					else
     					{
     						if(var==r2.head.head.var && !isPositive)
     							return true; //conflict exist
     					}
     				}		
     			}
     			
     		}
 			
 		}
     	
     	return false;
     }
     
     public void checkForUnits2(Rule[] array)
     {
     	Rule r;
     	for (int i = 0; i < array.length; i++)
     	{
     		r=array[i];
     		if(r!=null)
     		{
     			if(r.getSize()==1)
     			{

     				if(r.body.getSize() ==1)//body size is 1 and head size is 0s
     				{
     					ChangeDataStrucureByPlacingValueInVar2(array, array[i].body.head.var, false);

     				}
     				else//head size is 1 and body size is 0
     				{
     					ChangeDataStrucureByPlacingValueInVar2(array, array[i].head.head.var, true);
     				}
     			}
     		}
 		}
     	//System.out.println("after unit check");

     }
     
     
     public boolean isTheoryPositive2(Rule[] array)
     {
    	 for (int i = 0; i < array.length; i++) 
    	 {
			if(array[i]!=null)
			{
				Rule r = array[i];
				if(r.head.getSize()==0)
				{
					return false;
				}
			}
		}
    	 return true;
     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
    
	

}
