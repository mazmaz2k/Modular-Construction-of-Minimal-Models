package Rules;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MinimalModel window = new MinimalModel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MinimalModel() {
		super(true);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
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
					ModuMin(DS);
					lblNewLabel.setText("The minimal model is: " + DS.StringMinimalModel());
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
	
	public static void ModuMin(RulesDataStructure DS )
	{
			int size = DS.SIZE;
			
			while(DS.SIZE!=0)
			{
				
				System.out.println("SIZE OF T: " +DS.SIZE);
				//System.out.println("SIZE OF T: " +DS.SIZE);
				DS.printRulesArray();
				DS.checkForUnits();//remove empty sources
				Graph<Integer> g = initGraph(DS, size);
				//delete empty sources
				LinkedList s = sourceOfGraph(g);
				//need to check source size
				//System.out.println("s list is: ");
				//s.printList();
				LinkedList Ts=DS.Ts(s);
				//System.out.println("Ts list is: ");
				//Ts.printList();
				DS.FindMinimalModelForTs(Ts);
				//DS.printValueOfVariables();
				DS.updateRuleDS();
				//DS.printRulesArray();
				//
			}
			
		
	}
}
