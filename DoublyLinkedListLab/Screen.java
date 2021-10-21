import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Screen extends JPanel implements MouseListener, ActionListener{
	
	private DLList<Card> deck;
	private DLList<Card> player;
	private int points;
	private JLabel pointsTxt;
	private JButton playBtn;
	private Color green;
	private boolean gameStart, gameOver;
	private String msg;
	
	public Screen(){
		
		this.setLayout(null);
		this.addMouseListener(this);
		
		deck = new DLList<Card>();
		for(int value = 1; value < 14; value++){
			for(int suit = 0; suit < 4; suit++){
				deck.add(new Card(value, suit));
			}
		}
		shuffle();

		player = new DLList<Card>();
		for(int i = 0; i < 5; i++) {
			player.add(deck.get(0));
			deck.remove(0);
		}

		points = 50;
		gameStart  = false;
		msg = "Choose cards to hold.";
		gameOver = false;
		
		pointsTxt = new JLabel("Points: " + points);
		pointsTxt.setBounds(350, 10, 250, 50);
		pointsTxt.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		pointsTxt.setForeground(Color.black);
		this.add(pointsTxt);
		
		playBtn = new JButton("Play");
		playBtn.setBackground(Color.green);
		playBtn.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		playBtn.setBounds(350, 700, 150, 50);
		playBtn.addActionListener(this);
		this.add(playBtn);
		
		this.setFocusable(true);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(800, 800);
	}
	
	public void paintComponent(Graphics g){
		if(gameStart){
			for(int i = 0; i < player.size(); i++){
				player.get(i).drawMe(g, i * (player.get(i).getWidth() + 20) + 60, 400);
			}
		
			if(gameOver){
				g.setColor(Color.black);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
				g.drawString(msg, 300, 300);
			}
			else{
				msg = "Choose cards to hold.";
				g.setColor(Color.black);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
				g.drawString(msg, 300, 300);
			}
		}
		
	}
	
	public void shuffle(){
		Card temp;
		int rand;
		for(int i = 0; i < deck.size(); i++){
			rand = (int)(Math.random() * deck.size());
			temp = deck.get(i);
			deck.set(i, deck.get(rand));
			deck.set(rand, temp);
		}
	}
	
	public void playSound(String sound){
        try {
            URL url = this.getClass().getClassLoader().getResource(sound + ".wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

	public void mouseClicked(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		//System.out.println("x: " + x + " Y: " + y);
		if(gameStart && !gameOver){
			for(int i = 0; i < player.size(); i++){
				player.get(i).checkClicked(x, y);
			}
		}
		repaint();
	}

	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == playBtn){
			if(playBtn.getText().equals("Draw")){
				playBtn.setText("Play");
				points--;
				if(points >= 0){
					pointsTxt.setText("Points: " + points);
					for(int i = 0; i < player.size(); i++){
						if(!player.get(i).getClicked()){
							deck.add(player.get(i));
							player.set(i, deck.pop());
						}
					}
					for(int i = 0; i < player.size(); i++){
						if(player.get(i).getClicked()){
							player.get(i).unclicked();
						}
					}
					shuffle();
				} 
				else{
					gameStart = false;
					playBtn.setVisible(false);
					pointsTxt.setBounds(150, 10, 700, 50);
					pointsTxt.setText("You have no more points left.");
				}
				repaint();
				if(points >= 0){					
					DLList<Card> sortedPlayer = new DLList<Card>();
					for(int i = 0; i < player.size(); i++){
						sortedPlayer.add(player.get(i));
					}
					Card temp;
					for(int i = 0; i < sortedPlayer.size(); i++){
						for(int j = i+1; j < sortedPlayer.size(); j++){
							if(sortedPlayer.get(i).getValue() < sortedPlayer.get(j).getValue()){
								temp = sortedPlayer.get(i);
								sortedPlayer.set(i, sortedPlayer.get(j));
								sortedPlayer.set(j, temp);
							}
						}
					}
					//royal or straight flush
					boolean flush = true;
					for(int i = 0; i < sortedPlayer.size()-1; i++){
						if(i == sortedPlayer.size()-2 && sortedPlayer.get(i+1).getValue() == 1 && sortedPlayer.get(i).getValue() == 10){
							continue;
						} 
						else if(sortedPlayer.get(i).getValue() != sortedPlayer.get(i+1).getValue() + 1 ||
								sortedPlayer.get(i).getSuit() != sortedPlayer.get(i+1).getSuit()){
							flush = false;
							break;
						}
					}
					if(flush){
						if(sortedPlayer.get(0).getValue() == 10){
							win(250);
						} 
						else{
							win(50);
						}
						return;
					}		
					//4 of a kind			
					boolean fourOfAKindForward = true;
					for(int i = 0; i < sortedPlayer.size()-2; i++){
						if(sortedPlayer.get(i).getValue() != sortedPlayer.get(i+1).getValue()){
							fourOfAKindForward = false;
						}
					}
					boolean fourOfAKindBackward = true;
					for(int i = sortedPlayer.size()-1; i > 1; i--){
						if(sortedPlayer.get(i).getValue() != sortedPlayer.get(i-1).getValue()){
							fourOfAKindBackward = false;
						}
					}
					if(fourOfAKindForward || fourOfAKindBackward){
						points += 25;
						win(25);
						return;
					}
					//full house
					if(sortedPlayer.get(0).getValue() == sortedPlayer.get(1).getValue() &&
							sortedPlayer.get(3).getValue() == sortedPlayer.get(4).getValue() &&
							(sortedPlayer.get(2).getValue() == sortedPlayer.get(0).getValue() || sortedPlayer.get(2).getValue() == sortedPlayer.get(4).getValue())){
						points += 9;
						win(9);
						return;
					}				
					//flush	
					boolean suitFlush = true;
					for(int i = 0; i < sortedPlayer.size()-1; i++){
						if(sortedPlayer.get(i).getSuit() != sortedPlayer.get(i+1).getSuit()){
							suitFlush = false;
						}
					}
					if(suitFlush){
						win(6);
						return;
					}
					//straight				
					boolean straight = true;
					for(int i = 0; i < sortedPlayer.size()-1; i++) {
						if(i == sortedPlayer.size()-2 && sortedPlayer.get(i+1).getValue() == 1){
							continue;
						}
						else if(sortedPlayer.get(i).getValue() != sortedPlayer.get(i+1).getValue() + 1){
							straight = false;
						}
					}
					if(straight){
						win(4);
						return;
					}
					//3 of a kind				
					boolean threeAKind = false;
					for(int i = 0; i < sortedPlayer.size()-3; i++){
						if(sortedPlayer.get(i).getValue() == sortedPlayer.get(i+1).getValue() && sortedPlayer.get(i+1).getValue() == sortedPlayer.get(i+2).getValue()){
							threeAKind = true;
						}
					}
					if(threeAKind){
						win(3);
						return;
					}
					//2 pairs				
					int pairs = 0;
					for(int i = 0; i < sortedPlayer.size()-2; i++){
						if(sortedPlayer.get(i).getValue() == sortedPlayer.get(i+1).getValue()) {
							pairs++;
						}
					}
					if(pairs == 2){
						win(2);
						return;
					}
					//pair of jacks or higher
					boolean pair = false;
					for(int i = 0; i < sortedPlayer.size()-2; i++){
						
						if(sortedPlayer.get(i).getValue() == sortedPlayer.get(i+1).getValue() && (sortedPlayer.get(i).getValue() >= 11 || sortedPlayer.get(i).getValue() == 1)){
							pair = true;
						}
					}
					if(pair){
						win(1);
						return;
					}
					playSound("lose");
					msg = "You lose!";
					gameOver = true;
					repaint();
				}
			} 
			else{
				if(!gameStart){
					gameStart = true;
				}
				gameOver = false;
				playBtn.setText("Draw");
				for(int i = 0; i < player.size(); i++){
					deck.add(player.get(i));
					player.get(i).unclicked();
				}
				shuffle();
				for(int i = 0; i < player.size(); i++){
					player.set(i, deck.pop());
				}
				repaint();
			}
		}	
	}
	
	public void win(int amount){
		gameOver = true;
		playSound("win");
		points += amount;
		pointsTxt.setText("Points: " + points);
		msg = "You win " + amount + " points!";
	}


}
