import java.util.Scanner; 

/**
 * This class specifically manages the operations of a Blackjack player.
 * It inherits all the attributes from the Player class since a Blackjack player is a player.
 */
public final class BlackjackPlayer extends Player {
    private Scanner sc;
    protected Hand splitHand;

    public BlackjackPlayer() {}

    public BlackjackPlayer(String playerName, double money) {
        super(playerName, money);
        sc = new Scanner(System.in);
        splitHand = null;
    }

    /*
        A Blackjack player should be able to set their bets
     */
    @Override
    public void setBet(int amount) {
        int temp = amount;

        while (temp > money || temp < 1) {
            // While the bet is larger than the total money or is less than $1
            if (temp > money) { NotificationCenter.notEnoughBalance(money); }
            else if (temp < 1) { NotificationCenter.betMoney(playerName, 3); }

            while (true) {
                if (sc.hasNextInt()) {
                    // Input is an integer
                    temp = sc.nextInt(); break;
                } else {
                    // Invalid input, not an integer
                    sc.next(); // Free the buffer!
                    NotificationCenter.betMoney(playerName, 2);
                }
            }
        }

        bet = temp;
        money -= temp;
    }

    /*
        Initialize the player's hand.
        (i.e. deal the first two cards to the player and set the bet)
     */
    public void initializePlayerHand(Card c1, Card c2, int betAmount) {
        playerHand.addCard(c1);
        playerHand.addCard(c2);
        setBet(betAmount);
    }

    /*
        A Blackjack player can hit cards
     */
    @Override
    public void hit(Card card) {
        playerHand.addCard(card);
    }

    /*
        A Blackjack player can stand
     */
    @Override
    public void stand() {}

    /*
        A Blackjack player can split the cards
     */
    public void split(Card splitCard, Card handCard) {
        splitHand = new Hand(); // Initialize the split hand
        splitHand.addCard(playerHand.getHandCard().remove(0));
        splitHand.addCard(splitCard);
        setBet(bet); // Set another equal bet
        playerHand.addCard(handCard);
    }

    /*
        A Blackjack player can double up the bet
     */
    public void doubleUp(Card card, Hand hand) {
        setBet(bet); // Set another equal bet
        hand.addCard(card); // Hit a new card
    }
}
