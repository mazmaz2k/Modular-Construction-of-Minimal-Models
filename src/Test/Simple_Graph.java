/**
 * 
 */
package Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

/**
 * @author mazma
 *
 */
public class Simple_Graph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(true);
		g.addEdge(10, 3, 1);
		g.addEdge(10, 4, 1);
		g.addEdge(1, 10, 1);
		g.addEdge(4, 1, 1);
		g.addEdge(3, 1, 1);
		g.addEdge(1, 5, 1);
		g.addEdge(1, 2, 1);
		g.addEdge(2, 1, 1);
		g.addEdge(6, 1, 1);
		g.addEdge(6, 5, 1);
		g.addEdge(5, 6, 1);
		g.addEdge(2, 6, 1);
		g.addEdge(5, 2, 1);
		System.out.println(g);
		Graph<Integer> A = new Graph<>(false);
		Vertex<Integer> s= new Vertex<Integer>(10);
		Collection<Integer> N=new java.util.LinkedList<Integer>();
		for(Vertex<Integer> v:g.getAllVertex()) 
		{
			N.add((int) v.getId());

		}

		//System.out.println(N);

		Graph.constaruction(g,s,N,A);
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		System.out.println("A graph: ");
		System.out.println(A);
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		ArrayList<Vertex<Integer>> arr= Graph.dismantlingStrongestCC(g,A);
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
	    List<Set<Vertex<Integer>>> result = scc.scc(g);
		System.out.println("connected component BEFORE dismentle: ");

	    result.forEach(set -> {
            set.forEach(v -> System.out.print(v.getId() + " "));
            System.out.println();
        });
		System.out.println("Vertex to remove: "+arr);
		g=g.removeVertex(arr);
		result = scc.scc(g);
		System.out.println("connected component After dismentle: ");

        //print the result
        result.forEach(set -> {
            set.forEach(v -> System.out.print(v.getId() + " "));
            System.out.println();
        });


	}

}
