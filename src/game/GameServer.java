package game;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author Khurram Saeed Malik
 */
public class GameServer {
    private static final String REMOTEURL = "rmi://130.225.170.246/gameCalls";
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        try {
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created, with port: " + 1099);
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
        
        GameI gameCalls = new GameImpl();
        Naming.rebind(REMOTEURL, gameCalls);
        System.out.println("RMI server started");
        
    }
    
}

