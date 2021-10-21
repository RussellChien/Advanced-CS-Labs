import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Square {
	
	private int x, y;
	
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void drawPlayer(Player player, Graphics g, String p) {
		if(!player.getMoved()) {
			if(p.equals("p1")) 
				player.draw(g, x+5, y+5);
			else if(p.equals("p2")) 
				player.draw(g, x+20, y+5);
		}
		else 
			player.draw(g, x+15, y+5);
	}
	
	public void drawGold(Gold gold, Graphics g, BufferedImage icon) {
		gold.draw(g, x+13, y+13, icon);
	}
	
	public void drawLava(Lava lava, Graphics g, BufferedImage icon) {
		lava.draw(g, x, y, icon);
	}
	
	public void drawBomb(Bomb bomb, Graphics g, BufferedImage icon) {
		bomb.draw(g, x+10, y+5, icon);
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(211, 211, 211));
		g.fillRect(x, y, 40, 40);
		g.setColor(Color.black);
		g.drawRect(x, y, 40, 40);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
