package org.bitstorm.util;

import ch.hslu.vsk.logger.api.Logger;
import org.bitstorm.gameoflife.StandaloneGameOfLife;

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Shows a text file in a dialog box. It has a vertical scrollbar and a close
 * button.
 *
 * @author Edwin Martin
 */
public final class TextFileDialog extends Dialog {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a TextFileDialog.
     *
     * @param parent parent frame
     * @param title window title
     * @param filename filename to show (from jar-file)
     * @param posX x position.
     * @param posY y position.
     */
    public TextFileDialog(Frame parent, String title, String filename, int posX, int posY) {
        super(parent, title);

        StandaloneGameOfLife.LOGGER.debug("New Dialog with Title: "+title);
        String manualText;
        Button okButton = new Button(" Close ");
        okButton.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        }
        );

        EasyFile file = new EasyFile(this.getClass().getResourceAsStream(filename));
        try {
            manualText = file.readText();
        } catch (IOException e) {
            manualText = "";
            StandaloneGameOfLife.LOGGER.error("Couldn't read File!", e);
        }

        TextArea manualTextArea = new TextArea(manualText, 20, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
        manualTextArea.setBackground(Color.white);	// improve readability
        manualTextArea.setForeground(Color.black);	// we don't want white text on a white background
        manualTextArea.setEditable(false);

        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);

        this.add("Center", manualTextArea);
        this.add("South", buttonPanel);
        this.pack();
        this.enableEvents(Event.WINDOW_DESTROY);
        this.setLocation(posX, posY);
        this.setVisible(true);
    }

    /**
     * Close dialog box.
     */
    private void close() {
        this.setVisible(true);
        this.dispose();
        StandaloneGameOfLife.LOGGER.debug("Dialog Closed!");
    }

    /**
     * Handle close window button.
     *
     * @see java.awt.Component#processEvent(java.awt.AWTEvent)
     */
    @Override
    public void processEvent(AWTEvent e) {
        if (e.getID() == Event.WINDOW_DESTROY) {
            close();
        }
    }
}
