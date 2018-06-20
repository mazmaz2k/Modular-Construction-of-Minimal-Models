package Rules;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import Graph.Graph;
//import Graph.GraphScc;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;
import Rules.LinkedList.Node;





public class MinimalModel extends Graph<Integer>{


	RulesDataStructure DS ;
	boolean readFile = false;
	double avgSourceSize;
	static int rulesNum;
	static int varsNum;
	long runtimeOfseperator;
	private static final double MEGABYTE = 1024L * 1024L;

	public MinimalModel() {
		super(true);
		rulesNum=0;
		varsNum=0;
		runtimeOfseperator=0;
	}

	public static double bytesToMegabytes(double bytes) {
		return bytes / MEGABYTE;
	}
	public static void main(String[] args) 
	{
		MinimalModel m = new MinimalModel();
		//String path=args[0];
		String path=".//CnfFile.txt";


		m.readfile(path);
		m.ModuMinUsingDP();
		System.out.println(m.DS.StringMinimalModel());
		
//////////		//		//System.out.println(m.DS.isConflict());
////////		//m.DS.checkFormat().printList();
////	if(m.ModuMinUsingDP_AndSeperator2())
		//

		//m.graphTest();

		//		//System.out.println(m.DS.isConflict());
		//m.DS.checkFormat().printList();
//		if(m.DP())
//			System.out.println(m.DS.StringMinimalModel());

//		m.graphTest();



		//System.out.println(m.DS.isTheoryPositive());
		//		System.out.print(m.avgSourceSize);
		////		System.out.print(",");
		//		m.readfile(path);
		//		m.ModuMinUsingDP();
		//		System.out.print(m.DS.placedValueCounter);
		//System.out.println(m.DS.StringMinimalModel());
		//System.out.print(m.avgSourceSize);









//
//		/***run time checking*/
//		long startTime,endTime,totalTime;
//		
//		
//		startTime = System.nanoTime();
//		
//		m.readfile(path);
//		m.ModuMinUsingDP_AndSeperator();
//	    endTime   = System.nanoTime();
//		totalTime = endTime - startTime - m.runtimeOfseperator;
//		System.out.print((double)totalTime/1000000);
		
		//System.out.print(",");
//		
//		System.out.print(5);
//		
//		m.readfile(path);
//
//		startTime = System.nanoTime();
//		m.ModuminUsingWASP();
//		// m.DP();
//		
//	    endTime   = System.nanoTime();
//		totalTime = endTime - startTime;
//		System.out.print((double)totalTime/1000000);
//		
		
		
		
		
	
		
//
//			 System.out.print(",");
//			 
//
//				startTime = System.currentTimeMillis();
//			
//				 m.readfile(path);
//				 m.ModuMinUsingDP_AndSeperator();
//			
//				 endTime   = System.currentTimeMillis();
//				 totalTime = endTime - startTime;
//				 System.out.print(totalTime);
			 
			 



		//		m.readfile(path);
		//		m.DP();
		//		System.out.print(m.DS.dpCalls);
		//		System.out.print(",");
		//		m.readfile(path);
		//		m.ModuMinUsingDP();
		//		System.out.print(m.moduminDPcalls);
		//m.DP();
		//System.out.println("dp calls: "+m.DS.dpCalls);


		//m.Modumin();
		//m.WASP();
		//System.out.print(m.DS.StringMinimalModel());

		/**memory usage checking**/
		/*	m.readfile(path);
		m.WASP();
		Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        double memory = runtime.totalMemory() - runtime.freeMemory();

        System.out.print(bytesToMegabytes(memory));
        System.out.print(",");

        m.readfile(path);
        m.ModuminUsingWASP();
        runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
         memory = runtime.totalMemory() - runtime.freeMemory();
       // System.out.println("Used memory is bytes: " + memory);
      //  System.out.println("Used memory is megabytes: "
        //        + bytesToMegabytes(memory));
        System.out.print(bytesToMegabytes(memory));*/
		//		if(m.Modumin())
		//		{
		//			System.out.println("SAT The minimal model is: "+ m.DS.StringMinimalModel());		
		//		}
		//		else 
		//		{
		//			System.out.println("UNSAT");
		//		}



		/*LinkedList l= m.DS.checkFormat();
		if(l.getSize()==0)
		{
			if(m.Modumin())
			{
				System.out.println("SAT The minimal model is: "+ m.DS.StringMinimalModel());

			}
			else 
			{
				System.out.println("UNSAT");
			}
		}
		else
		{
			System.out.println("Please correct lines: ");
			l.printList();
			System.out.println("Its not in the right format");
			System.out.println("Can't be a clause where all litarals are negative");
		}*/



	}
	public void WASP()
	{
		LinkedList Ts=new LinkedList();
		for (int i = 0; i < rulesNum ; i++) {
			Ts.addAtTail(i);
		}
		BufferedWriter bw = null;
		FileWriter fw = null;
		String FILENAME=".//alviano-wasp-f3fed39/build/release/ex";
		String[] cnfContent=getCnfContent(Ts);	

		try
		{
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			for (int i = 0; i < cnfContent.length; i++) 
			{
				bw.write(cnfContent[i]);
				bw.newLine();
			}

		}
		catch (IOException e ) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		LinkedList minmodel = MinimalModelFromScript();
		if(minmodel.head!=null)
		{
			if(minmodel.head.var==-1)
				System.out.println("unsatisfiable");
		}
//		minmodel.printList();
//		System.out.println("size: "+minmodel.getSize());

	}
	

