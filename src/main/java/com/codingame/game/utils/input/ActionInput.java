package com.codingame.game.utils.input;

import java.util.List;
import java.util.stream.Collectors;

public class ActionInput {
    private String id;
    private String libelle;
    private int conditionsToDisplayCount;
    private List<String> conditionsToDisplay;
    private int conditionsToOkCount;
    private List<String> conditionsToOk;
    private int conditionsToDieCount;
    private List<String> conditionsToDie;
    private String element;
    private StateInput stateToAdd;
    private StateInput stateToRemove;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<String> getConditionsToOk() {
        return conditionsToOk;
    }

    public void setConditionsToOk(List<String> conditionsToOk) {
        this.conditionsToOk = conditionsToOk;
    }

    public List<String> getConditionsToDie() {
        return conditionsToDie;
    }

    public void setConditionsToDie(List<String> conditionsToDie) {
        this.conditionsToDie = conditionsToDie;
    }

    public int getConditionsToOkCount() {
        return conditionsToOkCount;
    }

    public void setConditionsToOkCount(int conditionsToOkCount) {
        this.conditionsToOkCount = conditionsToOkCount;
    }

    public int getConditionsToDieCount() {
        return conditionsToDieCount;
    }

    public void setConditionsToDieCount(int conditionsToDieCount) {
        this.conditionsToDieCount = conditionsToDieCount;
    }

    public int getConditionsToDisplayCount() {
        return conditionsToDisplayCount;
    }

    public void setConditionsToDisplayCount(int conditionsToDisplayCount) {
        this.conditionsToDisplayCount = conditionsToDisplayCount;
    }

    public List<String> getConditionsToDisplay() {
        return conditionsToDisplay;
    }

    public void setConditionsToDisplay(List<String> conditionsToDisplay) {
        this.conditionsToDisplay = conditionsToDisplay;
    }

    public StateInput getStateToAdd() {
        return stateToAdd;
    }

    public void setStateToAdd(StateInput stateToAdd) {
        this.stateToAdd = stateToAdd;
    }

    public StateInput getStateToRemove() {
        return stateToRemove;
    }

    public void setStateToRemove(StateInput stateToRemove) {
        this.stateToRemove = stateToRemove;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return  id + "\n" +
                libelle + "\n" +
                conditionsToDisplayCount + "\n" +
                conditionsToDisplay.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining("\n", "", "\n")) +
                conditionsToOkCount + "\n" +
                conditionsToOk.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining("\n", "", "\n")) +
                conditionsToDieCount + "\n" +
                conditionsToDie.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining("\n", "", "\n")) +
                element + "\n" +
                stateToAdd + "\n" +
                stateToRemove;
    }
}
