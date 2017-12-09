//package Graph;
//
//import java.awt.List;
//import Graph.StronglyConnectedComponent;
//import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.Deque;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.List;
//
//
//
//public class Main {
//
//  public static void main(String args[]){
//  Graph<Integer> graph = new Graph<>(true);
//  graph.addEdge(0, 1);
//  graph.addEdge(1, 2);
//  graph.addEdge(2, 0);
//  graph.addEdge(1, 3);
//  graph.addEdge(3, 4);
//  graph.addEdge(4, 5);
//  graph.addEdge(5, 3);
//  graph.addEdge(5, 6);
//
//  StronglyConnectedComponent scc = new StronglyConnectedComponent();
//  List< Set<Vertex<Integer>>> result = scc.scc(graph);
//
//  //print the result
//	  result.forEach(set -> {
//      set.forEach(v -> System.out.print(v.getId() + " "));
//      System.out.println();
//  });
//}
//
//}