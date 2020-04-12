package com.cs421g29;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class GUI {

	private JFrame frame;
	
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;

	private JLabel label;
	
	private JLabel displayedbooks=new JLabel (" ");
	
	private JLabel input1 = new JLabel ("Field1: ");
	private JTextField in1 = new JTextField();
	
	private JLabel input2 = new JLabel ("Field2: ");
	private JTextField in2 = new JTextField();
	private JLabel input3 = new JLabel ("Field3: ");
	private JTextField in3 = new JTextField();
	private JLabel input4 = new JLabel ("Field4: ");
	private JTextField in4 = new JTextField();
	private JLabel input5 = new JLabel ("Field5: ");
	private JTextField in5 = new JTextField();
	private JLabel dummy = new JLabel (" ");
	private JButton confirm = new JButton("CONFIRM");
	

	
	private JPanel taskPanel;
	private JPanel buttonPanel;
	private JPanel displayPanel;
	private JPanel resultPanel;
	
	private JLabel result = new JLabel (" ");
	private String[] args = new String[6];
	
	private class ButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {

			
			if (e.getSource()==button1) { //Insert New Orders
				
				
				
				/*
				//otd, date, rate, paymentmethod, status, shippingaddress		
				result.setText("");
				label.setText("Insert a new order:");
				input1.setText("Order date (yyyy-mm-dd):");
				input2.setText("Total price:");
				input3.setText("Payment Method:");
				input4.setText("Status:");
				input5.setText("Shipping Address:");
				
				int[] arr = {1, 2, 3, 4, 5};
				setPromptsVisible (arr);
				*/
				
				
				displayedbooks.setText(convertToMultiline(GUIHelper.option1()));
				
				
			}else if  (e.getSource()==button2) {
				displayedbooks.setText(convertToMultiline(GUIHelper.option2()));
				

				
			}else if  (e.getSource()==button4) {
				label.setText("4");
			}else if  (e.getSource()==button5) {
				label.setText("5");
				
			}else if (e.getSource()==confirm) {
				//if confirm
				
				args[1] = in1.getText();
				/*
				String output = Connect.connect(args);
				result.setText(convertToMultiline(output));
				result.setVisible(true);
				*/
				resetFields();
			}
		}
	
		
	}
	
	
	
	
	public GUI() {
		
		frame = new JFrame();
		//frame.setLayout (new GridLayout(0,1));
		
		
		taskPanel = new JPanel();
		displayPanel = new JPanel();
		buttonPanel = new JPanel();	
		resultPanel = new JPanel();	
		
		//add all panels
		frame.add(taskPanel, BorderLayout.PAGE_START);
		frame.add(displayPanel, BorderLayout.LINE_START);	
		frame.add(resultPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.PAGE_END);
		frame.setSize(800,800);
		
		
		//Task panel	
		label = new JLabel("Welcome!");
		taskPanel.add(label,BorderLayout.PAGE_START);	
		label.setFont(new Font("Serif", Font.BOLD, 24));
		
		
		// display panel
		displayPanel.setBorder(BorderFactory.createEmptyBorder (20,20,20,20));	
		displayPanel.add(displayedbooks);
		displayedbooks.setText(convertToMultiline(GUIHelper.display()));
		displayedbooks.setVisible(true);
		/*
		displayPanel.setLayout (new GridLayout(0,2));
		displayPanel.add(input1 );
		displayPanel.add(in1 );
		displayPanel.add(input2 );
		displayPanel.add(in2 );
		displayPanel.add(input3);
		displayPanel.add(in3 );
		displayPanel.add(input4 );
		displayPanel.add(in4 );
		displayPanel.add(input5 );
		displayPanel.add(in5 );
		displayPanel.add(dummy);
		displayPanel.add(confirm );
		
		input1.setVisible(false);
		in1.setVisible(false);
		input2.setVisible(false);
		in2.setVisible(false);
		input3.setVisible(false);
		in3.setVisible(false);
		input4.setVisible(false);
		in4.setVisible(false);
		input5.setVisible(false);
		in5.setVisible(false);
		dummy.setVisible(false);
		confirm.setVisible(false);
		*/
		
		
		//result panel
		resultPanel.add(result);
		result.setVisible(false);
		
		
		//button panel
		
		buttonPanel.setBorder(BorderFactory.createEmptyBorder (20,20,20,20)); //top,b,l,r
		buttonPanel.setLayout (new GridLayout(0,1));
		
		button1 = new JButton("VIEW PREVIOUS 10 BOOKS");	
		button2 = new JButton("VIEW NEXT 10 BOOKS");
		button3 = new JButton("option3"); //including their shippers
		button4 = new JButton("option4");
		button5 = new JButton("option5");
			
		button1.addActionListener(new ButtonListener());
		button2.addActionListener(new ButtonListener());
		button3.addActionListener(new ButtonListener());
		button4.addActionListener(new ButtonListener());
		button5.addActionListener(new ButtonListener());
		confirm.addActionListener(new ButtonListener());
		
		
		buttonPanel.add(button1);
		buttonPanel.add(button2);
		buttonPanel.add(button3);
		buttonPanel.add(button4);
		buttonPanel.add(button5);
		
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Online Book Vender Database");
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	private static String convertToMultiline(String orig)
	{
	    return "<html>" + orig.replaceAll("\n", "<br>");
	}
	
	
	private void resetFields() {
		in1.setText(null);
		in2.setText(null);
		in3.setText(null);
		in4.setText(null);
		in5.setText(null);
		
		args[1] = null;
		args[2] = null;
		args[3] = null;
		args[4] = null;
		args[5] = null;
		
	}
	
	
	private void setPromptsVisible (int[] arr) {
		
		input1.setVisible(false);
		in1.setVisible(false);
		input2.setVisible(false);
		in2.setVisible(false);
		input3.setVisible(false);
		in3.setVisible(false);
		input4.setVisible(false);
		in4.setVisible(false);
		input5.setVisible(false);
		in5.setVisible(false);
		
		for (int num: arr) {
			if (num==1) {
				input1.setVisible(true);
				in1.setVisible(true);
			}
			if (num==2) {
				input2.setVisible(true);
				in2.setVisible(true);
			}
			if (num==3) {
				input3.setVisible(true);
				in3.setVisible(true);
			}
			if (num==4) {
				input4.setVisible(true);
				in4.setVisible(true);
			}
			if (num==5) {
				input5.setVisible(true);
				in5.setVisible(true);
			}
		}
			
		dummy.setVisible(true);
		confirm.setVisible(true);
	}
	
	
	
	
	
	public static void main (String[] args) {
		new GUI();
	}



	

}
