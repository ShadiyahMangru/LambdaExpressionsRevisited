import java.io.*; 
import java.util.*;

//enum bc finite set of position values
enum Position{
	RIGHT_WING{
		public String toString(){
			return "Right Wing";	
		}
	}, 
	CENTER{
		public String toString(){
			return "Center";	
		}
	}, 
	LEFT_WING{
		public String toString(){
			return "Left Wing";	
		}
	}, 
	DEFENSE{
		public String toString(){
			return "Defense";	
		}
	}, 
	GOALIE{
		public String toString(){
			return "Goalie";	
		}
	}, 
	UNDEFINED{
		public String toString(){
			return "Undefined";	
		}
	}
}

class HockeyPlayer{
	//fields
	private String lastName;
	private Position position;
	private int jerseyNumber;
	private int gamesPlayed;
	
	//constructor
	public HockeyPlayer(String lastName, Position position, int jerseyNumber, int gamesPlayed){
		this.lastName = lastName;
		this.position = position;
		this.jerseyNumber = jerseyNumber;
		this.gamesPlayed = gamesPlayed;
	}
	
	//setter
	public void setGamesPlayed(int gamesPlayed){
		this.gamesPlayed = gamesPlayed;	
	}
	
	//getters
	public String getLastName(){
		return lastName;	
	}
	
	public Position getPosition(){
		return position;	
	}
	
	public int getJerseyNumber(){
		return jerseyNumber;	
	}
	
	public int getGamesPlayed(){
		return gamesPlayed;	
	}
	
}

class Skater extends HockeyPlayer{
	//fields
	private int goals;
	private int shots;
	private ShootingPer shootingP; //bc Skater (Forward & Defense) 'has-a' shooting percentage
	
	//constructor
	public Skater(HockeyPlayer hp, int goals, int shots){
		super(hp.getLastName(), hp.getPosition(), hp.getJerseyNumber(), hp.getGamesPlayed());
		this.goals = goals;
		this.shots = shots;
		this.shootingP = new ShootingPer(goals, shots);
	}
	
	//setters
	public void setGoals(int goals){
		this.goals = goals;	
	}
	
	public void setShots(int shots){
		this.shots = shots;	
	}
	
	public void setShootingP(int goals, int shots){
		shootingP = new ShootingPer(goals, shots);
	}
	
	//getters
	public int getGoals(){
		return goals;	
	}
	
	public int getShots(){
		return shots;	
	}
	
	public ShootingPer getShootingP(){
		return shootingP;	
	}
	
	@Override
	public String toString(){
		return String.format("%-15s | %-15s | %-5s | %-6s | %-6s | %-6s | %-8s ", getLastName(), getPosition(), getJerseyNumber(), getGamesPlayed(), goals, shots, shootingP.getShootingPercentage());	
	}
}

class Goalie extends HockeyPlayer{
	//fields
	private int saves;
	private int shotsAg;
	private SavePer saveP; //bc Goalie 'has-a' save percentage
	
	//constructor
	public Goalie(HockeyPlayer hp, int saves, int shotsAg){
		super(hp.getLastName(), hp.getPosition(), hp.getJerseyNumber(), hp.getGamesPlayed());
		this.saves = saves;
		this.shotsAg = shotsAg;
		this.saveP = new SavePer(saves, shotsAg);
	}
	
	//setters
	public void setSaves(int saves){
		this.saves = saves;	
	}
	
	public void setShotsAg(int shotsAg){
		this.shotsAg = shotsAg;	
	}
	
	public void setSaveP(int saves, int shotsAg){
		saveP = new SavePer(saves, shotsAg);
	}
	
	//getters
	public int getSaves(){
		return saves;	
	}
	
	public int getShotsAg(){
		return shotsAg;	
	}
	
	public SavePer getSaveP(){
		return saveP;	
	}
	
	@Override
	public String toString(){
		return String.format("%-15s | %-15s | %-5s | %-6s | %-6s | %-6s | %-8s ", getLastName(), getPosition(), getJerseyNumber(), getGamesPlayed(), saves, shotsAg, saveP.getSavePercentage());
	}
}

class ShootingPer{
	//fields
	private int goals;
	private int shots;
	private float shootingPercentage;
	
	//constructor
	public ShootingPer(int goals, int shots){
		this.goals = goals;
		this.shots = shots;
		setShootingPercentage();
	}
	
	//setters
	public void setGoals(int goals){
		this.goals = goals;	
	}
	
	public void setShots(int shots){
		this.shots = shots;	
	}
	
	public void setShootingPercentage(){
		if(shots == 0){
			shootingPercentage = 0;	
		}
		else{
			shootingPercentage = ((float)goals/(float)shots)*100;	
		}
	}
	
	//getters
	public int getGoals(){
		return goals;	
	}
	
	public int getShots(){
		return shots;	
	}
	
	public float getShootingPercentage(){
		return shootingPercentage;	
	}
	
}

class SavePer{
	//fields
	private int saves;
	private int shotsAg;
	private float savePercentage;
	
