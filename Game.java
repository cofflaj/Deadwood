import java.lang.Math;
import java.util.*;


/*
   Game class
   Responsibilites:
      - Set up the game board and the player array
      - Track the players, map, and bank, as well as the day, the player currently 
         taking their turn, and the number of wrapped scenes
      - Handle transitions between turns, days, and wrapping scenes
      - Scoring the game
*/
public class Game {

   //Game attributes
   
   
   //main attributes of game; most of the methods interact with these attributes
   private Map map;
   private Bank bank;
   private Player[] players;
   
   //values that track certain aspects of the game
   private int currentTurn = 0; //players[currentTurn] returns the current player's object
   private int day = 0;
   private int wrappedScenes = 0;
   private int finalDay = 4;
   
   
   //empty constructor, game is immediately initiailized 
   public Game(String numberOfPlayers) {
      players = new Player[Integer.parseInt(numberOfPlayers)];
   }


   //getters for the index in the array for the 
   //current player and the number of players

   public int getCurrentTurn() {
      return currentTurn;
   }
   public int getNumberOfPlayers() {
      return players.length;
   }
   
   
   
   //addPlayer method, used to add a player to the potentialPlayers arraylist
   public boolean addPlayer(int num) {
   
      //gets input from the user to use as the player's name
      String playerName = Deadwood.controller.getPlayerToAdd(num+1);
      for(int i = 0; i < players.length; i++) {
         if(playerName == null) {
            System.exit(0);
         }
         if(players[i] == null) {
            Player player = new Player(playerName);
            players[i] = player;
            return true;
         } else if(playerName.equals(players[i].getName())) {
            Deadwood.controller.alertNameInUse();
            return false;
         }
      }
      
      //if the name doesn't exist, add a player object with that name to the array
      
      //else tell the user that the player is already there
      
      
      return true;
      
   }
   
   private void resetCards() {
      for(int i = 0; i < 12; i++) {
         Deadwood.controller.resetCard(map.getLocationArea(i), i);
      }
   }
   
   private void resetShotCounters() {
      for(int i = 0; i<10; i++) {
         int[][] shotAreas = map.getShotAreas(i);
         Deadwood.controller.setUpShotCounter(shotAreas, i);
      }
   }
   
   
   //startGame method, sets up everything in the game and then starts the game with first player in the players array
   public void startGame() {
   
      //creates the players array from the potentialPlayers arraylist
      
      
      //shuffles the players and then prints out the turn order
      decideOrder();
      String[] names = new String[players.length];
      for(int i = 0; i<players.length; i++) {
         names[i] = players[i].getName();
      }
      Deadwood.controller.alertPlayerOrder(names);
      
      
      //initializes the map object using the number of players
      map = new Map(players.length);
      
      
      //prepares the game board
      map.prepareBoard();
      
      resetCards();
      resetShotCounters();
      
      
      //moves every player to the starting location
      map.setPlayersToStart();
      Deadwood.controller.setPlayersToStart(players.length);
      
      
      //initializes the bank object with the number of players
      bank = new Bank(players.length);
      
      
      //special cases of the game, where the number of days and the 
      // starting ranks change based on the number of players
      if(players.length<4)
         finalDay = 3;
      else if(players.length >= 7) {
         for(int i = 0; i < players.length; i++) {
            players[i].setRank(2);
         }
      }
      
      //starts the game by having the first player take their turn
      Deadwood.controller.alertNewTurn(players[0].getName());
      takeTurn();
      
      
   }
   
   
   //decideOrder method that shuffles the player order and then sets each player's
   // index in the array as their number
   private void decideOrder() {
      // Shuffles the player array
      for(int i = 1; i < players.length+1; i++) {
         int r = (int)(Math.random()*players.length);
         Player temp = players[r];
         players[r] = players[i-1];
         players[i-1] = temp;
      }
      for(int i = 0; i < players.length; i++) {
         players[i].setNumber(i);
      }
      
   }
   
   
   //transitionTurn method that transitions between turns, making sure that the day did not end after the 
   // previous player's turn
   public void transitionTurn() {
      
      //makes sure that the currentTurn value loops back to the beginning after the final player in the array
      // takes their turn
      if(currentTurn == players.length-1)
         currentTurn=0;
      else
         currentTurn++;
         
      //starts the next player's turn
      Deadwood.controller.alertNewTurn(players[currentTurn].getName());
      takeTurn();
   }
   
   
   //transitionDay function that ends the previous day and starts the next one,
   // unless it was the final day, which then ends and scores the game
   public void transitionDay() {
      day++;
      Deadwood.controller.alertDayEnd(day);
      if(day==finalDay)
         scoreGame();
      else {
      
         //resets the scenes on the board and then continues with the next player's turn
         map.assignScenes();
         resetCards();
         resetShotCounters();
         map.setPlayersToStart();
         fireAllActors();
         Deadwood.controller.setPlayersToStart(players.length);
         wrappedScenes=0;
         
         transitionTurn();
      }
   }
   
