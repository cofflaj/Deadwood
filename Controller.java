import java.util.Scanner;

public class Controller {

    private Scanner scanner = new Scanner(System.in);
    
    // Gets the player names and starts the game
    public void openMainMenu() {
      for(int i = 0; i < Deadwood.game.getNumberOfPlayers(); i++) {
         if(!Deadwood.game.addPlayer(i))
         i--;
      }
      Deadwood.game.startGame();
    }
    
    // Opens the turn menu and activates relevent labels, allowing players to take actions
    public void openTurnMenu() {
        
        boolean[] options = Deadwood.game.calculateOptions();
        boolean endTurn = false;
        boolean canMove = true;
        String choice;
        while(!endTurn){
            // 0-Move 1-Act 2-Rehearse 3-Upgrade 4-TakeARole 5-EndTurn
            options = Deadwood.game.calculateOptions();
            Deadwood.view.showTurnMenu((canMove && options[0]),options[1],options[2],options[3],options[4],true);
            choice = getInput();
//             if(choice == null) {}
            if(choice.equals("a") && options[1]) {
                Deadwood.game.act();
                endTurn = true;
            }
            else if(choice.equals("r") && options[2]) {
                Deadwood.game.rehearse();
                endTurn = true;
            }
            else if(choice.equals("m") && options[0] && canMove) {
                Deadwood.view.nullButtons();
                Deadwood.view.moveSelected(Deadwood.game.getLocationAndNeighbors());
                String moveChoice = getInput();
                Deadwood.view.hideMovementLabels();
                if(!moveChoice.equals("c") && Deadwood.game.move(moveChoice)) {
                  canMove=false;
                  Deadwood.game.updateMoveInfo();
                }
            }
            else if(choice.equals("u") && options[3]) {
                Deadwood.view.nullButtons();
                if(Deadwood.game.upgrade()) {
                  Deadwood.view.alertUpgradeSuccess();
                }
                else {
                  Deadwood.view.alertUpgradeFailed();
                }
            }
            else if(choice.equals("t") && options[4]) {
                Deadwood.view.nullButtons();
                if(Deadwood.game.takeRole())
                  endTurn = true;
            }
            else if(choice.equals("e")){
                endTurn = true;
            }
        }
        Deadwood.game.transitionTurn();
    }

    // Gets the role that the player wants to take
    public boolean offerRoles(boolean[] isAvailable, String setName, String[] names, int[] levels, boolean[] leadRole, int[][] roleAreas) {
      Deadwood.view.offerRoles(isAvailable, setName, names, levels, leadRole, roleAreas);
      String choice = getInput();
      if(choice.equals("c"))
         return false;
      int roleChoice = -1;
      boolean tookRole = false;
      for(int i = 0; i < names.length; i++) {
         if(choice.equals("r"+i+"") && isAvailable[i]) {
            if(Deadwood.game.makeRoleSelection(levels[i], names[i])) {
               roleChoice = i;
               tookRole = true;
            }
         }
      }
      Deadwood.view.removeRoleLabels();
      if(tookRole) {
         Deadwood.view.reportSuccessfulRole();
         Deadwood.view.updateRoleInfo(Deadwood.game.getCurrentTurn(), setName, roleAreas[roleChoice], leadRole[roleChoice]);
         return true;
      }
      else{
         Deadwood.view.reportInvalidRole();
         return false;
      }
      
    }
    
