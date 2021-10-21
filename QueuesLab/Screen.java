import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class Screen extends JPanel implements ItemListener, ActionListener {

	private JPanel cardsPanel, comboPanel, nursePanel, doctorPanel;
	private JComboBox<String> comboBox, addPriority, addAge, editPriority, editAge;
	private JTextArea patientArea, treatment, patientRecord;
	private JScrollPane scrollPane;
	private JLabel addPatientTxt, editPatientTxt, searchResult, patientTxt;
	private JTextField addName, addIllness, searchName, editIllness;
	private JButton addPatient, editPatient, discharge, done;
	private int timeStamp = 0;
	private PriorityQueue<Patient> patients;
	private Set<Patient> record;
	private Font font;
	
	public Screen() {
		
		this.setLayout(new BorderLayout());

		patients = new PriorityQueue<Patient>();
		record = new TreeSet<Patient>();
		
		patients.add(new Patient("Joe", "Headache", "child", "medium", timeStamp++));
		patients.add(new Patient("Bob", "Leukemia", "adult", "high", timeStamp++));
		patients.add(new Patient("Ryan", "Stress", "adult", "medium", timeStamp++));
		patients.add(new Patient("Tai", "Cut", "child", "low", timeStamp++));
		patients.add(new Patient("Ben", "Broken Leg", "adult", "high", timeStamp++));

		font = new Font("Times Roman", Font.PLAIN, 15);

		String[] itemNames = {"Nurse", "Doctor"};
		comboBox = new JComboBox<String>(itemNames);
		comboBox.addItemListener(this);

		comboPanel = new JPanel();
		Color topColor = new Color(234, 241, 252);
		comboPanel.setBackground(topColor);
		comboPanel.add(comboBox);
		this.add(comboPanel, BorderLayout.NORTH);

		cardsPanel = new JPanel();
		cardsPanel.setLayout(new CardLayout());
		
		// Nurse view	
		nursePanel = new JPanel();
		nursePanel.setBackground(Color.white);
		nursePanel.setBounds(0, 0, 1000, 600);
		nursePanel.setLayout(null);
		cardsPanel.add(nursePanel, "Nurse");
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 240, 580);
		nursePanel.add(scrollPane);
		
		patientArea = new JTextArea();
		patientArea.setEditable(false);
		scrollPane.setViewportView(patientArea);
		
		addPatientTxt = new JLabel("Add Patient");
		addPatientTxt.setBounds(370, 50, 150, 50);
		nursePanel.add(addPatientTxt);
		displayPatients();
		
		addName = new JTextField("Name");
		addName.setBounds(320, addPatientTxt.getY()+50, 200, 40);
		nursePanel.add(addName);
		
		addIllness = new JTextField("Illness");
		addIllness.setBounds(addName.getX(), addName.getY()+50, 200, 40);
		nursePanel.add(addIllness);
		
		String[] priorites = { "low", "medium", "high"};
		addPriority = new JComboBox<String>(priorites);
		addPriority.setBounds(addName.getX(), addIllness.getY()+50, 200, 40);
		nursePanel.add(addPriority);

		String[] ages = {"child", "adult"};
		addAge = new JComboBox<String>(ages);
		addAge.setBounds(addPriority.getX(), addPriority.getY()+50, 200, 40);
		nursePanel.add(addAge);
		
		addPatient = new JButton("Add Patient");
		addPatient.setBounds(addName.getX()+100, addPriority.getY()+100, 100, 40);
		addPatient.addActionListener(this);
		nursePanel.add(addPatient);
		
		editPatientTxt = new JLabel("Search and Edit Patient");
		editPatientTxt.setBounds(625, 50, 200, 50);
		nursePanel.add(editPatientTxt);
		
		searchName = new JTextField("Name");
		searchName.setBounds(editPatientTxt.getX(), editPatientTxt.getY()+50, 200, 40);
		nursePanel.add(searchName);
		
		editIllness = new JTextField("New Illness");
		editIllness.setBounds(searchName.getX(), searchName.getY()+50, 200, 40);
		nursePanel.add(editIllness);
		
		editPriority = new JComboBox<String>(priorites);
		editPriority.setBounds(searchName.getX(), editIllness.getY()+50, 200, 40);
		nursePanel.add(editPriority);
		
		editPatient = new JButton("Edit Patient");
		editPatient.setBounds(searchName.getX()+100, editPriority.getY()+50, 100, 40);
		editPatient.addActionListener(this);
		nursePanel.add(editPatient);
		
		searchResult = new JLabel();
		searchResult.setBounds(700, editPatient.getY()+30, 150, 50);
		searchResult.setForeground(Color.black);
		nursePanel.add(searchResult);
		
		// Doctor view
		doctorPanel = new JPanel();
		doctorPanel.setBackground(Color.white);
		doctorPanel.setBounds(0, 0, 1000, 600);
		doctorPanel.setLayout(null);
		cardsPanel.add(doctorPanel, "Doctor");
		
		patientTxt = new JLabel();
		displayImportant();
		patientTxt.setBounds(300, 100, 400, 50);
		doctorPanel.add(patientTxt);
		
		treatment = new JTextArea("Note");
		treatment.setBounds(300, 150, 400, 200);
		treatment.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		doctorPanel.add(treatment);

		patientRecord = new JTextArea();
		patientRecord.setBounds(700, 0, 300, 800);
		patientRecord.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		doctorPanel.add(patientRecord);
		displayRecord();
		
		discharge = new JButton("Discharge");
		discharge.setBounds(treatment.getX()+200, 365, 120, 35);
		discharge.setBackground(new Color(117, 204, 40));
		discharge.addActionListener(this);
		discharge.setOpaque(true);
		discharge.setBorderPainted(false);
		doctorPanel.add(discharge);
	
		this.add(cardsPanel);	
	}
	
	public void displayRecord() {
		String text = "";
		if(!record.isEmpty()){
			for(Patient p : record){
				text += p.getName() + "\nDoctor's Note: " + p.getDoctorNote() + "\n";
			}	
			patientRecord.setText(text);
		} else {
			patientRecord.setText("None Discharged");
		}
	}

	public void displayImportant() {
		if(!patients.isEmpty()) {
			Patient p = patients.peek();
			patientTxt.setText(p.getName() + ", " + p.getAge() +  ". Priority: " + p.getPriority() + " | Illness: " + p.getIllness());
		} else {
			patientTxt.setText("No Patients");
		}
	}
	
	public void displayPatients() {
		String text = "";
		Patient p;
		PriorityQueue<Patient> replace = new PriorityQueue<Patient>();
		while(!patients.isEmpty()) {
			p = patients.poll();
			replace.add(p);
			text += p + "\n";
		}
		patients = replace;
		patientArea.setText(text);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addPatient) {
			patients.add(new Patient(addName.getText(), addIllness.getText(), (String) addAge.getSelectedItem(), (String) addPriority.getSelectedItem(), timeStamp++));
			addName.setText("Name");
			addIllness.setText("Illness");
			displayPatients();
			displayImportant();
		} else if(e.getSource() == editPatient) {
			Patient p = new Patient(searchName.getText(), editIllness.getText(), (String) addAge.getSelectedItem(), (String) editPriority.getSelectedItem(), 1);
			boolean found = false;
			Iterator<Patient> it = patients.iterator();
			while(it.hasNext()) {
				Patient temp = it.next();
				if(temp.equals(p)) {
					patients.remove(p);
					p.setTimeStamp(temp.getTimeStamp());
					patients.add(p);
					searchResult.setText("Succesfully edited");
					found = true;
					displayPatients();
					displayImportant();
					searchName.setText("Name");
					editIllness.setText("Illness");
					break;
				}
			}
			if(!found) {
				searchResult.setText("  Patient not found");
				searchResult.setForeground(Color.red);
			}
		} else if(e.getSource() == discharge && !patients.isEmpty()) {
			Patient p = patients.poll();
			record.add(p);
			p.setDoctorNote(treatment.getText());
			treatment.setText("Note");
			displayImportant();
			displayPatients();
			displayRecord();
		} 
	}
	
	public void itemStateChanged(ItemEvent e){
		String itemName = (String) e.getItem();
		CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();
		cardLayout.show(cardsPanel, itemName);
	}

	
	public Dimension getPreferredSize() {
		return new Dimension(1000, 600);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}

}
