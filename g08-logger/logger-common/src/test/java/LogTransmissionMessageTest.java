import ch.hslu.vsk.logger.common.AbstractTransmissionMessage;
import ch.hslu.vsk.logger.common.LogTransmissionMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogTransmissionMessageTest {

    private LogTransmissionMessage logTransmissionMessage;

    @BeforeEach
    void setUp() {
         logTransmissionMessage = new LogTransmissionMessage();
    }

    @AfterEach
    void tearDown() {

    }

    @Disabled
    void testReadArgs() {
        //Tested in system tests
    }

    @Disabled
    void testWriteArgs() {
        //Tested in system tests
    }

    @Test
    void testDefineMessageType() {
        logTransmissionMessage.defineMessageType();
        assertEquals("LOG", logTransmissionMessage.getMessageType());

    }

    @Test
    void testHandlesTrue() {
        logTransmissionMessage.defineMessageType();
        assertTrue(logTransmissionMessage.handles("LOG"));
    }

    @Test
    void testHandlesFalse() {
        logTransmissionMessage.defineMessageType();
        assertFalse(logTransmissionMessage.handles("NotLog"));
    }

    @Test
    void operate() {
        assertThrows(UnsupportedOperationException.class, () -> logTransmissionMessage.operate());
    }

    @Test
    void newCopy() {
        AbstractTransmissionMessage logTransmissionMessage1 = logTransmissionMessage;
        AbstractTransmissionMessage logTransmissionMessage2 = logTransmissionMessage1.newCopy();
        assertNotEquals(logTransmissionMessage1.hashCode(), logTransmissionMessage2.hashCode());
    }

}