package com.codingame.game.utils;

import com.codingame.game.utils.input.GameInput;
import com.codingame.game.utils.input.Input;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigConverter {

    static public String convertJsonToTestIn(String jsonUrlIn, String jsonUrlOut){


        Path pathIn = Paths.get(jsonUrlIn);
        File fileIn = pathIn.toFile();
        Path pathOut = Paths.get(jsonUrlOut);
        File fileOut = pathOut.toFile();
        String read = "";

        ObjectMapper objectMapper = new ObjectMapper();
        GameInput gameModel = null;
        try {
            gameModel = objectMapper.readValue(fileIn, GameInput.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Input input = new Input();
        input.getTitle().put(1, "test_01_01");
        input.getTitle().put(2, "test_02_02");
        input.setTestIn(gameModel.toString());
        input.setTest(true);
        input.setValidator(true);
        try{
            objectMapper.writeValue(fileOut, input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameModel.toString();
    }
}







