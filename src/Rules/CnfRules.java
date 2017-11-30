package Rules;
import java.io.File;
//import Rules.LinkedList.Node;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class CnfRules
{
	private static final String FILENAME = "./CnfFile.txt" ;
	
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
						
			DS.printRulesArray();
			DS.placeValue(2, false);
			DS.printRulesArray();

			/*for (int i = 0; i < numOfRules; i++) 
			{
				Node n1 =DS.RulesArray[0].body.head;
				Node n2=DS.RulesArray[0].head.head;
				while(n1!=null)
				{
					while(n2!=null)
					{
						//addedge(n1.var,n2.var)
						n2=n2.next;
					}
					n1=n1.next;
				}
				
			}
			
			
			*/
			
			
			
			
			
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	

}
