import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Screen extends JPanel implements KeyListener, ActionListener, Runnable {
	
	private String hostName = "localhost";
	private int portNumber = 1024;
	private JButton start, restart;
	private boolean poll, active, startGame, first;
	private DLList<Player> players;
	private DLList<Thread> playerThreads, enemyThreads;
	private DLList<Enemy> enemies;
	private Player player;
	private int playerIndex;
	private PrintWriter out;
	private BufferedReader in;
	private JLabel waveNum, numPlayers;
	private int level, playerCount, bossLives;
	private int width, height;
	private JTextArea storyline;
	private JLabel instructions;
	
	public Screen() {
		
		this.setLayout(null);
		
		this.addKeyListener(this);

		players = new DLList<Player>();
		playerThreads = new DLList<Thread>();
		enemies = new DLList<Enemy>();
		enemyThreads = new DLList<Thread>();

		poll = false;
		active = false;
		startGame = false;

		level = 0;
		playerCount = 0; 
		bossLives = 5;

		width = 1000;
		height = 800;
    	
    	storyline = new JTextArea();
    	storyline.setBounds(450, 300, 400, 100);
    	storyline.setFont(new Font("TimesRoman", Font.PLAIN, 25));
    	storyline.setEditable(false);
    	storyline.setBorder(new LineBorder(Color.white, 5, true));
    	storyline.setBackground(Color.lightGray);
    	storyline.setForeground(Color.white);
    	storyline.setVisible(false);
    	this.add(storyline);
		
		instructions = new JLabel();
    	instructions.setBounds(340, 500, 400, 150);
    	instructions.setForeground(Color.green);
    	instructions.setText("<html><center>AD to move left and right<br/>WS to move up or down<br/>SPACE bar to shoot</center></html>");
    	instructions.setFont(new Font("TimesRoman", Font.PLAIN, 25));
    	instructions.setBackground(Color.black);
    	instructions.setOpaque(true);
    	instructions.setHorizontalAlignment(JLabel.CENTER);
    	instructions.setVisible(true);
    	this.add(instructions);
		
		start = new JButton("Join Game");
		start.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		start.addActionListener(this);
		start.setBounds(450, 400, 140, 50);
		start.setBackground(Color.red);
		start.setBorderPainted(false);
		start.setOpaque(true);
		this.add(start);
		
		restart = new JButton("Restart");
		restart.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		restart.addActionListener(this);
		restart.setBounds(450, 400, 140, 50);
		restart.setBackground(Color.white);
		restart.setBorderPainted(false);
		restart.setOpaque(true);
		restart.setVisible(false);
		this.add(restart);
		
		waveNum = new JLabel();
		waveNum.setForeground(Color.white);
		waveNum.setBounds(750, 5, 200, 80);
		waveNum.setFont(new Font("TimesRoman", Font.BOLD, 40));
		waveNum.setHorizontalAlignment(JLabel.CENTER);
		waveNum.setVisible(false);
    	this.add(waveNum);
    	
    	numPlayers = new JLabel();
    	numPlayers.setForeground(Color.white);
    	numPlayers.setBounds(750, 5, 300, 80);
    	numPlayers.setFont(new Font("TimesRoman", Font.BOLD, 30));
    	numPlayers.setHorizontalAlignment(JLabel.CENTER);
    	numPlayers.setVisible(false);
    	this.add(numPlayers);
		
		this.setFocusable(true);
		this.requestFocus();
		
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public void paintComponent(Graphics g) {
		if(level == 1) {
			try {
				BufferedImage bImage = ImageIO.read(new File("level1.jpg"));
				g.drawImage(bImage, 0, 0, width, height, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(level == 2) {
			try {
				BufferedImage bImage = ImageIO.read(new File("level2.jpg"));
				g.drawImage(bImage, 0, 0, width, height, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(level == 3) {
			try {
				BufferedImage bImage = ImageIO.read(new File("level3.jpg"));
				g.drawImage(bImage, 0, 0, width, height, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(level == 4) {
			try {
				BufferedImage bImage = ImageIO.read(new File("level4.jpg"));
				g.drawImage(bImage, 0, 0, width, height, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(!active) {
			try {
				BufferedImage bImage = ImageIO.read(new File("titleimage.png"));
				g.drawImage(bImage, 0, 0, width, height, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(int i=0; i<players.size(); i++) {
			if(players.get(i) != null) {
				players.get(i).draw(g);
			}
		}
		
		for(int i=0; i<enemies.size(); i++) {
			if(enemies.get(i) != null) {
				enemies.get(i).draw(g);
			}
		}
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(active) {
				repaint();
				for(int i=0; i<players.size(); i++) {
					for(int j=0; j<enemies.size(); j++) {
						if(players.get(i).hit(enemies.get(j))) {
							out.println("hit");
							hit();
							break;
						}
					}
				}
				
				for(int k=0; k<players.size(); k++) {
					DLList<Projectile> projectiles = players.get(k).getProjectiles();
					for(int j=0; j<projectiles.size(); j++) {
						for(int i=0; i<enemies.size(); i++) {
							if(enemies.get(i) != null && projectiles.get(j) != null && enemies.get(i).hit(projectiles.get(j))) {
								if(enemies.get(i).boss()) {
									bossLives--;
									if(bossLives == 0) {
										enemies.remove(i);
										enemyThreads.remove(i);
										i--;
									}
								} else {
									enemies.remove(i);
									enemyThreads.remove(i);
									i--;
								}
								projectiles.remove(j);
								players.get(k).getProjectileThreads().remove(j);
								j--;
								playSound("death");
							}
						}
					}
				}
				
				for(int i=0; i<players.size(); i++) {
					DLList<Thread> projectiles = players.get(i).getProjectileThreads();
					for(int j=0; j<projectiles.size(); j++) {
						if(!projectiles.get(j).isAlive()) {
							projectiles.remove(j);
							j--;
						}
					}
				}
				
				if(enemies.size() == 0) {
					if(level == 1) {
						level++;
						repaint();
						out.println("level 2");
						waveNum.setText("Wave 2");
						storyline.setVisible(true);
						storyline.setForeground(Color.black);
						storyline.setText("Good job! You have defeated the first \nwave. Get ready for the next wave!");
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						storyline.setVisible(false);
						createEnemies();
						createEnemiesTwo();
					} else if(level == 2) {
						level++;
						repaint();
						out.println("level 3");
						waveNum.setText("Wave 3");
						storyline.setVisible(true);
						storyline.setText("Second wave defeated!\nA major encounter is about to occur\n The mothership is coming!");
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						storyline.setVisible(false);
						Enemy e = new Enemy(300, 0, 3, 1.5, true);
						enemies.add(e);
						Thread t = new Thread(e);
						enemyThreads.add(t);
						t.start();
						createEnemies();
					} else if(level == 3) {
						level++;
						repaint();
						out.println("level 4");
					    storyline.setBounds(400, 300, 300, 100);
						storyline.setVisible(true);
						storyline.setText("Congratulations! \n You saved the Earth \nfrom space invaders!\n");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						storyline.setVisible(false);
						restart.setVisible(true);
					}
				}
				
			}
		}
	}

	public void hit() {
		enemies = new DLList<Enemy>();
		enemyThreads = new DLList<Thread>();
		if(level == 1) {
			createEnemies();
		} else if(level == 2) {
			createEnemies();
			createEnemiesTwo();
		} else if(level == 3) {
			Enemy e = new Enemy(300, 50, 5, 7, true);
			enemies.add(e);
			Thread t = new Thread(e);
			enemyThreads.add(t);
			t.start();
			createEnemies();
		}
		for(int k=0; k<players.size(); k++) {
			players.get(k).reset();
		}
	}
	
	public void poll() {
		try {

	    	Socket serverSocket = new Socket(hostName, portNumber);
	        in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
	        out = new PrintWriter(serverSocket.getOutputStream(), true);
	        
	        String connected = in.readLine();
	        System.out.println(connected);
	        playerIndex = Integer.parseInt(in.readLine());
	        numPlayers.setVisible(true);
	        numPlayers.setText("Players: " + (playerIndex+1));
	        playerCount = playerIndex + 1;
	        if(playerIndex != 0) {
	        	start.setVisible(false);
	        }
	        
	        first = true;
	        MyReader mr = new MyReader(in);
	        Thread t = null;
	        
	        while(true) {
	        	if(active) {
	        		String move = in.readLine();
	        		if(move.equals("restart")) {
	        			out.println("restart");
	        			restart();
	        		} else if(move.equals("p")) {
	        			enemies = new DLList<Enemy>();
	    				enemyThreads = new DLList<Thread>();
	        		} else if(move.split(" ")[0].equals("level")) {
	        			int newLevel = Integer.parseInt(move.split(" ")[1]);
	        			if(newLevel != level) {
	        				enemies = new DLList<Enemy>();
	        				enemyThreads = new DLList<Thread>();
	        			}
	        		} else if(move.equals("hit")) {
	        			hit();
	        		} else {
						String[] instrucs = move.split(" ");
						players.get(Integer.parseInt(instrucs[0])).move(instrucs[1]);
						repaint();
	        		}
	        	} else if(startGame) { 
	        		mr = null;
	        		t.interrupt();
	        		t = null;
	        		out.println("start send");
	    			startGame();
	        	} else if(playerIndex != 0) {
	        		String start = in.readLine();
	        		if(start.equals("start man")) {
		        		out.println("start gip");
		        		startGame();
	        		} else {
	        			playerCount = Integer.parseInt(start);
	        			numPlayers.setText("Players Online: " + playerCount);
	        		}
	        	} else {
	        		try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        		if(first) {
		    	        mr = new MyReader(in);
		    	        t = new Thread(mr);
		    	        t.start();
		    	        first = false;
	        		}
	        		if(mr.numPlayers() > playerIndex && mr.numPlayers() != playerCount) {
	        			playerCount = mr.numPlayers();
	        			numPlayers.setText("Players Online: " + playerCount);
	        		}
	        	}
	        }
	        
	    } catch (UnknownHostException e) {
	      System.err.println("Don't know about host " + hostName);
	      System.exit(1);
	    } catch (IOException e) {
	        System.err.println("Error connecting to " + hostName);
	        System.exit(1);
	    }
	}
	
	public void startGame() {
		instructions.setVisible(false);
		numPlayers.setVisible(false);
		
		if(level == 0) {
			level++;
	    	storyline.setBounds(250, 300, 500, 100);
			storyline.setForeground(Color.black);
			storyline.setVisible(true);
			storyline.setText("Space invaders have reached Earth's solar \nsystem. As a member of the space force defense, \n you must fight them off to protect Earth!");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			storyline.setVisible(false);
	    	storyline.setBounds(250, 300, 400, 100);
		}
		
		waveNum.setVisible(true);
		waveNum.setText("Wave 1");
		
		active = true;
		start.setVisible(false);
		
		player = new Player((int)(Math.random() * 600 + 100), 700);
		out.println(playerIndex + " " + player.getX() + " " + player.getY());
		try {
			for(int i=0; i<playerCount; i++) {
				players.add(null);
			}
			for(int i=0; i<playerCount; i++) {
				String result = in.readLine();
				String[] split = result.split(" ");
				int index = Integer.parseInt(split[0]);
				int x = Integer.parseInt(split[1]);
				int y = Integer.parseInt(split[2]);
				players.set(index, new Player(x, y));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		player = players.get(playerIndex);
		
		for(int i=0; i<players.size(); i++) {
			Thread t = new Thread(players.get(i));
			playerThreads.add(t);
			t.start();
		}
		
		createEnemies();

		Thread go = new Thread(this);
		go.start();
		
		repaint();
		startGame = false;
	}
	
	public void createEnemies() {
		Enemy e1 = new Enemy(300, 15, 3, 1, false);
		Enemy e2 = new Enemy(100, 50, 4, 2, false);
		Thread e1Thread = new Thread(e1);
		Thread e2Thread = new Thread(e2);
		enemies.add(e1);
		enemies.add(e2);
		enemyThreads.add(e1Thread);
		enemyThreads.add(e2Thread);
		e1Thread.start();
		e2Thread.start();
		
		e1 = new Enemy(500, 50, 3, 1, false);
		e2 = new Enemy(10, 30, 1, 3, false);
		e1Thread = new Thread(e1);
		e2Thread = new Thread(e2);
		enemies.add(e1);
		enemies.add(e2);
		enemyThreads.add(e1Thread);
		enemyThreads.add(e2Thread);
		e1Thread.start();
		e2Thread.start();
	}
	
	public void createEnemiesTwo() {
		Enemy e1 = new Enemy(250, 100, 2, 3, false);
		Enemy e2 = new Enemy(102, 80, -2, 1, false);
		Thread e1Thread = new Thread(e1);
		Thread e2Thread = new Thread(e2);
		enemies.add(e1);
		enemies.add(e2);
		enemyThreads.add(e1Thread);
		enemyThreads.add(e2Thread);
		e1Thread.start();
		e2Thread.start();
		
		e1 = new Enemy(102, 394, -2, 3, false);
		e2 = new Enemy(300, 20, 5, 1, false);
		e1Thread = new Thread(e1);
		e2Thread = new Thread(e2);
		enemies.add(e1);
		enemies.add(e2);
		enemyThreads.add(e1Thread);
		enemyThreads.add(e2Thread);
		e1Thread.start();
		e2Thread.start();
	}

	public void keyTyped(KeyEvent e) { }
	public void keyReleased(KeyEvent e) { }

	public void keyPressed(KeyEvent e) {
		if(active) {
			if(e.getKeyCode() == KeyEvent.VK_W) {
				out.println(playerIndex + " up");
				player.move("up");
				repaint();
			} else if(e.getKeyCode() == KeyEvent.VK_A) {
				out.println(playerIndex + " left");
				player.move("left");
				repaint();
			} else if(e.getKeyCode() == KeyEvent.VK_S) {
				out.println(playerIndex + " down");
				player.move("down");
				repaint();
			} else if(e.getKeyCode() == KeyEvent.VK_D) {
				out.println(playerIndex + " right");
				player.move("right");
				repaint();
			} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				out.println(playerIndex + " shot");
				player.shoot();
			} /*else if(e.getKeyCode() == KeyEvent.VK_P) {
				if(enemies.size() != 0) {
					if(level < 4) {
						out.println("p");
						enemies = new DLList<Enemy>();
						enemyThreads = new DLList<Thread>();
					}
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}*/
		}
		
	}

	public boolean getPoll() {
		return poll;
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == start) {
			if(start.getText().equals("Join Game")) {
				poll = true;
				start.setText("Start");
			} else {
				startGame = true;
			}
		} else if(e.getSource() == restart) {
			out.println("restart send");
		} 
		
	}
	
	public void restart() {
		instructions.setVisible(true);
		active = false;
		startGame = false;
		if(playerIndex == 0) {
			start.setVisible(true);
		}
		restart.setVisible(false);
		first = true;
		level = 0;
		bossLives = 5;
		waveNum.setVisible(false);
		numPlayers.setVisible(true);
		players = new DLList<Player>();
		repaint();
	}
	
	public void playSound(String name) {
    	try {
    		URL url = null; 
    		if(name.equals("shoot")) {
                url = this.getClass().getClassLoader().getResource("shoot.wav");
    		} else if(name.equals("death")){
    			url = this.getClass().getClassLoader().getResource("death.wav");
    		}  
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
           exc.printStackTrace(System.out);
        }
    }


}
