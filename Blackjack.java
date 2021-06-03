import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner; 

/**
 * This is the main class of the Blackjack game. It controls all the game logics.
 * It inherits from the CardGame class because a Blackjack is a card game.
 */
public final class Blackjack extends CardGame {
    private List<BlackjackPlayer> playerList;
    private BlackjackPlayer player;
    private BlackjackDealer dealer;
    private boolean hasRoundEnded; // A round has ended or not
    private boolean hasSplitHand; // The player has a split hand or not
    private boolean haveBothHandsDone; // Dealer must wait until both split and original hands done
    private boolean canChangeMoney; // If the player's money can be changed anymore
    private int timesOf21; // The times that the player got 21 (since there may be two hands)

    public Blackjack() {
        prepare(); // Get the player information and print out important messages
        assignPlayerAndDealer(); // Choose the dealer and player randomly
    }
    

    /*
        This is the KEY of this class, which manages all the game logics!
     */
    @Override
    public void play() {
        while (true) {
            // Initialize the member variables
            hasRoundEnded = false;
            hasSplitHand = false;
            haveBothHandsDone = false;
            canChangeMoney = true;
            timesOf21 = 0;

            int actionChoice;

            firstDeal(); // Deal cards for the first round
            showPlayerHandCards(); // Display all the cards of the player
            showDealerHandCards(false); // Display the cards of the dealer with one card hidden

            if (checkBlackjack() && playAgainOrCashOut()) {
                // Player blackjack OR Dealer blackjack OR Both blackjack (round ends),
                // and the player wishes to have another round (otherwise the game ends)
                continue;
            }

            while (true) {
                // Formally start the game
                if (!hasSplitHand) {
                    // If there is no split hand
                    actionChoice = ChooseAction(player.playerHand); // Ask for the action choice (hit/stand/...)

                    // If the player did not choose 3 (i.e. split), they won't be able to split anymore.
                    // So we mark both hands (original and split) are done
                    if (actionChoice != 3) { haveBothHandsDone = true; }

                    performAction(actionChoice, player.playerHand); // Perform the action (hit/stand/...)

                    if (hasRoundEnded) { break; } // If a round has ended, break

                } else { break; }
            }

            if (hasRoundEnded && playAgainOrCashOut()) { continue; } // If the player wishes to play again, continue


            /*
                If the code goes into here, it means the player has chosen the split action.
                The name of the two hands in our code will be : ORIGINAL and SPLIT, and
                we use the handsOperation() method to perform actions.

                Now the player should choose an action for the ORIGINAL hand
             */
            if (player.playerHand.getHandValue() != 21) {
                // If after split, the ORIGINAL hand value is not 21
                NotificationCenter.originalOrSplit(1);
                showPlayerHandCards();
                showDealerHandCards(false); // Don't show the hidden card
                actionChoice = ChooseAction(player.playerHand);
                handsOperation(actionChoice, player.playerHand, false); // Perform the action
            } else {
                // If after split, the ORIGINAL hand value is 21
                NotificationCenter.originalOrSplit(3);
                showPlayerHandCards();
                showDealerHandCards(false); // Don't show the hidden card
                checkWinnerAfterStand(player.playerHand); // Update money. No need to perform any actions
            }


            // Player should choose an action for the SPLIT hand
            if (player.splitHand.getHandValue() != 21) {
                // If after split, the SPLIT hand value is not 21
                NotificationCenter.originalOrSplit(2);
                showPlayerHandCards();
                showDealerHandCards(false);
                actionChoice = ChooseAction(player.splitHand);
                handsOperation(actionChoice, player.splitHand, true);
            } else {
                // If after split, the SPLIT hand value is 21
                NotificationCenter.originalOrSplit(4);
                showDealerHandCards(true);
                checkWinnerAfterStand(player.playerHand);
                haveBothHandsDone = true; // Both hands are done
                checkWinnerAfterStand(player.splitHand);
            }

            if (!playAgainOrCashOut()) { break; }
        }
    }

    /*
        // Get the player information and print out important messages.
     */
    @Override
    public void prepare() {
        NotificationCenter.welcome();
        final String DECISION = sc.nextLine();
        int code = NotificationCenter.startOrExit(DECISION);
        if (code == 0) {
            // Users don't want to play, exit the game
            System.exit(0);
        }

        // Get users' information
        setPlayersNumber();
        setPlayerNames();
        setPlayerMoney();
        setPlayerList();
    }

