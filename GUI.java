import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;

public class GUI extends JFrame {
   // JLabels
   JLabel boardlabel;
   JLabel[] roomLabels = new JLabel[12];
   JLabel[] movementLabels = new JLabel[12];
   JLabel[] playerLabels;
   JLabel[] roleLabels = new JLabel[7];
   JLabel[][] shotCounters = new JLabel[10][];
   JLabel mLabel;
   
   //JButtons
   JButton bAct;
   JButton bRehearse;
   JButton bMove;
   JButton bTake;
   JButton bUpgrade;
   JButton bEnd;
   JButton bCancel;
   
   JButton R2C;
   JButton R2D;
   JButton R3C;
   JButton R3D;
   JButton R4C;
   JButton R4D;
   JButton R5C;
   JButton R5D;
   JButton R6C;
   JButton R6D;
   
   // JLayered Pane
   JLayeredPane bPane;
   
   //Panels
   JPanel actionPane;
   JPanel scorePane;
   JPanel upgradeOptions;
   JPanel upgradePane;
   
   //Game Output
   JTextArea systemOutput;

   String playerSelection = null;
   boolean[] isFlipped = new boolean[10];
   
   // Constructor
   public GUI() {
   
      // Set the title of the JFrame
      super("Deadwood");
      
      // Set the exit option for the JFrame
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      // Create the deadwood board
      boardlabel = new JLabel();
      ImageIcon icon =  new ImageIcon("images/board.jpg");
      boardlabel.setIcon(icon);
      boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
      
      // Create the JLayeredPane to hold the display, cards, dice and buttons
      bPane = getLayeredPane();
   
      // Add the board to the lowest layer
      bPane.add(boardlabel, new Integer(0));
      
      // Set the size of the GUI
      setSize(icon.getIconWidth()+650,icon.getIconHeight());
      
      actionPane = new JPanel(new GridLayout(4,2,5,5));
      actionPane.setBounds(icon.getIconWidth()+20,40,240,200);
      
      upgradeOptions = new JPanel(new GridLayout(5,2,5,5));
      upgradePane = new JPanel();
      upgradePane.add(new JLabel("               Dollars | Credits"));
      upgradePane.add(upgradeOptions);
      upgradePane.setBounds(icon.getIconWidth() + 260, 40, 175, 200);

      
      // Create the Menu for action buttons
      mLabel = new JLabel("Action Menu");
      mLabel.setBounds(icon.getIconWidth()+90,10,100,20);
      bPane.add(mLabel, new Integer(2));
      
      for(int i = 0; i < 12; i++) {
         roomLabels[i] = new JLabel();
         movementLabels[i] = new JLabel();
         roomLabels[i].addMouseListener(new boardMouseListener());
         movementLabels[i].addMouseListener(new boardMouseListener());
         bPane.add(roomLabels[i], new Integer(1));
         bPane.add(movementLabels[i], new Integer(4));
      }
      hideMovementLabels();
      
      for(int i = 0; i < 7; i++) {
         roleLabels[i] = new JLabel();
         roleLabels[i].addMouseListener(new boardMouseListener());
         bPane.add(roleLabels[i], new Integer(2));
      }
      
      // Create Action buttons
      bAct = new JButton("ACT");
      bAct.setBackground(Color.white);
      bAct.addMouseListener(new boardMouseListener());
      bRehearse = new JButton("REHEARSE");
      bRehearse.setBackground(Color.white);
      bRehearse.addMouseListener(new boardMouseListener());
      bMove = new JButton("MOVE");
      bMove.setBackground(Color.white);
      bMove.addMouseListener(new boardMouseListener());
      bTake = new JButton("TAKE ROLE");
      bTake.setBackground(Color.white);
      bTake.addMouseListener(new boardMouseListener());
      bUpgrade = new JButton("UPGRADE");
      bUpgrade.setBackground(Color.white);
      bUpgrade.addMouseListener(new boardMouseListener());
      bEnd = new JButton("END TURN");
      bEnd.setBackground(Color.white);
      bEnd.addMouseListener(new boardMouseListener());
      
      bCancel = new JButton("CANCEL");
      bCancel.setBackground(Color.white);
      bCancel.setEnabled(false);
      bCancel.addMouseListener(new boardMouseListener());
      bCancel.setBounds(icon.getIconWidth()+80, 195, 120, 50);
      bPane.add(bCancel, new Integer(2));
      
      // Create the buttons for the upgrade menu
      R2D = new JButton("4");
      R2D.addMouseListener(new boardMouseListener());
      R3D = new JButton("10");
      R3D.addMouseListener(new boardMouseListener());
      R4D = new JButton("18");
      R4D.addMouseListener(new boardMouseListener());
      R5D = new JButton("28");
      R5D.addMouseListener(new boardMouseListener());
      R6D = new JButton("40");
      R6D.addMouseListener(new boardMouseListener());
      R2C = new JButton("5");
      R2C.addMouseListener(new boardMouseListener());
      R3C = new JButton("10");
      R3C.addMouseListener(new boardMouseListener());
      R4C = new JButton("15");
      R4C.addMouseListener(new boardMouseListener());
      R5C = new JButton("20");
      R5C.addMouseListener(new boardMouseListener());
      R6C = new JButton("25");
      R6C.addMouseListener(new boardMouseListener());
      upgradeOptions.add(new JLabel("Rank 2:"));
      upgradeOptions.add(R2D);
      upgradeOptions.add(R2C);
      upgradeOptions.add(new JLabel("Rank 3:"));
      upgradeOptions.add(R3D);
      upgradeOptions.add(R3C);
      upgradeOptions.add(new JLabel("Rank 4:"));
      upgradeOptions.add(R4D);
      upgradeOptions.add(R4C);
      upgradeOptions.add(new JLabel("Rank 5:"));
      upgradeOptions.add(R5D);
      upgradeOptions.add(R5C);
      upgradeOptions.add(new JLabel("Rank 6:"));
      upgradeOptions.add(R6D);
      upgradeOptions.add(R6C);
      upgradePane.setVisible(false);
      bPane.add(upgradePane);
      // Place the action buttons in the top layer
      actionPane.add(bMove);
      actionPane.add(bAct);
      actionPane.add(bRehearse);
      actionPane.add(bTake);
      actionPane.add(bUpgrade);
      actionPane.add(bEnd);
      bPane.add(actionPane, new Integer(2));
      
      scorePane = new JPanel(new GridLayout(0,7,5,5));
      scorePane.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
      scorePane.setBounds(1200, 250, 650, 300);
      
      JLabel label = new JLabel("Player", SwingConstants.CENTER);
      label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      scorePane.add(label);
      
      // Add labels to the score pane
      label = new JLabel("Icon", SwingConstants.CENTER);
      label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      scorePane.add(label);
      label = new JLabel("Rank", SwingConstants.CENTER);
      label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      scorePane.add(label);
      label = new JLabel("Dollars", SwingConstants.CENTER);
      label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      scorePane.add(label);
      label = new JLabel("Credits", SwingConstants.CENTER);
      label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      scorePane.add(label);
      label = new JLabel("Rehearsals", SwingConstants.CENTER);
      label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      scorePane.add(label);
      label = new JLabel("Score", SwingConstants.CENTER);
      label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      scorePane.add(label);
      
      bPane.add(scorePane, new Integer(2));
      
      systemOutput = new JTextArea();
      systemOutput.setBounds(icon.getIconWidth()+10, 575, 600, 300);
      systemOutput.setEditable(false);
      systemOutput.setLineWrap(true);
      systemOutput.setWrapStyleWord(true);
      
      // credit Mikle Garin/Duncan Jones - stackoverflow, 
      JScrollPane scroll = new JScrollPane();
      scroll.setBounds(icon.getIconWidth()+10, 575, 600, 300);
      scroll.getViewport().setView(systemOutput);
      scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      DefaultCaret caret = (DefaultCaret)systemOutput.getCaret();
      caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
      bPane.add(scroll, new Integer(2));
      
      
      setVisible(true);
   }
   
