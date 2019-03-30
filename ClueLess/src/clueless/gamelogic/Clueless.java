package clueless.gamelogic;

/**
 *
 */
public class Clueless {
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
	   // Eventual invocation will be different
	   generateAllCards();
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
}
