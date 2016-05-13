package navigation;

import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;

/**
 * Implement your graph representation here. This class will be instantiated
 * during the unit tests.
 */
public class GraphImpl implements Graph {

	public static Map<Integer,List<Double>> nodeListFromGraph; //Map<NodeID,List<coordinates>>
	public static Map<Integer,Map<Integer,Map<Integer,Integer>>> edgeListFromGraph; //Map<EdgeID,Map<startNodeValue,Map<endNodeValue,averageSpeedValue>>>
	public static List<Double> coordinateList;
	public static Map<Integer,Map<Integer,Integer>> startNodeList;
	public static Map<Integer,Integer> endNodeList;
	static int nodeId=0;

	public GraphImpl(){
					nodeListFromGraph= new HashMap<Integer,List<Double>>();
					edgeListFromGraph= new HashMap<Integer,Map<Integer, Map<Integer,Integer>>>();
	}

	@Override
	public void initializeFromFile(File inputXmlFile) {
		// TODO Auto-generated method stub
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
	private static void makeGraph(NodeList xmlNodeList){


		for(int i=0;i<xmlNodeList.getLength();i++) {
			Node tempNode = xmlNodeList.item(i); //1. tempNode.getNodeName()=graph


			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				if(tempNode.getNodeName()!="graph") {
					//System.out.println("Node Value =" + tempNode.getTextContent());
				}
				if(tempNode.hasAttributes()){
					//get attributes name and values
					NamedNodeMap nodeMap =tempNode.getAttributes();


					for(int j=0;j<nodeMap.getLength();j++){
						Node node = nodeMap.item(j);
						String attrValue = node.getNodeValue();
						String attrName = node.getNodeName();
						//System.out.println("attr name : " +attrName);
						//System.out.println("attr value : " + attrValue);

						if(attrName.equals("id")){
							 nodeId=Integer.valueOf(attrValue);
						}
							startNodeList=new HashMap<Integer,Map<Integer, Integer>>();
						if(tempNode.getParentNode().getNodeName()=="node"){

								coordinateList = new LinkedList<Double>();
								if(attrValue.equals("yCoord")) {
									//System.out.println("Node Value =" +tempNode.getTextContent());
									//y-coordinate
									coordinateList.add(Double.valueOf(tempNode.getTextContent()));
									NodeList tempNodeList=tempNode.getParentNode().getChildNodes();

									//x-coordinate
									//System.out.println("PArents lastChild Value =" + tempNodeList.item(3).getTextContent());
									coordinateList.add(Double.valueOf(tempNodeList.item(3).getTextContent()));
									//System.out.println("Node id:"+nodeId);
									nodeListFromGraph.put(nodeId, coordinateList);
								}


						}else if(tempNode.getParentNode().getNodeName()=="edge"){
							endNodeList= new HashMap<Integer,Integer>();
							int startNodeValue=0;
							if(attrValue.equals("startNode")){
								//System.out.println("startNode Value =" +tempNode.getTextContent());
								startNodeValue=Integer.valueOf(tempNode.getTextContent());

								NodeList tempNodeList=tempNode.getParentNode().getChildNodes();
								//System.out.println(" endNode Value =" + tempNodeList.item(3).getTextContent());
								//System.out.println(" speed Value =" + tempNodeList.item(5).getTextContent());
								endNodeList.put(Integer.valueOf(tempNodeList.item(3).getTextContent()),Integer.parseInt(tempNodeList.item(5).getTextContent()));

								startNodeList.put(startNodeValue,endNodeList);
								edgeListFromGraph.put(nodeId,startNodeList);
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
