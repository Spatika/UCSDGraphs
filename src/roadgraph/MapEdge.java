package roadgraph;


/**
 * @author Spatika
 * Class to encapsulate various attributes of an edge - start & end nodes, weight, road name, etc.
 * 
 */

public class MapEdge {
	
	//***PRIVATE MEMBERS***//
	private MapNode startNode ; 
	private MapNode endNode ; 
	
    private double weight ; //weight of the edge is it's length or distance in km.

	private String roadType ; //like 'city', 'main', etc.
	private String roadName ; 
	
	
	/**
	 * CONSTRUCTOR for MapEdge
	 * @param start the starting MapNode of this edge
	 * @param end the ending MapNode of this edge
	 * @param roadName 
	 * @param roadType 
	 * @param length the edge 'weight' 
	 */
	
	public MapEdge(MapNode start, MapNode end, String roadName, String roadType, double length) {
		startNode = start ;
		endNode = end ;
		
		this.roadName = roadName ; 
		this.roadType = roadType ;
		
		weight = length ;
	}

	//***SETTERS***//
	
	/**
	 * This method (and other setters) can be used to update edge properties 
	 * ...Change street name, or type, for example
	 * @param x - Starting point of this edge
	 */
	public void setStartNode(MapNode x) {
		startNode = x ;
	}
	
	public void setEndNode(MapNode y) {
		endNode = y ;
	}
	
	public void setWeight(double w) {
		weight = w ;
	}
	
	public void setStreet(String road) {
		roadName = road; 
	}
	
	public void setStreetType(String type) {
		roadType = type ; 
	}
	
	//***GETTERS***//
	
	public MapNode getStartNode() {
		return startNode ;
	}
	
	public MapNode getEndNode() {
		return endNode ;
	}
	
	public double getWeight() {
		return weight ;
	}
	
	public String getStreet() {
		return roadName ; 
	}
	
	public String getStreetType() {
		return roadType ; 
	}
	
	
}
