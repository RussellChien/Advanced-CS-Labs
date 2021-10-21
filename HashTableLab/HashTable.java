public class HashTable<E> {
	
	private DLList<E>[] table;
	private int size = 37455;
	
	@SuppressWarnings("unchecked")
	public HashTable(){
		table = new DLList[size];
		for(int i = 0; i < table.length; i++){
			table[i] = new DLList<E>();
		}
	}
	
	public void add(E data) {
        int index = data.hashCode() % table.length;
        if (table[index] == null){
        	table[index] = new DLList<E>();
        }
        table[index].add(data);
    }

    public String toString(){
        String str = "";
        for (int i = 0; i < table.length; i++){
            DLList<E> bucket = table[i];
            if (bucket == null || bucket.size() == 0) 
            	continue;
            for (int j = 0; j < bucket.size(); j++){
                str += bucket.get(j).toString();
                str += ("\n");
            }
        }
        return str;
    }
	
	public DLList<E> get(int index){
		return table[index];
	}

	public boolean contains(E e) {
        int index = e.hashCode() % table.length;
        if (table[index] == null)
            return false;
        return table[index].contains(e);
    }

	public void remove(E e){
        int index = e.hashCode() % table.length;
        if (table[index] == null) return;
        if (table[index].contains(e))
            table[index].remove(e);
    }

	public int size(){
		return size;
	}

	public int total(){
		int total = 0;
		for (int i = 0; i < table.length; i++){
            DLList<E> bucket = table[i];
            if (bucket == null || bucket.size() == 0) 
            	continue;
            for (int j = 0; j < bucket.size(); j++){
                total += 1;
            }
        }
        return total;
	}
	
}
