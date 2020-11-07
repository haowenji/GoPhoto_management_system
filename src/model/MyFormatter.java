package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Extents the Formatter object to create a custom Formatter.
 * This Java doc is adapted and edited fromm Oracle, Class Formatter:
 * https://docs.oracle.com/javase/8/docs/api/java/util/logging/Formatter.html#getTail-java.util.logging.Handler-
 * This code was adapted from a post from Wayan on 20170213 to Kode Java
 * forum here:
 * https://kodejava.org/how-do-i-create-a-custom-logger-formatter/
 * A Formatter provides support for formatting LogRecords.
 *
 * @see GoGoPhotoSystem
 */
public class MyFormatter extends Formatter {

    /**
     * Creates a DateFormat to format the logger timestamp.
     */
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

    /**
     * Format the given log record and return the formatted string.
     *
     * @param record the log record to be formatted
     * @return the formatted log record
     */
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(10000);
        builder.append(df.format(new Date(record.getMillis()))).append(" ImageName: ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Returns the header string for a set of formatted records.
     *
     * @param h The target handler (can be null)
     * @return header string
     */
    public String getHead(Handler h) {
        return super.getHead(h);
    }

    /**
     * Returns the tail string for a set of formatted records.
     *
     * @param h The target handler (can be null)
     * @return tail string
     */
    public String getTail(Handler h) {
        return super.getTail(h);
    }
}
