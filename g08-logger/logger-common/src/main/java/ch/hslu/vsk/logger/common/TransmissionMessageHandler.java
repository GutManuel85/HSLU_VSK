package ch.hslu.vsk.logger.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * g08-logger
 * <p>
 *     A Class to handle Transmission Messages.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class TransmissionMessageHandler implements Runnable {

    protected InputStream inputStream;
    protected OutputStream outputStream;
    private List<AbstractTransmissionMessage> handledMessages;

    public TransmissionMessageHandler() {
        handledMessages = new ArrayList<>();
    }

    public TransmissionMessageHandler(final InputStream inputStream, final OutputStream outputStream) {
        this();
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public final void addMessageType(final AbstractTransmissionMessage msgType) {
        handledMessages.add(msgType);
    }

    public final AbstractTransmissionMessage readMessage() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String messageType = dataInputStream.readUTF();
        AbstractTransmissionMessage message = buildMessage(messageType);
        if (message != null && message.readArgs(inputStream)) {
            return message;
        }
        return null;
    }

    public final void sendMessage(final AbstractTransmissionMessage message) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(message.getMessageType());
        message.writeArgs(outputStream);
    }

    @Override
    public void run() {
        boolean busy = true;
        while (busy) {
            AbstractTransmissionMessage message = null;
            try {
                message = readMessage();
            } catch (UTFDataFormatException e) {
                // e.printStackTrace();
            } catch (IOException e) {
                busy = false;
            }
            if (message != null) {
                message.operate();
            }
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final AbstractTransmissionMessage buildMessage(final String msgType) {
        for (AbstractTransmissionMessage msg : handledMessages) {
            if (msg.handles(msgType)) {
                return msg.newCopy();
            }
        }
        return null;
    }

    public List<AbstractTransmissionMessage> getHandledMessages() {
        return handledMessages;
    }


}