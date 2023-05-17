import java.util.Scanner;

public class Main {
    static Scanner inputStream = new Scanner(System.in);

    static int minNumberOfRounds = 5;
    static int maxNumberOfRounds = 10;
    static int numberOfRounds;
    static int userScore;
    static int computerScore;
    static String[] userMoves;


    public static void main(String args[]){
    }

    public static void startGame(){ //this starts the game
        userScore = 0;
        computerScore = 0;
        numberOfRounds = (int) Math.floor(Math.random()*(maxNumberOfRounds - minNumberOfRounds + 1) + minNumberOfRounds);
        userMoves = new String[numberOfRounds];
    }

    public static String promptedInput(String prompt) { //this prints a prompt and takes in an input
        System.out.println(prompt);
        return inputStream.nextLine();
    }
}