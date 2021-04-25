package com.codingame.game.utils;

import com.codingame.game.Referee;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Choice {
    private int id;
    private String libelle;
    private List<String> possibilities;
    private Boolean oneTime;

    public Choice(){

    }

    public Choice(int id, String libelle, List<String> possibilities, Boolean oneTime) {
        this.id = id;
        this.libelle = libelle;
        this.possibilities = possibilities;
        this.oneTime = oneTime;
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

    @Override
    public String toString() {
        return "" + id +
                "_" + libelle +
                "_" + possibilities.stream().collect(Collectors.joining("#")) +
                "_" + oneTime
                ;
    }
}
