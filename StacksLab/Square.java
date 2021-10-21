import java.awt.Graphics;
import java.awt.Color;

public class Square{
	private int red;
	private int green;
	private int blue;
	private int size = 40;

	public Square(){}
	
	public Square(int r, int g, int b){
		red = r;
		green = g;
		blue = b;
	}

	public void drawMe(Graphics g, int x, int y){
		//draw the filled box
		g.setColor(new Color(red, green, blue));
		g.fillRect(x,y,size,size);

		//draw the borders
		g.setColor(Color.black);
		g.drawRect(x,y,size,size);

	}

	public void changeColor(int r, int g, int b){
		red = r;
		green = g;
		blue = b;
	}

	public int getR(){
		return red;
	}

	public int getG(){
		return green;
	}

	public int getB(){
		return blue;
	}

	public int getSize(){
		return size;
	}

	public void reset(){
		changeColor(255,255,255);
	}

}
