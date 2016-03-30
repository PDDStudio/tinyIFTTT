package com.pddstudio.tinyifttt.server;

import com.pddstudio.tinyifttt.models.TinyAction;

import java.io.IOException;

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
            ProcessBuilder processBuilder = new ProcessBuilder(tinyAction.getActionExec());
            processBuilder.start();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
