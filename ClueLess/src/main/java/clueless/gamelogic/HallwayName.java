package clueless.gamelogic;

/**
 * Each hallway is named by the rooms it connects separated by an underscore.
 * 
 * @author drobi
 */
public enum HallwayName {
   	STUDY_LIBRARY("STUDY_LIBRARY"),
	STUDY_HALL("STUDY_HALL"),
	HALL_LOUNGE("HALL_LOUNGE"),
	HALL_BILLIARD("HALL_BILLIARD"),
	LOUNGE_DINING("LOUNGE_DINING"),
	LIBRARY_BILLIARD("LIBRARY_BILLIARD"),
	LIBRARY_CONSERVATORY("LIBRARY_CONSERVATORY"),
	BILLIARD_BALLROOM("BILLIARD_BALLROOM"),
	BILLIARD_DINING("BILLIARD_DINING"),
        DINING_KITCHEN("DINING_KITCHEN"),
        BALLROOM_KITCHEN("BALLROOM_KITCHEN"),
        CONSERVATORY_BALLROOM("CONSERVATORY_BALLROOM");        
	
	String hallwayName;
  
	/**
	 * Constructor that sets the hallway name
	 * 
	 * @param hallwayName
	 * 		the hallway name to set
	 */
    private HallwayName(String hallwayName) {
        this.hallwayName = hallwayName;
    }

    /**
     * Get the hallwayName
     * 
     * @return the hallwayName
     */
    public String getHallwayName() {
        return hallwayName;
    } 
}
