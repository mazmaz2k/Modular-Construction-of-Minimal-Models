package Graph;

import java.util.List;
import java.util.Set;
import Graph.Graph;
public class SuperGraph {
	
	//private Graph<Integer> graph;
	private Graph<Integer> super_graph;
	public SuperGraph(Graph<Integer> graph) {
		super_graph = new Graph<>(true);
		if(graph==null) {
			System.out.println("graph is empty!!!!");
			return;
		}
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> CClist = scc.scc(graph);
		///check if cclist ==null
		int id =1;
		for(Set<Vertex<Integer>> set: CClist) 
		{
			super_graph.addSingleVertex(id);
			super_graph.getVertex(id).setCCList(set);
			id++;
		}
		int x,y;
		for(Edge<Integer> edge : graph.getAllEdges()) {
			x=findIdInVertexCC(graph,(int) edge.getVertex1().getId());
			y=findIdInVertexCC(graph,(int) edge.getVertex2().getId());
//			if(findIdInVertexCC(graph,(int) edge.getVertex1().getId())!=findIdInVertexCC(graph,(int)edge.getVertex2().getId())&&findIdInVertexCC(graph,(int) edge.getVertex1().getId())!=-1) {
//				super_graph.addEdge(id1, id2);
//			}
			if( -1==y || x==-1) {
				System.out.println("Cannot find id of vertrx");
			}else if(x!=y) {
				super_graph.addEdge(x, y);

			}
			
			
		}
		
	}	
//	@Override
//	public String toString() {
//		
//		
//		
//	}
	
	
	
    /// find vertex of original graph in super graph, return id of where in superGraph it was found (id of superGraph vertex) -1 if not found  
	public int findIdInVertexCC(Graph<Integer> graph,int id) {
		for(Vertex<Integer> v : graph.getAllVertex()) {
			for(Vertex<Integer> vertexInCC: v.getCCList() ) {
				if(vertexInCC.getId()==id) {
					return (int) v.getId();
				}
				
			}
			
		}	
		return -1;
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
