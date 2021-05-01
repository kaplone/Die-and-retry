package com.codingame.game;

import com.codingame.game.utils.ConfigConverter;
import org.junit.Ignore;
import org.junit.Test;

public class ConfigConverterTest {

    @Test
//    @Ignore
    public void testConverter(){
        String urlIn = "src/test/resources/inputs_format_02.json";
        String urlOut = "src/test/resources/inputs_format_Out_02.json";

        System.err.println(ConfigConverter.convertJsonToTestIn(urlIn, urlOut));
    }

}