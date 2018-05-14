package Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sound.midi.Track;

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
		boolean flag;
		for(Edge<Integer> edge : graph.getAllEdges()) {
			flag= true;
			x=findIdInVertexCC(super_graph,(int) edge.getVertex1().getId());
			y=findIdInVertexCC(super_graph,(int) edge.getVertex2().getId());
//			if(findIdInVertexCC(graph,(int) edge.getVertex1().getId())!=findIdInVertexCC(graph,(int)edge.getVertex2().getId())&&findIdInVertexCC(graph,(int) edge.getVertex1().getId())!=-1) {
//				super_graph.addEdge(id1, id2);
//			}
		//	Vertex<Integer> v1 = new Vertex<Integer>(x);
			//Vertex<Integer> v2 = new Vertex<Integer>(y);
			for(Edge<Integer> e : super_graph.getAllEdges()) {
				if(e.getVertex1().getId()==x && e.getVertex2().getId()==y) {
					flag = false;
					break;
				}
			}

			if( -1==y || x==-1 || !flag) {
//				System.out.println("Cannot find id of vertrx");
				continue;
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
	public ArrayList<Vertex<Integer>> getParent(Vertex<Integer> v){
		ArrayList<Vertex<Integer>> parentArray = new ArrayList<>();
		for(Edge<Integer> edge: super_graph.getAllEdges()) {
			if(edge.getVertex2().equals(v)) {
				parentArray.add(edge.getVertex1());
			}
		}
		return parentArray;
	} 
	
	
    /// find vertex of original graph in super graph, return id of where in superGraph it was found (id of superGraph vertex) -1 if not found  
	public int findIdInVertexCC(Graph<Integer> graph,int id) {
		if(graph==null)
			return -1;
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
//	
//	public String toString() {
//		
//	}
	public void printGraph() {
		System.out.println(super_graph);
		System.out.println();
		for(Vertex<Integer> v : super_graph.getAllVertex()) {
			System.out.print("Vertex: "+v.getId());
			
			System.out.print(":=> Vertex of cc are: ");
			for(Vertex<Integer> vertexInCC: v.getCCList() ) {
				System.out.print(vertexInCC.getId()+"-> ");
				
			}
			System.out.println("Parent are: "+ getParent(v));
			System.out.println();
		}	
	}
	
}
