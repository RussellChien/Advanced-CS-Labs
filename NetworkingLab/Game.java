import java.io.Serializable;

public class Game implements Serializable {

	int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // 2 = none, 0 = X, 1 = O
	int[] reset = {2, 2, 2, 2, 2, 2, 2, 2, 2}; 
	int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7 ,8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
	int activePlayer = 0;
	boolean gameActive = true;
	
	public Game() { 
		this.gameState = reset;
	}
	
	public Game(int[] gameState) { 
		this.gameState = gameState;
	}
	
	public boolean place(int pos) {
		if(gameState[pos] == 2 && gameActive) {

            gameState[pos] = activePlayer;

            if(activePlayer == 0) {
                activePlayer = 1;
            } else {
                activePlayer = 0;
            }
            for(int i=0; i<gameState.length; i++) {
            	System.out.print(gameState[i] + " ");
            }
            
            return true;
		}
		return false;
	}
	
	public void checkPlace(int pos, int p) {
        gameState[pos] = p;

	}
	
	public int gameWon() {
		//-1 is no win, 0 is X, 1 is O, and 2 is tie
		
        for(int[] winningPosition : winningPositions) {
            if(gameState[winningPosition[0]] != 2 &&
                    gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]]) {

                // Someone has won

                gameActive = false;

                if(gameState[winningPosition[0]]==0) {
                    return 0;
                } else {
                	return 1;
                }

            }
        }
        
        boolean gameIsOver = true;

        for(int counterState : gameState) {
            if(counterState == 2) gameIsOver = false;
        }

        if(gameIsOver) {
        	gameActive = false;
        	return 2;
        }
        
        return -1;
	}

	
	public int getActivePlayer() {
		return activePlayer;
	}
	
	public int[] getGameState() {
		return gameState;
	}
	
	public boolean getGameActive() {
		return gameActive;
	}
}
