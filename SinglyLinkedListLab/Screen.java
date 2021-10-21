import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Screen extends JPanel implements ActionListener, ItemListener{
	
	private SLList<Item> items;
	private JTextField nameField, priceField;
	private JTextArea listArea;
	private JButton addBtn, delBtn;
	private JLabel listTitleTxt, sortByTxt, name, price;
	private JComboBox<String> comboBox;
	private int timeAdded;
	private String sort;
	
	public Screen(){
		
		this.setLayout(null);

		items = new SLList<Item>();
		timeAdded = 0;
		sort = "Time Added";

		nameField = new JTextField();
		nameField.setBounds(620, 10, 155, 33);
		this.add(nameField);

		priceField = new JTextField();
		priceField.setBounds(620, 45, 155, 33);
		this.add(priceField);

		Color green = new Color(91, 216, 120);
		addBtn = new JButton("Add");
		addBtn.setBackground(green);
		addBtn.setFont(new Font("Times Roman", Font.PLAIN, 15));
		addBtn.setBorderPainted(false);
		addBtn.setOpaque(true);
		addBtn.setBounds(605, 90, 100, 30);
		addBtn.addActionListener(this);
		this.add(addBtn);
		
		Color red = new Color(216, 91, 120);
		delBtn = new JButton("Delete");
		delBtn.setBackground(red);
		delBtn.setFont(new Font("Times Roman", Font.PLAIN, 15));
		delBtn.setBorderPainted(false);
		delBtn.setOpaque(true);
		delBtn.setBounds(690, 90, 100, 30);
		delBtn.addActionListener(this);
		this.add(delBtn);

		name = new JLabel("Name:");
		name.setBounds(nameField.getX()-45, nameField.getY(), 50, 30);
		name.setFont(new Font("Times Roman", Font.PLAIN, 15));
		this.add(name);

		price = new JLabel("Price:");
		price.setBounds(priceField.getX()-40, priceField.getY(), 50, 30);
		price.setFont(new Font("Times Roman", Font.PLAIN, 15));
		this.add(price);

		listTitleTxt = new JLabel("Shopping List");
		listTitleTxt.setBounds(150, 10, 250, 40);
		listTitleTxt.setFont(new Font("Times Roman", Font.BOLD, 30));
		this.add(listTitleTxt);

		listArea = new JTextArea();
		listArea.setBounds(130, 53, 300, 600);
		this.add(listArea);
		
		sortByTxt = new JLabel("Sort By:");
		sortByTxt.setBounds(625, 142, 150, 40);
		sortByTxt.setFont(new Font("Times Roman", Font.BOLD, 14));
		this.add(sortByTxt);
		
		String[] itemNames = {"Time Added", "Name", "Price"};
		comboBox = new JComboBox<String>(itemNames);
		comboBox.setBounds(625, 160, 150, 50);
		comboBox.addItemListener(this);
		this.add(comboBox);
		
		items.add(new Item("Jacket", 30, 1, timeAdded++));
		items.add(new Item("Soda", 1.50, 1, timeAdded++));
		items.add(new Item("Computer", 1199.99, 1, timeAdded++));
		sortList();
	}

	public void displayList(){
	 	String txt = "";
	 	double price = 0;
	 	for(int i = 0; i < items.size(); i++){
	 		txt += items.get(i).getQuantity() + " " + items.get(i).getName() + " $" + items.get(i).getPrice() + "\n";
	 		price += items.get(i).getQuantity() * items.get(i).getPrice();
	 	}
	 	DecimalFormat df = new DecimalFormat("#.##");
	 	df.setRoundingMode(RoundingMode.CEILING);
	 	txt += "________________________\nTotal price: $" + df.format(price);
	 	listArea.setText(txt);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(800, 600);
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 600);	
	}
	
	public void sortList(){
		int i, j;
		Item temp;
		
		if(sort.equals("Time Added")){
			for(i = 0; i < items.size(); i++){
				for(j= i + 1; j < items.size(); j++){
					if(items.get(i).getTimeAdded() > items.get(j).getTimeAdded()){
						temp = items.get(i);
						items.set(i, items.get(j));
						items.set(j, temp);
					}
				}
			}
		} 
		else if(sort.equals("Name")){
			for(i = 0; i< items.size(); i++){
				for(j = i+1; j < items.size(); j++){
					if(items.get(i).getName().compareTo(items.get(j).getName().toLowerCase()) > 0){
						temp = items.get(i);
						items.set(i, items.get(j));
						items.set(j, temp);
					}
				}
			}
		} 
		else if(sort.equals("Price")){
			for(i = 0; i < items.size(); i++){
				for(j = i+1; j < items.size(); j++){
					if(items.get(i).getPrice() > items.get(j).getPrice()){
						temp = items.get(i);
						items.set(i, items.get(j));
						items.set(j, temp);
					}
				}
			}
		}
		displayList();
	}

	public void itemStateChanged(ItemEvent e){
		if(e.getSource() == comboBox) {
			sort = (String) e.getItem();
			sortList();
		}
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == addBtn && !nameField.getText().equals("") && !priceField.getText().equals("")){
			Item req = new Item(nameField.getText().toLowerCase(), Double.parseDouble(priceField.getText()), 1, 0);
			if(items.contains(req)) {
				items.get(items.indexOf(req)).setQuantity(items.get(items.indexOf(req)).getQuantity()+1);
			} 
			else {
				req.setTimedAdded(timeAdded++);
				items.add(req);
			}
			sortList();
		} 
		else if(e.getSource() == delBtn && !nameField.getText().equals("") && !priceField.getText().equals("")){
			items.remove(new Item(nameField.getText().toLowerCase(), Double.parseDouble(priceField.getText()), 1, 0));
			displayList();
		}
	}
	
}
