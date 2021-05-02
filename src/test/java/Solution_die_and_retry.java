import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
 * A basic example AI for the game.
 * Goes to the closest reachable eggs.
 */

public class Solution_die_and_retry {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            // general
            int maxTurns = scanner.nextInt();
            System.err.println("Remaining turns = " + maxTurns);
            scanner.nextLine();
            String input = scanner.nextLine();

            List<String> inChoices = Arrays.asList(input.split("#"));
            System.err.println("Choix possibles = " + inChoices);
            System.out.println(inChoices.get(inChoices.size() - 1));
        }



    }

}
