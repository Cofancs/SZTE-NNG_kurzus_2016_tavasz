package navigation;

import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;

/**
 * Implement your graph representation here. This class will be instantiated
 * during the unit tests.
 */
public class GraphImpl implements Graph {

	private  Map<Integer,List<Double>> nodeListFromXML; //Map<NodeID,List<coordinates>>
	private  Map<Integer,Map<Integer,Map<Integer,Integer>>> edgeListFromXML; //Map<EdgeID,Map<startNodeValue,Map<endNodeValue,averageSpeedValue>>>
    private  int nodeId=0;
    private   List<Vertex> vertexList;
    private   List<Edge> edgeList;



    public GraphImpl(){
					nodeListFromXML = new HashMap<Integer,List<Double>>();
					edgeListFromXML = new HashMap<Integer,Map<Integer, Map<Integer,Integer>>>();
	}
    /*Getter And Setters*/
    public  Map<Integer, List<Double>> getNodeListFromXML() {
        return this.nodeListFromXML;
    }

    public  void setNodeListFromXML(Map<Integer, List<Double>> nodeListFromXML) {
        this.nodeListFromXML=nodeListFromXML;
    }

    public  Map<Integer, Map<Integer, Map<Integer, Integer>>> getEdgeListFromXML() {
        return edgeListFromXML;
    }

    public  void setEdgeListFromXML(Map<Integer, Map<Integer, Map<Integer, Integer>>> edgeListFromXML) {
        this.edgeListFromXML = edgeListFromXML;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    @Override
	public void initializeFromFile(File inputXmlFile) {

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = null;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputXmlFile);

			//System.out.println("Root element:" + doc.getDocumentElement().getNodeName());

			if(doc.hasChildNodes()){
				makeGraph(doc.getChildNodes());
			}


		}catch (Exception e){
			e.printStackTrace();
		}
	}
	private  void makeGraph(NodeList xmlNodeList){


		for(int i=0;i<xmlNodeList.getLength();i++) {
			Node tempNode = xmlNodeList.item(i); //1. tempNode.getNodeName()=graph


			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				/*if(tempNode.getNodeName()!="graph") {
					//System.out.println("Node Value =" + tempNode.getTextContent());
				}*/
				if(tempNode.hasAttributes()){
					//get attributes name and values
					NamedNodeMap nodeMap =tempNode.getAttributes();


					for(int j=0;j<nodeMap.getLength();j++){
						Node node = nodeMap.item(j);
						String attrValue = node.getNodeValue();
						String attrName = node.getNodeName();
						//System.out.println("attr name : " +attrName);
						//System.out.println("attr value : " + attrValue);
                        Vertex vertex=new Vertex();
                        Edge edge=new Edge();

						if(attrName.equals("id")){
							 this.nodeId=Integer.valueOf(attrValue);
                            if(tempNode.getNodeName().equals("node")) {

                                vertex.setNodeId(nodeId);
                            }else if(tempNode.getNodeName().equals("edge")){

                                edge.setId(nodeId);
                            }
						}
                        Map<Integer, Map<Integer, Integer>> startNodeList = new HashMap<Integer, Map<Integer, Integer>>();
						if(Objects.equals(tempNode.getParentNode().getNodeName(), "node")){


                            List<Double> coordinateList = new LinkedList<Double>();
								if(attrValue.equals("yCoord")) {
									//System.out.println("Node Value =" +tempNode.getTextContent());
									//y-coordinate
									coordinateList.add(Double.valueOf(tempNode.getTextContent()));
									NodeList tempNodeList=tempNode.getParentNode().getChildNodes();
                                    vertex.setyCoord(Double.valueOf(tempNodeList.item(1).getTextContent()));
                                    vertex.setxCoord(Double.valueOf(tempNodeList.item(5).getTextContent()));

									//x-coordinate
									//System.out.println("PArents lastChild Value =" + tempNodeList.item(3).getTextContent());
									coordinateList.add(Double.valueOf(tempNodeList.item(3).getTextContent()));
									//System.out.println("Node id:"+nodeId);
                                    vertexList.add(nodeId,vertex);
									this.nodeListFromXML.put(this.nodeId, coordinateList);
								}


						}else if(Objects.equals(tempNode.getParentNode().getNodeName(), "edge")){
                            Map<Integer, Integer> endNodeList = new HashMap<Integer, Integer>();
							int startNodeValue;

							if(attrValue.equals("startNode")){
								//System.out.println("startNode Value =" +tempNode.getTextContent());
								startNodeValue=Integer.valueOf(tempNode.getTextContent());

                                edge.setStartVertex(vertexList.get(startNodeValue));

								NodeList tempNodeList=tempNode.getParentNode().getChildNodes();
								//System.out.println(" endNode Value =" + tempNodeList.item(3).getTextContent());
								//System.out.println(" speed Value =" + tempNodeList.item(5).getTextContent());
                                int endNodeValue=Integer.valueOf(tempNodeList.item(3).getTextContent());
                                int avarageSpeed=Integer.valueOf(tempNodeList.item(5).getTextContent());
								endNodeList.put(endNodeValue,avarageSpeed);
                                edge.setEndVertex(vertexList.get(endNodeValue));
                                edge.setAverageSpeed(avarageSpeed);

                                edgeList.add(edge);
								startNodeList.put(startNodeValue, endNodeList);
								edgeListFromXML.put(nodeId, startNodeList);
							}


						}

					}

				}
				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					makeGraph(tempNode.getChildNodes());

				}



				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
			}
		}
	}



}
