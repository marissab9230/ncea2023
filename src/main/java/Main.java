import java.util.Scanner;

public class Main {
    static Scanner inputStream = new Scanner(System.in);
    static int minNumberOfRounds = 5;
    static int maxNumberOfRounds = 10;
    static int numberOfRounds;
    static int userScore;
    static int computerScore;
    static String[] playerOneMoves;
    static String[] playerTwoMoves;
    static String[] computerMoves;
    static String gameInstructions="game instructions"; //description of the game and instructions , to-do
    static int roundNumber;
    static String[] validMoves = {"c", "cooperate", "d", "defect"};
    static String[] yesNoResponse = {"y", "yes", "n", "no"};
    static String[] gameModes = {"m", "multiplayer", "c", "computer"};
    static String[] continueResponses = {"c", "continue"};
    static boolean needsYesOrNo=false;
    static String playerOneName;
    static String playerTwoName;
    static String gameMode;

    /*public enum GameMode {
        MULTIPLAYER,
        COMPUTER
    }
    public static GameMode gameMode;*/

    public static void main(String args[]){
        startGame();
    }

    public static void startGame(){ //this starts the game
        //resets relevant variables
        userScore = 0;
        computerScore = 0;
        numberOfRounds = (int) Math.floor(Math.random()*(maxNumberOfRounds - minNumberOfRounds + 1) + minNumberOfRounds);
        playerOneMoves = new String[numberOfRounds];
        playerTwoMoves = new String[numberOfRounds];
        computerMoves = new String[numberOfRounds];
        roundNumber=0;

        System.out.println(gameInstructions); //prints game instructions

        //setting game mode
        gameMode = getPromptedInput("would you like to play against the computer or multiplayer?", "startGame");
        while(gameMode==null) { //invalid input
            System.out.println("invalid input");
            gameMode = getPromptedInput("would you like to play against the computer or multiplayer?", "startGame");
        }

        //ask for players names when multiplayer
        if(gameMode.equals("m")){
            System.out.println("player one, what is your name?");
            playerOneName=inputStream.nextLine();
            System.out.println("player two, what is your name?");
            playerTwoName=inputStream.nextLine();
        }

        for(int i=0; i<numberOfRounds; i++) {
            round();
        }
        endGame();
    }

    public static void round(){ //this method runs each round
        clearTerminal();

        System.out.println("round " + (roundNumber+1));

        if (gameMode.equals("m")) {
            //ask player one for their decision
            playerOneMoves[roundNumber]= getPromptedInput((playerOneName+ ", do you cooperate or defect?"), "round"); //prompt user, take in and log users move
            while(playerOneMoves[roundNumber]==null){ //if input is invalid, ask again
                System.out.println("input invalid");
                playerOneMoves[roundNumber]= getPromptedInput((playerOneName+ ", do you cooperate or defect?"), "round");
            }

            clearTerminal();

            //ask player two for their decision
            playerTwoMoves[roundNumber]= getPromptedInput((playerTwoName+ ", do you cooperate or defect?"), "round"); //prompt user, take in and log users move
            while(playerTwoMoves[roundNumber]==null){ //if input is invalid, ask again
                System.out.println("input invalid");
                playerTwoMoves[roundNumber]= getPromptedInput((playerTwoName+ ", do you cooperate or defect?"), "round");
            }

            boolean playerOneCooperates = (playerOneMoves[roundNumber].equals("c"));
            boolean playerTwoCooperates = (playerTwoMoves[roundNumber].equals("c"));

            //determine outcome of round
            if(playerOneCooperates && playerTwoCooperates){
                System.out.println("you both cooperated");
            }if(playerOneCooperates && !playerTwoCooperates){
                System.out.println(playerOneName + " cooperated, " + playerTwoName + " defected.");
            }if(!playerOneCooperates && playerTwoCooperates){
                System.out.println(playerTwoName + " cooperated, " + playerOneName + " defected.");
            }if(!playerOneCooperates && !playerTwoCooperates){
                System.out.println("you both defected");
            }

        }else{
            playerOneMoves[roundNumber]= getPromptedInput("do you cooperate or defect", "round"); //prompt user, take in and log users move
            while(playerOneMoves[roundNumber]==null){ //if input is invalid, ask again
                System.out.println("input invalid");
                playerOneMoves[roundNumber]= getPromptedInput("do you cooperate or defect", "round");
            }

            computerDecisions();

            boolean playerCooperates = (playerOneMoves[roundNumber].equals("c"));
            boolean computerCooperates = (computerMoves[roundNumber].equals("c"));

            //determine outcome of round
            if(playerCooperates && computerCooperates){
                System.out.println("you both cooperated");
            }if(playerCooperates && !computerCooperates){
                System.out.println("they defected");
            }if(!playerCooperates && computerCooperates){
                System.out.println("they cooperated");
            }if(!playerCooperates && !computerCooperates){
                System.out.println("you both defected");
            }
        }

        waitToContinue();

        roundNumber++;
    }

