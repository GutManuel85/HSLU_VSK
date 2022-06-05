package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.LogPersistor;
import ch.hslu.vsk.logger.common.PersistedLog;
import ch.hslu.vsk.logger.common.StringPersistorAdapter;
import ch.hslu.vsk.logger.common.ConfigFileReader;
import ch.hslu.vsk.logger.common.StandardLogMessageFormatter;
import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.TransmissionMessageHandler;
import ch.hslu.vsk.logger.common.RMIListener;
import ch.hslu.vsk.logger.common.RMIServer;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorFile;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * g08-logger
 * <p>
 * Logger Server to get Logger Messages.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class LogServer implements Runnable, RMIServer {

    private final ExecutorService handlerPool;

    private ArrayList<PersistedLog> logMessageStore;

    private LogPersistor logPersistor;

    private List<RMIListener> listeners;

    private Remote registryServer;

    private ServerSocket listen;

    public LogServer(final int port) throws IOException {
        this.logMessageStore = new ArrayList<>();
        this.listen = new ServerSocket(port);
        this.handlerPool = Executors.newCachedThreadPool();
        listeners = new ArrayList<>();
    }

    public LogServer(final int port, final StringPersistorFile stringPersistor) throws IOException {
        this(port);
        this.logPersistor = StringPersistorAdapter.create(new StandardLogMessageFormatter(), stringPersistor);
        this.readStringPersistorFile();
    }

    private void registerRMIRegistry() throws AlreadyBoundException, RemoteException {
        final Registry reg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
        this.registryServer = UnicastRemoteObject.exportObject(this, 0);
        reg.bind("logServer", this.registryServer);
    }


    @Override
    public void addListener(final RMIListener listener) {
        listeners.add(listener);
        for (PersistedLog persistedLog : logMessageStore) {
            try {
                listener.push(persistedLog);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void removeListener(final RMIListener listener) {
        listeners.remove(listener);
    }

    public void update(final PersistedLog message) {
        for (RMIListener listener : listeners) {
            try {
                listener.push(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    private static List<Object> readServerConfiguration(final String fileName) {
        Properties prop = null;
        try {
            prop = ConfigFileReader.readConfigurationFile(fileName);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        int port = 1142;
        ArrayList<Object> args = new ArrayList<>();
        if (prop != null) {
            if (prop.getProperty("PORT") != null) {
                port = Integer.parseInt(prop.getProperty("PORT"));
            }
        }
        args.add(port);
        return args;
    }

    public static void main(final String[] args) throws AlreadyBoundException, RemoteException {

        String fileName = "server.config";
        String persistorFile = "standard.log";

        if (args.length == 1) {
            fileName = ((String) args[0]);
        }

        List<Object> configurationArgument = readServerConfiguration(fileName);
        int port = (Integer) configurationArgument.get(0);
        new Thread(new LogServerRMIRegistry(), "RMI LogServer").start();
        File file = new File(persistorFile);
        StringPersistorFile stringPersistorFile = new StringPersistorFile();
        stringPersistorFile.setFile(file);
        LogServer server = null;
        try {
            server = new LogServer(port, stringPersistorFile);
        } catch (IOException e) {
            System.exit(-1);
        }
        System.out.println("Logger server started on port " + port);
        server.registerRMIRegistry();
        server.run();
    }

    synchronized boolean storeNewLogMessage(final LogMessage message) {
        this.addPersistedLogToStore(new PersistedLog(Instant.now(), message));
        logPersistor.save(message);
        return true;
    }

    private void addPersistedLogToStore(final PersistedLog persistedLog) {
        logMessageStore.add(persistedLog);
        this.update(persistedLog);
    }

    private void addAllPersistedLogToStore(final List<PersistedLog> persistedLogs) {
        for (PersistedLog persistedLog : persistedLogs) {
            this.addPersistedLogToStore(persistedLog);
        }

    }

    private void readStringPersistorFile() {
        List<PersistedLog> add = this.logPersistor.get(Integer.MAX_VALUE);
        this.addAllPersistedLogToStore(add);
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Socket client = this.listen.accept();
                final TransmissionMessageHandler transmissionMessageHandler =
                        new TransmissionMessageHandler(client.getInputStream(), client.getOutputStream());
                transmissionMessageHandler.addMessageType(new LogTransmissionMessageServer(this));
                handlerPool.execute(transmissionMessageHandler);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}