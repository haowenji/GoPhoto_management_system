package model;

/**
 * <h1>Generate an object to represent a log message with time and content</h1>
 * The LogMessage program implements an application that
 * generate a LogMessage object with time and content
 * (which is the current name of a operated image
 * accorded into log history(LogHis.txt))of this LogMessage
 *
 * @see LoggerManager
 */
public class LogMessage {

    /**
     * The time of this LogMessage
     */
    private String time;

    /**
     * The content of this LogMessage
     */
    private String content;

    /**
     * Creates a new LogMessage with given
     * time and content of this LogMessage.
     *
     * @param time    the time of this LogMessage
     * @param content the content of this log message, which
     *                is the the current name of a operated image
     */
    LogMessage(String time, String content) {
        this.time = time;
        this.content = content;
    }

    /**
     * Converts this LogMessage into a readable string
     *
     * @return A string with the time and the content
     * of this LogMessage
     */
    @Override
    public String toString() {
        return time + content;
    }

    /**
     * Returns content of this log message with the changes of the names
     * of a {@link Image}
     * @return the content of this log message
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the time when the log message is generated which is also the time when an image changes its name.
     * @return the time of this log message when a image name is changed
     */
    public String getTime() {
        return time;
    }
}
