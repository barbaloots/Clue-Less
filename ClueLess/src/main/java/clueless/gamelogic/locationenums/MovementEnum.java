package clueless.gamelogic.locationenums;

import clueless.gamelogic.Location;

/**
 * 
 * @author erinsmedley
 *
 */
public enum MovementEnum {
	// First row for GUI placement
	STUDY(new Location(177,90)),
	STUDYTOHALLHALLWAY(new Location(306,90)),
	HALL(new Location(425,90)),
	HALLTOLOUNGEHALLWAY(new Location(615, 95)),
	LOUNGE(new Location(680,90)),
	
	// Second row for GUI placement
	STUDYTOLIBRARYHALLWAY(new Location(230, 220)),
	HALLTOBILLIARDROOMALLWAY(new Location(425,220)),
	LOUNGETODININGROOMHALLWAY(new Location(733, 230)),
	
	// Third row for GUI placement
	LIBRARY(new Location(177,360)),
	LIBRARYTOBILLIARDROOMHALLWAY(new Location(306, 360)),
	BILLIARDROOM(new Location(425,360)),
	BILLIARDROOMTODININGROOMHALLWAY(new Location(555,360)),
	DININGROOM(new Location(680,360)),

	// Fourth row for GUI placement
	LIBRARYTOCONSERVATORYHALLWAY(new Location(230, 490)),
	BILLIARDROOMTOBALLROOMHALLWAY(new Location(425,490)),
	DININGROOMTOKITCHENHALLWAY(new Location(733,490)),
	
	// Fifth row for GUI placement
	CONSERVATORY(new Location(177,630)),
	CONSERVATORYTOBALLROOMHALLWAY(new Location(360, 630)),
	BALLROOM(new Location(425,630)),
	BALLROOMTOKITCHENHALLWAY(new Location(615, 630)),
	KITCHEN(new Location(680,630));
		
		Location location;
		
		private MovementEnum(Location location) {
			this.location = location;
		}
		
		public Location getLocation() {
			return location;
		}
}
