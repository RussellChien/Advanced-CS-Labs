import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Screen extends JPanel implements ItemListener, ActionListener {
	
	private JPanel comboBoxPanel;
	private JComboBox comboBox;
	private JPanel all, teachers, police, bankers, engineers;
	private JButton submit;
	private JTextField search;
	private BufferedImage bImage;
	private Graphics bufferedG;
	private String currCard = "All";
	private String searchText;
	private boolean stateChanged = false, searched = false;
	
	public Screen() {
		this.setLayout(null);
		
		String[] itemNames = {"All", "Teachers", "Police", "Bankers", "Engineers"};
		comboBox = new JComboBox(itemNames);
		comboBox.addItemListener(this);
		comboBox.setBounds(650, 500, 100, 30);
		this.add(comboBox);

		search = new JTextField();
		search.setBounds(450, 500, 150, 30);
		this.add(search);

		submit = new JButton("Search");
		submit.setBounds(325, 500, 100, 30);
		submit.addActionListener(this);
		this.add(submit);
		
		bImage = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_ARGB);
		bufferedG = bImage.getGraphics();
	}

	public Dimension getPreferredSize() {
		return new Dimension(1000, 600);
	}
	
	public String getCurrCard() {
		return currCard;
	}
	
	public boolean getStateChanged() {
		return stateChanged;
	}

	public void setStateChanged(boolean b) {
		stateChanged = b;
	}
	
	public void clear() {
		for(int i=0; i<bImage.getWidth(); i++) {
			for(int j=0; j<bImage.getHeight(); j++) {
				bImage.setRGB(i, j, 0);
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
		
		String itemName = (String) e.getItem();
		currCard = itemName;
		stateChanged = true;
		
	}

	public boolean getSearched() {
		return searched;
	}

	public void setSearched() {
		searched = false;
	}

	public String getSearchText() {
		return searchText;
	}
	
	public Graphics getBufferedGraphics() { 
		return bufferedG; 
	}
		
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(bImage, 0, 0, this);
		
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == submit) {
			searched = true;
			searchText = search.getText();
		}

	}

}