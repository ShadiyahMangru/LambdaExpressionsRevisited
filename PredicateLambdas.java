import java.io.*;
import java.util.*;
import java.util.function.*;

class HockeyPlayer{
	//fields
	private String lastName;
	private String position;
	private int jersey;

	//constructor
	public HockeyPlayer(String lastName, String position, int jersey){
		this.lastName = lastName;
		this.position = position;
		this.jersey = jersey;
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
}

class Skater extends HockeyPlayer{
	//fields
	private int gamesPlayed;
	private int shots;
	
	//constructor
	public Skater(HockeyPlayer hp, int gamesPlayed, int shots){
		super(hp.getLastName(), hp.getPosition(), hp.getJersey());
		this.gamesPlayed = gamesPlayed;
		this.shots = shots;
	}
	
	//setters
	public void setGamesPlayed(int gamesPlayed){
		this.gamesPlayed = gamesPlayed;	
	}
	
	public void setShots(int shots){
		this.shots = shots;	
	}
	
	//getters
	public int getGamesPlayed(){
		return gamesPlayed;	
	}
	
	public int getShots(){
		return shots;	
	}
}

class Roster{
	//fields
	private ArrayList<HockeyPlayer> roster;
	
	//constructor
	public Roster(){	
		setRoster();
	}
	
	//setter
	public void setRoster(){
		roster = new ArrayList<>();
		try{
			//.txt file that stores data needed for calculations
			File file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\roster2018GP_Shots_Stats2_12_2019.txt"); 
    			Scanner sc = new Scanner(file); 
    			int counter = 0;
    			String name = "";
    			String position = "";
    			int jersey = 0;
    			int gamesPlayed = 0;
    			int shots = 0;
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
    				else if(counter>=3 && (!getData.equals("*")) && !position.equals("Goalie")){
    					String [] gps = getData.trim().split(":");
    					gamesPlayed = Integer.parseInt(gps[0]);
    					shots = Integer.parseInt(gps[1]);
				}
				counter++;
    				if(getData.equals("*")){
    					if(position.equals("Goalie")){
    						HockeyPlayer hp = new HockeyPlayer(name, position, jersey);
    						roster.add(hp);
    					}
    					else{
    						HockeyPlayer hp = new HockeyPlayer(name, position, jersey);
    						Skater s = new Skater(hp, gamesPlayed, shots);
    						roster.add(s);
    					}
    					counter = 0;
    				}
    			}
    		}
    		catch(Exception e){
    			System.out.println("Exception: " + e);
    		}
	}
	
	//getters
	public int getRosterCount(){
		return roster.size();	
	}
	
	public HockeyPlayer getHockeyPlayer(int index){
		return roster.get(index);	
	}
}

public class PredicateLambdas{
	private static void printSkaterGP(HockeyPlayer hp, Predicate<HockeyPlayer> trait){
		if(trait.test(hp)){
			Skater s = (Skater)hp;
			System.out.println(s.getLastName() + "'s GP: " + s.getGamesPlayed());
		}
	}
	
	public static void main(String... args){
		Roster r = new Roster();
		ArrayList<HockeyPlayer> team = new ArrayList<>();
		for(int i = 0; i< r.getRosterCount(); i++){
			team.add(r.getHockeyPlayer(i));
		}
		
		//use Predicate to test a condition (e.g., 'is player a Goalie?')
		team.forEach(player -> printSkaterGP(player, hp -> !hp.getPosition().equals("Goalie")));
	}
}