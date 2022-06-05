package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetup;
import ch.hslu.vsk.logger.api.LoggerSetupFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class LoggerSetupIT {

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
    void testCreateLoggerIT() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        loggerSetup.setMinimumLevel(LogLevel.INFO);
        loggerSetup.setServerPort(serverPort);
        loggerSetup.setServerIP(serverIp);
        Logger logger = loggerSetup.createLogger();
        assertEquals("Connected to logger server " + this.serverIp + ":"
                + this.serverPort + " / ", outContent.toString());
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}