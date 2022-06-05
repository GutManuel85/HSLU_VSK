package org.bitstorm.gameoflife;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.common.ConfigFileReader;
import org.bitstorm.util.AboutDialog;
import org.bitstorm.util.TextFileDialog;

/**
 * The window with the applet. Extra is the menu bar.
 *
 * @author Edwin Martin
 */
public final class AppletFrame extends Frame {

    private static final long serialVersionUID = 1L;

    private final GameOfLife applet;

    /**
     * Constructor.
     *
     * @param title  title of window
     * @param applet applet to show
     */
    public AppletFrame(String title, StandaloneGameOfLife applet) {
        super(title);
        this.applet = applet;

        URL iconURL = this.getClass().getResource("icon.gif");
        Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
        this.setIconImage(icon);

        enableEvents(Event.WINDOW_DESTROY);

        MenuBar menubar = new MenuBar();
        Menu fileMenu = new Menu("File", true);
        MenuItem readMenuItem = new MenuItem("Open...");
        readMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        getStandaloneGameOfLife().getGameOfLifeGridIO().openShape();
                        getStandaloneGameOfLife().reset();
                    }
                }
        );
        MenuItem writeMenuItem = new MenuItem("Save...");
        writeMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        getStandaloneGameOfLife().getGameOfLifeGridIO().saveShape();
                    }
                }
        );
        MenuItem quitMenuItem = new MenuItem("Exit");
        quitMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );
        Menu helpMenu = new Menu("Help", true);
        MenuItem manualMenuItem = new MenuItem("Manual");
        manualMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        showManualDialog();
                    }
                }
        );
        MenuItem licenseMenuItem = new MenuItem("License");
        licenseMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        showLicenseDialog();
                    }
                }
        );
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        showAboutDialog();
                    }
                }
        );

        Menu levelMenu = new Menu("Level", true);
        MenuItem traceItem = new MenuItem("TRACE");
        traceItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        StandaloneGameOfLife.LOGGER.setMinimumLevel(LogLevel.TRACE);
                    }
                }
        );
        MenuItem debugItem = new MenuItem("DEBUG");
        debugItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        StandaloneGameOfLife.LOGGER.setMinimumLevel(LogLevel.DEBUG);
                    }
                }
        );
        MenuItem infoItem = new MenuItem("INFO");
        infoItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        StandaloneGameOfLife.LOGGER.setMinimumLevel(LogLevel.INFO);
                    }
                }
        );
        MenuItem warnItem = new MenuItem("WARN");
        warnItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        StandaloneGameOfLife.LOGGER.setMinimumLevel(LogLevel.WARN);
                    }
                }
        );
        MenuItem errorItem = new MenuItem("ERROR");
        errorItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        StandaloneGameOfLife.LOGGER.setMinimumLevel(LogLevel.ERROR);
                    }
                }
        );
        MenuItem fatalItem = new MenuItem("FATAL");
        fatalItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        StandaloneGameOfLife.LOGGER.setMinimumLevel(LogLevel.FATAL);
                    }
                }
        );
        MenuItem offItem = new MenuItem("OFF");
        offItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        StandaloneGameOfLife.LOGGER.setMinimumLevel(LogLevel.OFF);
                    }
                }
        );
        MenuItem configFileItem = new MenuItem("Read config file");
        configFileItem.addActionListener(
                new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        LogLevel minimumLogLevel = LogLevel.TRACE;
                        String level = null;
                        try {
                            level = ConfigFileReader.readConfigurationFile("logger.config").getProperty("MINIMUMLOGLEVEL");
                        }catch(IOException ioe){
                            System.out.println(ioe.getMessage());
                        }
                        if (Objects.equals(level, "TRACE")) {
                            minimumLogLevel = LogLevel.TRACE;
                        } else if (Objects.equals(level, "DEBUG")) {
                            minimumLogLevel = LogLevel.DEBUG;
                        } else if (Objects.equals(level, "INFO")) {
                            minimumLogLevel = LogLevel.INFO;
                        } else if (Objects.equals(level, "WARN")) {
                            minimumLogLevel = LogLevel.WARN;
                        } else if (Objects.equals(level, "ERROR")) {
                            minimumLogLevel = LogLevel.ERROR;
                        } else if (Objects.equals(level, "FATAL")) {
                            minimumLogLevel = LogLevel.FATAL;
                        } else if (Objects.equals(level, "OFF")) {
                            minimumLogLevel = LogLevel.OFF;
                        }
                        StandaloneGameOfLife.LOGGER.setMinimumLevel(minimumLogLevel);

                    }
                }
        );


        fileMenu.add(readMenuItem);
        fileMenu.add(writeMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(quitMenuItem);
        helpMenu.add(manualMenuItem);
        helpMenu.add(licenseMenuItem);
        helpMenu.add(aboutMenuItem);
        levelMenu.add(traceItem);
        levelMenu.add(debugItem);
        levelMenu.add(infoItem);
        levelMenu.add(warnItem);
        levelMenu.add(errorItem);
        levelMenu.add(fatalItem);
        levelMenu.add(offItem);
        levelMenu.add(configFileItem);
        menubar.add(fileMenu);
        menubar.add(helpMenu);
        menubar.add(levelMenu);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints appletContraints = new GridBagConstraints();
        setLayout(gridbag);
        appletContraints.fill = GridBagConstraints.BOTH;
        appletContraints.weightx = 1;
        appletContraints.weighty = 1;
        gridbag.setConstraints(applet, appletContraints);
        setMenuBar(menubar);
        setResizable(true);
        add(applet);
        Toolkit screen = getToolkit();
        Dimension screenSize = screen.getScreenSize();
        // Java in Windows opens windows in the upper left corner, which is ugly! Center instead.
        if (screenSize.width >= 640 && screenSize.height >= 480) {
            setLocation((screenSize.width - 550) / 2, (screenSize.height - 400) / 2);
        }
        applet.init(this);
        applet.start();
        pack();
        // Read shape after initialization
        applet.readShape();
        // Bring to front. Sometimes it stays behind other windows.
        setVisible(true);
        toFront();
    }

    /**
     * Process close window button.
     *
     * @see java.awt.Component#processEvent(java.awt.AWTEvent)
     */
    @Override
    public void processEvent(AWTEvent e) {
        if (e.getID() == Event.WINDOW_DESTROY) {
            System.exit(0);
        }
    }

    /**
     * Show about dialog.
     */
    private void showAboutDialog() {
        Properties properties = System.getProperties();
        String jvmProperties = "Java VM " + properties.getProperty("java.version") + " from " + properties.getProperty("java.vendor");
        Point p = getLocation();
        new AboutDialog(this, "About the Game of Life", new String[]{"Version 1.5 - Copyright 1996-2004 Edwin Martin", "http://www.bitstorm.org/gameoflife/", jvmProperties}, "about.jpg", p.x + 100, p.y + 60);
        StandaloneGameOfLife.LOGGER.info("Show about the Game of Life");
    }

    /**
     * Show manual.
     */
    private void showManualDialog() {
        Point p = getLocation();
        new TextFileDialog(this, "Game of Life Manual", "manual.txt", p.x + 60, p.y + 60);
        StandaloneGameOfLife.LOGGER.info("Show Manual the Game of Life");

    }

    /**
     * Show license.
     */
    private void showLicenseDialog() {
        Point p = getLocation();
        new TextFileDialog(this, "Game of Life License", "license.txt", p.x + 60, p.y + 60);
        StandaloneGameOfLife.LOGGER.info("Show License the Game of Life");
    }

    /**
     * Get StandaloneGameOfLife object.
     *
     * @return StandaloneGameOfLife
     */
    private StandaloneGameOfLife getStandaloneGameOfLife() {
        return (StandaloneGameOfLife) applet;
    }
}
