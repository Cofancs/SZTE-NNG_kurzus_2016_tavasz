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
			DistanceResultImpl distanceResult=new DistanceResultImpl();

		List<Vertex> aStarList=aStar(startNodeId,destinationNodeId);
		if(!aStarList.isEmpty()){
			distanceResult.getClosedListFromAlgorithm(aStarList);

		}
		return distanceResult;
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
		double min=10000000;
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
			Vertex lowestVertex=openList.remove(0);
			List<Vertex> neighbourList=findNeighborVertexes(lowestVertex);
			for (Vertex neighbour:neighbourList){

				neighbour.setParentVertex(lowestVertex);
				if(neighbour.getNodeId()==destinationVertexId){
					closedList.add(neighbour);
					break;
				}
				/*double g=0,h=0,f=0;
				for (Edge edge:lowestVertex.getEdges()){
					if(!closedList.isEmpty()) {
						for (Vertex vertex : closedList) {
							if (!edge.getEndVertex().equals(vertex) && edge.getEndVertex().equals(neighbour)) {
								g = edge.getDistance();
							}
						}
					}else{
						if (edge.getEndVertex().equals(neighbour)) {
							g = edge.getDistance();
						}
					}
				}
				for(Edge edge:neighbour.getEdges()){
					if(!edge.getEndVertex().equals(lowestVertex)){
						h=airDistance(neighbour,destinationVertex);
					}

				}

				neighbour.setG(lowestVertex.getG()+g);
				neighbour.setH(h);
				neighbour.setF(neighbour.getG()+neighbour.getH());

				if(cheapestVertex.getF()<neighbour.getF()){
					if(openList.contains(neighbour)){
						openList.remove(neighbour);
					}
					if(closedList.contains(neighbour)){
						closedList.remove(neighbour);
					}
					if(openList.contains(neighbour)&&cheapestVertex){

				}
				}

				if(openList.contains(neighbour)&& lowestVertex.getG()<neighbour.getF()){
					openList.remove(neighbour);
				}
				if(closedList.contains(neighbour) && lowestVertex.getG()<neighbour.getF()){
					closedList.remove(neighbour);
				}*/
				if(!openList.contains(neighbour) && !closedList.contains(neighbour)){
					openList.add(neighbour);
				}

			}
			closedList.add(lowestVertex);
		}
		System.out.println(closedList.get(closedList.size()-1).getNodeId());
		return closedList;
	}


	/**
	 * @return neighbors of the input vertex
	 *
	 */

	public List<Vertex> findNeighborVertexes(Vertex vertex){
		List<Vertex> neighborVertexes = new ArrayList<Vertex> ();

		for (Edge edge: vertex.getEdges() ) {
			neighborVertexes.add(edge.getEndVertex());
		}

		return neighborVertexes;
	}

	public double airDistance(Vertex startVertex,Vertex destinationVertex){
		double airDistance=0;
		double x1,x2,y1,y2,x,y;
		x1=startVertex.getxCoord();
		y1=startVertex.getyCoord();
		x2=destinationVertex.getxCoord();
		y2=destinationVertex.getyCoord();
		x=x1-x2;
		y=y1-y2;
		airDistance=Math.sqrt((x*x)+(y*y));

		return airDistance;
	}
}

