package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetup;
import ch.hslu.vsk.logger.api.LoggerSetupFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoggerSetupTest {

    private LoggerSetup loggerSetup;
    private final int serverPort = 1142;
    private final String serverName = "Hans";
    private final String serverIp = "localhost";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @BeforeEach
    void setUp() throws ClassNotFoundException {
        loggerSetup = LoggerSetupFactory.createLoggerSetup("ch.hslu.vsk.logger.component.LoggerSetup");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSetMinimumLevelINFO() {
        loggerSetup.setMinimumLevel(LogLevel.INFO);
        assertEquals(LogLevel.INFO, loggerSetup.getMinimumLevel());
    }

    @Test
    void testSetMinimumLevelERROR() {
        loggerSetup.setMinimumLevel(LogLevel.ERROR);
        assertEquals(LogLevel.ERROR, loggerSetup.getMinimumLevel());
    }

    @Test
    void testSetMinimumLevelNull() {
        loggerSetup.setMinimumLevel(null);
        assertNull(loggerSetup.getMinimumLevel());
    }

    @Test
    void testGetMinimumLevelDefault() {
        assertEquals(LogLevel.TRACE, loggerSetup.getMinimumLevel());
    }

    @Test
    void testGetMinimumLevelINFO() {
        loggerSetup.setMinimumLevel(LogLevel.INFO);
        assertEquals(LogLevel.INFO, loggerSetup.getMinimumLevel());
    }

    @Test
    void testGetMinimumLevelERROR() {
        loggerSetup.setMinimumLevel(LogLevel.ERROR);
        assertEquals(LogLevel.ERROR, loggerSetup.getMinimumLevel());
    }

    @Test
    void testSetLoggerName() {
        loggerSetup.setLoggerName(serverName);
        assertEquals(serverName, loggerSetup.getLoggerName());
    }

    @Test
    void testGetLoggerName() {
        loggerSetup.setLoggerName(serverName);
        assertEquals(serverName, loggerSetup.getLoggerName());
    }

    @Test
    void testSetServerIP() {
        loggerSetup.setServerIP(serverIp);
        assertEquals(serverIp, loggerSetup.getServerIP());
    }

    @Test
    void testGetServerIP() {
        loggerSetup.setServerIP(serverIp);
        assertEquals(serverIp, loggerSetup.getServerIP());
    }

    @Test
    void testSetServerPort() {
        loggerSetup.setServerPort(serverPort);
        assertEquals(serverPort, loggerSetup.getServerPort());
    }

    @Test
    void testGetServerPort() {
        loggerSetup.setServerPort(serverPort);
        assertEquals(serverPort, loggerSetup.getServerPort());
    }

    @Test
    void testCreateLoggerMinimumLevelDefault() {
        ch.hslu.vsk.logger.api.Logger logger = loggerSetup.createLogger();
        assertEquals(LogLevel.TRACE, logger.getMinimumLevel());
    }

    @Test
    void testCreateLoggerCatchConnectException() {
        assertDoesNotThrow(() -> loggerSetup.createLogger());
    }

    @Test
    void testCreateLoggerMinimumLevel() {
        loggerSetup.setMinimumLevel(LogLevel.ERROR);
        ch.hslu.vsk.logger.api.Logger logger = loggerSetup.createLogger();
        assertEquals(LogLevel.ERROR, logger.getMinimumLevel());
    }
}