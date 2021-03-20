import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the intermediate class designed specifically for all types of card games.
 * It inherits from the Game class since a card game is a game.
 */
public abstract class CardGame extends Game {
    protected List<String> playerNames; // A list of player names
    protected List<Double> playerMoney; // A list of players' money
    protected Scanner sc;
    protected int playersNumber; // The number of players in the game

    public CardGame() {
        sc = new Scanner(System.in);
    }

    public abstract void setPlayersNumber();

    public abstract void setPlayerNames();

    public abstract void setPlayerMoney();

    /*
        Get and prepare all necessary information and display important notes (e.g. rules)
        before the game
     */
    public abstract void prepare();

    /*
        When a round ends, the player can decide to start a new round or cash out
     */
    public abstract boolean playAgainOrCashOut();
}
