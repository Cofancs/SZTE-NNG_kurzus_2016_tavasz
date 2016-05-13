package navigation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Cofancs on 2016. 05. 13..
 */
public class Vertex {
    private int nodeId;
    private double yCoord;
    private double xCoord;
    private List<Edge> edges;

    public Vertex() {
        this.edges=new LinkedList<Edge>();
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
