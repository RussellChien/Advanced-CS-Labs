import java.io.*;
import java.net.*;

public class Server {
	
	public static void main(String[] args) throws IOException {
		int portNumber = 1024;
		int index = 0;
		ServerSocket serverSocket = new ServerSocket(portNumber);
		GameManager gm = new GameManager();

		while(true){
			System.out.println("Waiting for a connection");

			Socket clientSocket = serverSocket.accept();
			gm.newPlayer();
		
			ServerThread st = new ServerThread(clientSocket, index++, gm);
			gm.add(st);
			Thread thread = new Thread(st);
			thread.start();
		}
	}

}
