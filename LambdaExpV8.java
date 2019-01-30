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

class GoalsThatYear implements Comparable<GoalsThatYear>{
	//fields
	private Integer year;
	private Integer goals;
	
	//constructor
	public GoalsThatYear(Integer year, Integer goals){
		this.year = year;
		this.goals = goals;
	}
	
	@Override
    	public int compareTo(GoalsThatYear other) {
    		if(this.getGoals() > other.getGoals()) {
			return -1;
		}
		else if (this.getGoals() == other.getGoals()) { 
			return 0;
		}
		else{
			return 1;
		}
	}
	
	//setters
	public void setYear(Integer year){
		this.year = year;
	}	
	
	public void setGoals(Integer goals){
		this.goals = goals;	
	}
	
	//getters
	public Integer getYear(){
		return year;	
	}
	
	public Integer getGoals(){
		return goals;	
	}
}

class HockeyPlayer{
	//fields
	private String lastName;
	private Position position;
	private int jerseyNumber;
	private ArrayList<GoalsThatYear> goalsYear;
	
	//constructor
	public HockeyPlayer(String lastName, Position position, int jerseyNumber, ArrayList<GoalsThatYear> goalsYear){
		this.lastName = lastName;
		this.position = position;
		this.jerseyNumber = jerseyNumber;
		setGoalsYear(goalsYear);
	}
	
	//setter
	public void setGoalsYear(ArrayList<GoalsThatYear> goalsYear){
		this.goalsYear = new ArrayList<>(goalsYear);
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
	
	public ArrayList<GoalsThatYear> getGoalsYear(){
		return goalsYear;	
	}
	
	//utility method
	@Override
	public String toString(){
		return lastName + ": " + goalsYear.size() + " year(s) of CAPS seniority.";
	}
}

public class LambdaExpV8{ 
	//fields
	private ArrayList<HockeyPlayer> roster;
	private String op;
	private BufferedWriter bw;
	
	//constructor
	public LambdaExpV8(){
		setRoster();
	}
	
