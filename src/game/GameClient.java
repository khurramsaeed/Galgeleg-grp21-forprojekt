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
/**
 *
 * @author Khurram Saeed Malik
 */
public class GameClient {

    private static final String REMOTEURL = "rmi://localhost/rmicalls";
    private static GameI gameCalls;
    private static String userName;

    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        gameCalls = (GameI) Naming.lookup(REMOTEURL);
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n--- Command line client ---\n\n");
        
        System.out.println("Indtast brugernavn: \n");
        userName = sc.nextLine();
        System.out.println("Indtast adgangskoden: \n");
        String password = sc.nextLine();
        Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");

        Bruger b = ba.hentBruger(userName, password);
        System.out.println("Fik bruger = " + b);
        System.out.println("Data: " + Diverse.toString(b));
        
        gameCalls.registerPlayer(userName, new GalgeImpl());
        
        System.out.println("**********************************************");
        System.out.println("**                                          **");
        System.out.println("**              Spil Startede               **");
        System.out.println("**                                          **");
        System.out.println("**********************************************\n");
         GalgeI galgeI = new GalgeImpl();
        while (true) {
            gameStatus(galgeI, sc);
        }

    }
    
    public static void gameStatus(GalgeI galgeI, Scanner sc) throws RemoteException {
        galgeI = gameCalls.findPlayer(userName);
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
                galgeI.resetGame();
                System.out.println("**********************************************");
                System.out.println("**                                          **");
                System.out.println("**              Spil Afsluttede             **");
                System.out.println("**            Du har tabt spillet           **");
                System.out.println("**                                          **");
                System.out.println("**********************************************\n");
                return;
            }
    }

}
