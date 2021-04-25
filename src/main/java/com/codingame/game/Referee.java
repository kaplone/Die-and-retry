package com.codingame.game;

import com.codingame.game.utils.Choice;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.entities.*;
import com.google.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

public class Referee extends AbstractReferee {
    @Inject private SoloGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private Integer score = 0;
    private Integer remainingTurns;
    private static int readLineIndex = 0;
    private static Choice currentChoice = null;
    private static Choice previousChoice = null;

    public static Map<Integer, Choice> choices;
    static List<String> aviable = new ArrayList<>();

    @Override
    public void init() {
        gameManager.setFrameDuration(500);

        choices = new HashMap<>();

        // general definitions
        Integer maxTurnsCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        remainingTurns = maxTurnsCount;
        readLineIndex++; // 1
        Integer choicesCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        readLineIndex++; //  2

        for (int i = 0; i < choicesCount; i++){
            Integer id = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex).split("_")[0]);
            String libelle = gameManager.getTestCaseInput().get(readLineIndex).split("_")[1];
            List<String> next = Arrays.asList(gameManager.getTestCaseInput().get(readLineIndex).split("_")[2].split("#"));
            Boolean unique = Boolean.valueOf(gameManager.getTestCaseInput().get(readLineIndex).split("_")[3]);

            choices.put(id, new Choice(id, libelle, next, unique));
            readLineIndex++; //  loop
        }

        currentChoice = choices.get(0);

    }

    @Override
    public void gameTurn(int turn) {

        System.err.println("remaining " + remainingTurns + " turns");
        System.err.println("  --> " + currentChoice.getLibelle());
        System.err.println("  --> " + currentChoice.toPlayer());

        gameManager.getPlayer().sendInputLine("" + remainingTurns);
        gameManager.getPlayer().sendInputLine(currentChoice.toPlayer());

        aviable = currentChoice.getPossibilities();

        gameManager.getPlayer().execute();

        try {
            List<String> outputs = gameManager.getPlayer().getOutputs();
            String output = checkOutput(outputs);

            if (output == null){
                throw new TimeoutException();
            }


            Optional<Choice> optChoice = choices.values().stream().filter(c -> c.getLibelle().equals(output)).findFirst();
            if(optChoice.isPresent()){
                previousChoice = currentChoice;
                currentChoice = optChoice.get();
            }
            else {
                currentChoice = previousChoice;
            }


           // Action action = Action.valueOf(output.toUpperCase());
           // checkInvalidAction(action);


        } catch (TimeoutException e) {
            gameManager.loseGame("Timeout!");
        }


    }

    @Override
    public void onEnd() {
        //gameManager.putMetadata("eggs", String.valueOf(eggsCollected));
    }

    private String checkOutput(List<String> outputs) {
        System.err.println(outputs);
        if (outputs.size() != 1) {
            gameManager.loseGame("You did not send 1 output in your turn.");
        } else {
            String output = outputs.get(0);
            System.err.println("Here aviable =" + aviable);
            System.err.println("Here output =" + output);
            if (!aviable.contains(output)) {
                gameManager
                        .loseGame(
                                String.format(
                                        "Expected output: %s but received %s",
                                        Arrays.asList(Constants.ACTIONS).stream().collect(Collectors.joining(" | ")),
                                        output
                                )
                        );
            } else {
                return output;
            }
        }
        return null;
    }

    private void checkInvalidAction(Action action) {
        if (false){
            gameManager.loseGame("Commande invalide.");
        }
    }


}
