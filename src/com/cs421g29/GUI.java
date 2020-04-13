package com.cs421g29;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class GUI {

	private JFrame frame;
	
	private JButton prev = new JButton("PREVIOUS 10 BOOKS");	
	private JButton next  = new JButton("NEXT 10 BOOKS");
	
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	

	private JLabel label;
	
	private JLabel displayedbooks=new JLabel (" ");
	
	
	
	
	

	
	private JPanel title;
	private JPanel buttonPanel;
	private JPanel middlePanel;
	
	
	private JPanel displayPanel;
	private JPanel resultPanel;

	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	
	
	
	
	private JLabel result = new JLabel ("                                                                                          ");
	
	private JLabel input1 = new JLabel ("Field1: ");
	private JTextField in1 = new JTextField();
	
	private JLabel input2 = new JLabel ("Field2: ");
	private JTextField in2 = new JTextField();
	private JLabel input3 = new JLabel ("Field3: ");
	private JTextField in3 = new JTextField();
	
	private JLabel dummy = new JLabel (" ");
	private JButton confirm = new JButton("CONFIRM");
	
	
	
	
	private String[] args = new String[6];
	
	private class ButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {

			
			if (e.getSource()==prev) { 
				
				displayedbooks.setText(convertToMultiline(GUIHelper.next()));
				
				
			}else if  (e.getSource()==next) {
				displayedbooks.setText(convertToMultiline(GUIHelper.prev()));
				

				
			}else if  (e.getSource()==button1) {
				//Ali.Zemlak94@yahoo.ca, Sasha15@gmail.com, Marianne.Sipes@hotmail.com)
				
				input1.setText("User Email of Shopping Cart (eg. Ali.Zemlak94@yahoo.ca): ");
				args[0]="1";
				int[] arr = {1};
				setPromptsVisible (arr);

			}else if  (e.getSource()==button2) {
				input1.setText("Book ID: ");
				args[0]="2";
				int[] arr = {1};
				setPromptsVisible (arr);
				
			}else if  (e.getSource()==button3) {
				input1.setText("Book ID: ");
				input2.setText("Number Of Copied Of This Book: ");
				
				args[0]="3";
				int[] arr = {1,2};
				setPromptsVisible (arr);
			}else if  (e.getSource()==button4) {
				input1.setText("User Email (eg. Ali.Zemlak94@yahoo.ca): ");
				input2.setText("Book ID: ");
				input3.setText("Score (1 to 5 stars): ");
					
				args[0]="4";
				int[] arr = {1,2,3};
				setPromptsVisible (arr);
						
		
			}else if (e.getSource()==confirm) {
				//if confirm
				if (args[0].equals("1")) {
					args[1] = in1.getText();
					result.setText(convertToMultiline(GUIHelper.option1(args[1])));
				}
				if (args[0].equals("2")) {
					args[1] = in1.getText();
					result.setText(convertToMultiline(GUIHelper.option2(args[1])));
				}
				if (args[0].equals("3")) {
					args[1] = in1.getText(); //bid
					args[2] = in2.getText(); //copies
					result.setText(convertToMultiline(GUIHelper.option3(args[1], args[2])));
				}
				if (args[0].equals("4")) {
					args[1] = in1.getText(); //email
					args[2] = in2.getText(); //bid
					args[3] = in3.getText(); //rating
					
					result.setText(convertToMultiline(GUIHelper.option4(args[1], args[2], args[2])));
				}
				
				resetFields();
			}
		}
	
		
	}
	
	
	
	
	public GUI() {
		
		frame = new JFrame();
		
		
		
		title = new JPanel();
		middlePanel = new JPanel();	
		
		buttonPanel = new JPanel();
		
		displayPanel = new JPanel();
		resultPanel = new JPanel();
		
		
		//add all panels
		frame.add(title, BorderLayout.PAGE_START);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.PAGE_END);
		frame.setSize(800,800);
		
		middlePanel.setLayout (new GridLayout(0,1));
		middlePanel.add(displayPanel);	
		middlePanel.add(resultPanel);	
		
		

		//title
		label = new JLabel("Welcome To Book Store Database");
		label.setHorizontalAlignment(JLabel.CENTER);
		title.setLayout (new GridLayout(0,1));
		title.add(label,BorderLayout.PAGE_START);	
		label.setFont(new Font("Serif", Font.BOLD, 30));
		
		JLabel m = new JLabel("MENU: Look Up Books");
		m.setHorizontalAlignment(JLabel.CENTER);
		m.setFont(new Font("Serif", Font.BOLD, 20));
		
		JLabel empty = new JLabel(" ");
		title.add(empty);
		title.add(m);
		
		title.add(prev);
		title.add(next);
		prev.addActionListener(new ButtonListener());
		next.addActionListener(new ButtonListener());
		
		
		// display panel
		displayPanel.setBorder(BorderFactory.createEmptyBorder (20,20,20,20));	
		displayPanel.add(displayedbooks);
	
		displayedbooks.setText(convertToMultiline(GUIHelper.display()));
		displayedbooks.setVisible(true);
		
		
		
		//result panel
	
		resultPanel.add(leftPanel, BorderLayout.NORTH);
		resultPanel.add(rightPanel, BorderLayout.SOUTH);

		resultPanel.add(result);
		
		leftPanel.setLayout(new GridLayout(0,2));
		leftPanel.add(input1);
		leftPanel.add(in1);
		leftPanel.add(input2);
		leftPanel.add(in2);
		leftPanel.add(input3);
		leftPanel.add(in3);
		
		leftPanel.add(dummy);
		leftPanel.add(confirm);
		input1.setVisible(false);
		in1.setVisible(false);
		input2.setVisible(false);
		in2.setVisible(false);
		input3.setVisible(false);
		in3.setVisible(false);
		
		dummy.setVisible(false);
		confirm.setVisible(false);
		
		
		
		
		
		//result.setVisible(false);
		
		
		//button panel
		
		buttonPanel.setBorder(BorderFactory.createEmptyBorder (20,20,20,20)); //top,b,l,r
		buttonPanel.setLayout (new GridLayout(0,1));
		

		button1 = new JButton("VIEW USER'S SHOPPING CART"); 
		button2 = new JButton("VIEW BOOK'S AVERAGE RATING"); 
		button3 = new JButton("UPDATE BOOK'S STOCK"); //including their shippers
		button4 = new JButton("ADD RATING");
		
			
		button1.addActionListener(new ButtonListener());
		button2.addActionListener(new ButtonListener());
		button3.addActionListener(new ButtonListener());
		button4.addActionListener(new ButtonListener());
		
		confirm.addActionListener(new ButtonListener());
		
		
		
		JLabel l = new JLabel("MENU: Update Database");
		buttonPanel.add(l);
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setFont(new Font("Serif", Font.BOLD, 20));
		
	
		
		buttonPanel.add(button1);
		buttonPanel.add(button2);
		buttonPanel.add(button3);
		buttonPanel.add(button4);
		
		
		
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Online Book Vender Database");
		//frame.pack();
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
	
		
		args[1] = null;
		args[2] = null;
		args[3] = null;
		args[4] = null;
		args[5] = null;
		
	}
	
	
	private void setPromptsVisible (int[] arr) {
		result.setText(" ");
		input1.setVisible(false);
		in1.setVisible(false);
		input2.setVisible(false);
		in2.setVisible(false);
		input3.setVisible(false);
		in3.setVisible(false);
		
		
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
			
		}
			
		dummy.setVisible(true);
		confirm.setVisible(true);
	}
	
	
	
	
	
	public static void main (String[] args) {
		new GUI();
	}



	

}
