package navigation;

import java.util.*;

/**
 * Implement your navigation algorithm here. This class will be instantiated
 * during the unit tests.
 */
public class AlgorithmImpl implements Algorithm {
	private  GraphImpl graph;
	List<Vertex> vertexList;
	List<Edge> edgeList;

	public AlgorithmImpl() {
		this.graph = graph;
	}

	@Override
	public void preprocess(Graph graph) {

		this.graph= (GraphImpl) graph;
		this.graph.collectVertexes();
		this.graph.collectEdges();
		vertexList=this.graph.getVertexList();
		edgeList = this.graph.getEdgesList();

		for (Vertex vertex: vertexList){
			for (Edge edge:edgeList){
				if(edge.getStartVertex()== vertex){
					vertex.addEdge(edge);
				}
			}
		}


	}

	@Override
	public DistanceResult findShortestPath(int startNodeId,
			int destinationNodeId) {
		Vertex startVertex = graph.getVertexList().get(startNodeId-1);
		Vertex endVertex = graph.getVertexList().get(destinationNodeId-1);
		return null;
	}

	@Override
	public TimeResult findFastestPath(int startNodeId, int destinationNodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPath(int startNodeId, int destinationNodeId) {

		boolean isPath=false;


			List<Vertex> aStarList=aStar(startNodeId,destinationNodeId);
			if(!aStarList.isEmpty()){
				for (Vertex vertex:aStarList) {
					if(vertex.getNodeId()==destinationNodeId){
						isPath=true;
					}
				}

			}


		return isPath;
	}

	public Vertex findVertexWithLowestHeuristicValue (List<Vertex> vertexList){
		Vertex vertexLowest=new Vertex();

		vertexLowest=vertexList.get(0);
		Vertex tempVertex=vertexLowest;
		double min=vertexList.get(0).getF();
		for (Vertex vertex:vertexList){
			double f=vertex.getF();
			if(f<min){
				min=f;
				vertexLowest=vertex;
			}
		}

		return vertexLowest;
	}

	public List<Vertex> aStar(int startVertexId,int destinationVertexId){
		List<Vertex> openList=new LinkedList<Vertex>();
		List<Vertex> closedList=new LinkedList<Vertex>();

		Vertex startingVertex=vertexList.get(startVertexId-1);
		Vertex destinationVertex=vertexList.get(destinationVertexId-1);
		openList.add(startingVertex);

		while(!openList.isEmpty()){
			Vertex cheapestVertex=findVertexWithLowestHeuristicValue(openList);
			openList.remove(cheapestVertex);
			List<Vertex> neighbourList=findNeighborVertexes(cheapestVertex);
			for (Vertex neighbour:neighbourList){
				neighbour.setParentVertex(cheapestVertex);
				if(neighbour.getNodeId()==destinationVertexId){
					break;
				}
				double g=0,h=0,f=0;
				for (Edge edge:cheapestVertex.getEdges()){
					if(edge.getEndVertex()==neighbour){
						g=edge.getDistance();
					}
				}
				for(Edge edge:neighbour.getEdges()){
					if(edge.getEndVertex()==destinationVertex){
						h=edge.getDistance();
					}
				}
				neighbour.setG(cheapestVertex.getG()+g);
				neighbour.setH(h);
				neighbour.setF(neighbour.getG()+neighbour.getH());

				if(cheapestVertex.getF()<neighbour.getF()){
					if(openList.contains(neighbour)){
						openList.remove(neighbour);
					}
					if(closedList.contains(neighbour)){
						closedList.remove(neighbour);
					}
				}
				if(!openList.contains(neighbour) && !closedList.contains(neighbour)){
					openList.add(neighbour);
				}

			}
			closedList.add(cheapestVertex);
		}
		return closedList;
	}


	/**
	 * @return neighbors of the input vertex
	 *
	 */

	public List<Vertex> findNeighborVertexes(Vertex vertex){
		List<Vertex> neighborVertexes = new ArrayList<Vertex> ();

		for (int key: graph.getEdgeListFromXML().get(vertex.getNodeId()).keySet() ) {
			neighborVertexes.add(graph.getVertexList().get(key-1));
		}

		return neighborVertexes;
	}

}