   // Show the allowed locations for a move command visually
   public void showAllowedLocations(String[] locations){ 
      int locationInts[] = new int[locations.length];
      for(int i = 0; i < movementLabels.length; i++) {
         Color c = new Color(250,50,75, 50);
         if(determineLocationIndex(locations[0]) == i){
            c = new Color(50, 75, 250, 50);
         }
         for(int j = 1; j < locations.length; j++) {
            if(determineLocationIndex(locations[j]) == i) {
               c = new Color(50, 255, 75, 50);
            }
         }
         movementLabels[i].setBackground(c);
         movementLabels[i].setOpaque(true);
         movementLabels[i].setVisible(true);
      }
   }
   
   // Hides the labels used for a move command
   public void hideMovementLabels() {
      for(int i = 0; i < movementLabels.length; i++) {
         movementLabels[i].setVisible(false);
      }
   }

   // Shows the upgrade menu
   public void displayUpgradeButtons(boolean[] isAvailable) {
      R2D.setEnabled(isAvailable[0]);
      R2C.setEnabled(isAvailable[1]);
      R3D.setEnabled(isAvailable[2]);
      R3C.setEnabled(isAvailable[3]);
      R4D.setEnabled(isAvailable[4]);
      R4C.setEnabled(isAvailable[5]);
      R5D.setEnabled(isAvailable[6]);
      R5C.setEnabled(isAvailable[7]);
      R6D.setEnabled(isAvailable[8]);
      R6C.setEnabled(isAvailable[9]);
      upgradePane.setVisible(true);
   }
   
