import java.awt.*;

import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Player implements Runnable {
	
	private int x, y;
	private int firstX, firstY;
	private int xStep, yStep; 
	private int diam;
	private int width, height;
	private DLList<Projectile> projectiles;
	private DLList<Thread> pThreads;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		xStep = 25;
		yStep = 25; 
		diam = 25; 
		this.width = 50;
        this.height = 50;
		firstX = x;
		firstY = y;
		projectiles = new DLList<Projectile>();
		pThreads = new DLList<Thread>();
	}
	
	public void draw(Graphics g) {
		try {
			BufferedImage bImage = ImageIO.read(new File("player.png"));
			g.drawImage(bImage, x, y, width, height, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0; i<projectiles.size(); i++) {
			projectiles.get(i).draw(g);
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void move(String dir) {
		if(dir.equals("up") && y - yStep > 0) {
			y += -12;
		} else if(dir.equals("down") && y + yStep < 800-diam) {
			y += 12;
		} else if(dir.equals("left") && x - xStep > 0) {
			x -= xStep;
		} else if(dir.equals("right") && x + xStep < 800-diam) {
			x += xStep;
		} else if(dir.equals("shot")) {
			shoot();
		}
	}
	
	public void run() {
		while(true) {
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			for(int i=0; i<projectiles.size(); i++) {
				if(projectiles.get(i).getX() - projectiles.get(i).getWidth()/2 > 800 || projectiles.get(i).getX() + projectiles.get(i).getWidth()/2 < 0) {
					projectiles.remove(i);
					pThreads.remove(i);
					i--;
				}
			}
		}
	}
	
	public boolean hit(Enemy e) {
		float circleX = x + diam/2;
		float circleY = y + diam/2;
		float enemyX = e.getX() + e.getDiam()/2;
		float enemyY = e.getY() + e.getDiam()/2;
		float dropXDist = enemyX - circleX;
	    float dropYDist = enemyY - circleY;
	    float dropDist = (float) Math.sqrt((dropXDist*dropXDist)+(dropYDist*dropYDist));
	    if(dropDist <= diam){
	    	x = firstX;
	    	y = firstY;
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	public void reset() {
		x = firstX;
		y = firstY;
	}
	
	public void shoot() {
		Projectile p1 = new Projectile(x, y + diam/2, -1);
		Projectile p2 = new Projectile(x + diam * 2, y + diam/2, -1);
		projectiles.add(p1);
		projectiles.add(p2);
		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		t1.start();
		t2.start();
		pThreads.add(t1);
		pThreads.add(t2);
		
		try {
    		URL url = this.getClass().getClassLoader().getResource("shoot.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
           exc.printStackTrace(System.out);
        }
	}
	
	public DLList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public DLList<Thread> getProjectileThreads() {
		return pThreads;
	}

}
