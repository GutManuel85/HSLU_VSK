package ch.hslu.vsk.logger.common;

import java.util.List;

/**
 * g08-logger
 * <p>
 *     Log Persistent Interface for logging LogMessages.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public interface LogPersistor {

    /**
     * Saves a LogMassage persistent.
     * @param log
     */
    public void save(LogMessage log);

    /**
     * Get a List of PersistedLog on the number of count.
     * @param count how many PersistedLogs
     * @return List of PersistedLogs
     */
    public List<PersistedLog> get(final int count);


}