package com.pddstudio.tinyiftt.server.utils;

/**
 * Project : WetterSuite
 * Author : pddstudio
 * Year : 2016
 */
public enum Log {
    DEBUG("D"),
    INFO("I"),
    WARNING("W"),
    ERROR("E");

    final String log;

    Log(String log) {
        this.log = log;
    }

    public String getPrefix() {
        return log;
    }

}
