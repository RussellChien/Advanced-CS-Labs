import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientScreen extends JPanel implements MouseListener, ActionListener{
	
	
    private ObjectOutputStream out;
    private Game game = new Game();
    private JLabel[] boxes = new JLabel[9];
    private JLabel winner, playAgainst;
    private JButton ai, player;
    private boolean clicked = false;
    private int score;
    private JButton restart;
     
    public ClientScreen() {
         
        this.setLayout(null);
        this.addMouseListener(this);
        
        playAgainst = new JLabel("Play against:");
        playAgainst.setFont(new Font("Helvetica", Font.PLAIN, 90));
        playAgainst.setBounds(100, 150, 800, 100);
        this.add(playAgainst);
        
        ai = new JButton("AI");
        ai.setBounds(200, 300, 200, 100);
        ai.addActionListener(this);
        this.add(ai);
        
        player = new JButton("Player");
        player.setBounds(410, 300, 200, 100);
        player.addActionListener(this);
        this.add(player);
        
        winner = new JLabel();
        winner.setFont(new Font("Helvetica", Font.PLAIN, 100));
        winner.setBounds(200, 300, 500, 100);
        this.add(winner);

        restart = new JButton("Restart");
        restart.setBounds(400, 700, 100, 50);
        restart.addActionListener(this);
        this.add(restart);
        restart.setVisible(false);

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
        g.drawString("Player 1", 400, 50);
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
    	String hostName = "localhost";
        int portNumber = 1024;
         
        Socket serverSocket = new Socket(hostName, portNumber);
        out = new ObjectOutputStream(serverSocket.getOutputStream());
        ObjectInputStream ino = new ObjectInputStream(serverSocket.getInputStream());
        PushbackInputStream pin = new PushbackInputStream(serverSocket.getInputStream());
        
        while(true) {
        	try {
        		if(pin.available()!=0) {
    				game = (Game) ino.readObject();
    				drawBoard();
    				gameWon();
                    int[] gameState = game.getGameState();
                    for(int i=0; i<boxes.length; i++) {
                        System.out.print(gameState[i] + " ");
                    }
                    System.out.println();
                    System.out.println("Recieved Object ");
        		} 
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
     
    }
    
    public void gameWon() {
    	
    	int winner = game.gameWon();
    	
    	if(winner!=-1) {
            restart.setVisible(true);
    		if(winner==0) {
    			this.winner.setForeground(Color.green);
    			this.winner.setText("You win");
    			playSound("win");
                score++;
    		} else if(winner==1) {
    			this.winner.setForeground(Color.red);
    			this.winner.setText("You lose");
    			playSound("lose");
    		} else if(winner==2) {
    			this.winner.setForeground(Color.orange);
    			this.winner.setText("It's a tie");
    			playSound("tie");
    		}
    	}
    	
    }
    
    public void drawBoard() {
    	int[] gameState = game.getGameState();
    	for(int i=0; i<boxes.length; i++) {
    		if(gameState[i]==1) boxes[i].setText("O");
    	}
        repaint();
    }
    
    public void resetBoard(){
        for(int i=0; i<boxes.length; i++) {
            boxes[i].setText("");
        }
        this.winner.setText("");
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
           // exc.printStackTrace(System.out);
        }
    }

    public void actionPerformed(ActionEvent e) {
    	try {
	    	if(e.getSource() == ai) {
				out.writeObject("ai");
                player.setVisible(false);
                ai.setVisible(false);
                playAgainst.setVisible(false);
                clicked = true;
	    	} else if(e.getSource() == player) {
	    		out.writeObject("player");
                player.setVisible(false);
                ai.setVisible(false);
                playAgainst.setVisible(false);
                clicked = true;
	    	} else if(e.getSource() == restart){
                out.writeObject("new game");
                game = new Game();
                resetBoard();
                gameWon();
                player.setVisible(true);
                ai.setVisible(true);
                playAgainst.setVisible(true);
                clicked = false;
                repaint();

            }
    	} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		
    }

	public void mouseClicked(MouseEvent e) {
		if(clicked && game.getActivePlayer() == 0 && game.getGameActive()) {
			int x = e.getX();
			int y = e.getY();
            //System.out.println("x: " + x + " y: " + y);
			
			int checkX = (x-100)/200;
			int checkY = (y-100)/200;
            //System.out.println("checkx: " + checkX + " checky: " + checkY);
			
			if(checkX>=0 && checkX<=2 && checkY>=0 && checkY<=2) {
				if(game.place(checkX + 3*checkY)) {
					boxes[checkX + 3*checkY].setText("X");
                    playSound("x");
					gameWon();
					try {
						out.reset();
						out.writeObject(game);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }

	
}

