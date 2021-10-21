import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private GameManager m;
	private int index;
	
	public ServerThread(Socket clientSocket, int index, GameManager m){
		this.clientSocket = clientSocket;
		this.index = index;
		this.m = m;
	}

	public void run(){
		System.out.println(Thread.currentThread().getName() + ": connection opened.");
		try{
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			out.println("Connection Successful!");
			out.println(index);

			starting();
			
			while(true) {
				String input = in.readLine();
				if(input.equals("restart send")) {
					m.broadcast("restart");
				} else if(input.equals("restart")) {
					starting();
				} else {
					m.broadcastExc(input, this);
				}
			}
			
		    
	    } catch (IOException ex){
	      System.out.println("Error listening for a connection");
	      System.out.println(ex.getMessage());
	    }
	}
	
	public void starting() throws IOException {
		NumPlayers np = new NumPlayers(m, out);
		Thread npThread = new Thread(np);
		npThread.start();
		
		String start = in.readLine();
		np = null;
		npThread = null;
		if(start.equals("start send")) {
			out.println(m.getPlayers() + "");
			m.startSignal(this);
			String result = in.readLine();
			m.broadcast(result);
		} else if(start.equals("start gip")) {
			System.out.println("Sending " + m.getPlayers());
			String result = in.readLine();
			m.broadcast(result);
		}
	}
	
	public void sendMessage(String s) {
		out.println(s);
	}
}

