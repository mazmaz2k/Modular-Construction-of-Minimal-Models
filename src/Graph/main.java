package Graph;

public class main {

	public static void main(String[] args) {
		Graph <Object> g= new Graph<Object>(true);
		g.addSingleVertex(1);
		g.addSingleVertex(2);
		g.addSingleVertex(3);
		Vertex <Object> v=new Vertex<Object>(4);
		g.addEdge(1, 2);
		g.addEdge(1, 3);
		g.addVertex(v);
		g.addEdge(v.id, 3);
		g.addEdge(3, 4);
		System.out.println(g);
		
	}

}
