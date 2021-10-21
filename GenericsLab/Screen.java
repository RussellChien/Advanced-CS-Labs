import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Screen extends JPanel implements ActionListener{
	
	private ArrayList<Pair<Student, Schedule>> students = new ArrayList<Pair<Student, Schedule>>();
	private int[] periods = {1, 2, 3};
	private String[] classes1 = {"AP Physics 1", "APUSH", "HAMLIT"};
	private String[] classes2 = {"AP Calculus BC", "AP Chinese", "AP Computer Science"};
	private String[] classes3 = {"AP Stats", "AP Physics C", "Chemistry Honors"};
	private JButton searchButton, backButton, addClassButton, addStudent;
	private JTextField search, period, course, name;
	private JLabel searchPeriodTxt;
	private String activeStudent = "All";
	private boolean isStudent = false;
	
	
	public Screen(){
		students.add(new Pair<Student, Schedule>(new Student("Bob", "bob.png"), new Schedule(periods, classes1)));
		students.add(new Pair<Student, Schedule>(new Student("Jim", "jim.jpg"), new Schedule(periods, classes2)));
		students.add(new Pair<Student, Schedule>(new Student("John", "john.jpg"), new Schedule(periods, classes3)));
	
		this.setLayout(null);

		period = new JTextField();
		period.setBounds(350, 380, 30, 30);
		period.setVisible(false);
		this.add(period);

		course = new JTextField();
		course.setBounds(period.getX()+40, period.getY(), 150, 30);
		course.setVisible(false);
		this.add(course);

		addClassButton = new JButton("Add Class");
		addClassButton.setBounds(course.getX()+160, period.getY(), 170, 30);
		addClassButton.addActionListener(this);

		searchPeriodTxt = new JLabel("Period Class");
		searchPeriodTxt.setBounds(period.getX(), period.getY()-25, 200, 30);

		search = new JTextField();
		search.setBounds(300, 500, 150, 30);
		this.add(search);
	
		searchButton = new JButton("Search");
		searchButton.setBounds(450, 500, 100, 30);
		searchButton.addActionListener(this);
		this.add(searchButton);

		name = new JTextField();
		name.setBounds(300, 32, 150, 30);
		this.add(name);

		addStudent = new JButton("Add Student");
		addStudent.setBounds(450, 32, 120, 30);
		addStudent.addActionListener(this);
		this.add(addStudent);
		
		backButton = new JButton("Back");
		backButton.setBounds(5, 5, 80, 30);
		backButton.addActionListener(this);
		
		this.setFocusable(true);
		
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(1000, 600);
    }
	
	public void addStudent(Pair<Student, Schedule> student) {
		students.add(student);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color newColor = new Color(168, 15, 59);
		g.setColor(newColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		for(int i=0; i<students.size(); i++) {
			if(students.get(i).getKey().toString().equals(search.getText())) 
				isStudent = true;
		}
		
		if(activeStudent.equals("All")) {
			for(int i=0; i<students.size(); i++) {
				students.get(i).getKey().drawStudent(g, i*200, 200);
			}
		}
		else if(isStudent){
			this.add(backButton);
			this.add(addClassButton);
			this.add(searchPeriodTxt);
			period.setVisible(true);
			course.setVisible(true);
			this.remove(search);
			this.remove(searchButton);
			this.remove(addStudent);
			this.remove(name);
			for(int i=0; i<students.size(); i++) {
				if(students.get(i).getKey().toString().equals(activeStudent)){
					students.get(i).getKey().drawStudent(g, this.getWidth()/4, 150);
					students.get(i).getValue().drawSchedule(g, this.getWidth()/2, 150);
				}
			}
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == searchButton) {
			activeStudent = search.getText();
			search.setText("");
			repaint();
		} else if(e.getSource() == addStudent) {
			students.add(new Pair<Student, Schedule>(new Student(name.getText(), "profile.jpg"), new Schedule()));
			name.setText("");
			repaint();
		} else if(e.getSource() == backButton) {
			activeStudent = "All";
			this.remove(backButton);
			this.remove(addClassButton);
			course.setVisible(false);
			period.setVisible(false);
			this.remove(searchPeriodTxt);
			this.add(addStudent);
			this.add(name);
			this.add(search);
			this.add(searchButton);
			repaint();
		} else if(e.getSource() == addClassButton) {
			for(int i=0; i<students.size(); i++) {
				if(students.get(i).getKey().toString().equals(activeStudent)){
					students.get(i).getValue().addClass(Integer.parseInt(period.getText()), course.getText());
					period.setText("");
					course.setText("");
				}
			}
			repaint();
		}

	}
	
}