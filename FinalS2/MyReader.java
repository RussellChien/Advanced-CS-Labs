import java.io.*;
import java.util.Scanner;

public class MyReader implements Runnable {
	
	private BufferedReader in;
	private int numPlayers;
	
	public MyReader(BufferedReader in) {
		this.in = in;
	}

	public void run() {
		while(true) {
			if(Thread.interrupted()) {
				break;
			}
			try {
				numPlayers = Integer.parseInt(in.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int numPlayers() {
		return numPlayers;
	}
	
}

