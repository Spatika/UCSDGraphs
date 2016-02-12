package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

/**
 * @author Spatika
 * 
 * A class to wrap around GeographicPoint object for each intersection on the Road Map
 * Neighbors of a MapNode can be accessed via edgeList member variable
 */

public class MapNode implements Comparable<MapNode> {
	
	
	private GeographicPoint location ; //Lat. & Lon.
	
	private List<MapEdge> edgeList ; //list of edges originating from this node
	
	double distance ; //from the 'source' that you're checking! - g score
	double distToGoal ; //dist from 'goal' that you're checking! - h score

	
	/**
	 * CONSTRUCTOR
	 * @param location is the GeographicPoint for the new node/vertex to be created
	 */
	public MapNode(GeographicPoint location) {
		this.location = location ;
		edgeList = new ArrayList<MapEdge>() ; 
		distance = Double.POSITIVE_INFINITY ;
		distToGoal = Double.POSITIVE_INFINITY ;
	}
	
	/**
	 * ADDING A NEW EDGE ORIGINATING FROM THIS NODE
	 * @param newEdge The newEdge which starts at this node & must be added to it's edgeList
	 */
	public void updateEdgeList(MapEdge newEdge) {
		edgeList.add(newEdge) ;
	}
	
	
	//****GETTERS****//
	
	/**
	 * 
	 * @return The list of edges originating from this node
	 */
	
	public List<MapEdge> getEdgeList() {
		return edgeList;
	}
	
	
	/**
	 * 
	 * @return the actual Lat, Lon of this MapNode, via GeographicPoint class
	 */
	public GeographicPoint getNodeLocation() {
		return this.location ;
	}

	@Override
	public int compareTo(MapNode other) {
		
		//compare the two predicted distances - f scores
		return Double.compare((this.distance+this.distToGoal), (other.distance+other.distToGoal)) ;
	}
}
