package Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

public class Vertex_size_10 {

	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(true);
		
		g.addEdge(1, 5, 1);
		g.addEdge(1, 8, 1);

		g.addEdge(2, 3, 1);

		g.addEdge(3, 6, 1);
		g.addEdge(3, 7, 1);
		g.addEdge(3, 2, 1);
		
		g.addEdge(4, 6, 1);
		
		g.addEdge(5, 1, 1);
		g.addEdge(5, 8, 1);
		
		g.addEdge(6, 3, 1);
		g.addEdge(6, 4, 1);
		
		g.addEdge(7, 3, 1);
		g.addEdge(7, 9, 1);
		g.addEdge(7, 8, 1);
//		g.addEdge(7, 6, 1);
		
		g.addEdge(8, 1, 1);
		g.addEdge(8, 5, 1);
		g.addEdge(8, 9, 1);

		g.addEdge(9, 8, 1);
		g.addEdge(9, 7, 1);


		System.out.println(g);
		Graph<Integer> A = new Graph<>(false);
		Vertex<Integer> s= new Vertex<Integer>(4);
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
		System.out.println("connected component BEFORE dismentle: ");

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
