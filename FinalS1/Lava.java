import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Lava implements Serializable{

	private int x, y;
	
	public Lava(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g, int x, int y, BufferedImage icon) {
	    g.drawImage(icon, x, y, null);
	}
	
	public GridSpace getGridSpace() {
		return new GridSpace(x, y);
	}
	
	public void setGridSpace(GridSpace gs) {
		x = gs.getX();
		y = gs.getY();
	}
	
}
