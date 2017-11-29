package Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph<T>{

    private List<Edge<T>> allEdges;
    private Map<Integer,Vertex<T>> allVertex;
    boolean isDirected ;
    
    public Graph(boolean isDirected){
        allEdges = new ArrayList<Edge<T>>();
        allVertex = new HashMap<Integer,Vertex<T>>();
        this.isDirected = isDirected;
    }
    
    public void addEdge(int id1, int id2){
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
    
    public Vertex<T> addSingleVertex(int id){
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
    
    public void addEdge(int id1,int id2, int weight){
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
//    public void setDataForVertex(long id, T data){
//        if(allVertex.containsKey(id)){
//            Vertex<T> vertex = allVertex.get(id);
//            vertex.setData(data);
//        }
//    }

//    @Override
//    public String toString(){
//        StringBuffer buffer = new StringBuffer();
//        for(Edge<T> edge : getAllEdges()){
//            buffer.append(edge.getVertex1() + " -> " + edge.getVertex2() + " weight " + edge.getWeight());
//            buffer.append("\n");
//        }
//        return buffer.toString();
//    }

    @Override
    public String toString() {
    	StringBuffer sb=new StringBuffer();
    	for(Vertex<T> v : getAllVertex()) {
    		sb.append("v num:"+v.id +" ");
    		for(Edge<T> edge : getAllEdges()){
    			if(v.equals(edge.getVertex1()))
    				sb.append("->" +edge.getVertex2()+" ");
    			
    		}
    		sb.append("\n");
    	}
   
    	return sb.toString();
    }
    
    
	// A function used by DFS
	void DFSUtil(Vertex <Object> v,boolean visited[])
	{
		// Mark the current node as visited and print it
		visited[v.getId()] = true;
		System.out.print(v+" ");

		// Recur for all the vertices adjacent to this vertex
		//List<Vertex<T>> adjcentVertex = v.getAdjacentVertexes()	;	
		Iterator<Vertex<Object>> i =  v.getAdjacentVertexes().listIterator();
		
		while (i.hasNext())
		{
			Vertex<Object> n = i.next();
			if (!visited[n.getId()])
				DFSUtil(n, visited);
		}
	}
    
	// The function to do DFS traversal. It uses recursive DFSUtil()
	void DFS(Vertex <Object> v)
	{
		// Mark all the vertices as not visited(set as
		// false by default in java)
		boolean visited[] = new boolean[allVertex.size()+1];

		// Call the recursive helper function to print DFS traversal
		DFSUtil(v, visited);
		
	}
    
}



