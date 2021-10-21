public class Company extends Employee{

	private String company;

	public Company(String name, String profile, String job, String company, int xPos) {
		super(name, profile, job, xPos);
		this.company = company;
	}
	
	public double getSalary() {
		return 600000.00;
	}
	
	public String toString() {
		return super.toString() + company + " ";
	}
	
}