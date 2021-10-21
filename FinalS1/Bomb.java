import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.awt.image.BufferedImage;

public class Bomb implements Serializable {
	
	private int x, y;
	
	public void draw(Graphics g, int x, int y, BufferedImage icon) {
		g.drawImage(icon, x, y, null);
	}
	
	public GridSpace getGridSpace() {
		return new GridSpace(x, y);
	}
	
	public void use(GridSpace loc) {
		x = loc.getX();
		y = loc.getY();
	}

}