	//constructor
	public SavePer(int saves, int shotsAg){
		this.saves = saves;
		this.shotsAg = shotsAg;
		setSavePercentage();
	}
	
	//setters
	public void setSaves(int saves){
		this.saves = saves;	
	}
	
	public void setShotsAg(int shotsAg){
		this.shotsAg = shotsAg;	
	}
	
	public void setSavePercentage(){
		if(shotsAg == 0){
			savePercentage = 0;	
		}
		else{
			savePercentage = (float)saves / (float)shotsAg;	
		}
	}
	
	//getters
	public int getSaves(){
		return saves;	
	}
	
	public int getShotsAg(){
		return shotsAg;	
	}
	
	public float getSavePercentage(){
		return savePercentage;	
	}
}

public class LambdaExpV5{ 
	//fields
	private ArrayList<HockeyPlayer> roster;
	
	//constructor
	public LambdaExpV5(){
		setRoster();
	}
	
	//setters
	public void setRoster(){
		roster = new ArrayList<>();
		try{
			File file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\roster2018Stats1_24_2019.txt"); 
    			Scanner sc = new Scanner(file); 
    			int counter = 0;
    			String name = "";
    			Position position = null;
    			int jersey = 0;
    			int gamesPlayed = 0;
    			int goalsSaves = 0;
    			int shotsShotsAg = 0;
    			while (sc.hasNextLine()) {
    				String getData = sc.nextLine();
    				if(counter==0){
    					name = getData.trim();
    				}
    				else if(counter==1){
    					String p = getData.trim();	
    					switch(p){
    					case "Right Wing":
    						position = Position.RIGHT_WING;	
    						break;
    					case "Left Wing":
    						position = Position.LEFT_WING;		
    						break;
    					case "Center":
    						position = Position.CENTER;		
    						break;
    					case "Goalie":
    						position = Position.GOALIE;		
    						break;
    					case "Defense":
    						position = Position.DEFENSE;		
    						break;
    					default:
    						position = Position.UNDEFINED;	
    					}
    				}
    				else if(counter==2){
    					jersey=Integer.parseInt(getData.trim());	
    				}
    				else if(counter==3 ){
    					gamesPlayed = Integer.parseInt(getData.trim());
				}
				else if(counter==4){
					goalsSaves = Integer.parseInt(getData.trim());	
				}
				else if(counter == 5){
					shotsShotsAg = Integer.parseInt(getData.trim());	
				}
				counter++;
				if(getData.equals("*")){
					HockeyPlayer hp = new HockeyPlayer(name, position, jersey, gamesPlayed);
					if(position.equals(Position.GOALIE)){
						Goalie p = new Goalie(hp, goalsSaves, shotsShotsAg);
						roster.add(p);
    					}
    					else{
    						Skater p = new Skater(hp, goalsSaves, shotsShotsAg);
    						roster.add(p);				
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
	public ArrayList<HockeyPlayer> getRoster(){
		return roster;	
	}
	
	//utility methods
	public void subSort(ArrayList<HockeyPlayer> output){
		//sort by gamesPlayed, if a tie, sort by last name
		Comparator<HockeyPlayer> ptsThenName = new Comparator<HockeyPlayer>(){
			public int compare(HockeyPlayer h1, HockeyPlayer h2){
				int result = h2.getGamesPlayed() - h1.getGamesPlayed();
				if(result != 0){
					return result;	
				}
				return h1.getLastName().compareTo(h2.getLastName());
			}
		};
		Collections.sort(output, ptsThenName);
		output.forEach(hp -> System.out.println("\t" + hp));
		output.clear();	
	}
	
	public void rosterStatsOutput(){
		System.out.println("***********************************************************************************************");
		System.out.println("CURRENT ROSTER STATS WIZARD");
		ArrayList<HockeyPlayer> output = new ArrayList<>();
		
		System.out.println("\nFORWARDS AND DEFENSE: ");
		System.out.println("\t" + String.format("%-15s | %-15s | %-5s | %-6s | %-6s | %-6s | %-8s ", "Name", "Position", "#", "GP", "G", "S", "S%"));
		System.out.println("\t---------------------------------------------------------------------------------");
		getRoster().stream()
		.filter(hp -> (!hp.getPosition().equals(Position.GOALIE)) )
		.forEach(hp -> output.add(hp));
		subSort(output);
		
		System.out.println("\nGOALIES: ");
		System.out.println("\t" + String.format("%-15s | %-15s | %-5s | %-6s | %-6s | %-6s | %-8s ", "Name", "Position", "#", "GP", "SV", "SA", "Sv%"));
		System.out.println("\t---------------------------------------------------------------------------------");
		getRoster().stream()
		.filter(hp -> (hp.getPosition().equals(Position.GOALIE)) )
		.forEach(hp -> output.add(hp));
		subSort(output);
		
		System.out.println("***********************************************************************************************");
	}
	
	public static void main(String[] args) throws Exception{ 
    		LambdaExpV5 le = new LambdaExpV5();
    		le.rosterStatsOutput();
      } 
} 