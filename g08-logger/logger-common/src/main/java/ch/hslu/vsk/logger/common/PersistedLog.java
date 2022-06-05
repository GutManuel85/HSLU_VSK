package ch.hslu.vsk.logger.common;

import java.io.Serializable;
import java.time.Instant;
/**
 * g08-logger
 * <p>
 *     A Persisted Log Class.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class PersistedLog implements Serializable {

    private LogMessage message;
    private Instant savestamp;


    public PersistedLog(final Instant savestamp, final LogMessage message) {
        this.message = message;
        this.savestamp = savestamp;
    }


    public LogMessage getMessage() {
        return message;
    }


    public Instant getSavestamp() {
        return savestamp;
    }
}