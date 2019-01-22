import java.io.*; 
import java.util.*;

class HockeyPlayer implements Comparable<HockeyPlayer>{
	//fields
	private String lastName;
	private String position;
	private int jersey;
	private int pointsWins;
	
	//constructor
	public HockeyPlayer(String lastName, String position, int jersey, int pointsWins){
		this.lastName = lastName;
		this.position = position;
		this.jersey = jersey;
		this.pointsWins = pointsWins;
	}
	
	@Override
    	public int compareTo(HockeyPlayer other) {
    		if(this.getPointsWins() < other.getPointsWins()) {
			return 1;
		}
		else if (this.getPointsWins() == other.getPointsWins()) { 
			return 0;
		}
		else{
			return -1;
		}
	}
	
	//setters
	public void setLastName(String lastName){
		this.lastName = lastName;	
	}
	
	public void setPosition(String position){
		this.position = position;	
	}
	
	public void setJersey(int jersey){
		this.jersey = jersey;	
	}
	
	public void setPointsWins(int pointsWins){
		this.pointsWins = pointsWins;	
	}
	
	//getters
	public String getLastName(){
		return lastName;	
	}
	
	public String getPosition(){
		return position;	
	}
	
	public int getJersey(){
		return jersey;	
	}
	
	public int getPointsWins(){
		return pointsWins;	
	}
	
	//utility method
	@Override
	public String toString(){
		return lastName + " | " + pointsWins + " points";
	}
}

public class LambdaExp{ 
	//fields
	private ArrayList<HockeyPlayer> roster;
	private ArrayList<Float> intervals;
	
	//constructor
	public LambdaExp(){
		setRoster();
		setIntervals();
	}
	
	//setters
	public void setRoster(){
		roster = new ArrayList<>();
		try{
			File file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\roster2018PointsWins1_22_2019.txt"); 
    			Scanner sc = new Scanner(file); 
    			int counter = 0;
    			String name = "";
    			String position = "";
    			int jersey = 0;
    			int pointsWins = 0;
    			while (sc.hasNextLine()) {
    				String getData = sc.nextLine();
    				if(counter==0){
    					name = getData.trim();
    				}
    				else if(counter==1){
    					position = getData.trim();	
    				}
    				else if(counter==2){
    					jersey=Integer.parseInt(getData.trim());	
    				}
    				else if(counter==3 ){
    					pointsWins = Integer.parseInt(getData.trim());
				}
				counter++;
				if(getData.equals("*")){
    					HockeyPlayer hp = new HockeyPlayer(name, position, jersey, pointsWins);
    					roster.add(hp);				
    					counter = 0;
    				}
    			}
    		}
    		catch(Exception e){
    			System.out.println("Exception: " + e);
    		}
	}
	

	public void setIntervals(){
		ArrayList<Integer> hpPointsWinsAL = new ArrayList<>();
		for(HockeyPlayer hp: getRoster()){
			hpPointsWinsAL.add(hp.getPointsWins());	
		}
		Collections.sort(hpPointsWinsAL);
		Collections.reverse(hpPointsWinsAL);
		int max = hpPointsWinsAL.get(0);
		float interval = max/4;
		intervals = new ArrayList<>();
		for(float i = 0; i<=max; i+=interval){
			intervals.add(i);	
		}
	}
	
	//getters
	public ArrayList<HockeyPlayer> getRoster(){
		return roster;	
	}
	
	public ArrayList<Float> getIntervals(){
		return intervals;	
	}
	
	//utility methods
	public void subSort(ArrayList<HockeyPlayer> output){
		Collections.sort(output);
		output.forEach(hp -> System.out.println("\t" + hp));
		output.clear();	
	}
	
	public void pointsQuarters(){
		System.out.println("***********************************************************************************************");
		System.out.println("CURRENT ROSTER POINTS QUARTERS WIZARD");
		ArrayList<HockeyPlayer> output = new ArrayList<>();
		
		System.out.println("\n1st Quarter: ");
		getRoster().stream()
		.filter(hp -> (!hp.getPosition().equals("Goalie")) & (hp.getPointsWins() > intervals.get(3)))
		.forEach(hp -> output.add(hp));
		subSort(output);
		
		System.out.println("\n2nd Quarter: ");
		getRoster().stream()
		.filter(hp -> (!hp.getPosition().equals("Goalie")) & (hp.getPointsWins() > intervals.get(2) & hp.getPointsWins() <= intervals.get(3)))
		.forEach(hp -> output.add(hp));
		subSort(output);
		
		System.out.println("\n3rd Quarter:");
		getRoster().stream()
		.filter(hp -> (!hp.getPosition().equals("Goalie")) & (hp.getPointsWins() > intervals.get(1) & hp.getPointsWins() <= intervals.get(2)))
		.forEach(hp -> output.add(hp));
		subSort(output);
		
		System.out.println("\n4th Quarter:");
		getRoster().stream()
		.filter(hp -> (!hp.getPosition().equals("Goalie")) & hp.getPointsWins() <= intervals.get(1))
		.forEach(hp -> output.add(hp));
		subSort(output);
		
		System.out.println("\n**** KEY ****");
		System.out.println("1st Quarter - Players with points greater than " + intervals.get(3) + " and less than or equal to " + intervals.get(4));
		System.out.println("2nd Quarter - Players with points greater than " + intervals.get(2) + " and less than or equal to " + intervals.get(3));
		System.out.println("3rd Quarter - Players with points greater than " + intervals.get(1) + " and less than or equal to " + intervals.get(2));
		System.out.println("4th Quarter - Players with points greater than or equal to " + intervals.get(0) + " and less than or equal to " + intervals.get(1));
		System.out.println("***********************************************************************************************");
	}
	
	public static void main(String[] args) throws Exception{ 
    		LambdaExp le = new LambdaExp();
    		le.pointsQuarters();
      } 
} 