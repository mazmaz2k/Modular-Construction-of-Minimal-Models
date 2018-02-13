package Rules;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.spec.DSAGenParameterSpec;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import Graph.Graph;
import Graph.StronglyConnectedComponent;
import Graph.Vertex;

public class MinimalModel extends Graph<Integer>{

	private JFrame frame;
	private JTextField textField;
	RulesDataStructure DS ;
	boolean readFile = false;
	static int rulesNum;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MinimalModel window = new MinimalModel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		MinimalModel m = new MinimalModel();
		m.readfile();
		if(m.Modumin())
		{
			System.out.println("SAT The minimal model is: "+ m.DS.StringMinimalModel());
			
		}
		else 
		{
			System.out.println("UNSAT");
		}
	}
	
	public void readfile()
	{

		Scanner sc;
		int var ;
		int index = 0;
		int numOfRules;

		try 
		{

			String Path = ".\\CnfFile.txt" ;
			sc = new Scanner(new File(Path));//read file
			numOfRules = sc.nextInt();
			rulesNum=numOfRules;
			DS = new RulesDataStructure(numOfRules);
			while (sc.hasNextLine()) 
			{
				var = sc.nextInt();
				if(var!=0)
					DS.addToRulsArray(index, var);
				else
					index++;
			}
			System.out.println("File was read successfully");
		}catch (FileNotFoundException ex)
		{
			// TODO Auto-generated catch block
			//ex.printStackTrace();
			System.out.println("Error on reading the file");


		}

	}
	
	public boolean Modumin()
	{
		int size = DS.SIZE;			
		while(DS.SIZE!=0)
		{

//			System.out.println("Rules array SIZE  : " +DS.SIZE);
//			DS.printRulesArray();
//			//DS.checkForUnits();//remove empty sources
			//TODO : print CC and see if I seperate them!!!!!!!
			Graph<Integer> g = initGraph(DS, size);
			LinkedList s = sourceOfGraph(g);
			double temp=0.2*g.getAllVertex().size(),sSize=s.getSize();
			if(sSize> temp)
			{
				
				System.out.println("Dismantle the CC");
				//get list of vertexes from graph and send it to spliteConnectedComponent on rulesDS
				int[] a=dismntleToArray(g,s); 
				System.out.println(a.length+" - a length-------------------------------------------------------------------");
				DS.splitConnectedComponent(a);
				
//					Graph<Integer> g2 = initGraph(DS, size);
//			        List<Set<Vertex<Integer>>> result = scc.scc(g2);
//			        System.out.println("------------------------------------------------------------------------------------------------------------------");
//			        System.out.println("Here are the size of all the connected component in the graph after Dismantle the CC");
//			        //print the result
//			        result.forEach(set -> {
//			        	System.out.println("sizeof :"+ set.size());
//			           // set.forEach(v -> System.out.print(v.getId() + " "));
//			            System.out.println();
//			        });
//			        System.out.println("------------------------------------------------------------------------------------------------------------------");

					System.out.println("exit split connected component");
				

			}
			else
			{
			
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
		}		
//		System.out.println("The amount of times we put value in a variable is : " + DS.counter);
		return true;
		//	DS.printValueOfVariables();
	}
	public boolean DP()
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * Create the application.
	 */
	public MinimalModel() {
		super(true);
		init();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		JLabel lblPath = new JLabel("Path: ");
		textField = new JTextField();
		textField.setColumns(10);

		textField.setText(".\\CnfFile.txt");



		JButton btnReadFile = new JButton("Read File");
		JLabel lblNewLabel_1 = new JLabel("");

		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Scanner sc;
				int var ;
				int index = 0;
				int numOfRules;


				try 
				{

					String Path = textField.getText();
					sc = new Scanner(new File(Path));//read file
					numOfRules = sc.nextInt();
					rulesNum=numOfRules;
					DS = new RulesDataStructure(numOfRules);
					lblNewLabel_1.setText("Please wait...");
					while (sc.hasNextLine()) 
					{
						var = sc.nextInt();
						if(var!=0)
							DS.addToRulsArray(index, var);
						else
							index++;
					}
					lblNewLabel_1.setText("File was read successfully");
					lblNewLabel_1.setForeground(Color.GREEN);
					readFile = true;
					//btnReadFile.setEnabled(false);
				}catch (FileNotFoundException ex)
				{
					// TODO Auto-generated catch block
					//ex.printStackTrace();
					lblNewLabel_1.setText("Error on reading the file");
					lblNewLabel_1.setForeground(Color.RED);


				}

			}
		});

		JLabel lblNewLabel = new JLabel("");
		JButton btnFindMinimalModel = new JButton("Find Minimal Model");
		JLabel lblNewLabel_2 = new JLabel("");

		btnFindMinimalModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(readFile)
				{
					// btnFindMinimalModel.setEnabled(false);
					lblNewLabel_2.setText("");
					/*	LinkedList l=DS.checkFormat();
						if(l.getSize()==0)
						{*/
					if(ModuMin(DS))
					{
						lblNewLabel.setText("SAT The minimal model is: " + DS.StringMinimalModel());
						System.out.println(DS.StringMinimalModel());
					}
					else
						lblNewLabel.setText(" UNSAT ");

						//}
					/*else
					{
						System.out.println("Please correct lines: ");
						l.printList();
						System.out.println("Its not in the right format");
						System.out.println("Can't be a clause where all litarals are negative");
					}*/
				}
				else
				{
					lblNewLabel_2.setText("Please read a file");
					lblNewLabel_2.setForeground(Color.RED);
					lblNewLabel_2.setHorizontalAlignment(JLabel.CENTER);
					lblNewLabel_2.setVerticalAlignment(JLabel.CENTER);
				}
			}
		});






		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(77)
										.addComponent(lblPath)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(172)
										.addComponent(btnReadFile)
										.addGap(18)
										.addComponent(lblNewLabel_1))
								.addGroup(groupLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(156)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(lblNewLabel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnFindMinimalModel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPath)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnReadFile)
								.addComponent(lblNewLabel_1))
						.addGap(41)
						.addComponent(btnFindMinimalModel)
						.addGap(18)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
						.addContainerGap())
				);
		frame.getContentPane().setLayout(groupLayout);
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
	
	public static boolean ModuMin(RulesDataStructure DS )
	{
		int size = DS.SIZE;			
		while(DS.SIZE!=0)
		{

//			System.out.println("Rules array SIZE  : " +DS.SIZE);
//			DS.printRulesArray();
//			//DS.checkForUnits();//remove empty sources
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
	}
	
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
