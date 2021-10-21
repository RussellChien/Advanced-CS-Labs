import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Education {

	private ArrayList<String> schools = new ArrayList<String>();
	private ListIterator<String> li;
	
	public Education() {}
	
	public void addSchool(String school, String gradYear) {
		
		li = schools.listIterator();
		while(li.hasNext()) {
			if(li.next().compareTo(gradYear)>0) {
				li.previous();
				break;
			}
		}
		li.add(gradYear + " " + school);

	}
	
	public ListIterator<String> getSchools() {
		return schools.listIterator();
	}
	
	public int getSize() {
		return schools.size();
	}
	
}
