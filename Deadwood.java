public class Deadwood {

    static Game game;
    static View view;
    static Controller controller;

    public static void main(String[] args) {
        
        view = new View();
        controller = new Controller();
        String playerCount = view.getPlayerCount();
        if(playerCount == null) {
           System.exit(0);
        }
        while(!(playerCount.equals("2") || playerCount.equals("3") || playerCount.equals("4") || playerCount.equals("5") || 
              playerCount.equals("6") || playerCount.equals("7") || playerCount.equals("8"))) {
              controller.alertNotANumber();
              playerCount = view.getPlayerCount();  
        }
        game = new Game(playerCount);
        controller.openMainMenu();
    }

}

/*
    DEADWOOD MVC BASIC FLOW v 0.1

    1. The Model needs input from the user
    2. The Model querries the Controller for input
    3. The Controller tells the View to print the desired prompt
    4. The user reads the prompt and types one of the allowed responses
    5. The Controller verifies that the request is valid, then passes it to the Model
    6. The Model runs the function as requested by the users
    7. The Model needs input from the user

    Example: Starting The Game

        1. The Model needs input from the user--------------------------------------------
            The game starts, and the Model decides it need to get input to add and remove
            players as well as start the game

        2. The Model querries the Controller for input------------------------------------
            The Controller's openMainMenu method is called

        3. The Controller tells the View to print the desired prompt----------------------
            The Controller calls the View's showMainMenu method

        4. The user reads the prompt and types one of the allowed responses---------------
            At this point we are still in the Controller's openMainMenu method. The View
            has printed the user's options, and the Controller has invoked a getInput
            method

        5. The Controller verifies that the request is valid, then passes it to the Model-
            Still within the openMainMenu method, the Controller makes sure that the input
            is valid. If it is not valid, the Controller calls the View to reprint the
            users options and gets another input from the user.

        6. The Model runs the function as requested by the users--------------------------
            If the user added or removed a player, the same menu is displayed. If the user
            selected 'Start Game', the openMainMenu method ends with a call to
            Deadwood.game.startGame()

        7. The Model needs input from the user--------------------------------------------
            Rinse, repeat


    Example 2: Taking a Turn

        1. The Model needs input from the user--------------------------------------------
            The Model has recognized that a player's turn has started, and needs input
            from the user

        2. The Model querries the Controller for input------------------------------------
            The Model calls the Controller's openTurnMenu method, passing as arguments the
            current players legal moves

        3. The Controller tells the View to print the desired prompt----------------------
            The Controller calls the View's showTurnMenu method, again passing as
            arguments the players legal moves

        4. The user reads the prompt and types one of the allowed responses---------------
            The Controller calls a getInput function, the user responds using the prompt
            provided by View

        5. The Controller verifies that the request is valid, then passes it to the Model-
            The Controller uses a simple while loop to make sure the input is legal

        6. The Model runs the function as requested by the users--------------------------
            The Controller calls Deadwood.game.currentPlayer.<action>()

        7. The Model needs input from the user--------------------------------------------
            etc, etc

 */