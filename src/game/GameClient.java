package game;

import galgeleg.*;
import brugerautorisation.data.Bruger;
import brugerautorisation.data.Diverse;
import brugerautorisation.transport.rmi.Brugeradmin;
import galgeleg.GalgeI;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Khurram Saeed Malik
 */
public class GameClient {
    // java -Djava.rmi.server.hostname=130.225.170.246 -cp Galgeleg-grp21.jar game.GameServer

    //private static final String REMOTEURL = "rmi://localhost/gameCalls";
    private static final String REMOTEURL = "rmi://130.225.170.246/gameCalls";
    private static GameI gameCalls;
    private static String userName, password;

    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        gameCalls = (GameI) Naming.lookup(REMOTEURL);     
        System.out.println("\n\n--- Command line client ---\n\n");
        Scanner sc = new Scanner(System.in);
        login();
        System.out.println("HEJ");
        try{
        gameCalls.registerPlayer(userName);
        } catch (IllegalArgumentException alreadyRegistred) {
            System.out.println("Velkommen tilbage " + userName);}
        
        GalgeI galgeI = gameCalls.findGame(userName);
        
        //galgeI = gameCalls.findGame(userName);
     
        System.out.println("**********************************************");
        System.out.println("**                                          **");
        System.out.println("**              Spil Startede               **");
        System.out.println("**                                          **");
        System.out.println("**********************************************\n");
        while (true) {
            gameStatus(galgeI, sc);
        }

    }
    public static void login() throws RemoteException, NotBoundException{
        Bruger user;
        Scanner sc = new Scanner(System.in);
     // Read user details
        System.out.println("Indtast brugernavn: \n");
        userName = sc.nextLine();
        System.out.println("Indtast adgangskoden: \n");
        password = sc.nextLine();
        
        try{
        Brugeradmin userAdmin = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        user = userAdmin.hentBruger(userName, password);
        
        System.out.println("Fik bruger = " + user);
        System.out.println("Data: " + Diverse.toString(user));
        } catch (IllegalArgumentException loginFejl) {
            System.out.println("loginfejl " + loginFejl.getMessage());
            login();
        } catch (MalformedURLException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    }
    public static void gameStatus(GalgeI galgeI, Scanner sc) throws RemoteException {
        System.out.println("Gæt følgende ord: " + galgeI.getVisibleWords());
            System.out.println("Du har nu brugt: " + galgeI.getUserWords());
            System.out.println("Forkert gæt: " + galgeI.getTotalWrongGuess());

            int temp = galgeI.getTotalWrongGuess();
            if (temp <= 6) {
                // todo
                // print hangman out here instead of pictures
                // hangman state depends on temp
                System.err.println("Hangmanstate: " + temp +"\n");
            }

            if (galgeI.isGameWon()) {
                System.out.println("**********************************************");
                System.out.println("**                                          **");
                System.out.println("**              Spil Afsluttede             **");
                System.out.println("**           Du har vundet spillet          **");
                System.out.println("**                                          **");
                System.out.println("**********************************************\n");
                galgeI.resetGame();
                return;
            } else if (!galgeI.isGameLost()) {
                System.out.println("Skriv dit gæt: \n");
                String guess = sc.nextLine();
                galgeI.guessWord(guess);
            } else {
                System.out.println("**********************************************");
                System.out.println("**                                          **");
                System.out.println("**              Spil Afsluttede             **");
                System.out.println("**            Du har tabt spillet           **");
                System.out.println("**        Ordet var "+ galgeI.getWord() +"  **");
                System.out.println("**                                          **");
                System.out.println("**********************************************\n");
                // Ønsker du at starte nyt eller luk?
                galgeI.resetGame();
                
                return;
            }
    }

}
