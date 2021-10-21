public class Runner{
	public static void main(String [] args){
		Practice p = new Practice();
		p.addStudent(new Student("Jose", 1234), "MVHS");
		p.addStudent(new Student("Jose", 1234), "LAHS");
		p.addStudent(new Student("Arthur", 1111), "LAHS");
		p.addStudent(new Student("Zelda", 4321), "MVHS");
		p.addStudent(new Student("Manuel", 9876), "MVHS");
		p.printHashandTreeLists();
		p.removeStudent(new Student("Jose", 1234));
		p.printHashandTreeLists();
		System.out.println(p.getSchool(new Student("Jose", 1234)));
		System.out.println(p.getSchool(new Student("Jose", 1233)));
		p.printStacksandPQueues();





	}
}