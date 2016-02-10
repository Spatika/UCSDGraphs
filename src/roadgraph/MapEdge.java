package roadgraph;

public class MapEdge {
	private MapNode startNode ; 
	private MapNode endNode ; 
	
	//weight of the edge
	private double weight ;
	private String roadType ;
	private String roadName ; 
	
	public MapEdge(MapNode start, MapNode end, String roadName, String roadType, double length) {
		startNode = start ;
		endNode = end ;
		this.roadName = roadName ; 
		this.roadType = roadType ;
		weight = length ;
	}

	//setters
	public void setStartNode(MapNode x) {
		startNode = x ;
	}
	
	public void setEndNode(MapNode y) {
		endNode = y ;
	}
	
	public void setWeight(double w) {
		weight = w ;
	}
	
	public void setStreet(String street) {
		roadName = street ; 
	}
	
	
	//getters
	
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
