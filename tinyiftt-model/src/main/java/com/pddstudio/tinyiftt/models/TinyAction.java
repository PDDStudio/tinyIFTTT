package com.pddstudio.tinyiftt.models;

/**
 * This Class was created by Patrick J
 * on 29.03.16. For more Details and Licensing
 * have a look at the README.md
 */
public class TinyAction {

    private int actionIdentifier;
    private String actionTitle;
    private String actionDescription;
    private String[] actionExec;

    public TinyAction() {}

    public int getActionIdentifier() {
        return actionIdentifier;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public String[] getActionExec() {
        return actionExec;
    }

}
