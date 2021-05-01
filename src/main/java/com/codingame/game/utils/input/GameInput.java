package com.codingame.game.utils.input;

import com.codingame.game.utils.input.ActionInput;
import com.codingame.game.utils.input.ElementInput;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.AccessibleObject;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties({ "comment_01", "comment_02", "comment_03", "mask" })
public class GameInput {

    private int maxTurns;
    private int elementsCount;
    private List<ElementInput> elements;
    private int actionsCount;
    private List<ActionInput> actions;

    public int getMaxTurns() {
        return maxTurns;
    }

    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public int getElementsCount() {
        return elementsCount;
    }

    public void setElementsCount(int elementsCount) {
        this.elementsCount = elementsCount;
    }

    public List<ElementInput> getElements() {
        return elements;
    }

    public void setElements(List<ElementInput> elements) {
        this.elements = elements;
    }

    public int getActionsCount() {
        return actionsCount;
    }

    public void setActionsCount(int actionsCount) {
        this.actionsCount = actionsCount;
    }

    public List<ActionInput> getActions() {
        return actions;
    }

    public void setActions(List<ActionInput> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "" + maxTurns + "\n" +
                elementsCount +
                elements.stream().map(ElementInput::toString).collect(Collectors.joining("\n", "\n", "\n")) +
                actionsCount + "\n" +
                actions.stream().map(ActionInput::toString).collect(Collectors.joining("\n", "", ""));
    }
}
