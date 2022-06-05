import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.PersistedLog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PersistedLogTest {

    PersistedLog persistedLog;
    Instant instant = Instant.now();
    LogMessage logMessage = new LogMessage(instant, "logger", 1, 10, "message");

    @BeforeEach
    void setUp() {
        persistedLog = new PersistedLog(instant, logMessage);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetMessage() {
        assertEquals(logMessage, persistedLog.getMessage());
    }

    @Test
    void testGetSavestamp() {
        assertEquals(instant, persistedLog.getSavestamp());
    }
}