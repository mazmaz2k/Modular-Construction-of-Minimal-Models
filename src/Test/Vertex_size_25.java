package Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

public class Vertex_size_25 {

	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(true);

		
		g.addEdge(1, 15, 1);

		g.addEdge(2, 17, 1);
		
		g.addEdge(3, 6, 1);
		g.addEdge(3, 9, 1);

		
		g.addEdge(4, 5, 1);
		g.addEdge(4, 14, 1);
		g.addEdge(4, 21, 1);
		g.addEdge(4, 24, 1);
		
		g.addEdge(5, 4, 1);
		g.addEdge(5, 11, 1);
		
		g.addEdge(6, 3, 1);
		g.addEdge(6, 9, 1);
		
		g.addEdge(7, 8, 1);
		g.addEdge(7, 12, 1);
		g.addEdge(7, 17, 1);
  		g.addEdge(7, 21, 1);
		
		g.addEdge(8, 7, 1);
		g.addEdge(8, 13, 1);
	
		g.addEdge(9, 3, 1);
		g.addEdge(9, 6, 1);
		g.addEdge(9, 13, 1);
		g.addEdge(9, 14, 1);

		g.addEdge(10, 16, 1);
		g.addEdge(10, 18, 1);
		g.addEdge(10, 22, 1);
		g.addEdge(10, 25, 1);
		
		g.addEdge(11, 5, 1);
		g.addEdge(11, 22, 1);
		
		g.addEdge(12, 7, 1);
		g.addEdge(12, 21, 1);
		
		g.addEdge(13, 8, 1);
		g.addEdge(13, 9, 1);
		g.addEdge(13, 18, 1);
		
		g.addEdge(14, 4, 1);
		g.addEdge(14, 18, 1);
		g.addEdge(14, 23, 1);
		
		g.addEdge(15, 1, 1);
		g.addEdge(15, 19, 1);
		g.addEdge(15, 20, 1);
		
		g.addEdge(16, 10, 1);
		g.addEdge(16, 25, 1);
		
		g.addEdge(17, 2, 1);
		g.addEdge(17, 7, 1);
		
		g.addEdge(18, 10, 1);
		g.addEdge(18, 13, 1);
		g.addEdge(18, 14, 1);
		
		g.addEdge(19, 15, 1);
		g.addEdge(19, 20, 1);
		g.addEdge(19, 23, 1);
		
		g.addEdge(20, 15, 1);
		g.addEdge(20, 19, 1);
		
		g.addEdge(21, 4, 1);
		g.addEdge(21, 7, 1);
		g.addEdge(21, 12, 1);
		
		g.addEdge(22, 10, 1);
		g.addEdge(22, 11, 1);
		g.addEdge(22, 23, 1);
		
		g.addEdge(23, 14, 1);
		g.addEdge(23, 19, 1);
		g.addEdge(23, 22, 1);

		g.addEdge(24, 4, 1);

		g.addEdge(25, 10, 1);
		g.addEdge(25, 16, 1);

		System.out.println(g);
		Graph<Integer> A = new Graph<>(false);
		Vertex<Integer> s= new Vertex<Integer>(10);
		Collection<Integer> N=new java.util.LinkedList<Integer>();
		for(Vertex<Integer> v:g.getAllVertex()) 
		{
			N.add((int) v.getId());

		}

		//System.out.println(N);
		g.constaruction(g,s,N,A);
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
//		System.out.println("A graph: ");
//		System.out.println(A);
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
		result = scc.scc(g);

	        //print the result
	        result.forEach(set -> {
	            set.forEach(v -> System.out.print(v.getId() + " "));
	            System.out.println();
	        });


	}

}

