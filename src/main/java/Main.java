/**
    Name: Marissa Burnette

    Date: 24/07/23
**/
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner inputStream = new Scanner(System.in);
    static final int minNumberOfRounds = 5;
    static final int maxNumberOfRounds = 10;
    //the following arrays will be formatted {abbreviated option 1, unabbreviated option 1, abbreviated option 2, unabbreviated option 2, ...}
    static final String[] validMoves = {"c", "cooperate", "d", "defect"};
    static final String[] yesNoResponse = {"y", "yes", "n", "no"};
    static final String[] gameModes = {"m", "multiplayer", "c", "computer"};
    static final String[] continueResponses = {"c", "continue"};
    static int playerOneScore;
    static int playerTwoScore;
    static int computerScore;
    static String[] playerOneMoves;
    static String[] playerTwoMoves;
    static String[] computerMoves;
    static final String gameInstructions="game instructions"; //description of the game and instructions , to-do
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
        int numberOfRounds = (int) Math.floor(Math.random()*(maxNumberOfRounds - minNumberOfRounds + 1) + minNumberOfRounds);
        playerOneMoves = new String[numberOfRounds];
        playerTwoMoves = new String[numberOfRounds];
        computerMoves = new String[numberOfRounds];
        roundNumber=0;

        System.out.println(gameInstructions); //prints game instructions

        //ask user to set game mode
        gameMode = getPromptedInput("would you like to play against the computer or multiplayer?", "startGame", "if you would like to play against the computer, say 'computer' or 'c'. if you would like to play multiplayer, say 'multiplayer' or 'm'");

        //ask for players names when multiplayer
        if(gameMode.equals("m")){
            //asks for player ones name
            System.out.println("player one, what is your name?");
            playerOneName=inputStream.nextLine();

            //aks for player twos name
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
            determineWinner((playerOneMoves[roundNumber].equals("c")), playerOneName, (playerTwoMoves[roundNumber].equals("c")), playerTwoName);
        }else{ //round when playing against computer
            playerOneMoves[roundNumber]= getPromptedInput("do you cooperate or defect", "round", "instructions (todo)"); //prompt user, take in and log users move
            computerDecisions(); //gets computer to decide move
            determineWinner((playerOneMoves[roundNumber].equals("c")), "you", (computerMoves[roundNumber].equals("c")), "they");
        }
        waitToContinue();
        roundNumber++;
    }

    public static void settings(){ //print settings options and allow user to adjust language, input options
        System.out.println("settings");

    }

    public static String getPromptedInput(String prompt, String methodCalledFrom, String invalidInputInstructions){ //outputs text and returns the response
        String input;
        String output = null;
        do {
            //print prompt and take in input
            System.out.println(prompt);
            input = inputStream.nextLine();
            input = input.toLowerCase();

            //check the user input against valid inputs

            //if at any point user wants to access settings/instructions
            if (input.equals("s") || input.equals("settings")) settings();

            //only runs if we want a yes or no
            if (needsYesOrNo) {
                needsYesOrNo = false;
                output = checkArray(input, yesNoResponse);
            }

            //for users decisions
            if (methodCalledFrom.equals("round")) output = checkArray(input, validMoves);

            //for mode decisions
            if (methodCalledFrom.equals("startGame")) output = checkArray(input, gameModes);

            //if the input is invalid, explain to the user this is the case then the loop repeats
            if(output==null){
                System.out.println("invalid input");
                System.out.println(invalidInputInstructions);
            }
        }while(output==null);
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
        for(int i=1; i<=validMoves.length/2; i++){ //randomly selects a move
            if(randomNumber<2.0D*i/validMoves.length){
                computerMoves[roundNumber]=validMoves[2*i-2];
            }
        }
    }

    public static void clearTerminal(){
        //clear terminal (doesn't work here)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String checkArray(String input, String[] array ){
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

    public static void determineWinner(boolean playerACooperates, String playerAName, boolean playerBCooperates, String playerBName){
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

        //asks user for
        do {
            input = inputStream.nextLine();
            input = input.toLowerCase();
            if(!Arrays.asList(continueResponses).contains(input)) System.out.println("invalid input"); //if input is invalid, inform the user then wait for another response
        }while(!Arrays.asList(continueResponses).contains(input));
    }
}
