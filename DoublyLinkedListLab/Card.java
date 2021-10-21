import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Card{
	private int value;
	private int suit;
	private BufferedImage hearts;
	private BufferedImage spades;
	private BufferedImage diamonds;
	private BufferedImage clubs;
	private boolean clicked;
	private int width, height, x, y;
	// 0 = spades, 1 = hearts, 2 = clubs, 3 = diamonds

	public Card(int value, int suit){
		this.value = value;
		this.suit = suit;
		clicked = false;
		width = 120;
		height = 150;

		if(suit == 0){
			try{
				spades = ImageIO.read(new File("images/spades.png"));
			} catch ( IOException e){
				System.out.println(e);
			}
		}
		else if(suit == 1){
			try{
				hearts = ImageIO.read(new File("images/hearts.png"));
			} catch ( IOException e){
				System.out.println(e);
			}			
		}
		else if(suit == 2){
			try{
				clubs = ImageIO.read(new File("images/clubs.png"));
			} catch ( IOException e){
				System.out.println(e);
			}
		}
		else if(suit == 3){
			try{
				diamonds = ImageIO.read(new File("images/diamonds.png"));
			} catch ( IOException e){
				System.out.println(e);
			}	
		}
	}

	public int getValue() {
		return value;
	}
	
	public int getSuit() {
		return suit;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void drawMe(Graphics g, int x, int y){
		this.x = x;
		this.y = y;

		if(clicked){
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			g.drawString("Hold", x+25, y-25);
		}

		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);

		Font font = new Font("TimesRoman", Font.PLAIN, 50);
		g.setFont(font);
		if(suit == 0){
			g.setColor(Color.black);
			if(value != 1 && value <= 10){
				g.drawString("" + value, x + 30, y + 100);
			}
			else if(value == 11){
				g.drawString("J", x + 30, y + 100);
			}
			else if(value == 12){
				g.drawString("Q", x + 30, y + 100);
			}
			else if(value == 13){
				g.drawString("K", x + 30, y + 100);
			}
			else if(value == 14 || value == 1){
				g.drawString("A", x + 30, y + 100);
			}
			g.drawImage(spades, x+2 , y, null);
		}
		else if(suit == 1){
			g.setColor(Color.red);
			if(value != 1 && value <= 10){
				g.drawString("" + value, x + 30, y + 100);
			}
			else if(value == 11){
				g.drawString("J", x + 30, y + 100);
			}
			else if(value == 12){
				g.drawString("Q", x + 30, y + 100);
			}
			else if(value == 13){
				g.drawString("K", x + 30, y + 100);
			}
			else if(value == 14 || value == 1){
				g.drawString("A", x + 30, y + 100);
			}
			g.drawImage(hearts, x+2 , y, null);
		}
		else if(suit == 2){
			g.setColor(Color.black);
			if(value != 1 && value <= 10){
				g.drawString("" + value, x + 30, y + 100);
			}
			else if(value == 11){
				g.drawString("J", x + 30, y + 100);
			}
			else if(value == 12){
				g.drawString("Q", x + 30, y + 100);
			}
			else if(value == 13){
				g.drawString("K", x + 30, y + 100);
			}
			else if(value == 14 || value == 1){
				g.drawString("A", x + 30, y + 100);
			}
			g.drawImage(clubs, x+2 , y, null);
		}
		else if(suit == 3){
			g.setColor(Color.red);
			if(value != 1 && value <= 10){
				g.drawString("" + value, x + 30, y + 100);
			}
			else if(value == 11){
				g.drawString("J", x + 30, y + 100);
			}
			else if(value == 12){
				g.drawString("Q", x + 30, y + 100);
			}
			else if(value == 13){
				g.drawString("K", x + 30, y + 100);
			}
			else if(value == 14 || value == 1){
				g.drawString("A", x + 30, y + 100);
			}
			g.drawImage(diamonds, x+2 , y, null);
		}
	}

	public void checkClicked(int x, int y){
		if(x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height){
			if(clicked){
				clicked = false;
			}
			else{
				clicked = true;
			}
		}
	}
	
	public boolean getClicked(){
		return clicked;
	}
	
	public void unclicked(){
		clicked = false;
	}

}