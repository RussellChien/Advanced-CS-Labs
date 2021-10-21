import javax.swing.JFrame;

public class Client {
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Client");
		 
        Screen sc = new Screen();
        frame.add(sc);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        while(true) {
			try {
			Thread.sleep(10);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        	if(sc.getPoll()) {
        		sc.poll();
        		break;
        	}
        }
		
	}

}