	//setters
	public void setRoster(){
		roster = new ArrayList<>();
		try{
			//.txt file that stores data needed for calculations
			File file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\roster2018GoalsThatYear.txt"); 
    			Scanner sc = new Scanner(file); 
    			int counter = 0;
    			String name = "";
    			Position position = null;
    			int jersey = 0;
    			GoalsThatYear gty = null;
    			ArrayList<GoalsThatYear> goalsYear = new ArrayList<>();
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
    				else if(counter>=3 && (!getData.equals("*"))){
    					String [] yg = getData.trim().split(":");
    					Integer year = Integer.parseInt(yg[0]);
    					Integer goals = Integer.parseInt(yg[1]);
    					gty = new GoalsThatYear(year, goals);
    					goalsYear.add(gty);
				}
				counter++;
    				if(getData.equals("*")){
    					HockeyPlayer hp = new HockeyPlayer(name, position, jersey, goalsYear);
    					roster.add(hp);  					
    					counter = 0;
    					goalsYear.clear();
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
	
	int counter = 0;
	int ctr = 0;
	String selectedPlayer;
	//utility method	
	public void rosterStatsOutput(){
		//sort last name
		Comparator<HockeyPlayer> sortLastName = new Comparator<HockeyPlayer>(){
			public int compare(HockeyPlayer h1, HockeyPlayer h2){
				return h1.getLastName().compareTo(h2.getLastName());
			}
		};
		System.out.println("******************************************************************");
		System.out.println("CURRENT ROSTER WSH CAREER GOALS *SCATTER PLOT* WIZARD");
		System.out.println("\nMAIN MENU:\n");
		System.out.println("Select a Current Player");
		Collections.sort(getRoster(), sortLastName);
		getRoster().forEach(hp -> {System.out.println(counter+1+") " + hp.getLastName());
				counter++;
		});
		System.out.println("\n\n" + (counter +1) + ".) EXIT\n");
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter number of selection: ");
			int userChoice = Integer.parseInt(reader.readLine());
			if(userChoice == getRoster().size() + 1){
				System.out.println("You selected: EXIT");
				System.exit(0);
			}
			else if(userChoice <= getRoster().size()){
				selectedPlayer = getRoster().get(userChoice-1).getLastName();
				System.out.println(selectedPlayer + " WSH Career Goals Scatter Plot: " );
			}
		}
		catch(Exception e){
			System.out.println("Exception: " + e + ".");
		}
		
		try {
		//.txt file that will store the graphed output
		File file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\roster2018CareerGoalsGraphOutput.txt");
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		  
		op = "******************************************************************";
		bw.write(op);
		bw.newLine();
		op = "CURRENT ROSTER WSH CAREER GOALS *SCATTER PLOT* WIZARD";
		bw.write(op);
		bw.newLine();
		op = selectedPlayer + " WSH Career Goals Scatter Plot: ";
		bw.write(op);
		bw.newLine();
		ArrayList<HockeyPlayer> output = new ArrayList<>();
		
		getRoster().stream()
		.filter(hp -> (hp.getLastName().equals(selectedPlayer)))
		.forEach(hp -> output.add(hp));
		
		ArrayList<GoalsThatYear> goalsYr = output.get(0).getGoalsYear();
		ArrayList<GoalsThatYear> yrsAsc = new ArrayList<>();
		goalsYr.forEach(y -> yrsAsc.add(y));
		
		op = "______________";
		bw.write(op);
		System.out.print(op);
		
		//pass in a lambda expression
		goalsYr.forEach(y -> {
		try{
			op = "______"; 
			bw.write(op); 
			System.out.print(op);
		}
		catch(Exception e){
			System.out.println("Exception " + e);	
		}
		});	
		
		bw.newLine();
		System.out.println();
		
		//print x-axis labels
		op = String.format("%-12s | ", "Goals \\ Year");
		bw.write(op);
		System.out.print(op);
		goalsYr.forEach(year -> {
		try{
			String y = Integer.toString(year.getYear());
			op = String.format("%-2s | ", "'" + y.substring(2));
			bw.write(op);
			System.out.print(op);
		}
		catch(Exception e){
			System.out.println("Exception " + e);	
		}
		});	
		
		bw.newLine();
		System.out.println();
		
		op = "______________";
		bw.write(op);
		System.out.print(op);
		
		//pass in a lambda expression
		goalsYr.forEach(y -> {
		try{
			op = "______"; 
			bw.write(op); 
			System.out.print(op);
		}
		catch(Exception e){
			System.out.println("Exception " + e);	
		}
		});	
		
		bw.newLine();
		System.out.println();
		
		//print y-axis labels and plot graph
		Collections.sort(goalsYr);
		GoalsThatYear goalsMaxObj = goalsYr.get(0);
		
		for(int i = goalsMaxObj.getGoals() + 1; i > -1; i--){
		ctr = i;
		try{
			op = String.format("%-5s | ", "\t" + i);
			bw.write(op);
			System.out.print(op);
			
			yrsAsc.forEach(y ->{
				try{
					if(y.getGoals() == ctr){
						op = String.format("%-2s  . ", " X");
						bw.write(op);
						System.out.print(op);
					}
					else{
						op = String.format("%-2s  . ", "  ");
						bw.write(op);
						System.out.print(op);
					}	
				}
				catch(Exception e){
					System.out.println("Exception " + e);	
				}
			});	
		
			bw.newLine();
			System.out.println();
		
			op = "______________";
			bw.write(op);
			System.out.print(op);
		
			//pass in a lambda expression
			yrsAsc.forEach(y -> {
				try{
					op = "______"; 
					bw.write(op); 
					System.out.print(op);
				}
				catch(Exception e){
					System.out.println("Exception " + e);	
				}
			});	
		
			bw.newLine();
			System.out.println();
		}
		catch(Exception e){
			System.out.println("Exception " + e);	
		}
		}
		
		//print x-axis labels again for readability
		op = String.format("%-12s | ", "Goals \\ Year");
		bw.write(op);
		System.out.print(op);
		yrsAsc.forEach(year -> {
		try{
			String y = Integer.toString(year.getYear());
			op = String.format("%-2s | ", "'" + y.substring(2));
			bw.write(op);
			System.out.print(op);
		}
		catch(Exception e){
			System.out.println("Exception " + e);	
		}
		});	
		
		bw.newLine();
		System.out.println();
		
		op = "______________";
		bw.write(op);
		System.out.print(op);
		
		//pass in a lambda expression
		goalsYr.forEach(y -> {
		try{
			op = "______"; 
			bw.write(op); 
			System.out.print(op);
		}
		catch(Exception e){
			System.out.println("Exception " + e);	
		}
		});	
		
		bw.newLine();
		System.out.println();
		
		bw.newLine();
		System.out.println();
		
		bw.newLine();
		bw.newLine();
		System.out.println("\n\n");
		  
		
		System.out.println("*" + selectedPlayer + " WSH Career Goals Scatter Plot File Written Successfully");
		
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally
		{ 
		   try{
			  if(bw!=null)
			 bw.close();
		   }
		   catch(Exception ex){
			   System.out.println("Error in closing the BufferedWriter"+ex);
		   }
		}
		
	}
	
	public static void main(String[] args) throws Exception{ 
    		LambdaExpV8 le = new LambdaExpV8();
    		le.rosterStatsOutput();
      } 
} 