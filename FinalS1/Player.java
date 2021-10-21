import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Player implements Serializable {
	
	private Color color;
	private int x, y;
	private Stack<Integer> health = new Stack<Integer>();
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private Bomb b = null;
	private boolean moved = false;
	
	public Player(int x, int y, Color color) {
		this.color = color;
		this.x = x;
		this.y = y;
		
		for(int i=0; i<3; i++) {
			health.push(1);
		}
		
		for(int i=0; i<5; i++) {
			bombs.add(new Bomb());
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		g.setColor(color);
		g.fillRect(x, y, 20, 20);
		g.setColor(Color.black);
		g.fillOval(x-1, y+18, 10, 10);
		g.fillOval(x+11, y+18, 10, 10);
		
	}
	
	public GridSpace getGridSpace() {
		return new GridSpace(x, y);
	}
	
	public void setGridSpace(GridSpace gs) {
		x = gs.getX();
		y = gs.getY();
	}
	
	public Stack<Integer> getHealth() {
		return health;
	}
	
	public void hit() {
		health.pop();
		x = 6;
		y = 6;
		
	}
	
	public void moved() {
		moved = true;
	}
	
	public boolean getMoved() {
		return moved;
	}
	
	public String getPlayer() {
		if(color.equals(Color.orange)) 
			return "p1"; 
		else 
			return "p2";
	}
	
	public Bomb useBomb(GridSpace loc) {
		bombs.get(bombs.size()-1).use(loc);
		Bomb b = bombs.remove(bombs.size()-1);
		this.b = b;
		return b;
	}
	
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}
	
	public Bomb getUsed() {
		return b;
	}
	
	public void reset() {
		bombs.clear();
		for(int i=0; i<5; i++) {
			bombs.add(new Bomb());
		}
		
		health.clear();
		for(int i=0; i<3; i++) {
			health.push(1);
		}

		moved = false;
	}

}
