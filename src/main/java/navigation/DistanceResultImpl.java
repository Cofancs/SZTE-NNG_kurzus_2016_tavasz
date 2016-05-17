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

    public List<Integer> getParentListFromAlgorithm(List<Vertex> closedList){
        for (Vertex vertex:closedList){
            resultPath.add(vertex.getNodeId());
        }

        travelDistance=closedList.get(closedList.size()-1).getG();

        System.out.println(travelDistance);

        return resultPath;
    }
}
