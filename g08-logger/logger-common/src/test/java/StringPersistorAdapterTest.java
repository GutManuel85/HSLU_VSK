import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.StandardLogMessageFormatter;
import ch.hslu.vsk.logger.common.StringPersistorAdapter;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class StringPersistorAdapterTest {

    private StringPersistorAdapter stringPersistorAdapter;

    @BeforeEach
    void setUp() {
        stringPersistorAdapter = StringPersistorAdapter.create(new StandardLogMessageFormatter(),
                new StringPersistorFile());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        assertNotNull(stringPersistorAdapter);
    }

    @Test
    void save() {
        assertThrows(NullPointerException.class, () -> stringPersistorAdapter.save(
                new LogMessage(Instant.now(), "testLogger", 1, 1, "testmessage")));
    }

    @Test
    void get() {
        assertThrows(NullPointerException.class, () -> stringPersistorAdapter.get(1));
    }
}