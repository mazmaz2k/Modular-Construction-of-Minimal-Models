package Graph;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Rules.RulesDataStructure;
import Rules.LinkedList;
import Rules.LinkedList.Node;
public class Graph<T>{

	private List<Edge<T>> allEdges;
	private Map<Long,Vertex<T>> allVertex;
	boolean isDirected = false;
	private final static int T =Integer.MIN_VALUE;
	private final static int S =Integer.MAX_VALUE;

	public Graph(boolean isDirected){
		allEdges = new ArrayList<Edge<T>>();
		allVertex = new HashMap<Long,Vertex<T>>();
		this.isDirected = isDirected;
	}

	public void addEdge(long id1, long id2){
		addEdge(id1,id2,0);
	}

	@SuppressWarnings("unlikely-arg-type")
	public boolean hasEdge(Vertex<T> v1 ,Vertex<T> v2) {
		if(v1.getEdges().contains(v2)) {
			return true;
		}
		return false;
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
	public void addEdge(long id1,long id2, int weight,int sizeOfS){
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

		Edge<T> edge = new Edge<T>(vertex1,vertex2,isDirected,weight,sizeOfS);
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
	public void removeEdge(Edge<T> edge) {
		List<Edge<T>> newEdgeList= new ArrayList<Edge<T>>();
		for(Edge<T> e: getAllEdges()) {
			if(!e.equals(edge)) {
				newEdgeList.add(e);
			}
		}
		this.allEdges=newEdgeList;
	}
	/**
	 * Remove the vertex v from the graph.
	 * 
	 * @param v The vertex that will be removed.
	 */
	public Graph<Integer> removeVertex(ArrayList<Vertex<Integer>> vertexsListToRemove) {
		Graph<Integer> newGraph= new Graph<Integer>(isDirected); 
		try {
			//			System.out.println(" test:::------------------------------------------------------");
			for (Edge<T> e: getAllEdges()) {
				if(!vertexsListToRemove.contains(e.getVertex1()) && !vertexsListToRemove.contains(e.getVertex2())) {
					//getAllEdges().remove(e);
					//					System.out.println("v1:" +e.getVertex1().getId()+" v2: "+e.getVertex2().getId());
					newGraph.addEdge(e.getVertex1().getId(), e.getVertex2().getId(),1);
					//					System.out.println("end test ------------------------------------------");
				}else if(vertexsListToRemove.contains(e.getVertex1()) && !vertexsListToRemove.contains(e.getVertex2())){
					newGraph.addSingleVertex(e.getVertex2().getId());
				}else if(!vertexsListToRemove.contains(e.getVertex1()) && vertexsListToRemove.contains(e.getVertex2())){
					newGraph.addSingleVertex(e.getVertex1().getId());

				}
			}

		}catch (Exception e2) {
			System.err.println("Remove Error: "+e2);
		}


		return newGraph;
	}

	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for(Edge<T> edge : getAllEdges()){
			buffer.append(edge.getVertex1() + " -> " + edge.getVertex2() + " w: " + edge.getWeight() +" s size:"+ edge.getSSize());
			buffer.append("\n");
		}
		return buffer.toString();
	}

	/**Vertex separator
	 * */
	public static ArrayList<Vertex<Integer>> vertexSeparator(Set<Vertex<Integer>> allVertex , Graph<Integer> graph){
		ArrayList<Vertex<Integer>> returnVertexes = new ArrayList<>();
		ArrayList<Vertex<Integer>> W_vertexList = new ArrayList<>();
		ArrayList<Vertex<Integer>> A_vertexList = new ArrayList<>();
		ArrayList<Vertex<Integer>> B_vertexList = new ArrayList<>();
		if(allVertex.isEmpty() || graph ==null) {
			System.out.println("Graph.vertexSeperator() error graph is empty in vertexSeparator method");
			return null;
		}
		int counter=0;
		boolean flag= true;
		while(flag) {
			do {
				if(W_vertexList != null ) {
					W_vertexList.clear();
				}
				for(Vertex<Integer> vertex : allVertex) {
					if(Math.random() < 0.1) {

						W_vertexList.add(vertex);
					}
				}
				System.out.println("in first while");

			}while(W_vertexList.isEmpty() || W_vertexList.size() < 0.1);
			System.out.println("W is:" + W_vertexList);
			System.out.println("W size: " + W_vertexList.size());
			do {
				if(A_vertexList != null || B_vertexList != null) {
					A_vertexList.clear();
					B_vertexList.clear();
				}
				for(Vertex<Integer> vertex :W_vertexList) {
					if(Math.random() > 0.5) {
						A_vertexList.add(vertex);
					}else {
						B_vertexList.add(vertex);
					}
				}

				System.out.println("In second while");
				if(counter==20) {
					counter=0;
					break;
				}
				counter++;
			}while(Math.abs(A_vertexList.size()-B_vertexList.size())/W_vertexList.size() > 0.5 || checkIfHasEdges(A_vertexList,B_vertexList) || A_vertexList.isEmpty() || B_vertexList.isEmpty());
			if ((Math.abs(A_vertexList.size()-B_vertexList.size())/W_vertexList.size() <= 0.5) &&  !checkIfHasEdges(A_vertexList,B_vertexList) && !A_vertexList.isEmpty() && !B_vertexList.isEmpty()) {
				flag=false;		
			}
		}

		System.out.println("Point B");
		Graph<Integer> flowNetGraph = createFlowNetwork(graph, allVertex , A_vertexList,B_vertexList);
		if(flowNetGraph ==null) {
			System.err.println("error in flow nework some of the variables are NULL");
			return null;
		}
		//TODO: continue working!! create flow net graph!!!
		// create edge to S T from A and B with weight |v|         V
		// Separate a to a1 and a2 to all vertex beside S A T B    V
		//weight between a1 and a2 is 1                            V
		//all other weights are |V|                                V
		System.out.println("Flow Graph----------");
		System.out.println(flowNetGraph);
		FordFulkerson ff = new FordFulkerson(flowNetGraph);
		int x1=0;
		Collection<Integer> collection_S = null,collection_T =null ;
		try {
			//System.out.println("v1: "+ flowNetGraph.getVertex(Long.MAX_VALUE) +" v2: "+flowNetGraph.getVertex(Long.MIN_VALUE));
			System.out.println("v1: "+flowNetGraph.getVertex(S));
			System.out.println("v2: "+flowNetGraph.getVertex(T));
			System.out.println("V1 index:" +ff.findVertexIndex(flowNetGraph.getVertex(S))+ " V2 index: " + ff.findVertexIndex(flowNetGraph.getVertex(T)));
			x1=ff.maxFlow( ff.findVertexIndex(flowNetGraph.getVertex(S)),  ff.findVertexIndex(flowNetGraph.getVertex(T)));//find max flow & min cut between S and T

			collection_S= ff.getS();
			collection_T = ff.getT();
			System.out.println();
		}catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("max flow is: "+ x1);
		System.out.println("S (of vertexes) is" + collection_S);
		System.out.println("T (of vertexes) is" + collection_T);

		System.out.println();
		if(collection_S ==null || collection_T ==null) {
			System.err.println("error in flow nework Set S or Set T are NULL");
			return null;		
		}		
		ArrayList<Edge<Integer>> edgeList=new ArrayList<>(); //array list to hold all the edges of the CUT !
//		ArrayList<Vertex<Integer>> vertexsListToRemove= new ArrayList<>();

		for(Vertex<Integer> v: flowNetGraph.getAllVertex()) {
			if(collection_S.contains((int)v.getId())) {
				for(Edge<Integer> e : v.getEdges()) {
					if(collection_T.contains((int)e.getVertex2().getId())) {
						edgeList.add(e); //add edge  of the cut 
					}
				}
			}

		}
		//		System.out.println(edgeList);
		for(Edge<Integer> e: edgeList) {
			if(!returnVertexes.contains(e.getVertex1()) && e.getVertex1().getId()!=S ) {
				returnVertexes.add(flowNetGraph.getVertex(Math.abs(e.getVertex1().getId())));
			}
			else if(!returnVertexes.contains(e.getVertex2()) && e.getVertex2().getId()!=T) {
				returnVertexes.add(flowNetGraph.getVertex(Math.abs(e.getVertex2().getId())));
			}
		}

		
		
		
		//TODO:
		/// find CUT and edges in the cut!
		//return vertex that in the cut

		return returnVertexes;
	}

	private static Graph<Integer> createFlowNetwork(Graph<Integer> graph, Set<Vertex<Integer>> allVertex,
			ArrayList<Vertex<Integer>> a_vertexList, ArrayList<Vertex<Integer>> b_vertexList) {
		if(graph==null || allVertex==null ||a_vertexList==null||b_vertexList==null) {
			System.err.println(" graph / allVertex / are / a_vertexList / b_vertexList null in createFlowNetwork method");
			return null;
		}

		graph.addSingleVertex(S);//This is "S" vertex
		graph.addSingleVertex(T);//This is "T" vertex
		for(Vertex<Integer> v : graph.getAllVertex()) {
			if(a_vertexList.contains(v))
			{
				graph.addEdge(S, v.getId(), graph.getAllVertex().size()); // create edge between "S" to Vertex in A  with weight |v| 
			}else if(b_vertexList.contains(v)) {
				graph.addEdge(v.getId(), T, graph.getAllVertex().size()); // create edge between Vertex in B to "T" with weight |v| 
			}

		}
		a_vertexList.add(graph.getVertex(S));	// add node S to A
		b_vertexList.add(graph.getVertex(T));	// add vertex T to B
		System.out.println("A: " + a_vertexList );
		System.out.println("B: " + b_vertexList);
		Set<Vertex<Integer>> vertexToDuplicate = new HashSet<>();
		for(Vertex<Integer> v : graph.getAllVertex()) {
			if(!a_vertexList.contains(v) && !b_vertexList.contains(v)) { // enter to array all node that not in A and B and not vertex T and vertex S
				vertexToDuplicate.add(v);
			}
		}
		//		System.out.println("duplicate " + vertexToDuplicate);

		Graph<Integer> g=duplicateGraph(graph, vertexToDuplicate); // duplicate all vertex that not in A and B and not vertex T and vertex S
		return g;
	}
	/*Duplicate a graph
	 * when given set of vertex to duplicate them 
	 * return a graph with duplicate JUST the necessary nodes 
	 * **/
	private static Graph<Integer> duplicateGraph(Graph<Integer> graph, Set<Vertex<Integer>> vertexToDuplicate) {
		if(graph==null || vertexToDuplicate ==null) {
			System.err.println(" graph / vertex to dupicate are null in duplicateGraph method");
			return null;
		}
		Graph<Integer> uniqueGraph=new Graph<Integer>(true);
		int size = graph.getAllVertex().size(); // size of |v| to be on the weight of all edges beside between x1->x2  
				System.out.println("vertexToDuplicate: " + vertexToDuplicate);
		for(Vertex<Integer> v: graph.getAllVertex()) {
			if(!vertexToDuplicate.contains(v)) {
				for(Edge<Integer> e : v.getEdges()) {
					
					if(vertexToDuplicate.contains(e.getVertex2())) {
						uniqueGraph.addEdge(v.getId(),e.getVertex2().getId()*(-1), size);
					}else {
						uniqueGraph.addEdge(v.getId(),e.getVertex2().getId(), size);
					}

				}
			}else { // if it NOT in A Or B
				uniqueGraph.addEdge(v.getId()*(-1), v.getId(), 1); // weight between duplicate node are 1
				for(Edge<Integer> e: v.getEdges()) { 
					if(vertexToDuplicate.contains(e.getVertex2())) {
						uniqueGraph.addEdge(v.getId(),e.getVertex2().getId()*(-1), size);
					}else {
						uniqueGraph.addEdge(v.getId(),e.getVertex2().getId(), size);
					}

				}

			}

		}

		return uniqueGraph;		
	}


	/* check if 2 arrays of vertexes in graph have common edge between them
	 * */
	public static boolean checkIfHasEdges(ArrayList<Vertex<Integer>> A ,ArrayList<Vertex<Integer>> B) {
		for(Vertex<Integer> v1: A) {

			for(Vertex<Integer> v2: B)
			{
				if(v1.getAdjacentVertexes().contains(v2)) {
					return true;
				}

			}
		}
		return false;
	} 

	/**	find cut from two set of vertexes id's collection
	 * **/
	public static Collection<Integer> cutLinkList(Collection<Integer> A,Collection<Integer> B){
		Collection<Integer> A_B= new java.util.LinkedList<Integer>();
		for(Integer a:A) {
			if(B.contains(a)) {
				A_B.add(a);
			}
		}
		return A_B;
	}
	/**
	 * we find source of the graph-only the first source is relevant**/
	public static LinkedList sourceOfGraph(Graph<Integer>  graph)
	{
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(graph);
		LinkedList s = new LinkedList();
		if(result.isEmpty())
		{
			return s;
		}
		for (Vertex<Integer> vertex : result.get(0)) // go over first set of vertex
		{
			s.addAtTail((int)vertex.getId());	//add to source arrayList 
		}
		//    System.out.println("------------------------------------------------------------------------------------------------------------------");
		//    System.out.println("Here are the size of all the connected component in the graph");
		//    //print the result
		//    result.forEach(set -> {
		//    	System.out.println("sizeof :"+ set.size());
		//       // set.forEach(v -> System.out.print(v.getId() + " "));
		//        System.out.println();
		//    });
		//    System.out.println("------------------------------------------------------------------------------------------------------------------");
		return s;
	}

	/**
	 * convert clauses data structure in to graph body-->head
	 * body  has edge to head
	 * this is a directed**/
	public static Graph<Integer> initGraph(RulesDataStructure DS ,int numOfRules) 
	{


		Graph<Integer> graph = new Graph<>(true);
		Node n1 ,n2;

		for (int i = 0; i < numOfRules; i++) 
		{
			if(DS.RulesArray[i]!=null)
			{
				n1 =DS.RulesArray[i].body.head;
				if(n1==null) 
				{
					n2=DS.RulesArray[i].head.head;
					while(n2!=null)
					{
						graph.addSingleVertex(n2.var);
						n2=n2.next;
					}
				}
				while(n1!=null)
				{
					graph.addSingleVertex(n1.var);
					n2=DS.RulesArray[i].head.head;

					while(n2!=null)
					{

						graph.addEdge(n1.var,n2.var,1);
						n2=n2.next;
					}
					n1=n1.next;

				}

			}


		}
		//		System.out.println("This is the Graph:");
		//		System.out.println(graph);


		return graph;	
	} 
	/**input original graph and set of vertexes and return (create) a smaller graph from those set of vertexes */

	public static Graph<Integer> copyGraph(LinkedList setOfVertex,Graph<Integer> oldGraph) {
		Graph<Integer> newGraph = new Graph<>(true);
		Node n = setOfVertex.head;
		while(n!=null)
		{
			if(oldGraph.getAllVertex().contains(oldGraph.getVertex((int)n.var))){
				newGraph.addSingleVertex(n.var);
			}
			for(Edge<Integer> e : oldGraph.getAllEdges()) {

				if( n.var==e.getVertex1().getId() && setOfVertex.contains((int)e.getVertex2().getId())) {
					newGraph.addEdge(n.var, e.getVertex2().getId(),e.getWeight());
					//System.out.println("v1: "+v.getId()+" v2: "+e.getVertex2().getId());
				}

			}
			n=n.next;
		}

		//System.out.println(newGraph.getAllEdges().toString());

		return newGraph;
	} 

	/**FIND ALL K-edge connected component Algorithm !!
	input graph(of strongest connected component/original graph) ,s vertex of connected component , N list of all vertexs in connected component
	return auxiliary graph*/

	public static void constaruction(Graph<Integer> graph ,Vertex<Integer> s, Collection<Integer> N,Graph<Integer> auxiliaryGraph){
		if(N==null||graph==null||s==null|| !N.contains((int)s.getId())|| !graph.getAllVertex().contains(s)) { //check if input are wrong -save recursive problems 
			System.out.println("wrong input");
			System.out.println(s.getId()+" "+N);
		}

		if(N.contains((int) s.getId()) && N.size()==1) { // exit condition from recursive 
			//			System.out.println("Exit recursion");
			return;
		}
		int t=-1;
		for( int vertex: N) { //find t vertex  from N- t!=s
			if(vertex!=s.getId()) {
				t=vertex;
			}
		}
		if(t==-1) { //check t is in N- if not there is problem in recursive
			System.out.println("Error:  N is Empty");

		}
		//		System.out.println("s is : "+s.getId()+" t is: "+t);
		FordFulkerson f1=new FordFulkerson(graph);	//find flow from s to t
		FordFulkerson f2=new FordFulkerson(graph);  //finf flow from t to s
		Vertex<Integer> tVertex=new Vertex<Integer>(t);
		int x1= f1.maxFlow(f1.findVertexIndex(s), f1.findVertexIndex(tVertex));
		//		System.out.println("max flow x1: " +x1);
		for(Edge<Integer> e: graph.getAllEdges()) {
			if(f1.getT().contains((int)e.getVertex2().getId())&& f1.getS().contains((int) tVertex.getId())) {
				e.setSizeOfS(f1.getS().size()); //save size of S to all cut edges!

			}
		}
		int x2= f2.maxFlow( f2.findVertexIndex(tVertex), f2.findVertexIndex(s));
		//		System.out.println("max flow x2: " +x2);
		Collection<Integer> S =f1.getS(); //find S- set of vertex in front the cut
		// remove matrix
		//Convert N S T  to Arrays
		Collection<Integer> T =f1.getT(); // find T- set of vertex behind the cut
		if(x1>x2) { //find maximum ways from s to t and t to s- way have to be equles 
			x1=x2;
			S=f2.getT();
			T=f2.getS();

		}

		//		//may need to add edge from t to s also!!
		//System.out.println("------------s is : "+s.getId()+" t is: "+ t);
		auxiliaryGraph.addEdge(s.getId(), t, x1,S.size());
		//	auxiliaryGraph.addEdge(t, s.getId(), x1);
		Collection<Integer> N_S=cutLinkList(N,S); //find cut from two set of vertexes  
		//	System.out.println("s is: "+s+"  S is:"+S+" N is: "+N+" the N_S Cut is: "+N_S);
		constaruction(graph,s,N_S,auxiliaryGraph);

		Collection<Integer> N_T=cutLinkList(N,T);
		//		System.out.println("the N_T Cut is: "+N_T);

		constaruction(graph,tVertex, N_T,auxiliaryGraph);
	}

	/***unique Graph creation -create unique graph data structure for greatest connected component
	we will return Graph from which we have double vertex number - I split all node to 2 nodes.**/

	public static Graph<Integer> uniqueGraphCreation(Graph<Integer> graph){
		Graph<Integer> uniqueGraph=new Graph<Integer>(true);
		for(Vertex<Integer> v:graph.getAllVertex()) {
			//			uniqueGraph.addSingleVertex(v.getId());
			//			uniqueGraph.addSingleVertex(v.getId()*(-1));
			uniqueGraph.addEdge(v.getId()*(-1), v.getId(), 1);
			for(Edge<Integer> e: v.getEdges()) {
				//				if(e.getVertex2().equals(v)) {
				uniqueGraph.addEdge(v.getId(),e.getVertex2().getId()*(-1), 1,e.getSSize());
				//				}
			}
		}
		//		System.out.println("-------------------real Graph-----------------");
		//		System.out.println(graph);
		//		System.out.println("-------------------unique Graph-----------------");
		//System.out.println(uniqueGraph);
		return uniqueGraph;
	}





	/** Dismantle strongest connected component
	 * return array of vertex which will be put in rules array**/
	public static ArrayList<Vertex<Integer>> dismantlingStrongestCC(Graph<Integer> graph,Graph<Integer> auxiliaryGraph) {
		//int[] vertexArray=new int[auxiliaryGraph.allVertex.size()]; //array of vertex id's so return to Rules Data structure 

		int min=Integer.MAX_VALUE;
		Vertex<Integer> a=null,b = null;
		//		System.out.println("A graph is:");
		//		System.out.println(auxiliaryGraph);
		//		System.out.println("end of A graph ");
		int t=0;
		for(Edge<Integer> e: auxiliaryGraph.getAllEdges()) { //find smallest K+t
			t=Math.abs(e.getSSize()-(auxiliaryGraph.getAllVertex().size()/2)) ;
			//			System.out.println("t is: "+t +", k is:"+e.getWeight() );

			if(e.getWeight()+t<min ) {
				//				a=(int)e.getVertex1().getId();
				//				b=(int)e.getVertex2().getId();
				a=e.getVertex1();
				b=e.getVertex2();
				min=e.getWeight()+t;
				//				System.out.println("min t is: "+t+" k is: "+ e.getWeight());
				//				System.out.println(e.getSSize()+"-"+(auxiliaryGraph.getAllVertex().size()/2));
				//				System.out.println(" finding min: a : "+a.getId()+" b:"+b.getId() );
			}
			//	System.out.println("e is: "+e+" t is: "+t+" minus "+auxiliaryGraph.getAllVertex().size());

			t=0;
		}

		Graph<Integer> uniqeGraph=uniqueGraphCreation(graph); // duplicate graph to change K-edge connected component to find vertex to remove

		FordFulkerson fordFulkerson= new FordFulkerson(uniqeGraph); //find cut between a an b  
		int maxflow=fordFulkerson.maxFlow( fordFulkerson.findVertexIndex(uniqeGraph.getVertex(Math.abs(a.getId()))), fordFulkerson.findVertexIndex(uniqeGraph.getVertex(-Math.abs(b.getId()))));
		if(maxflow>min) {
			maxflow=fordFulkerson.maxFlow( fordFulkerson.findVertexIndex(uniqeGraph.getVertex(Math.abs(b.getId()))), fordFulkerson.findVertexIndex(uniqeGraph.getVertex(-Math.abs(a.getId()))));
		}
		//		System.out.println("a is: "+a.getId()+" b is: "+ b.getId()+"----------------------------------------------------------");

		Collection<Integer> S =fordFulkerson.getS(); // find S- set of vertex before the cut
		Collection<Integer> T =fordFulkerson.getT(); // find T- set of vertex behind the cut
		ArrayList<Edge<Integer>> edgeList=new ArrayList<>(); //array list to hold all the edges of the CUT !
		ArrayList<Vertex<Integer>> vertexsListToRemove= new ArrayList<>();
		//		System.out.println(" uniqe graph:------------------------------------------");
		//		System.out.println(uniqeGraph);
		//		System.out.println("-------------------------------------------------------");
		System.out.println("S of cut: "+S);
		System.out.println("T of cut: "+ T);
		for(Vertex<Integer> v: uniqeGraph.getAllVertex()) {
			if(S.contains((int)v.getId())) {
				for(Edge<Integer> e : v.getEdges()) {
					if(T.contains((int)e.getVertex2().getId())) {
						edgeList.add(e); //add edge  of the cut 
					}
				}
			}

		}
		//		System.out.println(edgeList);
		for(Edge<Integer> e: edgeList) {
			if(!vertexsListToRemove.contains(e.getVertex1())) {
				vertexsListToRemove.add(uniqeGraph.getVertex(Math.abs(e.getVertex1().getId())));
			}
			else if(!vertexsListToRemove.contains(e.getVertex2())) {
				vertexsListToRemove.add(uniqeGraph.getVertex(Math.abs(e.getVertex2().getId())));
			}
		}
		return vertexsListToRemove;
	}

	/***  unite all dismantle graph methodes 
	ALL OF THAT IN COPY GRAPH: change name of method copy graph to something else
	receve a source
	create graph from the sorce
	send the graph to dismantle methods
	return array of vertexes to main***/
	public static int[] dismntleToArray(Graph<Integer> graph,LinkedList source) {
		System.out.println("Enter dismntleToArray----------------------------------------------------------------------");
		Graph<Integer> connectedComponentGraph = copyGraph(source, graph);

		Graph<Integer> A = new Graph<>(false);
		Vertex<Integer> s=null;//new Vertex<Integer>(10);

		Node n=source.head;

		if(n==null) {
			System.err.println("error in linked list DS");
			return null;
		}
		s=new Vertex<Integer>(n.var);



		Collection<Integer> N=new java.util.LinkedList<Integer>();
		for(Vertex<Integer> v:connectedComponentGraph.getAllVertex()) 
		{
			N.add((int) v.getId());

		}
		constaruction(connectedComponentGraph,s,N,A);
		if(A.getAllVertex().size()==0) {
			int[] a=new int[1];
			a[0]=(int)s.getId();
			return a;
		}
		ArrayList<Vertex<Integer>> arr= dismantlingStrongestCC(connectedComponentGraph,A);
		int [] a=new int[arr.size()];
		int i=0;
		System.out.println("a:---"+arr);
		for(Vertex<Integer> v: arr) {
			a[i]=(int)v.getId();
			i++;
		}
		return a;
	}

	public static void main(String args[]){
		//		Graph<Integer> graph = new Graph<>(true);
		//		graph.addEdge(0, 1);
		//		graph.addEdge(1, 2);
		//		graph.addEdge(2, 0);
		//		graph.addEdge(1, 3);
		//		graph.addEdge(3, 4);
		//		graph.addEdge(4, 19);
		//		graph.addEdge(19, 5);
		//		graph.addEdge(4, 5);
		//		graph.addEdge(9, 5);
		//		graph.addEdge(5, 1);
		//		graph.addEdge(0, 19);
		//		graph.addEdge(5, 5);
		//		graph.addEdge(5, 3);
		//		graph.addEdge(5, 6);
		//		SuperGraph sg=new SuperGraph(graph);
		//		System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
		//		//System.out.println(sg.getSuperGraph());
		//		sg.printGraph();
		//		System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
		//		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		//		List< Set<Vertex<Integer>>> result = scc.scc(graph);
		//
		//		//print the result
		//		Set<Vertex<Integer>> vList= new HashSet<>();
		//		int max=0;
		//		for(Set<Vertex<Integer>> set: result ) {
		//			if(set.size()>max) {
		//				max=set.size();
		//			}
		//		}
		//
		//		for(Set<Vertex<Integer>> set: result ) {
		//			if(set.size()==max) {
		//				//				set.forEach(v->{
		//				//					vList.add(v.getId());
		//				//				});
		//				vList.addAll(set);
		//				break;
		//			}
		//
		//		}
		//		Graph<Integer> graphStrongestConnectedComponnent =copyGraph(vList,graph);
		//		System.out.println("\n Origunal graph : \n"+graph+"\n\n ");		
		//
		//		System.out.println("Strongest CC : " +vList);
		//		System.out.println("\n graph Strongest Connected Componnent: \n"+graphStrongestConnectedComponnent+"\n\n ");		
		//		result.forEach(set -> {
		//
		//			set.forEach(v -> System.out.print(v.getId() + "-> "));
		//			System.out.println();
		//		});
		Graph<Integer> graphMaxFlow = new Graph<>(true);
		graphMaxFlow.addEdge(10, 3, 1);
		graphMaxFlow.addEdge(10, 4, 1);
		graphMaxFlow.addEdge(1, 10, 1);
		graphMaxFlow.addEdge(4, 1, 1);
		graphMaxFlow.addEdge(3, 1, 1);
		graphMaxFlow.addEdge(1, 5, 1);
		graphMaxFlow.addEdge(1, 2, 1);
		graphMaxFlow.addEdge(2, 1, 1);
		graphMaxFlow.addEdge(6, 1, 1);
		graphMaxFlow.addEdge(6, 5, 1);
		graphMaxFlow.addEdge(5, 6, 1);
		graphMaxFlow.addEdge(2, 6, 1);
		graphMaxFlow.addEdge(5, 2, 1);
		//
		//		//create an Equivalent Array to represent the index for each vertex in graphWeightMatrix 2d array
		//		int[] arrayIndexEquivalents=new int[graphMaxFlow.getAllVertex().size()]; //= {2,9,7,5,3,12,1,99};
		//		int i=0;
		//		//		graphMaxFlow.getAllVertex().toArray();
		//		for(Vertex<Integer> c : graphMaxFlow.getAllVertex())
		//		{
		//			if(i<arrayIndexEquivalents.length) 
		//			{
		//				arrayIndexEquivalents[i]=(int)c.getId();
		//				//	System.out.println(arrayIndexEquivalents[i]);
		//				i++;
		//			}
		//
		//		}

		// 2d representation of strongest connected component Edge weights  
		//		int graphWeightMatrix[][]=new int[graphMaxFlow.getAllVertex().size()][graphMaxFlow.getAllVertex().size()];
		//		for (int j = 0; j < arrayIndexEquivalents.length-1; j++) {
		//			for (int j2 = 0; j2 < arrayIndexEquivalents.length-1; j2++) {
		//			}
		//		}

		//		for (Edge<Integer> e : graphMaxFlow.getAllEdges()) {
		//			//		try {
		//			//System.out.println(e.toString());
		//			graphWeightMatrix[findInArray(arrayIndexEquivalents,e.getVertex1().getId())][findInArray(arrayIndexEquivalents,e.getVertex2().getId())]=e.getWeight();
		//			//			}catch (Exception e1) {
		//			//				System.err.println(e+"Error in array");
		//			//			}
		//		}
		//		System.out.println("\n");
		//
		//		for (int j = 0; j < arrayIndexEquivalents.length; j++) {
		//			if (j<arrayIndexEquivalents.length-1) {
		//				System.out.print(arrayIndexEquivalents[j]+", ");
		//
		//			}else {
		//				System.out.print(arrayIndexEquivalents[j]+" ");
		//			}
		//		}
		//		System.out.println("\n");
		//		for (int j = 0; j < graphWeightMatrix.length; j++) {
		//			for (int j2 = 0; j2 < graphWeightMatrix.length; j2++) {
		//				if (j2<graphWeightMatrix.length-1) {
		//					System.out.print(graphWeightMatrix[j][j2]+", ");
		//
		//				}else {
		//					System.out.print(graphWeightMatrix[j][j2]+" ");
		//				}
		//			}
		//			System.out.println();
		//		}
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

		//		FordFulkerson maxFlowFinder = new FordFulkerson(graphMaxFlow);
		//		int vertexS = 0;   //S is the first thing in the list
		//		int vertexT = 6;	//T is the last thing in the list
		//		for(int i=0;i<arrayIndexEquivalents.length;i++) {
		//			if(arrayIndexEquivalents[i]==9) {
		//				vertexS=i;
		//			}
		//			if(arrayIndexEquivalents[i]==7) {
		//				vertexT=i;
		//			}
		//		}
		//		

		//		System.out.println("\nBasic Ford Fulkerson Max Flow: " + maxFlowFinder.maxFlow( vertexS, vertexT));
		//
		//		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");

		Graph<Integer> A = new Graph<>(false);
		Vertex<Integer> s=new Vertex<Integer>(10);
		Collection<Integer> N=new java.util.LinkedList<Integer>();
		for(Vertex<Integer> v:graphMaxFlow.getAllVertex()) 
		{
			N.add((int) v.getId());

		}

		//System.out.println(N);
		constaruction(graphMaxFlow,s,N,A);
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		System.out.println("A graph: ");
		System.out.println(A);
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		ArrayList<Vertex<Integer>> arr= dismantlingStrongestCC(graphMaxFlow,A);
		System.out.println("Vertex to remove: "+arr);
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
		//		Graph<Integer> g=uniqueGraphCreation(graphMaxFlow);
		//		System.out.println(g.getAllVertex().size()+"->"+graphMaxFlow.getAllVertex().size());
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
