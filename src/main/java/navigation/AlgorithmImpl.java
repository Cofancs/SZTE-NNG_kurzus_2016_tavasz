package navigation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implement your navigation algorithm here. This class will be instantiated
 * during the unit tests.
 */
public class AlgorithmImpl implements Algorithm {
	private  GraphImpl graph;

	public AlgorithmImpl() {
		this.graph = graph;
	}

	@Override
	public void preprocess(Graph graph) {

		this.graph= (GraphImpl) graph;
// TODO: 2016. 05. 13. add edges to vertexes 

	}

	@Override
	public DistanceResult findShortestPath(int startNodeId,
			int destinationNodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeResult findFastestPath(int startNodeId, int destinationNodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPath(int startNodeId, int destinationNodeId) {
		// TODO Auto-generated method stub
		boolean isPath=false;



		return isPath;
	}

}
