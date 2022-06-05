package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    private Logger logger;
    private final String loggerName = "LoggerName";
    private final LogLevel logLevel = LogLevel.DEBUG;
    private final String serverIP = "localhost";
    private final int serverPort = 1142;
    private final int connectionTimeout = 30;
    private final String logMessage = "logMessage";

    @BeforeEach
    void setUp() {
        logger = new Logger(loggerName, logLevel, serverIP, serverPort, connectionTimeout);
    }

    @AfterEach
    void tearDown() {
        logger = null;
    }

    @Test
    void testConstructor1() {
        logger = new Logger(loggerName, logLevel, serverIP, serverPort);
        assertNotNull(logger);
    }

    @Test
    void testConstructor2() {
        logger = new Logger(loggerName, logLevel, serverIP, serverPort, connectionTimeout);
        assertNotNull(logger);
    }

    @Test
    void testSetMinimumLevel() {
        logger.setMinimumLevel(LogLevel.ERROR);
        assertEquals(LogLevel.ERROR, logger.getMinimumLevel());
    }

    @Test
    void testGetMinimumLevel() {
        logger.setMinimumLevel(LogLevel.ERROR);
        assertEquals(LogLevel.ERROR, logger.getMinimumLevel());
    }
}