import java.util.ArrayList;

public class Patient implements Comparable<Patient>{
	
	private String name, illness, priority, doctorNote = "";
	private int timeStamp;
	private boolean done = false;
	private String age;
	private ArrayList<String> priorities = new ArrayList<String>();
	
	public Patient(String name, String illness, String age, String priority, int timeStamp) {
		this.name = name;
		this.illness = illness;
		this.priority = priority;
		this.timeStamp = timeStamp;
		this.age = age;
		priorities.add("high");
		priorities.add("medium");
		priorities.add("low");
	}
	
	public String getDoctorNote() {
		return doctorNote;
	}
	
	public void setDoctorNote(String note) {
		doctorNote = note;
	}
	
	public String getName(){
		return name;
	}
	
	public String getIllness() {
		return illness;
	}

	public String getAge(){
		return age;
	}
	
	public String getPriority() {
		return priority;
	}
	
	public int getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(int t) {
		timeStamp = t;
	}
	
	public void done() {
		done = true;
	}
	
	public int compareTo(Patient p) {
		if(!done) {
			if(priorities.indexOf(p.getPriority()) < priorities.indexOf(priority)) 
				return 1;
			else if(priorities.indexOf(p.getPriority()) > priorities.indexOf(priority)) 
				return -1;
			else if(priorities.indexOf(p.getPriority()) == priorities.indexOf(priority)){
				if(p.getTimeStamp()<timeStamp) 
					return 1;
				else if(p.getTimeStamp()>timeStamp) 
					return -1;
			} 
		} else {
			return name.compareTo(p.getName());
		}
		return 0;
		
	}
	
	public boolean equals(Object o) {
		Patient p = (Patient) o;
		if(p.getName().equals(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return name + ", " + age + " : " + illness + " : " + priority;
		
	}

}