     // Gets the rank that the player wants to upgrade to
     public boolean getDesiredRank(int rank, int dollars, int credits) {
      int[] RANKS = {2,3,4,5,6};
      int[] DOLLARCOSTS = {4,10,18,28,40};
      int[] CREDITCOSTS = {5,10,15,20,25};
      boolean[] canPurchase = new boolean[10];
      
      for(int i = 0; i<10; i+=2) {
         int index = i/2;
         if(rank>=RANKS[index])
            canPurchase[i] = false;
         else {
            canPurchase[i] = !(dollars<DOLLARCOSTS[index]);
            canPurchase[i+1] = !(credits<CREDITCOSTS[index]);
         }
      }
      
      
      Deadwood.view.offerRanks(canPurchase);
      String choice = getInput();
      if(choice.equals("c")){
         Deadwood.view.hideUpgradeButtons();
         return false;
      }
      int index = -1;
      if(!choice.substring(0,1).equals("u")) {
         Deadwood.view.hideUpgradeButtons();
         return false;
      }
      for(int i = 0; i < RANKS.length; i++) {
         if(choice.substring(1,2).equals(Integer.toString(RANKS[i])))
            index = i;
      }
      Deadwood.game.setPlayerRank(RANKS[index]);
      if(choice.substring(2,3).equals("d"))
         Deadwood.game.changeFunds(-1, true, DOLLARCOSTS[index] * -1);
      else
         Deadwood.game.changeFunds(-1, false, CREDITCOSTS[index] * -1);
      Deadwood.view.hideUpgradeButtons();

      
      return true;     
   }
   
    // -----------Bridge functions to prevent coupling between model and view ------------------------
    public String getPlayerToAdd(int num) {
         return Deadwood.view.getPlayerToAdd(num); 
    }
    
    public void alertNotANumber() {
         Deadwood.view.alertNotANumber();
    }
    
    public void resetCard(int[] area, int index) {
         Deadwood.view.resetCard(area, index);
    }
    
    public void wrapScene(String setName, int[] players) {
      Deadwood.view.wrapScene(setName, players);
    }
    
    public void setCard(int[] area, String imageLink, int index) {
         Deadwood.view.setCard(area, imageLink, index);
    }
    
    public void setPlayersToStart(int p) {
         Deadwood.view.setPlayersToStart(p);
    } 
    public void updateScoreboard(int player, int amount, String decider) {
      Deadwood.view.updateScoreboard(player, amount, decider);
    }
    
    public void setUpShotCounter(int[][] areas, int location) {
      Deadwood.view.setUpShotCounter(areas, location);
    }
    
    public void takeShot(String location, int num) {
      Deadwood.view.takeShot(location, num);
    }
    
    public void updateMoveInfo(int player, String location, String imageLink) {
      Deadwood.view.updateMoveInfo(player, location, imageLink);
    }
    
    public String getInput() {
        return Deadwood.view.getPlayerSelection();
    }
    
    public void alertWrappedAScene(String setName){
      Deadwood.view.alertWrappedAScene(setName);
    }
    
    public void alertNameInUse() {
      Deadwood.view.reportNameAlreadyInUse();
    }
    
    public void alertNewTurn(String name) {
         Deadwood.view.displayNewTurn(name);
    }
    
    public void alertDayEnd(int d) {
         Deadwood.view.displayDayEnd(d);
    }
    
    public void alertDiceRolled(int n) {
         Deadwood.view.displayDiceRolled(n);
    }
    
    public void alertSuccessfulAct(Boolean s) {
         Deadwood.view.alertSuccessfulAct(s);
    }
    
    public void alertFailedAct(Boolean s){
         Deadwood.view.alertFailedAct(s);
    }
    
    public void alertRehearseNum(int p, int r) {
         Deadwood.view.displayRehearseNum(p, r);
    }
    
    public void alertWrapRoll(int l, int[] d) {
         Deadwood.view.displayWrapRoll(l, d);
    }
    
    public void alertAwardedMoney(String n, int m) {
         Deadwood.view.displayAwardedMoney(n, m);
    }
    
    public void alertGameEnd(String winner, int score) {
      Deadwood.view.displayWinner(winner, score);
    }
    
    public void alertPlayerOrder(String[] n) {
       Deadwood.view.displayPlayerOrder(n);
       Deadwood.view.createScoreboard(n);
    }
    // ---- End bridge functions ---------------------------------

}
