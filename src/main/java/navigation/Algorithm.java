package navigation;

/**
 * startNodeId and destinationNodeId are the ids that can be found as properties
 * of node xml elements in the project_root_dir/graph.xml input file.
 *
 */
public interface Algorithm {

	/**
	 * Store the graph. Implement and execute any computation you would like to
	 * rely on later during path finding, like calculating optimal landmark set,
	 * etc.
	 * 
	 * @param graph
	 *            the graph on which you will optimize
	 */
	void preprocess(Graph graph);

	/**
	 * Implement shortest path search minimizing the travel distance between two
	 * points.
	 * 
	 * @param startNodeId
	 *            the id of the node where the path must start
	 * @param destinationNodeId
	 *            the id of the node where the path must end
	 * @return the shortest path you found and the length of it
	 */
	DistanceResult findShortestPath(int startNodeId, int destinationNodeId);

	/**
	 * Implement shortest path search minimizing the travel time of the path.
	 * 
	 * @param startNodeId
	 *            the id of the node where the path must start
	 * @param destinationNodeId
	 *            the id of the node where the path must end
	 * @return the fastest path you found and the length of it
	 */
	TimeResult findFastestPath(int startNodeId, int destinationNodeId);

	/**
	 * Determine whether there are any path between the start node and
	 * destination node
	 * 
	 * @param startNodeId
	 *            the id of the node where the path must start
	 * @param destinationNodeId
	 *            the id of the node where the path must end
	 */
	boolean hasPath(int startNodeId, int destinationNodeId);
}