   // Hides the upgrade menu
   public void hideUpgradeButtons() { 
      upgradePane.setVisible(false);
   }
   
   public String getPlayerCount() {
       return JOptionPane.showInputDialog(this, "How many players?");
   }
   
   public String getPlayerName(int num) {
      String name = JOptionPane.showInputDialog(this, "What is Player " + num + "'s name?");
      return name;
   }
   
   public void alertNotANumber() {
      JOptionPane.showMessageDialog(this, "ERROR: That is not a valid number of players, please insert a number between " +
                                          "2 and 8 inclusive with no spaces.");
   }
   
   // Adds the shot counter labels to the GUI
   public void setUpShotCounter(int[][] areas, int location) {
      shotCounters[location] = new JLabel[areas.length];
      for(int i = 0; i < areas.length; i++) {
         shotCounters[location][i] = new JLabel();
         shotCounters[location][i].setBounds(areas[i][0],areas[i][1],areas[i][2],areas[i][3]);
         shotCounters[location][i].setIcon(new ImageIcon("images/shot.png"));
         shotCounters[location][i].setVisible(true);
         bPane.add(shotCounters[location][i], new Integer(2));
      }
   }
   
   // Removes a shot counter from the provided location
   public void takeShot(String location, int num) {
      int lIndex = determineLocationIndex(location);
      shotCounters[lIndex][num].setVisible(false);
      bPane.validate();
   }
   
   // Resets the cards for each set
   public void resetCard(int[] area, int index) {
      if(index<10) {
         ImageIcon cardBack = new ImageIcon("images/CardBack.jpg");
         ImageIcon rescaledCardBack = new ImageIcon(cardBack.getImage().getScaledInstance(area[2], area[3], Image.SCALE_DEFAULT));
         roomLabels[index].setIcon(rescaledCardBack);
         roomLabels[index].setBounds(area[0],area[1],area[2],area[3]);
         movementLabels[index].setBounds(area[0],area[1],area[2],area[3]);
         isFlipped[index] = false;
      } else {
         roomLabels[index].setBounds(area[0],area[1],area[2],area[3]);
         movementLabels[index].setBounds(area[0],area[1],area[2],area[3]);
      }

      bPane.validate();
   }
   
   // adds the card front to the GUI
   public void setCard(int[] area, String imgLink, int index) {
      ImageIcon scene = new ImageIcon("images/cards/cards/" + imgLink);
      roomLabels[index].setIcon(scene);
      roomLabels[index].setBounds(area[0],area[1],area[2],area[3]);
      bPane.validate();
   }
   
   // Moves player icons to trailer
   public void setPlayersToStart(int p) {
      for(int i = 0; i < p; i++) {
         playerLabels[i].setBounds(roomLabels[10].getX()+(20*i),roomLabels[10].getY()+roomLabels[10].getHeight()-46,46,46);
      }
   }
   
   // Returns the image link for the player
   private String determinePlayerColor(int n) {
      switch(n) {
         case 0: return "images/dice/dice/b";
         case 1: return "images/dice/dice/c";
         case 2: return "images/dice/dice/g";
         case 3: return "images/dice/dice/o";
         case 4: return "images/dice/dice/p";
         case 5: return "images/dice/dice/r";
         case 6: return "images/dice/dice/v";
         case 7: return "images/dice/dice/y";
      }
      return null;
   }
   
   // Translates string into int representation of location
   private int determineLocationIndex(String l) {
      switch(l) {
         case "Train Station": return 0;
         case "Secret Hideout": return 1;
         case "Church": return 2;
         case "Hotel": return 3;
         case "Main Street": return 4;
         case "Jail": return 5;
         case "General Store": return 6;
         case "Ranch": return 7;
         case "Bank": return 8;
         case "Saloon": return 9;
         case "trailer": return 10;
         case "office": return 11;
      }
      return -1;
   }
   
