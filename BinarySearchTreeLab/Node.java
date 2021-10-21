public class Node<E> {
	private E data;
	private Node<E> left;
	private Node<E> right;

	public Node(E data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}

	public E get() {
		return data;
	}

	public Node<E> left() {
		return left;
	}

	public Node<E> right() {
		return right;
	}

	public void setLeft(Node<E> left) {
		this.left = left;
	}

	public void setRight(Node<E> right) {
		this.right = right;
	}

	public void setData(E data){
		this.data = data;
	}
}