import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Screen extends JPanel implements ActionListener, ListSelectionListener{

	private HashTable<Car> cars;
	private JButton dealerView, consumerView, update, add, remove; //buy for consumer
	private JTextField makeField, nameField, yearField, priceField;
	private JTextArea text;
	private JList<String> makes;
	private DLList<JButton> updateButtons, removeButtons;
	private DLList<JTextField> updateFields;
	private DLList<String> carMakes;
	private String[] makesList;
	private DLList<Car> currentList;

	public Screen(){
		this.setLayout(null);

		cars = new HashTable<Car>();

		cars.add(new Car("Honda", "Civic", 1999, 999.99));
		cars.add(new Car("Honda", "Accord", 2015, 4999.99));
		cars.add(new Car("Honda", "Oddessy", 2017, 15000.99));
		cars.add(new Car("Toyota", "Camry", 2019, 10000.99));
		cars.add(new Car("Mercedes", "Benz", 2020, 20000.99));
		cars.add(new Car("Nissan", "Leaf", 2012, 1200.99));
		cars.add(new Car("Acura", "MDX", 2011, 1700.50));
		cars.add(new Car("Toyota", "Sierra", 2009, 1800.50));
		cars.add(new Car("Audi", "A6", 2020, 50000.00));
		cars.add(new Car("Audi", "A7", 2020, 70000.00));

		dealerView = new JButton("Dealer");
		dealerView.setBounds(550, 450, 200, 40);
		dealerView.addActionListener(this);
		this.add(dealerView);

		consumerView = new JButton("Consumer");
		consumerView.setBounds(550, 500, 200, 40);
		consumerView.addActionListener(this);
		this.add(consumerView);

		add = new JButton("Add");
		add.setBounds(200, 400, 100, 40);
		add.addActionListener(this);
		this.add(add);

		makeField = new JTextField("Make");
		makeField.setBounds(300, 360, 100, 40);
		this.add(makeField);

		nameField = new JTextField("Model");
		nameField.setBounds(300, 400, 100, 40);
		this.add(nameField);

		yearField = new JTextField("Year");
		yearField.setBounds(300, 440, 100, 40);
		this.add(yearField);

		priceField = new JTextField("Price");
		priceField.setBounds(300, 480, 100, 40);
		this.add(priceField);

		updateButtons = new DLList<JButton>();
		removeButtons = new DLList<JButton>();
		updateFields = new DLList<JTextField>();

		carMakes = new DLList<String>();
		carMakes.add("Honda");
		carMakes.add("Toyota");
		carMakes.add("Mercedes");
		carMakes.add("Audi");
		carMakes.add("Nissan");
		carMakes.add("Acura");
		carMakes.add("View All");

		makesList = new String[carMakes.size()];
		for(int i = 0; i < makesList.length; i++){
			makesList[i] = carMakes.get(i);
		}
		makes = new JList<String>(makesList);
		makes.setBounds(10, 10, 100, 40);
		makes.addListSelectionListener(this);
		this.add(makes);

        JScrollPane scrollPane = new JScrollPane(makes); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10,10,200,250);
        this.add(scrollPane);

		text = new JTextArea();
		text.setBounds(250, 50, 300, 200);
		text.setEditable(false);
		this.add(text);

		currentList = new DLList<Car>();
 
		if(makes.getSelectedValue() == null || makes.getSelectedValue().equals("View All")){
        	for(int i = 0; i < cars.size(); i++){
				for(int j = 0; j < cars.get(i).size(); j++){
						currentList.add(cars.get(i).get(j));
				}
			}
       	}
        else{
        	for(int i = 0; i < cars.size(); i++){
				for(int j = 0; j < cars.get(i).size(); j++){
					if(makes.getSelectedValue().equals(cars.get(i).get(j).getMake())){
						currentList.add(cars.get(i).get(j));
					}
				}
			}	
       	}
       	updateText();
       	for(int i = 0; i < currentList.size(); i++){
			updateButtons.add(new JButton("Update"));
			updateButtons.get(i).setBounds(750, 50 + (i*16), 100, 16);
			updateButtons.get(i).addActionListener(this);
			this.add(updateButtons.get(i));

			removeButtons.add(new JButton("Remove"));
			removeButtons.get(i).setBounds(550, 50 + (i*16), 100, 16);
			removeButtons.get(i).addActionListener(this);
			this.add(removeButtons.get(i));

			updateFields.add(new JTextField());
			updateFields.get(i).setBounds(650, 50 + (i*16), 100, 16);
			this.add(updateFields.get(i));

		}

		this.setFocusable(true);
	}

	public Dimension getPreferredSize(){
		return new Dimension(1000, 600);
	}

	public void updateButtons(){
		updateButtons = new DLList<JButton>();
		removeButtons = new DLList<JButton>();
		updateFields = new DLList<JTextField>();
		for(int i = 0; i < currentList.size(); i++){
			updateButtons.add(new JButton("Update"));
			updateButtons.get(i).setBounds(750, 50 + (i*16), 100, 16);
			updateButtons.get(i).addActionListener(this);
			this.add(updateButtons.get(i));

			removeButtons.add(new JButton("Remove"));
			removeButtons.get(i).setBounds(550, 50 + (i*16), 100, 16);
			removeButtons.get(i).addActionListener(this);
			this.add(removeButtons.get(i));

			updateFields.add(new JTextField());
			updateFields.get(i).setBounds(650, 50 + (i*16), 100, 16);
			this.add(updateFields.get(i));

		}
	}

	public void updateText(){
		String carList = "";
		for(int i = 0; i < currentList.size(); i++){
			if(currentList.get(i) != null){
				carList += currentList.get(i) + "\n";
			}
			else{
				carList += "\n";
			}
		}
		text.setText(carList);
	}

	public void paintComponent(Graphics g){
		repaint();

	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == consumerView){
			for(int i = 0; i < removeButtons.size(); i++){
				if(removeButtons.get(i).isVisible()){
					updateButtons.get(i).setVisible(false);
					updateFields.get(i).setVisible(false);
					removeButtons.get(i).setText("Buy");
				}

				add.setVisible(false);
				makeField.setVisible(false);
				nameField.setVisible(false);
				yearField.setVisible(false);
				priceField.setVisible(false);
			}
		}
		else if(e.getSource() == dealerView){
			for(int i = 0; i < removeButtons.size(); i++){
				if(removeButtons.get(i).isVisible()){
					updateButtons.get(i).setVisible(false);
					updateFields.get(i).setVisible(true);
					removeButtons.get(i).setText("Remove");
				}

				add.setVisible(true);
				makeField.setVisible(true);
				nameField.setVisible(true);
				yearField.setVisible(true);
				priceField.setVisible(true);
			}
		}
		for(int i = 0; i < updateButtons.size(); i++){
			if(e.getSource() == updateButtons.get(i)){
				Double d = Double.parseDouble(updateFields.get(i).getText());
				//System.out.println("update with " + d);
				currentList.get(i).setPrice(d);
				//cars.add(currentList.get(i));
				updateText();
			}
		}
		for(int i = 0; i < removeButtons.size(); i++){
			if(e.getSource() == removeButtons.get(i)){
				//System.out.println("remove " + i);
				//System.out.println(currentList.get(i));
				removeButtons.get(i).setVisible(false);
				updateButtons.get(i).setVisible(false);
				updateFields.get(i).setVisible(false);
				cars.remove(currentList.get(i));
				currentList.set(i, null);
				updateText();
			}
		}
		if(e.getSource() == add){
			carMakes.add(carMakes.size()-1, makeField.getText());
			makesList = new String[carMakes.size()];
			for(int i = 0; i < makesList.length; i++){
				makesList[i] = carMakes.get(i);
			}
			makes.setListData(makesList);
			cars.add(new Car(makeField.getText(), nameField.getText(), Integer.parseInt(yearField.getText()), Double.parseDouble(priceField.getText())));
			updateText();
		}
		repaint();
	}

	 public void valueChanged(ListSelectionEvent e){ 
        String make = makes.getSelectedValue();
        String carList = "";
        currentList = new DLList<Car>();
        if(makes.getSelectedValue() == null || makes.getSelectedValue().equals("View All")){
        	for(int i = 0; i < cars.size(); i++){
				for(int j = 0; j < cars.get(i).size(); j++){
					currentList.add(cars.get(i).get(j));
				}
			}
       	}
        else{
        	for(int i = 0; i < cars.size(); i++){
				for(int j = 0; j < cars.get(i).size(); j++){
					if(makes.getSelectedValue().equals(cars.get(i).get(j).getMake())){
						currentList.add(cars.get(i).get(j));
					}
				}
			}	
       	}
        updateText();
        //updateButtons();
        repaint();
    } 
}