    /*
        Set the number of players
     */
    @Override
    public void setPlayersNumber() {
        while (true) {
            NotificationCenter.setPlayersNumber(1);
            if (sc.hasNextInt()) {
                playersNumber = sc.nextInt();

                if (playersNumber < 1 || playersNumber > 2) {
                    // The number can only be 1 or 2
                    NotificationCenter.setPlayersNumber(2);
                } else {
                    break;
                }
            } else {
                // Invalid input
                sc.next();
                NotificationCenter.setPlayersNumber(3);
            }
        }
    }

    /*
        Set the names of players
     */
    @Override
    public void setPlayerNames() {
        playerNames = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        for (int i = 1; i <= playersNumber; ++i) {
            NotificationCenter.setPlayers(i, 1);
            String input = sc.nextLine();
            playerNames.add(input); // Add player names to the list
        }
    }

    /*
        Set the money of each player
     */
    @Override
    public void setPlayerMoney() {
        playerMoney = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        for (int i = 1; i <= playersNumber; ++i) {
            while (true) {
                NotificationCenter.setPlayers(i, 2);
                if (sc.hasNextDouble()) {
                    double money = sc.nextDouble();

                    if (money < 1.0 || money > 1000.0) {
                        // Money must be within [$1.0, $1000.0]
                        NotificationCenter.setPlayers(i, 3);
                    } else {
                        playerMoney.add(money);
                        break;
                    }
                } else {
                    // Invalid input
                    sc.next();
                    NotificationCenter.setPlayers(i, 4);
                }
            }
        }
    }

    /*
        Initialize the player list
     */
    private void setPlayerList() {
        playerList = new ArrayList<>();

        for (int i = 0; i < playersNumber; ++i) {
            // Instantiate the Blackjack players
            BlackjackPlayer temp = new BlackjackPlayer(playerNames.get(i), playerMoney.get(i));
            playerList.add(temp);
        }
    }

    /*
        Decide who will be the player, and who will be the dealer
     */
    private void assignPlayerAndDealer() {
        if (playersNumber == 1) {
            // If there is only one player, the computer will be the dealer
            NotificationCenter.assignDealer(1, "");
            player = playerList.get(0);
            dealer = new BlackjackDealer("Computer");
        } else {
            // Otherwise, randomly select a dealer
            Random rand = new Random();
            int id = rand.nextInt(playersNumber);
            String dealerName = playerNames.get(id);
            NotificationCenter.assignDealer(2, dealerName);
            dealer = new BlackjackDealer(dealerName);

            playerList.remove(id); // Remove the player that is chosen to be the dealer
            playerNames.remove(id);
            playerMoney.remove(id); // Dealer doesn't need to bet money (i.e. money returned)

            player = playerList.get(0);
        }
    }

    /*
        Deal the cards for the first dealt and set the bet
     */
    private void firstDeal() {
        int betAmount;

        while (true) {
            NotificationCenter.betMoney(player.playerName, 1);
            if (sc.hasNextInt()) {
                // Bet is an integer (note that we enforce the bet to be integer-type!)
                betAmount = sc.nextInt();
                break;
            } else {
                // Invalid input
                sc.next();
                NotificationCenter.betMoney(player.playerName, 2);
            }
        }

        // Dealer deals two cards to the player
        Card card1 = dealer.dealCard();
        dealer.cardDeck.shuffle();
        Card card2 = dealer.dealCard();

        player.initializePlayerHand(card1, card2, betAmount);
        dealer.cardDeck.shuffle();
        dealer.initializeDealerHand();
    }

    /*
        This method is used only when the user choose to split.
        It handles the logic when there is an ORIGINAL hand and a SPLIT hand
     */
    private void handsOperation(int choice, Hand hand, boolean haveBothHandsDone) {
        this.haveBothHandsDone = haveBothHandsDone;

        while (choice == 1) {
            // Only Hit can be performed multiple times in a round
            performAction(choice, hand);

            if (hand.getIsBust() || hand.getHandValue() == 21) {
                // If bust or reaches 21, break
                return;
            }

            if (!haveBothHandsDone) {NotificationCenter.originalOrSplit(1); }
            else { NotificationCenter.originalOrSplit(2); }

            choice = ChooseAction(hand);
        }

        // The choice is not Hit, and the hand is not bust
        if (!hand.getIsBust()) { performAction(choice, hand); }
    }

