import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Screen extends JPanel implements MouseMotionListener, MouseListener, ActionListener {
	
	private Stack<Square[][]> history = new Stack<Square[][]>();
	private Square[][] redoSave = new Square[16][16];
	private Square[][] pixels = new Square[16][16];
	private int palSqrSize = 70;
	private int selR = 255, selG = 255, selB = 255;
	private ArrayList<int[]> palColors = new ArrayList<int[]>();
	private boolean drawing = false;
	private JButton undo, clear, redo, save;
	private JTextField drawName;
	private boolean undoed = false;

	
	public Screen() {
		
		this.setLayout(null);
		
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {
				pixels[row][col] = new Square(255, 255, 255);
			}
		}
		
		Square[][] level = new Square[16][16];
		Square oldSqr;
		
		for(int row = 0; row < pixels.length; row++) {
			for(int col =0; col < pixels[0].length; col++) {
				oldSqr = pixels[row][col];
				level[row][col] = new Square(255, 255, 255);
			}
		}
		
		history.push(level);
		
		undo = new JButton("Undo");
		undo.setBounds(30, 600, 100, 50);
		undo.addActionListener(this);
		undo.setBackground(Color.gray);
		undo.setEnabled(false);
		this.add(undo);
		
		clear = new JButton("Clear");
		clear.setBounds(135, 600, 100, 50);
		clear.addActionListener(this);
		this.add(clear);
		
		redo = new JButton("Redo");
		redo.setBounds(235, 600, 100, 50);
		redo.addActionListener(this);
		this.add(redo);

		this.setFocusable(true);
		this.requestFocus();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(1200, 800);
	}
	
	public void paintComponent(Graphics g) {
		// Color palette
		int r = 0, gr = 0, b = 0;
		
		for(int row = 0; row < 6; row++) {
			for(int col = 0; col < 3; col++) {
				int[] rgb = {r, gr, b};
				palColors.add(rgb);
				g.setColor(new Color(r, gr, b));
				//red
				if(row == 0) {
					if(col == 0){
						r = 100;
						gr = 0;
						b = 0;
					}
					else{
						r+= 50;
						gr += 10;
						b += 20;
					}

				} 
				//orange
				else if(row == 1){
					if(col == 0){
						r = 255;
						gr = 75;
						b = 0;
					}
					else{
						gr += 45;
						b += 10;
					}
				}
				//yellow
				else if(row == 2){
					if(col == 0){
						r = 200;
						gr = 200;
						b = 0;
					}
					else{
						r+= 10;
						gr += 10;
					}
				} 
				//green
				else if(row == 3){
					if(col == 0){
						r = 0;
						gr = 100;
						b = 0;
					}
					else{
						r+= 10;
						gr += 75;
						b += 10;
					}
				} 
				//blue
				else if(row == 4) {
					if(col == 0){
						r = 0;
						gr = 0;
						b = 200;
					}
					else{
						r+= 10;
						gr += 50;
						b += 15;
					}
				} 
				//brown
				else if(row == 5) {
					if(col == 0){
						r = 140;
						gr = 70;
						b = 20;
					}
					else{
						r+= 15;
						gr += 15;
						b += 15;
					}
				}
				g.fillRect(90 + col*palSqrSize, 50 + row*palSqrSize, palSqrSize, palSqrSize);

			}
		}
		
		drawCanvas(g, 400, 50);
		
	}

	public void mouseDragged(MouseEvent e) {
		
		int mX = e.getX();
		int mY = e.getY();
		
		if(mX > 400 && mX < 400+40*pixels.length && mY > 50 && mY <50+40*pixels[0].length) {
			
			int row = (mY-50)/40;
			int col = (mX-400)/40;
			
			pixels[row][col].changeColor(selR, selG, selB);
			
			drawing = true;
			this.repaint();
			
		} else if(mX > 50 && mX < 50+palSqrSize*3 && mY > 50 && mY <50+palSqrSize*6){
			
			int row = (mY-50)/palSqrSize;
			int col = (mX-50)/palSqrSize;
			
			int index = row*3 + col;
			int[] color = palColors.get(index);
			selR = color[0];
			selG = color[1];
			selB = color[2];
			
		}
		
	}
	
	public void mousePressed(MouseEvent e) {
		
		int mX = e.getX();
		int mY = e.getY();
		
		if(mX > 400 && mX <400+40*pixels.length && mY > 50 && mY <50+40*pixels[0].length) {
			
			int row = (mY-50)/40;
			int col = (mX-400)/40;
			
			pixels[row][col].changeColor(selR, selG, selB);

			undo.setBackground(Color.white);
			undo.setEnabled(true);
			
			drawing = true;
			this.repaint();
			
		} else if(mX > 90 && mX <90+palSqrSize*3 && mY > 50 && mY <50+palSqrSize*6){
			
			int row = (mY-50)/palSqrSize;
			int col = (mX-90)/palSqrSize;
			
			int index = row*3 + col;
			int[] color = palColors.get(index);
			selR = color[0];
			selG = color[1];
			selB = color[2];
			
		}
		
	}
	

	public void mouseReleased(MouseEvent e) {

		if(drawing) {
			
			Square[][] level = new Square[16][16];
			Square oldSqr;
			
			for(int row = 0; row < pixels.length; row++) {
				for(int col = 0; col < pixels[0].length; col++) {
					oldSqr = pixels[row][col];
					level[row][col] = new Square(oldSqr.getR(), oldSqr.getG(), oldSqr.getB());
				}
			}
			
			history.push(level);
			drawing = false;
		}

	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == undo) {
			
			if(history.size() > 1) {
				
				undoed = true;
				redo.setBackground(Color.white);
				redo.setEnabled(true);
				Square[][] redoTemp = history.pop();
				Square oldSqr;
				for(int row = 0; row < pixels.length; row++) {
					for(int col = 0; col < pixels[0].length; col++) {
						oldSqr = redoTemp[row][col];
						redoSave[row][col] = new Square(oldSqr.getR(), oldSqr.getG(), oldSqr.getB());
					}
				}
				
				Square[][] level = history.peek();
				Square[][] copy = new Square[16][16];
				for(int row = 0; row < pixels.length; row++) {
					for(int col = 0; col < pixels[0].length; col++) {
						oldSqr = level[row][col];
						copy[row][col] = new Square(oldSqr.getR(), oldSqr.getG(), oldSqr.getB());
					}
				}
				
				pixels = copy;
				repaint();
			}
			if(history.size()<=1) {
				undo.setBackground(Color.gray);
				undo.setEnabled(false);
			}
			
		} else if(e.getSource() == clear) {
			for(int row = 0; row < pixels.length; row++) {
				for(int col = 0; col < pixels[0].length; col++) {
					pixels[row][col].changeColor(255, 255, 255);
				}
			}
			repaint();
		} else if(e.getSource() == redo && undoed) {
			for(int row = 0; row < pixels.length; row++) {
				for(int col = 0; col < pixels[0].length; col++) {
					pixels[row][col] = redoSave[row][col];
				}
			}
			
			Square[][] copy = new Square[16][16];
			for(int row = 0; row < pixels.length; row++) {
				for(int col = 0; col < pixels[0].length; col++) {
					copy[row][col] = redoSave[row][col];
				}
			}
			undoed = false;
			redo.setEnabled(false);
			redo.setBackground(Color.gray);
			undo.setBackground(Color.white);
			undo.setEnabled(true);
			
			history.push(copy);
			repaint();
		} 
		
	}

	public void drawCanvas(Graphics g, int offSetX, int offSetY) {
		for(int row = 0; row < pixels.length; row++) {
			for(int col = 0; col < pixels[0].length; col++) {
				Square sqr = pixels[row][col];
				sqr.drawMe(g, offSetX + col*sqr.getSize(), offSetY + row*sqr.getSize());;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	

}
