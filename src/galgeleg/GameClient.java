package galgeleg;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author Khurram Saeed Malik
 */
public class GameClient {

    //private static final String REMOTEURL = "http://ubuntu4.saluton.dk:9963/gameserver?wsdl";
    private static final String REMOTEURL = "serverIP?????:9963/gameserver?wsdl";

    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException, IOException {
        //gameCalls = (GalgeI) Naming.lookup(REMOTEURL);
        Scanner sc = new Scanner(System.in);
        
        //Do your calls
        URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
        QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
        Service service = Service.create(url, qname);
        Brugeradmin ba = service.getPort(Brugeradmin.class);
        
       
        URL remoteUrl = new URL(REMOTEURL);
        QName mqname = new QName("http://galgeleg/", "GalgeInterfaceImplService");
        Service gameService = Service.create(remoteUrl, mqname);
        GalgeI gameCalls = gameService.getPort(GalgeI.class);

        System.out.println("Indtast brugernavn: \n");
        String userName = sc.nextLine();
        System.out.println("Indtast adgangskoden: \n");
        String password = sc.nextLine();
        Bruger b = ba.hentBruger(userName, password);
        System.out.println("Fik bruger = " + b);
        //System.out.println("Data: " + Diverse.toString(b));
        
        System.out.println("**********************************************");
        System.out.println("**                                          **");
        System.out.println("**              Spil Startede               **");
        System.out.println("**                                          **");
        System.out.println("**********************************************\n");

        while (true) {
            gameStatus(gameCalls, sc);
        }

    }
    
    public static void gameStatus(GalgeI gameCalls, Scanner sc) {
        System.out.println("Gæt følgende ord: " + gameCalls.getVisibleWords());
            System.out.println("Du har nu brugt: " + gameCalls.getUserWords());
            System.out.println("Forkert gæt: " + gameCalls.getTotalWrongGuess());

            int temp = gameCalls.getTotalWrongGuess();
            if (temp <= 6) {
                // todo
                // print hangman out here instead of pictures
                // hangman state depends on temp
                System.err.println("Hangmanstate: " + temp +"\n");
            }

            if (gameCalls.isGameWon()) {
                System.out.println("**********************************************");
                System.out.println("**                                          **");
                System.out.println("**              Spil Afsluttede             **");
                System.out.println("**           Du har vundet spillet          **");
                System.out.println("**                                          **");
                System.out.println("**********************************************\n");
                gameCalls.resetGame();
                return;
            } else if (!gameCalls.isGameLost()) {
                System.out.println("Skriv dit gæt: \n");
                String guess = sc.nextLine();
                gameCalls.guessWord(guess);
            } else {
                gameCalls.resetGame();
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