   // Creates the scoreboard for the GUI
   public void createScoreboard(String[] n) {
      playerLabels = new JLabel[n.length];
      for(int i = 0; i < n.length; i++) {
         playerLabels[i] = new JLabel("", SwingConstants.CENTER);
         bPane.add(playerLabels[i], new Integer(5));
      }
      int creditStart = 0;
      int rankStart = 1;
      if(n.length == 5) {
         creditStart = 2;
      } else if(n.length == 6) {
         creditStart = 4;
      } else if(n.length >=7) {
         rankStart = 2;
      }
      for(int i = 0; i < n.length; i++) {
         scorePane.add(new JLabel(n[i], SwingConstants.CENTER));
         JLabel imgLink = new JLabel("", SwingConstants.CENTER);
         ImageIcon playerIcon;
         if(n.length==8)
            playerIcon = new ImageIcon(determinePlayerColor(i)+"2.png");
         else
            playerIcon = new ImageIcon(determinePlayerColor(i)+"1.png");
         imgLink.setIcon(playerIcon);
         scorePane.add(imgLink);
         playerLabels[i].setIcon(playerIcon);
         scorePane.add(new JLabel(""+rankStart+"", SwingConstants.CENTER));
         scorePane.add(new JLabel("0", SwingConstants.CENTER));
         scorePane.add(new JLabel(""+creditStart+"", SwingConstants.CENTER));
         scorePane.add(new JLabel("0", SwingConstants.CENTER));
         scorePane.add(new JLabel(""+(creditStart+(rankStart*5))+"", SwingConstants.CENTER));
      }
      bPane.validate();
   }
   
   // Updates the scoreboard on the GUI
   public void updateScoreboard(int player, int number, String decider) {
      if(decider.equals("rank")) {
         scorePane.remove(7*(player+1)+2);
         scorePane.add(new JLabel(""+number+"", SwingConstants.CENTER), 7*(player+1)+2);
         scorePane.remove(7*(player+1)+1);
         JLabel imgLink = new JLabel("", SwingConstants.CENTER);
         imgLink.setIcon(new ImageIcon(determinePlayerColor(player)+""+number+".png"));
         playerLabels[player].setIcon(new ImageIcon(determinePlayerColor(player)+""+number+".png"));
         scorePane.add(imgLink, 7*(player+1)+1);
      } else if(decider.equals("dollars")) {
         scorePane.remove(7*(player+1)+3);
         scorePane.add(new JLabel(""+number+"", SwingConstants.CENTER), 7*(player+1)+3);
      } else if(decider.equals("credits")) {
         scorePane.remove(7*(player+1)+4);
         scorePane.add(new JLabel(""+number+"", SwingConstants.CENTER), 7*(player+1)+4);
      } else if(decider.equals("rehearse")) {
         scorePane.remove(7*(player+1)+5);
         scorePane.add(new JLabel(""+number+"", SwingConstants.CENTER), 7*(player+1)+5);
      }else if(decider.equals("score")) {
         scorePane.remove(7*(player+1)+6);
         scorePane.add(new JLabel(""+number+"", SwingConstants.CENTER), 7*(player+1)+6);
      }
      bPane.validate();
   }
   
   // Shows the proper buttons for the current players turn
   public void showButtons(boolean move,boolean act,boolean rehearse,boolean upgrade,boolean take,boolean end) {
      bMove.setEnabled(move);
      bAct.setEnabled(act);
      bRehearse.setEnabled(rehearse);
      bUpgrade.setEnabled(upgrade);
      bTake.setEnabled(take);
      bEnd.setEnabled(end);
      bCancel.setEnabled(!end);
      bPane.validate();
   }
   
   public void sendMessage(String message) {
      JOptionPane.showMessageDialog(this, message);
   }
   
   public void updateConsole(String message) {
      systemOutput.append(message);
   }
   
   // Gets the user input using the GUI
   public String getPlayerSelection() {
      playerSelection = null;
      try {
         synchronized(Deadwood.controller) {
            while(playerSelection==null) {
               Deadwood.controller.wait();
            }
            return playerSelection;
         }
      } catch(Exception e) {
      }
      return null;
   }
   
   public void setPlayerSelection(String s) {
      playerSelection = null;
   }
   
   // updates the player location and flips a scene card after a move command
   public void updateMoveInfo(int player, String location, String imageLink) {
      int locationIndex = determineLocationIndex(location);
      playerLabels[player].setBounds(roomLabels[locationIndex].getX() + player*20, roomLabels[locationIndex].getY()+roomLabels[0].getHeight(),46,46);
      if(locationIndex <10 && !isFlipped[locationIndex]) {
         roomLabels[locationIndex].setIcon(new ImageIcon("images/cards/cards/"+imageLink));
         isFlipped[locationIndex] = true;
      }
   }
   
