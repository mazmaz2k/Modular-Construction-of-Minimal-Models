package Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

public class readGraphFromFileNewAlgorithm {
	public static Graph<Integer> readeGraphFromFile(String path){


		Scanner sc;
		int v1,v2;
		//		String var="";
		Graph<Integer> graph = new Graph<Integer>(true);
		try 
		{

			sc = new Scanner(new File(path));//read file
			StringTokenizer st = null;

			while (sc.hasNextLine()) 
			{
				st = new StringTokenizer(sc.nextLine()," ");
				if(!st.hasMoreTokens())
				{
					continue;
				}
				v1 = Integer.parseInt(st.nextToken());
				graph.addSingleVertex(v1);
				while(st.hasMoreTokens())
				{
					

					v2 = Integer.parseInt(st.nextToken());
					if(v1 == v2) {
						continue;
					}
					graph.addEdge(v1, v2, 1);
				}
			}
			System.out.println("File was read successfully");
		}catch (FileNotFoundException ex)
		{
			// TODO Auto-generated catch block
			ex.printStackTrace();
			System.out.println("Error on reading the file");


		}

		return graph;
	} 

	public static void main(String[] args) {
		String path = ".//GraphFile.txt";
//		String path = args[0];
		Graph<Integer> g=readeGraphFromFile(path);
		//		System.out.println(g);

		
//		/**memory usage checking**/
//		Runtime runtime = Runtime.getRuntime();
//        // Run the garbage collector
//        runtime.gc();
//        // Calculate the used memory
//        double memory = runtime.totalMemory() - runtime.freeMemory();
//       

		
		
		/***run time checking*/
		long startTime,endTime,totalTime;//in mili sec
		
		startTime = System.currentTimeMillis();
		//your program




		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(g);
		if(result.get(0).size()!=g.getAllVertex().size()) {
			System.out.println("not cc");
			return ;
		}

		//print the result
		result.forEach(set -> {
			set.forEach(v -> System.out.print(v.getId() + " "));
			System.out.println();
		});


		ArrayList<Vertex<Integer>> vertexToremove = Graph.vertexSeparator(g);
		System.out.println("point 1");

		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		System.out.println("Vertex to remove: " + vertexToremove);
		g=g.removeVertex(vertexToremove);
		//		System.out.println(g);
		//		 StronglyConnectedComponent scc = new StronglyConnectedComponent();
		System.out.println("connected component After dismentle: ");
		result = scc.scc(g);

		//print the result
		result.forEach(set -> {
			set.forEach(v -> System.out.print(v.getId() + " "));
			System.out.println();
		});

		/**memory usage checking**/
		Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        double memory = runtime.totalMemory() - runtime.freeMemory();
       System.out.println("Memory usage: "+ memory/(1024*1024)+" MB");

		 endTime   = System.currentTimeMillis();
		 totalTime = endTime - startTime;
		 System.out.print("Total Run Time in mili seconds: " + totalTime);
	}
}
