package Utility;

import java.util.Scanner;

public class Utility {

    public static int handleDecision(Scanner input, int minChoice, int maxChoice) {
        while (true) {
            System.out.print("Select: ");

            if (!input.hasNextInt()) {
                System.out.println("Invalid selection. Try again");
                input.nextLine();
                continue;
            }
            int choice = input.nextInt();
            input.nextLine();

            if (choice >= minChoice && choice <= maxChoice) {
                return choice;
            }
            System.out.println("Invalid selection. Try again");
        }
    }
}
