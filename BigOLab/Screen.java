import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Screen extends JPanel{

	private Database db;
	private Color white;
	private Color black;
	private JTextArea studentTextArea;
	private JTextField searchTextBox;

	public Screen() {

		this.setLayout(null);
		this.setFocusable(true);

		db = new Database();
		white = Color.white;
		black = Color.black;
		studentTextArea = new JTextArea(400, 600);

		studentTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(studentTextArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(400, 10, 400, 600);
		this.add(scrollPane);
		this.populateStudentTextArea();
		
		JButton showAllButton = new JButton("Show all");
		showAllButton.addActionListener(e -> {
			this.populateStudentTextArea();
		});
		showAllButton.setBounds(20, 280, 140, 50);
		this.add(showAllButton);

		JTextField searchTextBox = new JTextField();
		searchTextBox.setBounds(20, 80, 200, 30);
		this.add(searchTextBox);

		JButton binarySearchButton = new JButton("Binary search");
		binarySearchButton.setBounds(20, 130, 140, 30);
		binarySearchButton.addActionListener(e -> {
			int res = db.binarySearch(searchTextBox.getText());
			searchTextBox.setText("");

			if (res > -1) {
				studentTextArea.setText((res + 1) + ". " + db.getStudent(res).toString());
			} else {
				studentTextArea.setText("No students found. Try again.");
			}
			this.repaint();

		});
		this.add(binarySearchButton);

		JButton sequentialSearchButton = new JButton("Sequential search");
		sequentialSearchButton.setBounds(180, 130, 140, 30);
		sequentialSearchButton.addActionListener(e -> {
			int res = db.sequentialSearch(searchTextBox.getText());
			searchTextBox.setText("");
			if (res > -1) {
				studentTextArea.setText((res + 1) + ". " + db.getStudent(res).toString());
			} else {
				studentTextArea.setText("No students found. Try again.");
			}
			this.repaint();
		});
		this.add(sequentialSearchButton);


		JButton scrambleButton = new JButton("Scramble");
		scrambleButton.addActionListener(e -> {
			db.scramble();
			this.populateStudentTextArea();
			this.repaint();
		});
		scrambleButton.setBounds(20, 340, 140, 50);
		this.add(scrambleButton);

		JButton bubbleSortButton = new JButton("Bubble sort");
		bubbleSortButton.addActionListener(e -> {
			db.bubbleSort();
			this.populateStudentTextArea();
			this.repaint();
		});
		bubbleSortButton.setBounds(20, 480, 140, 50);
		this.add(bubbleSortButton);

		JButton mergeSortButton = new JButton("Merge sort");
		mergeSortButton.addActionListener(e -> {
			db.mergeSort();
			this.populateStudentTextArea();
			this.repaint();
		});
		mergeSortButton.setBounds(20, 530, 140, 50);
		this.add(mergeSortButton);
	
	}

	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(black);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		g.drawString("Search For Student By Last Name", 20, 50);
		g.drawString("Sorting", 20, 450);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		g.drawString("Passes: " + db.getPasses(), 20, 220);

	}

	public void populateStudentTextArea() {
		studentTextArea.setText(db.toString());
	}
}