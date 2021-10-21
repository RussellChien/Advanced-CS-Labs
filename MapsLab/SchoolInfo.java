public class SchoolInfo{
	private String school;
	private String schedule;
	private String[] schedules = {"AP Bio, APUSH, APCSA", "AP Physics 1, HAMLIT, AP Calc BC", "AP Stats, AP Chemistry, Advanced CS", 
	"Engineering, Orchestra, WHAP", "Band, AP Chinese, AP French"};
	private String[] schools = {"Mountain View High School", "Los Altos High School", "Cupertino High School",
	"Alta Vista High School", "Homestead High School", "Gunn High School", "St. Francis High School"};
	public SchoolInfo(){
		int random = (int)(Math.random() * schools.length); 
		school = schools[random];
		random = (int)(Math.random() * schedules.length);
		schedule = schedules[random];
	}
	public SchoolInfo(String school){
		this.school = school;
		int random = (int)(Math.random() * schedules.length);
		schedule = schedules[random];

	}
	public String getSchool(){
		return school;
	}
	public String getSchedule(){
		return schedule;
	}
	public String toString(){
		return school + ": " + schedule;
	}
}