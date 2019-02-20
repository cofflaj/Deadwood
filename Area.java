public class Area {
   int x;
   int y;
   int width;
   int height;
      
   Area(int x, int y, int w, int h) {
      this.x = x;
      this.y = y;
      width = w;
      height = h;
   }
   
   public int[] getArea() {
      int[] a = {x, y, width, height};
      return a;
   }
}