package clueless.gamelogic;

/**
 * Main class for the ClueLess game
 */
public class Clueless {
	
	/**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
	   // Eventual invocation will be different
	   generateAllCards();
	   playerStartLocations();
   }
   
   /**
    * Confirming design used to represent cards.
    */
   public static void generateAllCards() {
	   for(CharacterName character : CharacterName.values()) {
		   CharacterCard card = new CharacterCard(character.name());
		   System.out.println(card.toString());
	   }
	   
	   for(RoomName room : RoomName.values()) {
		   RoomCard card = new RoomCard(room.name());
		   System.out.println(card.toString());
	   }
	   
	   for(WeaponType weapon : WeaponType.values()) {
		   WeaponCard card = new WeaponCard(weapon.name());
		   System.out.println(card.toString());
	   }
   }
   
   /**
    * At the start of the game, set each of the player's characters to their respective locations
    */
   private static void playerStartLocations() {
	   
   }
}
