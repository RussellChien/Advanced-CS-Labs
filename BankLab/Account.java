public class Account{
	private String name;
	private double balance;
	private int pin;
	private boolean access; 
	public Account(String name, double balance, int pin){
		this.name = name;
		this.balance = balance; 
		this.pin = pin;
		access = false;
	}
	public String getName(){
		if(access){
			return name;
		}
		return null;
	}
	public void updateName(String n){
		if(access){
			name = n;
		}
	}
	public void updatePin(int p){
		if(access){
			pin = p;
		}
	}
	public double getBalance(){
		if(access){
			return balance;
		}
		return 0.0;
	}
	public boolean getAccess(){
		return access;
	}
	public void disableAccess(){
		access = false;
	}
	public boolean checkLogin(int a, String n){
		if(a == pin && name.equals(n)){
			access = true;
		}
		else{
			access = false; 
		}
		return access;
	}
	public void setBalance(double a){
		if(access){
			balance = a;
		}
	}
	public void withdraw(double a){
		if(access){
			if(balance > a){
				balance -= a;
			}
		}
	}
	public void deposit(double a){
		if(access){
			balance += a; 
		}
	}
}