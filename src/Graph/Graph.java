package Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import Rules.RulesDataStructure;
import Rules.LinkedList;
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

//    /// find vertex of original graph in super graph, return id of where in superGraph it was found (id of superGraph vertex) -1 if not found  
//	public int findIdInVertexCC(Graph<Integer> graph,int id) {
//		for(Vertex<Integer> v : graph.getAllVertex()) {
//			for(Vertex<Integer> vertexInCC: v.getCCList() ) {
//				if(vertexInCC.getId()==id) {
//					return (int) v.getId();
//				}
//				
//			}
//			
//		}	
//		return -1;
//	}
	
	//Initialize graph from Rules data structure
	//also return strongest connected component of the graph 
	
	
	public static LinkedList sourceOfGraph(Graph<Integer>  graph)
	{
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(graph);
		LinkedList s = new LinkedList();
		if(result.isEmpty())
		{
			return s;
		}
		for (Vertex<Integer> vertex : result.get(0)) 
		{
				s.addAtTail((int)vertex.getId());
		}
		
		
		return s;
	}
	
	
	public static Graph<Integer> initGraph(RulesDataStructure DS ,int numOfRules) 
	{


		Graph<Integer> graph = new Graph<>(true);
		Node n1 ,n2;
		for (int i = 0; i < numOfRules; i++) 
		{
			if(DS.RulesArray[i]!=null)
			{
				n1 =DS.RulesArray[i].body.head;
				while(n1!=null)
				{
					n2=DS.RulesArray[i].head.head;
					while(n2!=null)
					{
						graph.addEdge(n1.var,n2.var);
						n2=n2.next;
					}
					n1=n1.next;
				}
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
		/*StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(graph);
*/
		//System.out.println(graph);
		// Create a graph given in the above diagram
		//		System.out.println("Following are size of the strongly connected components in given graph ");
		// graph.printSCCs();
	
		
		//find strongest connected component
		/*int max=0; 
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
		System.out.println();*/

	/*	System.out.println("Following are ALL strongly connected componentsin given graph ");
		//print the result
		

		result.forEach(set -> {

			set.forEach(v -> System.out.print(v.getId() + "-> "));
			System.out.println();

		});*/

		return graph;	
	} 
	//input original graph and set of vertexes and return (create) a smaller graph from those set of vertexes 
	
	public static Graph<Integer> copyGraph(Set<Vertex<Integer>> setOfVertex,Graph<Integer> oldGraph) {
		Graph<Integer> newGraph = new Graph<>(true);
		
		for(Vertex<Integer> v:setOfVertex) {
			
			for(Edge<Integer> e : oldGraph.getAllEdges()) {
				
				if( v.getId()==e.getVertex1().getId()) {
					newGraph.addEdge(v.getId(), e.getVertex2().getId());
					//System.out.println("v1: "+v.getId()+" v2: "+e.getVertex2().getId());
				}

			}
		}
		
		//System.out.println(newGraph.getAllEdges().toString());

		return newGraph;
	} 
	
	public Graph<Integer> uniqueGraph(Graph<Integer> oldGraph){
		Graph<Integer> uniqueGraph = new Graph<>(true);
		
		
		
		
		return uniqueGraph;
	} 
	public static void main(String args[]){
		Graph<Integer> graph = new Graph<>(true);
		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(2, 0);
		graph.addEdge(1, 3);
		graph.addEdge(3, 4);
		graph.addEdge(4, 19);
		graph.addEdge(19, 5);
		graph.addEdge(4, 5);
		graph.addEdge(9, 5);
		graph.addEdge(5, 1);
		graph.addEdge(0, 19);
		graph.addEdge(5, 5);
		graph.addEdge(5, 3);
		graph.addEdge(5, 6);
		SuperGraph sg=new SuperGraph(graph);
		System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
		//System.out.println(sg.getSuperGraph());
		sg.printGraph();
		System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List< Set<Vertex<Integer>>> result = scc.scc(graph);

		//print the result
		Set<Vertex<Integer>> vList= new HashSet<>();
		int max=0;
		for(Set<Vertex<Integer>> set: result ) {
			if(set.size()>max) {
				max=set.size();
			}
		}
		
		for(Set<Vertex<Integer>> set: result ) {
			if(set.size()==max) {
//				set.forEach(v->{
//					vList.add(v.getId());
//				});
				vList.addAll(set);
				break;
			}
			
		}
		Graph<Integer> graphStrongestConnectedComponnent =copyGraph(vList,graph);
        System.out.println("\n Origunal graph : \n"+graph+"\n\n ");		

		System.out.println("Strongest CC : " +vList);
        System.out.println("\n graph Strongest Connected Componnent: \n"+graphStrongestConnectedComponnent+"\n\n ");		
		result.forEach(set -> {
			
			set.forEach(v -> System.out.print(v.getId() + "-> "));
			System.out.println();
		});
		Graph<Integer> graphMaxFlow = new Graph<>(true);
		graphMaxFlow.addEdge(0, 1, 1);
		graphMaxFlow.addEdge(1, 2, 1);
		graphMaxFlow.addEdge(2, 0, 1);
		graphMaxFlow.addEdge(1, 3, 1);
		graphMaxFlow.addEdge(3, 4, 1);
		graphMaxFlow.addEdge(4, 5, 1);
//		graphMaxFlow.addEdge(1, 6, 1);
		graphMaxFlow.addEdge(5, 3, 1);
//		graphMaxFlow.addEdge(5, 6, 1);
		graphMaxFlow.addEdge(0, 5, 1);
		graphMaxFlow.addEdge(4, 6, 1);
		
		//create an Equivalent Array to represent the index for each vertex in graphWeightMatrix 2d array
		int[] arrayIndexEquivalents=new int[graphMaxFlow.getAllVertex().size()]; //= {2,9,7,5,3,12,1,99};
		int i=0;
		//		graphMaxFlow.getAllVertex().toArray();
		for(Vertex<Integer> c : graphMaxFlow.getAllVertex())
		{
			if(i<arrayIndexEquivalents.length) 
			{
				arrayIndexEquivalents[i]=(int)c.getId();
			//	System.out.println(arrayIndexEquivalents[i]);
				i++;
			}

		}
		
		// 2d representation of strongest connected component Edge weights  
		int graphWeightMatrix[][]=new int[graphMaxFlow.getAllVertex().size()][graphMaxFlow.getAllVertex().size()];
		//		for (int j = 0; j < arrayIndexEquivalents.length-1; j++) {
		//			for (int j2 = 0; j2 < arrayIndexEquivalents.length-1; j2++) {
		//			}
		//		}

		for (Edge<Integer> e : graphMaxFlow.getAllEdges()) {
			//		try {
			//System.out.println(e.toString());
			graphWeightMatrix[findInArray(arrayIndexEquivalents,e.getVertex1().getId())][findInArray(arrayIndexEquivalents,e.getVertex2().getId())]=e.getWeight();
			//			}catch (Exception e1) {
			//				System.err.println(e+"Error in array");
			//			}
		}
		System.out.println("\n");

		for (int j = 0; j < arrayIndexEquivalents.length; j++) {
			if (j<arrayIndexEquivalents.length-1) {
				System.out.print(arrayIndexEquivalents[j]+", ");

			}else {
				System.out.print(arrayIndexEquivalents[j]+" ");
			}
		}
		System.out.println("\n");
		for (int j = 0; j < graphWeightMatrix.length; j++) {
			for (int j2 = 0; j2 < graphWeightMatrix.length; j2++) {
				if (j2<graphWeightMatrix.length-1) {
					System.out.print(graphWeightMatrix[j][j2]+", ");

				}else {
					System.out.print(graphWeightMatrix[j][j2]+" ");
				}
			}
			System.out.println();
		}
		//		int vertexS = 0;
		//		int vertexT = graphMaxFlow.getAllVertex().size()-1;	//T is the last thing in the list
		//		for(int i=0;i<arrayIndexEquivalents.length;i++) {
		//			if(arrayIndexEquivalents[i]==9) {
		//				vertexS=i;
		//			}
		//			if(arrayIndexEquivalents[i]==18) {
		//				vertexT=i;
		//			}
		//		}
		//		int[] arrayIndexEquivalents1= {2,9,7,5,3,2,1,18};
		//		int graphMatrix[][] =new int[][] {
		//									{0, 10, 5, 15, 0, 0, 0, 0},		//edges FROM S TO anything
		//									{0, 0, 4, 0, 9, 15, 0, 0},
		//									{0, 0, 0, 4, 0, 8, 0, 0},
		//									{0, 0, 0, 0, 0, 0, 30, 0},
		//									{0, 0, 0, 0, 0, 15, 0, 10},
		//									{0, 0, 0, 0, 0, 0, 15, 10},
		//									{1, 0, 16, 0, 0, 0, 30, 0},
		//									{0, 0, 0, 0, 0, 0, 0, 0}		//T's row (no edges leaving T)
		//		};
		//		for (int j = 0; j < graphMatrix.length; j++) {
		//			for (int j2 = 0; j2 < graphMatrix.length; j2++) {
		//				System.out.print(graphMatrix[j][j2]+" ");
		//			}
		//			System.out.println();
		//		}
		
		FordFulkerson maxFlowFinder = new FordFulkerson(arrayIndexEquivalents,arrayIndexEquivalents.length);
		int vertexS = 0;
		int vertexT = maxFlowFinder.vertexCount-1;	//T is the last thing in the list
		//		for(int i=0;i<arrayIndexEquivalents.length;i++) {
		//			if(arrayIndexEquivalents[i]==9) {
		//				vertexS=i;
		//			}
		//			if(arrayIndexEquivalents[i]==7) {
		//				vertexT=i;
		//			}
		//		}
		//		

		System.out.println("\nBasic Ford Fulkerson Max Flow: " + maxFlowFinder.maxFlow(graphWeightMatrix, vertexS, vertexT));

		//		graphMaxFlow.getAllVertex().forEach(s->{
		//			arrayIndexEquivalents[i]=(int) s.getId();
		//			i++;
		//		});
		//		for (int i = 0; i < arrayIndexEquivalents.length; i++) {
		//			arrayIndexEquivalents[i]=graphMaxFlow.ge
		//		}
		//FordFulkerson ff = new FordFulkerson(arrayIndexEquivalents);
		//		int[][] capacity = {{0, 3, 0, 3, 0, 0, 0},
		//				{0, 0, 4, 0, 0, 0, 0},
		//				{3, 0, 0, 1, 2, 0, 0},
		//				{0, 0, 0, 0, 2, 6, 0},
		//				{0, 1, 0, 0, 0, 0, 1},
		//				{0, 0, 0, 0, 0, 0, 9},
		//				{0, 0, 0, 0, 0, 0, 0}};
		//		int[][] capacity=new int[graphMaxFlow.getAllVertex().size()][graphMaxFlow.getAllVertex().size()];
		//		System.out.println("dddddddddddd "+graphMaxFlow.getAllVertex().size());
		//		for (int i = 0; i < capacity.length; i++) {
		//			for (int j = 0; j < capacity.length; j++) {
		//				capacity[][j]
		//			}
		//		}
		//		System.out.println("\nMaximum capacity " + ff.maxFlow(capacity, 0, 6));
	}	
	// find an vertex index in array of vertexes return -1 if not there
	public static int findInArray(int[] arr,long a) {
		for (int i = 0; i < arr.length; i++) {
			if(arr[i]==a) {
				return i;
			}
		}
		return -1;
	}

}
