import java.io.Serializable;

public class GridSpace implements Serializable{
	
	private int x, y;
	
	public GridSpace(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Object obj) {
		GridSpace gs = (GridSpace) obj;
		if(gs.getX()==x && gs.getY()==y) 
			return true;
		//else
		return false;
	}
	
	public int hashCode() {
		return (x * 11 + y * 17)/3;
	}

}