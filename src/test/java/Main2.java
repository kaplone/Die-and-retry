import com.codingame.gameengine.runner.SoloGameRunner;

public class Main2 {
    public static void main(String[] args) {
        SoloGameRunner gameRunner = new SoloGameRunner();

        // Sets the player
        gameRunner.setAgent(Solution_die_and_retry.class);

        // Sets a test case
        gameRunner.setTestCase("config/inputs_format_Out_02.json");

        gameRunner.start();
    }
}
