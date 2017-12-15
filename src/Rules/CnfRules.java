package Rules;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.*;
import Rules.LinkedList.Node;
import java.util.Set;
import java.util.Scanner;  

public class CnfRules
{
	private static final String FILENAME = "./CnfFile.txt";

	public static void main(String[] args)
	{
		Scanner sc;
		int var ;
		int index = 0;
		int numOfRules;
		RulesDataStructure DS ;

		try 
		{
			sc = new Scanner(new File(FILENAME));//read file
			numOfRules = sc.nextInt();
			DS = new RulesDataStructure(numOfRules);
			while (sc.hasNextLine()) 
			{
				var = sc.nextInt();
				if(var!=0)
					DS.addToRulsArray(index, var);
				else
					index++;

			}
			
			ModuMin(DS);
		}catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	


	}
	
	public static void ModuMin(RulesDataStructure DS )
	{
		LinkedList s = new LinkedList();
		s.addAtTail(1);
		s.addAtTail(2);
		s.addAtTail(3);
		s.addAtTail(4);
		s.addAtTail(5);
		
		LinkedList Ts=DS.Ts(s);
		Ts.printList();
		DS.printRulesArray();
		DS.FindMinimalModelForTs(Ts);
		DS.printValueOfVariables();
		DS.updateRuleDS();
		DS.printRulesArray();
		System.out.println("SIZE OF T: " +DS.SIZE);
	}
	
	


}