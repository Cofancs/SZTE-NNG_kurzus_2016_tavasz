package navigation;

import java.io.File;

public interface Graph {

	/**
	 * Implement your graph construction here using an xml file of predefined
	 * format, see project_root_dir/graph.xml
	 * 
	 * @param inputXmlFile
	 *            the xml file containing the graph data
	 */
	void initializeFromFile(File inputXmlFile);

}
