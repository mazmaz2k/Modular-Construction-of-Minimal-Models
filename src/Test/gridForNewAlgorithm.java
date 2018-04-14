package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

public class gridForNewAlgorithm {
	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(true);

		g.addEdge(1, 2, 1);
		g.addEdge(1, 5, 1);


		g.addEdge(2, 3, 1);

		g.addEdge(2, 6, 1);
		g.addEdge(3, 5, 1);
		g.addEdge(3, 7, 1);

		g.addEdge(4, 8, 1);

		g.addEdge(5, 6, 1);
		g.addEdge(5, 9, 1);

		g.addEdge(6, 7, 1);
		g.addEdge(6, 10, 1);

		g.addEdge(7, 8, 1);
		g.addEdge(7, 11, 1);
		g.addEdge(8, 12, 1);

		g.addEdge(9, 13, 1);

		g.addEdge(9, 10, 1);
		g.addEdge(10, 11, 1);
		g.addEdge(10, 14, 1);

		g.addEdge(11, 12, 1);
		g.addEdge(11, 15, 1);

		g.addEdge(12, 16, 1);
		g.addEdge(13, 14, 1);
		g.addEdge(14, 15, 1);

		g.addEdge(15, 16, 1);
		////this is not connected component

		g.addEdge(2, 1, 1);
		g.addEdge(5, 1, 1);


		g.addEdge(3, 2, 1);

		g.addEdge(6, 2, 1);
		g.addEdge(5, 3, 1);
		g.addEdge(4, 3, 1);
		g.addEdge(3, 4, 1);

		g.addEdge(7, 3, 1);

		g.addEdge(8, 4, 1);

		g.addEdge(6, 5, 1);
		g.addEdge(9, 5, 1);

		g.addEdge(7, 6, 1);
		g.addEdge(10, 6, 1);

		g.addEdge(8, 7, 1);
		g.addEdge(11, 7, 1);
		g.addEdge(12, 8, 1);

		g.addEdge(13, 9, 1);

		g.addEdge(10, 9, 1);
		g.addEdge(11, 10, 1);
		g.addEdge(14, 10, 1);

		g.addEdge(12, 11, 1);
		g.addEdge(15, 11, 1);

		g.addEdge(16, 12, 1);
		g.addEdge(14, 13, 1);
		g.addEdge(15, 14, 1);

		g.addEdge(16, 15, 1);

		System.out.println(g);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(g);
		//		System.out.println("Connected component in the graph:");
		//		//print the result -connected component in the graph !
		//		result.forEach(set -> {
		//			set.forEach(v -> System.out.print(v.getId() + " "));
		//			System.out.println();
		//		});

		ArrayList<Vertex<Integer>> vertexToremove = Graph.vertexSeparator(result.get(0), g);

		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		g=g.removeVertex(vertexToremove);
		//		System.out.println(g);
		//		 StronglyConnectedComponent scc = new StronglyConnectedComponent();
		System.out.println("Vertex to remove: " + vertexToremove);
		System.out.println("connected component After dismentle: ");
		result = scc.scc(g);

		//print the result
		result.forEach(set -> {
			set.forEach(v -> System.out.print(v.getId() + " "));
			System.out.println();
		});



	}

}
