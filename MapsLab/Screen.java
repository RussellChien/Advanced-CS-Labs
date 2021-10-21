import java.util.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Screen extends JPanel{

	private Database db = new Database("Names.txt");
	private JTextArea profileTextArea = new JTextArea(10, 350);
	private JButton searchSchool, showAll, adminView, changeSchoolName, addProfile, removeProfile;
	private JTextField lastName, firstName, birthYear, adminLastName, adminFirstName, adminBirthYear, adminSchool; 
	private boolean admin = false;
	private String lname, fname, school;
	private int by;
	public Screen(){
		this.setLayout(null);
		this.setFocusable(true);

		this.profileTextArea.setEditable(false);
		JScrollPane profileScrollPane = new JScrollPane(profileTextArea);
		profileScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		profileScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		profileScrollPane.setBounds(10, 50, 390, 350);
		this.add(profileScrollPane);
		this.populateProfiles();

		showAll = new JButton("Show All");
		showAll.setBounds(125, 20, 100, 30);
		showAll.addActionListener(e -> {
			this.populateProfiles();
		});
		this.add(showAll);

		adminView = new JButton("Toggle Admin View");
		adminView.setBounds(450, 100, 200, 30);
		adminView.addActionListener(e -> {
			admin = !admin;
			if(admin){
				changeSchoolName.setVisible(true);
				addProfile.setVisible(true);
				removeProfile.setVisible(true);
				adminLastName.setVisible(true);
				adminFirstName.setVisible(true);
				adminBirthYear.setVisible(true);
				adminSchool.setVisible(true);

			}
			else{
				changeSchoolName.setVisible(false);
				addProfile.setVisible(false);
				removeProfile.setVisible(false);
				adminLastName.setVisible(false);
				adminFirstName.setVisible(false);
				adminBirthYear.setVisible(false);
				adminSchool.setVisible(false);

			}
		});
		this.add(adminView);

		adminLastName = new JTextField();
		adminLastName.setBounds(525, 200, 200, 30);
		this.add(adminLastName);
		adminLastName.setVisible(false);

		adminFirstName = new JTextField();
		adminFirstName.setBounds(525, 235, 200, 30);
		this.add(adminFirstName);
		adminFirstName.setVisible(false);

		adminBirthYear = new JTextField();
		adminBirthYear.setBounds(525, 270, 100, 30);
		this.add(adminBirthYear);
		adminBirthYear.setVisible(false);

		adminSchool = new JTextField();
		adminSchool.setBounds(525, 300, 200, 30);
		this.add(adminSchool);
		adminSchool.setVisible(false);

		changeSchoolName = new JButton("Change School Name");
		changeSchoolName.setBounds(500, 400, 200, 30);
		changeSchoolName.addActionListener(e -> {
			lname = adminLastName.getText();
			fname = adminFirstName.getText();
			by = Integer.parseInt(adminBirthYear.getText());
			school = adminSchool.getText();
			db.changeSchool(lname, fname, by, school);
			adminLastName.setText("");
			adminFirstName.setText("");
			adminBirthYear.setText("");
			adminSchool.setText("");
		});
		this.add(changeSchoolName);
		changeSchoolName.setVisible(false);

		addProfile = new JButton("Add Profile");
		addProfile.setBounds(500, 450, 200, 30);
		addProfile.addActionListener(e -> {
			lname = adminLastName.getText();
			fname = adminFirstName.getText();
			by = Integer.parseInt(adminBirthYear.getText());
			school = adminSchool.getText();
			db.addProfile(lname, fname, by, school);
			this.populateProfiles();
			adminLastName.setText("");
			adminFirstName.setText("");
			adminBirthYear.setText("");
			adminSchool.setText("");
		});
		this.add(addProfile);
		addProfile.setVisible(false);

		removeProfile = new JButton("Remove Profile");
		removeProfile.setBounds(500, 500, 200, 30);
		removeProfile.addActionListener(e -> {
			lname = adminLastName.getText();
			fname = adminFirstName.getText();
			by = Integer.parseInt(adminBirthYear.getText());
			db.removeProfile(lname, fname, by);
			this.populateProfiles();
			adminLastName.setText("");
			adminFirstName.setText("");
			adminBirthYear.setText("");
		});
		this.add(removeProfile);
		removeProfile.setVisible(false);

		lastName = new JTextField();
		lastName.setBounds(150, 400, 200, 30);
		this.add(lastName);

		firstName = new JTextField();
		firstName.setBounds(150, 435, 200, 30);
		this.add(firstName);

		birthYear = new JTextField();
		birthYear.setBounds(150, 470, 100, 30);
		this.add(birthYear);

		searchSchool = new JButton("Search School");
		searchSchool.setBounds(150, 500, 150, 30);
		searchSchool.addActionListener(e -> {
			lname = lastName.getText();
			fname = firstName.getText();
			by = Integer.parseInt(birthYear.getText());
			Profile p = new Profile(lname, fname, by);
			this.profileTextArea.setText(p.toString() + "\n" + db.getSchool(p));
			this.lastName.setText("");
			this.firstName.setText("");
			this.birthYear.setText(""); 

		});
		this.add(searchSchool); 
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.setFont(new Font("Times Roman", Font.PLAIN, 18));
		g.drawString("Profiles", 10, 40);
		g.setFont(new Font("Times Roman", Font.PLAIN, 14));
		g.drawString("Last Name: ", 75, 420);
		g.drawString("First Name:", 75, 455);
		g.drawString("Birth Year:", 75, 490);
		if(admin){
			g.drawString("Last Name: ", 450, 220);
			g.drawString("First Name: ", 450, 255);
			g.drawString("Birth Year: ", 450, 290);
			g.drawString("School: ", 450, 320);
		}
		repaint();


	}

	public void populateProfiles() {
		this.profileTextArea.setText(db.getProfiles());
	}


	
}