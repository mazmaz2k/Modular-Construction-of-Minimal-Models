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
	public static Graph<Integer> readeGraphFromFile(){


		Scanner sc;
		int v1,v2;
		int idx=0;
		//		String var="";
		Graph<Integer> graph = new Graph<Integer>(true);
		try 
		{

			String Path = ".//GraphFile.txt" ;
			sc = new Scanner(new File(Path));//read file
			StringTokenizer st = null;

			while (sc.hasNextLine()) 
			{
//				System.out.println("idx is: "+idx);
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
					graph.addEdge(v1, v2, 1);
				}
				idx++;
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
		Graph<Integer> g=readeGraphFromFile();
		System.out.println(g);

		Graph<Integer> A = new Graph<>(false);
		Vertex<Integer> s= new Vertex<Integer>(1);
		Collection<Integer> N=new java.util.LinkedList<Integer>();
		for(Vertex<Integer> v:g.getAllVertex()) 
		{
			N.add((int) v.getId());

		}
		
		
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(g);

		//print the result
		result.forEach(set -> {
			set.forEach(v -> System.out.print(v.getId() + " "));
			System.out.println();
		});
		
		ArrayList<Vertex<Integer>> vertexToremove = Graph.vertexSeparator(result.get(0), g);
		System.out.println("Vertex to remove: " + vertexToremove);

		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
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





	}
}
