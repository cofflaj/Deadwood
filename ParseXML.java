import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.lang.Integer;

/*
   ParseXML class
   Responsibilities:
      - Read the board and card data from the board.xml and cards.xml files
      - Use the data to set the locations/sets and scenes in the given map object

*/
public class ParseXML {


   //sets up the Document object
   public Document getDocFromFile(String filename) 
   throws ParserConfigurationException{
   {
   
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = null;
      
      try{
         doc = db.parse(filename);
      } catch (Exception ex){
         System.out.println("XML parse failure");
         ex.printStackTrace();
      }
      return doc;
   }
       
   }
   
   
   //readBoardData method that takes a document, parses the applicable data,
   // and then creates and sets it into the map in the form of a location/set
   public void readBoardData(Document d, Map m) {
      
      //get the head of the document
      Element root = d.getDocumentElement();
      
      
      //get all of the set objects
      NodeList sets = root.getElementsByTagName("set");
      
      //for loop to go through all of the sets in the document
      for (int i=0; i<sets.getLength(); i++) {
      
         //get the set and its name
         Node set = sets.item(i);
         String setName = set.getAttributes().getNamedItem("name").getNodeValue();
         
         //set up all of the set attributes to be used later
         NodeList setAttributes = set.getChildNodes();
         String[] neighbors = null;
         Area area = null;
         Area[] shotAreas = null;
         Role[] roles = null;
         
         
         //for loop to go through all of the sets attributes
         for(int j=0; j<setAttributes.getLength(); j++) {
         
            //get the attribute
            Node sub = setAttributes.item(j);
            
            
            //if the attribute is the set's neighbors, get the neighbors array
            if("neighbors".equals(sub.getNodeName())) {
               NodeList n = sub.getChildNodes();
               neighbors = new String[n.getLength()/2];
               int index = 0;
               
               //for loop to get each neighbor
               for(int k=1; k<n.getLength(); k+=2) {
                  if("neighbor".equals(n.item(k).getNodeName())) {
                     neighbors[index] = n.item(k).getAttributes().getNamedItem("name").getNodeValue();
                     index++;
                  }
               }
            }
            
            //else if the attribute is the set's area, get the area
            else if("area".equals(sub.getNodeName())) {
               int x = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
               int y = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
               int w = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
               int h = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
               area = new Area(x,y,w,h);
            }
            
            //else if the attribute is the set's takes (shots), get the total and the area of each
            else if("takes".equals(sub.getNodeName())) {
               NodeList n = sub.getChildNodes();
               int size = Integer.parseInt(n.item(1).getAttributes().getNamedItem("number").getNodeValue());
               shotAreas = new Area[size];
               for(int k=1; k<n.getLength(); k+=2) {
                  if("take".equals(n.item(k).getNodeName())) {
                     size--;
                     int x = Integer.parseInt(n.item(k).getChildNodes().item(0).getAttributes().getNamedItem("x").getNodeValue());
                     int y = Integer.parseInt(n.item(k).getChildNodes().item(0).getAttributes().getNamedItem("y").getNodeValue());
                     int w = Integer.parseInt(n.item(k).getChildNodes().item(0).getAttributes().getNamedItem("w").getNodeValue());
                     int h = Integer.parseInt(n.item(k).getChildNodes().item(0).getAttributes().getNamedItem("h").getNodeValue());
                     shotAreas[size] = new Area(x,y,w,h);
                  }
               }
            }
            
            //else if the attribute is the set's parts (roles), get the roles array and their areas
            else if("parts".equals(sub.getNodeName())) {
               NodeList n = sub.getChildNodes();
               
               //every other object in a nodes children is just a newline character,
               // so for efficiency, step by 2 and start at 1
               roles = new Role[n.getLength()/2];
               int index = 0;
               for(int k=1; k<n.getLength(); k+=2) {
                  Node n2 = n.item(k);
                  NodeList n3 = n2.getChildNodes();
                  int x = Integer.parseInt(n3.item(1).getAttributes().getNamedItem("x").getNodeValue());
                  int y = Integer.parseInt(n3.item(1).getAttributes().getNamedItem("y").getNodeValue());
                  int w = Integer.parseInt(n3.item(1).getAttributes().getNamedItem("w").getNodeValue());
                  int h = Integer.parseInt(n3.item(1).getAttributes().getNamedItem("h").getNodeValue());
                  Area a = new Area(x,y,w,h);
                  roles[index] = new Role(n2.getAttributes().getNamedItem("name").getNodeValue(),
                                      n3.item(3).getFirstChild().getNodeValue(),
                                      Integer.parseInt(n2.getAttributes().getNamedItem("level").getNodeValue()),
                                      false,
                                      a);
                  index++;
               }
            }
         }
         
         //use the data above to create a new set and then set its location in the map object given
         m.setLocation(new Set(setName, neighbors, null, roles, area, shotAreas));
      }
      
      //create the trailer location
      NodeList trailer = root.getElementsByTagName("trailer");
      
      //get the trailer's attributes
      NodeList trailerAtt = trailer.item(0).getChildNodes();
      
      
      //get the neighbors for the trailer
      NodeList nT = trailerAtt.item(1).getChildNodes();
      String[] neighborsT = new String[3];
      int index = 0;
      for(int k=1; k<nT.getLength(); k+=2) {
         if("neighbor".equals(nT.item(k).getNodeName())) {
            neighborsT[index] = nT.item(k).getAttributes().getNamedItem("name").getNodeValue();
            index++;
         }
      }
      
      //get the area of the trailer
      Node aT = trailerAtt.item(3);
      int xT = Integer.parseInt(aT.getAttributes().getNamedItem("x").getNodeValue());
      int yT = Integer.parseInt(aT.getAttributes().getNamedItem("y").getNodeValue());
      int wT = Integer.parseInt(aT.getAttributes().getNamedItem("w").getNodeValue());
      int hT = Integer.parseInt(aT.getAttributes().getNamedItem("h").getNodeValue());
      Area areaT = new Area(xT,yT,wT,hT);
      
      //set the trailer's location in the map object given
      m.setLocation(new Location("trailer", neighborsT, areaT));
      
      
      
      
      //create the office location (upgrade room)
      NodeList office = root.getElementsByTagName("office");
      
      //get the office's attributes
      NodeList officeAtt = office.item(0).getChildNodes();
      
      
      //get the office's neighbors
      NodeList nO = officeAtt.item(1).getChildNodes();
      String[] neighborsO = new String[3];
      index = 0;
      for(int k=1; k<nO.getLength(); k+=2) {
         if("neighbor".equals(nO.item(k).getNodeName())) {
            neighborsO[index] = nO.item(k).getAttributes().getNamedItem("name").getNodeValue();
            index++;
         }
      }
      
      //get the office's area
      Node aO = officeAtt.item(3);
      int xO = Integer.parseInt(aO.getAttributes().getNamedItem("x").getNodeValue());
      int yO = Integer.parseInt(aO.getAttributes().getNamedItem("y").getNodeValue());
      int wO = Integer.parseInt(aO.getAttributes().getNamedItem("w").getNodeValue());
      int hO = Integer.parseInt(aO.getAttributes().getNamedItem("h").getNodeValue());
      Area areaO = new Area(xO,yO,wO,hO);
      
      //set the office's location in the map object given
      m.setLocation(new Location("office", neighborsO, areaO));
      


   }
   
   
   //readCardData method that takes a document, parses the applicable data,
   // and then sets the scene array in the given map to the scenes in the document
   public void readCardData(Document d, Map m) {
      
      //initialize the scene array
      Scene[] s = new Scene[40];
      
      //get each scene object
      Element root = d.getDocumentElement();
      NodeList cards = root.getElementsByTagName("card");
      
      
      //for loop to get the data for each scene
      for (int i=0; i<cards.getLength(); i++) {
      
         //get the individual scene and the simple attributes
         Node scene = cards.item(i);
         String sceneName = scene.getAttributes().getNamedItem("name").getNodeValue();
         int budget = Integer.parseInt(scene.getAttributes().getNamedItem("budget").getNodeValue());
         String imgLink = scene.getAttributes().getNamedItem("img").getNodeValue();
         int index = Integer.parseInt(imgLink.substring(0,2));
         
         //get the rest of the scene's attributes
         NodeList sceneAtt = scene.getChildNodes();
         
         String flavor = sceneAtt.item(1).getFirstChild().getNodeValue();
         
         
         //get the roles for the scene
         Role[] roles = new Role[sceneAtt.getLength()/2-1];
         int roleIndex = 0;
         
         //get each role with its appropriate name, level, and area
         for(int j=3; j<sceneAtt.getLength(); j+=2) {
            Node role = sceneAtt.item(j);
            NodeList roleAtt = role.getChildNodes();
            int x = Integer.parseInt(roleAtt.item(1).getAttributes().getNamedItem("x").getNodeValue());
            int y = Integer.parseInt(roleAtt.item(1).getAttributes().getNamedItem("y").getNodeValue());
            int w = Integer.parseInt(roleAtt.item(1).getAttributes().getNamedItem("w").getNodeValue());
            int h = Integer.parseInt(roleAtt.item(1).getAttributes().getNamedItem("h").getNodeValue());
            Area a = new Area(x,y,w,h);
            roles[roleIndex] = new Role(role.getAttributes().getNamedItem("name").getNodeValue(),
                                roleAtt.item(3).getFirstChild().getNodeValue(),
                                Integer.parseInt(role.getAttributes().getNamedItem("level").getNodeValue()),
                                true,
                                a);
            roleIndex++;
         }
         
         //set the scene in the array
         s[index-1] = new Scene(sceneName, imgLink, flavor, budget, roles);

      
      }
      
      //set the map's scene array as the scene array created in this method
      m.setScenes(s);
   
   }
}