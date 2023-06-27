import org.jetbrains.annotations.Nullable;

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
    static boolean needsYesOrNo=false;

    public static void main(String args[]){
        startGame();
    }

    public static void startGame(){ //this starts the game
        //resets relevant variables
        userScore = 0;
        computerScore = 0;
        numberOfRounds = (int) Math.floor(Math.random()*(maxNumberOfRounds - minNumberOfRounds + 1) + minNumberOfRounds);
        playerOneMoves = new String[numberOfRounds];
        computerMoves = new String[numberOfRounds];
        roundNumber=0;

        System.out.println(gameInstructions); //prints game instructions

        promptedInput("would you like to play against the computer or another player?", "startGame");

        for(int i=0; i<numberOfRounds; i++) {
            round();
        }
        endGame();
    }

    public static void round(){ //this method runs each round
        System.out.println("round " + (roundNumber+1));
        playerOneMoves[roundNumber]=promptedInput("do you cooperate or defect", "round"); //prompt user, take in and log users move
        while(playerOneMoves[roundNumber]==null){ //if input is invalid, ask again
            System.out.println("input invalid");
            playerOneMoves[roundNumber]=promptedInput("do you cooperate or defect", "round");
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

        roundNumber++;
    }

    public static void settings(){ //print settings options and allow user to adjust language, input options
        //TO-DO: all settings code
    }

    @Nullable
    public static String promptedInput(String prompt, String methodCalledFrom){ //
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

        if(methodCalledFrom.equals("startGame")){

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

        //if at any point user wants to access settings/instructions
        if(input.equals("s")||input.equals("settings")) settings();

        return null;//need to sort out invalid inputs
    }

    public static void endGame(){
        //runs when a game ends. should: announce score, announce victor, announce overall score including past games, offer option to play again or quit
        if(userScore>computerScore) System.out.println("Congratulations, you win! Your score was " + userScore + ", I scored "+ computerScore+ "!");//could add more possible answers
        if(computerScore>userScore) System.out.println("You lose! Your score was " + userScore + ", I scored "+ computerScore+ "!");
        else System.out.println("It's a tie! We both scored "+ userScore);

        needsYesOrNo=true;
        String decision = promptedInput("Would you like to play again", "endGame");
        if(decision.equals("y")) startGame();
    }

    public static void computerDecisions(){
        //for now i will randomly generate computers decisions
        if(Math.random()<=0.5) computerMoves[roundNumber] = "c";
        else computerMoves[roundNumber] = "d";
    }
}