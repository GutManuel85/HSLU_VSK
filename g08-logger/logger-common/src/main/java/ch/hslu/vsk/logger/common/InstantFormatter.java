package ch.hslu.vsk.logger.common;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * g08-logger
 * <p>
 *     A Instant formatter helper.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class InstantFormatter {
    public static Instant getInstant(final String formattedString) {
        return Instant.parse(formattedString);
    }
    public static String getString(final Instant instantToFormat) {
        return DateTimeFormatter.ISO_INSTANT.format(instantToFormat);
    }
}