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

        int turn = 0;
        String[] out = new String[] {"A_01", "A_07", "A_09", "A_02", "A_10", "A_03", "A_09", "A_08", "A_04", "A_01", "A_07", "A_10", "A_08", "A_02", "A_05", "A_07", "A_09", "A_06", "A_10", "A_01", "A_09", "A_08", "A_02"};

        while (true){
            // general
            int maxTurns = scanner.nextInt();
            System.err.println("Remaining turns = " + maxTurns);
            scanner.nextLine();
            String input = scanner.nextLine();

            List<String> inChoices = Arrays.asList(input.split("#"));
            System.err.println("Choix possibles = " + inChoices);
//            System.out.println(inChoices.get(inChoices.size() - 1));
            int indexRandom = Math.max(0, (int) Math.round(Math.random() * inChoices.size() - 1));
            System.err.println("index random = " + indexRandom);
            System.out.println(inChoices.get(indexRandom));
            //System.out.println(out[turn]);
            turn++;
        }



    }

}
