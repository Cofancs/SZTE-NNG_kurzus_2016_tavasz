package navigation;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PathChecker {

	private Map<Integer, List<Integer>> edges;

	public PathChecker() {
		this.edges = new HashMap<Integer, List<Integer>>();
	}

	public void initialize(String path) {
		try {
			for (String line : Files.readAllLines(Paths.get(path))) {
				String[] data = line.split("\\s+");
				Integer from = Integer.valueOf(data[0]);
				Integer to = Integer.valueOf(data[1]);
				if (edges.containsKey(from)) {
					edges.get(from).add(to);
				} else {
					List<Integer> toList = new ArrayList<Integer>();
					toList.add(to);
					edges.put(from, toList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isContinuous(List<Integer> path) {
		Iterator<Integer> it = path.iterator();
		if (it.hasNext()) {
			Integer previousVertex = it.next();
			while (it.hasNext()) {
				Integer currentVertex = it.next();
				if (!edges.containsKey(previousVertex)
						|| !edges.get(previousVertex).contains(currentVertex)) {
					return false;
				}
				previousVertex = currentVertex;
			}
		}
		return true;
	}

	public boolean isStartingWith(List<Integer> path, int startNodeId) {
		if (!path.isEmpty()) {
			return path.get(0) == startNodeId;
		}
		return false;
	}

	public boolean isEndingWith(List<Integer> path, int destinationNodeId) {
		if (!path.isEmpty()) {
			return path.get(path.size() - 1) == destinationNodeId;
		}
		return false;
	}
}
