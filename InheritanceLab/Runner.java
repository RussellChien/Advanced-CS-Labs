import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.util.ArrayList;

public class Runner {

	public static void main(String[] args) throws IOException{
		JFrame frame = new JFrame();
		frame.setTitle("Employees");
		frame.setBounds(0, 0, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Screen screen = new Screen();
		frame.add(screen);
		
		frame.pack();
		frame.setVisible(true);
		Graphics g = screen.getBufferedGraphics();
		ArrayList<Employee> employees = new ArrayList<Employee>();
		
		employees.add(new Government("Albert", "teacher1.jpg", "teacher", "Los Angeles", 200));
		employees.add(new Government("David", "teacher2.png", "teacher", "Mountain View", 500));
		employees.add(new Government("Joe", "police1.jpg", "police", "San Jose", 200));
		employees.add(new Government("Bob", "police2.jpg", "police", "Los Altos", 500));
		employees.add(new Company("John", "engineer1.jpg", "engineer", "Apple", 200));
		employees.add(new Company("Ethan", "engineer2.jpg", "engineer", "Google", 500));
		employees.add(new Company("Philip", "banker1.jpg", "banker", "West Bank", 200));
		employees.add(new Company("Alex", "banker2.jpg", "banker", "Wells Fargo", 500));
		

		for(int i=0; i<employees.size(); i++) {
			if(i < employees.size()/2){
				try {
					employees.get(i).drawPhoto(g, 20 + 260*i, 50, screen);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(i < employees.size()){
				try {
				employees.get(i).drawPhoto(g, 20 + 260*(i-employees.size()/2), 300, screen);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		screen.repaint();
		
		while(true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(screen.getStateChanged()) {
				for(int i=0; i<employees.size(); i++) {
					employees.get(i).clearButton();
				}
				screen.clear();
				System.out.println(screen.getCurrCard());
				if(screen.getCurrCard().equals("Teachers")) {
					for(int i=0; i<employees.size(); i++) {
						if(employees.get(i).getJob().equals("teacher")) {
							try {
								employees.get(i).drawPhoto(g, employees.get(i).getXPos(), 200, screen);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else if(screen.getCurrCard().equals("Police")) {
					for(int i=0; i<employees.size(); i++) {
						if(employees.get(i).getJob().equals("police")) {
							try {
								employees.get(i).drawPhoto(g, employees.get(i).getXPos(), 200, screen);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else if(screen.getCurrCard().equals("Engineers")) {
					for(int i=0; i<employees.size(); i++) {
						if(employees.get(i).getJob().equals("engineer")) {
							try {
								employees.get(i).drawPhoto(g, employees.get(i).getXPos(), 200, screen);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else if(screen.getCurrCard().equals("Bankers")) {
					for(int i=0; i<employees.size(); i++) {
						if(employees.get(i).getJob().equals("banker")) {
							try {
								employees.get(i).drawPhoto(g, employees.get(i).getXPos(), 200, screen);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else if(screen.getCurrCard().equals("All")) {
					for(int i=0; i<employees.size()/2; i++) {
			
						try {
							employees.get(i).drawPhoto(g, 50 + 250*i, 100, screen);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}

					for(int i=employees.size()/2; i<employees.size(); i++) {
						
						try {
							employees.get(i).drawPhoto(g, 50 + 250*(i-employees.size()/2), 400, screen);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				}

				screen.setStateChanged(false);
			}

			if(screen.getSearched()) {
				screen.clear();
				for(int i=0; i<employees.size(); i++) {
					employees.get(i).clearButton();
					if(employees.get(i).getName().equals(screen.getSearchText())) {
						employees.get(i).drawPhoto(g, 500, 300, screen);
					}
				}
				screen.setSearched();
			}

			for(int i=0; i<employees.size(); i++) {
				if(employees.get(i).getHide()) {
					employees.get(i).clearButton();
					employees.remove(i);
					i--;
					screen.setStateChanged(true);
				}
			}
			screen.repaint();

		}

	}

}