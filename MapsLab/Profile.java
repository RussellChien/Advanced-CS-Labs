import java.util.*;

public class Profile implements Comparable<Profile>{
	private String firstName;
	private String lastName;
	private int birthDate;

	public Profile(String lname, String fname, int bday){
		firstName = fname;
		lastName = lname;
		birthDate = bday;
	}

	public String getFirstIntial(){
		char[] charArray = firstName.toCharArray();
		return Character.toString(charArray[0]);
	}

	public String getLastIntial(){
		char[] charArray = lastName.toCharArray();
		return Character.toString(charArray[0]);
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public int getBirthDate(){
		return birthDate;
	}

	public String toString(){
		return lastName + " " + firstName + ": " + birthDate;
	}

	public int hashCode(){
		char[] firstNameArray = firstName.toCharArray();
		char[] lastNameArray = lastName.toCharArray();
		int hashCode = 0;
		for(int i = 0; i < firstNameArray.length; i++){
			hashCode += firstNameArray[i]-47 * Math.pow(17, i);
		}
		for(int i = 0; i < lastNameArray.length; i++){
			hashCode += lastNameArray[i]-47 * Math.pow(17, i);
		}
		hashCode += birthDate;
		return hashCode;

	}

	public boolean equals(Object o){
		Profile p = (Profile) o;
		return getFirstIntial().equals(p.getFirstIntial()) &&
			   getLastIntial().equals(p.getLastIntial()) &&
			   birthDate == p.getBirthDate();
	}

	public int compareTo(Profile p){
		if(this.equals(p)){
			return 0; 
		}
		else if(firstName.equals(p.getFirstName()) && lastName.equals(p.getLastName())){
			return birthDate - p.getBirthDate();
		}
		else if(lastName.equals(p.getLastName())){
			return firstName.compareTo(p.getFirstName());
		}
		else{
			return lastName.compareTo(p.getLastName());
		}
	}


}