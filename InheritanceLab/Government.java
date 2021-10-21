public class Government extends Employee{

	private String city;

	public Government(String name, String profile, String job, String city, int xPos) {
		super(name, profile, job, xPos);
		this.city = city;
	}
	
	public double getSalary() {
		return 175000.00;
	}
	
	public String toString() {
		return super.toString() + city;
	}
	
}