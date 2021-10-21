import java.io.IOException;
import javax.swing.JFrame;
public class Server {
        public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Player 1"); 
                ServerScreen sc = new ServerScreen();
                frame.add(sc);
 
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                System.out.println("Player 1 connected.");

                sc.playGame();
	}
}