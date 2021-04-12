/**
 * This class takes charge of all the operations that a dealer can perform.
 */
public abstract class Dealer{
    protected String dealerName;
    protected Hand dealerHand;
    protected CardDeck cardDeck;

    public Dealer() {}

    public Dealer(String dealerName) {
        this.dealerName = dealerName;
        
        dealerHand = new Hand();
        cardDeck = new CardDeck();
        cardDeck.shuffle();
    }

    public String getDealerName() {
        return dealerName;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    /*
        A dealer can deal cards
     */
    public abstract Card dealCard();

    /*
        A dealer can also hit cards
     */
    public abstract void hit();

    /*
        A dealer can reveal her/his cards
     */
    public abstract Card reveal();
}
