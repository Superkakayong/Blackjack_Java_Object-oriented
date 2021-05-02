import java.util.*;

/**
 * This class represents a card deck (i.e. a deck of (poker) cards).
 */
public class CardDeck {
    private List<Card> cardDeck;
    private List<Integer> faces;
    private List<String> suits; 

    public CardDeck() {
        cardDeck = new ArrayList<>(52);
        faces = new ArrayList<>(13);
        suits = new ArrayList<>(4);

        // Initialize the faces list
        for (int i = 1; i <= 13; ++i) { faces.add(i); }

        /*
            Initialize the suits list.

            Please note that I am using [ANSI escape code/sequence] to display the shapes and colors of the suits.
            If your machine system is Unix-based, the display should have no problems.
            However, if somehow you are using Windows and the display is not normal, please COMMENT these 4
            add() methods with [ANSI code] and UNCOMMENT the following 4 add() methods!

            BUT I really hope you can see the correct display since it will render the console colorful!
         */
        suits.add("\033[35m\u2660\033[0m"); // suit: Spade; set to purple
        suits.add("\033[35m\u2663\033[0m"); // suit: Club; set to purple
        suits.add("\033[31m\u2665\033[0m"); // suit: Heart; set to red
        suits.add("\033[31m\u2666\033[0m"); // suit: Diamond; set to red

        // If the display goes wrong, UNCOMMENT these 4 lines!
//        suits.add("Spade");
//        suits.add("Club");
//        suits.add("Heart");
//        suits.add("Diamond");

        // Initialize a standard 52 card deck
        for (String s : suits) {
            for (int i : faces) {
                cardDeck.add(new Card(i, s));
            }
        }
    }

    public List<Card> getCardDeck() {
        return cardDeck;
    }

    /*
        Shuffle the cards of the card deck
     */
    public void shuffle() {
        Collections.shuffle(faces); // Shuffle the faces
        Collections.shuffle(suits); // Shuffle the suits

        // Initialize a new standard 52 card deck
        cardDeck.clear();
        for (String s : suits) {
            for (int i : faces) {
                cardDeck.add(new Card(i, s));
            }
        }
    }
}
