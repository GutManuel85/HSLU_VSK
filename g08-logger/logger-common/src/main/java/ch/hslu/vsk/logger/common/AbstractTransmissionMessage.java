package ch.hslu.vsk.logger.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * g08-logger
 * <p>
 *     A abstract Class for Transmission Messages.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public abstract class AbstractTransmissionMessage {

    private String messageType;
    private final List<Object> argList = new ArrayList<>();
    private String endToken = "END_TOKEN";

    public AbstractTransmissionMessage() {
        this.defineMessageType();
    }

    public final void addArg(final Object arg) {
        argList.add(arg);
    }

    public final String getMessageType() {
        return messageType;
    }

    public final void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

    public final void setEndToken(final String endToken) {
        this.endToken = endToken;
    }

    public final String getEndToken() {
        return endToken;
    }

    public final Object getArg(final int index) {
        Object arg = null;
        if (index < argList.size()) {
            arg = argList.get(index);
        }
        return arg;
    }

    public final List<Object> getArgList() {
        return this.argList;
    }

    public abstract boolean readArgs(final InputStream inputStream) throws IOException;

    public abstract boolean writeArgs(final OutputStream outputStream) throws IOException;

    public abstract boolean operate();

    public abstract boolean handles(String messageType);

    public abstract AbstractTransmissionMessage newCopy();

    protected abstract void defineMessageType();

}