public class Location {
   
   protected String name;
   protected String[] neighbors;
   protected Area area;

   public Location() {}
   
   public Location(String n, String[] ne, Area a) {
      name = n;
      neighbors = ne;
      area = a;
   }
   
   public String getName() {
      return name;
   }
   
   public String[] getNeighbors() {
      return neighbors;
   }
   
   public int[] getArea() {
      return area.getArea();
   }
   
   public String toString() {
      String sentence = name + " has neighbors ";
      for(int i = 0; i<neighbors.length; i++) {
         sentence += neighbors[i] + ", ";
      }
      
      return sentence + "and has " + area.toString();
   }


   
}