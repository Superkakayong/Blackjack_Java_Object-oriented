/**
 * This class is responsible for managing the attributes of a (poker) card.
 */
public class Card {
    private int face; // The face of a card (from 1 to 13. 1: Ace; 2-10: 2-10; 11: Jack; 12: Queen; 13: King)
    private String suit; // The suit of a card (i.e. "Heart", "Spade", "Diamonds", "Clubs")

    public Card() {}

    public Card(int face, String suit) {
        this.face = face;
        this.suit = suit; 
    }

    public int getFace() {
        return face; 
    }

    public String getSuit() {
        return suit; 
    }

    /*
        Overload the toString() method in the Object class, so the cards can be easily printed out
     */
    @Override
    public String toString() {
        String faceStr;
        String contents; // combination of suit and face of a card

        switch (this.face) {
            case 1:
                faceStr = "Ace";
                break;
            case 11:
                faceStr = "Jack";
                break;
            case 12:
                faceStr = "Queen";
                break;
            case 13:
                faceStr = "King";
                break;
            default:
                faceStr = "" + this.face;
        }

        contents = suit + " - " + faceStr;
        
        return "|" + contents + "|";
    }
}
