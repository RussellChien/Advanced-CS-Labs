import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; 
public class Screen extends JPanel implements ActionListener{

	private Account c1;
	private Account c2;
	private Account c3;
	private Account c4;
	private Account c5;
	private JTextField pinInput;
	private JTextField nameInput;
	private JButton checkName;
	private JTextField changePin;
	private JButton checkLogin;
	private JButton updatePin;
	private JTextField input;
	private JButton deposit;
	private JButton withdraw;
	private JButton signout;
	private JTextField changeName;
	private JButton updateName;
	private ArrayList<Account> users; 
	private boolean login = true;
	private int correct = 0;
	
	public Screen(){
		setLayout(null);
		
		users = new ArrayList<Account>();
		c1 = new Account("Jennifer", 999.99, 1234);
		c2 = new Account("Jose", 500.01, 4321);
		c3 = new Account("Jerry", 50.50, 1111);
		c4 = new Account("Kai", 0.01, 9999);
		c5 = new Account("Ben", 1500, 5555);
		users.add(c1);
		users.add(c2);
		users.add(c3);
		users.add(c4);
		users.add(c5);
		//create the textfield
		pinInput = new JTextField();
		pinInput.setBounds(50, 300, 100, 30);
		this.add(pinInput);

		nameInput = new JTextField();
		nameInput.setBounds(50, 250, 100, 30);
		this.add(nameInput);
		
		checkLogin = new JButton("Log in");
		checkLogin.setBounds(50, 350, 100, 30);
		checkLogin.addActionListener(this);
		this.add(checkLogin);
		
		input = new JTextField();
		input.setBounds(250, 300, 250, 30);
		this.add(input);
		input.setVisible(false);
		
		deposit = new JButton("Deposit");
		deposit.setBounds(250, 350, 100, 30);
		deposit.addActionListener(this);
		this.add(deposit);
		deposit.setVisible(false);
		
		withdraw = new JButton("Withdraw");
		withdraw.setBounds(400, 350, 100, 30);
		withdraw.addActionListener(this);
		this.add(withdraw);
		withdraw.setVisible(false);
		
		signout = new JButton("Sign out");
		signout.setBounds(50, 400, 100, 30);
		signout.addActionListener(this);
		this.add(signout);
		signout.setVisible(false);
		
		updateName = new JButton("Update name");
		updateName.setBounds(200, 500, 150, 30);
		updateName.addActionListener(this);
		this.add(updateName);
		updateName.setVisible(false);
		
		changeName = new JTextField();
		changeName.setBounds(200, 450, 150, 30);
		this.add(changeName);
		changeName.setVisible(false);

		changePin = new JTextField();
		changePin.setBounds(400, 450, 150, 30);
		this.add(changePin);
		changePin.setVisible(false);

		updatePin = new JButton("Update pin");
		updatePin.setBounds(400, 500, 150, 30);
		updatePin.addActionListener(this);
		this.add(updatePin);
		updatePin.setVisible(false);


		
		setFocusable(true);
	}

	public Dimension getPreferredSize(){
		return new Dimension(600, 600);
	}
    
	public void paintComponent (Graphics g){
		super.paintComponent(g);
		Color background = new Color(0, 0, 139);
		Color black = new Color(0,0,0);
		Color blue = new Color(65,105,225);
		Color white = new Color(255,255,255);
		Color yellow = new Color(255, 255, 0);
		Color grey = new Color(192, 192, 192);
		g.setColor(grey);
		g.fillRect(0, 0, 600, 100);
		g.setColor(blue);
		g.drawString("ATM", 290, 50);
		g.setColor(background);
		g.fillRect(0, 100, 600, 500);
		g.setColor(yellow);
		g.fillRect(50, 0, 50, 50);
		g.fillRect(500, 0, 50, 50);
		/*
		g.setColor(white);
		g.drawString("Card", 275, 500);
		g.drawString("Cash", 475, 500);
		g.setColor(grey);
		g.fillRect(250, 500, 100, 30);
		g.fillRect(450, 500, 100, 30);
		g.setColor(black);
		g.fillRect(255, 505, 80, 15);
		g.fillRect(455, 505, 80, 15);
		*/
		int i = 0;
		while(i < correct){
			i++;
		}
		g.setColor(white);
		g.drawString("Name: ", 10, 270);
		g.drawString("Pin: ", 20, 320);
		if(!login){
				g.drawString("Invalid Login", 50, 220);
		}
		if (correct == 0){
			g.drawString("Enter name and pin", 50, 240);
		}
		else{
			if(users.get(i).getAccess()){
				g.setColor(white);
				g.drawString("Name: " + users.get(i).getName(), 20, 200);
				g.drawString("Balance : " + users.get(i).getBalance(), 20, 220);
				g.drawString("Enter in the amount you want to deposit or withdraw", 250, 250);
			}
		}

	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == checkLogin){
			String pinText = pinInput.getText();
			int pin = Integer.parseInt(pinText);
			String nameText = nameInput.getText();
			for(int i = 0; i < users.size(); i++){
				if(users.get(i).checkLogin(pin,nameText)){
					correct = i;
					login = true;
				}
			}
			if(correct > 0){
				input.setVisible(true);
				deposit.setVisible(true);
				withdraw.setVisible(true);
				signout.setVisible(true);
				updateName.setVisible(true);
				changeName.setVisible(true);
				updatePin.setVisible(true);
				changePin.setVisible(true);
			}
			else{
				login = false;
			}
		}
		if (e.getSource() == deposit){
			String depositText = input.getText();
			double deposit = Double.parseDouble(depositText);
			users.get(correct).deposit(deposit);
		}
		
		if (e.getSource() == withdraw){
			String withdrawText = input.getText();
			double withdraw = Double.parseDouble(withdrawText);
			users.get(correct).withdraw(withdraw);
		}
		
		if (e.getSource() == signout){
			for(int i = 0; i < users.size(); i++){
				users.get(i).disableAccess();
			}
			correct = 0;
			input.setVisible(false);
			deposit.setVisible(false);
			withdraw.setVisible(false);
			signout.setVisible(false);
			updateName.setVisible(false);
			changeName.setVisible(false);
			updatePin.setVisible(false);
			changePin.setVisible(false);
		}
		if (e.getSource() == updateName){
			String nameText = changeName.getText();
			String name = nameText;
			users.get(correct).updateName(name);
		}
		if (e.getSource() == updatePin){
			String pinText = changePin.getText();
			int newPin = Integer.parseInt(pinText);
			users.get(correct).updatePin(newPin);
		}
		
		
		repaint(); //redraw the screen
	}
	

}



