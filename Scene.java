public class Scene {

   private String name;
   private String imgLink;
   private String flavorText;
   private int budget;
   private Role[] roles;

   
   public Scene() {}
   
   public Scene(String n, String i, String f, int b, Role[] r) {
      name = n;
      imgLink = i;
      flavorText = f;
      budget = b;
      roles = r;
   }
   
   // Gets the budget of the movie as an int representing millions of dollars
   public int getBudget() {
      return this.budget;
   }
   
   public String getImageLink() {
      return imgLink;
   }
   
   // Returns the roles from this scene card. Roles on a scene card are always Lead Roles.
   public Role[] getRoles() {
      return this.roles;
   }

}