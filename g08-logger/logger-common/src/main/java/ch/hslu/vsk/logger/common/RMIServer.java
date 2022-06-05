package ch.hslu.vsk.logger.common;


import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * g08-logger
 * <p>
 *     The RMI Server Interface.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public interface RMIServer extends Remote {
    /**
     * to add Lister
     * @param listener
     * @throws RemoteException
     */
    void addListener(RMIListener listener) throws RemoteException;

    /**
     * to remove Listeners
     * @param listener
     * @throws RemoteException
     */
    void removeListener(RMIListener listener) throws RemoteException;
}
