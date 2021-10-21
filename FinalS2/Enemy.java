import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Enemy implements Runnable {
	
	private int x, y;
	private double xVel, yVel;
	private int diam;
	private BufferedImage image;
	private boolean boss;
	private int lives;
	
	public Enemy(int x, int y, double xVel, double yVel, boolean boss) {
		this.x = x;
		this.y = y;
		this.xVel = xVel;
		this.yVel = yVel;
		this.boss = boss;
		diam = 35;
		if(boss) {
			diam *= 4;
			lives = 5;
		}
		try {
			image = ImageIO.read(new File("enemy.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			x += xVel;
			y += yVel;
			if(x + diam > 1000 || x < 0) {
				xVel *= -1;
				x += xVel;
			}
			if(y + diam > 650 || y < 0) {
				yVel *= -1;
				y += yVel;
			}
		}
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, x, y, diam, diam, null);
		if(boss) {
			for(int i=0; i<5; i++) {
				if(i<lives) {
					if(lives <= 2) {
						g.setColor(Color.red);
					} else if(lives <= 4) {
						g.setColor(Color.yellow);
					} else if(lives == 5) {
						g.setColor(Color.green);
					}
					g.fillRect(x + i * 25 + 7, y + diam * 3/4, 20, 15);
				} else {
					g.setColor(Color.black);
					g.drawRect(x + i * 25 + 7, y + diam * 3/4, 20, 15);
				}
			}
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDiam() {
		return diam;
	}
	
	public boolean hit(Projectile p) {
		if(p.getX() + p.getWidth()/2 >= x && p.getX() - p.getWidth()/2 <= x + diam && p.getY() + p.getHeight()/2 >= y && p.getY() - p.getHeight()/1 <= y + diam) {
			if(boss) lives--;
			return true;
		}
		return false;
	}
	
	public boolean boss() {
		return boss;
	}
	
}
