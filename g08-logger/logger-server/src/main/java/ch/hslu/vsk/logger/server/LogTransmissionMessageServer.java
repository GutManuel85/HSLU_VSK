package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.InstantFormatter;
import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.AbstractTransmissionMessage;
import ch.hslu.vsk.logger.common.LogTransmissionMessage;

import java.time.Instant;

/**
 * g08-logger
 * <p>
 *     A Class to Transmission Messages on a Server.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class LogTransmissionMessageServer extends LogTransmissionMessage {

    private LogServer logServer;

    public LogTransmissionMessageServer(final LogServer server) {
        this.logServer = server;
    }

    @Override
    public boolean operate() {
        boolean success = false;
        Instant time = InstantFormatter.getInstant((String) getArg(0));
        String loggerName = (String) getArg(1);
        int logID = (int) getArg(2);
        int logLevel = (int) getArg(3);
        String messageText = (String) getArg(4);
        LogMessage logMessage = new LogMessage(time, loggerName, logID, logLevel, messageText);
        logServer.storeNewLogMessage(logMessage);
        success = true;
        return success;
    }

    @Override
    public AbstractTransmissionMessage newCopy() {
        return new LogTransmissionMessageServer(this.logServer);
    }

}