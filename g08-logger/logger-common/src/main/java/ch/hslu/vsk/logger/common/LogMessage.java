package ch.hslu.vsk.logger.common;

import java.io.Serializable;
import java.time.Instant;

/**
 * g08-logger
 * <p>
 * A Symple Log Message Class. You can use with the Logger.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class LogMessage implements Serializable {


    private Instant logTime;

    private String loggerName;

    private int logID;

    private int logLevel;

    private String messageText;


    public LogMessage(final Instant logTime, final String loggerName, final int logID, final int logLevel,
                      final String messageText) {
        this.logTime = logTime;
        this.loggerName = loggerName;
        this.logID = logID;
        this.logLevel = logLevel;
        this.messageText = messageText;
    }


    public Instant getLogTime() {
        return this.logTime;
    }

    public String getLoggerName() {
        return this.loggerName;
    }

    public int getLogID() {
        return this.logID;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public String getMessageText() {
        return this.messageText;
    }
}