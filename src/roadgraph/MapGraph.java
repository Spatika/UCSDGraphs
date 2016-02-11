/**
 * @author UCSD MOOC development team 
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	
	HashMap<GeographicPoint, MapNode> nodeList; //associate each (lat, long) to a MapNode object
	
	int numVertices ;
	int numEdges ; 
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		
		numEdges = 0 ;
		numVertices = 0 ; 
		nodeList = new HashMap<GeographicPoint, MapNode>() ;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{	
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		Set<GeographicPoint> verticeSet = new HashSet<GeographicPoint>() ; 
		
		
		verticeSet = nodeList.keySet() ; //set of keys in this map is the set of GeographicPoints, which is what we want
		
		return verticeSet ;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return numEdges;
	}
	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		
		if(location == null) 
			return false;

		MapNode newNode = new MapNode(location) ;
		
		if(nodeList.containsValue(newNode)) //if graph contains this node already
			return false ;
		
		nodeList.put(location, newNode) ; 
		//else, add the node to the nodeList
		//MapNode ctor takes care of initializing the new nodes edge list
		
		numVertices++ ; 
		
		return true ;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		
		//checking input
		if(from == null || to == null || roadName == null || roadType == null)
			throw new IllegalArgumentException("Arguments to addEdge method cannot be null!") ;
		
		if(length < 0)
			throw new IllegalArgumentException("Length of road cannot be less than zero!") ; 
		
		//if input is valid --
		
		MapNode start = nodeList.get(from) ; 
		MapNode end = nodeList.get(to) ; 
		
		MapEdge newEdge = new MapEdge(start, end, roadName, roadType, length) ; 
		
		//update the edge list associated with the origin of this edge
		start.updateEdgeList(newEdge);
	
		numEdges++ ; 
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		
		// Hook for visualization.  
		//nodeSearched.accept(next.getLocation());
		
		Queue<MapNode> bfsQueue = new LinkedList<MapNode>() ; 
		HashSet<MapNode> visited = new HashSet<MapNode>() ; 
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>() ;
			
		boolean status = bfsSetup(bfsQueue, visited, start, goal) ; 
		
		
		if(status == false)
			return new LinkedList<GeographicPoint>() ;
		
		//else, setup has gone as planned, so continue --
		

		boolean found = false ;
		
		while(!bfsQueue.isEmpty()) {
			MapNode curr = bfsQueue.remove() ; 
			
			
			if(curr == nodeList.get(goal)) {
				found = true ;
				break ; //match found, can display/return the path
			}
			
			//visit neighbors of curr Node
			List<MapEdge> curNeighbors = curr.getEdgeList() ; 
			
		
			for(int i = 0 ; i < curNeighbors.size() ; i++) {
				
				MapNode curNeighbor = curNeighbors.get(i).getEndNode() ; 
				
				if(!(visited.contains(curNeighbor))) {
					visited.add(curNeighbor) ;
					bfsQueue.offer(curNeighbor) ;
					parentMap.put(curNeighbor, curr) ; 
				}
				
			}//done visiting neighbors of curr
		}
		
		if(found == false) {
			System.out.println("Goal not reachable!!") ;
			return new LinkedList<GeographicPoint>() ;//return empty list
		}
		//find path from start to goal
		return findPath(parentMap, nodeList.get(start), nodeList.get(goal)) ;
	}
	
	
	/** This method takes care of the 'housekeeping' that needs to be done before doing the actual breadth-first search..
	 * ...Including adding the source node to 'visited' set and the underlying queue
	 * @param bfsQueue FIFO data structure for unexplored nodes
	 * @param visited  HashSet containing visited nodes
	 * @param parentMap maps visited MapNodes to their parents (just previously visited)
	 * @param goal destination GeographicPoint
	 * @param start source GeographicPoint
	 * @return whether input is valid or not/can move on to search part or not
	 */
	
	
	private boolean bfsSetup(Queue<MapNode> bfsQueue, 
			HashSet<MapNode> visited, GeographicPoint start, GeographicPoint goal) {
	
		//check for invalid input
		if(goal == null || start == null) {
			System.out.println("Argument null; goal not reachable!!") ;
			return false ;
		}
		
		MapNode source = nodeList.get(start) ;
		
		bfsQueue.offer(source) ;
		visited.add(source) ;
		
		return true ;
		
	}


	/** To backtrack the path taken from 'goal' to 'start'...
	 * ..using parentMap, and add to head of LinkedList
	 * @param parentMap
	 * @param start
	 * @param goal
	 * @return
	 */
	
	private List<GeographicPoint> findPath(HashMap<MapNode, MapNode> parentMap, MapNode start, MapNode goal) {
		
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>() ;
		
		MapNode curr = goal ; 
		
		while(curr != start) {
			GeographicPoint currPoint = curr.getNodeLocation() ;
			path.addFirst(currPoint) ;
			curr = parentMap.get(curr) ;
		}
		
		path.addFirst(start.getNodeLocation()) ;
		
		return path ; 
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		//printGraph() ;
		
		System.out.println("Num nodes: " + theMap.getNumVertices()) ; 
		System.out.println("Num edges: " + theMap.getNumEdges());
		
		
		GeographicPoint start = new GeographicPoint(1.0, 1.0) ; 
		GeographicPoint end = new GeographicPoint(8.0, -1.0) ; 
		List<GeographicPoint> route = theMap.bfs(start, end) ;
		
		System.out.println(route) ;
		
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}


	
}
