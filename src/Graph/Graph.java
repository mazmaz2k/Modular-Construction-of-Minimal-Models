package Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import Rules.RulesDataStructure;
import Rules.LinkedList.Node;
public class Graph<T>{

    private List<Edge<T>> allEdges;
    private Map<Long,Vertex<T>> allVertex;
    boolean isDirected = false;
    
    public Graph(boolean isDirected){
        allEdges = new ArrayList<Edge<T>>();
        allVertex = new HashMap<Long,Vertex<T>>();
        this.isDirected = isDirected;
    }
    
    public void addEdge(long id1, long id2){
        addEdge(id1,id2,0);
    }
    
    //This works only for directed graph because for undirected graph we can end up
    //adding edges two times to allEdges
    public void addVertex(Vertex<T> vertex){
        if(allVertex.containsKey(vertex.getId())){
            return;
        }
        allVertex.put(vertex.getId(), vertex);
        for(Edge<T> edge : vertex.getEdges()){
            allEdges.add(edge);
        }
    }
    
    public Vertex<T> addSingleVertex(long id){
        if(allVertex.containsKey(id)){
            return allVertex.get(id);
        }
        Vertex<T> v = new Vertex<T>(id);
        allVertex.put(id, v);
        return v;
    }
    
    public Vertex<T> getVertex(long id){
        return allVertex.get(id);
    }
    
    public void addEdge(long id1,long id2, int weight){
        Vertex<T> vertex1 = null;
        if(allVertex.containsKey(id1)){
            vertex1 = allVertex.get(id1);
        }else{
            vertex1 = new Vertex<T>(id1);
            allVertex.put(id1, vertex1);
        }
        Vertex<T> vertex2 = null;
        if(allVertex.containsKey(id2)){
            vertex2 = allVertex.get(id2);
        }else{
            vertex2 = new Vertex<T>(id2);
            allVertex.put(id2, vertex2);
        }

        Edge<T> edge = new Edge<T>(vertex1,vertex2,isDirected,weight);
        allEdges.add(edge);
        vertex1.addAdjacentVertex(edge, vertex2);
        if(!isDirected){
            vertex2.addAdjacentVertex(edge, vertex1);
        }

    }
    
    public List<Edge<T>> getAllEdges(){
        return allEdges;
    }
    
    public Collection<Vertex<T>> getAllVertex(){
        return allVertex.values();
    }
    public void setDataForVertex(long id, T data){
        if(allVertex.containsKey(id)){
            Vertex<T> vertex = allVertex.get(id);
            vertex.setData(data);
        }
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        for(Edge<T> edge : getAllEdges()){
            buffer.append(edge.getVertex1() + " " + edge.getVertex2() + " " + edge.getWeight());
            buffer.append("\n");
        }
        return buffer.toString();
    }

 
    
//	// A function used by DFS
//	void DFSUtil(Vertex <Object> v,boolean visited[])
//	{
//		// Mark the current node as visited and print it
//		visited[(int)v.getId()] = true;
//		System.out.print(v+" ");
//
//		// Recur for all the vertices adjacent to this vertex
//		//List<Vertex<T>> adjcentVertex = v.getAdjacentVertexes()	;	
//		Iterator<Vertex<Object>> i =  v.getAdjacentVertexes().listIterator();
//		
//		while (i.hasNext())
//		{
//			Vertex<Object> n = i.next();
//			if (!visited[(int) n.getId()])
//				DFSUtil(n, visited);
//		}
//	}
//    
//	// The function to do DFS traversal. It uses recursive DFSUtil()
//	void DFS(Vertex <Object> v)
//	{
//		// Mark all the vertices as not visited(set as
//		// false by default in java)
//		boolean visited[] = new boolean[allVertex.size()+1];
//
//		// Call the recursive helper function to print DFS traversal
//		DFSUtil(v, visited);
//		
//	}
    
    
    public static Graph<Integer> initGraph(RulesDataStructure DS ,int numOfRules) 
	{


		Graph<Integer> graph = new Graph<>(true);
		Node n1 ,n2;
		for (int i = 0; i < numOfRules; i++) 
		{
			n1 =DS.RulesArray[i].body.head;
			n2=DS.RulesArray[i].head.head;
			while(n1!=null)
			{
				while(n2!=null)
				{
					graph.addEdge(n1.var,n2.var);
					n2=n2.next;
				}
				n1=n1.next;
			}

		}
		System.out.println("This is the Graph:");
		System.out.println(graph);
		//        graph.addEdge(7, 1);
		//        graph.addEdge(1, 2);
		//        graph.addEdge(2, 7);
		//        graph.addEdge(1, 3);
		//        graph.addEdge(3, 4);
		//        graph.addEdge(4, 5);
		//        graph.addEdge(5, 3);
		//        graph.addEdge(5, 6);

		//System.out.println(graph);
		// Create a graph given in the above diagram
//		System.out.println("Following are size of the strongly connected components in given graph ");
		// graph.printSCCs();
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(graph);
		int max=0;
		for(Set<Vertex<Integer>> s: result) {
		//	System.out.println(s.size());
			if(max<s.size())
				max=s.size();
		}
		System.out.println("Following are THE strongly connected componentsin given graph ");
		for(Set<Vertex<Integer>> s: result) {
			if(s.size()==max) {
				s.forEach(v -> System.out.print(v.getId() + "-> "));
				System.out.println();
			}
		}
//		result.forEach(set -> {
//			System.out.println(set.size()); 
//		});
		System.out.println();

		System.out.println("Following are ALL strongly connected componentsin given graph ");
		//print the result
	
		result.forEach(set -> {
			
				set.forEach(v -> System.out.print(v.getId() + "-> "));
				System.out.println();
			
		});

		return graph;	
	} 
}