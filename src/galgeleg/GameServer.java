package galgeleg;

import javax.xml.ws.Endpoint;

/**
 *
 * @author Khurram Saeed Malik
 */
public class GameServer {
    private static final String REMOTEURL = "http://[::]:9963/gameserver";
    
    public static void main(String[] args) {
        /*try {
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }*/
        
        GalgeI calls = new GalgeInterfaceImpl();
        Endpoint.publish(REMOTEURL, calls);
        //Naming.rebind(REMOTEURL, calls);
        System.out.println("SOAP server started");
        
    }
    
}
