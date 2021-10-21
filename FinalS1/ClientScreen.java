import java.awt.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class ClientScreen extends JPanel {

	private ObjectOutputStream out;
	private Game game;
	private int gridSize, p1Score, p2Score, startPosX, startPosY;
	private Square[][] grid;
	private HashMap<GridSpace, Object> gameMap;
	private ArrayList<Gold> p1Items, p2Items;
	private Player p2, p1;
	private JLabel scoreOne, scoreTwo, winner, title, name;
	private JTextArea instructions;
	private boolean connected, gameActive;
	private String ip;
	private BufferedImage bombImg, lavaImg, goldImg, heart, background;
     
    public ClientScreen(String ip) {
         
        this.setLayout(null);
        this.setFocusable(true);

		this.ip = ip;
		gridSize = 13;
		startPosX = 6;
		startPosY = 6;
		grid = new Square[gridSize][gridSize];
		gameMap = new HashMap<GridSpace, Object>();
		p1Items = new ArrayList<Gold>();
		p2Items = new ArrayList<Gold>();
		p2 = new Player(6, 6, Color.gray);
		p1Score = 0;
		p2Score = 0;
		connected = false;
		gameActive = true;
		
		try {
			bombImg = ImageIO.read(new File("bomb.png"));
			lavaImg = ImageIO.read(new File("lava.png"));
			goldImg = ImageIO.read(new File("gold.png"));
			heart = ImageIO.read(new File("heart.png"));
			background = ImageIO.read(new File("background.png"));

		} catch (IOException e) {
            e.printStackTrace();
        }
    	
    	instructions = new JTextArea();
    	instructions.setBounds(100, 620, 800, 40);
    	instructions.setText("   WASD or arrow keys to move. Space to place bomb. Avoid the lava. Get as much gold as possible!   ");
    	instructions.setFont(new Font("Times Roman", Font.PLAIN, 16));
    	instructions.setEditable(false);
    	instructions.setForeground(Color.yellow);
    	instructions.setBackground(Color.black);
    	this.add(instructions);
    	
    	scoreOne = new JLabel("Score: " + p1Score);
    	scoreOne.setFont(new Font("Times Roman", Font.BOLD, 40));
    	scoreOne.setForeground(Color.white);
    	scoreOne.setBounds(30, 70, 200, 60);
    	this.add(scoreOne);
    	
    	scoreTwo = new JLabel("Score: " + p2Score);
    	scoreTwo.setFont(new Font("Times Roman", Font.BOLD, 40));
    	scoreTwo.setForeground(Color.white);
    	scoreTwo.setBounds(800, 70, 200, 60);
    	this.add(scoreTwo);
    	
    	winner = new JLabel();
    	winner.setFont(new Font("Times Roman", Font.BOLD, 70));
    	winner.setBounds(250, 200, 800, 100);
    	this.add(winner);

    	title = new JLabel();
    	title.setFont(new Font("Times Roman", Font.BOLD, 30));
    	title.setBounds(425, 0, 200, 50);
    	title.setBackground(Color.black);
    	title.setForeground(Color.yellow);
    	this.add(title);
    	title.setText("Gold Rush");

    	name = new JLabel();
    	name.setFont(new Font("Times Roman", Font.BOLD, 30));
    	name.setBounds(300, 580, 800, 40);
    	name.setForeground(Color.yellow);
    	this.add(name);
    	name.setText("You are Player 2 (grey)");
    	
    	game = new Game(gameMap, p2);

    	addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    			if(gameActive && connected) {
					int keyCode = e.getKeyCode();
					if(keyCode==KeyEvent.VK_W || keyCode==KeyEvent.VK_UP) {
						if(p2.getGridSpace().getY()>0) {
							p2.setGridSpace(new GridSpace(p2.getGridSpace().getX(), p2.getGridSpace().getY()-1));
							try {
								checkMovement();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					} 
					else if(keyCode==KeyEvent.VK_S || keyCode==KeyEvent.VK_DOWN) {
						if(p2.getGridSpace().getY()<gridSize-1) {
							p2.setGridSpace(new GridSpace(p2.getGridSpace().getX(), p2.getGridSpace().getY()+1));
							try {
								checkMovement();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					} 
					else if(keyCode==KeyEvent.VK_D || keyCode==KeyEvent.VK_RIGHT) {
						if(p2.getGridSpace().getX()<gridSize-1) {
							p2.setGridSpace(new GridSpace(p2.getGridSpace().getX()+1, p2.getGridSpace().getY()));
							try {
								checkMovement();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					} 
					else if(keyCode==KeyEvent.VK_A || keyCode==KeyEvent.VK_LEFT) {
						if(p2.getGridSpace().getX()>0) {
							p2.setGridSpace(new GridSpace(p2.getGridSpace().getX()-1, p2.getGridSpace().getY()));
							try {
								checkMovement();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					} 
					else if(keyCode==KeyEvent.VK_SPACE) {
						if(!p2.getBombs().isEmpty()) {
							gameMap.put(p2.getGridSpace(), p2.useBomb(p2.getGridSpace()));
						}
					}
    			}
    			repaint();
			}
		});
        
        this.setFocusable(true);
    	this.requestFocusInWindow();
    }
 
 
    public Dimension getPreferredSize() {
        return new Dimension(1000, 650);
    }
     
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);

    	g.drawImage(background, 0, 0, null);

    	scoreOne.setText("Score: " + p1Score);
    	scoreTwo.setText("Score: " + p2Score);
    	
       	for(int r=0; r<gridSize; r++) {
    		for(int c=0; c<gridSize; c++) {
    			grid[r][c] = new Square(240 + c*40, 40 + r*40);
    			grid[r][c].draw(g);
    		}
    	}
    	    	
    	Object[] items = gameMap.values().toArray();
		for(Object each : items) {
    		if(each instanceof Gold) {
    			Gold gold = (Gold) each;
    			grid[gold.getGridSpace().getY()][gold.getGridSpace().getX()].drawGold(gold, g, goldImg);
    		} else if(each instanceof Lava) {
    			Lava lava = (Lava) each;
    			grid[lava.getGridSpace().getY()][lava.getGridSpace().getX()].drawLava(lava, g, lavaImg);
    		} else if(each instanceof Bomb) {
    			Bomb bomb = (Bomb) each;
    			grid[bomb.getGridSpace().getY()][bomb.getGridSpace().getX()].drawBomb(bomb, g, bombImg);
    		}
    	}
	        	
    	if(connected)
    		grid[p1.getGridSpace().getY()][p1.getGridSpace().getX()].drawPlayer(p1, g, "p1");
		grid[p2.getGridSpace().getY()][p2.getGridSpace().getX()].drawPlayer(p2, g, "p2");
		
		int c = 0, r = 0;
		for(int i=0; i<p1Items.size(); i++) {
			if(i%5==0) {
				r++;
				c=0;
			}
			p1Items.get(i).draw(g, scoreOne.getX()+7+c*30, 150+r*30, goldImg);
			c++;
		}
		
		c = 0;
		r = 0;
		for(int i=0; i<p2Items.size(); i++) {
			if(i%5==0) {
				r++;
				c=0;
			}
			p2Items.get(i).draw(g, scoreTwo.getX()+7+c*30, 150+r*30, goldImg);
			c++;
		}
		
		Stack<Integer> health1 = p2.getHealth();
		if(health1.size()==1){
			g.drawImage(heart, scoreTwo.getX(), 30, null);
		}
		else if(health1.size()==2){
			g.drawImage(heart, scoreTwo.getX(), 30, null);
			g.drawImage(heart, scoreTwo.getX()+50, 30, null);
		}
		else{
			g.drawImage(heart, scoreTwo.getX(), 30, null);
			g.drawImage(heart, scoreTwo.getX()+50, 30, null);
			g.drawImage(heart, scoreTwo.getX()+100, 30, null);
		}

		if(connected) {
			Stack<Integer> health2 = p1.getHealth();
			if(health2.size()==1){
				g.drawImage(heart, scoreOne.getX(), 30, null);
			}
			else if(health2.size()==2){
				g.drawImage(heart, scoreOne.getX(), 30, null);
				g.drawImage(heart, scoreOne.getX()+50, 30, null);
			}
			else{
				g.drawImage(heart, scoreOne.getX(), 30, null);
				g.drawImage(heart, scoreOne.getX()+50, 30, null);
				g.drawImage(heart, scoreOne.getX()+100, 30, null);
			}
		}
				
		ArrayList<Bomb> bombs = p2.getBombs();
		for(int i=0; i<bombs.size(); i++) {
			bombs.get(i).draw(g, scoreTwo.getX()+5+i*30, 130, bombImg);
		}
		
		if(connected) {
			bombs = p1.getBombs();
			for(int i=0; i<bombs.size(); i++) {
				bombs.get(i).draw(g, scoreOne.getX()+5+i*30, 130, bombImg);
			}
		}
    } 
 
    public void playGame() throws IOException {
    	String hostName = ip;
        int portNumber = 1024;
         
        Socket serverSocket = new Socket(hostName, portNumber);
        out = new ObjectOutputStream(serverSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(serverSocket.getInputStream());
        PushbackInputStream pin = new PushbackInputStream(serverSocket.getInputStream());
        
        out.writeObject(p2);
        
        boolean first = true;
        while(true) {
        	try {
        		if(pin.available()!=0) {
        			game = (Game) in.readObject();
        			if(first) {
            			gameMap = game.getGameMap();
        				first = false;
        				connected = true;
        			} else if(!gameActive) {
        				gameMap = game.getGameMap();
        				gameActive = true;
        				gameReset();
        			}
        			p1 = game.getPlayer();
        			if(p1.getUsed()!=null && !gameMap.containsKey(p1.getUsed().getGridSpace())) {
        				gameMap.put(p1.getUsed().getGridSpace(), p1.getUsed());
        			}
        			checkGridSpace(p1);
        			checkWinner();
        			repaint();
        		} 
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
   	}
    
    public void checkMovement() throws IOException {
		p2.moved();
		out.reset();
		out.writeObject(p2);
		checkGridSpace(p2);
		repaint();
	}

	public void checkGridSpace(Player p) {
		if(gameMap.containsKey(p.getGridSpace())) {
			if(gameMap.get(p.getGridSpace()) instanceof Gold) {
				playSound("gold");
				if(p.getPlayer().equals("p1")) {
					p1Score++;
					scoreOne.setText("Score: " + p1Score);
					p1Items.add((Gold) gameMap.remove(p.getGridSpace()));
				}else {
					p2Score++;
					scoreTwo.setText("Score: " + p2Score);
					p2Items.add((Gold) gameMap.remove(p.getGridSpace()));
				}
			} else if(gameMap.get(p.getGridSpace()) instanceof Lava || gameMap.get(p.getGridSpace()) instanceof Bomb) {
				gameMap.remove(p.getGridSpace());
				p.hit();
				playSound("damage");
			}
			repaint();
			checkWinner();
		}
	}
	
	public void checkWinner() {
		if(p1.getHealth().size()==0) {
			winner.setForeground(Color.green);
			winner.setText("Player 2 Wins!");
			gameActive = false;
		} else if(p2.getHealth().size()==0) {
			winner.setForeground(Color.red);
			winner.setText("Player 1 Wins!");
			gameActive = false;
		}
		
		if(p1Score > p2Score && p1Score >= 8) {
			winner.setForeground(Color.red);
			winner.setText("Player 1 Wins!");
			gameActive = false;
		} else if(p2Score > p1Score && p2Score >= 8){
			winner.setForeground(Color.green);
			winner.setText("Player 2 Wins!");
			gameActive = false;
		}
	}
	
	public void playSound(String name) {
    	try {
    		URL url = null;
    		if(name.equals("gold")) {
                url = this.getClass().getClassLoader().getResource("gold.wav");
    		} else if(name.equals("damage")) {
    			url = this.getClass().getClassLoader().getResource("damage.wav");
    		} 
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
           exc.printStackTrace(System.out);
        }
    }
	
	public void gameReset() throws IOException {
		gameActive = true;
		p1.setGridSpace(new GridSpace(6, 6));
		p2.setGridSpace(new GridSpace(6, 6));
		p1.reset();
		p2.reset();
		p1Items.clear();
		p2Items.clear();
		p1Score = 0;
		p2Score = 0;
		winner.setText("");
		System.out.println(winner.getText());
		repaint();
	}
}