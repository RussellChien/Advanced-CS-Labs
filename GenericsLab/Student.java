import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import javax.imageio.ImageIO;

public class Student{
	private String name;
	private String imageFile;
	public Student(String name, String imageFile){
		this.name = name;
		this.imageFile = imageFile;
	}
	public void drawStudent(Graphics g, int x, int y) {
		
		try {
			BufferedImage image = ImageIO.read(new File(imageFile));
			g.drawImage(image, x, y, null);
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.BOLD, 20));
			g.drawString(name, x, y-15);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String toString() {
		return name;
	}

}