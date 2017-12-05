//package Graph;
//
//public class main {
//
//	public static void main(String[] args) {
//		Graph <Object> g= new Graph<Object>(true);
//
//		Vertex <Object> v4=new Vertex<Object>(4);
//		Vertex <Object> v1=new Vertex<Object>(1);
//		Vertex <Object> v2=new Vertex<Object>(2);
//		Vertex <Object> v3=new Vertex<Object>(3);
//		g.addVertex(v1);
//		g.addVertex(v2);
//		g.addVertex(v3);
//		g.addVertex(v4);
//		g.addEdge(v1.getId(), v2.getId());
//		g.addEdge(v2.getId(), v1.getId());
//		g.addEdge(v1.getId(), v3.getId());
//		g.addEdge(v3.getId(), v2.getId());
//		//System.out.println(v1.getEdges().toString() );
//		//g.DFS(v1);
//		StronglyConnectedComponent scc = new StronglyConnectedComponent();
//		List<Set<Vertex<Integer>>> result = scc.scc(graph);
//
//      //print the result
//      result.forEach(set -> {
//          set.forEach(v -> System.out.print(v.getId() + " "));
//          System.out.println();
//      });
//		System.out.println();
//		System.out.println(g);
//		
//	}
//

