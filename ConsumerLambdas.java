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
	private int goals;
	private int shots;
	private float shootingPercent;
	
	//constructor
	public Skater(HockeyPlayer hp, int goals, int shots){
		super(hp.getLastName(), hp.getPosition(), hp.getJersey());
		this.goals = goals;
		this.shots = shots;
		this.shootingPercent = (float)0;
	}
	
	//setters
	public void setGoals(int goals){
		this.goals = goals;	
	}
	
	public void setShots(int shots){
		this.shots = shots;	
	}
	
	public void setShootingPercent(float shootingPercent){
		this.shootingPercent = shootingPercent;	
	}
	
	//getters
	public int getGoals(){
		return goals;	
	}
	
	public int getShots(){
		return shots;	
	}
	
	public float getShootingPercent(){
		return shootingPercent;	
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
			File file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\roster2018Goals_Shots_Stats2_13_2019.txt"); 
    			Scanner sc = new Scanner(file); 
    			int counter = 0;
    			String name = "";
    			String position = "";
    			int jersey = 0;
    			int goals = 0;
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
    					String [] gs = getData.trim().split(":");
    					goals = Integer.parseInt(gs[0]);
    					shots = Integer.parseInt(gs[1]);
				}
				counter++;
    				if(getData.equals("*")){
    					if(position.equals("Goalie")){
    						HockeyPlayer hp = new HockeyPlayer(name, position, jersey);
    						roster.add(hp);
    					}
    					else{
    						HockeyPlayer hp = new HockeyPlayer(name, position, jersey);
    						Skater s = new Skater(hp, goals, shots);
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

public class ConsumerLambdas{
	//the BiFunction Functional Interface Function turns two parameters into a value of a potentially different type and returns it
	private static BiFunction<Integer, Integer, Float> shootPer = (g, s) -> {
		if(s == 0){
			return (float)0;
		}
		else{
			return ((float)g / (float)s)*100;
		}		
	};
	
	//the BiConsumer Functional Interface accepts/manipulates two parameters, but does not return anything
	private static BiConsumer<Float, Skater> setShootPer = (sp, sk) -> {
		sk.setShootingPercent(sp);	
	};
	
	//the Predicate Functional Interface may be used to test a condition (often used when filtering or matching)
	private static Predicate<HockeyPlayer> filterOutGoalies = hp -> {
		if(!hp.getPosition().equals("Goalie")){
			return true;	
		}
		else{
			return false;	
		}
	};
	
	//this method relies on Predicate F.I. to filter out goalies, BiFunction F.I. to calculate skater's shooting percentage,
	//and BiConsumer F.I. to set the shootingPercent field of the skater object (in case this value needed later).
	private static void printSKShootPercent(HockeyPlayer hp){
		if(filterOutGoalies.test(hp)){ //calls Predicate
			Skater s = (Skater)hp;
			Float calcSP = shootPer.apply(s.getGoals(), s.getShots()); //calls BiFunction
			setShootPer.accept(calcSP, s); //calls BiConsumer
			System.out.println(String.format("| %-15s | %-15s |", s.getLastName() + "", "S%:  " + s.getShootingPercent()));
		}
	}
	
	//main method that prints (to console) the stat chart of the current roster
	public static void main(String... args){
		Roster r = new Roster();
		ArrayList<HockeyPlayer> team = new ArrayList<>();
		for(int i = 0; i< r.getRosterCount(); i++){
			team.add(r.getHockeyPlayer(i));
		}
		
		System.out.println("\n******* WSH Skaters' Shooting Percentage since 2/13/2019 *******\n");
		team.forEach(hp -> printSKShootPercent(hp));
		System.out.println("\n****************************************************************");
	}
}