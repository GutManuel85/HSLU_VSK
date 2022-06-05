package ch.hslu.vsk.logger.viewer;

import javafx.beans.property.SimpleStringProperty;


/**
 * g08-logger
 * <p>
 *     Log class to handel a LogMessage.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class Log {
    private final SimpleStringProperty quelle;
    private final SimpleStringProperty level;
    private final SimpleStringProperty message;
    private final SimpleStringProperty timeErstellung;
    private final SimpleStringProperty timeServer;
    private final SimpleStringProperty logID;

    public Log(final String quelle, final String level, final String logID, final String message,
               final String timeErstellung, final String timeServer) {
        this.quelle = new SimpleStringProperty(quelle);
        this.level = new SimpleStringProperty(level);
        this.logID = new SimpleStringProperty(logID);
        this.message = new SimpleStringProperty(message);
        this.timeErstellung = new SimpleStringProperty(timeErstellung);
        this.timeServer = new SimpleStringProperty(timeServer);
    }

    public final String getQuelle() {
        return quelle.get();
    }

    public final void setQuelle(final String quelle) {
        this.quelle.set(quelle);
    }

    public final String getLevel() {
        return level.get();
    }

    public final void setLevel(final String level) {
        this.level.set(level);
    }

    public final String getMessage() {
        return message.get();
    }

    public final void setMessage(final String message) {
        this.message.set(message);
    }

    public final String getTimeErstellung() {
        return timeErstellung.get();
    }

    public final void setTimeErstellung(final String timeErstellung) {
        this.timeErstellung.set(timeErstellung);
    }

    public final String getTimeServer() {
        return timeServer.get();
    }

    public final void setTimeServer(final String timeServer) {
        this.timeServer.set(timeServer);
    }

    public final String getLogID() {
        return this.logID.get();
    }

    public final void setLogID(final String logID) {
        this.logID.set(logID);
    }

}

