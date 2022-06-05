package ch.hslu.vsk.logger.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 * g08-logger
 * <p>
 *     Log Server RMI to get Registrations.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class LogServerRMIRegistry implements Runnable {

    @Override
    public void run() {
        try {
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT); // 1099
            synchronized (registry) {
                registry.wait();
            }
        } catch (RemoteException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}