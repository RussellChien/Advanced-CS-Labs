import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;


public abstract class Employee implements ActionListener {
	
	private String name;
	private String profile;
	private String job;
	private int xPos;
	private JButton delete;
	private boolean hide = false;
	
	public Employee(String name, String profile, String job, int xPos) {
		this.name = name;
		this.profile = profile;
		this.job = job;
		this.xPos = xPos;
	}
	
	public abstract double getSalary();
	
	public void drawPhoto(Graphics g, int x, int y, JPanel screen) throws IOException {
		
		BufferedImage image = ImageIO.read(new File(profile));
		g.drawImage(image, x, y, null);
		g.setColor(Color.black);
		g.drawString(toString(), x-10, y-20);

		screen.setLayout(null);

		delete = new JButton("Delete");
		delete.setBounds(x, y+image.getHeight()+10, 50, 20);
		delete.addActionListener(this);
		screen.add(delete);
		
	}

	public void clearButton() {
		//delete.hide();
		delete.setVisible(false);
	}
	
	public String getJob() {
		return job;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public String toString() {
		return name + " " + job + " " + getSalary() + " ";
	}

	public boolean getHide() {
		return hide;
	}

	public String getName() {
		return name;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==delete) {
			hide = true;
			System.out.println("delete");
		}
	}

}

