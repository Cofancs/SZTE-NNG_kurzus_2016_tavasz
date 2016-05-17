package navigation;

/**
 * Created by Cofancs on 2016. 05. 13..
 */
public class Edge {
    private int id;
    private Vertex startVertex;
    private Vertex endVertex;
    private int averageSpeed;
    private double distance;
    private double time;

    public void calculateDistance(){
        double x = startVertex.getxCoord() - endVertex.getxCoord();
        double y = startVertex.getyCoord()-endVertex.getyCoord();
        setDistance(Math.abs(Math.sqrt((x*x)+(y*y))));
    }

    public void calculateTime(){
        setTime( getDistance()/getAverageSpeed());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(Vertex endVertex) {
        this.endVertex = endVertex;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
