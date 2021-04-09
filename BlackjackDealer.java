/**
 * This class specifically manages the operations of a Blackjack dealer.
 * It inherits all the attributes from the Dealer class since a Blackjack dealer is a dealer.
 */
public final class BlackjackDealer extends Dealer{
    private Card hiddenCard;

    public BlackjackDealer() {}

    public BlackjackDealer(String dealerName) {
        super(dealerName);
    }

    /*
        A Blackjack dealer can deal cards
     */
    @Override
    public Card dealCard() {
        if (cardDeck.getCardDeck().size() <= 7) {
            // If the card deck is going to run out of cards, create a new one
            cardDeck = new CardDeck();
            cardDeck.shuffle();
        }

        return cardDeck.getCardDeck().remove(0); // Return the top card of the card deck
    }

    /*
        Initialize the dealer's hand.
        (i.e. deal the first two cards (including a hidden card) to the dealer)
     */
    public void initializeDealerHand() {
        dealerHand.addCard(dealCard());
        cardDeck.shuffle();
        hiddenCard = dealCard();
        dealerHand.addCard(hiddenCard);
    }

    /*
        A Blackjack dealer can hit cards
     */
    @Override
    public void hit() {
        dealerHand.addCard(dealCard());
    }

    /*
        A Blackjack dealer can reveal her/his cards until the hand value >= 17
     */
    @Override
    public Card reveal() {
        while (dealerHand.getHandValue() < 17) { cardDeck.shuffle(); hit(); }
        
        return hiddenCard;
    }
}
