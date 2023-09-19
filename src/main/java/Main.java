/**
    Name: Marissa Burnette

    Date: 24/07/23
**/
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner inputStream = new Scanner(System.in);
    static final int MIN_NUMBER_OF_ROUNDS = 5;
    static final int MAX_NUMBER_OF_ROUNDS = 10;
    //the following arrays will be formatted {abbreviated option 1, unabbreviated option 1, abbreviated option 2, unabbreviated option 2, ...}
    static final String[] VALID_MOVES = {"c", "cooperate", "d", "defect"};
    static final String[] YES_NO_RESPONSES = {"y", "yes", "n", "no"};
    static final String[] GAME_MODES = {"m", "multiplayer", "c", "computer"};
    static final String[] CONTINUE_RESPONSES = {"c", "continue"};
    static final String GAME_INSTRUCTIONS ="game instructions"; //description of the game and instructions , to-do
    static int playerOneScore;
    static int playerTwoScore;
    static int computerScore;
    static String[] playerOneMoves;
    static String[] playerTwoMoves;
    static String[] computerMoves;
    static int roundNumber;
    static boolean needsYesOrNo=false;
    static String playerOneName;
    static String playerTwoName;
    static String gameMode;


    public static void main(String args[]){
        startGame();
    }

    public static void startGame(){ //this starts the game
        //resets relevant variables
        playerOneScore = 0;
        playerTwoScore=0;
        computerScore = 0;
        int numberOfRounds = (int) Math.floor(Math.random()*(MAX_NUMBER_OF_ROUNDS - MIN_NUMBER_OF_ROUNDS + 1) + MIN_NUMBER_OF_ROUNDS);
        playerOneMoves = new String[numberOfRounds];
        playerTwoMoves = new String[numberOfRounds];
        computerMoves = new String[numberOfRounds];
        roundNumber=0;

        System.out.println(GAME_INSTRUCTIONS); //prints game instructions

        //ask user to set game mode
        gameMode = getPromptedInput("would you like to play against the computer or multiplayer?", "startGame", "if you would like to play against the computer, say 'computer' or 'c'. if you would like to play multiplayer, say 'multiplayer' or 'm'");

        //ask for players names when multiplayer
        if(gameMode.equals("m")){
            //asks for and take in player ones name
            System.out.println("player one, what is your name?");
            playerOneName=inputStream.nextLine();

            //aks for and take in player twos name
            System.out.println("player two, what is your name?");
            playerTwoName=inputStream.nextLine();
            if(playerOneName.toLowerCase().equals("thomas")||playerTwoName.toLowerCase().equals("thomas")) System.out.println("Thomas, my favourite person");
        }

        //runs the method for each round
        for(int i=0; i<numberOfRounds; i++) {
            round();
        }

        endGame();
    }

    public static void round(){ //this method runs each round
        clearTerminal();
        System.out.println("round " + (roundNumber+1));
        if (gameMode.equals("m")) { //round when multiplayer
            //ask player one for their decision
            playerOneMoves[roundNumber]= getPromptedInput((playerOneName+ ", do you cooperate or defect?"), "round", "to cooperate: 'cooperate', 'c'. to defect 'defect', 'd'."); //prompt user, take in and log users move
            clearTerminal();
            //ask player two for their decision
            playerTwoMoves[roundNumber]= getPromptedInput((playerTwoName+ ", do you cooperate or defect?"), "round", "instructions (todo)"); //prompt user, take in and log users move
            clearTerminal();
            //determine and announce who won the round
            determineRoundWinner((playerOneMoves[roundNumber].equals("c")), playerOneName, (playerTwoMoves[roundNumber].equals("c")), playerTwoName);
        }else{ //round when playing against computer
            playerOneMoves[roundNumber]= getPromptedInput("do you cooperate or defect", "round", "instructions (todo)"); //prompt user, take in and log users move
            computerDecisions(); //gets computer to decide move
            determineRoundWinner((playerOneMoves[roundNumber].equals("c")), "you", (computerMoves[roundNumber].equals("c")), "they");
        }
        waitToContinue();
        roundNumber++;
    }

    public static void settings(){ //print settings options and allow user to adjust language, input options
        System.out.println("settings");
        //todo all this
    }

    public static String getPromptedInput(String prompt, String methodCalledFrom, String invalidInputInstructions){ //outputs text and reliably returns the response
        String input;
        String output = null;
        do {
            //print prompt and take in input
            System.out.println(prompt);
            input = inputStream.nextLine();
            input = input.toLowerCase();

            //check the user input against valid inputs
            if (input.equals("s") || input.equals("settings")) settings(); //if at any point user wants to access settings/instructions
            if (needsYesOrNo) {  //only runs if we want a yes or no
                needsYesOrNo = false;
                output = findInArray(input, YES_NO_RESPONSES);
            }
            if (methodCalledFrom.equals("round")) output = findInArray(input, VALID_MOVES); //for users decisions
            if (methodCalledFrom.equals("startGame")) output = findInArray(input, GAME_MODES); //for mode decisions
            if(output==null){ //if the input is invalid, explain to the user this is the case
                System.out.println("invalid input");
                System.out.println(invalidInputInstructions);
            }
        }while(output==null); //if they dont have a valid input, ask again
        return output;
    }

    public static void endGame(){
        //runs when a game ends. should: announce score, announce victor, announce overall score including past games, offer option to play again or quit
        if(gameMode.equals("c")) { //when playing against computer checks computerScore vs playerOneScore
            if (playerOneScore > computerScore) System.out.println("Congratulations, you win! Your score was " + playerOneScore + ", I scored " + computerScore + "!");//could add more possible answers
            if (computerScore > playerOneScore) System.out.println("You lose! Your score was " + playerOneScore + ", I scored " + computerScore + "!");
            else System.out.println("It's a tie! We both scored " + playerOneScore);
        }if(gameMode.equals("m")){ //when playing multiplayer checks playerOneScore vs playerTwoScore
            if (playerOneScore > playerTwoScore) System.out.println(playerOneName + " wins!");//could add more possible answers
            if (playerTwoScore > playerOneScore) System.out.println(playerTwoName + " wins!");
            else System.out.println("It's a tie! You both scored " + playerOneScore);
        }

        //ask if they would like to play again
        needsYesOrNo=true;
        String decision = getPromptedInput("Would you like to play again", "endGame", "invalid input instructions (to do)");
        if(decision.equals("y")) startGame();
    }

    public static void computerDecisions(){
        double randomNumber=Math.random();
        for(int i = 1; i<= VALID_MOVES.length/2; i++){ //randomly selects a move
            if(randomNumber<2.0D*i/ VALID_MOVES.length){
                computerMoves[roundNumber]= VALID_MOVES[2*i-2];
            }
        }
    }

    public static void clearTerminal(){
        //clear terminal (doesn't work in intellij)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String findInArray(String input, String[] array ){
        //checks if the input is in the array. if so returns the users decision, always in the abbreviated form
        for(int i=0; i<array.length; i++){//checks against each element of the array
            if(input.equals(array[i])){
                //return the abbreviated input
                if (i % 2 == 1) return array[i - 1];
                else return array[i];
            }
        }
        return null;
    }

    public static void determineRoundWinner(boolean playerACooperates, String playerAName, boolean playerBCooperates, String playerBName){ //determines the winner of a round
        if(playerACooperates && playerBCooperates){
            System.out.println("you both cooperated");
        }if(playerACooperates && !playerBCooperates){
            System.out.println(playerAName + " cooperated, " + playerBName + " defected.");
        }if(!playerACooperates && playerBCooperates){
            System.out.println(playerBName + " cooperated, " + playerAName + " defected.");
        }if(!playerACooperates && !playerBCooperates){
            System.out.println("you both defected");
        }
    }

    public static void waitToContinue(){
        //pauses game until user says to continue
        System.out.println("'c' to continue");
        String input;

        //waits for valid response
        do {
            input = inputStream.nextLine();
            input = input.toLowerCase();
            if(!Arrays.asList(CONTINUE_RESPONSES).contains(input)) System.out.println("invalid input"); //if input is invalid, inform the user then wait for another response
        }while(!Arrays.asList(CONTINUE_RESPONSES).contains(input));
    }
}
