package Graph;

import java.util.*;

/**
 * Date 04/14/2014
 * @author Tushar Roy
 *
 * Ford fulkerson method Edmonds Karp algorithm for finding max flow
 *
 * Capacity - Capacity of an edge to carry units from source to destination vertex
 * Flow - Actual flow of units from source to destination vertex of an edge
 * Residual capacity - Remaining capacity on this edge i.e capacity - flow
 * AugmentedPath - Path from source to sink which has residual capacity greater than 0
 *
 * Time complexity is O(VE^2)
 *
 * References:
 * http://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
 * https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm
 */
public class FordFulkerson {

	public static int[] vertexArray;//= {"S","2","3","4","5","6","T"};
	private int maxFlow;
	private Collection<Integer> T,S;
	private class Node{
		int indexJ;
		int wight;
		Node(int idx ,int wight){
			this.indexJ=idx;
			this.wight = wight;
		} 
	}
	private class ListNode{

		private LinkedList<Node> listNode;
		private int indexI;

		ListNode(int idx) {
			this.listNode = new LinkedList<Node>();
			this.indexI=idx;
		}
		public LinkedList<Node> getList() {
			return this.listNode;
		}
		public int getindexI() {
			return this.indexI;
		}

	}

	private LinkedList<ListNode> resArray;


	public FordFulkerson(Graph<Integer> graph) {
		this.T=new LinkedList<Integer>();
		this.S=new LinkedList<Integer>();
		this.maxFlow=0;
		vertexArray=new int[graph.getAllVertex().size()];
		int i=0;
		this.resArray = new LinkedList<ListNode>();
//		this.c=new int[graph.getAllVertex().size()][graph.getAllVertex().size()];
		for(Vertex<Integer> v: graph.getAllVertex()) {
			vertexArray[i]=(int)v.getId();
			i++;
		}
		//graph.getAllVertex().
		for(Vertex<Integer> v: graph.getAllVertex()) 
		{
			for(Edge<Integer> e : graph.getAllEdges()) 
			{
				if(v.equals(e.getVertex1())) 
				{						
					if(findVertexIndex(v) != -1) {
						Node n =new Node(findVertexIndex(e.getVertex2()), e.getWeight());
						if(hasIndexInList(findVertexIndex(v))) {
							setDataTolist(resArray,findVertexIndex(v),findVertexIndex(e.getVertex2()),e.getWeight());			
						}else
						{
							ListNode list = new ListNode(findVertexIndex(v));
							list.listNode.add(n);
							resArray.add(list);	
						}
					}

				}
			}
		}

//		for(ListNode pNode : resArray) {
//			//			System.out.println("I "+ pNode.indexI);
//			System.out.print("I: "+ pNode.indexI+" J: ");
//			for(Node node :pNode.getList()) {
//				System.out.print( node.indexJ+" ");	
//			}
//			System.out.println();
//		}

	}

	public void setDataTolist(LinkedList<ListNode> list,int i ,int j, int w) {
		for(ListNode l: list) {
			if(l.getindexI()==i) {
				for(Node n: l.getList() ) {
					if(n.indexJ==j) {
						l.getList().set(findNodeIndex(list, l.indexI, n.indexJ), new Node(j,w));
						return;
					}

				}
				l.getList().add(new Node(j, w));
				return;
			}
		}
		Node n = new Node(j , w);
		ListNode l = new ListNode(i);
		l.getList().add(n);
		list.add(l);
	}


	public int findNodeIndex(LinkedList<ListNode> listNode,int i ,int j) {
		int idx=-1;
		for(ListNode list : listNode) {
			idx=0;
			if(list.indexI==i) {
				for(Node n: list.listNode) {
					if(n.indexJ==j)
					{
						return idx;
					}
					idx++;
				}

			}
		}

		return -1;
	}

	public int getWightFromList (LinkedList<ListNode> list,int i ,int j) {
		for(ListNode l: list) {
			if(l.getindexI()==i) {
				for(Node n: l.getList() ) {
					if(n.indexJ==j)
						return n.wight;
				}
			}
		}
		return -1;
	}

	public boolean hasIndexInList(int idx) {

		for(ListNode listnode: resArray) {
			if(listnode.indexI==idx)
				return true;
		}
		return false;
	}

