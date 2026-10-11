import java.util.Scanner;

/**
 * InputHelper.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * Reads console input safely. Every method keeps re-prompting until the user
 * types a valid value, so bad input can never crash the program.
 */
public class InputHelper {

    private final Scanner scanner = new Scanner(System.in);

    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {            // input stream closed (Ctrl+D / end of file)
            System.out.println("\nInput closed. Exiting the program safely.");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    /** Reads a non-blank text value. */
    public String readText(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("  Invalid input: this field cannot be blank. Try again.");
        }
    }

    /** Reads a person's name: must contain a letter and only letters, spaces, . ' - */
    public String readName(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (value.isEmpty()) {
                System.out.println("  Invalid name: the name cannot be blank. Try again.");
            } else if (value.matches("[A-Za-z][A-Za-z .'-]*")) {
                return value;
            } else {
                System.out.println("  Invalid name: use letters only (spaces, - and ' allowed). Try again.");
            }
        }
    }

    /** Reads a whole number between min and max (inclusive). */
    public int readInt(String prompt, int min, int max) {
        while (true) {
            String value = readLine(prompt);
            try {
                int number = Integer.parseInt(value);
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println("  Invalid input: enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input: '" + value + "' is not a whole number. Try again.");
            }
        }
    }

    /** Reads a positive amount of money (commas are allowed, e.g. 12,500). */
    public double readPositiveAmount(String prompt) {
        while (true) {
            String value = readLine(prompt).replace(",", "");
            try {
                double number = Double.parseDouble(value);
                if (!Double.isNaN(number) && !Double.isInfinite(number) && number > 0) {
                    return number;
                }
                System.out.println("  Invalid input: the amount must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input: '" + value + "' is not a valid amount. Try again.");
            }
        }
    }

    /** Reads a yes/no answer. */
    public boolean readYesNo(String prompt) {
        while (true) {
            String value = readLine(prompt).toLowerCase();
            if (value.equals("y") || value.equals("yes")) {
                return true;
            }
            if (value.equals("n") || value.equals("no")) {
                return false;
            }
            System.out.println("  Invalid input: please type Y or N.");
        }
    }
}
