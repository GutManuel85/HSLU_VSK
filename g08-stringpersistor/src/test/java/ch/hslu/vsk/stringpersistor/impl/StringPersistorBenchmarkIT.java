package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class StringPersistorBenchmarkIT {

    private static final int TIMEOUT_IN_MILLISECONDS = 200;
    private static final int NUMBER_OF_WRITE_MESSAGES = 1000;
    private static final int NUMBER_OF_READ_MESSAGES = 100;
    private static final int MESSAGE_LENGTH = 1000;

    private StringPersistor persistor;
    private List<String> messages;
    private List<PersistedString> retrieved;
    private File file;
    private String testFileStringName = "StringPersistorBenchmarkIT.log";

    @BeforeEach
    public void setUp() {
        this.file = new File(testFileStringName);
        this.persistor = new StringPersistorFile();
        this.persistor.setFile(file);
        this.messages = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_WRITE_MESSAGES; i++) {
            this.messages.add(this.randomString(MESSAGE_LENGTH));
        }
    }

    @Test
    public void testWrite() {
        for (int i = 0; i < NUMBER_OF_WRITE_MESSAGES; i++) {
            this.persistor.save(Instant.now(), this.messages.get(i));
        }
    }

    @Test
    @Timeout(value = TIMEOUT_IN_MILLISECONDS, unit = TimeUnit.MILLISECONDS)
    public void testRead() throws IOException {
        this.retrieved = this.persistor.get(NUMBER_OF_READ_MESSAGES);
        Files.deleteIfExists(Path.of(testFileStringName));
    }

    private String randomString(final int messageLength) {
        final Random random = new Random(System.currentTimeMillis());
        final StringBuilder str = new StringBuilder();
        for (int i = 0; i < messageLength; i++) {
            final char c = (char) (random.nextInt('z' - 'a' + 1) + 'a');
            str.append(c);
        }
        return str.toString();
    }

}