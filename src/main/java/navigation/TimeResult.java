package navigation;

public interface TimeResult extends Result {

	/**
	 * @return the whole travel time along the best path your algorithm found
	 */
	double getTravelTimeOfResultPath();
	
}
