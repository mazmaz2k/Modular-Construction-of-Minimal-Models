package Rules;

import java.io.ObjectOutputStream.PutField;
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
    public int counter = 0;
    public LinkedList minModel;
    int SIZE;
    public RulesDataStructure (int numOfRules)
    {
    	SIZE=numOfRules;
    	RulesArray = new Rule[numOfRules];
    	for (int i = 0; i < RulesArray.length; i++)
    	{
    		RulesArray[i]= new Rule();
		}
    	
    	varHT = new Hashtable<Integer, LinkedList>();
    	
    	literalMap = new HashMap<String,Boolean>();
    	minModel = new LinkedList();
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
   
  
    
    
   
    
    
    
    
  /* public void checkForUnits()
    {
    	
    	for (int i = 0; i < RulesArray.length; i++)
    	{
    		if(RulesArray[i]!=null)
    		{
    			if((RulesArray[i].body.getSize() + RulesArray[i].head.getSize())==1)
    			{

    				if(RulesArray[i].body.getSize() ==1)//body size is 1 and head size is 0s
    				{
    					literalMap.put(String.valueOf(RulesArray[i].body.head.var), false);
    					ChangeDataStrucureByPlacingValueInVar(RulesArray[i].body.head.var, false);

    				}
    				else//head size is 1 and body size is 0
    				{
    					literalMap.put(String.valueOf(RulesArray[i].head.head.var), true);

    					ChangeDataStrucureByPlacingValueInVar(RulesArray[i].head.head.var, true);
    				}
    			}
    		}
		}
    	System.out.println("Ufter unit check");

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
    
    public LinkedList Ts (LinkedList s)
    {
    	LinkedList Ts = new LinkedList();
    	
    	Node Snode =s.head;
    	DefaultHashMap<Integer, Boolean> map = new DefaultHashMap<Integer, Boolean>(false);//on default we did not check the rules
    	boolean addToTs;
    	for (int i = 0; i < s.getSize() ; i++)
    	{
    		literalMap.put(String.valueOf(Snode.var), false);//init all vars of s to false
    		LinkedList l = varHT.get(Snode.var);
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
    	if(Ts.isEmpty())
    	{
    		this.counter+=s.getSize();//count how many times we put value in a variable
    	}
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
 	 
 	  if(DLL(clauses)==true)
 	  {
 		  return true;
 	  }
 	  return false;
 	 
    }
    private boolean DLL(ArrayList<Clause> Clauses)
	{
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
				this.counter++;//count how many times we put value in a variable
//				printClauses(Clauses);
//				System.out.println("Performing unitary propagation with: "+literalToRemove);
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
				//	System.out.println("Falsehood detected. Returning false.");
					return false;
				}
				else if(hasEmptyClause(Clauses))
				{
				//	System.out.println("Empty clause detected. Returning false.");
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
    
    public void updateRuleDS()
    {
    	Set<String> keys = literalMap.keySet();
    	//try {
    		for(String key: keys)
    		{
    			//System.out.println("key: "+ Integer.parseInt(key) + " value:  "+literalMap.get(key) );
    			if(literalMap.get(key))
    			{
    				minModel.addAtTail(Integer.parseInt(key));
    			}
    			ChangeDataStrucureByPlacingValueInVar(Integer.parseInt(key), literalMap.get(key));
    		//	literalMap.remove(key);
    			//System.out.println("remove key  " + key);
    		}
    		literalMap.clear();
    //	}
    	/*catch(Exception e)
    	{
    		
    	}*/
    }
    public void ChangeDataStrucureByPlacingValueInVar(int var , boolean value)
    {
    	if(conflictExist(var, value))
    	{
//    		System.out.println("CONFLICT");
    		return ;
    	}
    	if(!variableExist(var))
    	{
    		System.out.println("VARIABLE NOT EXIST");
//    		return ;
    	}
    	LinkedList l = varHT.get(var);
    	Node n = l.head;
    	while(n!=null)
    	{
    		if(existInBody(var, n.var)&& !value)
    		{
    			deleteRule(n.var);
    			this.SIZE--;
 //   			System.out.println("DELETE RULE NUMBER " + n.var);
    		}
    		else if((existInBody(var, n.var)&& value))
    		{
    			deleteVarFromBody(var,n.var);
//    			System.out.println( "DELETE VARIABLE " + var + " IN RULE " + n.var);
    		}
    		else if(existInHead(var, n.var)&& value)
    		{
    			deleteRule(n.var);
    			this.SIZE--;
//    			System.out.println("DELETE RULE NUMBER " + n.var);
    		}
    		else if (existInHead(var, n.var)&& !value)
    		{
    			deleteVarFromHead(var,n.var);
//    			System.out.println("DELETE VARIABLE "+var+" IN RULE " + n.var);
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
    public boolean conflictExist(int var ,boolean val)
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
    			if( (existInBody(var,n.var )) && sizeOfBody==1 && val &&sizeOfHead==0)
    			{
    				return true;
    			}
    			else if((existInHead(var,n.var)) && sizeOfHead==1 && !val &&sizeOfBody==0)
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
    public void splitConnectedComponent(int[] v)
    {
    	
    	for (int i = 0; i < v.length; i++) 
    	{
    		if(!conflictExist(v[i], false))
    		{
    			ChangeDataStrucureByPlacingValueInVar(v[i], false);
    		}
    		else
    		{
    			ChangeDataStrucureByPlacingValueInVar(v[i], true);
    		}
		}
    	
    	/*ArrayList<Clause> clauses = new ArrayList<>();
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
//    	System.out.println("print clauses");
    	//printClauses(clauses);
 //   	System.out.println("copy to array list");
    	int size = v.length;
    	System.out.println("size of array is: " + size);
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
					literal = String.valueOf(v[j]);
				}
				else
				{
					literal = "-"+String.valueOf(v[j]);
				}
				clause.addLiteral(literal);
				copy.add(clause);
				System.out.println( "Adding clause: "+clause.printClause());		
			}
    		//check if sat
    		//System.out.println("check sat");
    		/**Here is the problem!!!!!!!!!! Run DP on large source!!!!!!!!!!!!!
    		 * */
    		if(DLL(copy))
    		{
    			System.out.println("found and update . we found in index: "+ i);
    			updateRuleDS();
    			return true;
    		}
    		//System.out.println(i);
    		
		}
    	return false;*/
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
    /*public static void main(String[] args)
    {
    		boolean[] a = toBinary(15,8);
    		for (int i = 0; i < a.length; i++) {
				System.out.print(" "+a[i]);
			}
    }*/
   /* private int N_OVER_K(int n ,int k)
    {
    	return (int)(getFactorial(n)/(getFactorial(k)*getFactorial(n-k)));
    }
    private long getFactorial(int number) 
    {
        long factorial = 1;
        for (int i = 1; i <= number; ++i) 
        {
            factorial *= i;
        }
        return factorial;
    }*/
   /* public static void main(String[] args) 
    {
    	System.out.println(getFactorial(20));
    	System.out.println(N_OVER_K(10,5));
    }*/
    
  /*  public LinkedList remainingVars()
    {
    	LinkedList s = new LinkedList();
    	for (int i = 0; i < RulesArray.length; i++) 
    	{
			if(RulesArray[i]!=null)
			{
				Node nB = RulesArray[i].body.head;
				while(nB!=null)
				{
					s.addAtTail(nB.var);
					nB=nB.next;				}
				Node nH = RulesArray[i].head.head;
				while(nH!=null)
				{
					s.addAtTail(nH.var);
					nH=nH.next;	
				}
			}
		}
    		return s;
    }*/
    
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
