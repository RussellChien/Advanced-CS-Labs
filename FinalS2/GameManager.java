public class GameManager {
	
	private DLList<ServerThread> serverThreads = new DLList<ServerThread>();
	private int numPlayers = 0;
	
	public void remove(ServerThread thread) {
		serverThreads.remove(thread);
	}
	
	public void add(ServerThread thread) {
		serverThreads.add(thread);
	}
	
	public synchronized void broadcast(String message) {
		System.out.println("Broadcasting " + message);
		for(int i=0; i<serverThreads.size(); i++) {
			serverThreads.get(i).sendMessage(message);
		}
	}
	
	public synchronized void broadcastExc(String message, ServerThread thread) {
		for(int i=0; i<serverThreads.size(); i++) {
			if(!thread.equals(serverThreads.get(i))) {
				serverThreads.get(i).sendMessage(message);
			}
		}
	}
	
	public synchronized void startSignal(ServerThread thread) {
		for(int i=0; i<serverThreads.size(); i++) {
			if(!thread.equals(serverThreads.get(i))) {
				serverThreads.get(i).sendMessage("start man");
			}
		}
	}
	
	public void newPlayer() {
		numPlayers++;
	}
	
	public synchronized int getPlayers() {
		return numPlayers;
	}

}
