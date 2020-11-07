package model;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Set up logger to track logging history and read the logging history into an ArrayList of LogMessage
 * The LoggerManager program implements an application that set up logger from Logging Class to generate
 * a text file(LogHis.txt) and track the logging history when changing name of the image. Also it read the
 * LogHis.txt into the program is a format of a ArrayList of LogMessage.
 * (LogMessage is an object with time and content of each message of the LogHis.txt)
 *
 * @see LogMessage
 * @see Image
 */
public class LoggerManager {

    /**
     * Reads the log history into an ArrayList of LogMessage(an object with attributes of time and content
     * of each log message in the log history) if there is a log history text file(LogHis.txt) exist. Otherwise it
     * returns a empty ArrayList
     *
     * @param path the path of the logging history text file
     * @return An ArrayList of LogMessage with attributes time and content of each log message in the logging
     * history text file when the file exists. Otherwise it returns an empty ArrayList.
     * @throws ClassNotFoundException indicates that reading log messages fails
     * @throws IOException indicates that reading log messages fails
     */
    public static ArrayList<LogMessage> ReadLogMessages(Logger logger, String path) throws
            ClassNotFoundException, IOException {

        BufferedReader log = null;
        String line;
        ArrayList<LogMessage> logMessages = new ArrayList<>();
        try {
            log = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException f) {
            logger.log(Level.SEVERE, "This file is not found.");
        }
        try {
            assert log != null;
            line = log.readLine();
            while (line != null) {
                String[] parts = line.split(" ImageName:");
                String name = parts[1];
                String time = parts[0];
                LogMessage message = new LogMessage(time, name);
                logMessages.add(message);
                line = log.readLine();
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }

        return logMessages;
    }
}

