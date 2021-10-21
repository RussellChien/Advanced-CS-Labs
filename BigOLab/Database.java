import java.util.ArrayList;
import java.util.Scanner;
import java.util.ListIterator;
import java.io.FileNotFoundException;
import java.io.File;

public class Database {

	private ArrayList<Student> students;
	private int passes = 0;

	public Database() {
		students = new ArrayList<Student>();

		try {
			Scanner in = new Scanner(new File("names.txt"));

			while (in.hasNextLine()) {

				String line = in.nextLine();
				String[] name = line.split("\\s+");

				ListIterator<Student> it = students.listIterator();
				Student stu = new Student(name[0], name[1], (int)((Math.random() * 4) + 14));


				while (it.hasNext()) {
					if (it.next().toString().compareTo(stu.toString()) > 0) {
						it.previous();
						break;
					}
				}

				it.add(stu);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public int binarySearch(String lastName) {
		this.passes = 0;
		int startPos = 0;
		int endPos = students.size() - 1;

		while (startPos <= endPos) {
			this.passes++;
			int mid = (startPos + endPos) / 2;

			if (students.get(mid).getLastName().equals(lastName)) {
				return mid + 1; // not index
			} else if (students.get(mid).getLastName().compareTo(lastName) > 0) {
				endPos = mid - 1;

			} else if (students.get(mid).getLastName().compareTo(lastName) < 0) {
				startPos = mid + 1;
			}

		}

		return -1;

	}


	public int sequentialSearch(String lastName) {
		this.passes = 0;

		for (int i = 0; i < students.size(); i++) {
			this.passes++;

			if (students.get(i).getLastName().equals(lastName))
				return i + 1;
		}

		return -1;
	}


	public void bubbleSort() {
		this.passes = 0;

		Student temp;

		for (int i = 0; i < students.size(); i++) {
			for (int j = i + 1; j < students.size(); j++) {
				this.passes++;

				if (students.get(i).getLastName().compareTo(students.get(j).getLastName()) > 0) {
					temp = students.get(i);
					students.set(i, students.get(j));
					students.set(j, temp);
				}
			}
		}
	}

	public void mergeSort() {
		this.passes = 0;
		this._mergeSort(0, students.size());
	}


	private void _mergeSort(int start, int end) {
		int mid = (start + end) / 2;

		if (mid == start)
			return;

		this._mergeSort(start, mid);
		this._mergeSort(mid, end);

		this.merge(start, end);
	}

	private void merge(int start, int end) {

		this.passes++;
		Student[] temp = new Student[end - start];

		int mid = (start + end) / 2;
		int i = start;
		int j = mid;
		int k = 0;

		while (i < mid && j < end) {
			if (students.get(i).getLastName().compareTo(students.get(j).getLastName()) < 0) {
				temp[k] = students.get(i);
				i++;
			} else {
				temp[k] = students.get(j);
				j++;
			}

			k++;
		}

		while (i < mid) {
			temp[k] = students.get(i);
			i++;
			k++;
		}

		while (j < end) {
			temp[k] = students.get(j);
			j++;
			k++;
		}

		for (i = 0; i < end - start; i++) {
			students.set(start + i, temp[i]);
		}

	}


	public int getPasses() {
		return this.passes;
	}

	public Student getStudent(int i) {
		return students.get(i - 1);
	}

	public void scramble() {
		int index;
		Student temp;

		for (int i = 0; i < students.size(); i++) {

			index = (int)(Math.random() * students.size());
			temp = students.get(index);
			students.set(index, students.get(i));
			students.set(i, temp);

		}
	}

	public String toString() {
		String returnVal = "";

		for (int i = 0; i < students.size(); i++) {
			returnVal += (i + 1) + ". " + students.get(i).toString() + "\n";
		}

		return returnVal.substring(0, returnVal.length() - 1);
	}


}