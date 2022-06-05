package ch.hslu.vsk.logger.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * g08-logger
 * <p>
 *     The RMI listener Interface.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public interface RMIListener extends Remote {

    /**
     * to Update all Listener with the new Message
     * @param message the new Message
     * @throws RemoteException
     */
    void push(PersistedLog message) throws RemoteException;


}
