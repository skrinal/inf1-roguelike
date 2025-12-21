package utility;
import java.util.Random;
import java.util.Scanner;

public class Utility {

    private static final Scanner INPUT = new Scanner(System.in);
    private static final Random RANDOM = new Random();

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

    public static void enterToContinue() {
        System.out.println("Press enter to continue...");
        INPUT.nextLine();
    }

    public static double getRandomDouble() {
        return RANDOM.nextDouble();
    }

    public static Random getRandom() {
        return RANDOM;
    }
}
