import java.util.*;

/*
 * A basic example AI for the game.
 * Goes to the closest reachable eggs.
 */

public class Solution_die_and_retry {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int turn = 0;
        String[] out = new String[] {"A_01", "A_07", "A_09", "A_02", "A_10", "A_03", "A_09", "A_08", "A_04", "A_01", "A_07", "A_10", "A_08", "A_02", "A_05", "A_07", "A_09", "A_06", "A_10", "A_01", "A_09", "A_08", "A_02"};

        List<Result> results = new ArrayList<>();

        while (true){
            // general
            int maxTurns = scanner.nextInt(); // turns to play
            System.err.println("Remaining turns = " + maxTurns);
            scanner.nextLine();

            int nbElements = scanner.nextInt(); // elementsCount
            scanner.nextLine();
            for (int i = 0; i < nbElements; i++){
                String element = scanner.nextLine();
                System.err.println("Element = " + element);
            }


            String input = scanner.nextLine();

            List<String> inChoices = Arrays.asList(input.split("#"));
            Collections.shuffle(inChoices);
            System.err.println("Choix possibles = " + inChoices);
//            System.out.println(inChoices.get(inChoices.size() - 1));
            int indexRandom = Math.max(0, (int) Math.round(Math.random() * inChoices.size() - 1));

            System.err.println("index random = " + indexRandom);
            System.out.println(inChoices.get(indexRandom));
//            System.out.println(out[turn]);
            turn++;
        }
    }
}

class Result{
    private String action;
    private List<String> otherActions;
    private Map<String, List<String>> elementsStates;
    private Result nextResult;
    private Result previousResult;

    public Result(String action, List<String> otherActions, Map<String, List<String>> elementsStates, Result nextResult, Result previousResult) {
        this.action = action;
        this.otherActions = otherActions;
        this.elementsStates = elementsStates;
        this.nextResult = nextResult;
        this.previousResult = previousResult;
    }

    public List<String> getOtherActions() {
        return otherActions;
    }

    public void setOtherActions(List<String> otherActions) {
        this.otherActions = otherActions;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, List<String>> getElementsStates() {
        return elementsStates;
    }

    public void setElementsStates(Map<String, List<String>> elementsStates) {
        this.elementsStates = elementsStates;
    }

    public Result getNextResult() {
        return nextResult;
    }

    public void setNextResult(Result nextResult) {
        this.nextResult = nextResult;
    }

    public Result getPreviousResult() {
        return previousResult;
    }

    public void setPreviousResult(Result previousResult) {
        this.previousResult = previousResult;
    }
}
