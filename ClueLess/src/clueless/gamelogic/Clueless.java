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
      Weapon weapon = null;

      System.out.println("6 instances of the character card object:");
      for (CharacterName character : CharacterName.values()) {
         CharacterCard card = new CharacterCard(character.name());
         System.out.println(card.toString());
      }

      System.out.println("\n9 instances of the room card object:");
      for (RoomName room : RoomName.values()) {
         RoomCard card = new RoomCard(room.name());
         System.out.println(card.toString());
      }

      System.out.println("\n6 instances of the room card object:");
      for (WeaponType weapon2 : WeaponType.values()) {
         WeaponCard card = new WeaponCard(weapon2.name());
         System.out.println(card.toString());
      }
      System.out.println("\nIdentify location of item on game board using x,y coordinates:");
      Location loc = new Location(5, 8);
      System.out.println("Item located at X coordinate " + loc.getX() + ", y coordinate " + loc.getY());
   }
}
