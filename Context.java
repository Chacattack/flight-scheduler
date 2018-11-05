import java.util.ArrayList;

public class Context<E> {
	private HeuristicInterface<E> heuristic;
	
	/**
	 * Class constructor that sets the heuristic type to use
	 * @param h as the heuristic type
	 */
	public Context(HeuristicInterface<E> h){
		this.heuristic = h;
	}
	
	/**
	 * Executes the heuristic and gets the heuristic value
	 * @param g of type Graph as the graph of all nodes and links
	 * @param flights of type ArrayList<Edge<E>> that details which flights are left to be made
	 * @return the heuristic value generated as an int
	 */
	public int executeHeuristic(Graph<E> g, ArrayList<Edge<E>> flights){
		return heuristic.getHeuristic(g, flights);
	}
}
