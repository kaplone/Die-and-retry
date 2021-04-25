package com.codingame.game;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.entities.*;
import com.google.inject.Inject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Referee extends AbstractReferee {
    @Inject private SoloGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private Integer score = 0;

    @Override
    public void init() {
        gameManager.setFrameDuration(500);


        // Draw background
        graphicEntityModule.createSprite().setImage(null);

        // traffic fire definitions

    }

    @Override
    public void gameTurn(int turn) {
        //gameManager.getPlayer().sendInputLine(fishPosition.toString());

        updateView();

        try {
            List<String> outputs = gameManager.getPlayer().getOutputs();
            //System.err.println(outputs);
            String output = checkOutput(outputs);

            if (output == null){
                throw new TimeoutException();
            }

            Action action = Action.valueOf(output.toUpperCase());
            checkInvalidAction(action);


        } catch (TimeoutException e) {
            gameManager.loseGame("Timeout!");
        }
    }

    @Override
    public void onEnd() {
        //gameManager.putMetadata("eggs", String.valueOf(eggsCollected));
    }

    private void updateView() {


    }

    private String checkOutput(List<String> outputs) {
        //System.err.println(outputs);
        if (outputs.size() != 1) {
            gameManager.loseGame("You did not send 1 output in your turn.");
        } else {
            String output = outputs.get(0);
            //System.err.println(output);
            if (!Arrays.asList(Constants.ACTIONS).contains(output)) {
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
        if (action == null || !(action == Action.WAIT|| action == Action.H_RED || action == Action.V_RED || action == Action.H_GREEN || action == Action.V_GREEN
                || action == Action.N_RED || action == Action.S_RED || action == Action.E_RED || action == Action.W_RED)){
            gameManager.loseGame("Commande invalide.");
        }

//        if ((fishPosition.y == 0 && action == Action.UP)
//            || (fishPosition.y == Constants.ROWS - 1 && action == Action.DOWN)
//            || fishPosition.add(Coord.RIGHT).x > Constants.COLUMNS - 1) {
//            gameManager.loseGame("Your fish swum out of the game zone.");
//        }
    }


}
