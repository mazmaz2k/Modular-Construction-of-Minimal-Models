package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

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
		System.out.println(g);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(g);

		//print the result
		result.forEach(set -> {
			set.forEach(v -> System.out.print(v.getId() + " "));
			System.out.println();
		});
		
		ArrayList<Vertex<Integer>> vertexToremove = Graph.vertexSeparator(result.get(0), g);
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



	}

}
