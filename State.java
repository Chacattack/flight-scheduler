import java.util.ArrayList;
import java.util.HashSet;

public class State<E> implements Comparable<State<E>> {
	private ArrayList<Edge<E>> flights = new ArrayList<Edge<E>>();
	private E currLocation;
	private int gvalue;
	private int hvalue;
	private ArrayList<Edge<E>> path = new ArrayList<Edge<E>>();
	
	/**
	 * Sets the heuristic of the state
	 * @param h of Type int as the heuristic
	 */
	public void setHValue(int h){
		this.hvalue = h;
	}
	
	/**
	 * Sets the cost so far for the state
	 * @param g of Type int as the cost
	 */
	public void setGValue(int g){
		this.gvalue = g;
	}
	
	/**
	 * Sets the current location
	 * @param l of Type E as the current node
	 */
	public void setCurrLocation(E l){
		this.currLocation = l;
	}
	
	/**
	 * Adds flights needed to be made
	 * @param f of Type Edge<E> representing which paths still need to be taken
	 */
	public void addFlight(Edge<E> f){
		this.flights.add(f);
	}
	
	/**
	 * Removes flights that have been taken
	 * @param f of Type Edge<E> representing an edge no longer needed
	 */
	public void removeFlight(Edge<E> f){
		this.flights.remove(f);
	}
	
	/**
	 * Gets the current cost so far that the state has moved from the initial state
	 * @return the gvalue as an int
	 */
	public int getGValue(){
		return this.gvalue;
	}
	
	/**
	 * Gets the heuristic value of the state
	 * @return the hvalue as an int
	 */
	public int getHValue(){
		return this.hvalue;
	}
	
	/**
	 * Gets the current location of the state
	 * @return the current location as an E
	 */
	public E getCurrLocation(){
		return this.currLocation;
	}
	
	/**
	 * Gets the number of flights left to be made
	 * @return size of the flights array as an int
	 */
	public int flightsLeft(){
		return flights.size();
	}
	
	/**
	 * Gets the current path taken to get to this point
	 * @return the path as an ArrayList<Edge<E>>
	 */
	public ArrayList<Edge<E>> getPath(){
		return path;
	}

	@Override
	/**
	 * Compares states fvalues and returns whether a given state is less than, equal to, or greater than the current one
	 * @param state1 of type State<E> which is the state to compare to
	 * @return a value representing where it should be placed, 0 if equal, 1 if greater than, -1 if less than
	 */
	public int compareTo(State<E> state1) {
		if ((this.getGValue() + this.getHValue()) == (state1.getGValue() + state1.getHValue())){
			return 0;
		} else if ((this.getGValue() + this.getHValue()) < (state1.getGValue() + state1.getHValue())){
			return -1;
		} else {
			return 1;
		}
	}
	
	/**
	 * Gets every current state that can occur after the current state
	 * @param g of Type Graph<E> as a representation of the nodes and edges connected
	 * @param c of Type Context<E> that gets the heuristic value
	 * @return a list containing all possible states as an ArrayList<State<E>>
	 */
	public ArrayList<State<E>> getNextStates(Graph<E> g, Context<E> c) {
		ArrayList<State<E>> ret = new ArrayList<State<E>>(); 
		Node<E> currNode = g.getNode(currLocation);
		HashSet<Edge<E>> neighbours = currNode.getNeighbours();
		
		//for each edge, a new state is created that would take that edge
		//the rest of the details are updated so that the state would be correct
		for (Edge<E> e: neighbours){
			State<E> newState = new State<E>();
			newState.currLocation = e.getDestination();
			newState.gvalue = this.gvalue + e.getDistance() + currNode.getDelay();
			newState.flights.addAll(this.flights);
			newState.path.addAll(this.path);
			newState.path.add(e);
			if (newState.flights.contains(e)){
				newState.flights.remove(e);
			} 
			newState.setHValue(c.executeHeuristic(g, newState.flights));
			ret.add(newState);
		}
		return ret;
	}
	
	/**
	 * Redefines what it means for two states to be equals
	 * @param o as an Object to be looked at
	 * @return a boolean value whether it is equal or not
	 */
	public boolean equals(Object o) throws ClassCastException{
		
		//checks the object is of the same class, otherwise chuck an exception
		if (o.getClass().equals(State.class)){
			State<E> n = (State<E>) o;
			
			//compares the states based on current location and flights left to make
			if (this.currLocation.equals(n.getCurrLocation()) && this.flights.equals(n.flights)){
				return true;
			} else {
				return false;
			}
		} else {
			throw new ClassCastException("Object is not a state");
		}
	}
	
	/**
	 * returns the last edges distance in the path
	 * @return the distance of the edge as an int
	 */
	public int lastDistance() {
		Edge<E> e = path.get(path.size() - 1);
		return e.getDistance();
	}
}
