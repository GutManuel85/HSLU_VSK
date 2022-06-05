package ch.hslu.vsk.logger.common;

/**
 * g08-logger
 * <p>
 *     A Log Massage Formatter Interface.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public interface LogMessageFormatter {

    /**
     * Format a Log Massage into a String.
     * @param message
     * @return LogMessage in a String
     */
    public String format(LogMessage message);

    /**
     * Parse a formated LogMessage String into a LogMessage Object.
     * @param payload
     * @return LogMessage Object
     */
    public LogMessage parse(String payload);
}