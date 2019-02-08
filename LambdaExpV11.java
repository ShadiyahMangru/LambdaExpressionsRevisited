import java.io.*; 
import java.util.*;
import java.util.Map;

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
	private TreeMap<Integer, Integer> goalsThatYear;
	
	//constructor
	public HockeyPlayer(String lastName, Position position, int jerseyNumber, TreeMap<Integer, Integer> goalsThatYear){
		this.lastName = lastName;
		this.position = position;
		this.jerseyNumber = jerseyNumber;
		setGoalsThatYear(goalsThatYear);
	}
	
	//setter
	public void setGoalsThatYear(TreeMap <Integer, Integer> goalsThatYear){
		this.goalsThatYear = new TreeMap<Integer, Integer>(goalsThatYear);
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
	
	public TreeMap<Integer, Integer> getGoalsThatYear(){
		return goalsThatYear;	
	}
	
	//utility method
	@Override
	public String toString(){
		return lastName + ": " + goalsThatYear.size() + " year(s) of CAPS seniority.";
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
			File file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\roster2018GoalsThatYear.txt"); 
    			Scanner sc = new Scanner(file); 
    			int counter = 0;
    			String name = "";
    			Position position = null;
    			int jersey = 0;
    			TreeMap goalsThatYear = new TreeMap<Integer, Integer>(); //<year, goals>
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
    					goalsThatYear.put(year, goals);
				}
				counter++;
    				if(getData.equals("*")){
    					HockeyPlayer hp = new HockeyPlayer(name, position, jersey, goalsThatYear);
    					roster.add(hp);  					
    					counter = 0;
    					goalsThatYear.clear();
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

public class LambdaExpV11{ 
	//fields
	private Roster roster;
	private Comparator<HockeyPlayer> sortLastName;
	private String op;
	private BufferedWriter bw;
	private String selectedPlayer;
	private TreeMap<Integer, Integer> goalsYr;
	
	//constructor
	public LambdaExpV11(){
		roster = new Roster();
		setSortLastName();
	}
	
	//setters
	public void setSortLastName(){
		sortLastName = new Comparator<HockeyPlayer>(){
			public int compare(HockeyPlayer h1, HockeyPlayer h2){
				return h1.getLastName().compareTo(h2.getLastName());
			}
		};
	}
	
	public void setGoalsYr(TreeMap<Integer, Integer> gy){
		goalsYr = new TreeMap<>(gy);	
	}
	
	//getters
	public Roster getRoster(){
		return roster;	
	}
	
	public Comparator<HockeyPlayer> getSortLastName(){
		return sortLastName;	
	}
	
	public TreeMap<Integer, Integer> getGoalsYr(){
		return goalsYr;	
	}
	
	//utility methods
	public void printToFile(String op){
		try{
			bw.write(op);
			bw.newLine();
		}
		catch(Exception e){
			System.out.println("Exception: " + e);	
		}
	}
	
	public void printToFileAndConsole(String op){
		try{
			bw.write(op);
			System.out.print(op);	
		}
		catch(Exception e){
			System.out.println("Exception: " + e);	
		}
	}
	
	public void printYAxisPlotGraph(){
		Map<Integer, Integer> sortedMap = sortByValues(goalsYr);
		List<Integer> HPGoals = new ArrayList<>(sortedMap.values());  	
		Integer goalsMaxVal = HPGoals.get(0);
		
		List<Integer> yAxis = new ArrayList<>();
		for(int i = goalsMaxVal + 1; i > -1; i--){
			yAxis.add(i);	
		}
		
		//calls (IndexDecrement)Consumer Functional Interface in Lambda Expression
		forEach(yAxis, (goalsMaxVal + 1), (elem, i) -> { 
		try{
			op = String.format("%-5s | ", "\t" + i);
			printToFileAndConsole(op);
			
			for(Map.Entry<Integer,Integer> entry : goalsYr.entrySet()) {
				if(entry.getValue() == i){ 
					op = String.format("%-2s  . ", " X");
					printToFileAndConsole(op);
				}
				else{
					op = String.format("%-2s  . ", "  ");
					printToFileAndConsole(op);
				}	
			}
		
			bw.newLine();
			System.out.println();
			op = "______________";
			printToFileAndConsole(op);
		
			for(Map.Entry<Integer,Integer> entry : goalsYr.entrySet()) {
				op = "______"; 
				printToFileAndConsole(op);
			}	
		
			bw.newLine();
			System.out.println();
		}
		catch(Exception e){
			System.out.println("Exception " + e);	
		}
		});
	}
	
	public void printXAxis(){
		op = String.format("%-12s | ", "Goals \\ Year");
		printToFileAndConsole(op);
		try{
			
			for(Map.Entry<Integer,Integer> entry : goalsYr.entrySet()) {
				String y = Integer.toString(entry.getKey());
				op = String.format("%-2s | ", "'" + y.substring(2));
				printToFileAndConsole(op);
			}	
			
			bw.newLine();
			System.out.println();
			op = "______________";
			printToFileAndConsole(op);
			
			for(Map.Entry<Integer,Integer> entry : goalsYr.entrySet()) {
				op = "______"; 
				printToFileAndConsole(op);
			}
			bw.newLine();
		}
		catch(Exception e){
			System.out.println("Exception " + e);	
		}
		System.out.println();	
	}
	
	//to sort treemap
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k2).compareTo(map.get(k1));
					if (compare == 0) {
						return 1;
					}
				return compare;
			}
		};
 
		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
    		sortedByValues.putAll(map);
    		return sortedByValues;
    	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
	//Consumer Functional Interface!
	@FunctionalInterface
	public interface IndexIncrementConsumer<T> {
		void accept(T t, int i);
	}
	public static <T> void forEach(Collection<T> collection, IndexIncrementConsumer<T> consumer) {
		int index = 0;
		for (T object : collection){
			consumer.accept(object, index++);
		}
	}
	
	//Consumer Functional Interface!
	@FunctionalInterface
	public interface IndexDecrementConsumer<T> {
		void accept(T t, int i);
	}
	public static <T> void forEach(Collection<T> collection, int maxVal, IndexDecrementConsumer<T> consumer) {
		int index = maxVal;
		for (T object : collection){
			consumer.accept(object, index--);
		}
	}
	
	public void rosterStatsOutput(){
		System.out.println("******************************************************************");
		System.out.println("CURRENT ROSTER WSH CAREER GOALS *SCATTER PLOT* WIZARD");
		System.out.println("\nMAIN MENU:\n");
		System.out.println("Select a Current Player");
		ArrayList<HockeyPlayer> team = new ArrayList<>();
		for(int i = 0; i<roster.getRosterCount(); i++){
			team.add(roster.getHockeyPlayer(i));	
		}
		Collections.sort(team, sortLastName);
		//calls (IndexIncrement)Consumer Functional Interface in Lambda Expression
		forEach(team, (hp, i) -> System.out.println("\t" + (i+1) + ".) " + hp.getLastName()));
		System.out.println("\n\n\t0.) EXIT\n");
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter number of selection: ");
			int userChoice = Integer.parseInt(reader.readLine());
			if(userChoice == 0){
				System.out.println("You selected: EXIT");
				System.exit(0);
			}
			else if(userChoice <= team.size()){
				selectedPlayer = team.get(userChoice-1).getLastName();
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
		printToFile(op);
		op = "CURRENT ROSTER WSH CAREER GOALS *SCATTER PLOT* WIZARD";
		printToFile(op);
		op = selectedPlayer + " WSH Career Goals Scatter Plot: ";
		printToFile(op);
		ArrayList<HockeyPlayer> output = new ArrayList<>();
		
		team.stream()
		.filter(hp -> (hp.getLastName().equals(selectedPlayer)))
		.forEach(hp -> output.add(hp));
		
		setGoalsYr(output.get(0).getGoalsThatYear()); 
		op = "______________";
		printToFileAndConsole(op);
		for(Map.Entry<Integer,Integer> entry : goalsYr.entrySet()) {
			op = "______"; 
			printToFileAndConsole(op);
		}	
		bw.newLine();
		System.out.println();
		
		//print x-axis labels
		printXAxis();
		//print y-axis labels and plot graph
		printYAxisPlotGraph();
		//print x-axis labels again for readability
		printXAxis();
		
		bw.newLine();
		bw.newLine();
		bw.newLine();
		System.out.println("\n\n\n");
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
    		LambdaExpV11 le = new LambdaExpV11();
    		le.rosterStatsOutput();
      } 
} 