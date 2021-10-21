import java.util.*;
public class Practice{
	private HashSet<Student> hashset; 
	private TreeSet<Student> treeset;
	private HashMap<Student, String> hashmap;
	private TreeMap<Student, String> treemap;
	private PriorityQueue<Student> priorityqueue;
	private Stack<Student> stack;

	public Practice(){
		hashset = new HashSet<Student>();
		treeset = new TreeSet<Student>();
		hashmap = new HashMap<Student, String>();
		treemap = new TreeMap<Student, String>();
		priorityqueue = new PriorityQueue<Student>();
		stack = new Stack<Student>();
	}

	public void addStudent(Student student, String school){
		hashset.add(student);
		treeset.add(student);
		hashmap.put(student, school);
		treemap.put(student, school);
		priorityqueue.add(student);
		stack.push(student);

	}

	public void printHashandTreeLists(){
		System.out.println("hash set");
		for(Student s : hashset){
			System.out.println(s);
		}
		System.out.println();
		System.out.println("tree set");
		for(Student t : treeset){
			System.out.println(t);
		}
		System.out.println();
		System.out.println("hash map");
		for(Student hm : hashmap.keySet()){
			System.out.println(hm + ", " + hashmap.get(hm));
		}
		System.out.println();

		System.out.println("tree map");
		for(Student tm : treemap.keySet()){
			System.out.println(tm + ", " + treemap.get(tm));
		}
		System.out.println();
	}

	public void removeStudent(Student student){
		hashset.remove(student);
		treeset.remove(student);
		hashmap.remove(student);
		treemap.remove(student);
	}

	public String getSchool(Student student){
		return "school: " + hashmap.get(student);
	}

	public void printStacksandPQueues(){
		System.out.println("stack");
		while(!stack.isEmpty()){
			System.out.println(stack.pop());
		}
		System.out.println("\npriority queue");
		while(!priorityqueue.isEmpty()){
			System.out.println(priorityqueue.poll());
		}
	}

	

	
}