	public LinkedList MinimalModelFromScript()
	{
		//System.out.println("reading minimal model");
		String s ="python3 cnf2lparse.py ex | ./wasp --minimize-predicates=a --minimization-algorithm=guess-check-split --silent" ;

		String[] cmd = {
				"/bin/sh",
				"-c",
				s
		};

		String path = ".//alviano-wasp-f3fed39/build/release";
		LinkedList list = new LinkedList();
		try {
			Process p =Runtime.getRuntime().exec(cmd,null,new File(path));
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

			//	String line;
			//System.out.println(in.readLine());
			String line = in.readLine();
			if(line.equals("{}"))
			{
				return list;
			}
			if(line.equals("INCOHERENT"))
			{
				list.addAtTail(-1);
				return list;
			}
			String[] str = line.split(" ");
			for (int i = 0; i < str.length; i++) {
				str[i]=str[i].replace("a", "");
				str[i]=str[i].replace("{", "");
				str[i]=str[i].replace("}", "");
				str[i]=str[i].replace("(", "");
				str[i]=str[i].replace(")", "");
				str[i]=str[i].replace(",", "");
			}
			for (int j = 0; j < str.length; j++) 
			{				
				list.addAtTail(Integer.parseInt(str[j]));
			}

		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void readfile(String path)
	{

		Scanner sc;
		int var ;
		int index = 0;
		int numOfRules=0;
		int numOfVariables=0;

		try 
		{

			//String path = ".//CnfFile.txt" ;
			sc = new Scanner(new File(path));//read file
			if(sc.hasNext())
				numOfRules = sc.nextInt();
			rulesNum=numOfRules;
			if(sc.hasNext())
				numOfVariables = sc.nextInt();
			varsNum=numOfVariables;
			DS = new RulesDataStructure(numOfRules);
			while (sc.hasNext()) 
			{
				var = sc.nextInt();
				if(var!=0)
					DS.addToRulsArray(index, var);
				else
					index++;
			}
			//System.out.println("File was read successfully");
		}catch (FileNotFoundException ex)
		{
			//ex.printStackTrace();
			//System.out.println("Error on reading the file");
		}

	}

	public boolean ModuminUsingWASP()
	{
		DS.removeDoubles();
		int size = DS.SIZE;		
		Graph<Integer> g;
		LinkedList source ,Ts, minmodel;
		double sumSorceSize=0.0;
		double numOfSources=0.0;
		while(DS.SIZE!=0)
		{
			//DS.printRulesArray();
			/**unity check*/
			DS.checkForUnits();
			/**create graph*/
			g = initGraph(DS, size);
			/**find source*/
			source = sourceOfGraph(g);
			//System.out.println("ver size: "+g.getAllVertex().size()+ " source size; " +source.getSize());
			numOfSources++;
			sumSorceSize+=source.getSize();
			/**Find Ts*/
			Ts=DS.Ts(source);

			if(Ts.getSize()>0)
			{
				/**Write cnf to file */
				writeToFile(Ts);			
				/**find minimal model for Ts*/
				minmodel = MinimalModelFromScript();
				if(minmodel.head!=null)
				{
					if(minmodel.head.var==-1)//unsat
					{
						System.out.println("UNSAT");
						return false;
					}
					/**put the minimal model in the literal map*/
					DS.putMinModelInLiteralMap(minmodel);

				}

			}		
			/**Update the rules data structure*/
			DS.updateRuleDS();	
		}
		//System.out.println(numOfSources);
		this.avgSourceSize=sumSorceSize/numOfSources;
		return true;
	}

	public void writeToFile(LinkedList Ts)
	{
		//System.out.println("writing to file");
		BufferedWriter bw = null;
		FileWriter fw = null;
		String FILENAME=".//alviano-wasp-f3fed39/build/release/ex";
		String[] cnfContent=getCnfContent(Ts);	

		try
		{
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			for (int i = 0; i < cnfContent.length; i++) 
			{
				bw.write(cnfContent[i]);
				bw.newLine();
			}

		}
		catch (IOException e ) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	public String[] getCnfContent(LinkedList Ts)
	{
		int size = Ts.getSize();
		String[] toReturn= new String[size];
		Rules.LinkedList.Node nTs ;
		Rules.LinkedList.Node nBody;
		Rules.LinkedList.Node nHead;

		nTs=Ts.head;
		for (int i = 0; i < size; i++) 
		{
			String oneRule="";
			nBody=DS.RulesArray[nTs.var].body.head;
			while(nBody!=null)
			{
				oneRule+="-"+nBody.var+" ";
				nBody=nBody.next;
			}
			nHead=DS.RulesArray[nTs.var].head.head;
			while(nHead!=null)
			{
				oneRule+=nHead.var+" ";
				nHead=nHead.next;
			}
			oneRule+="0";
			toReturn[i]=oneRule;
			nTs=nTs.next;
		}


		return toReturn;
	}














	public boolean ModuMinUsingDP()
	{
		int size = DS.SIZE;	
		DS.removeDoubles();
		int biggestSource=0;
		while(DS.SIZE!=0)
		{
			//DS.printRulesArray();
			//System.out.println("SIZE: " + DS.SIZE);
			DS.checkForUnits();//remove empty sources
			//System.out.println("positive theory? " + DS.isTheoryPositive());
			Graph<Integer> g = initGraph(DS, size);
			LinkedList s = sourceOfGraph(g);
			if(s.getSize()> biggestSource)
			{
				biggestSource= s.getSize();
			}
			//System.out.println("size: " + s.getSize());
			LinkedList Ts=DS.Ts(s);
			//System.out.println("Ts size: " + Ts.getSize());
			if(!DS.FindMinimalModelForTs(Ts))
			{
				System.out.println("UNSAT");
				//				System.out.println("The amount of time we put value in a variable is : " + DS.counter);
				return false;
			}
			//DS.printValueOfVariables();
			DS.updateRuleDS();
		}		
		//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
		Collections.sort(DS.minModel);
		//System.out.print(biggestSource/varsNum);
		return true;
	}

	public boolean DP()
	{
		DS.removeDoubles();
		LinkedList Ts=new LinkedList();
		//		System.out.println(rulesNum);
		for (int i = 0; i < rulesNum ; i++) {
			Ts.addAtTail(i);
		}
		
		//Ts.printList();
		if(!DS.FindMinimalModelForTs(Ts))
		{
			//			System.out.println("UNSAT");
			//			System.out.println("The amount of time we put value in a variable is : " + DS.counter);
			return false;
		}
		DS.updateRuleDS();
		//System.out.println(DS.SIZE);
		return true;
	}
	
	 
	 



	public void graphTest() {


		int size = DS.SIZE;			

		Graph<Integer> g = initGraph(DS, size);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(g);

		System.out.println("Original CC: ");
		//print the result
		result.forEach(set -> {
			set.forEach(v -> System.out.print(v.getId() + " "));
			System.out.println();
		});
		if(result.get(0).size()!=g.getAllVertex().size()) {
			System.out.println("not cc");
			return ;
		}
		ArrayList<ArrayList<Vertex<Integer>>> arr=new ArrayList<>();;
		ArrayList<Vertex<Integer>> min_array=new ArrayList<>();
		min_array= Graph.vertexSeparator(g);
		//			System.out.println(i+") "+arr.get(i));

		int i=0;
		System.out.println("Min Vertex to remove: " + min_array + " Size of the Seperator: "+ min_array.size());			
		Graph<Integer> copyGraph = copyGraph(g);


		//		for(ArrayList<Vertex<Integer>> vertexToremove : arr) {
		//			System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		////			System.out.println("Vertex to remove: " + vertexToremove + " Size of the Seperator: "+ vertexToremove.size());			
		//			g = initGraph(DS, size);
		////			copyGraph = copyGraph(g);
		//			g=g.removeVertex(vertexToremove);
		//
		//			//		System.out.println(g);
		//			//		 StronglyConnectedComponent scc = new StronglyConnectedComponent();
		//			System.out.println(i+") "+vertexToremove + ", Size of the Seperator: "+ vertexToremove.size());
		//			System.out.println("connected component After dismentle: ");
		//			result = scc.scc(g);
		//
		//			//print the result
		//			result.forEach(set -> {
		//				set.forEach(v -> System.out.print(v.getId() + " "));
		//				System.out.println();
		//			});
		//			//copyGraph = copyGraph(g);
		//			i++;
		//		}

	}



	
	public boolean ModuMinUsingDP_AndSeperator()
	{
		int size = DS.SIZE;	
		int allVertexes=0;
		boolean first =true;
		Graph<Integer> g = null ;
		DS.removeDoubles();
		StronglyConnectedComponent scc;
		List<Set<Vertex<Integer>>> result;
		while(DS.SIZE!=0)
		{
			DS.checkForUnits();//remove empty sources
			//DS.printRulesArray();
			//	DS.IntegrityConstraint();//added

			g = initGraph(DS, size);
			if(first)
			{
				allVertexes=g.getAllVertex().size();
				//System.out.println("alllllllllllll vertexessssssss : "+ allVertexes);
				first= false;
			}
//			scc=null;
//			scc = new StronglyConnectedComponent();
//			result=null;
//			result = scc.scc(g);

			LinkedList s= sourceOfGraph(g);
			//System.out.println("Source size: " + s.getSize());
			double ratio = (double)s.getSize() / allVertexes;
			if(ratio > 0.2 ) 
			{
				//System.out.println("source Is TOO BIG ");
				//System.out.println();
				//System.out.println(s.getSize());
				Graph<Integer> graph = createGraphFromSource(s,g);

				long startTime,endTime,totalTime;//in mili sec

				startTime = System.currentTimeMillis();
				//System.out.println("before");
				
				ArrayList<Vertex<Integer>> arrayToRemove = vertexSeparator(graph);

				//System.out.println(arrayToRemove);


				 endTime   = System.currentTimeMillis();
				 totalTime = endTime - startTime;
				 this.runtimeOfseperator=totalTime;
			  //  System.out.println("sep size " +this.runtimeOfseperator);

				if(arrayToRemove==null)
				{
					System.out.print("0,");
					LinkedList Ts=DS.Ts(s);
					//	System.out.println("TS");
					//Ts.printList();
					//DS.printRulesArray();
					if(!DS.FindMinimalModelForTs(Ts))
					{
						//System.out.println("UNSAT");
						//				System.out.println("The amount of time we put value in a variable is : " + DS.counter);
						//DS.printRulesArray();
						return false;
					}
					//DS.printValueOfVariables();
					DS.updateRuleDS();
				}
				else
				{
				    System.out.print(arrayToRemove.size()+",");
					LinkedList Ts=DS.Ts(s);
				//Ts.printList();
					DS.SplitConnectedComponent(arrayToRemove, Ts);
			
				//System.out.println(graph);
				//DS.printRulesArray();
				}

				//System.out.println("AFTER SPLIT===============");
				//DS.printRulesArray();
			//	System.out.println("CHECK IF THE THEORY IS POSITIVE   #############################"  + DS.isTheoryPositive());

			}
			else
			{
				//	System.out.println("source size: " + s.getSize());
				//	s.printList();

				LinkedList Ts=DS.Ts(s);
				//	System.out.println("TS");
				//Ts.printList();
				//DS.printRulesArray();
				if(!DS.FindMinimalModelForTs(Ts))
				{
					//System.out.println("UNSAT");
					//				System.out.println("The amount of time we put value in a variable is : " + DS.counter);
					//DS.printRulesArray();
					return false;
				}
				//DS.printValueOfVariables();
				DS.updateRuleDS();
			}





		}		
		//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
		Collections.sort(DS.minModel);

		return true;
	}

//	public boolean ModuMinUsingDP_AndSeperator3()
//	{
//		int size = DS.SIZE;	
//		int allVertexes=0;
//		boolean first =true;
//		Graph<Integer> g = null ;
//		DS.removeDoubles();
//		StronglyConnectedComponent scc;
//		List<Set<Vertex<Integer>>> result;
//		while(DS.SIZE!=0)
//		{
//			DS.checkForUnits();//remove empty sources
//			//DS.printRulesArray();
//			//	DS.IntegrityConstraint();//added
//
//			g = initGraph(DS, size);
//			if(first)
//			{
//				allVertexes=g.getAllVertex().size();
//				//System.out.println("alllllllllllll vertexessssssss : "+ allVertexes);
//				first= false;
//			}
////			scc=null;
////			scc = new StronglyConnectedComponent();
////			result=null;
////			result = scc.scc(g);
//
//			LinkedList s= sourceOfGraph(g);
//			//System.out.println("Source size: " + s.getSize());
//			double ratio = (double)s.getSize() / allVertexes;
//			if(ratio > 0.2 ) 
//			{
//				//System.out.println("source Is TOO BIG ");
//				//System.out.println();
//				//System.out.println(s.getSize());
//				Graph<Integer> graph = createGraphFromSource(s,g);
//
//				long startTime,endTime,totalTime;//in mili sec
//
//				startTime = System.currentTimeMillis();
//				//System.out.println("before");
//				
//				ArrayList<Vertex<Integer>> arrayToRemove = vertexSeparator(graph);
//
//				//System.out.println(arrayToRemove);
//
//
//				 endTime   = System.currentTimeMillis();
//				 totalTime = endTime - startTime;
//				 this.runtimeOfseperator=totalTime;
//			  //  System.out.println("sep size " +this.runtimeOfseperator);
//
//				if(arrayToRemove==null)
//				{
//					//System.out.println("nulllll");
//					LinkedList Ts=DS.Ts(s);
//					//	System.out.println("TS");
//					//Ts.printList();
//					//DS.printRulesArray();
//					if(!DS.FindMinimalModelForTs(Ts))
//					{
//						//System.out.println("UNSAT");
//						//				System.out.println("The amount of time we put value in a variable is : " + DS.counter);
//						//DS.printRulesArray();
//						return false;
//					}
//					//DS.printValueOfVariables();
//					DS.updateRuleDS();
//				}
//				else
//				{
//				    //System.out.println(arrayToRemove);
//					LinkedList Ts=DS.Ts(s);
//				//Ts.printList();
//					Try(arrayToRemove, Ts);
//			
//				//System.out.println(graph);
//				//DS.printRulesArray();
//				}
//
//				//System.out.println("AFTER SPLIT===============");
//				//DS.printRulesArray();
//			//	System.out.println("CHECK IF THE THEORY IS POSITIVE   #############################"  + DS.isTheoryPositive());
//
//			}
//			else
//			{
//				//	System.out.println("source size: " + s.getSize());
//				//	s.printList();
//
//				LinkedList Ts=DS.Ts(s);
//				//	System.out.println("TS");
//				//Ts.printList();
//				//DS.printRulesArray();
//				if(!DS.FindMinimalModelForTs(Ts))
//				{
//					//System.out.println("UNSAT");
//					//				System.out.println("The amount of time we put value in a variable is : " + DS.counter);
//					//DS.printRulesArray();
//					return false;
//				}
//				//DS.printValueOfVariables();
//				DS.updateRuleDS();
//			}
//
//
//
//
//
//		}		
//		//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
//		Collections.sort(DS.minModel);
//
//		return true;
//	}
//	 
//    public void Try(ArrayList<Vertex<Integer>> v, LinkedList Ts)
//    {
//    	ArrayList<Clause> clauses = new ArrayList<>();
//    	Node n = Ts.head;
//    	while(n!=null) 
//    	{
//    		Node nBody =DS.RulesArray[n.var].body.head;
//			Node nHead = DS.RulesArray[n.var].head.head;
//			Clause clause = new Clause();
//			String literal;
//			while(nBody!=null)//first put the negative literals in order to calculate a minimal model (because of the way that DLL works)
//			{
//				literal = "-";
//				literal+= String.valueOf(nBody.var);
//				clause.addLiteral(literal);
//				nBody=nBody.next;
//			}
//			while(nHead!=null)
//			{
//				literal = String.valueOf(nHead.var); 
//				clause.addLiteral(literal);
//				nHead=nHead.next;
//			}
//
//			clauses.add(clause);
//			n=n.next;
//		}
//    	
//    	
//    	
//    	int size = v.size();
//       // System.out.println("size of array to remove is: " + size);
//        int N = (int)Math.pow(2,size); 
//       // System.out.println(N);
//        boolean[] binaryArray ;
//    	String literal;
//    	Clause clause;
//    	ArrayList<Clause> copy;
//        for (int i = 0; i < N; i++)//from 0 to 2^n -1
//        {
//        	//System.out.println(i);
//        	copy = new ArrayList<Clause>();//
//        	for(Clause c: clauses)
//        	{
//        		Clause c2 = new Clause();
//        		for(String s: c.literals)
//        		{
//        			c2.addLiteral(s);
//        		}
//        		copy.add(c2);
//        	}// copy original to not make any changes
//        	binaryArray = DS.toBinary(i,size);//returns array
//        	LinkedList minmod = new LinkedList();
//        	for (int j = 0; j < size; j++) 
//        	{		
//        		if(binaryArray[j])
//        			minmod.addAtTail((int)v.get(j).getId());
//        		DS.ChangeDataStrucureByPlacingValueInVar((int)v.get(j).getId(),binaryArray[j]);
//        		//System.out.println( "Adding clause: "+clause.printClause());	
//    		}
//        	//check if sat
//        	//System.out.println("check sat");
//        	//printRulesArray();
//        	//System.out.println(i +"  "+ N);
//        	if(ModuMinUsingDP())
//        	{
//        		break;
//        	}
//       	}
//    	//System.out.println("not found");
//     	
//    }
//    


}
