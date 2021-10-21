import java.io.IOException;
import javax.swing.JFrame;
public class Client {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Player 2");
        ClientScreen sc = new ClientScreen("localhost");

        frame.add(sc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
 		System.out.println("Player 2 connected.");

        try {
			sc.playGame();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
