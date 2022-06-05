package ch.hslu.vsk.logger.viewer;


import ch.hslu.vsk.logger.common.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * g08-logger
 * <p>
 *     Shows Logs from a server. Connection per RMI
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public final class LogViewer extends Application implements RMIListener {

    private final TableView<Log> table = new TableView();
    private final ObservableList<Log> data = FXCollections.observableArrayList();

    private Remote remote;

    private String registryHost = "localhost";

    public static void main(final String[] args) {
        launch(args);
    }

    // Adds an entry to the tableview
    public final void add(final String timeErstellung, final String timeServer, final String quelle,
                          final String level, final String logID, final String message) {
        // 0 = add entry at the beginning
        data.add(0, new Log(timeErstellung, timeServer, quelle, level, logID, message));
    }

    @Override
    public void start(final Stage stage) throws RemoteException, NotBoundException {
        final Registry reg = LocateRegistry.getRegistry(this.registryHost, Registry.REGISTRY_PORT);
        final RMIServer server = (RMIServer) reg.lookup("logServer");
        this.remote = UnicastRemoteObject.exportObject(this, 0);

        server.addListener((RMIListener) this.remote);

        Scene scene = new Scene(new Group());
        stage.setTitle("Logger Viewer");
        stage.setWidth(1100);
        stage.setHeight(700);

        final Label label = new Label("Logs");
        label.setFont(new Font("Arial", 20));

        initTable();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private final void initTable() {
        TableColumn timeServerCol = new TableColumn("Eingang Server");
        timeServerCol.setMinWidth(210);
        timeServerCol.setSortable(false);
        timeServerCol.setCellValueFactory(new PropertyValueFactory<>("timeServer"));

        TableColumn timeErstellungCol = new TableColumn("Erstellungszeit");
        timeErstellungCol.setMinWidth(210);
        timeErstellungCol.setSortable(false);
        timeErstellungCol.setCellValueFactory(new PropertyValueFactory<>("timeErstellung"));

        TableColumn quelleCol = new TableColumn("Quelle");
        quelleCol.setMinWidth(100);
        quelleCol.setSortable(false);
        quelleCol.setCellValueFactory(new PropertyValueFactory<>("quelle"));

        TableColumn levelCol = new TableColumn("Level");
        levelCol.setMinWidth(80);
        levelCol.setSortable(false);
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn idCol = new TableColumn("LogID");
        idCol.setMinWidth(80);
        idCol.setSortable(false);
        idCol.setCellValueFactory(new PropertyValueFactory<>("logID"));

        TableColumn messageCol = new TableColumn("Message");
        messageCol.setMinWidth(380);
        messageCol.setSortable(false);
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));

        table.setItems(data);
        table.setEditable(false);
        table.setSelectionModel(null);
        table.setMinWidth(1060);
        table.setMinHeight(600);
        table.getColumns().addAll(timeServerCol, timeErstellungCol, quelleCol, levelCol, idCol, messageCol);
    }

    @Override
    public void push(final PersistedLog persistedLog) throws RemoteException {
        LogMessage message = persistedLog.getMessage();
        String serverStamp = InstantFormatter.getString(persistedLog.getSavestamp());
        this.add(
                String.valueOf(message.getLoggerName()),
                getLogLevelString(message.getLogLevel()),
                Integer.toString(message.getLogID()),
                message.getMessageText(),
                message.getLogTime().toString(),
                serverStamp
        );
    }
    public String getLogLevelString(final int logLevelNumber) {
        switch (logLevelNumber) {
            case 10:
                return "TRACE";
            case 20:
                return "DEBUG";
            case 30:
                return "INFO";
            case 40:
                return "WARN";
            case 50:
                return "ERROR";
            case 60:
                return "FATAL";
            default:
                return "OFF";
        }
    }
}

