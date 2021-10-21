import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.*;
import java.net.*;

public class ServerScreen extends JPanel implements MouseListener{
	
 
    private ObjectOutputStream out;
    private Game game = new Game();
    private JLabel[] boxes = new JLabel[9];
    private JLabel winner;
    private boolean ai = false;
    private int[] bestMoves = {4, 0, 2, 6, 8, 1, 3, 5, 7};
    private int score;
     
    public ServerScreen() {
         
        this.setLayout(null);
        this.addMouseListener(this);
        
        winner = new JLabel();
        winner.setFont(new Font("Helvetica", Font.PLAIN, 100));
        winner.setBounds(200, 300, 500, 100);
        this.add(winner);
        
        int index;
        for(int r=0; r<3; r++) {
        	for(int c=0; c<3; c++) {
        		index = c + r*3;
        		boxes[index] = new JLabel("");
            	boxes[index].setFont(new Font("Helvetica", Font.PLAIN, 160));
            	boxes[index].setBounds(140+c*200, 110+r*200, 200, 200);
            	this.add(boxes[index]);
        	}
        }
        
         
        this.setFocusable(true);
    }
 
 
    public Dimension getPreferredSize() {
        return new Dimension(800,800);
    }
     
    public void paintComponent(Graphics g) {
        
    	super.paintComponent(g);
        g.setFont(new Font("Times Roman", 24, 24));
        g.drawString("Player 2", 400, 50);
        g.drawString("Score: " + score, 500, 50);
    	Graphics2D g2 = (Graphics2D) g;
    	g2.setStroke(new BasicStroke(3));
    	g2.drawLine(300, 100, 300, 700);
    	g2.drawLine(500, 100, 500, 700);
    	g2.drawLine(100, 300, 700, 300);
    	g2.drawLine(100, 500, 700, 500);
        repaint();
 
    } 
 
    public void poll() throws IOException {
        int portNumber = 1024;
         
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream ino = new ObjectInputStream(clientSocket.getInputStream());
        PushbackInputStream pin = new PushbackInputStream(clientSocket.getInputStream());

        while(true) {
        	try {
        		if(pin.available()!=0) {
        			Object o = ino.readObject();
        			if(o instanceof Game) {
        				game = (Game) o;
        				drawBoard();
        				gameWon();
                        repaint();
        				if(ai && game.getActivePlayer()==1) playAI();
        			} else {
        				if(((String) o).equals("ai")) {
        		        	ai = true;
        		        }
                        if(((String) o).equals("new game")) {
                            resetBoard();
                            game = new Game();
                            gameWon();
                            repaint();

                        }
        			}
        		} 
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
     
    }
    
    public void gameWon() {
    	
    	int winner = game.gameWon();

    	if(winner!=-1) {
    		if(winner==0) {
    			this.winner.setForeground(Color.red);
    			this.winner.setText("You lose");
    			playSound("lose");
    		} else if(winner==1) {
    			this.winner.setForeground(Color.green);
    			this.winner.setText("You win");
    			playSound("win");
                score++;
    		} else if(winner==2) {
    			this.winner.setForeground(Color.orange);
    			this.winner.setText("It's a tie");
    			playSound("tie");
    		}
    	}
        repaint();
    	
    }
    
    public void drawBoard() {
    	int[] gameState = game.getGameState();
    	for(int i=0; i<boxes.length; i++) {
    		if(gameState[i]==0) boxes[i].setText("X");
    	}
    }

    public void resetBoard(){
        for(int i=0; i<boxes.length; i++) {
            boxes[i].setText("");
        }
        this.winner.setText("");
        ai = false;
        repaint();
    }

    public void playSound(String name) {
    	try {
    		URL url;
    		if(name.equals("x")) {
                url = this.getClass().getClassLoader().getResource("xclick.wav");
    		} else if(name.equals("o")) {
    			url = this.getClass().getClassLoader().getResource("oclick.wav");
    		} else if(name.equals("lose")) {
    			url = this.getClass().getClassLoader().getResource("lose.wav");
    		} else if(name.equals("tie")) {
    			url = this.getClass().getClassLoader().getResource("tie.wav");
    		} else {
    			url = this.getClass().getClassLoader().getResource("win.wav");
    		}
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
    
     public void playAI() {
        int[] newGameState = new int[9];
        for(int i=0; i<game.getGameState().length; i++) {
            newGameState[i] = game.getGameState()[i];
        }
        Game gameTest = new Game(newGameState);
        
        for(int i=0; i<gameTest.getGameState().length; i++){
            if(gameTest.getGameState()[i] == 2) {
                gameTest.checkPlace(i, 1);
                if(gameTest.gameWon()==1){
                    game.place(i);
                    boxes[i].setText("O");
                    checkGame();
                    return;
                }
                gameTest.checkPlace(i, 2);
            }
        }
        
        for(int i=0; i<gameTest.getGameState().length; i++) {
            if(game.getGameState()[i] == 2) {

                gameTest.checkPlace(i, 0);
                if(gameTest.gameWon()==0) {
                    boxes[i].setText("O");
                    game.place(i);
                    checkGame();
                    //Sending object from 2 because x almost won
                    return;
                }
                gameTest.checkPlace(i, 2);
            }
        }
        
        for(int i=0; i<bestMoves.length; i++) {
            for(int j=0; j<gameTest.getGameState().length; j++) {
                if(gameTest.getGameState()[j] == 2 && j==bestMoves[i]) {
                    game.place(j);
                    boxes[j].setText("O");
                    checkGame();
                    return;
                }
            }
        }
        
    }
    
    
    private void checkGame() {
        System.out.println("Sending object before");
    	gameWon();
    	try {
			out.reset();
			out.writeObject(game);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        System.out.println("Sending object after");
    }

	public void mouseClicked(MouseEvent e) {
		if(!ai && game.getActivePlayer() == 1 && game.getGameActive()) {
			int x = e.getX();
			int y = e.getY();
            //System.out.println("x: " + x + " y: " + y);
			
			int checkX = (x-100)/200;
			int checkY = (y-100)/200;
           //System.out.println("checkx: " + checkX + " checky: " + checkY);
			
			if(checkX>=0 && checkX<=2 && checkY>=0 && checkY<=2) {
				if(game.place(checkX + 3*checkY)) {
					boxes[checkX + 3*checkY].setText("O");
                    playSound("o");
                    gameWon();
					checkGame();
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
}
