package com.pddstudio.tinyiftt.server.utils;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Project : WetterSuite
 * Author : pddstudio
 * Year : 2016
 */
public class Logger {

    private static File LOG_FILE;

    private Logger() {}

    public static void init(String location) {
        LOG_FILE = new File(location);
    }

    public static void log(Class className, String message) {
        log(className.getSimpleName(), message);
    }

    public static void log(String logPrefix, String message) {
        String logMsg = getSystemTime() + "[" + logPrefix + "::/" + Log.DEBUG.getPrefix() + "] " + message;
        printLogMessage(logMsg);
    }

    public static void log(Object object, String message) {
        log(object, Log.DEBUG, message);
    }

    public static void log(Object object, Log logType, String message) {
        String logMsg = getSystemTime() + "[" + object.getClass().getSimpleName() + "::/" + logType.getPrefix() + "] " + message;
        printLogMessage(logMsg);
    }

    private static synchronized void printLogMessage(String logMessage) {
        System.out.println(logMessage);
    }

    private static String getSystemTime() {
        Calendar calendar = GregorianCalendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String newday;
        String newmnth;

        if(day < 10) {
            newday = "0" + day;
        } else {
            newday = "" + day;
        }

        int mnth = calendar.get(Calendar.MONTH);
        //cuz of stupid month -1 rule
        mnth++;

        if(mnth < 10) {
            newmnth = "0" + mnth;
        } else {
            newmnth = "" + mnth;
        }

        int year = calendar.get(Calendar.YEAR);
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        String secs;
        if(sec < 10) {
            secs = "0" + sec;
        } else {
            secs = "" + sec;
        }
        return "[" + newmnth + "-" + newday + "-" + year + "|" + hr + ":" + min + ":" +secs + "]";
    }

}
