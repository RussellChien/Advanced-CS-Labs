public class BinarySearchTree<E extends Comparable<E>> {
	private Node<E> root;
	private int passes; 
	private String string;

	public BinarySearchTree() {
		this.root = null;
		passes = 0;
	}

	public void add(E data) {
		passes = 0;
		if(root != null) {
			add(data, root);
		} else {
			root = new Node<E>(data);
		}
	}

	private void add(E data, Node<E> node) {
		passes++;
		if(data.compareTo(node.get()) > 0) {
			if(node.right() != null) {
				add(data, node.right());
			} else {
				node.setRight(new Node<E>(data));
			}
		} else if(data.compareTo(node.get()) < 0) {
			if(node.left() != null) {
				add(data, node.left());
			} else {
				node.setLeft(new Node<E>(data));
			}
		}
	}

	public E get(E data) {
		passes = 0;
		return get(data, root);
	}

	private E get(E data, Node<E> node) {
		passes++; 
		if(data.equals(node.get())) {
			return node.get();
		} else if (data.compareTo(node.get()) < 0) {
			if(node.left() != null) {
				return get(data, node.left());
			} else {
				return null;
			}
		} else {
			if(node.right() != null) {
				return get(data, node.right());
			} else {
				return null;
			}
		}
	}

	public void remove(E data) {
		remove(data, root, null);
	}

	public void remove(E data, Node<E> node, Node<E> parent) {
		if(data.equals(node.get())) {
			if(node.left() == null && node.right() == null) {
				if(parent == null) {
					root = null;
				} else {
					if(parent.right().equals(node)) {
						parent.setRight(null);
					} else {
						parent.setLeft(null);
					}
				}
			} else if(node.right() != null && node.left() != null) {
				Node<E> temp = node.right();
				Node<E> tempParent = node;
				while(temp.left() != null) {
					tempParent = temp;
					temp = temp.left();
				}
				node.setData(temp.get());
				if(node.equals(tempParent)) {
					tempParent.setRight(null);
				} else {
					tempParent.setLeft(null);
				}
			} else if(node.right() != null) {
				if(parent == null) {
					root = node.right();
				} else {
					if(parent.right().equals(node)) {
						parent.setRight(node.right());
					} else {
						parent.setLeft(node.right());
					}
				}
			} else if(node.left() != null) {
				if(parent == null) { 
					root = node.left();
				} else {
					if(parent.right().equals(node)) {
						parent.setRight(node.left());
					}
					else {
						parent.setLeft(node.left());
					}
				}
			}
		} else if(data.compareTo(node.get()) < 0) {
			if(node.left() == null) {
				return;
			} else {
				remove(data, node.left(), node);
			}
		} else {
			if(node.right() == null) {
				return;
			} else {
				remove(data, node.right(), node);
			}
		}
	}

	public String toString() {
		string = "";
		return inOrderString(root);
	}

	public String inOrderString(Node<E> node) {
		if(node != null) {
			inOrderString(node.left());
			string += node.get() + "\n";
			inOrderString(node.right());
		}
		return string;
	}

	public int getPasses() {
		return passes;
	}
}
