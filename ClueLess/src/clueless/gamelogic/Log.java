package clueless.gamelogic;

/**
 * This class creates the logs required for the game as well as
 * the methods for how the logs are updated as the game progresses.



 logging will be done on a per class basis, this is so that we do not have to pass a reference of the logging class throughout the program
 log4j seems to be able to have multiple handles on a single log file
 each log line is referenced by class which is based off the instantiation of the logging class itself (when I call class.getClass())
 I am passing in a general reference to the type of class that the logger is being delcared in

 If a log for a specific class wants to be utilized then, in the class declaration you declare an instance of the Log class e.g. Log logger = null;
 Then the log class will be instantiated in the constructor e.g. logger = Log(this);
 this references the type of class that should be passed in and outputted into the log file

 some specific methods have been created in order to aid in the logging syntax we are looking for and as to not repeat the lines
 otherwise, the specific reference to the logger can be passed and utilized in the host class

 the controls "private static final" on the declaration of the logger make sure that each instance is indivudal and does not conflict with other logger instances created
 */

import java.util.ArrayList;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Log 
{

   private static final Logger logger = null;

   //private ArrayList charAndWeapMovement;
   //private ArrayList suggestions;
   //private ArrayList accusations;
   
   public Log(Class class)
   {
      logger = Logger.getLogger(class.getClass()) //can I do this?

      PropertyConfiguratior.configure("log4j.properties")

      //charAndWeapMovement = new ArrayList();
      //suggestions = new ArrayList();
      //accusations = new ArrayList();
   }
   
   public void logCharAndWeapMovement(CharacterName character, WeaponType weapon, RoomName previousCharRoom, RoomName previousWeaponRoom, RoomName destinationRoom)
   {
      logger.info(character.getCharacterName() + " was moved from " + previousCharRoom.getRoomName() + " to " + destinationRoom.getRoomName() + "." + "\n" + weapon.getWeapon() + " was moved from " + previousWeaponRoom.getRoomName() + " to " + destinationRooml.getRoomName()+ "."))
       //charAndWeapMovement.add(character + " was moved from " + previousCharRoom + " to " + destinationRoom + "." + "\n" + weapon + " was moved from " + previousWeaponRoom + " to " + destinationRoom + ".");
   }
   
   public void logSuggestions(CharacterName character, WeaponType weapon, RoomName destinationRoom)
   {
      logger.info("Suggestion made: It was " + character.getCharacterName() + " with the " + weapon.getWeapon() + " in the " + destinationRoom.getRoomName() + ".")
       //suggestions.add("Suggestion made: It was " + character + " with the " + weapon + " in the " + destinationRoom + ".");
   }
   
   public void logAccusations(CharacterName character, WeaponType weapon, RoomName room)
   {
      logger.info("Accusation made: It was " + character.getCharacterName() + " with the " + weapon.getWeapon() + " in the " + room.getRoomName() + ".")
       //accusations.add("Accusation made: It was " + character + " with the " + weapon + " in the " + room + ".");
   }
   /*
   public void checkLogByTurn(ArrayList chosenLog, int turnNumber)
   {
       System.out.println(chosenLog.get(turnNumber - 1));   
   }*/
   
   //Logs will be wrriten to file for post processing debug
   /*public void printAllFromLog(ArrayList chosenLog)
   {
       for(String entry : chosenLog)
       {
           System.out.println(entry);   
       }
   }*/
   
    public Logger getLogger()
    {
      return logger
    }

   /*public ArrayList getCharAndWeapLog()
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
   }*/
   
}
