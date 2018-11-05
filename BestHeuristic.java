import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BestHeuristic<E> implements HeuristicInterface<E>{
	
	@Override
	/**
	 * Returns a heuristic value based on how many flights need to be made
	 * @param graph of Type Graph<E> representing the nodes and edges
	 * @param flightToMake of Type ArrayList<Edge<E>> that lists all the flights left to make
	 * @return the heuristic value calculated
	 */
	public int getHeuristic(Graph<E> graph, ArrayList<Edge<E>> flightToMake) {
		int heuristic = 0;
		LinkedHashMap<E, Integer> cities = new LinkedHashMap<E, Integer>();
		
		//if no flights to be made we're at the goal state
		if (flightToMake.size() > 0) {
			
			//checks all remaining flights
			for (Edge<E> e: flightToMake){
				
				//checks if multiple flights need to be made out from the same city
				if (cities.containsKey(e.getSource())){
					cities.put(e.getSource(), cities.get(e.getSource()) + 1);
				} else {
					cities.put(e.getSource(), 0);
				}
				
				//gets the node and adds on the distance and delay from one location to the next location
				Node<E> n = graph.getNode(e.getSource());
				heuristic += e.getDistance() + n.getDelay();
			}
			
			//if any flights are multiples from same city, we can add to the gcost another "flight" which
			//is the minimum cost to get back to that city from the closest one in best case scenario
			for (E c: cities.keySet()){
				if (cities.get(c) > 1){
					heuristic += graph.smallestEdge(c) * (cities.get(c) - 1);
				}
			}
			
		}
		return heuristic;
	}

}