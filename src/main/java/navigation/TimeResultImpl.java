package navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cofancs on 2016. 05. 16..
 */
public class TimeResultImpl implements TimeResult{
    List<Integer> resultPath;
    double travelTime=0;

    public TimeResultImpl() {
        this.resultPath=new ArrayList<Integer>();
    }

    @Override
    public double getTravelTimeOfResultPath() {
        return travelTime;
    }

    @Override
    public List<Integer> getResultPath() {
        return resultPath;
    }

    public List<Integer> getParentListFromAlgorithm(List<Vertex> closedList) {
        for (Vertex vertex : closedList) {
            resultPath.add(vertex.getNodeId());
        }

        travelTime = closedList.get(closedList.size() - 1).getT();
        System.out.println(travelTime);

        return resultPath;
        }
    }
