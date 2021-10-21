public class Student implements Comparable<Student>{
	private String name;
	private int id;

	public Student(String n, int i){
		name = n;
		id = i;
	}

	public String toString(){
		return name + ": " + id;
	}

	public int hashCode(){
		return name.hashCode() * id * 7;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public boolean equals(Object o){
		Student s = (Student) o;
		return hashCode() == s.hashCode();
	}

	public int compareTo(Student s){
		if(name.equals(s.getName()) && id == s.getId()){
				return 0;
		}
		else if(name.equals(s.getName())){
			return id - s.getId();
		}
		else{
			return name.compareTo(s.getName());
		}
		
		
	}


}