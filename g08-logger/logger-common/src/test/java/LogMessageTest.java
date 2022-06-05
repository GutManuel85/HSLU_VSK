import ch.hslu.vsk.logger.common.LogMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LogMessageTest {

    private LogMessage logMessage;

    private String logMessageText;
    private int logLevel;
    private Instant date;
    private int logID;
    private String loggerName;


    @BeforeEach
    void setUp() {
        this.logMessageText = "Test Message";
        this.logLevel = 10;
        this.date = Instant.now();
        this.logID = 1;
        this.loggerName = "test-logger";
        this.logMessage = new LogMessage(this.date, this.loggerName, this.logID, this.logLevel, this.logMessageText);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLogTime() {
        assertEquals(this.date, logMessage.getLogTime());
    }

    @Test
    void getLoggerName() {
        assertEquals(this.loggerName, logMessage.getLoggerName());
    }

    @Test
    void getLogID() {
        assertEquals(this.logID, logMessage.getLogID());
    }

    @Test
    void getLogLevel() {
        assertEquals(this.logLevel, logMessage.getLogLevel());
    }

    @Test
    void getMessageText() {
        assertEquals(this.logMessageText, logMessage.getMessageText());
    }
}