package navigation;

import java.util.*;

/**
 * Created by Cofancs on 2016. 05. 14..
 */
public class DistanceResultImpl implements DistanceResult {
    double travelDistance=0;
    List<Integer> resultPath;


    public DistanceResultImpl() {
        this.resultPath=new ArrayList<Integer>();
    }

    @Override
    public double getTravelDistanceOfResultPath() {



        return travelDistance;
    }

    @Override
    public List<Integer> getResultPath() {

        return resultPath;
    }

    public List<Integer> getClosedListFromAlgorithm(List<Vertex> closedList){
        List<?> listCopy=closedList.subList(0,closedList.size());
        Collections.reverse(listCopy);
       for (Object vertex:listCopy){
           resultPath.add(((Vertex)vertex).getNodeId());
       }
        Iterator<Vertex> iterator= closedList.iterator();
        if (iterator.hasNext()){
            Vertex previousVertex= iterator.next();
            while (iterator.hasNext()){
                Vertex currentVertex=iterator.next();
                for (Edge previousVertexEdge:previousVertex.getEdges()) {
                 for (Edge currentVertexEdge:currentVertex.getEdges()) {
                     if (previousVertexEdge.getStartVertex().equals(currentVertexEdge.getEndVertex())) {
                            travelDistance+=currentVertexEdge.getDistance();
                     }
                 }
                }
                previousVertex=currentVertex;
            }
        }



        return resultPath;
    }
}
