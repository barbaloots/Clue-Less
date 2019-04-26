package clueless.gamelogic;

/**
 * Utility class to map from abbreviations to full strings, and vice versa.
 *
 * @author matthewsobocinski
 */
public class AbbreviationMap {
	public static String getCharacterFullname(String characterAbbrev) {
		switch(characterAbbrev) {
			case "PP": return "Professor Plum";
			case "CM": return "Colonel Mustard";
			case "MS": return "Miss Scarlet";
			case "MP": return "Mrs. Peacock";
			case "MG": return "Mr. Green";
			case "MW": return "Mrs. White";
			default: return "No mapping for " + characterAbbrev;
		}
	}
}
