package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LogServerIT {

    String logMessage;
    LogLevel logLevel;
    Instant date;
    int logID;
    String loggerID;
    LogServer logServer;
    int port;
    String persistorFile;

    @BeforeEach
    void setUp() {
        this.logMessage = "Test Message";
        this.logLevel = LogLevel.DEBUG;
        this.date = Instant.now();
        this.logID = 1;
        this.loggerID = "test-logger";
        this.port = 1144;
        this.persistorFile = "test.log";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testConstructor1() throws IOException {
        LogServer logServer = new LogServer(port);
        assertNotNull(logServer);
    }

    @Test
    void testConstructor2() throws IOException, AlreadyBoundException {
        StringPersistorFile stringPersistorFile = new StringPersistorFile();
        stringPersistorFile.setFile(new File(persistorFile));
        LogServer logServer = new LogServer(this.port + 1, stringPersistorFile);
        assertNotNull(logServer);
    }

    @Test
    void storeNewLogMessage() throws IOException, AlreadyBoundException {
        LogMessage message = new LogMessage(this.date, this.loggerID, this.logID, 10, this.logMessage);
        StringPersistorFile stringPersistorFile = new StringPersistorFile();
        File file = new File(this.persistorFile);
        stringPersistorFile.setFile(file);
        logServer = new LogServer(this.port + 2, stringPersistorFile);
        boolean result = logServer.storeNewLogMessage(message);
        assertTrue(result);
    }
}