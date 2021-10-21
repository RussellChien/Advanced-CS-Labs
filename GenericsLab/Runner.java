import javax.swing.JFrame;

public class Runner{
	
	public static void main(String[] args) {

		JFrame frame = new JFrame("Student Manager");
		frame.setBounds(0, 0, 800, 400);
		
		Screen sc = new Screen();
		frame.add(sc);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
}