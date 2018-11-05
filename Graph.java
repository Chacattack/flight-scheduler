import java.util.ArrayList;
import java.util.HashSet;

public class Graph<E> {
	HashSet<Node<E>> adjacencyList = new HashSet<Node<E>>();
	ArrayList<Edge<E>> requiredEdges = new ArrayList<Edge<E>>();
	
	/**
	 * Adds a node to the graph
	 * @param e of Type E as the location of the node
	 * @param d of Type int as the delay for stopping at the node
	 */
	public void addNode(E e, int d) {
		Node<E> newNode = new Node<E>();
		newNode.setLocation(e);
		newNode.setDelay(d);
		adjacencyList.add(newNode);
	}
	
	/**
	 * Figures out if the graph has the node in question
	 * @param e of Type E as the location of the node
	 * @return whether the node is found as a boolean value, true if found, false otherwise
	 */
	public boolean hasNode(E e){
		Node<E> node = new Node<E>();
		node.setLocation(e);
		if (adjacencyList.contains(node)){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Figures out the smallest edge for a given node
	 * @param city as the location of the node
	 * @return the smallest distance an edge travels as an int
	 */
	public int smallestEdge(E city){
		int smallest = Integer.MAX_VALUE;
		for(Edge<E> e: getNode(city).getNeighbours()){
			if (e.getDistance() < smallest){
				smallest = e.getDistance();
			}
		}
		return smallest;
	}
	
	/**
	 * Adds an edge between two given locations in both directions
	 * @param start of Type E as the start location for the edge
	 * @param end of Type E as the end location for the edge
	 * @param time of Type int as the distance taken to travel along the edge
	 */
	public void addEdge(E start, E end, int time) {
		Node<E> nodeOne = getNode(start);
		Node<E> nodeTwo = getNode(end);
		nodeOne.addEdge(start, end, time);
		nodeTwo.addEdge(end, start, time);
	}
	
	/**
	 * Gets a node from the graph
	 * @param e of Type E as the location of the node
	 * @return the node of Type Node<E> if found, null otherwise
	 */
	public Node<E> getNode(E e){
		for (Node<E> n: adjacencyList){
			if (n.getLocation().equals(e)){
				return n;
			}
		}
		return null;
	}
	
	/**
	 * Gets an edge between two given locations
	 * @param s of Type E as the starting location
	 * @param d of Type E as the ending location
	 * @return the edge between the locations as an Edge<E>
	 */
	public Edge<E> getEdge(E s, E d){
		Node<E> n = getNode(s);
		return n.getEdge(d);
	}
	
	/**
	 * Adds the required flights to a list
	 * @param e of Type Edge<E> representing the flight that needs to be taken
	 */
	public void addRequiredEdge(Edge<E> e){
		requiredEdges.add(e);
	}
	
	/**
	 * Gets the entire flight plan
	 * @return the flight plan as an ArrayList<Edge<E>>
	 */
	public ArrayList<Edge<E>> getRequiredFlights(){
		return requiredEdges;
	}
}
