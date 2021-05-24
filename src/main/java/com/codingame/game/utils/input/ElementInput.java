package com.codingame.game.utils.input;

import java.util.Set;
import java.util.stream.Collectors;

public class ElementInput {
    private int xPos;
    private int yPos;
    private int xRank;
    private int yOffset;
    private String id;
    private String libelle;
    private int statesCount;
    private Set<StateInput> states;
    private int finalStatesCount;
    private Set<StateInput> finalStates;

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxRank() {
        return xRank;
    }

    public void setxRank(int xRank) {
        this.xRank = xRank;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

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

    public Set<StateInput> getStates() {
        return states;
    }

    public void setStates(Set<StateInput> states) {
        this.states = states;
    }

    public int getStatesCount() {
        return statesCount;
    }

    public void setStatesCount(int statesCount) {
        this.statesCount = statesCount;
    }

    public int getFinalStatesCount() {
        return finalStatesCount;
    }

    public void setFinalStatesCount(int finalStatesCount) {
        this.finalStatesCount = finalStatesCount;
    }

    public Set<StateInput> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<StateInput> finalStates) {
        this.finalStates = finalStates;
    }

    @Override
    public String toString() {
        return id + " " +
               libelle + " " +
               xPos + " " +
               yPos + " " +
               xRank + " " +
               yOffset + " " +
               statesCount +
               states.stream().map(StateInput::toString).collect(Collectors.joining("\n", "\n", "\n")) +
               finalStatesCount +
               finalStates.stream().map(StateInput::toString).collect(Collectors.joining("\n", "\n", ""));
    }
}
