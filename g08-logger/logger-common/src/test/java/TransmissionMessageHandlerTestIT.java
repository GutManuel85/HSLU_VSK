import ch.hslu.vsk.logger.common.AbstractTransmissionMessage;
import ch.hslu.vsk.logger.common.LogTransmissionMessage;
import ch.hslu.vsk.logger.common.TransmissionMessageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransmissionMessageHandlerTestIT {

    private final AbstractTransmissionMessage abstractTransmissionMessage = new LogTransmissionMessage();
    TransmissionMessageHandler transmissionMessageHandler;
    int testValue;

    private class TestMessage extends AbstractTransmissionMessage {

        private static final String MESSAGE_TYPE = "TEST";

        private TestMessage(){
            super();
        }

        @Override
        public boolean readArgs(InputStream inputStream) {
            final DataInputStream dataInputStream = new DataInputStream(inputStream);
            try {
                addArg(dataInputStream.readInt());
                addArg(dataInputStream.readInt());
                String temp = dataInputStream.readUTF();
                while (temp.compareTo(this.getEndToken()) != 0) {
                    temp = dataInputStream.readUTF();
                }
            } catch (IOException e) {
                return false;
            }
            return true;
        }

        @Override
        public boolean writeArgs(OutputStream outputStream) {
            final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            try{
                dataOutputStream.writeInt((Integer) this.getArgList().get(0));
                dataOutputStream.writeInt((Integer) this.getArgList().get(1));
                dataOutputStream.writeUTF(this.getEndToken());
            } catch (IOException e) {
                return false;
            }
            return true;
        }

        @Override
        public boolean operate() {
            testValue = (Integer) this.getArgList().get(0) + (Integer) this.getArgList().get(1);
            return true;
        }

        @Override
        public boolean handles(String messageType) {
            return messageType.compareTo(MESSAGE_TYPE) == 0;
        }

        @Override
        public AbstractTransmissionMessage newCopy() {
            return (new TestMessage());
        }

        @Override
        protected void defineMessageType() {
            this.setMessageType(MESSAGE_TYPE);

        }

    }

    @BeforeEach
    void init(){
        transmissionMessageHandler = new TransmissionMessageHandler();
    }

    @Test
    void addMessageType() {
        transmissionMessageHandler.addMessageType(abstractTransmissionMessage);
        assertEquals(abstractTransmissionMessage,transmissionMessageHandler.getHandledMessages().get(0));
    }

    @Test
    public void testBuildMessageAndAddMessageType(){
        TransmissionMessageHandler transmissionMessageHandler = new TransmissionMessageHandler();
        transmissionMessageHandler.addMessageType(new TestMessage());
        AbstractTransmissionMessage message = transmissionMessageHandler.buildMessage("TEST");
        AbstractTransmissionMessage notAllowedMessage = transmissionMessageHandler.buildMessage("NOTALLOWED");
        boolean notAllowedMessageIsNull = false;
        if (notAllowedMessage == null) {
            notAllowedMessageIsNull = true;
        }

        assertEquals(message.getMessageType(), "TEST");
        assertTrue(notAllowedMessageIsNull);

    }
}