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
	public int[][] c;
	private int maxFlow;
	private Collection<Integer> T,S;
	
	
	public FordFulkerson(Graph<Integer> graph) {
		this.T=new LinkedList<Integer>();
		this.S=new LinkedList<Integer>();
		this.maxFlow=0;
		vertexArray=new int[graph.getAllVertex().size()];
		int i=0;
		this.c=new int[graph.getAllVertex().size()][graph.getAllVertex().size()];
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
					//System.out.println("v1: "+v.getId()+ " v2:"+ e.getVertex2().getId());
					if(findVertexIndex(v)!=-1 && findVertexIndex(e.getVertex2())!=-1) {
						c[findVertexIndex(v)][findVertexIndex(e.getVertex2())]=e.getWeight();
						
					}
					
				}
			}
		}
//		for (int j = 0; j < vertexArray.length; j++) {
//			System.out.println(vertexArray[i]);
//
//		}
//		for (int j = 0; j < c.length; j++) {
//			for (int j2 = 0; j2 < c.length; j2++) {
//				System.out.print(c[j][j2]+" ");
//			}
//			System.out.println();
//		}
	
	
	}
	public int findVertexIndex(Vertex<Integer> v) {
		int x=-1;
//		for (int i = 0; i < vertexArray.length; i++) {
//			System.out.print(vertexArray[i]+" ");
//
//		}
//		System.out.println();
//		System.out.println(v +":is-------------------00000000000000000000");

		for(int i=0;i<vertexArray.length;i++) {
			if(vertexArray[i]==v.getId()) {
				//System.out.print("ss");
				return i;
			}
		}
		return x;
	}
	
    public int maxFlow( int source, int sink){
    	T.clear();
    	S.clear();
    	int capacity[][]=this.c;
        //declare and initialize residual capacity as total avaiable capacity initially.
        int residualCapacity[][] = new int[capacity.length][capacity[0].length];
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity[0].length; j++) {
                residualCapacity[i][j] = capacity[i][j];
            }
        }

        //this is parent map for storing BFS parent
        Map<Integer,Integer> parent = new HashMap<>();
        

        //stores all the augmented paths
        List<List<Integer>> augmentedPaths = new ArrayList<>();

        //max flow we can get in this network
        int maxFlow = 0;

        //see if augmented path can be found from source to sink.
        while(BFS(residualCapacity, parent, source, sink)){
            List<Integer> augmentedPath = new ArrayList<>();
            int flow = Integer.MAX_VALUE;
            //find minimum residual capacity in augmented path
            //also add vertices to augmented path list
            int v = sink;
            while(v != source){
                augmentedPath.add(v);
                int u = parent.get(v);
                if (flow > residualCapacity[u][v]) {
                    flow = residualCapacity[u][v];
                }
                v = u;
            }
            augmentedPath.add(source);
            Collections.reverse(augmentedPath);
            augmentedPaths.add(augmentedPath);

            //add min capacity to max flow
            maxFlow += flow;

            //decrease residual capacity by min capacity from u to v in augmented path
            // and increase residual capacity by min capacity from v to u
            v = sink;
            while(v != source){
                int u = parent.get(v);
                residualCapacity[u][v] -= flow;
                residualCapacity[v][u] += flow;
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
        Vertex<Integer> v=new Vertex<Integer>(source);
//      System.out.println(vertexArray[source]+"->>>>>>>"+ source);
        S.add(vertexArray[source]);
        T.remove(vertexArray[source]);
      //  System.out.println(parent);
        System.out.println("T is:"+T);
 //       System.out.println(T);
        System.out.println("S is: "+S);
 //       System.out.println();

//		 printAugmentedPaths(augmentedPaths,maxFlow);
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
           // System.out.println(" flow is: "+maxFlow);
            System.out.println();
        });
    }

    /**
     * Breadth first search to find augmented path
     */
    private boolean BFS(int[][] residualCapacity, Map<Integer,Integer> parent,
            int source, int sink){
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        parent.clear();
        queue.add(source);
        visited.add(source);
        boolean foundAugmentedPath = false;
        //see if we can find augmented path from source to sink
        while(!queue.isEmpty()){
            int u = queue.poll();
            for(int v = 0; v < residualCapacity.length; v++){
                //explore the vertex only if it is not visited and its residual capacity is
                //greater than 0
                if(!visited.contains(v) &&  residualCapacity[u][v] > 0){
                    //add in parent map saying v got explored by u
                    parent.put(v, u);
                    //add v to visited
                    visited.add(v);
                    //add v to queue for BFS
                    queue.add(v);
                    //if sink is found then augmented path is found
                    if ( v == sink) {
                        foundAugmentedPath = true;
                        break;
                    }
                }
            }
        }
        //returns if augmented path is found from source to sink or not
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