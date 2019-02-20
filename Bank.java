/*
This class is used to store credits and dollars for each player in the game
*/
public class Bank {

   private PlayerFunds[] funds;
   public final int[] RANKS = {2,3,4,5,6};
   public final int[] DOLLARCOSTS = {4,10,18,28,40};
   public final int[] CREDITCOSTS = {5,10,15,20,25};
   
   public Bank(int p) {
      funds = new PlayerFunds[p];
      int creditStart = 0;
      if(p==5) {
         creditStart = 2;
      } else if(p==6) {
         creditStart = 4;
      }
      
      for(int i=0; i<p; i++) {
         PlayerFunds pf = new PlayerFunds(0,creditStart);
         funds[i] = pf;
      }
   }
   
   // Returns the specified funds for a given player
   public int getFunds(int p, boolean d) {
      if(d)
         return funds[p].dollars;
      else
         return funds[p].credits;
   }
   
   // Changes the recorded funds for the given player
   public void changeFunds(int p, boolean d, int m) {
      if(d)
         funds[p].dollars+=m;
      else
         funds[p].credits+=m;
   }
   
   // Helper class used to store credits and dollars
   class PlayerFunds {

      int dollars;
      int credits;
      
      PlayerFunds(int d, int c) {
         dollars = d;
         credits = c;
      }
   }
}
