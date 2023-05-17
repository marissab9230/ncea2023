import java.util.Scanner;

public class Main {
    static Scanner inputStream = new Scanner(System.in);

    public static void main(String args[]){
    }

    public static String promptedInput(String prompt){
        System.out.println(prompt);
        return inputStream.nextLine();
    }

    public static void interpretInput(String input){
        input= (input.toLowerCase());
    }
}