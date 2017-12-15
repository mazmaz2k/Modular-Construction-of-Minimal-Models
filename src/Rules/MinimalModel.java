package Rules;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MinimalModel{

	private JFrame frame;
	private JTextField textField;
	RulesDataStructure DS ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
					//ModuMin(DS);
				}catch (FileNotFoundException ex)
				{
					// TODO Auto-generated catch block
					ex.printStackTrace();
					lblNewLabel_1.setText("Error on reading the file");

				
				}
				
			}
		});
		
		JLabel lblNewLabel = new JLabel("");
		JButton btnFindMinimalModel = new JButton("Find Minimal Model");
		btnFindMinimalModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				ModuMin(DS);
				lblNewLabel.setText("The minimal model is: " + DS.StringMinimalModel());
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
							.addGap(154)
							.addComponent(btnFindMinimalModel))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)))
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
					.addGap(42)
					.addComponent(btnFindMinimalModel)
					.addGap(31)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public static void ModuMin(RulesDataStructure DS )
	{
		LinkedList s = new LinkedList();
		s.addAtTail(1);
		s.addAtTail(2);
		s.addAtTail(3);
		s.addAtTail(4);
		s.addAtTail(5);
		
		LinkedList Ts=DS.Ts(s);
		Ts.printList();
		DS.printRulesArray();
		DS.FindMinimalModelForTs(Ts);
		DS.printValueOfVariables();
		DS.updateRuleDS();
		DS.printRulesArray();
		System.out.println("SIZE OF T: " +DS.SIZE);
	}
}
