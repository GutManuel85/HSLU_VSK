import ch.hslu.vsk.logger.common.AbstractTransmissionMessage;
import ch.hslu.vsk.logger.common.LogTransmissionMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTransmissionMessageTest {

    private AbstractTransmissionMessage abstractTransmissionMessage;

    @BeforeEach
    void setUp() {
        abstractTransmissionMessage  = new LogTransmissionMessage();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testAddArgAndGetArg() {
        String argument0 = "argument0";
        int argument1 = 1;
        abstractTransmissionMessage.addArg(argument0);
        abstractTransmissionMessage.addArg(argument1);
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(abstractTransmissionMessage.getArg(0));
        arrayList.add(abstractTransmissionMessage.getArg(1));
        assertEquals(argument0, arrayList.get(0));
        assertEquals(argument1, arrayList.get(1));
    }

    @Test
    void testGetMessageType() {
        assertEquals("LOG", abstractTransmissionMessage.getMessageType());

    }

    @Test
    void testSetMessageType() {
        abstractTransmissionMessage.setMessageType("Abstract");
        assertEquals("Abstract", abstractTransmissionMessage.getMessageType());
    }

    @Test
    void tSetEndToken() {
        abstractTransmissionMessage.setEndToken("End");
        assertEquals("End", abstractTransmissionMessage.getEndToken());
    }

    @Test
    void testGetEndToken() {
        assertEquals("END_TOKEN", abstractTransmissionMessage.getEndToken());
    }

    @Test
    void testGetArgList() {
        String argument0 = "argument0";
        int argument1 = 1;
        abstractTransmissionMessage.addArg(argument0);
        abstractTransmissionMessage.addArg(argument1);
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(abstractTransmissionMessage.getArg(0));
        arrayList.add(abstractTransmissionMessage.getArg(1));
        List<Object> args = abstractTransmissionMessage.getArgList();
        assertEquals(arrayList.get(0), args.get(0));
        assertEquals(arrayList.get(1), args.get(1));
    }
}