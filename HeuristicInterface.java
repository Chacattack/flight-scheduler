import java.util.ArrayList;

public interface HeuristicInterface<E> {
	/**
	 * Gets the heuristic value for a particular state
	 * @param graph of Type Graph<E> which is the graph of all the nodes
	 * @param flightsToMake of Type ArrayList<Edge<E>> which is the flights left to make
	 * @return the heuristic value of type int
	 */
	public int getHeuristic(Graph<E> graph, ArrayList<Edge<E>> flightsToMake);
}
