import java.io.Serializable;
import java.util.HashMap;

public class Game implements Serializable {
	
	private HashMap<GridSpace, Object> gameMap;
	private Player player;
	
	public Game(HashMap<GridSpace, Object> gameMap, Player player) {
		this.gameMap = gameMap;
		this.player = player;
	}

	public HashMap<GridSpace, Object> getGameMap() {
		return gameMap;
	}

	public void setGameMap(HashMap<GridSpace, Object> gameMap) {
		this.gameMap = gameMap;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
