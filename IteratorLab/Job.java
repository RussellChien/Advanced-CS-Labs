import java.util.ArrayList;
import java.util.ListIterator;

public class Job {

	private ArrayList<String> jobs = new ArrayList<String>();
	private ListIterator<String> li;
	
	public Job() {}
	
	public void addJob(String job, String company, String startTime, String endTime, String description) {
		
		li = jobs.listIterator();
		while(li.hasNext()) {
			if(li.next().compareTo(endTime)>0) {
				li.previous();
				break;
			}
		}
		li.add(job + "  " + company + "  " + startTime + "  " + endTime + "  " + description);
		
	}
	
	public ListIterator<String> getJobs() {
		return jobs.listIterator();
	}
	
	public int getSize() {
		return jobs.size();
	}
	
}