    public static void settings(){ //print settings options and allow user to adjust language, input options
        //TO-DO: all settings code
    }

    public static void waitToContinue(){
        //pauses game until user says to continue

        System.out.println("'c' to continue");

        while(true){
            String input = inputStream.nextLine();
            input=input.toLowerCase();
            for (String continueRespons : continueResponses) {
                if (input.equals(continueRespons)) {
                    break;
                }
            }
        }
    }

    public static String getPromptedInput(String prompt, String methodCalledFrom){ //outputs text and returns the response
        //print prompt and take in input
        System.out.println(prompt);
        String input = inputStream.nextLine();
        input=input.toLowerCase();

        //check the user input against valid inputs

        //only runs if we want a yes or no
        if(needsYesOrNo){
            for(int i=0; i<yesNoResponse.length; i++){
                if(input.equals(yesNoResponse[i])){
                    //return "y" for yes or "n" for no regardless of the user input; this should make things easier
                    if(i % 2 == 1){
                        needsYesOrNo=false;
                        return yesNoResponse[i-1];
                    }
                    else{
                        needsYesOrNo=false;
                        return yesNoResponse[i];
                    }
                }
            }
        }

        //for users decisions
        if(methodCalledFrom.equals("round")) {
            for (int i = 0; i < validMoves.length; i++) {
                if (input.equals(validMoves[i])) {
                    //return "c" for cooperate or "d" for defect regardless of the user input; this should make things easier
                    if (i % 2 == 1) return validMoves[i - 1];
                    else return validMoves[i];
                }
            }
        }

        if(methodCalledFrom.equals("startGame")){
            for (int i = 0; i < gameModes.length; i++) {
                if (input.equals(gameModes[i])) {
                    //return "m" for multiplayer or "c" for computer regardless of the user input; this should make things easier
                    if (i % 2 == 1) return gameModes[i - 1];
                    else return gameModes[i];
                }
            }
        }

        //if at any point user wants to access settings/instructions
        if(input.equals("s")||input.equals("settings")) settings();

        return null;
    }

    public static void endGame(){
        //runs when a game ends. should: announce score, announce victor, announce overall score including past games, offer option to play again or quit
        if(userScore>computerScore) System.out.println("Congratulations, you win! Your score was " + userScore + ", I scored "+ computerScore+ "!");//could add more possible answers
        if(computerScore>userScore) System.out.println("You lose! Your score was " + userScore + ", I scored "+ computerScore+ "!");
        else System.out.println("It's a tie! We both scored "+ userScore);

        needsYesOrNo=true;
        String decision = getPromptedInput("Would you like to play again", "endGame");
        if(decision.equals("y")) startGame();
        while (decision==null){ //invalid inputs
            System.out.println("input invalid");
            needsYesOrNo=true;
            decision = getPromptedInput("Would you like to play again", "endGame");
        }
    }

    public static void computerDecisions(){
        //for now i will randomly generate computers decisions
        if(Math.random()<=0.5) computerMoves[roundNumber] = "c";
        else computerMoves[roundNumber] = "d";
    }

    public static void clearTerminal(){
        //clear terminal (doesn't work here)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}