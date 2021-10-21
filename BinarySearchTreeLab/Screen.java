import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Screen extends JPanel {

	private JPanel adminPanel, userPanel, cardsPanel;
	private JComboBox<String> views;
	private File file;
	private FileWriter writer;
	private Account loggedInAccount;
	//admin panel
	private JScrollPane scrollPane;
	private JTextArea users;
	private JLabel search, searchResult, add, fname, lname, fname2, lname2, pin, balance;
	private JTextField userfname, userlname, addfname, addlname, addPin, addBalance;
	//user panel
	private JTextField inputfname, inputlname, inputPin, changefname, changelname, changePin, amount;
	private JLabel login, loginResult, loginfname, loginlname, loginPin, newfname, newlname, newPin, newBalance, amountLabel;
	private BinarySearchTree<Account> accounts;
	private JButton searchButton, addButton;
	private JButton loginButton, fnameButton, lnameButton, pinButton, depositButton, withdrawButton, logout;

	public Screen() {
		this.setLayout(null);

		accounts = new BinarySearchTree<Account>();

		file = new File("names.txt");

		try {
			BufferedReader f = new BufferedReader(new FileReader("names.txt"));
			int size = 1000;
			f = new BufferedReader(new FileReader("names.txt"));
			String[] line;
			for(int i = 0; i < size; i++) {
				line = f.readLine().split(",");
				accounts.add(new Account(line[1], line[0], (int)(Math.random() * 10000), (double)(int)(Math.random() * 100001)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] panels = {"Admin", "User"};
		views = new JComboBox<String>(panels);
		views.setBounds(300, 15, 120, 40);
		views.requestFocus();
		views.addItemListener(e -> {
			String itemName = (String) e.getItem();
			CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();
			cardLayout.show(cardsPanel, itemName);
		});
		this.add(views);
		
		cardsPanel = new JPanel();
		cardsPanel.setBounds(0, 0, 800, 600);
		cardsPanel.setLayout(new CardLayout());
				
		//admin
		adminPanel = new JPanel();
		adminPanel.setBounds(0, 0, 800, 600);
		adminPanel.setLayout(null);
		cardsPanel.add(adminPanel, "Admin");
				
		search = new JLabel("Search");
		search.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		search.setBounds(400, 50, 400, 50);
		adminPanel.add(search);
		
		userfname = new JTextField();
		userfname.setBounds(search.getX(), 100, 150, 35);
		userfname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(userfname);

		fname = new JLabel("First Name: ");
		fname.setBounds(userfname.getX() - 75, userfname.getY(), 100, 35);
		fname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(fname);
		
		userlname = new JTextField();
		userlname.setBounds(userfname.getX(), userfname.getY() + 45, 150, 35);
		userlname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(userlname);

		lname = new JLabel("Last Name: ");
		lname.setBounds(userlname.getX() - 75, userlname.getY(), 100, 35);
		lname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(lname);
		
		searchButton = new JButton("Enter");
		searchButton.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		searchButton.setBounds(400, 190, 100, 30);
		searchButton.addActionListener(e -> {
			Account result = accounts.get(new Account(userfname.getText(), userlname.getText(), 0, 0.0));
			if(result != null) {
				searchResult.setText("<html>" + result.toString() + "<br/>Pin: " + result.getPin() + "<br/>Balance: " + result.getBalance() + "<br/>Passes: " + accounts.getPasses() + "</html>");
				userfname.setText("");
				userlname.setText("");
			} else {
				searchResult.setText("Invalid search");
			}
		});
		adminPanel.add(searchButton);

		searchResult = new JLabel();
		searchResult.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		searchResult.setBounds(400, 180, 550, 150);
		adminPanel.add(searchResult);
		
		add = new JLabel("Add");
		add.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		add.setBounds(435, 300, 400, 50);
		adminPanel.add(add);
		
		addfname = new JTextField();
		addfname.setBounds(add.getX() - 50, 350, 200, 35);
		addfname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(addfname);

		fname2 = new JLabel("First Name: ");
		fname2.setBounds(addfname.getX() - 75, addfname.getY(), 100, 35);
		fname2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(fname2);
		
		addlname = new JTextField();
		addlname.setBounds(addfname.getX(), addfname.getY() + 45, 200, 35);
		addlname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(addlname);

		lname2 = new JLabel("Last Name: ");
		lname2.setBounds(addlname.getX() - 75, addlname.getY(), 100, 35);
		lname2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(lname2);
		
		addPin = new JTextField();
		addPin.setBounds(addfname.getX(), addfname.getY() + 90, 200, 35);
		addPin.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(addPin);

		pin = new JLabel("Pin: ");
		pin.setBounds(addPin.getX() - 30, addPin.getY(), 100, 35);
		pin.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(pin);
		
		addBalance = new JTextField();
		addBalance.setBounds(addfname.getX(), addfname.getY() + 135, 200, 35);
		addBalance.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(addBalance);

		balance = new JLabel("Balance: ");
		balance.setBounds(addBalance.getX() - 60, addBalance.getY(), 100, 35);
		balance.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		adminPanel.add(balance);
		
		addButton = new JButton("Enter");
		addButton.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		addButton.setBounds(435, 525, 100, 30);
		addButton.addActionListener(e -> {
			accounts.add(new Account(addfname.getText(), addlname.getText(), Integer.parseInt(addPin.getText()), Double.parseDouble(addBalance.getText())));
			searchResult.setText("Passes: " + accounts.getPasses());
			try {
				writer = new FileWriter(this.file, true);
	            writer.write(addlname.getText() + " " + addfname.getText() + " " + addPin.getText() + " " + addBalance.getText() + "\n");
	            writer.flush();
	            writer.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
			displayAccounts();
			addfname.setText("");
			addlname.setText("");
			addPin.setText("");
			addBalance.setText("");
		});
		adminPanel.add(addButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 300, 600);
		adminPanel.add(scrollPane);
		
		users = new JTextArea();
		users.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		users.setEditable(false);
		scrollPane.setViewportView(users);
		displayAccounts();
		
		//user
		userPanel = new JPanel();
		userPanel.setBounds(0, 0, 800, 600);
		userPanel.setLayout(null);
		cardsPanel.add(userPanel, "User");
		
		login = new JLabel("Login");
		login.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		login.setBounds(265, 85, 400, 50);
		userPanel.add(login);
		
		inputfname = new JTextField();
		inputfname.setBounds(250, 150, 200, 35);
		inputfname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		userPanel.add(inputfname);

		loginfname = new JLabel("First Name: ");
		loginfname.setBounds(inputfname.getX() - 75, inputfname.getY(), 100, 35);
		loginfname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		userPanel.add(loginfname);
		
		inputlname = new JTextField();
		inputlname.setBounds(inputfname.getX(), inputfname.getY() + 40, 200, 35);
		inputlname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		userPanel.add(inputlname);

		loginlname = new JLabel("Last Name: ");
		loginlname.setBounds(inputlname.getX() - 75, inputlname.getY(), 100, 35);
		loginlname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		userPanel.add(loginlname);
		
		inputPin = new JTextField();
		inputPin.setBounds(inputfname.getX(), inputfname.getY() + 80, 200, 35);
		inputPin.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		userPanel.add(inputPin);

		loginPin = new JLabel("Pin: ");
		loginPin.setBounds(inputPin.getX() - 30, inputPin.getY(), 100, 35);
		loginPin.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		userPanel.add(loginPin);
		
		loginButton = new JButton("Login");
		loginButton.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		loginButton.setBounds(login.getX(), 280, 100, 30);
		loginButton.addActionListener(e -> {
			Account result = accounts.get(new Account(inputfname.getText(), inputlname.getText(), 0, 0.0));
			if(result != null) {
				if(result.getPin() == Integer.parseInt(inputPin.getText())) {
					loggedInAccount = result;
					loginResult.setText("<html>" + "<br/>User: " + result.toString() + "<br/>Balance: " + result.getBalance() + "<br/>Passes: " + accounts.getPasses() + "</html>");
					updateLogin(true);
					inputfname.setText("");
					inputlname.setText("");
					inputPin.setText("");
				}
			}
		});
		userPanel.add(loginButton);
				
		loginResult = new JLabel();
		loginResult.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		loginResult.setBounds(20, 20, 400, 100);
		loginResult.setVisible(false);
		userPanel.add(loginResult);
		
		changefname = new JTextField();
		changefname.setBounds(100, 125, 200, 35);
		changefname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		changefname.setVisible(false);
		userPanel.add(changefname);

		newfname = new JLabel("First Name: ");
		newfname.setBounds(changefname.getX() - 75, changefname.getY(), 100, 35);
		newfname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		newfname.setVisible(false);
		userPanel.add(newfname); 
		
		fnameButton = new JButton("Change");
		fnameButton.setBounds(changefname.getX() + 200, changefname.getY(), 150, 35);
		fnameButton.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		fnameButton.setVisible(false);
		fnameButton.addActionListener(e -> {
			loggedInAccount.setfname(changefname.getText());
			accounts.remove(loggedInAccount);
			accounts.add(loggedInAccount);
			changefname.setText("");
			displayAccounts();
			saveFile();
		});
		userPanel.add(fnameButton);
		
		changelname = new JTextField();
		changelname.setBounds(changefname.getX(), changefname.getY() + 45, 200, 35);
		changelname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		changelname.setVisible(false);
		userPanel.add(changelname);

		newlname = new JLabel("Last Name: ");
		newlname.setBounds(changelname.getX() - 75, changelname.getY(), 100, 35);
		newlname.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		newlname.setVisible(false);
		userPanel.add(newlname);
		
		lnameButton = new JButton("Change");
		lnameButton.setBounds(changelname.getX() + 200, changelname.getY(), 150, 35);
		lnameButton.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		lnameButton.setVisible(false);
		lnameButton.addActionListener(e -> {
			loggedInAccount.setlname(changelname.getText());
			changelname.setText("");
			accounts.remove(loggedInAccount);
			accounts.add(loggedInAccount);
			displayAccounts();
			saveFile();
		});
		userPanel.add(lnameButton);
		
		changePin = new JTextField();
		changePin.setBounds(changelname.getX(), changelname.getY() + 45, 200, 35);
		changePin.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		changePin.setVisible(false);
		userPanel.add(changePin);

		newPin = new JLabel("Pin: ");
		newPin.setBounds(changePin.getX() - 30, changePin.getY(), 100, 35);
		newPin.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		newPin.setVisible(false);
		userPanel.add(newPin);
		
		pinButton = new JButton("Change");
		pinButton.setBounds(changePin.getX() + 200, changePin.getY(), 150, 35);
		pinButton.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		pinButton.setVisible(false);
		pinButton.addActionListener(e -> {
			loggedInAccount.setPin(Integer.parseInt(changePin.getText()));
			changePin.setText("");
			displayAccounts();
			saveFile();
		});
		userPanel.add(pinButton);
		
		amount = new JTextField();
		amount.setBounds(150, 350, 250, 35);
		amount.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		amount.setVisible(false);
		userPanel.add(amount);

		amountLabel = new JLabel("Amount: ");
		amountLabel.setBounds(amount.getX() - 55, amount.getY(), 100, 35);
		amountLabel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		amountLabel.setVisible(false);
		userPanel.add(amountLabel);
		
		withdrawButton = new JButton("Withdraw");
		withdrawButton.setBounds(amount.getX() - 50, amount.getY() + 45, 150, 35);
		withdrawButton.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		withdrawButton.setVisible(false);
		withdrawButton.addActionListener(e -> {
			loggedInAccount.addBalance(-1 * Integer.parseInt(amount.getText()));
			amount.setText("");
			displayAccounts();
			saveFile();
		});
		userPanel.add(withdrawButton);
		
		depositButton = new JButton("Deposit");
		depositButton.setBounds(withdrawButton.getX() + 150, amount.getY() + 45, 150, 35);
		depositButton.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		depositButton.setVisible(false);
		depositButton.addActionListener(e -> {
			loggedInAccount.addBalance(Integer.parseInt(amount.getText()));
			amount.setText("");
			displayAccounts();
			saveFile();
		});
		userPanel.add(depositButton);
		
		logout = new JButton("Logout");
		logout.setBounds(225, 500, 100, 35);
		logout.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		logout.setVisible(false);
		logout.addActionListener(e -> {
			loggedInAccount = null;
			updateLogin(false);
		});
		userPanel.add(logout);
		
		this.add(cardsPanel);

	}

	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
	}

	public void displayAccounts() {
		users.setText(accounts.toString());
	}

	public void updateLogin(boolean b) {
		inputfname.setVisible(!b);
		loginfname.setVisible(!b);
		inputlname.setVisible(!b);
		loginlname.setVisible(!b);
		inputPin.setVisible(!b);
		loginPin.setVisible(!b);
		login.setVisible(!b);
		loginButton.setVisible(!b);
		loginResult.setVisible(b);
		changefname.setVisible(b);
		newfname.setVisible(b);
		changelname.setVisible(b);
		newlname.setVisible(b);
		changePin.setVisible(b);
		newPin.setVisible(b);
		fnameButton.setVisible(b);
		lnameButton.setVisible(b);
		pinButton.setVisible(b);
		amount.setVisible(b);
		amountLabel.setVisible(b);
		withdrawButton.setVisible(b);
		depositButton.setVisible(b);
		logout.setVisible(b);
	}

	public void saveFile() {
		try {
			writer = new FileWriter(file);
			writer.write("");
            writer.flush();
            writer.close();
            writer = new FileWriter(file, true);
            String[] userList = users.getText().split("\n"), each;
            Account result;
            for(int i = 0; i < userList.length; i++) {
            	each = userList[i].split(", ");
            	result = accounts.get(new Account(each[1], each[0], (int)(Math.random() * 10000), (double)(Math.random() * 100001)));
            	if(result != null) {
	            	writer.write(result.getlname() + " " + result.getfname() + " " + result.getPin() + " " + result.getBalance() + "\n");
            	}
            }
            writer.flush();
            writer.close();
            char passes = loginResult.getText().split("")[0].charAt(loginResult.getText().split("")[0].length()-1);
			loginResult.setText("<html>" + "<br/>User: " + loggedInAccount.toString() + "<br/>Balance: " + loggedInAccount.getBalance() + "<br/>Passes: " + passes + "</html>");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

}