package ch.hslu.vsk.logger.common;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * g08-logger
 * <p>
 *     A Class to format Log Messages.
 *     Implements LogMessageFormatter.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class StandardLogMessageFormatter implements LogMessageFormatter {


    @Override
    public String format(final LogMessage message) {
        final String payload = String.format("%s  |  %s  |  %s  |  %s  |  %s",
                InstantFormatter.getString(message.getLogTime()), message.getLoggerName(), message.getLogID(),
                getLogLevelString(message.getLogLevel()), message.getMessageText());
        return payload;
    }


    @Override
    public LogMessage parse(final String payload) {
        final Pattern pattern = Pattern.compile("^(.*)\\s\\s\\|\\s\\s(.*)\\s\\|\\s\\s(.*)"
                + "\\s\\s\\|\\s\\s(.*)\\s\\s\\|\\s\\s(.*)$");
        final Matcher matcher = pattern.matcher(payload);

        if (!matcher.matches() || matcher.groupCount() < 5) {
            return null;
        }
        final Instant logTime = InstantFormatter.getInstant(matcher.group(1));
        final String loggerName = matcher.group(2);
        final int logID = Integer.parseInt(matcher.group(3));
        final int logLevel = getLogLevelInt(matcher.group(4));
        final String messageText = matcher.group(5);
        final LogMessage parsedMessage = new LogMessage(logTime, loggerName, logID, logLevel, messageText);
        return parsedMessage;
    }

    public String getLogLevelString(final int logLevelNumber) {
        switch (logLevelNumber) {
            case 10:
                return "TRACE";
            case 20:
                return "DEBUG";
            case 30:
                return "INFO";
            case 40:
                return "WARN";
            case 50:
                return "ERROR";
            case 60:
                return "FATAL";
            default:
                return "OFF";
        }
    }

    public int getLogLevelInt(final String logLevelString) {
        switch (logLevelString) {
            case "TRACE":
                return 10;
            case "DEBUG":
                return 20;
            case "INFO":
                return 30;
            case "WARN":
                return 40;
            case "ERROR":
                return 50;
            case "FATAL":
                return 60;
            default:
                return 70;
        }
    }
}