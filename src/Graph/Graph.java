package Graph;


//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.concurrent.TimeUnit;

import Rules.RulesDataStructure;
import Rules.LinkedList;
import Rules.LinkedList.Node;

/**
 * Date 10/10/2017
 * @author Omri Mizrahi
 *
 * Graph data structure 
 * Find and minimum balanced node separator 
 *
 *
 *
 *
 * References:
 *
 * 
 */

/* Graph data structure class*/
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

		//		for (Edge<T> edge : v1.getEdges()) 
		//		{		System.out.println("ssssssssssssssssssssss");
		//
		//			if(edge.getVertex2().getId()==v2.getId())
		//				return true;
		//		}

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

	/**
	 * return graph from source .
	 * 
	 * @param list of id's to create graph from
	 * @param old graph 
	 */
	public static Graph<Integer>  createGraphFromSource(LinkedList s, Graph<Integer> graph){
		Graph<Integer> graphToReturn = new Graph<Integer>(graph.isDirected);
		for(Vertex<Integer> v: graph.getAllVertex()) {
			if(s.contains((int)v.getId())) {
				graphToReturn.addSingleVertex(v.getId());
				for(Edge<Integer> e : v.getEdges()) {
					if(s.contains((int) e.getVertex2().getId() )){
						graphToReturn.addEdge(e.getVertex1().id, e.getVertex2().getId(), 1);
					}
				}
			}

		}
		return graphToReturn;
	} 


	/**
	 * find all vertex of the parent up to the source and insert them to array.
	 * 
	 * @param super Graph
	 * @param verex to find all hit parents
	 * @param returnVertex to return all the vertex up to the source
	 * */
	public static void findAllVertexes(SuperGraph superGraph,Vertex<Integer> vertex, LinkedList returnVertex){
		if(superGraph.getParent(vertex).isEmpty())
		{
			return;
		}

		ArrayList<Vertex<Integer>> parentArr= superGraph.getParent(vertex);
		//		returnVertex.addAll(vertex.getCCList());

		for(Vertex<Integer> v: parentArr) {
			for(Vertex<Integer> vertexInCC :v.getCCList()) {
				if(!returnVertex.contains((int)vertexInCC.getId())) {
					//    				System.out.println("222222");
					returnVertex.addAtTail((int)vertexInCC.getId());
				}
			}
			findAllVertexes(superGraph,v,returnVertex);
		}
	}

	/**
	 * Handle Integrity Constraint 
	 * return all vertexes up to the source the connect to IC vertexes
	 * */
	public static LinkedList IntegrityConstraintHandle(Graph<Integer> graph, ArrayList<Integer>  CIVars){
		//Vertex<ArrayList<Integer>> x = new Vertex<>(5);
		LinkedList returnVertexes = new LinkedList();
		//		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		SuperGraph superGraph = new SuperGraph(graph);
		Graph<Integer> super_graph = superGraph.getSuperGraph();
		//        ArrayList<Vertex<Integer>> parentArray;
		for(Integer vId :CIVars) {
			for(Vertex<Integer> vertex : super_graph.getAllVertex()) {
				for(Vertex<Integer> v :vertex.getCCList()) {
					if(vId==(int)v.getId()) {
						//        				System.out.println("11111");
						for(Vertex<Integer> vertexInCC :vertex.getCCList()) {
							if(!returnVertexes.contains((int)vertexInCC.getId())) {
								returnVertexes.addAtTail((int)vertexInCC.getId());
							}
						}
						findAllVertexes(superGraph,vertex,returnVertexes);

					}
				}
			}
		}
		//        superGraph.printGraph();
		//print the result
		//        result.forEach(set -> {
		//            set.forEach(v -> System.out.print(v.getId() + " "));
		//            //System.out.println();
		//        });
		//		for(Set<Vertex<Integer>> set : result) {
		//			for(Integer x : CIVars) {
		//				if()
		//				acyclicGraph.addSingleVertex(set);
		//				
		//			}
		//		}


		//x.setData();
		return returnVertexes;
	}





	/**Vertex separator 
	 * 
	 * find minimum vertex array.
	 * when we remove those vertexes we will receive balanced separation of the graph  
	 * 
	 * @param graph which most be a connected component
	 * */
	public static ArrayList<Vertex<Integer>> vertexSeparator( Graph<Integer> graph){
		ArrayList<ArrayList<Vertex<Integer>>> arr = new ArrayList<>();
		ArrayList<Vertex<Integer>> returnVertexes = new ArrayList<>();
//		ArrayList<Vertex<Integer>> W_vertexList = new ArrayList<>();
		ArrayList<Vertex<Integer>> A_vertexList = new ArrayList<>();
		ArrayList<Vertex<Integer>> B_vertexList = new ArrayList<>();
		ArrayList<Edge<Integer>> edgeList = new ArrayList<>(); //array list to hold all the edges of the CUT !


		if(graph ==null) {
			System.out.println("Graph.vertexSeperator() error graph is empty in vertexSeparator method");
			return null;
		}
		FordFulkerson ff =null;
		Graph<Integer> flowNetGraph =null; 
		double w_max = Math.ceil( 0.1 * graph.getAllVertex().size() ) ;
		if (w_max <2) {
			w_max=2;
		}

		int[] arrOfA_B = new int[]{100}; //
		// shuffle
		int a [] = new int[graph.getAllVertex().size()];
		// insert integers 0..n-1 
		for(int a_b : arrOfA_B) {
			arr.clear();
			returnVertexes.clear();
			for(int w=4 ; w<= 32; w=w*2) 
			{
//
//				int count = 0;
//				W_vertexList.clear();
//				//lottery vertex from V to W
//				do {
//					W_vertexList.clear();
//					for(int i=0 ; i<3 ; i++) { //Lottery W 3 times !
//						for(Vertex<Integer> vertex : graph.getAllVertex()) {
//							if(Math.random() <= 0.1) {
//								W_vertexList.add(vertex);
//							}
//						}
//					}
//					count++;
//				}while((W_vertexList.isEmpty() || W_vertexList.size() > w) && count <=2000  );	//we have W set
//
				
				for (int i = 0; i < a.length; i++)
				a[i] = i;
				for (int i = 0; i < a.length; i++) {
					int r = (int) (Math.random() * (i+1));     // int between 0 and i
					int swap = a[r];
					a[r] = a[i];
					a[i] = swap;
				}

//				if(count > 2000) {
//					continue;
//				}
				
				
				for(int count_a_b=0; count_a_b < Math.min(a_b, Math.pow(2, w)) /*&& flag*/ ;count_a_b++) {	//for every w find several A and B sets
					int counter=0;
					returnVertexes.clear();
					A_vertexList.clear();
					B_vertexList.clear();
					double rand_prob ;
					//lottery A B vertex set from W
					do {
						rand_prob = Math.random();
					}
					while(!(rand_prob>0.25) || !(rand_prob < 0.75));
					do {
						A_vertexList.clear();
						B_vertexList.clear();
						double x = Math.random();

//						for(Vertex<Integer> vertex :W_vertexList) {
						for(int i =0;i<w;i++) {
							if(graph.getVertex(a[i])==null) {
								A_vertexList.clear();
								B_vertexList.clear();
								break;
							}
//							System.out.println(graph.getVertex(a[i]));
							if(x > rand_prob) {
								A_vertexList.add(graph.getVertex(a[i]));
//								A_vertexList.add(vertex);

							}else {
								B_vertexList.add(graph.getVertex(a[i]));
//								B_vertexList.add(vertex);
							}
							x = Math.random();
						}
//						}
						counter++;
					}while((checkIfHasEdges(A_vertexList,B_vertexList) || A_vertexList.isEmpty() || B_vertexList.isEmpty())&& counter<2000 );

					if(  checkIfHasEdges(A_vertexList,B_vertexList) || A_vertexList.isEmpty() || B_vertexList.isEmpty() || counter>2000 ) {
						continue;
					}	
					flowNetGraph =null;
					flowNetGraph = createFlowNetwork(graph, A_vertexList,B_vertexList); //create flow net graph
					if(flowNetGraph ==null) {
						System.out.println("error in flow nework some of the variables are NULL");
						continue;
					}
					ff =null;
					ff = new FordFulkerson(flowNetGraph);
					int x1=0;
					Collection<Integer> collection_S = null,collection_T =null ;
					// run flow algorithm and fin S and T vertex sets before and after the cut 
					try {
						x1=ff.maxFlow( ff.findVertexIndex(flowNetGraph.getVertex(S)),  ff.findVertexIndex(flowNetGraph.getVertex(T)));//find max flow & min cut between S and T
						collection_S= ff.getS();
						collection_T = ff.getT();
					}catch (Exception e) {
						e.printStackTrace();
					}

					if(x1<=0 || collection_S ==null || collection_T ==null) {
						ff=null;
						flowNetGraph = null;
						continue;
					}
					double s_balance =((double)collection_S.size() /((double) flowNetGraph.getAllVertex().size()*2));
					double t_balance = (double) collection_T.size() / ((double)flowNetGraph.getAllVertex().size()*2);
					if(s_balance< 0.1 || t_balance< 0.1) {
						ff=null;
						flowNetGraph = null;
						continue;
					}
					edgeList.clear(); //array list to hold all the edges of the CUT !
					for(Vertex<Integer> v: flowNetGraph.getAllVertex()) {
						if(collection_S.contains((int)v.getId())) {
							for(Edge<Integer> e : v.getEdges()) {
								if(collection_T.contains((int)e.getVertex2().getId())) {
									edgeList.add(e); //add edge  of the cut 

								}
							}
						}

					}

					ArrayList<Vertex<Integer>> temp = new ArrayList<>();
					for(Edge<Integer> e: edgeList) {

						if(!returnVertexes.contains(e.getVertex1()) && e.getVertex1().getId()!=S ) {
							returnVertexes.add(flowNetGraph.getVertex(Math.abs(e.getVertex1().getId())));
							temp.add(flowNetGraph.getVertex(Math.abs(e.getVertex1().getId())));

						}
						else if(!returnVertexes.contains(e.getVertex2()) && e.getVertex2().getId()!=T) {
							returnVertexes.add(flowNetGraph.getVertex(Math.abs(e.getVertex2().getId())));
							temp.add(flowNetGraph.getVertex(Math.abs(e.getVertex2().getId())));

						}

					}
					ff=null;
					flowNetGraph= null;
					arr.add(temp);

				}
			}

			if(arr.isEmpty()) {
				return null;
			}
			// sort all the return separators maybe it is not necessary(just return the smallest one)  
			arr.sort(new Comparator<ArrayList<Vertex<Integer>>>() {

				@Override
				public int compare(ArrayList<Vertex<Integer>> arr1, ArrayList<Vertex<Integer>> arr2) {
					if(arr1.size()<arr2.size()) {
						return -1;
					}else if(arr1.size()>arr2.size()) {
						return 1;
					}
					return 0;
				}

			});
		}
		return arr.get(0);
	}


	/**get sorted array of arrays of vertexes and print 
	 * */
