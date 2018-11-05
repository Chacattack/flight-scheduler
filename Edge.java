
public class Edge<E> {
	E source;
	E destination;
	int distance;
	
	/**
	 * Sets the starting point
	 * @param s of type E as the starting point
	 */
	public void setSource(E s){
		this.source = s;
	}
	
	/**
	 * Sets the destination
	 * @param d of Type E as the destination
	 */
	public void setDestination(E d){
		this.destination = d;
	}
	
	/**
	 * Gets the source of the edge
	 * @return the starting point of the edge as an E
	 */
	public E getSource(){
		return this.source;
	}
	
	/**
	 * Gets the destination of the edge
	 * @return the endpoint of the edge as an E
	 */
	public E getDestination(){
		return this.destination;
	}
	
	/**
	 * Sets the distance of the edge
	 * @param d of Type int as the distance
	 */
	public void setDistance(int d){
		this.distance = d;
	}
	
	/**
	 * Gets the distance of the edge
	 * @return the distance of the edge as an int
	 */
	public int getDistance(){
		return this.distance;
	}
}
