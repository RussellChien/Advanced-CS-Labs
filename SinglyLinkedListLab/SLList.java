public class SLList<E>{
	
	private Node<E> head;
	private int size;
	
	public SLList(){
		head = null;
		size = 0;
	}
	
	public E get(int index){
		Node<E> current = head;
		int i = 0;
		while(current != null){
			if(i == index){
				return current.get();
			}
			current = current.next();
			i++;
		}
		return null;
	}
	
	public void add(E data){
		if(head == null){
			head = new Node<E>(data);
		} 
		else{
			Node<E> current = head;
			while(current.next() != null){
				current = current.next();
			}
			current.setNext(new Node<E>(data));
		}
		size++;
	}
	
	public String toString(){
		String list = "[";
		Node<E> current = head;
		while(current != null){
			list += current.get() + ", ";
			current = current.next();
		}
		list += "]";
		return list;
	}
	
	public void remove(E data){
		if(head != null){
			if(head.get().equals(data)){
				head = head.next();
				size--;
				return;
			}
			Node<E> current = head;
			Node<E> previous = current;
			while(current != null){
				if(current.get().equals(data)){
					previous.setNext(current.next());
					size--;
					return;
				}
				previous = current;
				current = current.next();
			}
		}
	}
	
	public int size(){
		return size;
	}
	
	public void set(int index, E data){
		Node<E> current = head;
	    Node<E> previous = current;
	    Node<E> newNode = new Node<E>(data);
	    if(index == 0){
	    	newNode.setNext(current.next());
	    	head = newNode;
	    	return;
	    }
	    int i = 0;     
	    while(current != null){
	      	if(i == index){
	        	previous.setNext(new Node<E>(data));
	            previous.next().setNext(current.next());
	        }
	      	previous = current;
	      	current = current.next();
	        i++;
	    }
	}

	public boolean contains(E data){
	    Node<E> current = head;
	    while(current != null){
	    	if(current.get().equals(data)){
	        	return true;
	        }
	        current = current.next();
	    }
	    return false;
	}
	
	public int indexOf(E data){
    	int index = -1;
    	int i = 0;
    	Node<E> current = head;
    	while(current != null){
    		if(current.get().equals(data)){
	        	index = i;
	        	break;
	        } 
	        else{
	        	i++;
	        	current = current.next();
	        }
	    }
    	return index;
    }
}