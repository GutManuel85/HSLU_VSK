import ch.hslu.vsk.logger.common.InstantFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class InstantFormatterTest {

    private String testStringDateTime;
    private Instant testInstant;

    @BeforeEach
    void setUp() {
        this.testStringDateTime = "2020-11-11T10:00:00Z";
        this.testInstant = Instant.parse(testStringDateTime);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstant() {
        Assertions.assertEquals(this.testInstant, InstantFormatter.getInstant(testStringDateTime));

    }

    @Test
    void getString() {
        assertEquals(this.testStringDateTime, InstantFormatter.getString(testInstant));
    }
}