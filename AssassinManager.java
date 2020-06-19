// Shivangi Khanna

// Manages the game Assassin. Takes a list of players as an input. Each player is 
// stalking the next player on the list. When a player is killed, they move to the graveyard. 
// When only one player remains, the game gets over and that player is the winner.
import java.util.*;

public class AssassinManager {
   
   // Stores the front of the kill ring and the graveyard.
   private AssassinNode killRingFront;
   private AssassinNode graveFront;
   
   // Constructs the initial kill ring. Initializes it with the list of player names.
   // Throws Illegal Argument Exception if the list is empty.
   public AssassinManager(List<String> names) {
      if(names.size() == 0 || names == null) {
         throw new IllegalArgumentException();
      }
      killRingFront = new AssassinNode(names.get(0));
      AssassinNode temp = killRingFront;
      for(int i = 1; i < names.size(); i++) {
         temp.next = new AssassinNode(names.get(i));
         temp = temp.next;
      }
   }
   
   // Prints the names in the kill ring indented by 4 spaces. 
   public void printKillRing() {
      AssassinNode temp = killRingFront;
      while(temp.next != null) {
         System.out.println("    " + temp.name + " is stalking " + temp.next.name);
         temp = temp.next;
      }
      System.out.println("    " + temp.name + " is stalking " + killRingFront.name);
   }
   
   // Prints the names in the graveyard indented by 4 spaces.
   // No output if the graveyard is empty.
   public void printGraveyard() {
      if(graveFront != null) {
         AssassinNode temp = graveFront;
         while(temp.next != null) {
            System.out.println("    " + temp.name + " was killed by " + temp.killer);
            temp = temp.next;
         }
         System.out.println("    " + temp.name + " was killed by " + temp.killer);
      }
   }
   
   // Returns true if the given name is in the kill ring while ignoring the case 
   // of the letters.
   public boolean killRingContains(String name) {
      AssassinNode temp = killRingFront;
      while(temp != null) {
         if(temp.name.equalsIgnoreCase(name)) {
            return true;
         }
         temp = temp.next;
      }
      return false;
   }
   
   // Returns true if the given name is in the graveyard while ignoring the case 
   // of the letters.
   public boolean graveyardContains(String name) {
      AssassinNode temp = graveFront;
      while(temp != null) {
         if(temp.name.equalsIgnoreCase(name)) {
            return true;
         }
         temp = temp.next;
      }
      return false;
   }

   // Returns true if the game is over.
   public boolean gameOver() {
      return killRingFront.next == null;
   }
   
   // Returns the name of the winner of the game and null if the game is not over.
   public String winner() {
      if(gameOver()) {
         return killRingFront.name;
      } else {
         return null;
      }
   }
   
   // Assassinates the victim and updates their killer to the name of the person
   // who killed them. Adds the killed person to the graveyard and removes them 
   // from the killring. Ignores the case of the names. 
   // Throws IllegalStateException if the game is over.
   // Throws IllegalArgumentException if the kill ring does not contain the name.
   public void kill(String name) {
      if(gameOver()) {
         throw new IllegalStateException();
      }
      if(!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      AssassinNode killed = killRingFront;
      AssassinNode assassin = killRingFront;
      if(killed.name.equalsIgnoreCase(name)) {
         while(assassin.next != null) {
            assassin = assassin.next;
         }
         killed.killer = assassin.name;
         update(killed, assassin);
      } else {
         while(!killed.next.name.equalsIgnoreCase(name)) {
            killed = killed.next;
         }
         assassin = killed;
         killed = killed.next;
         killed.killer = assassin.name;
         update(assassin, killed);
      }
   }
   
   // Updates the kill ring and the graveyard by moving players from kill ring to the 
   // graveyard. 
   // The person killed last is at the front of the graveyard.
   private void update(AssassinNode alive, AssassinNode dead) {
      if(alive.killer != null) {
         dead = alive;
         alive = alive.next;
      } else {
         alive.next = alive.next.next;
      }
      dead.next = graveFront;
      graveFront = dead;
   }
}