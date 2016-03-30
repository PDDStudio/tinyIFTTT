package com.pddstudio.tinyifttt.server;

import com.pddstudio.tinyifttt.models.TinyAction;
import com.pddstudio.tinyifttt.server.utils.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class ActionExecutor {

    private final TinyAction tinyAction;

    protected ActionExecutor(TinyAction tinyAction) {
        this.tinyAction = tinyAction;
    }

    protected void execute() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(tinyAction.getActionExec()).redirectErrorStream(true);
            Process process = processBuilder.start();
            inheritIO(process.getErrorStream());
            inheritIO(process.getInputStream());
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static void inheritIO(final InputStream src) {
        new Thread(new Runnable() {
            public void run() {
                Scanner sc = new Scanner(src);
                while (sc.hasNextLine()) {
                    Logger.log(ActionExecutor.class, sc.nextLine());
                }
            }
        }).start();
    }

}
