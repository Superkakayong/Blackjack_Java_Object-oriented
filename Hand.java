import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the attributes of a hand (cards).
 */
public class Hand {
    private List<Card> handCard;
    private int handValue;
    private boolean isBust;
    private boolean isBlackJack;

    public Hand() {
        this.handCard = new ArrayList<>();
    }

    public List<Card> getHandCard() {
        return handCard;
    }

    public int getHandValue() {
        handValue = calculateHandValue();
        return handValue;
    }

    public boolean getIsBust() {
        isBust = isBust(getHandValue());
        return isBust;
    }

    public boolean getIsBlackJack() {
        isBlackJack = isBlackJack();
        return isBlackJack;
    }

    // Add a card to the current hand
    public void addCard(Card card) {
        if (card != null) { handCard.add(card); }
    }

    /*
        Calculate the current hand value
     */
    private int calculateHandValue() {
        int res = 0;
        int aceNum = 0;

        // Calculate the current hand value
        for (Card card : handCard) {
            if (card.getFace() >= 2 && card.getFace() <= 10) {
                // The face of the card is "2"-"10"
                res += card.getFace();
            } else if (card.getFace() >= 11 && card.getFace() <= 13) {
                // The face of the card is "Jack"-"King"
                res += 10;
            } else {
                // The face of the card is "Ace"
                ++aceNum;
                res += 11; // Assign face value 11 to res first
            }
        }

        // If bust, change the face value of Ace to 1
        while (res > 21 && aceNum > 0) {
            res -= 10;
            --aceNum;
        }

        return res;
    }

    // Determine if a hand is bust
    private boolean isBust(int minHandValue) {
        return minHandValue > 21;
    }

    // Determine if a hand is a Blackjack (no matter it is natural or not natural)
    private boolean isBlackJack() {
        if (handCard.size() != 2) { return false; }
        return handValue == 21;
    }
}
