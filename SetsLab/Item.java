public class Item implements Comparable<Item> {
	private String name;
	private double price;

	public Item(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public boolean equals(Object o) {
		Item item = (Item)o;
		return item.hashCode() == o.hashCode();
	}

	public int hashCode() {
		int hashCode = 0;
		char[] array = name.toCharArray();
		for (int i = 0; i < array.length; i++) {
			hashCode += ((int)array[i] - 47) * Math.pow(26, i);
		}
		hashCode = hashCode + (int)(price * 79);
		return hashCode;
	}

	public int compareTo(Item item) {
		return this.getCompareString().compareTo(item.getCompareString());
	}

	public String getCompareString() {
		return name + "- " + price;
	}

	public String getName() { 
		return name; 
	}
	
	public double getPrice() { 
		return price; 
	}

	public String toString() {
		return name + ", $" + price;
	}
}