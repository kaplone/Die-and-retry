package com.codingame.game.utils.model;

import com.codingame.game.Referee;

import java.util.List;
import java.util.stream.Collectors;

public class ActionModel {
    private String id;
    private String libelle;
    private List<String> conditionsToDisplay;
    private List<String> conditionsToOk;
    private List<String> conditionsToDie;
    private String element;
    private String stateToAdd;
    private String stateToRemove;

    public ActionModel(String id, String libelle, List<String> conditionsToDisplay, List<String> conditionsToOk, List<String> conditionsToDie, String stateToAdd, String stateToRemove, String element) {
        this.id = id;
        this.libelle = libelle;
        this.conditionsToDisplay = conditionsToDisplay;
        this.conditionsToOk = conditionsToOk;
        this.conditionsToDie = conditionsToDie;
        this.element = element;
        this.stateToAdd = stateToAdd;
        this.stateToRemove = stateToRemove;
    }

    public boolean anyOKForDie(){
        //System.err.println("anyOKForDie :: " + conditionsToDie.size());
        boolean res = false;
        boolean cond = false;

        for (String s : conditionsToDie.stream().filter(c -> !c.equals("NONE")).collect(Collectors.toList())){
            //System.err.println("Loop ActionForDie = " + s);
            String booleanOperator = s.split("-")[0];
            String condition = s.split("-")[1];
            String element = s.split("-")[2];
            String state = s.split("-")[3];

            ElementModel elementModel = Referee.elementsById.get(element);

            switch (condition){
                case "OK" : cond = elementModel.containsState(state);
                    break;
                case "NOT" : cond = !elementModel.containsState(state);
                    break;
                case "SAMESTATE" : ElementModel elementModel1 = Referee.elementsById.get(state);
                    cond = elementModel.getStates().size() == elementModel1.getStates().size() && elementModel1.containsState(elementModel.getStates().iterator().next().getId());
                    break;
                case "CONTAINSSTATE" : ElementModel elementModel2 = Referee.elementsById.get(state);
                    System.err.println(elementModel.getStates().stream().map(e -> e.getId()).collect(Collectors.toList()));
                    System.err.println(elementModel2.getStates().stream().map(e -> e.getId()).collect(Collectors.toList()));
                    cond = elementModel.getStates().size() == elementModel2.getStates().size() && elementModel2.containsState(elementModel.getStates().iterator().next().getId());
                    break;
                case "NOTSAMESTATE" : ElementModel elementModel3 = Referee.elementsById.get(state);
                    cond = elementModel.getStates().size() == elementModel3.getStates().size() && !elementModel3.containsState(elementModel.getStates().iterator().next().getId());
                    break;
                case "NOTCONTAINSSTATE" : ElementModel elementModel4 = Referee.elementsById.get(state);
                    cond = elementModel.getStates().size() == elementModel4.getStates().size() && !elementModel4.containsState(elementModel.getStates().iterator().next().getId());
                    break;
            }

            if (cond){
                System.err.println("condition = " + condition + " Element = " + elementModel.getLibelle() + " State = " + state + "  =>  cond :: " + cond);
            }


            res = booleanOperator.equals("AND") ? res && cond : res || cond;
        }
        if (res){
            System.err.println("Die :: " + libelle);
        }


        return res;
    }

    public boolean allOKForOk(){

        return true;
    }

    public boolean allOKForDisplay(){
        boolean res = true;
        boolean cond = false;

        for (String s : conditionsToDisplay){
            //System.err.println(s);
            String booleanOperator = s.split("-")[0];
            String condition = s.split("-")[1];
            String element = s.split("-")[2];
            String state = s.split("-")[3];

            ElementModel elementModel = Referee.elementsById.get(element);
            if(element.equals("E_05")){
                //System.err.println("---> " + elementModel.getStates().stream().map(st -> st.getId()).collect(Collectors.joining(" // ")));
            }

            switch (condition){
                case "OK" : cond = elementModel.containsState(state);
                break;
                case "NOT" : cond = !elementModel.containsState(state);
                    break;
                case "SAMESTATE" : ElementModel elementModel2 = Referee.elementsById.get(element);
                        cond = elementModel.containsState(elementModel2.getStates().iterator().next().getId());
                        break;
                case "NOTSAMESTATE" : ElementModel elementModel3 = Referee.elementsById.get(element);
                    cond = !elementModel.containsState(elementModel3.getStates().iterator().next().getId());
                    break;
            }



            res = booleanOperator.equals("AND") ? res && cond : res || cond;
        }
        //System.err.println(libelle + " Res = " + res);
        return res;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateToAdd() {
        return stateToAdd;
    }

    public void setStateToAdd(String stateToAdd) {
        this.stateToAdd = stateToAdd;
    }

    public String getStateToRemove() {
        return stateToRemove;
    }

    public void setStateToRemove(String stateToRemove) {
        this.stateToRemove = stateToRemove;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
