import java.util.*;
/*
   Set class
   Responsibilites:
      - Keeps track of the acting related tasks of a location
      - Stores the shot counters and off-card roles
*/
public class Set extends Location {

   private Scene scene;
   private Role[] roles;
   private Area[] shotArea;
   private int shotTotal;
   private int shotsLeft;
   private boolean isWrapped = false;
   
   public Set() {}
   
   public Set(String n, String[] l, Scene s, Role[] r, Area a, Area[] sa) {
      name = n;
      neighbors = l;
      scene = s;
      roles = r;
      area = a;
      shotArea = sa;
      shotsLeft = sa.length;
      shotTotal = sa.length;
   }
   
   public void setScene(Scene s) {
      scene = s;
   }
   
   public Role[] getSceneRoles() {
      return scene.getRoles();
   }
   
   public String getSceneImage() {
      return scene.getImageLink();
   }
   
   public int getBudget() {
      return scene.getBudget();
   }
   
   public int[][] getShotAreas(){
      int[][] shots = new int[shotTotal][4];
      for(int i = 0; i < shotTotal; i++) {
         shots[i] = shotArea[i].getArea();
      }
      return shots;
   }
   
   public boolean getIsStarring(int p) {
      Role[] extra = roles;
      boolean isStarring = true;
      for(int i = 0; i < extra.length; i++) { 
         if(p == extra[i].getActorNumber())
            isStarring = false;
            
      }
      
      return isStarring;
   }
   
   public boolean getIsLead() {
      Role[] leadRoles = getSceneRoles();
      for(int i = 0; i < leadRoles.length; i++) {
         if(leadRoles[i].getActorNumber() != -1) {
            return true;
         }
      }
      
      return false;
   }
   
   // Gets the actors on this set that are lead actors
   public int[] getLeadActors() {
      Role[] leadRoles = getSceneRoles();
      int[] leadActors = new int[leadRoles.length];
      for(int i = 0; i < leadRoles.length; i++) {
         leadActors[i] = leadRoles[leadRoles.length-i-1].getActorNumber();
      }
      return leadActors;
   }
   
   // Gets the actors for this set that are extras
   public int[] getExtraActors() {
      int[] extraActors = new int[roles.length];
      for(int i = 0; i < roles.length; i++) {
         extraActors[i] = roles[i].getActorNumber();
      }
      return extraActors;
   }
   
   // Resets the isWrapped boolean
   public void unwrap() {
      isWrapped = false;
   }
   
   // Resets the role object belonging to this set
   public void resetRoles() {
      for(int i = 0; i < roles.length; i++) {
         roles[i].setActorNumber(-1);
      }
   }
   
   // Resets the shot counter for this set
   public void resetShots() {
      while(shotsLeft != shotTotal)
         shotsLeft++;
   }
   
   // Returns all the roles from this set as well as this set's scene card
   private Role[] getAllRoles() {
      Role[] e = roles;
      Role[] s = scene.getRoles();
      Role[] r = new Role[e.length+s.length];
      int i = 0;
      while(i < e.length) {
         r[i] = e[i];
         i++;
      }
      while(i < r.length) {
         r[i] = s[i-e.length];
         i++;
      }
      return r;
   }
   
   public String[] getRoleNames() {
      Role[] roles = getAllRoles();
      String[] roleNames = new String[roles.length];
      for(int i = 0; i < roles.length; i++) {
         roleNames[i] = roles[i].getName();
      }
      return roleNames;
   }
   
   public int[] getRoleRanks() {
      Role[] roles = getAllRoles();
      int[] roleRanks = new int[roles.length];
      for(int i = 0; i < roles.length; i++) {
         roleRanks[i] = roles[i].getRank();
      }
      return roleRanks;
   }
   
   public boolean makeRoleSelection(int p, String n) {   
      Role[] roles = getAllRoles();
      for(int i = 0 ; i < roles.length; i++) {
         if(roles[i].getName().equals(n)) {   
            roles[i].setActorNumber(p);
            return true;
         }
      }
      return false;
   }
   
   public boolean[] getIfLeadRole() {
      Role[] roles = getAllRoles();
      boolean[] ifLeadRole = new boolean[roles.length];
      for(int i = 0; i < roles.length; i++) {
         if(roles[i].getIsStarring())
           ifLeadRole[i] = true;
      }
      
      return ifLeadRole;
   }
   
   public int[][] getRoleAreas() {
      Role[] roles = getAllRoles();
      int[][] roleAreas = new int[roles.length][4];
      for(int i = 0; i < roles.length; i++) {
         roleAreas[i] = roles[i].getArea();
      }
      
      return roleAreas;
   }
   
   // Returns an array containing all roles not currently filled that exist on this set, including
   //    this sets scene card
   public boolean areAvailableRoles() {
      if(isWrapped) {
         return false;
      }
      Role[] roles = getAllRoles();
      for(int i = 0; i < roles.length; i++) {
         if(roles[i].getActorNumber() == -1) {
            return true;
         }
      }
      return false;
   }
   
   public boolean[] getAvailableRoles(int r) {
      if(isWrapped) {
         return null;
      }
      Role[] roles = getAllRoles();
      boolean[] avail = new boolean[roles.length];
      for(int i = 0; i < roles.length; i++) {
         if(roles[i].getActorNumber() == -1 && r >= roles[i].getRank()) {
            avail[i] = true;
         }
      }
      
      return avail;
   }
   
   // Removes a shot counter and checks to see if the scene is wrapped
   public int acceptShot() {
      this.shotsLeft--;
      if(shotsLeft < 1) {
         isWrapped = true;
         //Deadwood.game.wrapScene(this);
         
      }
      return shotsLeft;
   }
}