    /*
        Display the cards of the player
     */
    private void showPlayerHandCards() {
        int playerValue = player.playerHand.getHandValue();
        int splitValue;

        // Display the current hand of the player
        NotificationCenter.showCards(1, playerValue);
        for (Card c : player.playerHand.getHandCard()) {
            System.out.println(c);
        }
        NotificationCenter.showCards(3, playerValue); // Show player's current ORIGINAL value
        System.out.println();

        if (hasSplitHand) {
            // If player also has a split hand, display it
            splitValue = player.splitHand.getHandValue();

            NotificationCenter.showCards(5, playerValue);
            for (Card c : player.splitHand.getHandCard()) {
                System.out.println(c);
            }
            NotificationCenter.showCards(4, splitValue); // Show player's current SPLIT value
            System.out.println();
        }
    }

    /*
        Display the cards of the player
     */
    private void showDealerHandCards(boolean showHiddenCard) {
        int dealerValue = dealer.dealerHand.getHandValue();

        NotificationCenter.showCards(2, 0);
        if (!showHiddenCard) {
            // Show the first card, and the second card should keep face down
            System.out.println(dealer.dealerHand.getHandCard().get(0));
            System.out.println("|hidden card|");
            System.out.println();
        } else {
            // Dealer should show the hidden card and all other cards
            for (Card c : dealer.dealerHand.getHandCard()) {
                System.out.println(c);
            }
            NotificationCenter.showCards(6, dealerValue); // Show dealer's current hand value
            System.out.println();
        }
    }

    /*
        Check if there is a Natural Blackjack
     */
    private boolean checkBlackjack() {
        if (player.playerHand.getIsBlackJack() && !dealer.dealerHand.getIsBlackJack()) {
            // Only Player got blackjack. Player wins
            player.money += player.bet * 2;
            NotificationCenter.winnerCongratulations(player.playerName, player.money, 2);
            return true;
        } else if (!player.playerHand.getIsBlackJack() && dealer.dealerHand.getIsBlackJack()) {
            // Only Dealer got blackjack. Dealer wins
            NotificationCenter.winnerCongratulations(player.playerName, player.money, 1);
            showDealerHandCards(true);
            return true;
        } else if (player.playerHand.getIsBlackJack() && dealer.dealerHand.getIsBlackJack()) {
            // Both got blackjack. Push
            player.money += player.bet;
            NotificationCenter.winnerCongratulations(player.playerName, player.money, 6);
            return true;
        }

        return false; // No one got blackjack
    }

    /*
        Choose an action (hit/stand/...)
     */
    private int ChooseAction(Hand hand) {
        List<Card> currentHand = hand.getHandCard();
        boolean canDoubleUp = false;
        boolean canSplit = false;
        int choice;

        if (player.money >= player.bet && currentHand.size() == 2) {
            /*
            We only allow the player to Double Up when the hand size is 2 and there is no Natural Blackjack,
            and the money is enough for another equal bet.
            There are two scenarios where the hand size could be 2:
                1. After the first deal, the player will have 2 cards
                2. Split, then the player will have 2 cards for both the ORIGINAL and SPLIT hands
             */
            canDoubleUp = true;

            if (currentHand.get(0).getFace() == currentHand.get(1).getFace() && !hasSplitHand) {
                /*
                We only allow the player to Split if the [if] statement above is satisfied and
                the two cards have the same face (i.e. rank), and there is no Split hand yet.
                In other words, the player can only Split ONCE in a round.
                A valid example: the player has two Jacks after the first deal.
                Note that if the player has a Jack and a Queen, it does not count as "same face (i.e. rank)"
                even their face values in Blackjack are both 10!
                 */
                canSplit = true;
            }
        }

        while (true) {
            if (canSplit) {
                // Player can split and double up
                NotificationCenter.getPlayerChoice(1);
            } else if (canDoubleUp) {
                // Player cannot split but can double up
                NotificationCenter.getPlayerChoice(2);
            } else {
                // Player cannot split or double up
                NotificationCenter.getPlayerChoice(6);
            }

            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (canSplit) {
                    // Can split and double up, player has 4 options
                    if (choice < 1 || choice > 4) {
                        NotificationCenter.getPlayerChoice(3);
                    } else {
                        break;
                    }
                } else if (canDoubleUp) {
                    // Cannot split but can double up, player has 3 options
                    if (choice < 1 || choice == 3 || choice > 4) {
                        NotificationCenter.getPlayerChoice(4);
                    } else {
                        break;
                    }
                } else {
                    // Cannot split or double up, player has 2 options
                    if (choice != 1 && choice != 2) {
                        NotificationCenter.getPlayerChoice(7);
                    } else {
                        break;
                    }
                }
            } else {
                // Input is not an integer
                sc.next();
                NotificationCenter.getPlayerChoice(5);
            }
        }

