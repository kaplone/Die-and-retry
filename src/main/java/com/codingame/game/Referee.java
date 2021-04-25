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
    static List<String> statesReached = new ArrayList<>();

    private  static Text textUp;
    private  static Text textIn;
    private  static Text textOut;

    private  static String request = "";
    private  static String response = "";

    private final String stringUp  = "Remaining turns : ";
    private final String stringIn  = "Action : ";
    private final String stringOut = "     --> ";

    @Override
    public void init() {
        gameManager.setFrameDuration(500);

        choices = new HashMap<>();

        textUp = graphicEntityModule.createText(stringUp + "")
                .setFontSize(80)
                .setFillColor(0x6784DE)
                .setX(450)
                .setY(50)
                .setZIndex(20);

        textIn = graphicEntityModule.createText(stringIn + "")
                .setFontSize(80)
                .setFillColor(0x67846314)
                .setX(250)
                .setY(350)
                .setZIndex(20);

        textOut = graphicEntityModule.createText(stringOut + "")
                .setFontSize(80)
                .setFillColor(0xFB9914)
                .setX(350)
                .setY(450)
                .setZIndex(20);

        // general definitions
        Integer maxTurnsCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        remainingTurns = maxTurnsCount;
        readLineIndex++; // 1
        Integer choicesCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        readLineIndex++; //  2

        for (int i = 0; i < choicesCount; i++){
            System.err.println(gameManager.getTestCaseInput().get(readLineIndex));
            Integer id = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex).split("_")[0]);
            String libelle = gameManager.getTestCaseInput().get(readLineIndex).split("_")[1];
            List<String> next = Arrays.asList(gameManager.getTestCaseInput().get(readLineIndex).split("_")[2].split("#"));
            String back = gameManager.getTestCaseInput().get(readLineIndex).split("_")[3];
            Boolean unique = Boolean.valueOf(gameManager.getTestCaseInput().get(readLineIndex).split("_")[4]);
            String giveState = gameManager.getTestCaseInput().get(readLineIndex).split("_")[5];
            List<String> needStates = Arrays.asList(gameManager.getTestCaseInput().get(readLineIndex).split("_")[6].split("#"));

            choices.put(id, new Choice(id, libelle, next, unique, giveState, needStates, back));
            readLineIndex++; //  loop
        }

        currentChoice = choices.get(0);


    }

    @Override
    public void gameTurn(int turn) {

        gameManager.getPlayer().sendInputLine("" + remainingTurns);
        gameManager.getPlayer().sendInputLine(currentChoice.toPlayer());

        aviable = currentChoice.getPossibilities();

        updateView();

        gameManager.getPlayer().execute();

        try {
            List<String> outputs = gameManager.getPlayer().getOutputs();

            String output = checkOutput(outputs);

            if (output == null){
                throw new TimeoutException();
            }

            request = outputs.get(0);


            Optional<Choice> optChoice = choices.values().stream().filter(c -> c.getLibelle().equals(output)).findFirst();
            if(optChoice.isPresent() && statesReached.containsAll(optChoice.get().getNeedStates())){
                previousChoice = currentChoice;
                currentChoice = optChoice.get();
                response = "new state reached = " + currentChoice.getGiveState();
                statesReached.add(currentChoice.getGiveState());
            }
            else if(optChoice.isPresent()){
                response = optChoice.get().getBack();
                currentChoice = previousChoice != null ? previousChoice : currentChoice;
            }
            else {
                currentChoice = previousChoice != null ? previousChoice : currentChoice;
            }

            remainingTurns--;

            if (remainingTurns < 0){
                response = "No remaining turn";
                gameManager.loseGame("No remaining turn");
            }

            if (statesReached.contains("You died !")){
                currentChoice = choices.get(0);
                statesReached.clear();
            }

            if (statesReached.contains("You did it !")){
                response = "You did it !";
                gameManager.winGame("Well done !");
            }


           // Action action = Action.valueOf(output.toUpperCase());
           // checkInvalidAction(action);


        } catch (TimeoutException e) {
            gameManager.loseGame("Timeout!");
        }


    }

    public void updateView(){

       textUp.setText(stringUp + remainingTurns);
       textIn.setText(stringIn + request);
       textOut.setText(stringOut + response);
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
//            System.err.println("Here aviable =" + aviable);
//            System.err.println("Here output =" + output);
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
