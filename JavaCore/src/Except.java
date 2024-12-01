import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Except {
    static int divUp(int num, int den) {
        return (num + den - 1) / den;
    }
    public static void main(String[] args) {
        System.out.println(divUp(128, 16));

        String input = "Hello 123, this is 456 and 789";

        String regex = "\\d+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        boolean matches = matcher.matches();
        System.out.println("Does the entire string match? " + matches);

        System.out.println("\nFinding all matches:");
        while (matcher.find()) {
            System.out.println("Found: " + matcher.group() + " at position: " + matcher.start() + " to " + matcher.end());
        }

        String replaced = matcher.replaceAll("NUMBER");
        System.out.println("\nReplaced all numbers with 'NUMBER': " + replaced);

        matcher = pattern.matcher(input);

        if (matcher.find()) {
            System.out.println("\nFirst match (group): " + matcher.group());
        }

        if (matcher.find()) {
            System.out.println("Start position of the match: " + matcher.start());
            System.out.println("End position of the match: " + matcher.end());
        }
    }
}
