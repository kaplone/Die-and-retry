package com.codingame.game.utils.model;

import com.codingame.game.utils.input.StateInput;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ElementModel {
    private int xPos;
    private int yPos;
    private int xRank;
    private int yOffset;
    private String libelle;

    private Set<StateModel> states;

    public ElementModel(String libelle,  int xPos, int yPos, int xRank, int yOffset, Set<StateModel> states) {
        this.libelle = libelle;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xRank = xRank;
        this.yOffset = yOffset;
        this.states = new HashSet<>();
        this.states = states;

    }

    public int getX(){
        return xPos + xRank * 100;
    }

    public int getY(){
        return yPos + yOffset;
    }

    public void addState(StateModel s){
        if (s != null){
            states.add(s);
        }
    }

    public void removeState(StateModel s){
        if(s != null){
            System.err.println("To search for remove = " + s.getId());

            StateModel stateToremove = states.stream().filter(st -> st.getId().equals(s.getId())).findFirst().orElse(null);
            if (stateToremove != null){
                states.remove(stateToremove);
            }

        }

    }

    public void clearStates(){
        states.clear();
    }

    public boolean containsState(String s){
        return states.stream().map(a -> a.getId()).collect(Collectors.toList()).contains(s);
    }

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

    public Set<StateModel> getStates() {
        return states;
    }

    public void setStates(Set<StateModel> states) {
        this.states = states;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
