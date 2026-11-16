
import java.util.Scanner;

public class SafeInput {

    // Gets a whole number within the allowed range.
    public static int getRangedInt(Scanner console, String prompt,
                                   int low, int high) {
        while (true) {
            System.out.print(prompt + " [" + low + "-" + high + "]: ");
            String input = console.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= low && value <= high) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Invalid numbers are handled below.
            }

            System.out.println("Invalid input. Enter a whole number from "
                    + low + " to " + high + ".");
        }
    }

    // Asks a Yes or No question.
    public static boolean getYNConfirm(Scanner console, String prompt) {
        while (true) {
            System.out.print(prompt + " [Y/N]: ");
            String answer = console.nextLine().trim();

            if (answer.equalsIgnoreCase("Y")
                    || answer.equalsIgnoreCase("YES")) {
                return true;
            }

            if (answer.equalsIgnoreCase("N")
                    || answer.equalsIgnoreCase("NO")) {
                return false;
            }

            System.out.println("Please enter Y or N.");
        }
    }
}
