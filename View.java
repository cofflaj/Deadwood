public class View {
   
    GUI board;


    public View() {
       board = new GUI();
    }

    public String getPlayerCount() {
      return board.getPlayerCount();
    }
    
    public void alertNotANumber() {
      board.alertNotANumber();
    }
    
    public void createScoreboard(String[] n) {
      board.createScoreboard(n);
    }
    
    public void resetCard(int[] area, int index) {
      board.resetCard(area, index);
    }
    
    public void setCard(int[] area, String imgLink, int index) {
      board.setCard(area, imgLink, index);
    }
    
    public void setPlayersToStart(int p) {
      board.setPlayersToStart(p);
    }

    public void showTurnMenu(boolean canMove, boolean canAct, boolean canRehearse, boolean canUpgrade, boolean canTakeRole, boolean canEnd) {

        board.showButtons(canMove, canAct, canRehearse, canUpgrade, canTakeRole, canEnd);
    }
    
    public String getPlayerSelection() {
         return board.getPlayerSelection();
    }
    
    public void nullButtons() {
      board.showButtons(false,false,false,false,false,false);
    }
    
    public void moveSelected(String[] locations) {
      board.showAllowedLocations(locations);
      board.updateConsole("Please select a neighboring room's card to move there, or select your current room's card to cancel.\n" +
                          "If there is no card, clicking anywhere in the room will do.\n");
    }
    
    public void hideMovementLabels() {
      board.hideMovementLabels();
    }
    
    public void updateScoreboard(int player, int amount, String decider) {
      board.updateScoreboard(player, amount, decider);
    }
    
    public void setUpShotCounter(int[][] areas, int location) {
      board.setUpShotCounter(areas, location);
    }
    
    public void takeShot(String location, int num) {
      board.takeShot(location, num);
    }
    
    public void updateMoveInfo(int player, String location, String imageLink) {
      board.updateMoveInfo(player, location, imageLink);
    }
    
    public void updateRoleInfo(int player, String setName, int[] roleArea, boolean isLeadRole) {
      board.updateRoleInfo(player, setName, roleArea, isLeadRole);
    }
    
    public void removeRoleLabels() {
      board.removeRoleLabels();
    }
    
    public void wrapScene(String setName, int[] players) {
      board.wrapScene(setName, players);
    }
    
    public String getPlayerToAdd(int num) {
         return board.getPlayerName(num);
    }
    
    public void reportNameAlreadyInUse() {
         board.sendMessage("Oops! That name's already used! Player not added.\n");
    }
    
    
    
    public void displayPlayerOrder(String[] names) {
         String output = "After shuffling the players, here is the current player order:\n";
         for(int i = 0; i < names.length; i++) {
            output+= names[i] + "\n";
         }
         board.updateConsole(output);
    }
    
    public void reportSuccessfulRole() {
         board.updateConsole("You have successfully taken the role.\n");
    }
    
    public void reportInvalidRole() {
         board.updateConsole("You are unable to take that role, or you have cancelled the action.\n");
    }
    
    public void displayRehearseNum(int p, int r) {
         board.updateConsole("You have rehearsed " + r + " times for this role.\n");
         board.updateScoreboard(p, r, "rehearse");
    }
    
    public void displayNewTurn(String name) {
         board.updateConsole("--------------------------------\nIt is now " + name + "'s turn.\n");
    }
    
    public void displayDayEnd(int d) {
         board.sendMessage("Day " + d + " has ended. All players have been returned to the Trailers.\n");
    }
    
    public void displayWinner(String winner, int score) {
         board.sendMessage("The game has ended!\nPlayer " + winner + " has won the game with a score of " + score + " points! Congratulations!");
    }
    public void offerRoles(boolean[] isAvailable, String setName, String[] n, int[] l, boolean[] leadRole, int[][] roleAreas) {
      board.updateConsole("Click a role to take it, or click anywhere else to cancel.\n");
      board.displayRoles(setName, isAvailable, roleAreas, leadRole);
    }
    
    public void displayDiceRolled(int n) {
         board.updateConsole("You rolled a " + n + ".\n");
    }
    
    public void alertSuccessfulAct(boolean s) {
         if(s)
            board.updateConsole("You acted successfully! You have earned $2.\n");
         else
            board.updateConsole("You acted successfully! You have earned $1 and 1 credit.\n");
    }
    
    public void alertFailedAct(boolean s){
         if(s)
            board.updateConsole("You didn't act well enough. You earn nothing :(\n");
         else
            board.updateConsole("You didn't act well enough. You still earn 1 credit.\n");
    }
    
    public void displayWrapRoll(int l, int[] d) {
         String output = "Since there are 1 or more actors with starring roles, everyone will a role at this set will get some bonus money!\n";
         output += l + " dice have been rolled and sorted highest to lowest: ";
         for(int i = l-1; i >= 0; i--) {
            output += d[i] + " ";
         }
         board.updateConsole(output + "\n");
    }
    
    public void displayAwardedMoney(String n, int m) {
         board.updateConsole(n + " has been awarded $" + m + ".\n");
    }
    
    public void offerRanks(boolean[] canPurchase) {
      board.displayUpgradeButtons(canPurchase);
      String output = "Click the button that corresponds with the rank and payment method you desire, or anywhere else to cancel.\n";
      board.updateConsole(output);
   }
   
   public void hideUpgradeButtons() {
      board.hideUpgradeButtons();
   }
   
   public void alertUpgradeSuccess() {
      board.updateConsole("Upgrade complete!\n");
   }
   
   public void alertUpgradeFailed() {
      board.updateConsole("Upgrade cancelled.\n");
   }    
   
   public void alertWrappedAScene(String setName) {
      board.updateConsole("Wrapped " + setName + "!\n");
   }

}