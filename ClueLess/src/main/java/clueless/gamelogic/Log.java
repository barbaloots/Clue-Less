package clueless.gamelogic;

import java.util.ArrayList;

/**
 * This class creates the logs required for the game as well as
 * the methods for how the logs are updated as the game progresses.
 */
public class Log 
{
   private ArrayList charAndWeapMovement;
   private ArrayList suggestions;
   private ArrayList accusations;
   
   /**
    * Constructor
    */
   public Log()
   {
       charAndWeapMovement = new ArrayList();
       suggestions = new ArrayList();
       accusations = new ArrayList();
   }
   
   /**
    * Update movement/location of the characters and weapons on the board
    * 
    * @param character
    * 		the character to be moved
    * @param weapon
    * 		the weapon to be moved
    * @param previousCharRoom
    * 		the current/previous location (room) of the character
    * @param previousWeaponRoom
    * 		the current/previous location (room) of the weapon
    * @param destinationRoom
    * 		the new destination (room) of the character and weapon
    */
   public void updateCharAndWeapMovement(CharacterName character, WeaponType weapon, RoomName previousCharRoom, 
   RoomName previousWeaponRoom, RoomName destinationRoom)
   {
       charAndWeapMovement.add(character + " was moved from " + previousCharRoom + " to " + destinationRoom + "."
       + "\n" + weapon + " was moved from " + previousWeaponRoom + " to " + destinationRoom + ".");
   }
   
   /**
    * After a player makes a suggestion or accusation, add to the log
    * 
    * @param character
    * 		the character being suggested
    * @param weapon
    * 		the weapon being suggested
    * @param destinationRoom
    * 		the destination being suggested
    */
   public void updateSuggestAccuse(Boolean accuse, CharacterName character, WeaponType weapon, RoomName destinationRoom)
   {
	   String claim = " made: It was " + character + " with the " + weapon + " in the " +
		       destinationRoom + ".";
	   if(accuse) {
		   accusations.add("Accusation" + claim);
	   }
	   else {
		   suggestions.add("Suggestion" + claim);
	   }
   }
   
   /**
    * Provide logs for a specific turn
    * 
    * @param chosenLog
    * 		the log to be displayed
    * @param turnNumber
    * 		the turn whose log is to be displayed
    */
   public void checkLogByTurn(ArrayList chosenLog, int turnNumber)
   {
       System.out.println(chosenLog.get(turnNumber - 1));   
   }
   
   /**
    * Provide entire logs
    * 
    * @param chosenLog
    * 		the log to be displayed
    */
   public void printAllFromLog(ArrayList<String> chosenLog)
   {
       for(String entry : chosenLog)
       {
           System.out.println(entry);   
       }
   }
   
   /**
    * Get the charAndWeapMovement
    * 
    * @return the charAndWeapMovement
    */
   public ArrayList getCharAndWeapLog()
   {
       return charAndWeapMovement;   
   }
   
   /**
    * Get the suggestions
    * 
    * @return the suggestions
    */
   public ArrayList getSuggestionLog()
   {
       return suggestions;
   }
   
   /**
    * Get the accusations
    * 
    * @return the accusations
    */
   public ArrayList getAccusationLog()
   {
      return accusations;
   }
   
}
