package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;

import java.net.ConnectException;
/**
 * g08-logger
 * <p>
 *     LoggerSetup to create a Logger. To Setup the Remote Params.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class LoggerSetup implements ch.hslu.vsk.logger.api.LoggerSetup {


    private String loggerName;
    private String serverIP;
    private int serverPort;
    private LogLevel minimumLevel = LogLevel.TRACE;


    @Override
    public void setMinimumLevel(final LogLevel minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    @Override
    public LogLevel getMinimumLevel() {
        return minimumLevel;
    }

    @Override
    public void setLoggerName(final String loggerName) {
        this.loggerName = loggerName;
    }

    @Override
    public String getLoggerName() {
        return loggerName;
    }

    @Override
    public void setServerIP(final String serverIP) {
        this.serverIP = serverIP;
    }

    @Override
    public String getServerIP() {
        return serverIP;
    }

    @Override
    public void setServerPort(final int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public ch.hslu.vsk.logger.api.Logger createLogger() {
        int connectionTimeout = 7;
        Logger logger = new Logger(loggerName, minimumLevel, serverIP, serverPort, connectionTimeout);
        try {
            logger.init();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        Thread t = new Thread(logger);
        t.start();
        return logger;
    }

    public ch.hslu.vsk.logger.api.Logger createLogger(final String loggerName) {
        this.loggerName = loggerName;
        return this.createLogger();
    }
}