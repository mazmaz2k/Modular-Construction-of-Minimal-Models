package Rules;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class CnfRules
{
	private static final String FILENAME = "C:\\Users\\mazma\\Desktop\\Cnf.txt";
	
	public static void main(String[] args)
	{
		Scanner sc;
		int var ;
		int index = 0;
		int numOfRules;
		RulesDataStructure DS ;
		try 
		{
			sc = new Scanner(new File(FILENAME));
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
						
			//DS.printRulesArray();
			DS.printHashTable();
			
			
			
			
			
			
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	

}
