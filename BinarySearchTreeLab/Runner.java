import javax.swing.JFrame;
public class Runner {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Account Manager");
		Screen sc = new Screen();
		frame.add(sc);
		frame.pack();
		frame.setVisible(true);
	}
}