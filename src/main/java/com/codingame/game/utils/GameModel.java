package com.codingame.game.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties({ "comment_01", "comment_02", "comment_03", "mask" })
class GameModel {

    private int maxTurns;
    private int choicesCount;
    private List<Choice> choices;

    public int getMaxTurns() {
        return maxTurns;
    }

    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public int getChoicesCount() {
        return choicesCount;
    }

    public void setChoicesCount(int choicesCount) {
        this.choicesCount = choicesCount;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "" + maxTurns + "\n" +
                choicesCount + "\n" +
                choices.stream().map(Choice::toString).collect(Collectors.joining("\n"));
    }
}
