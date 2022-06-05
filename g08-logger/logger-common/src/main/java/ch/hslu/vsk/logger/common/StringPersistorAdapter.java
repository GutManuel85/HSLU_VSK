package ch.hslu.vsk.logger.common;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * g08-logger
 * <p>
 *     A Adapter Class to implements the LogPersistor Interface.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class StringPersistorAdapter implements LogPersistor {

    private final StringPersistor stringPersistor;
    private final LogMessageFormatter formatter;

    private StringPersistorAdapter(final LogMessageFormatter formatter, final StringPersistor stringPersistor) {
        this.formatter = formatter;
        this.stringPersistor = stringPersistor;
    }


    public static StringPersistorAdapter create(final LogMessageFormatter formatter,
                                                final StringPersistor stringPersistor) {
        StringPersistorAdapter stringPersistorAdapter = new StringPersistorAdapter(formatter, stringPersistor);
        return stringPersistorAdapter;
    }


    @Override
    public void save(final LogMessage log) {
        this.stringPersistor.save(Instant.now(), this.formatter.format(log));
    }


    @Override
    public List<PersistedLog> get(final int count) {
        List<PersistedString> strings = this.stringPersistor.get(count);
        List<PersistedLog> result = new ArrayList<>();
        for (PersistedString string : strings) {
            result.add(new PersistedLog(string.getTimestamp(), this.formatter.parse(string.getPayload())));
        }
        return result;
    }
}