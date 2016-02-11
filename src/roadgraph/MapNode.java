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

public class MapNode {
	
	
	private GeographicPoint location ; //Lat. & Lon.
	
	private List<MapEdge> edgeList ; //list of edges originating from this node
	
	/**
	 * CONSTRUCTOR
	 * @param location is the GeographicPoint for the new node/vertex to be created
	 */
	public MapNode(GeographicPoint location) {
		this.location = location ;
		edgeList = new ArrayList<MapEdge>() ; 
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
}
