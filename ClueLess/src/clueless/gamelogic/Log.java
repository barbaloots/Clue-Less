package clueless.gamelogic;

/**
 * This class creates the logs required for the game as well as
 * the methods for how the logs are updated as the game progresses.
 */

import java.util.ArrayList;

public class Log 
{
   private ArrayList charAndWeapMovement;
   private ArrayList suggestions;
   private ArrayList accusations;
   
   public Log()
   {
       charAndWeapMovement = new ArrayList();
       suggestions = new ArrayList();
       accusations = new ArrayList();
   }
   
   public void updateCharAndWeapMovement(CharacterName character, WeaponType weapon, RoomName previousCharRoom, 
   RoomName previousWeaponRoom, RoomName destinationRoom)
   {
       charAndWeapMovement.add(character + " was moved from " + previousCharRoom + " to " + destinationRoom + "."
       + "\n" + weapon + " was moved from " + previousWeaponRoom + " to " + destinationRoom + ".");
   }
   
   public void updateSuggestions(CharacterName character, WeaponType weapon, RoomName destinationRoom)
   {
       suggestions.add("Suggestion made: It was " + character + " with the " + weapon + " in the " +
       destinationRoom + ".");
   }
   
   public void updateAccusations(CharacterName character, WeaponType weapon, RoomName room)
   {
       accusations.add("Accusation made: It was " + character + " with the " + weapon + " in the " +
       room + ".");
   }
   
   public void checkLogByTurn(ArrayList chosenLog, int turnNumber)
   {
       System.out.println(chosenLog.get(turnNumber - 1));   
   }
   
   
   public void printAllFromLog(ArrayList chosenLog)
   {
       for(String entry : chosenLog)
       {
           System.out.println(entry);   
       }
   }
   
   public ArrayList getCharAndWeapLog()
   {
       return charAndWeapMovement;   
   }
   
   public ArrayList getSuggestionLog()
   {
       return suggestions;
   }
   
   public ArrayList getAccusationLog()
   {
      return accusations;
   }
   
}
