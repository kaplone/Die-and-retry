package com.codingame.game;

import com.codingame.game.utils.Choice;
import com.codingame.game.utils.model.ActionModel;
import com.codingame.game.utils.model.ElementModel;
import com.codingame.game.utils.model.StateModel;
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

    private boolean died = false;
    private boolean retry = false;

    public static Map<Integer, Choice> choices;
    static List<String> aviable = new ArrayList<>();
    static List<String> statesReached = new ArrayList<>();

    private  static Text textUp;
    private  static Text textIn;
    private  static Text textOut;

    private static Sprite man;
    private static Sprite goat;

    private static Sprite wolf;
    private static Sprite cabbage;
    private static Sprite boat;
    private static Sprite boat_border;

    private static Sprite retryText;

    private  static String request = "";
    private  static String response = "";

    private final String stringUp  = "Remaining turns : ";
    private final String stringIn  = "Action : ";
    private final String stringOut = "     --> ";

    public static Map<String, ElementModel> elementsById;
    private Map<String, ElementModel> elementsByLibelle;
    private Map<String, ElementModel> elements_start;
    public static Map<String, ActionModel> actionsById;
    private List<ActionModel> actions;

    public static Map<String, StateModel> statesById;

    public static ActionModel dieAndRetry;

    @Override
    public void init() {
        gameManager.setFrameDuration(500);

        choices = new HashMap<>();
        elementsById =  new HashMap<>();
        elementsByLibelle =  new HashMap<>();
        actions = new ArrayList<>();
        actionsById = new HashMap<>();
        statesById = new HashMap<>();

        dieAndRetry = new ActionModel("A_00", "DIE AND RETRY", null, null, null, null, null, null);

        retryText = graphicEntityModule.createSprite().setImage(Constants.RETRY_SPRITE)
                .setScale(0.2)
                .setAlpha(0)
                .setX(graphicEntityModule.getWorld().getWidth()/2)
                .setY(graphicEntityModule.getWorld().getHeight()/2)
                .setAnchor(.5)
                .setZIndex(10000);

        // general definitions
        Integer maxTurnsCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        remainingTurns = maxTurnsCount;
        readLineIndex++; // 1
        Integer elementsCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        readLineIndex++; //  2

        for (int i = 0; i < elementsCount; i++){
            //System.err.println(gameManager.getTestCaseInput().get(readLineIndex));
            String id = gameManager.getTestCaseInput().get(readLineIndex).split(" ")[0];
            String libelle = gameManager.getTestCaseInput().get(readLineIndex).split(" ")[1];
            int xPos = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex).split(" ")[2]);
            int yPos = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex).split(" ")[3]);
            int xRank = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex).split(" ")[4]);
            int yOffset = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex).split(" ")[5]);
            int statesCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex).split(" ")[6]);

            Set<StateModel> states = new HashSet<>();

            for (int j = 0; j < statesCount; j++){
                readLineIndex++; //  loop
                String idState = gameManager.getTestCaseInput().get(readLineIndex).split(" ")[0];
                String libelleState = gameManager.getTestCaseInput().get(readLineIndex).split(" ")[1];
                StateModel stateModel = new StateModel(idState, libelleState);
                states.add(stateModel);

                statesById.put(idState, stateModel);

            }

            ElementModel element = new ElementModel(libelle ,xPos, yPos, xRank, yOffset, states);
            elementsById.put(id, element);

            if (libelle != null){
                elementsByLibelle.put(libelle, element);

                // Draw sprites
                switch(libelle){
                    case "GOAT" :
                        goat = graphicEntityModule.createSprite().setImage(Constants.GOAT_SPRITE)
                                .setScale(0.2)
                                .setX(elementsByLibelle.get("GOAT").getX())
                                .setY(elementsByLibelle.get("GOAT").getY())
                                .setAnchor(.5)
                                .setZIndex(2000);
                        break;
                    case "WOLF" :
                        wolf = graphicEntityModule.createSprite().setImage(Constants.WOLF_SPRITE)
                                .setScale(0.2)
                                .setX(elementsByLibelle.get("WOLF").getX())
                                .setY(elementsByLibelle.get("WOLF").getY())
                                .setAnchor(.5)
                                .setZIndex(2000);
                        break;
                    case "MAN" :
                        man = graphicEntityModule.createSprite().setImage(Constants.MAN_SPRITE)
                                .setScale(0.5)
                                .setX(elementsByLibelle.get("MAN").getX())
                                .setY(elementsByLibelle.get("MAN").getY())
                                .setAnchor(.5)
                                .setZIndex(2000);
                        break;
                    case "CABBAGE" :
                        cabbage = graphicEntityModule.createSprite().setImage(Constants.CABBAGE_SPRITE)
                                .setScale(0.1)
                                .setX(elementsByLibelle.get("CABBAGE").getX())
                                .setY(elementsByLibelle.get("CABBAGE").getY())
                                .setAnchor(.5)
                                .setZIndex(2000);
                        break;
                    case "BOAT" :
                        boat = graphicEntityModule.createSprite().setImage(Constants.BOAT_SPRITE)
                                .setScale(0.2)
                                .setX(elementsByLibelle.get("BOAT").getX())
                                .setY(elementsByLibelle.get("BOAT").getY())
                                .setAnchor(.5)
                                .setZIndex(1000);
                        boat_border = graphicEntityModule.createSprite().setImage(Constants.BOAT_BORDER_SPRITE)
                                .setScale(0.2)
                                .setX(elementsByLibelle.get("BOAT").getX())
                                .setY(elementsByLibelle.get("BOAT").getY())
                                .setAnchor(.5)
                                .setZIndex(5000);
                        break;
                    default:

                }
            }
            readLineIndex++; //  loop
        }

        // Draw background
        graphicEntityModule.createSprite().setImage(Constants.RIVER_SPRITE);
        elements_start = new HashMap<>(elementsById);


        Integer actionsCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
        readLineIndex++;
        for (int a = 0; a < actionsCount; a++){
            String idAction = gameManager.getTestCaseInput().get(readLineIndex);
            //System.err.println("idAction : "  + idAction);
            readLineIndex++;
            String libelleAction = gameManager.getTestCaseInput().get(readLineIndex);
            //System.err.println("libelleAction : "  + libelleAction);
            readLineIndex++;
            Integer conditionsToDisplayCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
            readLineIndex++;
            List<String> condToDisp = new ArrayList<>();
            for (int c1 = 0; c1 < conditionsToDisplayCount; c1++){
                String condToDisplay = gameManager.getTestCaseInput().get(readLineIndex);
                condToDisp.add(condToDisplay);
                readLineIndex++;
            }

            Integer conditionsToOkCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
            readLineIndex++;
            List<String> condToOk = new ArrayList<>();
            for (int c1 = 0; c1 < conditionsToOkCount; c1++){
                String condToOk_ = gameManager.getTestCaseInput().get(readLineIndex);
                condToOk.add(condToOk_);
                readLineIndex++;
            }

            Integer conditionsToDieCount = Integer.parseInt(gameManager.getTestCaseInput().get(readLineIndex));
            readLineIndex++;
            List<String> condToDie = new ArrayList<>();
            for (int c1 = 0; c1 < conditionsToDieCount; c1++){
                String condToDie_ = gameManager.getTestCaseInput().get(readLineIndex);
                condToDie.add(condToDie_);
                readLineIndex++;
            }
            String element = gameManager.getTestCaseInput().get(readLineIndex);
            readLineIndex++;
            String stateToAdd = gameManager.getTestCaseInput().get(readLineIndex);
            readLineIndex++;
            String stateToRemove = gameManager.getTestCaseInput().get(readLineIndex);
            readLineIndex++;

            ActionModel actionModel = new ActionModel(idAction, libelleAction, condToDisp, condToOk, condToDie, stateToAdd.split(" ")[0], stateToRemove.split(" ")[0], element);
            actions.add(actionModel);
            actionsById.put(idAction, actionModel);

            if(stateToAdd.contains(" ")){
                String idState = stateToAdd.split(" ")[0];
                String libelleState = stateToAdd.split(" ")[1];
                StateModel stateModel = new StateModel(idState, libelleState);
                statesById.put(idState, stateModel);
            }
            if(stateToRemove.contains(" ")){
                String idState = stateToRemove.split(" ")[0];
                String libelleState = stateToRemove.split(" ")[1];
                StateModel stateModel = new StateModel(idState, libelleState);
                statesById.put(idState, stateModel);
            }
        }
    }

    @Override
    public void gameTurn(int turn) {

        gameManager.getPlayer().sendInputLine("" + remainingTurns);


        List<ActionModel> actionsPossibles = new ArrayList<>();

        if (died){
            actionsPossibles.add(dieAndRetry);
            died = false;
        }
        else {
            for (ActionModel action : actions){
                if (action.allOKForDisplay()){
                    actionsPossibles.add(action);
                }
            }
        }



        //System.err.println("Actions possibles = " + actionsPossibles.stream().map(ActionModel::getLibelle).collect(Collectors.joining("\n")));

        aviable = actionsPossibles.stream().map(ActionModel::getId).collect(Collectors.toList());
        gameManager.getPlayer().sendInputLine(aviable.stream().collect(Collectors.joining("#")));

       updateView();

        gameManager.getPlayer().execute();

        try {
            List<String> outputs = gameManager.getPlayer().getOutputs();

            String output = checkOutput(outputs);

            if (output == null){
                throw new TimeoutException();
            }

            System.err.println("Output = " + output + " / Died = " + died);

            if (!died && !outputs.get(0).equals("A_00")) {
                //System.err.println("Process Output = " + output);
                ActionModel actionOutput = actionsById.get(output);
                ElementModel elementOutput = elementsById.get(actionOutput.getElement());
                StateModel stateOutputToAdd = statesById.get(actionOutput.getStateToAdd());
                elementOutput.addState(stateOutputToAdd);
                elementOutput.removeState(statesById.get(actionOutput.getStateToRemove()));

                //System.err.println(elementOutput.getStates().stream().map(stateModel -> stateModel.getLibelle()).collect(Collectors.toList()));
                request = outputs.get(0);

            }
            else {
                request = outputs.get(0);

            }

            remainingTurns--;

            if (remainingTurns < 0){
                response = "No remaining turn";
                gameManager.loseGame("No remaining turn");
            }

            //updateStates();

           // Action action = Action.valueOf(output.toUpperCase());
           // checkInvalidAction(action);


        } catch (TimeoutException e) {
            gameManager.loseGame("Timeout!");
        }


    }

    public static void updateStates(String output){

        System.err.println("updateStates():: Request = " + output);

        ElementModel goatElement = elementsById.get("E_01");
        ElementModel wolfElement = elementsById.get("E_02");
        ElementModel cabbageElement = elementsById.get("E_03");
        ElementModel manElement = elementsById.get("E_04");
        ElementModel boatElement = elementsById.get("E_05");
        switch (output) {
            case "A_00":
                //reinit();
                break;
            case "A_01":
                goatElement.addState(statesById.get("S_05"));
                break;
            case "A_02":
                goatElement.removeState(statesById.get("S_05"));
                break;
            case "A_03":
                wolfElement.addState(statesById.get("S_05"));
                break;
            case "A_04":
                wolfElement.removeState(statesById.get("S_05"));
                break;
            case "A_05":
                cabbageElement.addState(statesById.get("S_05"));
                break;
            case "A_06":
                cabbageElement.removeState(statesById.get("S_05"));
                break;
            case "A_07":
                manElement.addState(statesById.get("S_05"));
                break;
            case "A_08":
                manElement.removeState(statesById.get("S_05"));
                break;
            case "A_09":
                if (manElement.getStates().contains(statesById.get("S_05"))) {
                    manElement.removeState(statesById.get("S_01"));
                    manElement.addState(statesById.get("S_03"));

                    if (goatElement.getStates().contains(statesById.get("S_05"))) {
                        goatElement.removeState(statesById.get("S_01"));
                        goatElement.addState(statesById.get("S_03"));
                    } else if (wolfElement.getStates().contains(statesById.get("S_05"))) {
                        wolfElement.removeState(statesById.get("S_01"));
                        wolfElement.addState(statesById.get("S_03"));
                    } else if (cabbageElement.getStates().contains(statesById.get("S_05"))) {
                        wolfElement.removeState(statesById.get("S_01"));
                        wolfElement.addState(statesById.get("S_03"));
                    }
                } else {
                    if (goatElement.getStates().contains(statesById.get("S_05"))) {

                    } else if (wolfElement.getStates().contains(statesById.get("S_05"))) {

                    } else if (cabbageElement.getStates().contains(statesById.get("S_05"))) {

                    }
                }

                break;
            case "A_10":

                if (manElement.getStates().contains(statesById.get("S_05"))) {
                    manElement.removeState(statesById.get("S_03"));
                    manElement.addState(statesById.get("S_01"));

                    if (goatElement.getStates().contains(statesById.get("S_05"))) {
                        goatElement.removeState(statesById.get("S_03"));
                        goatElement.addState(statesById.get("S_01"));
                    } else if (wolfElement.getStates().contains(statesById.get("S_05"))) {
                        wolfElement.removeState(statesById.get("S_03"));
                        wolfElement.addState(statesById.get("S_01"));
                    } else if (cabbageElement.getStates().contains(statesById.get("S_05"))) {
                        wolfElement.removeState(statesById.get("S_03"));
                        wolfElement.addState(statesById.get("S_01"));
                    }
                } else {

                    if (goatElement.getStates().contains(statesById.get("S_05"))) {

                    } else if (wolfElement.getStates().contains(statesById.get("S_05"))) {

                    } else if (cabbageElement.getStates().contains(statesById.get("S_05"))) {

                    }
                }

                break;
        }

    }

    public void updateView(){

        System.err.println("updateView():: Request = " + request + "  Died = " + died + "    Retry = " + retry);

            retryText.setAlpha(0, Curve.LINEAR)
                    .setScale(0.2, Curve.LINEAR);

            ElementModel goatElement = elementsById.get("E_01");
            ElementModel wolfElement = elementsById.get("E_02");
            ElementModel cabbageElement = elementsById.get("E_03");
            ElementModel manElement = elementsById.get("E_04");
            ElementModel boatElement = elementsById.get("E_05");
            switch (request) {
                case "A_00":
                    reinit();
                    break;
                case "A_01":
                    if (goatElement.containsState("S_01")) {
                        goat.setX(goatElement.getxPos() + 750, Curve.LINEAR);
                        goat.setY(goatElement.getyPos() + goatElement.getyOffset() - 50, Curve.LINEAR);

                    } else {
                        goat.setX(goatElement.getxPos() + 1250, Curve.LINEAR);
                        goat.setY(goatElement.getyPos() + goatElement.getyOffset() - 50, Curve.LINEAR);
                    }
                    break;
                case "A_02":
                    if (goatElement.containsState("S_01")) {
                        goat.setX(goatElement.getxPos() + goatElement.getxRank() * 100, Curve.LINEAR);
                        goat.setY(goatElement.getyPos() + goatElement.getyOffset(), Curve.LINEAR);

                    } else {
                        goat.setX(goatElement.getxPos() + 1500 + goatElement.getxRank() * 100, Curve.LINEAR);
                        goat.setY(goatElement.getyPos() + goatElement.getyOffset(), Curve.LINEAR);
                    }
                    break;
                case "A_03":
                    wolf.setImage(Constants.WOLF_CUT_SPRITE);
                    if (wolfElement.containsState("S_01")) {
                        wolf.setX(wolfElement.getxPos() + 750, Curve.LINEAR);
                        wolf.setY(wolfElement.getyPos() + wolfElement.getyOffset() - 50, Curve.LINEAR);
                    } else {
                        wolf.setX(wolfElement.getxPos() + 1250, Curve.LINEAR);
                        wolf.setY(wolfElement.getyPos() + wolfElement.getyOffset() - 50, Curve.LINEAR);
                    }
                    break;
                case "A_04":
                    wolf.setImage(Constants.WOLF_SPRITE);
                    if (wolfElement.containsState("S_01")) {
                        wolf.setX(wolfElement.getxPos() + wolfElement.getxRank() * 100, Curve.LINEAR);
                        wolf.setY(wolfElement.getyPos() + wolfElement.getyOffset(), Curve.LINEAR);
                    } else {
                        wolf.setX(wolfElement.getxPos() + 1500 + wolfElement.getxRank() * 100, Curve.LINEAR);
                        wolf.setY(wolfElement.getyPos() + wolfElement.getyOffset(), Curve.LINEAR);
                    }
                    break;
                case "A_05":
                    if (cabbageElement.containsState("S_01")) {
                        cabbage.setX(cabbageElement.getxPos() + 750, Curve.LINEAR);
                        cabbage.setY(cabbageElement.getyPos() + cabbageElement.getyOffset() - 50, Curve.LINEAR);
                    } else {
                        cabbage.setX(cabbageElement.getxPos() + 1250, Curve.LINEAR);
                        cabbage.setY(cabbageElement.getyPos() + cabbageElement.getyOffset() - 50, Curve.LINEAR);
                    }
                    break;
                case "A_06":
                    if (cabbageElement.containsState("S_01")) {
                        cabbage.setX(cabbageElement.getxPos() + cabbageElement.getxRank() * 100, Curve.LINEAR);
                        cabbage.setY(cabbageElement.getyPos() + cabbageElement.getyOffset(), Curve.LINEAR);
                    } else {
                        cabbage.setX(cabbageElement.getxPos() + 1500 + cabbageElement.getxRank() * 100, Curve.LINEAR);
                        cabbage.setY(cabbageElement.getyPos() + cabbageElement.getyOffset(), Curve.LINEAR);
                    }
                    break;
                case "A_07":
                    if (manElement.containsState("S_01")) {
                        man.setX(manElement.getxPos() + 650, Curve.LINEAR);
                        man.setY(manElement.getyPos() + manElement.getyOffset() - 50, Curve.LINEAR);
                    } else {
                        man.setX(manElement.getxPos() + 1150, Curve.LINEAR);
                        man.setY(manElement.getyPos() + manElement.getyOffset() - 50, Curve.LINEAR);
                    }
                    break;
                case "A_08":
                    if (manElement.containsState("S_01")) {
                        man.setX(manElement.getxPos() + manElement.getxRank() * 100, Curve.LINEAR);
                        man.setY(manElement.getyPos() + manElement.getyOffset(), Curve.LINEAR);
                    } else {
                        man.setX(manElement.getxPos() + 1500 + manElement.getxRank() * 100, Curve.LINEAR);
                        man.setY(manElement.getyPos() + manElement.getyOffset(), Curve.LINEAR);
                    }
                    break;
                case "A_09":

                    int full = 1200;
                    int semi = 1000;

                    if (manElement.getStates().contains(statesById.get("S_05"))) {
                        man.setX(manElement.getxPos() + full + 50, Curve.LINEAR);

                        boat.setX(boatElement.getxPos() + full, Curve.LINEAR);
                        boat.setY(boatElement.getyPos() + boatElement.getyOffset(), Curve.LINEAR);
                        boat_border.setX(boatElement.getxPos() + full, Curve.LINEAR);
                        boat_border.setY(boatElement.getyPos() + boatElement.getyOffset(), Curve.LINEAR);

                        if (goatElement.getStates().contains(statesById.get("S_05"))) {
                            goat.setX(goatElement.getxPos() + full + 50, Curve.LINEAR);
                            goat.setY(goatElement.getyPos() + goatElement.getyOffset() - 50, Curve.LINEAR);
                        } else if (wolfElement.getStates().contains(statesById.get("S_05"))) {
                            wolf.setX(wolfElement.getxPos() + full + 50, Curve.LINEAR);
                            wolf.setY(wolfElement.getyPos() + wolfElement.getyOffset() - 50, Curve.LINEAR);
                        } else if (cabbageElement.getStates().contains(statesById.get("S_05"))) {
                            cabbage.setX(cabbageElement.getxPos() + full + 50, Curve.LINEAR);
                            cabbage.setY(cabbageElement.getyPos() + cabbageElement.getyOffset() - 50, Curve.LINEAR);
                        }
                    } else {
                        boat.setX(boatElement.getxPos() + semi, Curve.LINEAR);
                        boat.setY(boatElement.getyPos() + boatElement.getyOffset(), Curve.LINEAR);
                        boat_border.setX(boatElement.getxPos() + semi, Curve.LINEAR);
                        boat_border.setY(boatElement.getyPos() + boatElement.getyOffset(), Curve.LINEAR);

                        if (goatElement.getStates().contains(statesById.get("S_05"))) {
                            goat.setX(goatElement.getxPos() + semi + 50, Curve.LINEAR);
                            goat.setY(goatElement.getyPos() + goatElement.getyOffset() - 50, Curve.LINEAR);
                        } else if (wolfElement.getStates().contains(statesById.get("S_05"))) {
                            wolf.setX(wolfElement.getxPos() + semi + 50, Curve.LINEAR);
                            wolf.setY(wolfElement.getyPos() + wolfElement.getyOffset() - 50, Curve.LINEAR);
                        } else if (cabbageElement.getStates().contains(statesById.get("S_05"))) {
                            cabbage.setX(cabbageElement.getxPos() + semi + 50, Curve.LINEAR);
                            cabbage.setY(cabbageElement.getyPos() + cabbageElement.getyOffset() - 50, Curve.LINEAR);
                        }
                    }

                    break;
                case "A_10":

                    int full_ = 700;
                    int semi_ = 1000;

                    if (manElement.getStates().contains(statesById.get("S_05"))) {
                        man.setX(manElement.getxPos() + full_ + 50, Curve.LINEAR);

                        boat.setX(boatElement.getxPos() + full_, Curve.LINEAR);
                        boat.setY(boatElement.getyPos() + boatElement.getyOffset(), Curve.LINEAR);
                        boat_border.setX(boatElement.getxPos() + full_, Curve.LINEAR);
                        boat_border.setY(boatElement.getyPos() + boatElement.getyOffset(), Curve.LINEAR);

                        if (goatElement.getStates().contains(statesById.get("S_05"))) {
                            goat.setX(goatElement.getxPos() + full_ + 50, Curve.LINEAR);
                            goat.setY(goatElement.getyPos() + goatElement.getyOffset() - 50, Curve.LINEAR);
                        } else if (wolfElement.getStates().contains(statesById.get("S_05"))) {
                            wolf.setX(wolfElement.getxPos() + full_ + 50, Curve.LINEAR);
                            wolf.setY(wolfElement.getyPos() + wolfElement.getyOffset() - 50, Curve.LINEAR);
                        } else if (cabbageElement.getStates().contains(statesById.get("S_05"))) {
                            cabbage.setX(cabbageElement.getxPos() + full_ + 50, Curve.LINEAR);
                            cabbage.setY(cabbageElement.getyPos() + cabbageElement.getyOffset() - 50, Curve.LINEAR);
                        }
                    } else {
                        boat.setX(boatElement.getxPos() + semi_, Curve.LINEAR);
                        boat.setY(boatElement.getyPos() + boatElement.getyOffset(), Curve.LINEAR);
                        boat_border.setX(boatElement.getxPos() + semi_, Curve.LINEAR);
                        boat_border.setY(boatElement.getyPos() + boatElement.getyOffset(), Curve.LINEAR);

                        if (goatElement.getStates().contains(statesById.get("S_05"))) {
                            goat.setX(goatElement.getxPos() + semi_ + 50, Curve.LINEAR);
                            goat.setY(goatElement.getyPos() + goatElement.getyOffset() - 50, Curve.LINEAR);
                        } else if (wolfElement.getStates().contains(statesById.get("S_05"))) {
                            wolf.setX(wolfElement.getxPos() + semi_ + 50, Curve.LINEAR);
                            wolf.setY(wolfElement.getyPos() + wolfElement.getyOffset() - 50, Curve.LINEAR);
                        } else if (cabbageElement.getStates().contains(statesById.get("S_05"))) {
                            cabbage.setX(cabbageElement.getxPos() + semi_ + 50, Curve.LINEAR);
                            cabbage.setY(cabbageElement.getyPos() + cabbageElement.getyOffset() - 50, Curve.LINEAR);
                        }
                    }

                    break;
            }
    }

    public void reinit(){
        retry = false;
        died = false;

        // reinit states
        elementsById = new HashMap<>(elements_start);

        retryText.setAlpha(0.9, Curve.LINEAR)
                .setScale(0.5, Curve.LINEAR);

        goat.setX(elementsByLibelle.get("GOAT").getX())
            .setY(elementsByLibelle.get("GOAT").getY());

        wolf.setX(elementsByLibelle.get("WOLF").getX())
            .setY(elementsByLibelle.get("WOLF").getY());

        cabbage.setX(elementsByLibelle.get("CABBAGE").getX())
               .setY(elementsByLibelle.get("CABBAGE").getY());

        man.setX(elementsByLibelle.get("MAN").getX())
           .setY(elementsByLibelle.get("MAN").getY());

        boat.setX(elementsByLibelle.get("BOAT").getX())
            .setY(elementsByLibelle.get("BOAT").getY());

        boat_border.setX(elementsByLibelle.get("BOAT").getX())
                   .setY(elementsByLibelle.get("BOAT").getY());
    }

    @Override
    public void onEnd() {
        //gameManager.putMetadata("eggs", String.valueOf(eggsCollected));
    }

    private String checkOutput(List<String> outputs) {
        //System.err.println(outputs);
        if (outputs.size() != 1) {
            gameManager.loseGame("You did not send 1 output in your turn.");
        }

        else {
            String output = outputs.get(0);
            System.err.println("Here aviable =" + aviable);
            System.err.println("Here output = " + output);
            if (!aviable.contains(output)) {
                gameManager
                        .loseGame(
                                String.format(
                                        "Expected output: %s but received %s",
                                        Arrays.asList(Constants.ACTIONS).stream().collect(Collectors.joining(" | ")),
                                        output
                                )
                        );
            }
            else if (output.equals("A_00")){
                System.err.println(output + " => Retry)");
                return output;
            }
            else if (actionsById.get(output).anyOKForDie()){
                System.err.println(output + " => Die)");
                died = true;
                return output;
            }
            else {
                System.err.println(output + " => Normal)");
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
