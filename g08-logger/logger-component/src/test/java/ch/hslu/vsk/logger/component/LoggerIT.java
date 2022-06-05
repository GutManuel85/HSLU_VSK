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

class LoggerIT {

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
    void testInitIT() {
        assertDoesNotThrow(() -> logger.init());
    }


    @Test
    void testLogIT() throws ConnectException {
        logger.init();
        logger.log(LogLevel.INFO, logMessage);
        assertEquals(1, logger.getLogID());
        logger.log(LogLevel.INFO, logMessage);
        assertEquals(2, logger.getLogID());
    }


    @Test
    void testTraceLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.TRACE);
        logger.trace(logMessage);
        assertEquals(1, logger.getLogID());
    }

    @Test
    void testTraceNotLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.OFF);
        logger.trace(logMessage);
        assertEquals(0, logger.getLogID());
    }

    @Test
    void testDebugLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.TRACE);
        logger.debug(logMessage);
        assertEquals(1, logger.getLogID());
    }

    @Test
    void testDebugNotLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.OFF);
        logger.debug(logMessage);
        assertEquals(0, logger.getLogID());
    }

    @Test
    void testInfoLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.TRACE);
        logger.info(logMessage);
        assertEquals(1, logger.getLogID());
    }

    @Test
    void testInfoNotLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.OFF);
        logger.info(logMessage);
        assertEquals(0, logger.getLogID());
    }

    @Test
    void testWarnLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.TRACE);
        logger.warn(logMessage);
        assertEquals(1, logger.getLogID());
    }

    @Test
    void testWarnNotLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.OFF);
        logger.warn(logMessage);
        assertEquals(0, logger.getLogID());
    }

    @Test
    void testErrorLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.TRACE);
        logger.error(logMessage, new Exception());
        assertEquals(1, logger.getLogID());
    }

    @Test
    void testErrorNotLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.OFF);
        logger.error(logMessage, new Exception());
        assertEquals(0, logger.getLogID());
    }


    @Test
    void testFatalLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.TRACE);
        logger.fatal(logMessage, new Exception());
        assertEquals(1, logger.getLogID());
    }

    @Test
    void testFatalNotLoggedIT() throws ConnectException {
        logger.init();
        logger.setMinimumLevel(LogLevel.OFF);
        logger.fatal(logMessage, new Exception());
        assertEquals(0, logger.getLogID());
    }

    @Test
    void testRun() {
    }
}