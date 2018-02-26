package galgeleg;

import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Khurram Saeed Malik
 */
@WebService
public interface GalgeI {
    //Methods that i want my server to implement and client to call
    @WebMethod ArrayList getUserWords();
    @WebMethod String getVisibleWords();
    @WebMethod String getWord();
    @WebMethod int getTotalWrongGuess();
    @WebMethod boolean isLastGuessCorrect();
    @WebMethod boolean isGameWon();
    @WebMethod boolean isGameLost();
    @WebMethod boolean isGameOver();
    @WebMethod void guessWord(String guess);
    @WebMethod void resetGame();
   
}