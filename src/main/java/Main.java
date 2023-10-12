/**
    Name: Marissa Burnette

    Date: 12/10/23
**/
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner inputStream = new Scanner(System.in);
    static final int MIN_NUMBER_OF_ROUNDS = 5;
    static final int MAX_NUMBER_OF_ROUNDS = 10;
    //the following arrays will be formatted {abbreviated option 1, unabbreviated option 1, abbreviated option 2, unabbreviated option 2, ...}
    static final String[] VALID_MOVES = {"c", "cooperate", "d", "defect"};
    static final String[] YES_NO_RESPONSES = {"y", "yes", "n", "no"};
    static final String[] GAME_MODES = {"m", "multiplayer", "c", "computer"};
    static final String[] YES_RESPONSES = {"y", "yes"};
    static final String GAME_INSTRUCTIONS ="Welcome to the game-ified 'Prisoners Dilemma'. You and your partner-in-crime have been arrested. The police don't have enough evidence to convict either of you on the principal charge, so are interrogating both of you. You may choose to cooperate with your partner, and stick to your story, or you may defect and betray them. If you both cooperate, you will have one year added to your prison sentence. If you defect and your partner cooperates, you will have no added punishment, but your partner will have three years added - and vice-versa if they betray you and you stick to the story. If you both betray eachother, you will both have two years added to your sentences. ";
    static int playerOneScore;
    static int playerTwoScore;
    static int computerScore;
    static String[] playerOneMoves;
    static String[] playerTwoMoves;
    static String[] computerMoves;
    static int roundNumber;
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
        gameMode = getPromptedInput("Would you like to play against the computer or multiplayer?", GAME_MODES);

        //ask for players names when multiplayer
        if(gameMode.equals("m")){
            do{
                //ask for users names
                System.out.println("Player one, what is your name?");
                playerOneName = inputStream.nextLine();
                System.out.println("Player two, what is your name?");
                playerTwoName = inputStream.nextLine();
                //if the names are the same, tell them to choose different names
                if(playerOneName.equals(playerTwoName)) System.out.println("Please choose different names.");
            }while(playerOneName.equals(playerTwoName)); //if they picked the same names, have them re-choose
        }

        //runs the method for each round
        for(int i=0; i<numberOfRounds; i++) round();

        endGame();
    }

    public static void round() { //this method runs each round
        getPromptedInput("Would you like to continue", YES_RESPONSES);
        clearTerminal();

        System.out.println("Round " + (roundNumber+1));
        if (gameMode.equals("m")) { //round when multiplayer
            //ask player one for their decision
            playerOneMoves[roundNumber]= getPromptedInput((playerOneName+ ", do you cooperate or defect?"), VALID_MOVES); //prompt user, take in and log users move
            clearTerminal();
            //ask player two for their decision
            playerTwoMoves[roundNumber]= getPromptedInput((playerTwoName+ ", do you cooperate or defect?"), VALID_MOVES); //prompt user, take in and log users move
            clearTerminal();
            //determine and announce who won the round
            determineRoundWinner((playerOneMoves[roundNumber].equals("c")), playerOneName, (playerTwoMoves[roundNumber].equals("c")), playerTwoName);
        }else{ //round when playing against computer
            playerOneMoves[roundNumber]= getPromptedInput("do you cooperate or defect", VALID_MOVES); //prompt user, take in and log users move
            computerDecisions(); //gets computer to decide move
            determineRoundWinner((playerOneMoves[roundNumber].equals("c")), "you", (computerMoves[roundNumber].equals("c")), "they");
        }
        roundNumber++;
    }

    public static String getPromptedInput(String prompt, String[] validResponses){ //outputs text and reliably returns the response
        String input;
        String output;
        do {
            System.out.println(prompt); //print the prompt
            input = inputStream.nextLine().toLowerCase(); //take in the input
            output=findInArray(input, validResponses); //check if the input is a valid response, if so sets output equal to their response, if else the output is null and it asks again
            if(output.equals("")){
                //if they've already inputted something they shouldn't, explain that
                System.out.print("This input is not valid. The valid responses are: ");
                for(int i=0; i< validResponses.length; i++){
                    if(i==(validResponses.length-1)) System.out.println("or " + validResponses[i]);
                    else System.out.print(validResponses[i] + ", ");
                }
            }
        }while(output.equals("")); //if they don't have a valid input, ask again
        return output;
    }

    public static void endGame(){
        //announces sentences
        if(gameMode.equals("c")) System.out.println("You are sentenced to " + playerOneScore + " years, I am sentenced to " + computerScore + " years.");
        if(gameMode.equals("m")) System.out.println(playerOneName +" is sentenced to " + playerOneScore + " years. " + playerTwoName +" is sentenced to " + playerTwoScore + " years.");

        //ask if they would like to play again
        String decision = getPromptedInput("Would you like to play again", YES_NO_RESPONSES);
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
        for(int i=0; i<array.length; i++){//compares input to each element of the array
            if(input.equals(array[i])){
                //return the abbreviated input
                if (i % 2 == 1) return array[i - 1];
                else return array[i];
            }
        }
        return "";
    }

    public static void determineRoundWinner(boolean playerACooperates, String playerAName, boolean playerBCooperates, String playerBName){ //determines the winner of a round
        int addedSentancePlayerA=0;
        int addedSentancePlayerB=0;
        if(playerACooperates && playerBCooperates){
            System.out.println("you both cooperated");
            addedSentancePlayerA=1;
            addedSentancePlayerB=1;
        }if(playerACooperates && !playerBCooperates){
            System.out.println(playerAName + " cooperated, " + playerBName + " defected.");
            addedSentancePlayerA=3;
        }if(!playerACooperates && playerBCooperates){
            System.out.println(playerBName + " cooperated, " + playerAName + " defected.");
            addedSentancePlayerB=3;
        }if(!playerACooperates && !playerBCooperates){
            System.out.println("you both defected");
            addedSentancePlayerA=2;
            addedSentancePlayerB=2;
        }
        playerOneScore+=addedSentancePlayerA;
        if(gameMode.equals("m")) playerTwoScore+=addedSentancePlayerB;
        if(gameMode.equals("c")) computerScore+=addedSentancePlayerB;
    }
}
