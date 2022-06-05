package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringPersistorFileTest {

    StringPersistorFile stringPersistorFile;
    File file;
    String testFileString = "unitTest.log";
    String payLoad = "payLoad";
    Instant instant;

    @BeforeEach
    void setUp() {
        this.stringPersistorFile = new StringPersistorFile();
        this.file = new File(testFileString);
        instant = Instant.now();
    }

    @AfterEach
    void tearDown() throws IOException {
       Files.deleteIfExists(Path.of(testFileString));
    }

    @Test
    void testConstructor() {
        assertNotNull(this.stringPersistorFile);
    }

    @Test
    void testSetFile() {
        assertDoesNotThrow(() -> this.stringPersistorFile.setFile(file));
    }

    @Test
    void testSaveTimestampNull() {
        assertThrows(IllegalArgumentException.class, () -> this.stringPersistorFile.save(null, payLoad));
    }

    @Test
    void testSavePayloadNull() {
        assertThrows(IllegalArgumentException.class, () -> this.stringPersistorFile.save(instant, null));
    }

    @Test
    void testSave() {
        this.stringPersistorFile.setFile(new File(testFileString));
        assertDoesNotThrow(() -> this.stringPersistorFile.save(instant, payLoad));
    }

    @Test
    void testGet(){
        this.stringPersistorFile.setFile(new File(testFileString));
        this.stringPersistorFile.save(instant,payLoad);
        PersistedString persistedString = new PersistedString(instant, payLoad);
        List<PersistedString> persistedStrings = new ArrayList<>();
        persistedStrings.add(persistedString);
        assertEquals(persistedStrings, this.stringPersistorFile.get(1));
    }
}