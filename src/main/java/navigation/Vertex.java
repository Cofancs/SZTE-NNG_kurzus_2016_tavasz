package navigation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Cofancs on 2016. 05. 13..
 */
public class Vertex {
    private int nodeId;
    private  Vertex parentVertex;
    private double yCoord;
    private double xCoord;
    private double f,g,hV,t;
    private List<Edge> edges;

    public Vertex() {
        this.edges=new LinkedList<Edge>();
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getHV() {
        return hV;
    }

    public void setHV(double hValue) {
        this.hV = hValue;
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

    public Vertex getParentVertex() {
        return parentVertex;
    }

    public void setParentVertex(Vertex parentVertex) {
        this.parentVertex = parentVertex;
    }
}
