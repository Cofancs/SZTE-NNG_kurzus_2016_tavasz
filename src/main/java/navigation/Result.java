package navigation;

import java.util.List;

public interface Result {

	/**
	 * @return the best path your algorithm found as a list of node ids. The
	 *         first node id in the list must be the start node of the
	 *         underlying path search, the last must be destination node's id.
	 *         The subsequent node ids must follow each other just as the nodes
	 *         follow each other on the path in the graph.  
	 */
	List<Integer> getResultPath();

}