//	private static void printsForTests(Graph<Integer> graph ,ArrayList<ArrayList<Vertex<Integer>>> arr) {
//		int maxCC =0;
//		for(int i =0; i<Math.min(5, arr.size());i++) {
//			System.out.println("separator size: "+ arr.get(i).size());
//			Graph<Integer> tempGraph=graph.removeVertex(arr.get(i));
//			StronglyConnectedComponent scc = new StronglyConnectedComponent();
//			List<Set<Vertex<Integer>>> result = scc.scc(tempGraph);
//			result = scc.scc(tempGraph);
//
//			//print the sorted result
//			result.sort(new Comparator<Set<Vertex<Integer>>>() {
//				@Override
//				public int compare(Set<Vertex<Integer>> s1, Set<Vertex<Integer>> s2) {
//					if(s1.size()==s2.size()) {
//						return 0;
//					}else if(s1.size()>=s2.size()) {
//						return -1;
//					}else {
//						return 1;
//					}
//				}
//			});
//			double x= 100-((double)result.get(0).size() / (double) graph.getAllVertex().size())*100;
//			System.out.println("percentage of dismantle: "+ x+" %" );
//			System.out.println("Largest CC: "+ result.get(0).size());
//			System.out.print("connected component After dismentle: ");
//			if(result.get(0).size()>maxCC) {
//				maxCC=result.get(0).size();
//			}
//			result.forEach(set -> {
//				System.out.print(set.size()+" ");
//			});
//			System.out.println("fin\n");			
//		}
//		System.out.println("Max CC: "+ maxCC);
//	}

	
	/**create Flow Network to execute flow algorithm and get vertex cut instead edge cut 
	 * 
	 * @param old graph
	 * @param A vertex list 
	 * @param B vertex list
	 * */
	
	private static Graph<Integer> createFlowNetwork(Graph<Integer> graph ,
			ArrayList<Vertex<Integer>> a_vertexList, ArrayList<Vertex<Integer>> b_vertexList) {
		if(graph==null || a_vertexList==null||b_vertexList==null) {
			System.err.println(" graph / allVertex / are / a_vertexList / b_vertexList null in createFlowNetwork method");
			return null;
		}
		Graph<Integer> g2 = copyGraph(graph);
		g2.addSingleVertex(S);//This is "S" vertex
		g2.addSingleVertex(T);//This is "T" vertex
		for(Vertex<Integer> v : g2.getAllVertex()) {
			if(a_vertexList.contains(v))
			{
				g2.addEdge(S, v.getId(), g2.getAllVertex().size()); // create edge between "S" to Vertex in A  with weight |v| 
			}else if(b_vertexList.contains(v)) {
				g2.addEdge(v.getId(), T, g2.getAllVertex().size()); // create edge between Vertex in B to "T" with weight |v| 
			}

		}
		a_vertexList.add(g2.getVertex(S));	// add node S to A
		b_vertexList.add(g2.getVertex(T));	// add vertex T to B
		Set<Vertex<Integer>> vertexToDuplicate = new HashSet<>();
		for(Vertex<Integer> v : g2.getAllVertex()) {
			if(!a_vertexList.contains(v) && !b_vertexList.contains(v)) { // enter to array all node that not in A and B and not vertex T and vertex S
				vertexToDuplicate.add(v);
			}
		}
		Graph<Integer> g=duplicateGraph(g2, vertexToDuplicate); // duplicate all vertex that not in A and B and not vertex T and vertex S
		return g;
	}
	/**
	 * Duplicate a graph
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
		//		System.out.println("vertexToDuplicate: " + vertexToDuplicate);
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
				if(v1==null || v2 ==null)
				{
					return false;
				}
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

		return s;
	}

	/**
	 * convert clauses data structure in to graph body-->head
	 * body  has edge to head
	 * this is a directed
	 * **/
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





	/** 
	 * Dismantle strongest connected component 
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

	public static Graph<Integer> copyGraph(Graph<Integer> oldGraph) {
		Graph<Integer> newGraph = new Graph<>(true);
		if(oldGraph==null)
			return null;
		for(Vertex<Integer> v: oldGraph.getAllVertex()) {
			//			if(v.getId()!= T  && v.getId()!=S ) {
			newGraph.addSingleVertex(v.getId());
			//			}
			//			System.out.println("ffff"+ v.getEdges());
			for(Edge<Integer> e : v.getEdges()) {
				//				System.out.println("ffff "+ e);

				newGraph.addEdge(e.getVertex1().getId(), e.getVertex2().getId(),e.getWeight());
			}

		}

		return newGraph;
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
