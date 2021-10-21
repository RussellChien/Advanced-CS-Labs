import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;


public class Schedule {
	
	private ArrayList<Pair<Integer, String>> mySchedule = new ArrayList<Pair<Integer, String>>();

	public Schedule(){}

	public Schedule(int[] periods, String[] courses){
		for(int i=0; i<periods.length; i++) {
			mySchedule.add(new Pair<Integer, String>(periods[i], courses[i]));
		}
		
	}
	
	public void addClass(int period, String course){
		
		int i, j;
		Pair<Integer, String> temp;
		for(i=0; i<mySchedule.size(); i++) {
			if(mySchedule.get(i).getKey()==period) {
				return;
			}
		}
		mySchedule.add(new Pair<Integer, String>(period, course));
		for(i=0; i<mySchedule.size()-1; i++) {
			for(j=i+1; j<mySchedule.size(); j++) {
				if(mySchedule.get(i).getKey()>mySchedule.get(j).getKey()){
					temp = mySchedule.get(i);
					mySchedule.set(i, mySchedule.get(j));
					mySchedule.set(j, temp);
				}
			}
		}
		
	}
	
	public String[] getSchedule(){
		String[] schedule = new String[mySchedule.size()];
		for(int i=0; i<schedule.length; i++) {
			schedule[i] = mySchedule.get(i).toString();
		}
		return schedule;
	}

	public void drawSchedule(Graphics g, int x, int y){
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		for(int i=0; i<mySchedule.size(); i++) {
			g.drawString(mySchedule.get(i).toString(), x, y+i*22);
		}
		
	}
	
}