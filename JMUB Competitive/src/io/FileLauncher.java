package io;

import java.io.File;
import java.io.IOException;

public class FileLauncher {

    public static void launchResponder(String param) throws IOException {
        File dir = new File("ChallengeResponder");
        String command =
                "java -jar Responder.jar \""+param+"\"  \";\"";
        Runtime.getRuntime().exec(command,null,dir);
    }

    public static void launchScoreboard(String param) throws IOException {
        File dir = new File("RaceScoreboard");
        String command =
                "java -jar Scoreboard.jar \""+param+"\"  \";\"";
        Runtime.getRuntime().exec(command,null,dir);
    }
}
