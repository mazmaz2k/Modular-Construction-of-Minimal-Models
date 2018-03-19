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

public class graphTests {

	public static Graph<Integer> readeGraphFromFile(){


			Scanner sc;
			int v1,v2;
			int idx=0;
//			String var="";
			Graph<Integer> graph = new Graph<Integer>(true);
			try 
			{

				String Path = ".//GraphFile.txt" ;
				sc = new Scanner(new File(Path));//read file
				StringTokenizer st = null;

				while (sc.hasNextLine()) 
				{
					System.out.println("idx is: "+idx);
					//v1 = sc.nextInt(); 
					st = new StringTokenizer(sc.nextLine()," ");
//					while(sc.hasNext())
//					{
//						v2 = sc.nextInt();
//						graph.addEdge(v1, v2);
//						
//					}
					v1 = Integer.parseInt(st.nextToken());
					while(st.hasMoreTokens())
					{
						
						v2 = Integer.parseInt(st.nextToken());
						graph.addEdge(v1, v2);
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

//		System.out.println("N is: "+N);
		Graph.constaruction(g,s,N,A);
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		System.out.println("A graph: ");
		System.out.println(A);
		System.out.println("connected component BEFORE dismentle: ");

		 StronglyConnectedComponent scc = new StronglyConnectedComponent();
	        List<Set<Vertex<Integer>>> result = scc.scc(g);

	        //print the result
	        result.forEach(set -> {
	            set.forEach(v -> System.out.print(v.getId() + " "));
	            System.out.println();
	        });
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		ArrayList<Vertex<Integer>> arr= Graph.dismantlingStrongestCC(g,A);
		System.out.println("Vertex to remove: "+arr);
		g=g.removeVertex(arr);
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
