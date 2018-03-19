package Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

public class Test_in_calss {
	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(true);

		
		g.addEdge(1, 2, 1);
		
		g.addEdge(2, 3, 1);
		
		g.addEdge(3, 4, 1);
		g.addEdge(3, 5, 1);
		
		g.addEdge(4, 1, 1);
		g.addEdge(5, 7, 1);
		g.addEdge(6, 3, 1);
		
		g.addEdge(10, 6, 1);
		g.addEdge(7, 8, 1);
		g.addEdge(8, 9, 1);
		g.addEdge(9, 10, 1);
		g.addEdge(10, 7, 1);

	

		System.out.println(g);
		Graph<Integer> A = new Graph<>(false);
		Vertex<Integer> s= new Vertex<Integer>(1);
		Collection<Integer> N=new java.util.LinkedList<Integer>();
		for(Vertex<Integer> v:g.getAllVertex()) 
		{
			N.add((int) v.getId());

		}

		//System.out.println(N);
		g.constaruction(g,s,N,A);
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		System.out.println("connected component BEFORE dismentle: ");

		System.out.println("A graph: ");
		System.out.println(A);
		 StronglyConnectedComponent scc = new StronglyConnectedComponent();
	        List<Set<Vertex<Integer>>> result = scc.scc(g);

	        //print the result
	        result.forEach(set -> {
	            set.forEach(v -> System.out.print(v.getId() + " "));
	            System.out.println();
	        });
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		ArrayList<Vertex<Integer>> arr= g.dismantlingStrongestCC(g,A);
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