	public int findVertexIndex(Vertex<Integer> v) {
		int x=-1;
		for(int i=0;i<vertexArray.length;i++) {
			if(vertexArray[i]==v.getId()) {
				return i;
			}
		}
		return x;
	}
	public LinkedList<ListNode> copy2DList(LinkedList<ListNode> resList){
		LinkedList<ListNode> list2D = new LinkedList<ListNode>(); 
		for(ListNode l : resList) {
			ListNode list_node = new ListNode(l.indexI);
			for(Node n: l.getList() ) {
				Node n2 = new Node(n.indexJ, n.wight);
				list_node.listNode.add(n2);
			}
			list2D.add(list_node);
		}

		return list2D;
	}
	public int maxFlow( int source, int sink){
		T.clear();
		S.clear();
		//    	int capacity[][]=this.c;

		//declare and initialize residual capacity as total avaiable capacity initially.
		//        int residualCapacity[][] = new int[capacity.length][capacity[0].length];
		//        for (int i = 0; i < capacity.length; i++) {
		//            for (int j = 0; j < capacity[0].length; j++) {
		//                residualCapacity[i][j] = capacity[i][j];
		//            }
		//        }
		LinkedList<ListNode> resCapacity =copy2DList(this.resArray);
		//this is parent map for storing BFS parent
		Map<Integer,Integer> parent = new HashMap<>();


		//stores all the augmented paths
		List<List<Integer>> augmentedPaths = new ArrayList<>();

		//max flow we can get in this network
		int maxFlow = 0;

		//see if augmented path can be found from source to sink.
		while(BFS(resCapacity, parent, source, sink)){

			List<Integer> augmentedPath = new ArrayList<>();
			int flow = Integer.MAX_VALUE;
			//find minimum residual capacity in augmented path
			//also add vertices to augmented path list
			int v = sink;

			while(v != source){
				augmentedPath.add(v);
				int u = parent.get(v);
				if (flow > getWightFromList(resCapacity, u, v)) {
					flow = getWightFromList(resCapacity, u, v);
				}
				v = u;
			}
			augmentedPath.add(source);
//			System.out.println(augmentedPath);
//			System.out.println("source " +source);

			Collections.reverse(augmentedPath);
			augmentedPaths.add(augmentedPath);

			//add min capacity to max flow
			maxFlow += flow;

			//decrease residual capacity by min capacity from u to v in augmented path
			// and increase residual capacity by min capacity from v to u
			v = sink;
			while(v != source){
				int u = parent.get(v);
				setDataTolist(resCapacity, u, v, getWightFromList(resCapacity, u, v)-flow);// residualCapacity[u][v] -= flow;
				setDataTolist(resCapacity, v, u, getWightFromList(resCapacity, v, u)+flow);//residualCapacity[v][u] += flow;
				v = u;
			}
		}

		for (int i = 0; i < vertexArray.length; i++) {
			if(parent.containsKey(i)) {
				S.add(vertexArray[i]);
			}else {
				T.add(vertexArray[i]);
			}
		}
		//        Vertex<Integer> v=new Vertex<Integer>(source);
		//      System.out.println(vertexArray[source]+"->>>>>>>"+ source);
		S.add(vertexArray[source]);
		T.remove(vertexArray[source]);
		//       System.out.println(parent);
		//      System.out.println("T is:"+T);
		//       System.out.println(T);
		//       System.out.println("S is: "+S);
		//       System.out.println();

//		printAugmentedPaths(augmentedPaths,maxFlow);
		this.maxFlow=maxFlow;
		return maxFlow;
	}

	public int getMaxFlow() {
		return this.maxFlow;
	}

	/**
	 * Prints all the augmented path which contribute to max flow
	 */
	private void printAugmentedPaths(List<List<Integer>> augmentedPaths,int maxFlow) {
		System.out.println("Augmented paths");
		augmentedPaths.forEach(path -> {
			path.forEach(i -> System.out.print(vertexArray[i] + " "));
			//            System.out.println(" flow is: "+maxFlow);
			System.out.println();
		});
	}

	/**
	 * Breadth first search to find augmented path
	 */
	private boolean BFS(LinkedList<ListNode> resList, Map<Integer,Integer> parent,
			int source, int sink){
		
		Set<Integer> visited = new HashSet<>();
		Queue<Integer> queue = new LinkedList<>();
		parent.clear();
		queue.add(source);
		visited.add(source);
		boolean foundAugmentedPath = false;
		//see if we can find augmented path from source to sink
//		System.out.println(queue);
		while(!queue.isEmpty()){

			int u = queue.poll();
			for(ListNode list :resList) {
				//explore the vertex only if it is not visited and its residual capacity is
				//greater than 0	

				if(!visited.contains(list.getindexI()) && getWightFromList(resList, u, list.indexI)>0 ) {
					//add in parent map saying v got explored by u
					parent.put(list.getindexI(), u);
					//add v to visited
					visited.add(list.getindexI());
					//add v to queue for BFS
					queue.add(list.getindexI());
					//if sink is found then augmented path is found
					if ( list.getindexI() == sink) {
//						System.out.println("index J "+list.indexI+" sink is: " +sink);
						foundAugmentedPath = true;
						break;
					}


				}

			}
			
			
		}
		//        //returns if augmented path is found from source to sink or not
		return foundAugmentedPath;
	}

	public static void main(String args[]){
		//       
		//        int[][] capacity = {{0, 1, 0, 3, 0, 0, 0},
		//                            {0, 0, 4, 0, 0, 0, 0},
		//                            {3, 0, 0, 1, 2, 0, 0},
		//                            {0, 0, 0, 0, 2, 6, 0},
		//                            {0, 1, 0, 0, 0, 0, 1},
		//                            {0, 0, 0, 0, 0, 0, 9},
		//                            {0, 0, 0, 0, 0, 0, 0}};
		//        

		Graph<Integer> graphMaxFlow = new Graph<>(true);
		graphMaxFlow.addEdge(0, 3, 1);
		graphMaxFlow.addEdge(0, 4, 1);
		graphMaxFlow.addEdge(1, 0, 1);
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
		FordFulkerson ff = new FordFulkerson(graphMaxFlow);
		System.out.println(graphMaxFlow.getAllVertex());
		System.out.println("\nMaximum capacity " + ff.maxFlow( 0, 6));
		System.out.println("vertexArray is :");
		for (int i = 0; i < vertexArray.length; i++) {
			System.out.print(vertexArray[i]+" ");
		}
		System.out.println();
		System.out.println("T: "+ff.getT());
		System.out.println("S: "+ ff.getS());
	}


	public Collection<Integer> getS() {
		return S;
	}


	public void setS(Collection<Integer> s) {
		S = s;
	}


	public Collection<Integer> getT() {
		return T;
	}


	public void setT(Collection<Integer> t) {
		T = t;
	}
}