package dev.vladimir.cfemain.loggerbot;

import java.util.Timer;
import java.util.TimerTask;

public class Logger {
    private static final LoggerBot loggerBot = new LoggerBot("6208014440:AAFNXlWH_9RGOsZInkBTGOvFWV1qb-BTZhk");
    private static final StringBuilder intermediateBuilder = new StringBuilder();
    private static final StringBuilder logBuilder = new StringBuilder();
    static {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                logLine();
            }
        }, 0, 100);
    }

    private Logger() {}

    public static void logInQueue(String message) {
        if(message.isEmpty()) return;

        if(message.length() < 4000) {
            loggerBot.log(message);
        } else loggerBot.log("message has length more than 4000 characters, ignored");
    }

    public static LoggerBot getLoggerBot() {
        return loggerBot;
    }

    public static void addInQueue(char b) {
        intermediateBuilder.append(b);
        
        if(b == '\n') {
            String str = intermediateBuilder.toString();
            if (!str.startsWith("\tat org") && !str.startsWith("\tat java")
                    && !str.startsWith("\tat feign") && !str.isEmpty()) {
                logBuilder.append(str);
            }

            intermediateBuilder.setLength(0);
        }
    }

    private static void logLine() {
        if(logBuilder.isEmpty()) return;

        Logger.logInQueue(logBuilder.toString());
        logBuilder.setLength(0);
    }

    public static void log(String message) {
        loggerBot.log(message);
    }
}
