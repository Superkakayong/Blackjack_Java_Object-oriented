/**
 * This class maintains all the operations that a player can perform.
 */

public abstract class Player{
    protected String playerName;
    protected double money;
    protected int bet;
    protected Hand playerHand;

    public Player() {}

    public Player(String playerName, double money) {
        this.playerName = playerName;
        this.money = money;
        bet = 0;
        playerHand = new Hand();
    }

    public String getPlayerName() {
        return playerName;
    }

    public double getMoney() {
        return money;
    }

    public double getBet() {
        return bet;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }


    /*
        A player should be able to set their bets
     */
    public abstract void setBet(int amount);

    /*
        A player can hit cards
     */
    public abstract void hit(Card card);

    /*
        A player can stand
     */
    public abstract void stand();

    /*
        Please note that NOT all types of players can Split or Double Up!
     */
}