   private void fireAllActors() {
      for(int i = 0; i < players.length; i++) {
         players[i].finishedRole();
         players[i].resetRehearse();
      }
   }
   
   //scoreGame method that tallies up the points for each player and then displays the winner and their score
   public void scoreGame() {
      Player winner = players[0];
      int score = winner.getRank()*5 + bank.getFunds(0, true) + bank.getFunds(0, false);
      int score2;
      for(int i = 1; i<players.length; i++) {
         score2 = players[i].getRank()*5 + bank.getFunds(i, true) + bank.getFunds(i, false);
         if(score2 >= score) {
            winner = players[i];
            score = score2;
         }
      }
      Deadwood.controller.alertGameEnd(winner.getName(), score);
      System.exit(0);
   }
   
   
   //roll method used for wrapping a scene, roles dice equal to its parameter and then sorts the dice in ascending order
   public int[] roll(int numberOfDice){
      int[] ret = new int[numberOfDice];
      for(int i = 0; i < numberOfDice; i++) {
         ret[i] = rollDice();
      }
      Arrays.sort(ret);
      Deadwood.controller.alertWrapRoll(ret.length, ret);
      return ret;
   }
   
      
   //simple rollDice function that returns a random number between 1 and 6
   public int rollDice() {
      return (int)(6.0*Math.random()) + 1;
   }
   
   
   //wrapScene method used for wrapping a scene once the shotCounter for the given set reaches zero
   public void wrapScene() {
      String setName = map.getSetName(currentTurn);
      Deadwood.controller.alertWrappedAScene(setName);
      int budget = map.getSetBudget(currentTurn);
      boolean isLead = map.getIsLead(currentTurn);
      
      
      //if there are players on the card, give out the appropriate funds to everyone
      //else do nothing
      if(isLead) { 
         //get the lead actors, going through in reverse order to get the actor with the highest level role first
         int[] leadActors = map.getLeadActors(currentTurn);
         int index = 0;

         
         //give out funds for each lead actor
         int[] dice = roll(budget);
         
         //go through the dice array backwards to get the highest dice rolls in the front
         for(int i = dice.length- 1; i >= 0 ; i--) {   
            //index%occupiedLeadRoles will make sure the winnings are handed out in the correct order
            //example, 3 lead actors, budget of $5
            //the money handed out will be in the order: 1, 2, 3, 1, 2
            if(leadActors[index%leadActors.length] != -1) {
               changeFunds(leadActors[index%leadActors.length], true, dice[i]);
               Deadwood.controller.alertAwardedMoney(players[leadActors[index%leadActors.length]].getName(), dice[i]);
            }
            else
               i++;
            index++;
         }
         
         //give out money for each extra
         int[] extraActors = map.getExtraActors(currentTurn);
         int[] roleRanks = map.getRoleRanks(currentTurn);
         for(int i = 0; i < extraActors.length; i++) {
            if(extraActors[i] != -1){
               changeFunds(extraActors[i], true, roleRanks[i]);
               Deadwood.controller.alertAwardedMoney(players[extraActors[i]].getName(), roleRanks[i]);
            }
         }
      }
      
      //increment wrappedScenes, and make sure that the day ends if there is only one available scene left
      wrappedScenes++;
      int[] playersAtLocation = map.getPlayersAtLocation(currentTurn);
      Deadwood.controller.wrapScene(setName, playersAtLocation);
      for(int i = 0; i < playersAtLocation.length; i++) {
         players[playersAtLocation[i]].finishedRole();
         players[playersAtLocation[i]].resetRehearse();
      }
      if(wrappedScenes == 9) {
         transitionDay();
      }
   }
   
   // Performs the logic for a player acting
   public void act() {
      Player player = players[currentTurn];
      int roll = rollDice();
      Deadwood.controller.alertDiceRolled(roll);
      boolean isStarring = map.getIsStarring(currentTurn);

      if((roll+player.getRehearseNum()) >= map.getSetBudget(currentTurn)){
         if(isStarring) {
            changeFunds(currentTurn, true, 2);
         }
         else {
            changeFunds(currentTurn, true, 1);
            changeFunds(currentTurn, false, 1);
         }
         Deadwood.controller.alertSuccessfulAct(isStarring);
         String setName = map.getSetName(currentTurn);
         int shotsLeft = map.acceptShot(currentTurn);
         Deadwood.controller.takeShot(setName, shotsLeft);
         if(shotsLeft == 0) {
            wrapScene();
         }
      }
      else{
         if(!isStarring)
            changeFunds(currentTurn, false, 1);
         Deadwood.controller.alertFailedAct(isStarring);
      }
   }

   
   // Performs the logic for a player rehearsing
   public void rehearse() {
     players[currentTurn].rehearse();
     Deadwood.controller.alertRehearseNum(currentTurn, players[currentTurn].getRehearseNum());
   }
   
