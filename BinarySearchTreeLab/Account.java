public class Account implements Comparable<Account> {
	private String fname, lname;
	private int pin;
	private double balance;

	public Account(String fname, String lname, int pin, double balance) {
		this.fname = fname;
		this.lname = lname;
		this.pin = pin;
		this.balance = balance;
	}

	public String getfname() {
		return fname;
	}

	public String getlname() {
		return lname;
	}

	public int getPin() {
		return pin;
	}

	public double getBalance() {
		return balance; 
	}

	public String toString() {
		return lname + ", " + fname;
	}

	public void setfname(String fname) {
		this.fname = fname;
	}

	public void setlname(String lname) {
		this.lname = lname;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
	
	public void addBalance(double balance) {
		this.balance = this.balance + balance;
	}

	public boolean equals(Object other) {
		Account a = (Account) other;
		return this.fname.equals(a.getfname()) && this.lname.equals(a.getlname());
	}

	public int compareTo(Account other) {
		if(lname.compareTo(other.getlname()) > 0) {
			return 1; 
		} else if(lname.compareTo(other.getlname()) == 0) {
			if(fname.compareTo(other.getfname()) > 0) {
				return 1;
			}
			else {
				return -1;
			}
		} else if(lname.compareTo(other.getlname()) < 0) {
			return -1;
		}
		return 0; 	
	}
	
}