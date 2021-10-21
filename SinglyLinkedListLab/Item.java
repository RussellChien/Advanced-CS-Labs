public class Item{

	private String name;
	private double price;
	private int quantity, timeAdded;
	
	public Item(String name, double price, int quantity, int timeAdded){
		this.name = name.toLowerCase();
		this.price = price;
		this.quantity = quantity;
		this.timeAdded = timeAdded;
	}
	
	public String getName(){
		return name;
	}
	
	public double getPrice(){
		return price;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getTimeAdded(){
		return timeAdded;
	}
	
	public void setTimedAdded(int timeAdded){
		this.timeAdded = timeAdded;
	}
	
	public boolean equals(Object o){
		Item i = (Item) o;
		if(i.getName().equals(name) && i.getPrice() == price){
			return true;
		}
		return false;
	}
	
}
