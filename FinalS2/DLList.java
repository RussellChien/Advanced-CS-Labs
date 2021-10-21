public class DLList<E> {
	
	private Node<E> dummy;
	private int size = 0;
	
	public DLList() {
		dummy = new Node<E>(null);
		dummy.setNext(dummy);
		dummy.setPrev(dummy);
	}
	
	public void add(int index, E data) {
		Node<E> current = dummy.next();
		
		if(index == size) {
			add(data);
			return;
		}
		
		for(int i=0; i<index; i++) {
			current = current.next();
		}
		
		Node<E> newNode = new Node<E>(data);
		
		Node<E> before = current.prev();
		before.setNext(newNode);
		current.setPrev(newNode);
		newNode.setNext(current);
		newNode.setPrev(before);
		
		size++;
		
	}
	
	public void add(E data) {
		Node<E> newNode = new Node<E>(data);

		newNode.setPrev(dummy.prev());
		dummy.prev().setNext(newNode);
		dummy.setPrev(newNode);
		newNode.setNext(dummy);
		
		size++;
	}
	
	public void remove(int index) {
		Node<E> current = dummy.next();
		
		for(int i=0; i<index; i++) {
			current = current.next();
		}
		
		Node<E> before = current.prev();
		Node<E> after = current.next();
		before.setNext(after);
		after.setPrev(before);
		
		size--;
	}
	
	public void set(int index, E data) {
		remove(index);
		add(index, data);
	}
	
	public E get(int index) {
		if(index < size && index >= 0) {
			Node<E> current = dummy.next();
			
			for(int i=0; i<index; i++) {
				current = current.next();
			}
			
			return current.get();
		} else {
			return null;
		}
	}
	
	public E pop() {
		E get = get(0);
		remove(0);
		return get;
	}
	
	public int size() {
		return size;
	}
	
	  public void remove(E data){
		  	Node<E> current = dummy.next();
		    for(int i = 0; i < size; i++){
		    	if(current.get().equals(data)){
		      	current.prev().setNext(current.next());
		      	current.next().setPrev(current.prev());
		        size--;
		        break;
		      }
		      current = current.next();
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

	  public String toString() {
		  	String str = "[";
		    int i;
		    for(i=0; i<size-1; i++) {
		    	str += get(i) + ", ";
		    }
		    str += get(i) + "]";
		    
		    return str;
		 }



}
