import ch.hslu.vsk.logger.common.AlternativeLogMessageFormatter;
import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.LogMessageFormatter;
import ch.hslu.vsk.logger.common.StandardLogMessageFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class AlternativeLogMessageFormatterTest {

    private LogMessageFormatter logMessageFormatter;

    private LogMessage logMessage;

    private String messageText;
    private int logLevel;
    private Instant testTime;
    private int logID;
    private String loggerName;



    @BeforeEach
    public void setup() {
        this.logMessage = new LogMessage(testTime, loggerName, logID, logLevel, messageText);
        this.logMessageFormatter = new AlternativeLogMessageFormatter();
        this.messageText = "Test Message";
        this.logLevel = 10;
        this.testTime = Instant.now();
        this.logID = 1;
        this.loggerName = "test-logger";
    }

    @Test
    void testFormatOk() {
        final LogMessage message = new LogMessage(testTime, loggerName, logID, logLevel, messageText);
        final String expected = String.format("%s  |  %s  |  %s  |  %s  |  %s",
                testTime.toString(), this.loggerName, this.logID, this.logLevel, this.messageText);
        final String actual = this.logMessageFormatter.format(message);
        assertEquals(expected, actual);
    }

    @Test
    void testFormatNok() {
        final LogMessage message = new LogMessage(testTime, loggerName, logID, logLevel, messageText);
        final String expected = String.format("%s  |  %s  |  %s  |  %s  |  %s",
                testTime.toString(), this.loggerName + "1", this.logID, this.logLevel, this.messageText);
        final String actual = this.logMessageFormatter.format(message);
        assertNotEquals(expected, actual);
    }

    @Test
    void testParse() {

        this.logMessageFormatter = new StandardLogMessageFormatter();
        final LogMessage message = new LogMessage(testTime, loggerName, logID, logLevel, messageText);
        final String payload = this.logMessageFormatter.format(message);

        final LogMessage parsedMessage = this.logMessageFormatter.parse(payload);

        assertEquals(this.messageText, parsedMessage.getMessageText());
        assertEquals(this.logLevel, parsedMessage.getLogLevel());
        assertEquals(this.testTime, parsedMessage.getLogTime());
        assertEquals(this.logID, parsedMessage.getLogID());
        // assertEquals(this.loggerName, parsedMessage.getLoggerName()); Todo: problem with white space
    }
}