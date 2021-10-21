package kea.adventure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    public static void main(String[] args) {

        File file = new File("data/hejmeddig.txt");
        File temp = new File("data/DMI.txt");
        String linje;
        try {
            //Opgave 1: read from file and print to screen with words of at least 5 letters in capitals
            String ord;
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                linje = input.nextLine();
                String[] ordListe = linje.split(" ");
                for (String s : ordListe) {
                    ord = s;
                    if (ord.length() > 4) {
                        System.out.print(s.toUpperCase() + " ");
                    } else {
                        System.out.print(s + " ");
                    }
                }
                System.out.println();
            }
            //Opgave 2: read temperatures from file and calculate the average. Output as rounded up.
            input = new Scanner(temp);
            int sum = 0;
            int count = 0;
            while (input.hasNext()) {
                linje = input.nextLine();
                String[] ordListe = linje.split(" ");
                int t = Integer.parseInt(ordListe[1]);
                sum += t;
                count++;

            }
            System.out.println();
            System.out.printf("%s %2.0f", "Average temperature is", Math.ceil((double) sum / count));

        } catch (FileNotFoundException e) {
            System.out.println("KaffePause " + e.getMessage());
        }
    }
}