   // Visually wraps a given scene
   public void wrapScene(String setName, int[] players) {
      int locationIndex = determineLocationIndex(setName);
      roomLabels[locationIndex].setIcon(null);
      for(int i = 0; i<players.length; i++) {
         int player = players[i];
         playerLabels[player].setBounds(roomLabels[locationIndex].getX()+(20*player), roomLabels[locationIndex].getY()+roomLabels[0].getHeight(),46,46);
         updateScoreboard(players[i], 0, "rehearse");
      }
   }

   // Displays the visual element of the role selection process
   public void displayRoles(String setName, boolean[] isAvailable, int[][] roleAreas, boolean[] leadRole) {
      for(int i = 0; i < roleAreas.length; i++) {
         if(leadRole[i]) {
            int locationIndex = determineLocationIndex(setName);
            roleLabels[i].setBounds(roleAreas[i][0]+roomLabels[locationIndex].getX(),roleAreas[i][1]+roomLabels[locationIndex].getY(), roleAreas[i][2],roleAreas[i][3]);
         }
         else
            roleLabels[i].setBounds(roleAreas[i][0],roleAreas[i][1],roleAreas[i][2],roleAreas[i][3]);
         if(isAvailable[i]) {
            roleLabels[i].setBackground(new Color(50,250,75, 50));
         }
         else {
            roleLabels[i].setBackground(new Color(250,50,75, 50));
         }
         roleLabels[i].setOpaque(true);
      }
      bPane.validate();
   }
   
   // Moves the player icon to the selected role
   public void updateRoleInfo(int player, String setName, int[] roleArea, boolean isLeadRole) {
      if(isLeadRole) {
         int locationIndex = determineLocationIndex(setName);
         playerLabels[player].setBounds(roleArea[0]+roomLabels[locationIndex].getX() - 2, roleArea[1]+roomLabels[locationIndex].getY() -2, 46, 46);
      }
      else
         playerLabels[player].setBounds(roleArea[0] + 1, roleArea[1], 46, 46); 
   }
   
   // Hides the colorful labels used during role selection
   public void removeRoleLabels() {
      for(int i = 0; i < roleLabels.length; i++) {
         roleLabels[i].setOpaque(false);
         roleLabels[i].setBounds(0,0,0,0);
      }
   }
   
   // This class implements Mouse Events
   class boardMouseListener implements MouseListener{
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         synchronized(Deadwood.controller) {
            if (e.getSource()== bAct && bAct.isEnabled()){
               playerSelection = "a";
            }
            else if (e.getSource()== bRehearse && bRehearse.isEnabled()){
               playerSelection = "r";
            }
            else if (e.getSource()== bMove && bMove.isEnabled()){
               playerSelection = "m";
            }
            else if (e.getSource()== bEnd && bEnd.isEnabled()) {
               playerSelection = "e";
            }
            else if (e.getSource()== bUpgrade && bUpgrade.isEnabled()) {
               playerSelection = "u";
            }
            else if (e.getSource()== bTake && bTake.isEnabled()) {
               playerSelection = "t";
            }
            else if (e.getSource()== bCancel && bCancel.isEnabled()) {
               playerSelection = "c";
            }
            else if (e.getSource()== R2D && R2D.isEnabled()) {
               playerSelection = "u2d";
            }
            else if (e.getSource()== R2C && R2C.isEnabled()) {
               playerSelection = "u2c";
            }
            else if (e.getSource()== R3D && R3D.isEnabled()) {
               playerSelection = "u3d";
            }
            else if (e.getSource()== R3C && R3C.isEnabled()) {
               playerSelection = "u3c";
            }
            else if (e.getSource()== R4D && R4D.isEnabled()) {
               playerSelection = "u4d";
            }
            else if (e.getSource()== R4C && R4C.isEnabled()) {
               playerSelection = "u4c";
            }
            else if (e.getSource()== R5D && R5D.isEnabled()) {
               playerSelection = "u5d";
            }
            else if (e.getSource()== R5C && R5C.isEnabled()) {
               playerSelection = "u5c";
            }
            else if (e.getSource()== R6D && R6D.isEnabled()) {
               playerSelection = "u6d";
            }
            else if (e.getSource()== R6C && R6C.isEnabled()) {
               playerSelection = "u6c";
            }
            for(int i = 0; i < 7; i++) {
               if(e.getSource() == roleLabels[i]) {
                  playerSelection = "r"+i+"";
               }
            }
            for(int i = 0; i < 12; i++) {
               if(e.getSource() == movementLabels[i]) {
                  playerSelection = ""+i+"";
               }
            }
            Deadwood.controller.notify();
         }
      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
   }  
}