   // Performs the logic for a player upgrading
   public boolean upgrade() {
      return Deadwood.controller.getDesiredRank(players[currentTurn].getRank(), bank.getFunds(currentTurn, true), bank.getFunds(currentTurn, false));
   }
   
   
   // Decides what moves are legal for a player
   public boolean[] calculateOptions() {
      boolean options[] = new boolean[5];
      options[0] = canMove();
      options[1] = canAct();
      options[2] = canRehearse();
      options[3] = canUpgrade();
      options[4] = canTakeRole();
      return options;
   }
   
   public void takeTurn() {
      Deadwood.controller.openTurnMenu();
   }
   
   // Performs the logic for a player moving
   public boolean move(String choice) {
      int lIndex = Integer.parseInt(choice);
      String locationChoice = map.getLocationChoice(lIndex);
      String[] locations = getLocationAndNeighbors();
      if(locationChoice.equals(locations[0]))
         return false;
      for(int i = 1; i < locations.length; i++) {
         if(locationChoice.equals(locations[i])) {
            map.movePlayer(currentTurn, locations[i]);
            return true;
         }
      }
      return false;
   }
   
   public String[] getLocationAndNeighbors() {
      String[] allowedLocations = map.getLocationNeighbors(currentTurn);
      String[] ret = new String[allowedLocations.length + 1];
      for(int i = 1; i < ret.length; i++) {
         ret[i] = allowedLocations[i-1];
      }
      ret[0] = map.getSetName(currentTurn);
      return ret;
   }

   
   // Performs the logic for a player taking a role
   public boolean takeRole() {
      boolean[] availableRoles = map.getAvailableRoles(currentTurn, players[currentTurn].getRank());
      boolean roleTaken = offerRoles(availableRoles);
      if(roleTaken) {
         players[currentTurn].gotAJob();
         return true;
      }
      else
         return false;
   }

   public boolean canAct() {
      return players[currentTurn].getIsWorking();
   }

   public boolean canRehearse() {
      return (players[currentTurn].getIsWorking() && (players[currentTurn].getRehearseNum() < 6));
   }
   
   public boolean canMove() {
      return !players[currentTurn].getIsWorking();
   }

   public boolean canUpgrade() {
      if(map.isAtOffice(currentTurn) && players[currentTurn].getRank() != 6) {
         return true;
      }
      return false;
   }

   public boolean canTakeRole() {
      if(players[currentTurn].getIsWorking() || !map.isAtSet(currentTurn)){
         return false;
      }
      return map.areAvailableRoles(currentTurn);
   }

   public String getPlayerName(int i) {
      return players[i].getName();
   }   
   
   
   public void updateMoveInfo() {
      String location = map.getSetName(currentTurn);
      String imageLink = map.getSceneImage(currentTurn);
      Deadwood.controller.updateMoveInfo(currentTurn, location, imageLink);
   }
   
   
   public int getPlayerRank() {
      return players[currentTurn].getRank();
   }
   
   public void setPlayerRank(int i) {
      players[currentTurn].setRank(i);
      Deadwood.controller.updateScoreboard(currentTurn, i, "rank");
   }
   
   public String getPlayerLocation(int i) {
      return map.getSetName(i);
   }
   
   public void changeFunds(int player, boolean dollars, int amount) {
      int index;
      if(player == -1)
         index = currentTurn;
      else
         index = player;
      bank.changeFunds(index, dollars, amount);
      String decider;
      if(dollars)
         decider = "dollars";
      else
         decider = "credits";
      Deadwood.controller.updateScoreboard(index, bank.getFunds(index, dollars), decider);
      Deadwood.controller.updateScoreboard(index, players[index].getRank()*5 + bank.getFunds(index, true) + bank.getFunds(index, false), "score");
   }
   
   // Does the logic to offer the proper roles for a set
   public boolean offerRoles(boolean[] a) {
       String setName = map.getSetName(currentTurn);
       boolean[] leadRole = map.getIfLeadRole(currentTurn);
       String[] names = map.getRoleNames(currentTurn);
       int[] ranks = map.getRoleRanks(currentTurn);
       int[][] areas = map.getRoleAreas(currentTurn);

       return Deadwood.controller.offerRoles(a, setName, names, ranks, leadRole, areas);
    }
    
    // Does the logic to check if a selected role is valid
    public boolean makeRoleSelection(int rank, String name) {
    
      if(rank <= players[currentTurn].getRank())
         return map.makeRoleSelection(currentTurn, name);
      else
         return false;
    }
}