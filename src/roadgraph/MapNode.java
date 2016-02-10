package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode {
	private GeographicPoint location ;
	private List<MapEdge> edgeList ;
	
	//ctor
	public MapNode(GeographicPoint location) {
		this.location = location ;
		edgeList = new ArrayList<MapEdge>() ; 
	}
	
	//update list
	public void updateEdgeList(MapEdge newEdge) {
		edgeList.add(newEdge) ;
	}
	
	
	//getters
	public List<MapEdge> getEdgeList() {
		return edgeList;
	}
	
	public GeographicPoint getNodeLocation() {
		return this.location ;
	}
}
