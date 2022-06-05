package ch.hslu.vsk.logger.common;


import java.io.*;
import java.util.Objects;

/**
 * g08-logger
 * <p>
 *     A Log transmission Message Class.
 *     extens AbstractTransmissionMessage.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class LogTransmissionMessage extends AbstractTransmissionMessage {

    public LogTransmissionMessage() {
        super();
    }

    public LogTransmissionMessage(final LogMessage message) {
        this.addArg(InstantFormatter.getString(message.getLogTime()));
        this.addArg(message.getLoggerName());
        this.addArg(message.getLogID());
        this.addArg(message.getLogLevel());
        this.addArg(message.getMessageText());
    }

    @Override
    public final boolean readArgs(final InputStream inputStream) throws IOException {
        boolean success = false;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.addArg(dataInputStream.readUTF());
        this.addArg(dataInputStream.readUTF());
        this.addArg(dataInputStream.readInt());
        this.addArg(dataInputStream.readInt());
        this.addArg(dataInputStream.readUTF());
        if (Objects.equals(dataInputStream.readUTF(), this.getEndToken())) {
            success = true;
        }
        return success;
    }

    @Override
    public final boolean writeArgs(final OutputStream outputStream) throws IOException {
        boolean success = false;
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF((String) this.getArg(0));
        dataOutputStream.writeUTF((String) this.getArg(1));
        dataOutputStream.writeInt((int) this.getArg(2));
        dataOutputStream.writeInt((int) this.getArg(3));
        dataOutputStream.writeUTF((String) this.getArg(4));
        dataOutputStream.writeUTF(this.getEndToken());
        success = true;
        return success;
    }

    @Override
    public final void defineMessageType() {
        setMessageType("LOG");
    }

    @Override
    public final boolean handles(final String messageType) {
        return messageType.equals(this.getMessageType());
    }

    @Override
    public boolean operate() {
        throw new UnsupportedOperationException("Not implemented!");
    }

    @Override
    public AbstractTransmissionMessage newCopy() {
        return new LogTransmissionMessage();
    }
}