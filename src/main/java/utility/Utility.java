package utility;
import java.util.Scanner;

/**
 * The Utility class provides static helper methods for user input handling,
 * generating random values, and other general utility functions that can be
 * used throughout the application.
 */
public class Utility {

    private static final Scanner INPUT = new Scanner(System.in);

    private Utility() {

    }

    /**
     * Handles user input by prompting for a decision within a specified range.
     * The method continuously prompts the user until a valid integer within the
     * range [min, max] is entered.
     *
     * @param min the minimum valid value the user can select
     * @param max the maximum valid value the user can select
     * @return the user's valid selection as an integer within the range [min, max]
     */
    public static int handleDecision(int min, int max) {
        while (true) {
            System.out.print("Select: ");

            if (!INPUT.hasNextInt()) {
                System.out.println("Invalid selection. Try again");
                INPUT.nextLine();
                continue;
            }
            int choice = INPUT.nextInt();
            INPUT.nextLine();

            if (choice >= min && choice <= max) {
                return choice;
            }
            System.out.println("Invalid selection. Try again");
        }
    }

    /**
     * Prompts the user to press the enter key to continue.
     * This method pauses the program execution until the user presses the enter key.
     * Typically used to create pauses in console-based workflows, such as waiting
     * for user acknowledgment before proceeding.
     */
    public static void enterToContinue() {
        System.out.println("Press enter to continue...");
        INPUT.nextLine();
    }

}
