import java.util.Scanner;

public class Main {
    static Scanner inputStream = new Scanner(System.in);

    static int minNumberOfRounds = 5;
    static int maxNumberOfRounds = 10;
    static int numberOfRounds;
    static int userScore;
    static int computerScore;
    static String[] userMoves;
    static String gameInstructions; //description of the game and instructions , to-do
    static String roundInstructions;//prints every round, asks user to defect/stick to story
    static int roundNumber;
    static String[] validMoves = {"c", "cooperate", "d", "defect"};

    public static void main(String args[]){
        startGame();
    }

    public static void startGame(){ //this starts the game
        //resets relevant variables
        userScore = 0;
        computerScore = 0;
        numberOfRounds = (int) Math.floor(Math.random()*(maxNumberOfRounds - minNumberOfRounds + 1) + minNumberOfRounds);
        userMoves = new String[numberOfRounds];
        roundNumber=0;

        System.out.println(gameInstructions); //prints game instructions
        for(int i=0; i<numberOfRounds; i++) {
            round();
        }

        endGame();
    }

    public static void round(){ //this method runs each round
        userMoves[roundNumber]=promptedInput(roundInstructions); //
        roundNumber++;

    }

    public static void settings(){
        //print settings options and allow user to adjust language, input options
    }

    public static String promptedInput(String prompt) { //this prints a prompt and takes in an input. TO-DO: MAKE INPUT RELIABLE
        System.out.println(prompt);
        String input = inputStream.nextLine();
        input=input.toLowerCase();
        return input;
    }

    public static void interpretInput(String input){
        for(int i=0; i<validMoves.length; i++){
            if(input.equals(validMoves[i])) //to do
        }
    }

    public static void endGame(){
        //runs when a game ends. should: announce score, announce victor, announce overall score including past games, offer option to play again or quit
    }
}