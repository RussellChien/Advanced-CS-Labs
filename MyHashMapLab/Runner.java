import javax.swing.JFrame;

public class Runner {
	
	public static void main(String[] args) {
	
		JFrame frame = new JFrame("Travel Catalog");
		Screen sc = new Screen();
		
		frame.add(sc);
		frame.pack();
		frame.setVisible(true);
	}
}