import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

/* Runtime Analysis of Heuristic Function used
 * My heuristic takes into account how many flights are left remaining as well as their flight paths.
 * From there it adds this onto the heuristic, and also takes into account the delay for stopping in
 * each city to further improve it. On top of this, I also realised that if multiple flights are made
 * from the same city, you are able to add on n-1 * the smallest flight as that would be the minimum
 * in order to get back to the city once flying away. It is admissible because it should always fall short
 * of what the true flight cost is, never overestimating as it only factors in flights left as well as multiple flights
 * As a time complexity, it takes O(2n) = O(n) to calculate the heuristic, where n is equal to the number of flights
 */

public class FlightScheduler {
	static private String input[];
	static private Graph<String> graph = new Graph<String>();
	
	/**
	 * The main function that reads the input and delegates what needs to be done
	 * @param args of Type String[] that reads in program arguments
	 */
	public static void main(String[] args) {
	    Scanner sc = null;
	    try { 
	    	sc = new Scanner(new FileReader(args[0]));	
	    	while (sc.hasNextLine()) {
	    		input = sc.nextLine().split(" ");
	    		if (input[0].equals("City")){
	    			createCity(input[1], Integer.parseInt(input[2]));
	    		} else if (input[0].equals("Time")) {
	    			createConnection(input[1], input[2], Integer.parseInt(input[3]));
	    		} else if (input[0].equals("Flight")) {
	    			scheduleFlights(input[1], input[2]);
	    		} else {
	    			System.err.println("Encountered bad input");
	    		}
	    	}
	    }
	    catch (FileNotFoundException e) {}
	    finally {
	    	if (sc != null){
	    		sc.close();
	    	}
	    	Context<String> c = new Context<String>(new BestHeuristic<String>());
	    	AStarSearch(c);
	    }
	}
	
	/**
	 * Creates a city node and stores it in the graph
	 * @param name of Type String of the location of the city
	 * @param delay of Type int of the time added on for visiting the city
	 */
	private static void createCity(String name, int delay) {
		graph.addNode(name, delay);
	}
	
	/**
	 * Creates an edge between 2 given city nodes
	 * @param city1 of Type String as the location of the first city
	 * @param city2 of Type String as the location of the second city
	 * @param time of Type int as the time taken to travel between the city nodes
	 */
	private static void createConnection(String city1, String city2, int time) {
		graph.addEdge(city1, city2, time);	
	}

	/**
	 * Gets the flight edge from the graph and another area in the graph
	 * @param from of Type String as the location of the city where the flight starts
	 * @param to of Type String as the location of the city where the flight ends
	 */
	private static void scheduleFlights(String from, String to) {
		Edge<String> e = graph.getEdge(from, to);
		graph.addRequiredEdge(e);
	}
	
	/**
	 * Completes an A* Search on the graph created
	 * @param c of Type Context<String> contains details of how to calculate the heuristic value
	 */
	private static void AStarSearch(Context<String> c){
		int nodespopped = 0;
		PriorityQueue<State<String>> openset = new PriorityQueue<State<String>>();
		HashSet<State<String>> closedset = new HashSet<State<String>>();
		
		//creates the initial state
		State<String> start = new State<String>();
		start.setCurrLocation("Sydney");
		start.setGValue(0);
		ArrayList<Edge<String>> requiredFlights = graph.getRequiredFlights();
		for (Edge<String> e: requiredFlights){
			start.addFlight(e);
		}
		start.setHValue(c.executeHeuristic(graph, requiredFlights));
		openset.add(start);
		
		//will loop through each state based on the priority in the queue
		//if emptied, no solution was found
		while (openset.size() != 0){
			State<String> current = openset.remove();
			closedset.add(current);
			nodespopped++;
			
			//goal state has been found
			if (current.flightsLeft() == 0){
				
				//Subtract extra delay from starting location Sydney as it's not needed
				current.setGValue(current.getGValue() - graph.getNode("Sydney").getDelay());
				System.out.println(nodespopped + " nodes expanded");
				System.out.println("cost = " + current.getGValue());
				for (Edge<String> e: current.getPath()){
					System.out.println("Flight " + e.getSource() + " to " + e.getDestination());
				}
				return;
			}
			
			//for each state that can be expanded from the current state
			for (State<String> s: current.getNextStates(graph, c)){
				if (closedset.contains(s)){
					continue;
				}
				int tempGValue = current.getGValue() + s.lastDistance() + graph.getNode(current.getCurrLocation()).getDelay();
				if ((!openset.contains(s)) || tempGValue < s.getGValue()) {
					s.setGValue(tempGValue);
					if (!openset.contains(s)){
						openset.add(s);
					}
				}
			}
		}
	}
}
