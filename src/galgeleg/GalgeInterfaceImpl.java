package galgeleg;

import java.util.ArrayList;
import javax.jws.WebService;

/**
 *
 * @author Khurram Saeed Malik
 */
@WebService(endpointInterface = "galgeleg.GalgeI")
public class GalgeInterfaceImpl implements GalgeI {
    private Galgelogik logic;

    public GalgeInterfaceImpl() {
        logic = new Galgelogik();
    }

    @Override
    public ArrayList getUserWords() {
        return logic.getBrugteBogstaver();
    }

    @Override
    public String getVisibleWords() {
        return logic.getSynligtOrd();
    }

    @Override
    public String getWord() {
        return logic.getOrdet();
    }

    @Override
    public int getTotalWrongGuess() {
        return logic.getAntalForkerteBogstaver();
    }

    @Override
    public boolean isLastGuessCorrect() {
        return logic.erSidsteBogstavKorrekt();
    }

    @Override
    public boolean isGameWon() {
        return logic.erSpilletVundet();
    }

    @Override
    public boolean isGameLost() {
        return logic.erSpilletTabt();
    }

    @Override
    public boolean isGameOver() {
        return logic.erSpilletSlut();
    }

    @Override
    public void guessWord(String guess) {
        
        logic.gætBogstav(guess);
        logic.logStatus();
    }

    @Override
    public void resetGame() {
        logic.nulstil();
    }
    
}
