public class DLList<E>{
	private Node<E> dummy;
	private int size; 

	public DLList(){
		dummy = new Node<E>(null);
		dummy.setNext(dummy);
		dummy.setPrev(dummy);
		size = 0;
	}

	public int size(){
		return size;
	}

	public E get(int loc){
		if(loc < size && loc >= 0){
			Node<E> current = dummy.next();
			for(int i = 0; i < loc; i++){
				current = current.next();
			}
			return current.get();
		} 
		else{
			return null;
		}
	}

	public void add(E data){
		Node<E> newNode = new Node<E>(data);
		newNode.setPrev(dummy.prev());
		dummy.prev().setNext(newNode);
		dummy.setPrev(newNode);
		newNode.setNext(dummy);
		size++;
	}

	public void add(int loc, E data){
		Node<E> current = dummy.next();
		if(loc == size){
			add(data);
			return;
		}
		for(int i = 0; i < loc; i++){
			current = current.next();
		}
		Node<E> newNode = new Node<E>(data);
		Node<E> prev = current.prev();
		prev.setNext(newNode);
		current.setPrev(newNode);
		newNode.setNext(current);
		newNode.setPrev(prev);
		size++;
	}

	public void remove(E data) {
    	Node<E> current = this.dummy;
    	for (int i = 0; i < this.size; i++) {
      		current = current.next();
      		if (current.get().equals(data)) {
       			Node<E> prev = current.prev();
        		Node<E> next = current.next();
        		prev.setNext(next);
        		next.setPrev(prev);
        		this.size--;
        		return;
      		}
    	}	
  	}

  	public boolean contains(E data) {
  		Node<E> current = dummy.next();
    	for(int i=0; i<size; i++) {
    		if(current.get().equals(data)) {
      		return true;
      		}
      		current = current.next();
    	}
    	return false;
  	}

	public void remove(int loc) {
   		Node<E> current = dummy.next();
    	for (int i = 0; i < loc; i++){
      		current = current.next();	
    	}
    	Node<E> prev = current.prev();
        Node<E> next = current.next();
       	prev.setNext(next);
  		next.setPrev(prev);
        size--;
  	}

	public void set(int loc, E data){
		remove(loc);
		add(loc, data);
	}

	public E pop(){
		E get = get(0);
		remove(0);
		return get;
	}

	public String toString(){
  		String str = "[";
    	int i;
    	for(i = 0; i < size-1; i++){
    		str += get(i) + ", ";
    	}
    	str += get(i) + "]";
   		return str;
  	}

}