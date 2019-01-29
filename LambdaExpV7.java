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

class HockeyPlayer implements Comparable<HockeyPlayer>{
	//fields
	private String lastName;
	private Position position;
	private int jerseyNumber;
	private ArrayList<Integer> yearsWith;
	
	//constructor
	public HockeyPlayer(String lastName, Position position, int jerseyNumber, ArrayList<Integer> yearsWith){
		this.lastName = lastName;
		this.position = position;
		this.jerseyNumber = jerseyNumber;
		setYearsWith(yearsWith);
	}
	
	@Override
    	public int compareTo(HockeyPlayer other) {
    		if(this.getYearsWith().size() < other.getYearsWith().size()) {
			return 1;
		}
		else if (this.getYearsWith().size() == other.getYearsWith().size()) { 
			return 0;
		}
		else{
			return -1;
		}
	}
	
	//setter
	public void setYearsWith(ArrayList<Integer> yearsWith){
		this.yearsWith = new ArrayList<>(yearsWith);
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
	
	public ArrayList<Integer> getYearsWith(){
		return yearsWith;	
	}
	
	//utility method
	@Override
	public String toString(){
		return lastName + ": " + yearsWith.size() + " year(s) of CAPS seniority.";
	}
}

public class LambdaExpV7{ 
	//fields
	private ArrayList<HockeyPlayer> roster;
	private String op;
	private BufferedWriter bw;
	
	//constructor
	public LambdaExpV7(){
		setRoster();
	}
	
	//setters
	public void setRoster(){
		roster = new ArrayList<>();
		try{
			//.txt file that stores data needed for calculations
			File file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\roster2018SeniorityBarGraph.txt"); 
    			Scanner sc = new Scanner(file); 
    			int counter = 0;
    			String name = "";
    			Position position = null;
    			int jersey = 0;
    			int year = 0;
    			ArrayList<Integer> years = new ArrayList<>();
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
    					year = Integer.parseInt(getData.trim());
    					years.add(year);
				}
				counter++;
    				if(getData.equals("*")){
    					HockeyPlayer hp = new HockeyPlayer(name, position, jersey, years);
    					roster.add(hp);  					
    					counter = 0;
    					years.clear();
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
	
	//utility method	
	public void rosterStatsOutput(){
		try {
		//.txt file that will store the calculated output
		File file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\roster2018SeniorityBarGraphOutput.txt");
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		  
		op = "******************************************************************";
		bw.write(op);
		bw.newLine();
		System.out.println(op);
		op = "CURRENT ROSTER SENIORITY *HORIZONTAL BAR GRAPH* WIZARD";
		bw.write(op);
		bw.newLine();
		System.out.println(op);
		op = "All Current Players Seniority Horizontal Bar Graph: ";
		bw.write(op);
		bw.newLine();
		System.out.println(op); 
		
		//sort last name
		Comparator<HockeyPlayer> sortLastName = new Comparator<HockeyPlayer>(){
			public int compare(HockeyPlayer h1, HockeyPlayer h2){
				return h1.getLastName().compareTo(h2.getLastName());
			}
		};
		
		//sort roster by player with largest yearsWith value
		Collections.sort(getRoster());
		//use that arrayList to provide avaYears data
		ArrayList<Integer> avaYears = getRoster().get(0).getYearsWith();
		Collections.reverse(avaYears);
		
		op = "______________";
		bw.write(op);
		System.out.print(op);
		
		//pass in a lambda expression
		avaYears.forEach(y -> {
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
		op = String.format("%-12s | ", "Player");
		bw.write(op);
		System.out.print(op);
		avaYears.forEach(year -> {
		try{
			String y = Integer.toString(year);
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
		avaYears.forEach(y -> {
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
		Collections.sort(getRoster(), sortLastName);
		
		getRoster().forEach(hp -> {
		try{
			op = String.format("%-12s | ", hp.getLastName());
			bw.write(op);
			System.out.print(op);
			avaYears.forEach(y ->{
				try{
					if(hp.getYearsWith().contains(y)){
						op = String.format("%-2s   ", " X ");
						bw.write(op);
						System.out.print(op);
					}
					else{
						op = String.format("%-2s   ", "");
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
			avaYears.forEach(y -> {
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
		});	
		//print x-axis labels again for readability
		op = String.format("%-12s | ", "Player");
		bw.write(op);
		System.out.print(op);
		avaYears.forEach(year -> {
		try{
			String y = Integer.toString(year);
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
		avaYears.forEach(y -> {
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
		bw.newLine();
		System.out.println("\n\n");
		  
		 
		System.out.println("*Stats Chart File Written Successfully");
		
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
    		LambdaExpV7 le = new LambdaExpV7();
    		le.rosterStatsOutput();
      } 
} 