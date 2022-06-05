package ch.hslu.vsk.logger.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LogServerRMIRegistryTest {

    LogServerRMIRegistry logServerRMIRegistry;

    @Test
    void testConstructor(){
        logServerRMIRegistry = new LogServerRMIRegistry();
        assertNotNull(logServerRMIRegistry);
    }
}