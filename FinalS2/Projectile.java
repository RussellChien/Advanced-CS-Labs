import java.awt.*;

public class Projectile implements Runnable {
	
	private int x, y;
	private int yVel;
	private int height, width;
	
	public Projectile(int x, int y, int yVel) {
		this.x = x;
		this.y = y;
		this.yVel = yVel;
		height = 10;
		width = 10;
	}
	
	public void draw(Graphics g) {
		
		g.setColor(Color.red);
		g.fillRect(x - width/2, y - height/2, width, height);
		
	}
	
	public void run() {
		while(true) {
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			y += yVel;
			if(y > 800 + height/2) {
				break;
			}
			
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

}
