package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.common.*;
import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorFile;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.time.Instant;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * g08-logger
 * <p>
 *    A Logger to log some Messages. Need to use the LoggerSetup to create a Logger.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class Logger implements ch.hslu.vsk.logger.api.Logger, Runnable {

    private String loggerName;

    private LogLevel minimumLogLevel;

    private String serverIP;

    private int serverPort;

    private int connectionTimeout;

    private Socket socket;

    private int logID;

    private LinkedBlockingQueue<LogMessage> senderCache;

    private File cacheFile;

    private LogPersistor logPersistor;

    private boolean isCaching;

    private boolean isConnected;

    private TransmissionMessageHandler transmissionMessageHandler;

    private boolean allowServerless;


    Logger(final String loggerName, final LogLevel minimumLevel, final String serverIP, final int serverPort) {
        this.isCaching = true;
        this.isConnected = false;
        this.loggerName = loggerName;
        this.minimumLogLevel = minimumLevel;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.logID = 0;
        this.connectionTimeout = 30;
        this.senderCache = new LinkedBlockingQueue<>();
        this.socket = null;
        this.allowServerless = false;
    }

    Logger(final String loggerName, final LogLevel minimumLevel, final String serverIP, final int serverPort,
           final int connectionTimeout) {
        this(loggerName, minimumLevel, serverIP, serverPort);
        this.connectionTimeout = connectionTimeout;
    }

    public void init() throws ConnectException {
        configurationFileReader();
        openSocket();
        if (!isConnected) {
            if (!allowServerless) {
                throw new ConnectException("Could not Initialize Logger. Connection Exception on first Connection!");
            } else {
                switchToServerNotAvailableMode();
            }
        }

    }

    private void configurationFileReader() {
        String configFileName = "advancedLogger.config";
        Properties prop = null;
        try {
            prop = ConfigFileReader.readConfigurationFile(configFileName);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        if (prop != null && prop.getProperty("TIMEOUT") != null) {
            connectionTimeout = Integer.parseInt(prop.getProperty("TIMEOUT"));
        }
        if (prop != null && prop.getProperty("ALLOW_SERVERLESS") != null) {
            if (Objects.equals(prop.getProperty("ALLOW_SERVERLESS").toUpperCase(), "TRUE")) {
                allowServerless = true;
            }
        }
    }

    private void switchToServerAvailableMode() {
        if (this.logPersistor != null) {
            for (PersistedLog persistedLog : this.logPersistor.get(Integer.MAX_VALUE)) {
                senderCache.add(persistedLog.getMessage());
            }
        }
        this.isCaching = false;
    }

    private void switchToServerNotAvailableMode() {
        createCacheFile();
        for (LogMessage log : senderCache) {
            logPersistor.save(log);
        }
        senderCache.clear();
    }

    private void createCacheFile() {
        try {
            this.cacheFile = File.createTempFile("cache", ".log");
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringPersistorFile stringPersistor = new StringPersistorFile();
        stringPersistor.setFile(this.cacheFile);
        this.logPersistor = StringPersistorAdapter.create(new StandardLogMessageFormatter(), stringPersistor);
    }

    private void openSocket() {
        try {
            if (this.socket == null) {
                this.socket = new Socket(this.serverIP, this.serverPort);
                System.out.print("Connected to logger server " + this.serverIP + ":"
                        + this.serverPort +  " / ");
            }
            this.transmissionMessageHandler = new TransmissionMessageHandler(socket.getInputStream(),
                    socket.getOutputStream());
            this.isConnected = true;
            switchToServerAvailableMode();
        } catch (IOException e) {
            this.isConnected = false;
        }
    }

    private void sendMessageFromCache() throws IOException {
        if (!senderCache.isEmpty()) {
            LogMessage logMessage = senderCache.peek();
            LogTransmissionMessage message = new LogTransmissionMessage(logMessage);
            this.transmissionMessageHandler.sendMessage(message);
            senderCache.poll();
        }
    }

    public void log(final LogLevel logLevel, final String messageText) {
        if (logLevel.getLogValue() >= this.minimumLogLevel.getLogValue()) {
            LogMessage logMessage = new LogMessage(Instant.now(), this.loggerName, logID, logLevel.getLogValue(),
                    messageText);
            if (isCaching) {
                this.logPersistor.save(logMessage);
            } else {
                this.senderCache.add(logMessage);
            }
            this.logID++;
        }
    }


    @Override
    public void setMinimumLevel(final LogLevel logLevel) {
        this.minimumLogLevel = logLevel;
    }

    @Override
    public LogLevel getMinimumLevel() {
        return minimumLogLevel;
    }

    @Override
    public void trace(final String message) {
        log(LogLevel.TRACE, message);
    }

    @Override
    public void debug(final String message) {
        log(LogLevel.DEBUG, message);
    }

    @Override
    public void info(final String message) {
        log(LogLevel.INFO, message);
    }

    @Override
    public void warn(final String message) {
        log(LogLevel.WARN, message);
    }

    @Override
    public void error(final String message, final Throwable throwable) {
        log(LogLevel.ERROR, message + ", " + throwable.getMessage());
    }

    @Override
    public void fatal(final String message, final Throwable throwable) {
        log(LogLevel.FATAL, message + ", " + throwable.getMessage());
    }


    private void disconnect() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                isCaching = true;
                isConnected = false;
                socket = null;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            while (transmissionMessageHandler != null && isConnected) {
                isCaching = false;
                try {
                    sendMessageFromCache();
                } catch (UTFDataFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    disconnect();
                    switchToServerNotAvailableMode();
                    break;
                }
            }
            try {
                Thread.sleep(connectionTimeout * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            openSocket();
        }
    }

    public String getLoggerName() {
        return loggerName;
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getLogID() {
        return logID;
    }

    public boolean isCaching() {
        return isCaching;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean isAllowServerless() {
        return allowServerless;
    }
}
