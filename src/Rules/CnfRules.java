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

	public static Graph<Integer> initGraph(RulesDataStructure DS ,int numOfRules) {

		Graph<Integer> graph = new Graph<>(true);
		Node n1 ,n2;
		for (int i = 0; i < numOfRules; i++) 
		{
			n1 =DS.RulesArray[i].body.head;
			n2=DS.RulesArray[i].head.head;
			while(n1!=null)
			{
				while(n2!=null)
				{
					graph.addEdge(n1.var,n2.var);
					n2=n2.next;
				}
				n1=n1.next;
			}

		}
		System.out.println("This is the Graph:");
		System.out.println(graph);
		//        graph.addEdge(7, 1);
		//        graph.addEdge(1, 2);
		//        graph.addEdge(2, 7);
		//        graph.addEdge(1, 3);
		//        graph.addEdge(3, 4);
		//        graph.addEdge(4, 5);
		//        graph.addEdge(5, 3);
		//        graph.addEdge(5, 6);

		//System.out.println(graph);
		// Create a graph given in the above diagram
		System.out.println("Following are strongly connected components "+
				"in given graph ");
		// graph.printSCCs();
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(graph);
		result.forEach(set -> {
			System.out.println(set.size()); 

		});
		//print the result
		result.forEach(set -> {
			set.forEach(v -> System.out.print(v.getId() + "-> "));
			System.out.println();
		});

		return graph;	
	} 

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

			Graph<Integer> graph =new Graph<>(true); 
			//DS.printHashTable();
			//DS.placeValue(-1, false);
			//DS.placeValue(3, false);
			//DS.printHashTable();
			graph=initGraph(DS,numOfRules);


			Scanner console = new Scanner(System.in); 
			//(console!=null & Integer.class.isInstance(console.nextInt())) {
			try {
				//System.out.println(console.nextInt()+"ddddddddddddddd");
				DS.placeValue(console.nextInt(), false);
			}
			catch( Exception e)
			{
				e.printStackTrace();
			}
			finally {
				console.close();
				DS.printRulesArray();
				DS.printHashTable();
				graph=null;
				graph=initGraph(DS,numOfRules);

			}
			//}


		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



}
