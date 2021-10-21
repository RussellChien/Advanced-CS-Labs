public class Car{
	private String make;
	private String model;
	private int year;
	private double price;

	public Car(String make, String model, int year, double price){
		this.make = make;
		this.model = model;
		this.year = year;
		this.price = price;
	}

	public String getMake(){
		return make;
	}

	public String getModel(){
		return model;
	}

	public int year(){
		return year;
	}

	public double price(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public String toString(){
		return make + " " + model + ": " + year + ". $" + price;
	}

	public int hashCode(){
		String m = make.toLowerCase();
		char one = m.charAt(0);
		char two =  m.charAt(1);
		char three = m.charAt(2);
		int valOne = (int) one;
		int valTwo = (int) two;
		int valThree = (int) three;
		return valOne + (valTwo * 17) + (valThree * 17 * 17);
	}
}