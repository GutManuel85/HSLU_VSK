import ch.hslu.vsk.logger.viewer.Log;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {
    private Log log;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        log = new Log("Quelle", "Level", "ID", "Message", "TimeErstellung", "TimeServer");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getQuelle() {
        assertEquals("Quelle", log.getQuelle());
    }

    @org.junit.jupiter.api.Test
    void setQuelle() {
        log.setQuelle("neueQuelle");
        assertEquals("neueQuelle", log.getQuelle());
    }

    @org.junit.jupiter.api.Test
    void getLevel() {
        assertEquals("Level", log.getLevel());
    }

    @org.junit.jupiter.api.Test
    void setLevel() {
        log.setLevel("neuesLevel");
        assertEquals("neuesLevel", log.getLevel());

    }

    @org.junit.jupiter.api.Test
    void getMessage() {
        assertEquals("Message", log.getMessage());
    }

    @org.junit.jupiter.api.Test
    void setMessage() {
        log.setMessage("neueMessage");
        assertEquals("neueMessage", log.getMessage());
    }

    @org.junit.jupiter.api.Test
    void getTimeErstellung() {
        assertEquals("TimeErstellung", log.getTimeErstellung());
    }

    @org.junit.jupiter.api.Test
    void setTimeErstellung() {
        log.setTimeErstellung("NeueTime");
        assertEquals("NeueTime", log.getTimeErstellung());
    }

    @org.junit.jupiter.api.Test
    void getTimeServer() {
        assertEquals("TimeServer", log.getTimeServer());
    }

    @org.junit.jupiter.api.Test
    void setTimeServer() {
        log.setTimeServer("NeueTime");
        assertEquals("NeueTime", log.getTimeServer());
    }

    @org.junit.jupiter.api.Test
    void getLogID() {
        assertEquals("ID", log.getLogID());
    }

    @org.junit.jupiter.api.Test
    void setLogID() {
        log.setLogID("NeueID");
        assertEquals("NeueID", log.getLogID());
    }

}