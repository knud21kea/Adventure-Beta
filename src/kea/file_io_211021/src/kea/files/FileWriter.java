package kea.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class FileWriter {

    public static void main(String[] args) throws FileNotFoundException {

        //Opgave 1: output number of tries for a user to guess a number to file

        File random = new File("data/random.txt");
        PrintStream psr = new PrintStream(new FileOutputStream(random, true));
        Random rand = new Random();
        Scanner input = new Scanner(System.in);
        int countTries = 0;
        boolean guessedCorrect = false;
        int guess;
        int numberToGuess = rand.nextInt(4) + 1;
        System.out.println("Try to guess a number between 1 and 5 inclusive");
        while (!guessedCorrect) {
            System.out.print("\nWhat is your guess? ");
            guess = input.nextInt();
            input.nextLine();
            countTries++;
            if (guess != numberToGuess) {
                System.out.println("That is incorrect, please try again.");
            } else {
                System.out.println("That is correct.");
                guessedCorrect = true;
            }
        }
        psr.println(countTries);

        //Opgave 2: output user text to file of users choice

        String fileName;
        System.out.println("\nProgram now tries to save your text to a file.");
        System.out.print("What filename would you like? ");
        fileName = "data/" + input.nextLine() + ".txt";
        System.out.println("Will write to " + fileName);
        File userText = new File(fileName);
        PrintStream psu = new PrintStream(new FileOutputStream(userText, true));
        System.out.println("Please enter text one line at a time");
        boolean endOfInput = false;
        String inputText;
        while (!endOfInput) {
            System.out.println("Are you finished inputting lines? (y/n) ");
            inputText = input.nextLine();
            if (!inputText.toLowerCase().startsWith("y")) {
                System.out.println("OK, please enter the next line of text");
                psu.println(input.nextLine());
            } else {
                endOfInput = true;
            }
        }
    }
}
