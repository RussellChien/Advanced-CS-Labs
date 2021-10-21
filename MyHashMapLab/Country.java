public class Country {

	private String ab, name;
	
	public Country(String ab, String name) {
		this.ab = ab;
		this.name = name;
	}
	
	public String toString() {
		return ab + ", " + name;
	}
	
	public String getAb() {
		return ab;
	}
	
	public String getName() {
		return name;
	}
	
	public int hashCode() {
		char[] chars = ab.toCharArray();
		int hashCode = (chars[0]-'a') * 26 + (chars[1]-'a');
		return hashCode;
	}
	
}
