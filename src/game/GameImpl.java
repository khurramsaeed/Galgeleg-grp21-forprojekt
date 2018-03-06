package game;

import galgeleg.GalgeI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class GameImpl extends UnicastRemoteObject implements GameI {
    HashMap<String, GalgeI> game = new HashMap<>();

    public GameImpl() throws java.rmi.RemoteException {}
    
    @Override
    public GalgeI findPlayer(String name) throws RemoteException {
        System.out.println("findPlayer: " + name);
        return game.get(name);
    }

    @Override
    public ArrayList<String> getAllPlayers() throws RemoteException {
        System.out.println("getAllPlayers() called");
        return new ArrayList<>(game.keySet());
    }

    @Override
    public void registerPlayer(String name, GalgeI galge) throws RemoteException {
        if(game.containsKey(name)) {
            throw new IllegalArgumentException("Player with " + name + " already exists");
        }
        System.out.println("Registered player: " + name + " " + galge);
        game.put(name, galge);
    }
    
}
