package Graph;

import java.util.List;
import java.util.Set;

public class SuperGraph {
	
	//private Graph<Integer> graph;
	private Graph<Integer> super_graph;
	public SuperGraph(Graph<Integer> graph) {
		
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> CClist = scc.scc(graph);
		
		super_graph = new Graph<>(true);
		int id =1;
		for(Set<Vertex<Integer>> set: CClist) 
		{
			super_graph.addSingleVertex(id);
			id++;
		}
	}

//	public void setGraph(Graph<Integer> graph) {
//		this.graph=graph;
//	}
	public Graph<Integer> getSuperGraph() {
		return this.super_graph;
	}
//	public Graph<Integer> getGraph() {
//		return this.graph;
//	}
	
}
