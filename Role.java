public class Role {
   
   private String name;
   private String line;
   private int rank;
   private int actorNumber = -1;
   private boolean isStarring;
   private Area area;
   
   public Role() {
   
   }
   
   public Role(String n, String l, int r, boolean s, Area a) {
      name = n;
      line = l;
      rank = r;
      isStarring = s;
      area = a;
   }
   
   public int getActorNumber() {
      return actorNumber;
   }
   
   public int getRank() {
      return rank;
   }
   
   
   public boolean getIsStarring() {
      return isStarring;
   }
   
   public void setActorNumber(int n) {
      actorNumber = n;
   }
   
   public String getName(){
      return name;
   }
   
   public int[] getArea() {
      return area.getArea();
   }
}