import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class Database{
	private HashMap<Profile, SchoolInfo> hashMap; 
	private TreeMap<Profile, SchoolInfo> treeMap;
	public Database(String fileName){
		hashMap = new HashMap<Profile, SchoolInfo>();
		treeMap = new TreeMap<Profile, SchoolInfo>();
		try{
			Scanner in = new Scanner(new File(fileName));

			while (in.hasNextLine()) {

				String[] nameParts = in.nextLine().split(" ");
				int birthYear = (int)(Math.random() * 20 + 	1990);
				Profile profile = new Profile(nameParts[1], nameParts[0], birthYear);
				hashMap.put(profile, new SchoolInfo());
				treeMap.put(profile, new SchoolInfo());
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	public String getProfiles() {
		String profiles = "";
		Iterator it = treeMap.keySet().iterator();

		while (it.hasNext()) {
			profiles += it.next().toString() + "\n";
		}

		return profiles.substring(0, profiles.length() - 1);
	}

	public String getSchool(Profile p){
		for (Profile i : hashMap.keySet()){
			if(i.equals(p)){
				return hashMap.get(p).toString();
			}
		}
		return "not found";
	}

	public void changeSchool(String lname, String fname, int by, String school){
		Profile temp = new Profile(lname, fname, by);
		for(Profile i : hashMap.keySet()){
			if(i.equals(temp)){
				hashMap.put(temp, new SchoolInfo(school));
				treeMap.put(temp, new SchoolInfo(school));
			}
		}
	}

	public void addProfile(String lname, String fname, int by, String school){
		Profile p = new Profile(lname, fname, by);
		SchoolInfo si = new SchoolInfo(school);
		hashMap.put(p, si);
		treeMap.put(p, si);
	}

	public void removeProfile(String lname, String fname, int by){
		Profile p = new Profile(lname, fname, by);
		Iterator hashit = hashMap.keySet().iterator();
		while(hashit.hasNext()){
			if(hashit.next().equals(p)){
				hashit.remove();
			}
		}
		Iterator treeit = treeMap.keySet().iterator();
		while(treeit.hasNext()){
			if(treeit.next().equals(p)){
				treeit.remove();
			}
		}

	}
	
}