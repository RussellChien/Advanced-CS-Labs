import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class Screen extends JPanel implements ActionListener {

	private String name, address, email, objective;
	private JButton done, add;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private JTextField nameField, addressField, emailField, objectiveField, schoolField, gradField;
	private JTextField jobField, companyField, startTimeField, endTimeField;
	private JTextArea workFuncArea;
	private JLabel titleTxt, nameTxt, addressTxt, emailTxt, objectiveTxt, schoolTxt, gradTxt;
	private JLabel jobTxt, companyTxt, startTimeTxt, endTimeTxt, workFuncTxt;
	private JScrollPane scrollPane;
	private boolean onEducation = true, resumePage = false;
	private Education ed = new Education();
	private Job job = new Job();
	private ListIterator<String> schools, jobs;
	
	public Screen() {
		
		this.setLayout(null);
		
		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.add(scrollPane);
		
		titleTxt = new JLabel("Resume Builder");
		titleTxt.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		titleTxt.setBounds(15, -15, 400, 100);
		this.add(titleTxt);
		
		//Name Field
		nameTxt = new JLabel("Name:");
		nameTxt.setBounds(30, 55, 100, 30);
		this.add(nameTxt);
		
		nameField = new JTextField();
		nameField.setBounds(30, 75, 100, 30);
		this.add(nameField);
		
		//Email Field
		emailTxt = new JLabel("Email:");
		emailTxt.setBounds(nameField.getX(), nameField.getY()+30, 100, 30);
		this.add(emailTxt);
		
		emailField = new JTextField();
		emailField.setBounds(nameField.getX(), nameField.getY()+50, 150, 30);
		this.add(emailField);
		
		//Address Field
		addressTxt = new JLabel("Address:");
		addressTxt.setBounds(nameField.getX(), emailField.getY()+30, 100, 30);
		this.add(addressTxt);
		
		addressField = new JTextField();
		addressField.setBounds(nameField.getX(), emailField.getY()+50, 150, 30);
		this.add(addressField);
		
		//Objective Field
		objectiveTxt = new JLabel("Objective:");
		objectiveTxt.setBounds(nameField.getX(), addressField.getY()+30, 100, 30);
		this.add(objectiveTxt);
		
		objectiveField = new JTextField();
		objectiveField.setBounds(nameField.getX(), addressField.getY()+50, 150, 30);
		this.add(objectiveField);
		
		//Education Field
		
		schoolTxt = new JLabel("School:");
		schoolTxt.setBounds(nameField.getX(), objectiveField.getY()+30, 100, 30);
		this.add(schoolTxt);
		
		schoolField = new JTextField();
		schoolField.setBounds(nameField.getX(), objectiveField.getY()+50, 100, 30);
		this.add(schoolField);
		
		gradTxt = new JLabel("Graduation Year:");
		gradTxt.setBounds(nameField.getX()+80, objectiveField.getY()+30, 200, 30);
		this.add(gradTxt);
		
		gradField = new JTextField();
		gradField.setBounds(nameField.getX()+103, objectiveField.getY()+50, 60, 30);
		this.add(gradField);
		
		add = new JButton("Add");
		add.addActionListener(this);
		add.setBounds(nameField.getX()+180, objectiveField.getY()+55, 50, 20);
		this.add(add);
		
		done = new JButton("Done");
		done.addActionListener(this);
		done.setBounds(nameField.getX()+235, objectiveField.getY()+55, 50, 20);
		this.add(done);
		
		//Job Field
		
		jobTxt = new JLabel("Job:");
		jobTxt.setVisible(false);
		jobTxt.setBounds(nameField.getX(), schoolField.getY()+30+ed.getSize()*20, 100, 30);
		this.add(jobTxt);
		
		jobField = new JTextField();
		jobField.setVisible(false);
		jobField.setBounds(nameField.getX(), jobTxt.getY()+20, 100, 30);
		this.add(jobField);
		
		companyTxt = new JLabel("Company:");
		companyTxt.setVisible(false);
		companyTxt.setBounds(nameField.getX()+103, jobTxt.getY(), 150, 30);
		this.add(companyTxt);
		
		companyField = new JTextField();
		companyField.setVisible(false);
		companyField.setBounds(nameField.getX()+103, jobField.getY(), 120, 30);
		this.add(companyField);
		
		startTimeTxt = new JLabel("Start Year-Month:");
		startTimeTxt.setVisible(false);
		startTimeTxt.setBounds(nameField.getX(), jobField.getY()+30, 200, 30);
		this.add(startTimeTxt);
		
		startTimeField = new JTextField();
		startTimeField.setVisible(false);
		startTimeField.setBounds(nameField.getX(), jobField.getY()+51, 100, 30);
		this.add(startTimeField);
		
		endTimeTxt = new JLabel("End Year-Month:");
		endTimeTxt.setVisible(false);
		endTimeTxt.setBounds(nameField.getX()+115, startTimeTxt.getY(), 200, 30);
		this.add(endTimeTxt);
		
		endTimeField = new JTextField();
		endTimeField.setVisible(false);
		endTimeField.setBounds(nameField.getX()+115, startTimeField.getY(), 100, 30);
		this.add(endTimeField);
		
		workFuncTxt = new JLabel("Work Function:");
		workFuncTxt.setVisible(false);
		workFuncTxt.setBounds(nameField.getX(), startTimeField.getY()+30, 200, 30);
		this.add(workFuncTxt);
		
		workFuncArea = new JTextArea();
		workFuncArea.setVisible(false);
		workFuncArea.setBorder(new LineBorder(Color.lightGray, 1));
		workFuncArea.setBounds(nameField.getX(), startTimeField.getY()+52, 200, 70);
		this.add(workFuncArea);
		
		this.setFocusable(true);
		
	}
	
	public Dimension getPreferredSize() {
		
        return new Dimension(500, 700);
		
    }
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		if(!resumePage) {
		    g.setColor(Color.black);
			schools = ed.getSchools();
			jobs = job.getJobs();
			
			int i = 0;
			while(schools.hasNext()) {
				g.drawString(schools.next(), schoolField.getX(), schoolField.getY() + 42 + i*20);
				i++;
			}
			
			i = 0;
			String[] job;
			while(jobs.hasNext()) {
				job = jobs.next().split("  ");
				g.drawString(job[3] + " " + job[0], add.getX(), add.getY() + 40 + i*20);
				i++;
			}
		} 
		else if(resumePage) {
			g.setColor(Color.black);
			
			int titleX = this.getWidth()/2-45;
			int titleY = 30;
			
			// Title
			g.setFont(new Font("TimesRoman", Font.BOLD, 30)); 
			g.drawString(nameField.getText(), titleX, titleY);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
			g.drawString(addressField.getText() + " | " + emailField.getText(), titleX-30, titleY+20);
			
			// Objective
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
			g.drawString(objectiveField.getText(), 10, titleY+30);
			
			int profX = 10;
			int profY = 120;
			
			int jobY = profY+30;
			schools = ed.getSchools();
			jobs = job.getJobs();
			
			// Professional Experience
			g.setFont(new Font("TimesRoman", Font.BOLD, 30));
			g.drawString("Professional Experience", profX, profY);
			g.drawString("______________________________________________________" , profX-5, profY+10);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
			String[] job;
			while(jobs.hasNext()) {
				job = jobs.next().split("  ");
				g.drawString(job[1], profX, jobY); 
				g.drawString(job[0] + ", " + job[2] + " - " + job[3], profX, jobY+15);
				g.drawString(job[4], profX, jobY+30);
				
				JButton delete = new JButton("Delete");
				delete.setBounds(profX+300, jobY, 50, 20);
				delete.setName(job[0]);
				delete.setFont(new Font("Helvetica", Font.PLAIN, 10));
				delete.setBackground(Color.YELLOW);
				delete.addActionListener(this);
				this.add(delete);
				buttons.add(delete);
				
				jobY += 60;
			}
			
			int edY = jobY+10;
			int schoolY = jobY+40;
			
			// Education
			g.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g.drawString("Education", profX, edY);
			g.drawString("______________________________________________________" , profX-5, edY+10);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 18)); 
			String school;
			while(schools.hasNext()) {
				school = schools.next();
				g.drawString(school.substring(0, 4) +  " - " + school.substring(5), profX, schoolY); 
				
				JButton delete = new JButton("Delete");
				delete.setBounds(profX+300, schoolY-15, 50, 20);
				delete.setName(school.substring(5));
				delete.setFont(new Font("Helvetica", Font.PLAIN, 10));
				delete.setBackground(Color.YELLOW);
				delete.addActionListener(this);
				this.add(delete);
				buttons.add(delete);
				
				schoolY += 18;
			}
			
		}
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==add && onEducation) {
			
			ed.addSchool(schoolField.getText(), gradField.getText());
			schoolField.setText("");
			gradField.setText("");
			repaint();
			
		} 
		else if(e.getSource()==done && onEducation) {

			onEducation = false;

			jobTxt.setBounds(nameField.getX(), schoolField.getY()+30+ed.getSize()*20, 100, 30);
			jobField.setBounds(nameField.getX(), jobTxt.getY()+20, 100, 30);
			companyTxt.setBounds(nameField.getX()+103, jobTxt.getY(), 150, 30);
			companyField.setBounds(nameField.getX()+103, jobField.getY(), 120, 30);
			startTimeTxt.setBounds(nameField.getX(), jobField.getY()+30, 200, 30);
			startTimeField.setBounds(nameField.getX(), jobField.getY()+51, 100, 30);
			endTimeTxt.setBounds(nameField.getX()+115, startTimeTxt.getY(), 200, 30);
			endTimeField.setBounds(nameField.getX()+115, startTimeField.getY(), 100, 30);
			workFuncTxt.setBounds(nameField.getX(), startTimeField.getY()+30, 200, 30);
			workFuncArea.setBounds(nameField.getX(), startTimeField.getY()+52, 200, 70);
			
			jobTxt.setVisible(true);
			companyTxt.setVisible(true);
			startTimeTxt.setVisible(true);
			endTimeTxt.setVisible(true);
			workFuncTxt.setVisible(true);
			jobField.setVisible(true);
			companyField.setVisible(true);
			startTimeField.setVisible(true);
			endTimeField.setVisible(true);
			workFuncArea.setVisible(true);
			
			add.setBounds(nameField.getX(), workFuncArea.getY()+workFuncArea.getHeight()+add.getHeight()-5, add.getWidth(), add.getHeight());
			done.setBounds(nameField.getX()+55, workFuncArea.getY()+workFuncArea.getHeight()+done.getHeight()-5, done.getWidth(), done.getHeight());
			
		} 
		else if(e.getSource()==add && !onEducation) {
			
			job.addJob(jobField.getText(), companyField.getText(), startTimeField.getText(), endTimeField.getText(), workFuncArea.getText());
			jobField.setText("");
			companyField.setText("");
			startTimeField.setText("");
			endTimeField.setText("");
			workFuncArea.setText("");
			repaint();
			
		} 
		else if(e.getSource()==done && !onEducation) {
			
			resumePage = true;
			
			titleTxt.setVisible(false);
			nameTxt.setVisible(false);
			addressTxt.setVisible(false);
			emailTxt.setVisible(false);
			objectiveTxt.setVisible(false);
			schoolTxt.setVisible(false);
			gradTxt.setVisible(false);
			nameField.setVisible(false);
			addressField.setVisible(false);
			emailField.setVisible(false);
			objectiveField.setVisible(false);
			schoolField.setVisible(false);
			gradField.setVisible(false);
			
			jobTxt.setVisible(false);
			companyTxt.setVisible(false);
			startTimeTxt.setVisible(false);
			endTimeTxt.setVisible(false);
			workFuncTxt.setVisible(false);
			jobField.setVisible(false);
			companyField.setVisible(false);
			startTimeField.setVisible(false);
			endTimeField.setVisible(false);
			workFuncArea.setVisible(false);
			
			add.setVisible(false);
			done.setVisible(false);
			
			repaint();
			
		} 
		else if(resumePage) {
			
			String[] jobsString;
			
			for(int i=0; i<buttons.size(); i++) {
				if(e.getSource() == buttons.get(i)) {
					jobs = job.getJobs();
					schools = ed.getSchools();
					
					while(jobs.hasNext()) {
						jobsString = jobs.next().split("  ");
						if(jobsString[0].equals(buttons.get(i).getName())) {
							jobs.remove();
							this.remove(buttons.get(i));
							buttons.remove(i);
							break;
						} 
					}
					
					while(schools.hasNext()) {
						if(schools.next().substring(5).equals(buttons.get(i).getName())) {
							schools.remove();
							this.remove(buttons.get(i));
							buttons.remove(i);
							break;
						}
					}
					repaint();
					break;
				}
			}
		}
		
	}
	
}
