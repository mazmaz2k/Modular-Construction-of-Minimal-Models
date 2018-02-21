package Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

public class Vertexs_size_20 {

	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(true);
		g.addEdge(10, 4, 1);
		g.addEdge(10, 12, 1);
		g.addEdge(10, 5, 1);
		
		g.addEdge(11, 6, 1);
		g.addEdge(11, 7, 1);
		
		g.addEdge(1, 19, 1);
		
		g.addEdge(12, 10, 1);
		
		g.addEdge(13, 4, 1);
		g.addEdge(13, 17, 1);
		
		g.addEdge(14, 3, 1);
		g.addEdge(14, 7, 1);
		g.addEdge(14, 9, 1);
		
		g.addEdge(15, 2, 1);
		g.addEdge(15, 8, 1);
		g.addEdge(15, 10, 1);
		g.addEdge(15, 16, 1);
		g.addEdge(15, 7, 1);

		
		g.addEdge(16, 8, 1);
		g.addEdge(16, 15, 1);
		g.addEdge(16, 17, 1);
		
		g.addEdge(17, 16, 1);
		g.addEdge(17, 8, 1);
		g.addEdge(17, 13, 1);
		
		g.addEdge(18, 19, 1);
//		g.addEdge(18, 20, 1);
		
		g.addEdge(19, 1, 1);
		g.addEdge(19, 18, 1);
		
		g.addEdge(20, 2, 1);
		
		g.addEdge(2, 8, 1);
		g.addEdge(2, 15, 1);
		
		g.addEdge(3, 5, 1);
		g.addEdge(3, 7, 1);
		g.addEdge(3, 9, 1);
		g.addEdge(3, 14, 1);
		g.addEdge(3, 18, 1);
		
		g.addEdge(4, 10, 1);
		g.addEdge(4, 13, 1);
		
		g.addEdge(5, 3, 1);
		g.addEdge(5, 9, 1);
		
		g.addEdge(6, 11, 1);
		
		g.addEdge(7, 3, 1);
		g.addEdge(7, 9, 1);
		g.addEdge(7, 14, 1);
//		g.addEdge(7, 6, 1);
		
		g.addEdge(8, 2, 1);
		g.addEdge(8, 15, 1);
		g.addEdge(8, 16, 1);
		g.addEdge(8, 17, 1);

		g.addEdge(9, 3, 1);
		g.addEdge(9, 5, 1);
		g.addEdge(9, 7, 1);
		g.addEdge(9, 14, 1);

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
