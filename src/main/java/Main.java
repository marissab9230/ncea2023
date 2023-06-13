import java.util.Scanner;

public class Main {
    static Scanner inputStream = new Scanner(System.in);

    static int minNumberOfRounds = 5;
    static int maxNumberOfRounds = 10;
    static int numberOfRounds;
    static int userScore;
    static int computerScore;
    static String[] userMoves;
    static String[] computerMoves;
    static String gameInstructions; //description of the game and instructions , to-do
    static String roundInstructions;//prints every round, asks user to defect/stick to story
    static int roundNumber;
    static int gameNumber;
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
        userMoves = new String[numberOfRounds];
        computerMoves = new String[numberOfRounds];
        roundNumber=0;

        System.out.println(gameInstructions); //prints game instructions
        for(int i=0; i<numberOfRounds; i++) {
            round();
        }
        endGame();
    }

    public static void round(){ //this method runs each round
        userMoves[roundNumber]=promptedInput(roundInstructions); //prompt user, take in and log users move

        //determine outcome of round
        if(userMoves[roundNumber].equals("c") && computerMoves[roundNumber].equals("c")){
            //change scores accordingly and print results
        }if(userMoves[roundNumber].equals("c") && computerMoves[roundNumber].equals("d")){

        }if(userMoves[roundNumber].equals("d") && computerMoves[roundNumber].equals("c")){

        }

        roundNumber++;
    }

    public static void settings(){
        //print settings options and allow user to adjust language, input options
    }

    public static String promptedInput(String prompt){ //
        //print prompt and take in input
        System.out.println(prompt);
        String input = inputStream.nextLine();
        input=input.toLowerCase();

        //check the user input against valid inputs
        if(roundNumber==numberOfRounds-1){ //used when the game is ending
            for(int i=0; i<yesNoResponse.length; i++){
                if(input.equals(yesNoResponse[i])){
                    //return "y" for yes or "n" for no regardless of the user input; this should make things easier
                    if(i % 2 == 1) return yesNoResponse[i-1];
                    else return yesNoResponse[i];
                }
            }
        }

        for(int i=0; i<validMoves.length; i++){
            if(input.equals(validMoves[i])){
                //return "c" for cooperate or "d" for defect regardless of the user input; this should make things easier
                if(i % 2 == 1) return validMoves[i-1];
                else return validMoves[i];
            }
        }
        if(input.equals("s")||input.equals("settings")) settings();
        return null;//need to sort out invalid inputs
    }

    /*public static String interpretInput(String input){//THIS METHOD HAS BEEN COMBINES WITH promptedInput
        for(int i=0; i<validMoves.length; i++){
            if(input.equals(validMoves[i])){//check the user input against valid moves
                //return "c" for cooperate or "d" for defect regardless of the user input; this should make things easier
                if(i % 2 == 1) return validMoves[i-1];
                else return validMoves[i];
            }
        }
        if(input.equals("s")||input.equals("settings")) settings();
        return null;
    }*/

    public static void endGame(){
        //runs when a game ends. should: announce score, announce victor, announce overall score including past games, offer option to play again or quit
        if(userScore>computerScore) System.out.println("Congratulations, you win! Your score was " + userScore + ", I scored "+ computerScore+ "!");//could add more possible answers
        if(computerScore>userScore) System.out.println("You lose! Your score was " + userScore + ", I scored "+ computerScore+ "!");
        else System.out.println("It's a tie! We both scored "+ userScore);

        needsYesOrNo=true;
        String decision = promptedInput("Would you like to play again");
        if(decision.equals("y")) startGame();
    }
}