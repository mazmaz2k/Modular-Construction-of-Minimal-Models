package Rules;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.spec.DSAGenParameterSpec;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

public class MinimalModel extends Graph<Integer>{

	private JFrame frame;
	private JTextField textField;
	RulesDataStructure DS ;
	boolean readFile = false;
	static int rulesNum;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MinimalModel window = new MinimalModel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		MinimalModel m = new MinimalModel();
		m.script();
		
//		m.readfile();
//		if(m.Modumin())
//		{
//			System.out.println("SAT The minimal model is: "+ m.DS.StringMinimalModel());
//			
//		}
//		else 
//		{
//			System.out.println("UNSAT");
//		}
		
		
		/*LinkedList l= m.DS.checkFormat();
		if(l.getSize()==0)
		{
			if(m.Modumin())
			{
				System.out.println("SAT The minimal model is: "+ m.DS.StringMinimalModel());

			}
			else 
			{
				System.out.println("UNSAT");
			}
		}
		else
		{
			System.out.println("Please correct lines: ");
			l.printList();
			System.out.println("Its not in the right format");
			System.out.println("Can't be a clause where all litarals are negative");
		}*/

		
		
	}
	
	public void script()
	{
		String s ="python3 cnf2lparse.py ex | ./wasp --minimize-predicates=a --minimization-algorithm=guess-check-split --silent" ;

		String[] cmd = {
				"/bin/sh",
				"-c",
				s
				};

		String place = "/home/rachel/Desktop/alviano-wasp-f3fed39/build/release";
		try {
			Process p =Runtime.getRuntime().exec(cmd,null,new File(place));
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while((line = in.readLine())!=null)
			{
				System.out.println(line);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readfile()
	{

		Scanner sc;
		int var ;
		int index = 0;
		int numOfRules;

		try 
		{

			String Path = ".//CnfFile.txt" ;
			sc = new Scanner(new File(Path));//read file
			numOfRules = sc.nextInt();
			rulesNum=numOfRules;
			DS = new RulesDataStructure(numOfRules);
			while (sc.hasNextLine()) 
			{
				var = sc.nextInt();
				if(var!=0)
					DS.addToRulsArray(index, var);
				else
					index++;
			}
			System.out.println("File was read successfully");
		}catch (FileNotFoundException ex)
		{
			// TODO Auto-generated catch block
			//ex.printStackTrace();
			System.out.println("Error on reading the file");


		}

	}
	
	public boolean Modumin()
	{
		int size = DS.SIZE;			
		while(DS.SIZE!=0)
		{

			//create graph
			Graph<Integer> g = initGraph(DS, size);
			//find source
			LinkedList s = sourceOfGraph(g);
			//Find Ts
			LinkedList Ts=DS.Ts(s);
			//find minimal model for Ts
			if(!DS.FindMinimalModelForTs(Ts))
			{
				return false;
			}
			DS.updateRuleDS();		
		}	
		
//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
	//	DS.printValueOfVariables();
		return true;
	}
	/*
	public boolean Modumin()
	{
		int size = DS.SIZE;			
		while(DS.SIZE!=0)
		{

			System.out.println("Rules array SIZE  : " +DS.SIZE);
			//DS.printRulesArray();
//			//DS.checkForUnits();//remove empty sources
			//TODO : print CC and see if I separate them!!!!!!!
			Graph<Integer> g = initGraph(DS, size);
			LinkedList s = sourceOfGraph(g);
			System.out.println("s is: ");
			s.printList();
			double temp=0.2*g.getAllVertex().size(),sSize=s.getSize();
			System.out.println("vals : " + DS.literalMap.toString());
			if(sSize>temp)
			{	
//				System.out.println("Dismantle the CC");
				//get list of vertexes from graph and send it to spliteConnectedComponent on rulesDS
				int[] a=dismntleToArray(g,s); 
//				System.out.println("print array");
//				for (int i = 0; i < a.length; i++)
//				{
//					System.out.println(a[i]);
//				}
				DS.splitConnectedComponent(a);
//				System.out.println("exit split connected component");
			}
			else
			{

				System.out.println("Ts is: ");
				LinkedList Ts=DS.Ts(s);
				Ts.printList();	
				if(!DS.FindMinimalModelForTs(Ts))
				{
					DS.printRulesArray();
					return false;
				}
				System.out.println("vals after : " + DS.literalMap.toString());
				DS.updateRuleDS();
				
			}
		}	
		
//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
//		DS.printValueOfVariables();
		return true;
	}
	*/
	public boolean DP()
	{
		LinkedList Ts=new LinkedList();
//		System.out.println(rulesNum);
		for (int i = 0; i < rulesNum ; i++) {
			Ts.addAtTail(i);
		}
		if(!DS.FindMinimalModelForTs(Ts))
		{
//			System.out.println("UNSAT");
//			System.out.println("The amount of time we put value in a variable is : " + DS.counter);
			return false;
		}
		DS.updateRuleDS();
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * Create the application.
	 */
	public MinimalModel() {
		super(true);
	}

/*	public static boolean ModuMin(RulesDataStructure DS )
	{
		int size = DS.SIZE;			
		while(DS.SIZE!=0)
		{

//			System.out.println("Rules array SIZE  : " +DS.SIZE);
//			DS.printRulesArray();
//			//DS.checkForUnits();//remove empty sources
			//TODO : print CC and see if I seperate them!!!!!!!
			Graph<Integer> g = initGraph(DS, size);
	        StronglyConnectedComponent scc = new StronglyConnectedComponent();
////	        
//////	        List<Set<Vertex<Integer>>> result = scc.scc(g);
////	        System.out.println("------------------------------------------------------------------------------------------------------------------");
////	        System.out.println("Here are the size of all the connected component in the graph");
////	        //print the result
////	        result.forEach(set -> {
////	        	System.out.println("sizeof :"+ set.size());
////	           // set.forEach(v -> System.out.print(v.getId() + " "));
////	            System.out.println();
////	        });
//	        System.out.println("------------------------------------------------------------------------------------------------------------------");
//
			LinkedList s = sourceOfGraph(g);
//			System.out.println("print the source size "+ s.getSize());
//			/***If we have a large source 
//			 * then we build a graph from the source which is a connected component
//			 *  and dismantle the connected component by removing some vertexes ?
			double temp=0.2*g.getAllVertex().size(),sSize=s.getSize();
//			System.out.println("this is the size "+temp+" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

			if(sSize> temp)
			{
				
				System.out.println("Dismantle the CC");
				//get list of vertexes from graph and send it to spliteConnectedComponent on rulesDS
				int[] a=dismntleToArray(g,s); 
//				System.out.println(a.length+" - a length-------------------------------------------------------------------");
				if(!DS.splitConnectedComponent(a))
				{
					System.out.println("exit split connected component");
					return false;
				}

			}
			else
			{

				LinkedList Ts=DS.Ts(s);
				//Ts.printList();
				
				if(!DS.FindMinimalModelForTs(Ts))
				{
//					System.out.println("UNSAT");
//					System.out.println("The amount of time we put value in a variable is : " + DS.counter);
					return false;
				}
				DS.updateRuleDS();
			}
		}
		
		
//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
		return true;
		//	DS.printValueOfVariables();
	} */
	
/*	public static boolean ModuMin(RulesDataStructure DS )
	{
		int size = DS.SIZE;			
		while(DS.SIZE!=0)
		{

//			System.out.println("Rules array SIZE  : " +DS.SIZE);
//			DS.printRulesArray();
			//DS.checkForUnits();//remove empty sources
			Graph<Integer> g = initGraph(DS, size);
			LinkedList s = sourceOfGraph(g);
			LinkedList Ts=DS.Ts(s);
			//Ts.printList();	
			if(!DS.FindMinimalModelForTs(Ts))
			{
//				System.out.println("UNSAT");
//				System.out.println("The amount of time we put value in a variable is : " + DS.counter);
				return false;
			}
			DS.updateRuleDS();
		}		
//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
		return true;
		//	DS.printValueOfVariables();
	}*/
	
	/*
	public static boolean DP(RulesDataStructure DS)
	{
		LinkedList Ts=new LinkedList();
//		System.out.println(rulesNum);
		for (int i = 0; i < rulesNum ; i++) {
			Ts.addAtTail(i);
		}
		if(!DS.FindMinimalModelForTs(Ts))
		{
//			System.out.println("UNSAT");
//			System.out.println("The amount of time we put value in a variable is : " + DS.counter);
			return false;
		}
		DS.updateRuleDS();
		return true;
	}
*/


}
