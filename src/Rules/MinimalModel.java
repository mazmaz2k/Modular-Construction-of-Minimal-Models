package Rules;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import Graph.Graph;



public class MinimalModel extends Graph<Integer>{

	
	RulesDataStructure DS ;
	boolean readFile = false;
	double avgSourceSize;
	static int rulesNum;
	private static final double MEGABYTE = 1024L * 1024L;

	public MinimalModel() {
		super(true);
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
		m.ModuminUsingWASP();
		System.out.println(m.DS.StringMinimalModel());
//		System.out.print(m.avgSourceSize);
////		System.out.print(",");
//		m.readfile(path);
//		m.ModuMinUsingDP();
//		System.out.print(m.DS.placedValueCounter);
		//System.out.println(m.DS.StringMinimalModel());
		//System.out.print(m.avgSourceSize);
		
		
		
		
		
		
		
		
		

		/***run time checking*/
		/*	
    	long startTime,endTime,totalTime;//in mili sec
		
		startTime = System.currentTimeMillis();
		 m.readfile(path);
		 m.ModuMinUsingDP();

		 endTime   = System.currentTimeMillis();
		 totalTime = endTime - startTime;
		 System.out.print(totalTime);
		
			System.out.print(",");

			startTime = System.currentTimeMillis();
			 m.readfile(path);
			 m.DP();

			 endTime   = System.currentTimeMillis();
			 totalTime = endTime - startTime;
			 System.out.print(totalTime);*/
			 
		 
		
		 
		 //System.out.println(",");

		 
		
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
		minmodel.printList();
		System.out.println("size: "+minmodel.getSize());

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

		try 
		{

			//String path = ".//CnfFile.txt" ;
			sc = new Scanner(new File(path));//read file
			if(sc.hasNext())
				numOfRules = sc.nextInt();
			rulesNum=numOfRules;
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
						return false;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public boolean Modumin()
	{
		int size = DS.SIZE;			
		while(DS.SIZE!=0)
		{

			System.out.println("Rules array SIZE  : " +DS.SIZE);
			//DS.printRulesArray();
//			//DS.checkForUnits();//remove empty sources
			//TODO : print CC and see if I separate them!!!!!!!
			Graph<Integer> g = initGraph(DS, size);
			LinkedList s = sourceOfGraph(g);
			System.out.println("s is: ");
			s.printList();
			double temp=0.2*g.getAllVertex().size(),sSize=s.getSize();
			System.out.println("vals : " + DS.literalMap.toString());
			if(sSize>temp)
			{	
//				System.out.println("Dismantle the CC");
				//get list of vertexes from graph and send it to spliteConnectedComponent on rulesDS
				int[] a=dismntleToArray(g,s); 
//				System.out.println("print array");
//				for (int i = 0; i < a.length; i++)
//				{
//					System.out.println(a[i]);
//				}
				DS.splitConnectedComponent(a);
//				System.out.println("exit split connected component");
			}
			else
			{

				System.out.println("Ts is: ");
				LinkedList Ts=DS.Ts(s);
				Ts.printList();	
				if(!DS.FindMinimalModelForTs(Ts))
				{
					DS.printRulesArray();
					return false;
				}
				System.out.println("vals after : " + DS.literalMap.toString());
				DS.updateRuleDS();
				
			}
		}	
		
//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
//		DS.printValueOfVariables();
		return true;
	}
	*/
	
	
	public boolean ModuMinUsingDP()
	{
		int size = DS.SIZE;	
		DS.removeDoubles();
		while(DS.SIZE!=0)
		{
			//DS.printRulesArray();
			DS.checkForUnits();//remove empty sources
			Graph<Integer> g = initGraph(DS, size);
			LinkedList s = sourceOfGraph(g);
			LinkedList Ts=DS.Ts(s);
			if(!DS.FindMinimalModelForTs(Ts))
			{
//				System.out.println("UNSAT");
//				System.out.println("The amount of time we put value in a variable is : " + DS.counter);
				return false;
			}
			//DS.printValueOfVariables();
			DS.updateRuleDS();
		}		
//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
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
		if(!DS.FindMinimalModelForTs(Ts))
		{
//			System.out.println("UNSAT");
//			System.out.println("The amount of time we put value in a variable is : " + DS.counter);
			return false;
		}
		DS.updateRuleDS();
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


/*	public static boolean ModuMin(RulesDataStructure DS )
	{
		int size = DS.SIZE;			
		while(DS.SIZE!=0)
		{

//			System.out.println("Rules array SIZE  : " +DS.SIZE);
//			DS.printRulesArray();
//			//DS.checkForUnits();//remove empty sources
			//TODO : print CC and see if I seperate them!!!!!!!
			Graph<Integer> g = initGraph(DS, size);
	        StronglyConnectedComponent scc = new StronglyConnectedComponent();
////	        
//////	        List<Set<Vertex<Integer>>> result = scc.scc(g);
////	        System.out.println("------------------------------------------------------------------------------------------------------------------");
////	        System.out.println("Here are the size of all the connected component in the graph");
////	        //print the result
////	        result.forEach(set -> {
////	        	System.out.println("sizeof :"+ set.size());
////	           // set.forEach(v -> System.out.print(v.getId() + " "));
////	            System.out.println();
////	        });
//	        System.out.println("------------------------------------------------------------------------------------------------------------------");
//
			LinkedList s = sourceOfGraph(g);
//			System.out.println("print the source size "+ s.getSize());
//			/***If we have a large source 
//			 * then we build a graph from the source which is a connected component
//			 *  and dismantle the connected component by removing some vertexes ?
			double temp=0.2*g.getAllVertex().size(),sSize=s.getSize();
//			System.out.println("this is the size "+temp+" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

			if(sSize> temp)
			{
				
				System.out.println("Dismantle the CC");
				//get list of vertexes from graph and send it to spliteConnectedComponent on rulesDS
				int[] a=dismntleToArray(g,s); 
//				System.out.println(a.length+" - a length-------------------------------------------------------------------");
				if(!DS.splitConnectedComponent(a))
				{
					System.out.println("exit split connected component");
					return false;
				}

			}
			else
			{

				LinkedList Ts=DS.Ts(s);
				//Ts.printList();
				
				if(!DS.FindMinimalModelForTs(Ts))
				{
//					System.out.println("UNSAT");
//					System.out.println("The amount of time we put value in a variable is : " + DS.counter);
					return false;
				}
				DS.updateRuleDS();
			}
		}
		
		
//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
		return true;
		//	DS.printValueOfVariables();
	} */
	
/*	public static boolean ModuMin(RulesDataStructure DS )
	{
		int size = DS.SIZE;			
		while(DS.SIZE!=0)
		{

//			System.out.println("Rules array SIZE  : " +DS.SIZE);
//			DS.printRulesArray();
			//DS.checkForUnits();//remove empty sources
			Graph<Integer> g = initGraph(DS, size);
			LinkedList s = sourceOfGraph(g);
			LinkedList Ts=DS.Ts(s);
			//Ts.printList();	
			if(!DS.FindMinimalModelForTs(Ts))
			{
//				System.out.println("UNSAT");
//				System.out.println("The amount of time we put value in a variable is : " + DS.counter);
				return false;
			}
			DS.updateRuleDS();
		}		
//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
		return true;
		//	DS.printValueOfVariables();
	}*/
	
	/*
	public static boolean DP(RulesDataStructure DS)
	{
		LinkedList Ts=new LinkedList();
//		System.out.println(rulesNum);
		for (int i = 0; i < rulesNum ; i++) {
			Ts.addAtTail(i);
		}
		if(!DS.FindMinimalModelForTs(Ts))
		{
//			System.out.println("UNSAT");
//			System.out.println("The amount of time we put value in a variable is : " + DS.counter);
			return false;
		}
		DS.updateRuleDS();
		return true;
	}
*/


}
