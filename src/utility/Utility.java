package utility;
import java.util.Scanner;

public class Utility {

    private static Scanner input = new Scanner(System.in);

    public static int handleDecision(int minChoice, int maxChoice) {
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

    public static void enterToContinue() {
        System.out.println("Press enter to continue...");
        input.nextLine();
    }
}
