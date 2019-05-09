package clueless.gamelogic.locationenums;

import clueless.gamelogic.Location;

/**
 * 
 * @author erinsmedley
 *
 */
public enum MovementEnum {
		// First row for GUI placement
		STUDY(new Location(437,90)),
		STUDYTOHALLHALLWAY(new Location(566,90)),
		HALL(new Location(685,90)),
		HALLTOLOUNGEHALLWAY(new Location(815, 90)),
		LOUNGE(new Location(940,90)),
		
		// Second row for GUI placement
		STUDYTOLIBRARYHALLWAY(new Location(437, 220)),
		HALLTOBILLIARDROOMALLWAY(new Location(685,220)),
		LOUNGETODININGROOMHALLWAY(new Location(940, 230)),
		
		// Third row for GUI placement
		LIBRARY(new Location(437,360)),
		LIBRARYTOBILLIARDROOMHALLWAY(new Location(566, 360)),
		BILLIARDROOM(new Location(685,360)),
		BILLIARDROOMTODININGROOMHALLWAY(new Location(815,360)),
		DININGROOM(new Location(940,360)),

		// Fourth row for GUI placement
		LIBRARYTOCONSERVATORYHALLWAY(new Location(437, 490)),
		BILLIARDROOMTOBALLROOMHALLWAY(new Location(685,490)),
		DININGROOMTOKITCHENHALLWAY(new Location(940,490)),
		
		// Fifth row for GUI placement
		CONSERVATORY(new Location(437,630)),
		CONSERVATORYTOBALLROOMHALLWAY(new Location(566, 630)),
		BALLROOM(new Location(685,630)),
		BALLROOMTOKITCHENHALLWAY(new Location(815, 630)),
		KITCHEN(new Location(940,630));
		
		Location location;
		
		private MovementEnum(Location location) {
			this.location = location;
		}
		
		public Location getLocation() {
			return location;
		}
}
