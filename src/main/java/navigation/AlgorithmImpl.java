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
			distanceResult.getParentListFromAlgorithm(aStarList);

		}
		return distanceResult;
	}

	@Override
	public TimeResult findFastestPath(int startNodeId, int destinationNodeId) {
        TimeResultImpl timeResult=new TimeResultImpl();

        List<Vertex> aStarList=aStarWithTime(startNodeId,destinationNodeId);
        if(!aStarList.isEmpty()){
            timeResult.getParentListFromAlgorithm(aStarList);

        }
        return timeResult;
	}

	@Override
	public boolean hasPath(int startNodeId, int destinationNodeId) {

		boolean isPath=false;

            if(startNodeId==destinationNodeId){
                return true;
            }
        List<Vertex> openList=new LinkedList<Vertex>();
        List<Vertex> closedList=new LinkedList<Vertex>();

        Vertex startingVertex=vertexList.get(startNodeId-1);
        Vertex destinationVertex=vertexList.get(destinationNodeId-1);
        openList.add(startingVertex);
        while (!openList.isEmpty()){
            Vertex currentVertex=openList.remove(0);
            List<Vertex> neighbourList=findNeighborVertexes(currentVertex,closedList);
            for (Vertex neighbour:neighbourList){
                if(neighbour.equals(destinationVertex)){
                    return true;
                }
                if(!openList.contains(neighbour)&&!closedList.contains(neighbour)){
                    openList.add(neighbour);
                }
            }
            closedList.add(currentVertex);
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
		List<Vertex> parentList=new LinkedList<Vertex>();
        List<Integer> parentListById=new LinkedList<Integer>();

		Vertex startingVertex=vertexList.get(startVertexId-1);
		Vertex destinationVertex=vertexList.get(destinationVertexId-1);
		startingVertex.setParentVertex(startingVertex);
        startingVertex.setF(airDistance(startingVertex,destinationVertex));
		openList.add(startingVertex);


		while(!openList.isEmpty()){
			Vertex currentVertex=findVertexWithLowestHeuristicValue(openList);
            openList.remove(currentVertex);
			if(currentVertex.equals(destinationVertex)){

				closedList.add(currentVertex);
				break;
			}
            closedList.add(currentVertex);
            //azokat a szomszédokat adja vissza amelyek nincsenek benne a closedListben
			List<Vertex> neighbourList=findNeighborVertexes(currentVertex,closedList);

			for (Vertex neighbour:neighbourList){
				neighbour.setParentVertex(currentVertex);

				if(neighbour.equals(destinationVertex)){
					Edge currentEdge=null;
					for (Edge edge:destinationVertex.getEdges()){
						if(edge.getEndVertex().equals(currentVertex)){
							currentEdge=edge;
							break;
						}
					}
					neighbour.setG(currentVertex.getG()+currentEdge.getDistance());
                    neighbour.setT(currentVertex.getT()+currentEdge.getTime());
					closedList.add(neighbour);
					openList.removeAll(openList);
					break;
				}

				double g=1000000,t=0;
				Edge currentAndNeighboursEdge=null;
				for (Edge edge:currentVertex.getEdges()){
					if(edge.getEndVertex().equals(neighbour)){
						currentAndNeighboursEdge=edge;
						break;
					}
				}
                if(!closedList.isEmpty()) {
                    for (Vertex vertex : closedList) {
                        if (!currentAndNeighboursEdge.getEndVertex().equals(currentVertex.getParentVertex())&&!currentAndNeighboursEdge.getEndVertex().equals(vertex) ) {
                            g=currentAndNeighboursEdge.getDistance();
                            t=currentAndNeighboursEdge.getTime();
                        }
                    }
                }else{
                    if (!currentAndNeighboursEdge.getEndVertex().equals(currentVertex.getParentVertex())) {
                        g=currentAndNeighboursEdge.getDistance();
                        t=currentAndNeighboursEdge.getTime();
                    }
                }
                double hValue=airDistance(neighbour,destinationVertex);
                neighbour.setG(currentVertex.getG()+g);
                neighbour.setT(currentVertex.getT()+t);
				neighbour.setHV(hValue);
				neighbour.setF(neighbour.getG()+neighbour.getHV());

                if(!openList.contains(neighbour)&& !closedList.contains(neighbour)){
                    openList.add(neighbour);
                }


			}
		}
        Vertex lastVertex=closedList.get(closedList.size()-1);
        parentList.add(lastVertex);
        parentListById.add(lastVertex.getNodeId());
        while (!lastVertex.getParentVertex().equals(startingVertex)){
            parentList.add(lastVertex.getParentVertex());
            parentListById.add(lastVertex.getParentVertex().getNodeId());
            lastVertex=lastVertex.getParentVertex();
        }
        parentList.add(lastVertex.getParentVertex());
        parentListById.add(lastVertex.getParentVertex().getNodeId());
        Collections.reverse(parentList);
        //Collections.reverse(parentListById);
        System.out.println(parentListById);
		return parentList;
	}

    public List<Vertex> aStarWithTime(int startVertexId,int destinationVertexId){
        List<Vertex> openList=new LinkedList<Vertex>();
        List<Vertex> closedList=new LinkedList<Vertex>();
        List<Vertex> parentList=new LinkedList<Vertex>();
        List<Integer> parentListById=new LinkedList<Integer>();

        Vertex startingVertex=vertexList.get(startVertexId-1);
        Vertex destinationVertex=vertexList.get(destinationVertexId-1);
        startingVertex.setParentVertex(startingVertex);
        startingVertex.setF(airDistance(startingVertex,destinationVertex));
        openList.add(startingVertex);


        while(!openList.isEmpty()){
            Vertex currentVertex=findVertexWithLowestHeuristicValue(openList);
            openList.remove(currentVertex);
            if(currentVertex.equals(destinationVertex)){

                closedList.add(currentVertex);
                break;
            }
            closedList.add(currentVertex);
            //azokat a szomszédokat adja vissza amelyek nincsenek benne a closedListben
            List<Vertex> neighbourList=findNeighborVertexes(currentVertex,closedList);

            for (Vertex neighbour:neighbourList){
                neighbour.setParentVertex(currentVertex);

                if(neighbour.equals(destinationVertex)){
                    Edge currentEdge=null;
                    for (Edge edge:destinationVertex.getEdges()){
                        if(edge.getEndVertex().equals(currentVertex)){
                            currentEdge=edge;
                            break;
                        }
                    }
                    neighbour.setT(currentVertex.getT()+currentEdge.getTime());
                    closedList.add(neighbour);
                    openList.removeAll(openList);
                    break;
                }

                double g=1000000,t=0;
                Edge currentAndNeighboursEdge=null;
                for (Edge edge:currentVertex.getEdges()){
                    if(edge.getEndVertex().equals(neighbour)){
                        currentAndNeighboursEdge=edge;
                        break;
                    }
                }
                if(!closedList.isEmpty()) {
                    for (Vertex vertex : closedList) {
                        if (!currentAndNeighboursEdge.getEndVertex().equals(currentVertex.getParentVertex())&&!currentAndNeighboursEdge.getEndVertex().equals(vertex) ) {
                            t=currentAndNeighboursEdge.getTime();
                        }
                    }
                }else{
                    if (!currentAndNeighboursEdge.getEndVertex().equals(currentVertex.getParentVertex())) {
                        t=currentAndNeighboursEdge.getTime();
                    }
                }
                double hValue=airDistance(neighbour,destinationVertex);

                neighbour.setT(currentVertex.getT()+t);
                neighbour.setHV(hValue);
                neighbour.setF(neighbour.getT()+neighbour.getHV());

                if(!openList.contains(neighbour)&& !closedList.contains(neighbour)){
                    openList.add(neighbour);
                }


            }
        }
        Vertex lastVertex=closedList.get(closedList.size()-1);
        parentList.add(lastVertex);
        parentListById.add(lastVertex.getNodeId());
        while (!lastVertex.getParentVertex().equals(startingVertex)){
            parentList.add(lastVertex.getParentVertex());
            parentListById.add(lastVertex.getParentVertex().getNodeId());
            lastVertex=lastVertex.getParentVertex();
        }
        parentList.add(lastVertex.getParentVertex());
        parentListById.add(lastVertex.getParentVertex().getNodeId());
        Collections.reverse(parentList);
        //Collections.reverse(parentListById);
        System.out.println(parentListById);
        return parentList;
    }


	/**
	 * @return neighbors of the input vertex
	 *
	 */

	public List<Vertex> findNeighborVertexes(Vertex vertex,List<Vertex> closedList){
		List<Vertex> neighborVertexes = new ArrayList<Vertex> ();

		for (Edge edge: vertex.getEdges() ) {
            if(!closedList.contains(edge.getEndVertex())){
			    neighborVertexes.add(edge.getEndVertex());
            }
		}

		return neighborVertexes;
	}

	public double airDistance(Vertex startVertex,Vertex destinationVertex){

		double x1,x2,y1,y2,x,y;
		x1=startVertex.getxCoord();
		y1=startVertex.getyCoord();
		x2=destinationVertex.getxCoord();
		y2=destinationVertex.getyCoord();
		x=x1-x2;
		y=y1-y2;

		return Math.sqrt((x*x)+(y*y));
	}
}