        return choice;
    }

    /*
        Perform the action
     */
    private void performAction(int playerChoice, Hand hand) {
        if (playerChoice == 1) {
            // Hit
            playerHit(hand);
            checkWinnerAfterHit(hand);
            if (hasSplitHand && hasRoundEnded) {
                /*
                If player chooses Stand for the ORIGINAL hand, and Hit for the Split hand.
                Then the game ends after Hit, we need to display the result for the ORIGINAL hand
                without changing the money again.
                 */
                canChangeMoney = false;
                checkWinnerAfterStand(hand);
            }
        } else if (playerChoice == 2) {
            // Stand
            playerStand();
            checkWinnerAfterStand(hand);
        } else if (playerChoice == 3) {
            // Split
            playerSplit();
        } else {
            // Double Up
            playerDoubleUp(hand);
            checkWinnerAfterHit(hand);

            if (hasRoundEnded) {
                // If a winner generated after hitting a card, no need to continue
                showPlayerHandCards();
                showDealerHandCards(true);
                if (hasSplitHand) { checkWinnerAfterStand(hand); }
                return;
            } else if (hand.getIsBust()) {
                // If the round has not finished, which means the ORIGINAL hand busts,
                // or the only hand busts (i.e. no Split action), no need to continue
                player.bet *= 0.5;
                return;
            }

            playerStand();
            checkWinnerAfterStand(hand);

            // Only one hand has doubled the bet, don't double the other hand!
            if (!haveBothHandsDone) { player.bet *= 0.5; }
        }
    }

    /*
        Player hits a card
     */
    private void playerHit(Hand hand) {
        dealer.cardDeck.shuffle();
        hand.addCard(dealer.dealCard());

        showPlayerHandCards();
        if (haveBothHandsDone) {
            if (hand.getHandValue() == 21 || hand.getIsBust()) {
                // If player got 21 or bust, show the hidden card of the dealer
                showDealerHandCards(true);
                return;
            }
        }

        showDealerHandCards(false);
    }

    /*
        Check the winner or if the hand is finished after a hit, and update the money
     */
    private void checkWinnerAfterHit(Hand hand) {
        if (hand.getIsBust()) {
            // Player hand got bust
            if (hasSplitHand) {
                // Has a SPLIT hand
                hasRoundEnded = haveBothHandsDone; // If both hands are done, the round has ended
                NotificationCenter.handOperation(player.playerName, player.money, haveBothHandsDone, 0, 1);
            } else {
                hasRoundEnded = true;
                NotificationCenter.winnerCongratulations(player.playerName, player.money, 7);
            }

        } else if (hand.getHandValue() == 21) {
            // Player got 21
            if (hasSplitHand) {
                // Has SPLIT hand
                hasRoundEnded = haveBothHandsDone;

                if (!haveBothHandsDone) { ++timesOf21; }
                else {
                    // Both hands are done
                    ++timesOf21;
                    if (dealer.dealerHand.getHandValue() != 21) {
                        // Dealer didn't get 21
                        player.money += player.bet * 2 * timesOf21;
                        NotificationCenter.handOperation(player.playerName, player.money, haveBothHandsDone,
                                                         timesOf21, 2);
                    } else {
                        // Dealer also got 21
                        player.money += player.bet * timesOf21;
                        NotificationCenter.handOperation(player.playerName, player.money, haveBothHandsDone,
                                timesOf21, 3);
                    }
                }
            } else {
                hasRoundEnded = true;
                if (dealer.dealerHand.getHandValue() != 21) {
                    // Dealer did not get 21. Player wins
                    player.money += player.bet * 2;
                    NotificationCenter.winnerCongratulations(player.playerName, player.money, 3);
                } else {
                    // Dealer also got 21. Push
                    player.money += player.bet;
                    NotificationCenter.winnerCongratulations(player.playerName, player.money, 6);
                }
            }
        }
    }

    /*
        Player stands
     */
    private void playerStand() {
        dealer.reveal(); // Dealer continues to hit until >= 17
        showPlayerHandCards();

        // If both hands (split and original) are done OR there is no split hand, dealer shows all the cards
        // Otherwise, dealer only shows the first card and "|hidden card|"
        showDealerHandCards(haveBothHandsDone || !hasSplitHand);
    }

    /*
        Check the winner or if the hand is finished after a stand, and update the money
     */
    private void checkWinnerAfterStand(Hand hand) {
        if (dealer.dealerHand.getIsBust()) {
            // Dealer got bust
            if (hasSplitHand) {
                // Has a SPLIT hand
                hasRoundEnded = haveBothHandsDone;

                if (!haveBothHandsDone) { player.money += player.bet * 2; }
                else {
                    // Both hands are done
                    if (canChangeMoney) { player.money += player.bet * 2; }
                    NotificationCenter.handOperation(player.playerName, player.money, true, 0, 4);
                }
            } else {
                // No SPLIT hand, player wins
                hasRoundEnded = true;
                player.money += player.bet * 2;
                NotificationCenter.winnerCongratulations(player.playerName, player.money, 8);
            }
        } else if (dealer.dealerHand.getHandValue() > hand.getHandValue()) {
            // Dealer got larger hand value
            if (hasSplitHand) {
                // Has a SPLIT hand
                hasRoundEnded = haveBothHandsDone;

                if (haveBothHandsDone) {
                    NotificationCenter.dealerHandValue(player.playerName, player.money, dealer.dealerHand.getHandValue());
                }
            } else {
                // No SPLIT hand, dealer wins
                hasRoundEnded = true;
                NotificationCenter.winnerCongratulations(player.playerName, player.money, 4);
            }
        } else if (dealer.dealerHand.getHandValue() < hand.getHandValue()) {
            // Player got larger hand value without busting
            if (hasSplitHand) {
                hasRoundEnded = haveBothHandsDone;

                if (hand.getIsBust()) {
                    // If busts, don't increase the player's money! Just print out the message and return.
                    NotificationCenter.dealerHandValue(player.playerName, player.money, dealer.dealerHand.getHandValue());
                    return;
                }

                if (!haveBothHandsDone) { player.money += player.bet * 2; }
                else {
                    // Both hands are done
                    if (canChangeMoney) { player.money = player.money + player.bet * 2; }
                    NotificationCenter.dealerHandValue(player.playerName, player.money, dealer.dealerHand.getHandValue());
                }
            } else {
                // No SPLIT hand, player wins
                hasRoundEnded = true;
                player.money = player.money + player.bet * 2;
                NotificationCenter.winnerCongratulations(player.playerName, player.money, 5);
            }
        } else if (dealer.dealerHand.getHandValue() == hand.getHandValue()) {
            // Dealer has the same hand value of Player
            if (hasSplitHand) {
                // Has a SPLIT hand
                hasRoundEnded = haveBothHandsDone;

                if (!haveBothHandsDone) { player.money += player.bet; }
                else {
                    // Both hands are done
                    player.money += player.bet;
                    NotificationCenter.dealerHandValue(player.playerName, player.money, dealer.dealerHand.getHandValue());
                }
            } else {
                // No SPLIT hand, push
                hasRoundEnded = true;
                player.money = player.money + player.bet;
                NotificationCenter.winnerCongratulations(player.playerName, player.money, 6);
            }
        }
    }

    /*
        Player splits
     */
    private void playerSplit() {
        dealer.cardDeck.shuffle();
        Card splitCard = dealer.dealCard();
        dealer.cardDeck.shuffle();
        Card handCard = dealer.dealCard();
        player.split(splitCard, handCard); // call the split() method
        hasSplitHand = true; // Has a SPLIT hand
    }

    /*
        Player doubles up
     */
    private void playerDoubleUp(Hand hand) {
        dealer.cardDeck.shuffle();
        player.doubleUp(dealer.dealCard(), hand);
        player.bet *= 2; // Doubles the bet
    }

    /*
        After a round, ask the player if they want to start a new round or cash out
     */
    @Override
    public boolean playAgainOrCashOut() {
        if (player.money <= 0) {
            // No cash. Directly exit
            NotificationCenter.runOutOfMoney();
            System.exit(0);
            return false;
        }

        final String userDecision;
        NotificationCenter.playAgainOrCashOut();
        sc = new Scanner((System.in));
        userDecision = sc.nextLine();

        if (userDecision.equalsIgnoreCase("Y")) {
            // Still have cash. Play another round
            player.playerHand.getHandCard().clear();
            dealer.dealerHand.getHandCard().clear();
            return true;
        } else {
            // Still have cash, but want to exit
            System.out.println("Game stops. Player still has $" + player.money + " left.");
            System.exit(0);
        }

        return false;
    }
}
