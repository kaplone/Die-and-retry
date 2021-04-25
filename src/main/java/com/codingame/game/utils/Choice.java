package com.codingame.game.utils;

import com.codingame.game.Referee;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Choice {
    private int id;
    private String libelle;
    private String giveState;
    private List<String> needStates;
    private List<String> possibilities;
    private String back;
    private Boolean oneTime;

    public Choice(){

    }

    public Choice(int id, String libelle, List<String> possibilities, Boolean oneTime, String giveState, List<String> needStates, String back) {
        this.id = id;
        this.libelle = libelle;
        this.possibilities = possibilities;
        this.giveState = giveState;
        this.needStates = needStates;
        this.oneTime = oneTime;
        this.back = back;
    }

    public List<String> getPossibilities() {
        return possibilities.stream().map(p -> p.startsWith("$") ? Referee.choices.get(Integer.parseInt(p.split("\\$")[1])).getLibelle() : p).collect(Collectors.toList());
    }

    public void setPossibilities(String[] possibilities) {
        this.possibilities = Arrays.asList(possibilities);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getOneTime() {
        return oneTime;
    }

    public void setOneTime(Boolean oneTime) {
        this.oneTime = oneTime;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String toPlayer() {
        return possibilities.stream().map(c -> c.startsWith("$") ? Referee.choices.get(Integer.parseInt(c.split("\\$")[1])).getLibelle() : c).collect(Collectors.joining("#"));
    }

    public String getGiveState() {
        return giveState;
    }

    public void setGiveState(String giveState) {
        this.giveState = giveState;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public List<String> getNeedStates() {
        return needStates;
    }

    public void setNeedStates(List<String> needStates) {
        this.needStates = needStates;
    }

    @Override
    public String toString() {
        return "" + id +
                "_" + libelle +
                "_" + String.join("#", possibilities) +
                "_" + back +
                "_" + oneTime +
                "_" + giveState +
                "_" + String.join("#", needStates)
                ;
    }
}
