public class HashTable <E> {

	private DLList<E>[] table;
	public final int SIZE = 702;
	private int size = 0;

	@SuppressWarnings("unchecked")
	public HashTable() {
		table = new DLList[SIZE];
		for (int i = 0; i < table.length; i++) {
			table[i] = new DLList < E > ();
		}
	}

	public void add(E data) {
		table[data.hashCode() % 702].add(data);
		size++;
	}

	public DLList < E > get(int index) {
		return table[index];
	}

	public DLList < E > get(E data) {
		return table[data.hashCode() % 702];
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < table.length; i++) {
			str += "bucket " + i + " " + table[i].toString() + "\n";
		}
		return str;
	}

	public int size() {
		return size;
	}

	public boolean contains(E e) {
		int index = e.hashCode() % table.length;
		if (table[index] == null) return false;
		return table[index].contains(e);
	}

	public void remove(E e) {
		int index = e.hashCode() % table.length;
		if (table[index] == null) {
			return;
		}
		if (table[index].contains(e)) {
			table[index].remove(e);
		}
	}

}