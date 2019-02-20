import java.util.*;

public class Player {
   
   private String name;
   private int rank;
   private boolean isWorking = false;
   private int rehearseNum = 0;
   private int number;
   
   public Player() {
   
   }
   
   public Player(String n) {
      name = n;
      rank = 1;
   }

   public int getNumber() {
      return number;
   }
   
   public void rehearse() {
     this.rehearseNum++;
   }
   
   public void resetRehearse() {
      rehearseNum = 0;
   }
   
   public void setNumber(int n) {
      number = n;
   }
   
   public String getName() {
      return name;
   }
   
   public int getRank() {
      return rank;
   }
   
   public void setRank(int r) {
      rank = r;
   }
   
   public boolean getIsWorking() {
      return isWorking;
   }
   
   public void gotAJob() {
      isWorking = true;
   }
   
   public void finishedRole() {
      isWorking = false;
   }
   
   public int getRehearseNum() {
      return rehearseNum;
   }
   

}