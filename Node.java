import java.util.HashSet;

public class Node<E> {
	private E location;
	private int delay;
	private HashSet<Edge<E>> edges = new HashSet<Edge<E>>();
	
	/**
	 * Sets the location for the node
	 * @param s of type E as the name of the location
	 */
	public void setLocation(E s){
		this.location = s;
	}
	
	/**
	 * Sets the delay for stopping at the node
	 * @param i of type int as the time
	 */
	public void setDelay(int i){
		this.delay = i;
	}
	
	/**
	 * Gets the location name of the node
	 * @return the locations as type E
	 */
	public E getLocation(){
		return this.location;
	}
	
	/**
	 * Gets the delay for stopping at the node
	 * @return the delay time as an int
	 */
	public int getDelay(){
		return this.delay;
	}
	
	/**
	 * Adds an edge between two given cities of type E, as well as a time to fly
	 * @param s of type E as the name of city1
	 * @param d of type E as the name of city2
	 * @param time of type int as the flight time between city1 and city2
	 */
	public void addEdge(E s, E d, int time){
		Edge<E> edge = new Edge<E>();
		edge.setSource(s);
		edge.setDestination(d);
		edge.setDistance(time);
		edges.add(edge);
	}
	
	/**
	 * Gets an edge from current node to a given city
	 * @param d of type E as the name of the city
	 * @return the edge of Type Edge<E> of the edge from the current city to the given city, null if not found
	 */
	public Edge<E> getEdge(E d){
		for (Edge<E> e: edges){
			if (e.getDestination().equals(d)){
				return e;
			}
		}
		return null;
	}
	/**
	 * Gets a set of all edges for a given node
	 * @return edges as HashSet<E> of all edges associated with the current city
	 */
	public HashSet<Edge<E>> getNeighbours(){
		return edges;
	}
}