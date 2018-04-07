package Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

//https://github.com/Draxent/ConnectedComponents/tree/master/data

public class Vertex_size_97 {
	public static void main(String[] args) {
		Graph<Integer> g = new Graph<>(true);

		
		g.addEdge(1, 65, 1);
		g.addEdge(1, 69, 1);
		g.addEdge(1, 85, 1);

		g.addEdge(2, 31, 1);
		g.addEdge(2, 87, 1);
		g.addEdge(2, 58, 1);
		
		g.addEdge(3, 42, 1);
		g.addEdge(3, 56, 1);

		
		g.addEdge(4, 50, 1);
		g.addEdge(4, 20, 1);
		g.addEdge(4, 64, 1);
		g.addEdge(4, 91, 1);
		
		g.addEdge(5, 44, 1);
		g.addEdge(5, 55, 1);
		g.addEdge(5, 51, 1);
		
		g.addEdge(6, 42, 1);
		g.addEdge(6, 69, 1);
		
		g.addEdge(7, 7, 1);
//		g.addEdge(7, 12, 1);
//		g.addEdge(7, 17, 1);
//  		g.addEdge(7, 21, 1);
		
		g.addEdge(8, 83, 1);
		g.addEdge(8, 47, 1);
	
		g.addEdge(9, 45, 1);
		g.addEdge(9, 17, 1);
		g.addEdge(9, 81, 1);

		g.addEdge(10, 90, 1);
		g.addEdge(10, 99, 1);
		g.addEdge(10, 38, 1);
		
		g.addEdge(11, 25, 1);
		g.addEdge(11, 57, 1);
		g.addEdge(11, 79, 1);
		
		g.addEdge(12, 39, 1);
		g.addEdge(12, 94, 1);
		
		g.addEdge(13, 86, 1);
		g.addEdge(13, 32, 1);
		
		g.addEdge(14, 32, 1);
		g.addEdge(14, 68, 1);
		g.addEdge(14, 35, 1);
		
		g.addEdge(15, 97, 1);
		g.addEdge(15, 40, 1);
		
		g.addEdge(16, 71, 1);
		
		g.addEdge(17, 52, 1);
		g.addEdge(17, 9, 1);
		g.addEdge(17, 45, 1);
		g.addEdge(17, 81, 1);
		
		g.addEdge(18, 82, 1);
		g.addEdge(18, 73, 1);
		g.addEdge(18, 37, 1);
		g.addEdge(18, 49, 1);
		g.addEdge(18, 70, 1);
		
		g.addEdge(19, 73, 1);
		g.addEdge(19, 8, 1);
		
		g.addEdge(20, 4, 1);
		g.addEdge(20, 64, 1);
		g.addEdge(20, 91, 1);
		g.addEdge(20, 50, 1);
		
		g.addEdge(21, 98, 1);
		g.addEdge(21, 90, 1);
		
		g.addEdge(22, 85, 1);

		g.addEdge(23, 40, 1);
		g.addEdge(23, 61, 1);
		g.addEdge(23, 67, 1);

		g.addEdge(24, 58, 1);
		g.addEdge(24, 80, 1);

		g.addEdge(25, 11, 1);
		g.addEdge(25, 79, 1);

		g.addEdge(26, 88, 1);
		g.addEdge(26, 96, 1);
		g.addEdge(26, 60, 1);

		g.addEdge(27, 75, 1);
		g.addEdge(27, 92, 1);

		g.addEdge(29, 99, 1);
		g.addEdge(29, 47, 1);
		
		g.addEdge(30, 75, 1);
		g.addEdge(30, 70, 1);
		g.addEdge(30, 49, 1);

		g.addEdge(31, 2, 1);
		g.addEdge(31, 89, 1);
		g.addEdge(31, 61, 1);
		g.addEdge(31, 77, 1);

		g.addEdge(32, 13, 1);
		g.addEdge(32, 14, 1);
		g.addEdge(32, 78, 1);
		g.addEdge(32, 57, 1);

		g.addEdge(33, 39, 1);

		g.addEdge(34, 89, 1);
		g.addEdge(34, 59, 1);

		g.addEdge(35, 14, 1);
		g.addEdge(35, 68, 1);

		g.addEdge(36, 74, 1);

		g.addEdge(37, 18, 1);
		g.addEdge(37, 49, 1);
		g.addEdge(37, 70, 1);

		g.addEdge(38, 10, 1);
		g.addEdge(38, 90, 1);
		g.addEdge(38, 99, 1);

		g.addEdge(39, 12, 1);
		g.addEdge(39, 94, 1);
		g.addEdge(39, 33, 1);
		g.addEdge(39, 86, 1);

		g.addEdge(40, 15, 1);
		g.addEdge(40, 23, 1);

		g.addEdge(41, 56, 1);
		g.addEdge(41, 88, 1);
		g.addEdge(41, 43, 1);

		g.addEdge(42, 85, 1);
		g.addEdge(42, 6, 1);
		g.addEdge(42, 3, 1);

		g.addEdge(43, 88, 1);
		g.addEdge(43, 41, 1);
		g.addEdge(43, 56, 1);
		
		g.addEdge(44, 5, 1);
		g.addEdge(44, 55, 1);
		g.addEdge(44, 51, 1);
		
		g.addEdge(45, 52, 1);
		g.addEdge(45, 9, 1);
		g.addEdge(45, 17, 1);
		g.addEdge(45, 81, 1);

		g.addEdge(46, 95, 1);
		g.addEdge(46, 76, 1);
		g.addEdge(46, 54, 1);

		g.addEdge(47, 99, 1);
		g.addEdge(47, 29, 1);

		g.addEdge(48, 71, 1);

		g.addEdge(49, 37, 1);
		g.addEdge(49, 18, 1);
		g.addEdge(49, 70, 1);
		g.addEdge(49, 30, 1);

		g.addEdge(50, 4, 1);
		g.addEdge(50, 64, 1);
		g.addEdge(50, 91, 1);
		g.addEdge(50, 20, 1);

		g.addEdge(51, 55, 1);
		g.addEdge(51, 44, 1);
		g.addEdge(51, 5, 1);

		g.addEdge(52, 17, 1);
		g.addEdge(52, 5, 1);
		g.addEdge(52, 93, 1);

		g.addEdge(53, 71, 1);

		g.addEdge(54, 95, 1);
		g.addEdge(54, 46, 1);
		g.addEdge(54, 76, 1);

		g.addEdge(55, 92, 1);
		g.addEdge(55, 5, 1);
		g.addEdge(55, 44, 1);
		g.addEdge(55, 51, 1);

		g.addEdge(56, 3, 1);
		g.addEdge(56, 41, 1);
		g.addEdge(56, 88, 1);
		g.addEdge(56, 43, 1);

		g.addEdge(57, 32, 1);
		g.addEdge(57, 78, 1);
		g.addEdge(57, 79, 1);
		g.addEdge(57, 11, 1);
		g.addEdge(57, 25, 1);

		g.addEdge(58, 24, 1);
		g.addEdge(58, 80, 1);
		g.addEdge(58, 87, 1);
		g.addEdge(58, 2, 1);

		g.addEdge(59, 34, 1);
		g.addEdge(59, 89, 1);

		g.addEdge(60, 96, 1);
		g.addEdge(60, 26, 1);
		g.addEdge(60, 95, 1);

		g.addEdge(61, 77, 1);
		g.addEdge(61, 89, 1);
		g.addEdge(61, 31, 1);
		g.addEdge(61, 23, 1);

		g.addEdge(62, 71, 1);

		g.addEdge(63, 71, 1);

		g.addEdge(64, 4, 1);
		g.addEdge(64, 91, 1);
		g.addEdge(64, 50, 1);
		g.addEdge(64, 20, 1);

		g.addEdge(65, 1, 1);

		g.addEdge(66, 84, 1);
		g.addEdge(66, 98, 1);

		g.addEdge(67, 23, 1);

		g.addEdge(68, 35, 1);
		g.addEdge(68, 14, 1);

		g.addEdge(69, 1, 1);
		g.addEdge(69, 6, 1);

		g.addEdge(70, 18, 1);
		g.addEdge(70, 49, 1);
		g.addEdge(70, 37, 1);
		g.addEdge(70, 30, 1);

		g.addEdge(71, 93, 1);
		g.addEdge(71, 63, 1);
		g.addEdge(71, 53, 1);
		g.addEdge(71, 48, 1);
		g.addEdge(71, 62, 1);
		g.addEdge(71, 16, 1);
		g.addEdge(71, 10, 1);
		g.addEdge(71, 82, 1);

		g.addEdge(72, 84, 1);

		g.addEdge(73, 18, 1);
		g.addEdge(73, 19, 1);
		g.addEdge(73, 8, 1);

		g.addEdge(74, 83, 1);
		g.addEdge(74, 8, 1);
		g.addEdge(74, 36, 1);

		g.addEdge(75, 27, 1);
		g.addEdge(75, 30, 1);

		g.addEdge(76, 95, 1);
		g.addEdge(76, 54, 1);
		g.addEdge(76, 46, 1);

		g.addEdge(77, 61, 1);
		g.addEdge(77, 31, 1);
		g.addEdge(77, 89, 1);

		g.addEdge(78, 32, 1);
		g.addEdge(78, 57, 1);

		g.addEdge(79, 57, 1);
		g.addEdge(79, 11, 1);
		g.addEdge(79, 25, 1);

		g.addEdge(80, 24, 1);
		g.addEdge(80, 58, 1);

		g.addEdge(81, 17, 1);
		g.addEdge(81, 45, 1);
		g.addEdge(81, 9, 1);

		g.addEdge(82, 18, 1);
		g.addEdge(82, 71, 1);

		g.addEdge(83, 8, 1);
		g.addEdge(83, 74, 1);

		g.addEdge(84, 72, 1);
		g.addEdge(84, 66, 1);

		g.addEdge(85, 1, 1);
		g.addEdge(85, 42, 1);
		g.addEdge(85, 22, 1);

		g.addEdge(86, 39, 1);
		g.addEdge(86, 13, 1);

		g.addEdge(87, 2, 1);
		g.addEdge(87, 58, 1);

		g.addEdge(88, 56, 1);
		g.addEdge(88, 41, 1);
		g.addEdge(88, 43, 1);
		g.addEdge(88, 26, 1);

		g.addEdge(89, 31, 1);
		g.addEdge(89, 61, 1);
		g.addEdge(89, 77, 1);
		g.addEdge(89, 59, 1);
		g.addEdge(89, 34, 1);

		g.addEdge(90, 21, 1);
		g.addEdge(90, 10, 1);
		g.addEdge(90, 38, 1);
		g.addEdge(90, 99, 1);

		g.addEdge(91, 20, 1);
		g.addEdge(91, 50, 1);
		g.addEdge(91, 4, 1);
		g.addEdge(91, 64, 1);

		g.addEdge(92, 27, 1);
		g.addEdge(92, 55, 1);

		g.addEdge(93, 52, 1);
		g.addEdge(93, 71, 1);

		g.addEdge(94, 12, 1);
		g.addEdge(94, 39, 1);

		g.addEdge(95, 76, 1);
		g.addEdge(95, 54, 1);
		g.addEdge(95, 46, 1);
		g.addEdge(95, 60, 1);

		g.addEdge(96, 60, 1);
		g.addEdge(96, 26, 1);
		
		g.addEdge(97, 15, 1);

		g.addEdge(98, 66, 1);
		g.addEdge(98, 21, 1);

		g.addEdge(99, 47, 1);
		g.addEdge(99, 29, 1);
		g.addEdge(99, 39, 1);
		g.addEdge(99, 90, 1);
		g.addEdge(99, 10, 1);

		System.out.println(g);
		Graph<Integer> A = new Graph<>(false);
		Vertex<Integer> s= new Vertex<Integer>(46);
		Collection<Integer> N=new java.util.LinkedList<Integer>();
		for(Vertex<Integer> v:g.getAllVertex()) 
		{
			N.add((int) v.getId());

		}

		//System.out.println(N);
		g.constaruction(g,s,N,A);
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
//		System.out.println("A graph: ");
//		System.out.println(A);
		 StronglyConnectedComponent scc = new StronglyConnectedComponent();
	        List<Set<Vertex<Integer>>> result = scc.scc(g);

	        //print the result
	        result.forEach(set -> {
	            set.forEach(v -> System.out.print(v.getId() + " "));
	            System.out.println();
	        });
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		ArrayList<Vertex<Integer>> arr= g.dismantlingStrongestCC(g,A);
		System.out.println("Vertex to remove: "+arr);
		g=g.removeVertex(arr);
//		System.out.println(g);
//		 StronglyConnectedComponent scc = new StronglyConnectedComponent();
		System.out.println("connected component After dismentle: ");

		result = scc.scc(g);

	        //print the result
	        result.forEach(set -> {
	            set.forEach(v -> System.out.print(v.getId() + " "));
	            System.out.println();
	        });


	}


}
