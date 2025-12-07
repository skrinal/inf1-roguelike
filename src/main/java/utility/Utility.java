package utility;
import java.util.Scanner;

public class Utility {

    private static final Scanner INPUT = new Scanner(System.in);

    public static int handleDecision(int minChoice, int maxChoice) {
        while (true) {
            System.out.print("Select: ");

            if (!INPUT.hasNextInt()) {
                System.out.println("Invalid selection. Try again");
                INPUT.nextLine();
                continue;
            }
            int choice = INPUT.nextInt();
            INPUT.nextLine();

            if (choice >= minChoice && choice <= maxChoice) {
                return choice;
            }
            System.out.println("Invalid selection. Try again");
        }
    }

    public static void enterToContinue() {
        System.out.println("Press enter to continue...");
        INPUT.nextLine();
    }


}
