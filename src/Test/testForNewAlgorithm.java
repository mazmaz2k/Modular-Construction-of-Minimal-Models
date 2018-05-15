package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.SuperGraph;
import Graph.Vertex;
import Rules.LinkedList;

public class testForNewAlgorithm {

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
		
	
		g.addEdge(95, 96, 1);
		g.addEdge(96, 92, 1);
		g.addEdge(92, 95, 1);

		g.addEdge(94, 92, 1);
		
//		System.out.println(g);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(g);

		//print the result
		result.forEach(set -> {
			set.forEach(v -> System.out.print(v.getId() + " "));
			System.out.println();
		});
		
		ArrayList<Vertex<Integer>> vertexToremove = Graph.vertexSeparator( g);
		System.out.println("Vertex to remove: " + vertexToremove);
		
		
//		g=g.removeVertex(vertexToremove);
//		System.out.println(g);
		//StronglyConnectedComponent scc = new StronglyConnectedComponent();
		System.out.println("connected component After dismentle: ");
		result = scc.scc(g);
		ArrayList<Integer> CLA = new ArrayList<>();
		for(Vertex<Integer> v: vertexToremove) {
			CLA.add((int) v.getId());
		}
		//print the result
//		result.forEach(set -> {
//			set.forEach(v -> System.out.print(v.getId() + " "));
//			System.out.println();
//		});
//		
//		
		LinkedList vertexCI = Graph.IntegrityConstraintHandle( g,CLA);
		System.out.println("CLA vertex:"+ CLA);
		System.out.println("CI vertex: " +vertexCI);

		System.out.println("-------------------------------------------------------------------------------------------");
		SuperGraph super_graph = new SuperGraph(g);
		super_graph.printGraph();
		System.out.println("------------------------------");



	}

}
