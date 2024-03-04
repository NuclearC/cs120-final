package game.experimental.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Logger {
    

    private static final String LOG_FILE_DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    private static final String LOG_MESSAGE_DATE_FORMAT = "HH-mm-ss";

    private static Logger instance = null;
    
    private FileWriter outputFile;

    private Logger() {
        try {
            System.out.println(getFilename());
            this.outputFile = new FileWriter("./" + getFilename());
        } catch (IOException e) {
            System.out.println("Failed to open a file for writing!");
        }
    }

    private String getFilename() {
        // return "game-" +  (new SimpleDateFormat(LOG_FILE_DATE_FORMAT)).format(new Date()) + ".log";
        return "game-debug.log";
    }

    private String getTimePrefix() {
        return "[" + (new SimpleDateFormat(LOG_MESSAGE_DATE_FORMAT)).format(new Date()) + "]";
    }

    private void writeMessage(String message) {
        try {
            this.outputFile.write(getTimePrefix() + " " + message + "\n");
        } catch (IOException e) {
            System.out.println("Failed to write to file!");
        }
    }

    public static void log(String message) {
        getSingleton().writeMessage(message);
    }

    public static void error(String message) {
        getSingleton().writeMessage("error: " + message);
        System.out.println("[E] " + message);
    }
    
    public static Logger getSingleton() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }



}
