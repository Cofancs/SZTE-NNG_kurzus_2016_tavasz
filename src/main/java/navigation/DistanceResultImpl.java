package navigation;

import java.util.List;

/**
 * Created by Cofancs on 2016. 05. 14..
 */
public class DistanceResultImpl implements DistanceResult {
    double travelDistance;
    List<Integer> resultPath;



    @Override
    public double getTravelDistanceOfResultPath() {

        return travelDistance;
    }

    @Override
    public List<Integer> getResultPath() {
        return resultPath;
    }
}
