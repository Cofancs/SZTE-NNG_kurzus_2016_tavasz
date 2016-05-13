package navigation;

/**
 * @author blevai
 *
 */
public interface DistanceResult extends Result {

	/**
	 * @return the whole travel distance of the best path your algorithm found
	 */
	double getTravelDistanceOfResultPath();
	
}
