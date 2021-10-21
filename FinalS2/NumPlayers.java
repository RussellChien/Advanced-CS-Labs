import java.io.*;

public class NumPlayers implements Runnable {
	
	private GameManager m;
	private PrintWriter out;
	private int numPlayers;

	public NumPlayers(GameManager m, PrintWriter out) {
		this.m = m;
		numPlayers = m.getPlayers();
		this.out = out;
	}
	
	public void run() {
		
		while(true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(m.getPlayers() != numPlayers) {
				numPlayers = m.getPlayers();
				out.println(numPlayers);
			}
		}
	}
	
}
