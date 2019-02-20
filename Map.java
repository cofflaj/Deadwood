/*
   Map class
   Responsibilites:
      - Keeps track of players locations on the map
      - Moves players when required
*/
import java.lang.Math;
import java.util.*;
import org.w3c.dom.Document;

public class Map {
   
   private Location[] locations = new Location[12];
   private int[] playerLocations;
   private int lCount = 0; //helper int for tracking the number of locations in the array
   private Scene[] scenes;
   private boolean[] usedScene = new boolean[40];
   private int unusedScenesLeft = 40;
   
   public Map() {} 
   
   public Map(int p) {
      playerLocations = new int[p];
   }
   
   // Helper method for building the board
   public void setLocation(Location l) {
      locations[lCount] = l;
      lCount++;
   }
   
   public void setScenes(Scene[] s) {
      scenes = s;
   }
   
   public int[] getLocationArea(int i) {
      return locations[i].getArea();
   }
   
   // Returns the location containing the player specified
   // Param (int p) the index of the player in the array Game.players
   public Location getPlayerLocation(int p) {
      return locations[playerLocations[p]];
   }
   
   // Moves all players to the trailer
   public void setPlayersToStart() {
      for(int i = 0; i<playerLocations.length; i++) {
         playerLocations[i] = 10;
      }
   }
   
   //Returns indexes of players at a given location
   public int[] getPlayersAtLocation(int p) {
      Location location = getPlayerLocation(p);
      ArrayList<Integer> players = new ArrayList<Integer>();
      for(int i = 0; i < playerLocations.length; i ++) {
         if(getPlayerLocation(i).getName().equals(location.getName())) {
            players.add(i);
         }
      }
      int[] ret = new int[players.size()];
      for(int i = 0; i < ret.length; i ++) {
         ret[i] = players.get(i);
      }
      return ret;
   }
   
   //prepareBoard method that parses the xml docs, sets the locations/sets for the map, 
   // and then assigns scenes to all of the sets
   public void prepareBoard() {
      Document doc = null;
      ParseXML parsing = new ParseXML();
      
      //try catch for the board data
      try{
         doc = parsing.getDocFromFile("board.xml");
         parsing.readBoardData(doc, this);
      } catch (Exception e){
         System.out.println("Board reading error = "+e);
      }
      
      //try catch for the card data
      try{
         doc = parsing.getDocFromFile("cards.xml");
         parsing.readCardData(doc, this);
      } catch (Exception e){
         System.out.println("Card reading error = "+e);
      }
      
      assignScenes();
      
   }
   
   // Assigns a new scene card to every set on the board
   public void assignScenes() {
      if(unusedScenesLeft < 10) {
         usedScene = new boolean[40];
      }
      int i = 0;
      while(i < 10) {
         int sceneNum = (int)(40*Math.random());
         if(!usedScene[sceneNum]) {
            ((Set)locations[i]).setScene(scenes[sceneNum]);
            usedScene[sceneNum] = true;
            ((Set)locations[i]).unwrap();
            ((Set)locations[i]).resetRoles();
            ((Set)locations[i]).resetShots();
            i++;
         }
      }
      
      unusedScenesLeft -= 10;
      
   }

   
   // Changes the location of a given player to a given location
   public void movePlayer(int player, String location) {
      for(int i = 0; i<locations.length; i++) {
         if(locations[i].getName().equals(location))
            playerLocations[player] = i;
      }
   }
   
   public int[][] getShotAreas(int l) {
      return ((Set)locations[l]).getShotAreas();
   }
   
   public boolean getIsStarring(int p) {
      return ((Set)getPlayerLocation(p)).getIsStarring(p);
   }
   
   public int getSetBudget(int p) {
      return ((Set)getPlayerLocation(p)).getBudget();
   }
   
   public int acceptShot(int p) {
      return ((Set)getPlayerLocation(p)).acceptShot();
   }
   
   public boolean getIsLead(int p) {
      return ((Set)getPlayerLocation(p)).getIsLead();
   }
   
   public int[] getLeadActors(int p) {
      return ((Set)getPlayerLocation(p)).getLeadActors();
   }
   
   public int[] getExtraActors(int p) {
      return ((Set)getPlayerLocation(p)).getExtraActors();
   }
   
   public String getSetName(int p) {
      return getPlayerLocation(p).getName();
   }
   
   public String[] getLocationNeighbors(int p) {
      return getPlayerLocation(p).getNeighbors();
   }
   
   public String[] getRoleNames(int p) {
      return ((Set)getPlayerLocation(p)).getRoleNames();
   }
   
   public int[] getRoleRanks(int p) {
      return ((Set)getPlayerLocation(p)).getRoleRanks();
   }
   
   public boolean makeRoleSelection(int p, String n) {
      return ((Set)getPlayerLocation(p)).makeRoleSelection(p, n);
   }
   
   public boolean[] getIfLeadRole(int p) {
      return ((Set)getPlayerLocation(p)).getIfLeadRole();
   }
   
   public boolean isAtSet(int p) {
      return !(playerLocations[p] == 10 || playerLocations[p] == 11);
   }
   
   public boolean areAvailableRoles(int p) {
      return ((Set)getPlayerLocation(p)).areAvailableRoles();
   }
   
   public boolean[] getAvailableRoles(int p, int r) {
      return ((Set)getPlayerLocation(p)).getAvailableRoles(r);
   }
   
   public boolean isAtOffice(int p) {
      return playerLocations[p] == 11;
   }
   
   public String getSceneImage(int p) {
      if(playerLocations[p] <10)
         return ((Set)getPlayerLocation(p)).getSceneImage();
      else
         return null;
   }
   
   public String getLocationChoice(int l) {
      return locations[l].getName();
   }
   
   public int[][] getRoleAreas(int p) {
      return ((Set)getPlayerLocation(p)).getRoleAreas();
   }

}