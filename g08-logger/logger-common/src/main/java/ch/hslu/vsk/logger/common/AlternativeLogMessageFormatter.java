package ch.hslu.vsk.logger.common;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * g08-logger
 * <p>
 * A Class to format Log Messages.
 * Implements LogMessageFormatter.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class AlternativeLogMessageFormatter implements LogMessageFormatter {


    @Override
    public String format(final LogMessage message) {
        final String payload = String.format("%s  |  %s  |  %s  |  %s  |  %s",
                InstantFormatter.getString(message.getLogTime()), message.getLoggerName(), message.getLogID(),
                message.getLogLevel(), message.getMessageText());
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
        final int logLevel = Integer.parseInt(matcher.group(4));
        final String messageText = matcher.group(5);
        final LogMessage parsedMessage = new LogMessage(logTime, loggerName, logID, logLevel, messageText);
        return parsedMessage;
    }
}